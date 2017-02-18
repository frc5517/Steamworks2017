package org.usfirst.frc.team5517.robot.subsystems;

import org.usfirst.frc.team5517.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {
	
	private final double ROLLER_IN_SPEED = 0.7;

    private Talon intakeMotor;
    
    public Intake() {
    	intakeMotor = new Talon(RobotMap.intakeMotorPWMPort);
    }

    @Override
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new Intake());
    }
    
    public void spinRollerIn() {
    	intakeMotor.set(ROLLER_IN_SPEED);
    }
    
    public void stopRoller() {
    	intakeMotor.set(0);
    }
}

