package org.usfirst.frc.team5517.robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class WaitCommand extends TimedCommand {

    public WaitCommand(double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(timeout);
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
	protected void end() {
    }
}
