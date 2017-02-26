
package org.usfirst.frc.team5517.robot;

import org.usfirst.frc.team5517.robot.subsystems.Climber;
import org.usfirst.frc.team5517.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5517.robot.subsystems.DumpDoor;
import org.usfirst.frc.team5517.robot.subsystems.Intake;
import org.usfirst.frc.team5517.robot.utils.Debouncer;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * Main Robot class
 * The VM on the RoboRIO calls the methods in this class during a match
 *
 */
public class Robot extends IterativeRobot {

	public static final DriveTrain driveTrain = new DriveTrain();
	public static final Intake intake = new Intake();
	public static final DumpDoor dumpDoor = new DumpDoor();
	public static final Climber climber = new Climber();
	public static OI oi;
	
	public boolean matchStarted = false;
	
	// Gyro variables
	private double curAngle;
	private double lastAngle;
	private boolean gyroCalibrating;
	private boolean lastGyroCalibrating;
	private int gyroReinits;
	private Debouncer gyroDriftDetector;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		System.out.println("Robot Initializing...");
		
		// Create controls
		oi = new OI();
		
		// Gyro stuff
		gyroDriftDetector = new Debouncer(1.0);
		driveTrain.calibrateGyro();
		
		//chooser.addDefault("Default Auto", new ExampleCommand());
		//chooser.addObject("My Auto", new MyAutoCommand());
		//SmartDashboard.putData("Auto mode", chooser);
		
		System.out.println("Robot Initialized");
	}
	
	@Override
	public void robotPeriodic() {}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		matchStarted = false;
		System.out.println("Robot Disabled");
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		reinitGyro();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
	
		matchStarted = true;
		driveTrain.stopCalibrating();

		autonomousCommand = chooser.getSelected();
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
		
		System.out.println("Autonomous Initialized");
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		matchStarted = true;
		
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		System.out.println("Initial Drivetrain Heading: " + driveTrain.getHeading());
		System.out.println("Teleop Initialized");
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	/**
	 * If the gyro is drifting, re-initialize/calibrate it
	 * (Thanks to FRC 2168 for this)
	 */
	public void reinitGyro() {
		
		curAngle = driveTrain.getHeading();
		gyroCalibrating = driveTrain.isGyroCalibrating();

		if (lastGyroCalibrating && !gyroCalibrating) {
			// if we've just finished calibrating the gyro, reset
			gyroDriftDetector.reset();
			curAngle = driveTrain.getHeading();
			// reset target angle so when we enable it doesn't try to correct
			// to the target angle before gyro calibrated
			driveTrain.setTargetAngle(0);
			System.out.println("Finished auto-reinit gyro");
		}
		else if (gyroDriftDetector.update(Math.abs(curAngle - lastAngle) > (0.75 / 50.0))
				&& !matchStarted && !gyroCalibrating) {
			// start calibrating gyro
			gyroReinits++;
			System.out.println("!!! Sensed drift, about to auto-reinit gyro (#"+ gyroReinits + ")");
			driveTrain.calibrateGyro();
		}

		lastAngle = curAngle;
		lastGyroCalibrating = gyroCalibrating;
		
	}
	
}
