package org.usfirst.frc.team5517.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem{
	
	
	DoubleSolenoid solenoid;
	
	public Arm() {
		solenoid = new DoubleSolenoid(1, 2);
	}

	@Override
	protected void initDefaultCommand() {
		
	}

	public void raise() {
		solenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void lower() {
		solenoid.set(DoubleSolenoid.Value.kForward);
	}

	public void stop() {
		solenoid.set(DoubleSolenoid.Value.kOff);
	}
}