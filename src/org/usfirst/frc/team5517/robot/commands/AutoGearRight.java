package org.usfirst.frc.team5517.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGearRight extends CommandGroup {

    public AutoGearRight() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	addSequential(new LowerIntake());
    	//TO-DO make RaiseIntakeToPeg command
    	addSequential(new DriveForward(3, 0.5));
    	addSequential(new TurnToAngle(45));
    	addSequential(new DriveForward(3, 0.5));
    	addSequential(new SpinIntakeRollerOut());
    	addSequential(new DriveForward(2, -0.5));
    }
}
