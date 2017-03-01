package org.usfirst.frc.team5517.robot.commands;

import org.usfirst.frc.team5517.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

//import org.usfirst.frc.team5517.robot.Robot.autonomousCommand;
/**
 *
 */
public class DriveForward extends TimedCommand {

    public DriveForward(double timeout) {
        super(timeout);
        requires(Robot.driveTrain);
    }

	// Called just before this Command runs the first time
    @Override
	protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    	Robot.driveTrain.drive(0, .25, 0);
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
