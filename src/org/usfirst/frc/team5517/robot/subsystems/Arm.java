package org.usfirst.frc.team5517.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem{
	
	
	DoubleSolenoid exampleDouble = new DoubleSolenoid(1, 2); {
	
	exampleDouble.set(DoubleSolenoid.Value.kOff);
	exampleDouble.set(DoubleSolenoid.Value.kForward);
	exampleDouble.set(DoubleSolenoid.Value.kReverse);
	}
	
	Compressor c = new Compressor(0); {
		c.setClosedLoopControl(true);
		c.setClosedLoopControl(false);
	}

	@Override
	protected void initDefaultCommand() {
		
	}

	public void raise() {
		exampleDouble.set(DoubleSolenoid.Value.kForward);
	}
	
	public void lower() {
		exampleDouble.set(DoubleSolenoid.Value.kReverse);
	}

	public void stop() {
		exampleDouble.set(DoubleSolenoid.Value.kOff);
	}
}
