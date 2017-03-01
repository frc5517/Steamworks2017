package org.usfirst.frc.team5517.robot.commands;

import org.usfirst.frc.team5517.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnToAngle extends Command {

	private double angle;
	private boolean hasRun = false;
	
    public TurnToAngle(double angle) {
        requires(Robot.driveTrain);
    	this.angle = angle;
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    	if(!hasRun) {
    		Robot.driveTrain.turnToAngle(angle);
    		hasRun = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {
        return Robot.driveTrain.isAngleOnTarget();
    }

    // Called once after isFinished returns true
    @Override
	protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    }
}
