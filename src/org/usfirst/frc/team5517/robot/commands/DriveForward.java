package org.usfirst.frc.team5517.robot.commands;

import org.usfirst.frc.team5517.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

//import org.usfirst.frc.team5517.robot.Robot.autonomousCommand;
/**
 *
 */
public class DriveForward extends TimedCommand {

	private double speed;
	
    public DriveForward(double timeout, double speed) {
        super(timeout);
        requires(Robot.driveTrain);
        this.speed = speed;
    }

	// Called just before this Command runs the first time
    @Override
	protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    	Robot.driveTrain.drive(0, speed, 0);
    	System.out.println("speed: "+speed);
    }

	// Called once after timeout
    @Override
	protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    }
}
