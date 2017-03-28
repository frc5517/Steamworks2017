package org.usfirst.frc.team5517.robot.subsystems;

import org.usfirst.frc.team5517.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Intake subsystem
 * Sucks fuel into our robot
 * Has methods for spinning intake bar and raising/lowering the entire mechanism
 */
public class Intake extends Subsystem {
	
	private final double ROLLER_IN_SPEED = 0.5;
	private final double ROLLER_OUT_SPEED = 1;
	private final double LIFT_SPEED = 0.2; //ACUTALLY LOWER
	private final double LOWER_SPEED = 1; //ACTUALLY LIFT

    private Talon intakeMotor;
    private Talon intakeLiftMotor;
    private Talon intakeLiftMotorTwo;
    
    public Intake() {
    	intakeLiftMotor = new Talon(RobotMap.intakeLiftMotorPWMPort);
    	intakeLiftMotorTwo = new Talon(RobotMap.intakeLiftMotorTwoPWMPort);
    	intakeMotor = new Talon(RobotMap.intakeMotorPWMPort);
    }

    @Override
	protected void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new Intake());
    }
    
    public void spinRollerIn() {
    	intakeMotor.set(ROLLER_IN_SPEED);
    }
    
    public void spinRollerOut() {
    	intakeMotor.set(-ROLLER_OUT_SPEED);
    }
    
    public void liftIntake() {
    	intakeLiftMotor.set(-LIFT_SPEED);
    }
    
    public void lowerIntake() {
    	intakeLiftMotor.set(LOWER_SPEED);
    }
    
    public void stopIntakeLift() {
    	intakeLiftMotor.set(0);
    }
    
    public void stopRoller() {
    	intakeMotor.set(0);
    }
}

