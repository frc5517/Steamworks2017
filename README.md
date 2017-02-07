## Steamworks 2017
FRC Team 5517's repository for the 2017 game, Steamworks.

![Steamworks](http://firstinspires.org/sites/default/files/uploads/resource_library/frc/game-and-season-info/competition-manual/2017/first-steamworks-transparent-logo.png)

##Java Docs
The Official WPILib Java documentation can be found
[here](https://wpilib.screenstepslive.com/s/4485/m/13809)

## FRC Plugins
### How to install FRC plugins
1. Open Eclipse
2. Select "Help" on the top menu bar
3. Click "Install New Software"
4. Click "Add..."  at the right of "Work with" slot
5. Name: FRC Plugins
6. Location: http://first.wpi.edu/FRC/roborio/release/eclipse/
7. Click "OK"
8. Agree with terms
9. Click "Finish"

***Note: You need to install the FRC Plugins before you do the below steps.***

## Cloning the Repository
1. Ensure you have git installed - if not, get it from: https://git-scm.com/downloads
2. Open the command line program (Windows: Git Bash, Mac: Terminal)
3. Navigate to your Eclipse workspace folder (whatever you chose when opening Eclipse): `cd ~/FOLDER`
   (Possibly: `Documents/workspace` or `workspace`)
4. Run `git clone https://github.com/frc5517/Steamworks2017.git`
5. Enter GitHub username and password if prompted
6. Once it is cloned, cd into the directory (`cd Steamworks2017`)
7. List directory contents, ensuring everything is there: `ls`

## Importing Project into Eclipse
1. In Eclipse, go to File > Import...
2. Double click Git and then Projects from Git
3. Double click **Existing local repository**
4. If "Steamworks2017" is not listed:
   1. Click Add... and then Browse
   2. Find the workspace directory, select it and click Open
   3. Click Search
   4. Check the checkbox next to the repository found (Steamworks2017) and click Finish
5. Select "Steamworks2017" and click Next
6. Select **Import using New Project Wizard** and then click Finish
9. Expand **WPILib Robot Java Development** and double click *Robot Java Project*
10. Enter Steamworks2017 for Project Name
11. Select Command-Based Robot for Project Type
12. Click Finish

Eclipse should now open the project and import the code from the repository. 

You will notice there is probably an error. This is because the WPILib wizard creates the "ExampleSubsystem" and "ExampleCommand" classes. You just need to delete these to fix this error.

1. Expand Steamworks2017 if it isn't already expanded
2. Expand `src`
3. Expand `org.usfirst.frcteam5517.robot.commands` and `org.usfirst.frcteam5517.robot.subsystems`
4. Delete `ExampleCommand.java` and `ExampleSubsystem.java` from their respective directories.
5. The errors should now vanish.

### Voila! You're now all setup.

Whenever you make changes and are ready to commit, go to command line, ensure you're in the project folder, and you can then run the git commands from there. For info on git, check out https://git-scm.com/

Ask Nathan if you need assistance.
