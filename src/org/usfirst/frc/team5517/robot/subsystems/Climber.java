package org.usfirst.frc.team5517.robot.subsystems;

import org.usfirst.frc.team5517.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

	private final double CLIMB_SPEED = 0.5; 
	
	private Spark climbMotor;
	
	public Climber() {
		climbMotor = new Spark(RobotMap.climberMotorPWMPort);
	}
	
    @Override
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void up() {
    	climbMotor.set(CLIMB_SPEED);
    }
    
    public void down() {
    	climbMotor.set(-CLIMB_SPEED);
    }

	public void stop() {
		climbMotor.set(0);
		
	}
    
}

