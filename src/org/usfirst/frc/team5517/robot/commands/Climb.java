package org.usfirst.frc.team5517.robot.commands;

import org.usfirst.frc.team5517.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Climb extends Command {

    public Climb() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.climber);
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    	double x = Robot.oi.getOperatorLeftX();
    	if(x > 0.5)
    		Robot.climber.up();
    	else if(x < - 0.5)
    		Robot.climber.down();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
	protected void end() {
    	Robot.climber.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    	end();
    }
}
