/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.DriveManualCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX; //******** */
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * Add your docs here.
 */

public class DriveSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private RobotContainer container;

  public WPI_VictorSPX leftMaster = new WPI_VictorSPX(Constants.leftMasterPort); // ******** changed talon to victor*/
  public WPI_VictorSPX leftSlave = new WPI_VictorSPX(Constants.leftSlavePort); // ******** changed talon to victor*/

  public WPI_VictorSPX rightMaster = new WPI_VictorSPX(Constants.rightMasterPort); // ******** changed talon to victor*/
  public WPI_VictorSPX rightSlave = new WPI_VictorSPX(Constants.rightSlavePort); // ******** changed talon to victor*/

  public SpeedControllerGroup left = new SpeedControllerGroup(leftMaster, leftSlave);
  public SpeedControllerGroup right = new SpeedControllerGroup(rightMaster, rightSlave);
  public DifferentialDrive drive = new DifferentialDrive(left, right);

  public DriveSubsystem(RobotContainer container) {
    this.container = container;
  }

  public void manualTankDrive() {
    /*
     * if (move > 0.5) { move = 0.5; }
     */
    if (container.controller.getBButton()) {// Kill switch
      leftMaster.set(ControlMode.PercentOutput, 0);
      rightMaster.set(ControlMode.PercentOutput, 0);
    }
    else if (container.rightTrigger > 0.01 && !(container.leftTrigger > 0.01)) {// forward straight
      leftMaster.set(ControlMode.PercentOutput, -container.rightTrigger);
      rightMaster.set(ControlMode.PercentOutput, container.rightTrigger);
    }
    else if (container.leftTrigger > 0.01 && !(container.leftTrigger > 0.01)) {// backward straight
      rightMaster.set(ControlMode.PercentOutput, -container.leftTrigger);
      leftMaster.set(ControlMode.PercentOutput, container.leftTrigger);
    }
    else {
      drive.tankDrive(container.leftStickY, container.rightStickY, true);
    }
  }

  public void curvatureDrive(){
      drive.curvatureDrive(container.leftStickY, container.rightStickX, container.leftTrigger > 0.1);
  }
  
  public void init() {
    leftMaster.set(ControlMode.PercentOutput, 0);
    rightMaster.set(ControlMode.PercentOutput, 0);
    leftSlave.set(ControlMode.PercentOutput, 0);
    rightSlave.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new DriveManualCommand(container));
  }
}
