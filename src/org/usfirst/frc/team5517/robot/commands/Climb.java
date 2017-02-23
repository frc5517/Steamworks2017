package org.usfirst.frc.team5517.robot.commands;

import org.usfirst.frc.team5517.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Climber command:
 * This command is called when the operator holds A
 * It then checks the left joystick to determine whether to climb up or down
 * (Safety Feature)
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
    	double y = Robot.oi.getOperatorLeftY();
    	if(y < 0.5)
    		Robot.climber.up();
    	else if(y > -0.5)
    		Robot.climber.down();
    	else
    		Robot.climber.stop();
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
