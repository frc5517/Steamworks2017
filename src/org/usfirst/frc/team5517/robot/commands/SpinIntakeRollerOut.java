package org.usfirst.frc.team5517.robot.commands;

import org.usfirst.frc.team5517.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SpinIntakeRollerOut extends Command {

    public SpinIntakeRollerOut() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    	Robot.intake.spinRollerOut();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
	protected void end() {
    	Robot.intake.stopRoller();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    	end();
    }
}
