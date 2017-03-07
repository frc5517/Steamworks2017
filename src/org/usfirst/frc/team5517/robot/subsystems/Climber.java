package org.usfirst.frc.team5517.robot.subsystems;

import org.usfirst.frc.team5517.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climber subsystem
 * Methods to turn the climber wench to climb either up or down
 */
public class Climber extends Subsystem {

	private final double CLIMB_SPEED = 1;
	
	private Spark climbMotor;
	
	public Climber() {
		climbMotor = new Spark(RobotMap.climberMotorPWMPort);
	}
	
    @Override
	public void initDefaultCommand() {}
    
    public void up(double speed) {
    	climbMotor.set(-speed);
    }
    
    public void down(double speed) {
    	climbMotor.set(-speed);
    }

	public void stop() {
		climbMotor.set(0);
	}
    
}

