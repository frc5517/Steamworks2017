package org.usfirst.frc.team5517.robot.commands;

import org.usfirst.frc.team5517.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class TimedLowerIntake extends TimedCommand {

    public TimedLowerIntake(double timeout) {
        super(timeout);
        // Use requires() here to declare subsystem dependencies
        requires(Robot.intake);
        requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.lowerIntake();
    	Robot.arm.lower();
    }

    // Called once after timeout
    protected void end() {
    	Robot.intake.stopIntakeLift();
    	Robot.arm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
