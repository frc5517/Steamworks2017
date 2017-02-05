package org.usfirst.frc.team5517.robot.commands;

import org.usfirst.frc.team5517.robot.OI;
import org.usfirst.frc.team5517.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive train subsystem
 */
public class Drive extends Command {

	private OI oi = Robot.oi;
	
    public Drive() {
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    	// Drive robot with the x and y from left joystick,
    	// and the x from the right joystick (for rotation)
    	Robot.driveTrain.drive(
    		oi.getDriverLeftX(),
    		oi.getDriverLeftY(), 
    		oi.getDriverRightX()
    	);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
	protected void end() {
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    	end();
    }
}
