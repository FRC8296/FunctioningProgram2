/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;

//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX; //******** */
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.RobotContainer;
import frc.robot.commands.ClimbManualCommand;

/**
 * Add your docs here.
 */

public class Climb extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public RobotContainer container;

    public Climb(RobotContainer container){
        this.container = container;
    }

    public WPI_VictorSPX climb1 = new WPI_VictorSPX(Constants.climb1);
    public WPI_VictorSPX climb2 = new WPI_VictorSPX(Constants.climb2);
    public WPI_VictorSPX climb3 = new WPI_VictorSPX(Constants.climb3);

    public SpeedControllerGroup climb = new SpeedControllerGroup(climb1, climb2, climb3);

    public void setSpeed(double speed) {
        climb.set(speed);
    }

    public void startClimb() {
        // Might need to adjust speed based on direction of motors
        climb.set(1);
    }

    public void updateClimb(){
        if (container.controller.getAButton() && !container.controller.getYButton()) {
            climb.set(1);
          } else if (!container.controller.getAButton() && container.controller.getYButton()) {
            climb.set(-1);
          } else {
            climb.set(0);
        }
    }

    public void liftHook() {
        climb.disable();
    }

    public void holdHook() {
        climb.stopMotor();
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ClimbManualCommand(container));
    }
}
