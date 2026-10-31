// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  private DifferentialDrive differentialDrive;
  private WPI_TalonSRX leftMotorController;
  private WPI_VictorSPX rightMotorController;
  private WPI_TalonSRX leftFollowerMotorController;
  private WPI_VictorSPX rightFollowerMotorController;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    // declare motor controllers
    // left side is Talon SRX, right side is Victor SPX
    leftMotorController = new WPI_TalonSRX(Constants.CanIDs.LeftDrive);
    rightMotorController = new WPI_VictorSPX(Constants.CanIDs.RightDrive);
    leftFollowerMotorController = new WPI_TalonSRX(Constants.CanIDs.LeftDriveFollower);
    rightFollowerMotorController = new WPI_VictorSPX(Constants.CanIDs.RightDriveFollower);

    // second motor on each side follows the first
    leftFollowerMotorController.follow(leftMotorController);
    rightFollowerMotorController.follow(rightMotorController);

    // set the right side as inverted
    rightMotorController.setInverted(true);

    // followers match their leader's direction
    leftFollowerMotorController.setInverted(InvertType.FollowMaster);
    rightFollowerMotorController.setInverted(InvertType.FollowMaster);

    // declare differential drive
    differentialDrive = new DifferentialDrive(leftMotorController, rightMotorController);
    SmartDashboard.putData("Drive/Differential Drive", differentialDrive);
  }

  /***
   * Drive the robot using an Arcade Drive
   * 
   * @param forwardSpeed speed to move the robot forward
   * @param rotateSpeed  speed to rotate the robot
   */
  public void drive(double forwardSpeed, double rotateSpeed) {
    differentialDrive.arcadeDrive(forwardSpeed, rotateSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    logMotor("Left", leftMotorController);
    logMotor("Left Follower", leftFollowerMotorController);
    logMotor("Right", rightMotorController);
    logMotor("Right Follower", rightFollowerMotorController);

    SmartDashboard.putString("Drive/Current Command",
        getCurrentCommand() == null ? "none" : getCurrentCommand().getName());
  }

  /***
   * Put debug info for one motor controller on the SmartDashboard
   *
   * @param name            label to show on the dashboard
   * @param motorController motor controller to read from
   */
  private void logMotor(String name, BaseMotorController motorController) {
    String prefix = "Drive/" + name + "/";
    // firmware version reads -1 if the controller is not found on the CAN bus
    SmartDashboard.putNumber(prefix + "Firmware", motorController.getFirmwareVersion());
    SmartDashboard.putNumber(prefix + "Output %", motorController.getMotorOutputPercent());
    SmartDashboard.putNumber(prefix + "Output Volts", motorController.getMotorOutputVoltage());
    SmartDashboard.putNumber(prefix + "Bus Volts", motorController.getBusVoltage());
    // only Talons can measure current, Victors cannot
    if (motorController instanceof WPI_TalonSRX talon) {
      SmartDashboard.putNumber(prefix + "Supply Amps", talon.getSupplyCurrent());
    }
    SmartDashboard.putBoolean(prefix + "Inverted", motorController.getInverted());
    SmartDashboard.putString(prefix + "Control Mode", motorController.getControlMode().toString());
    SmartDashboard.putString(prefix + "Last Error", motorController.getLastError().toString());

    Faults faults = new Faults();
    motorController.getFaults(faults);
    SmartDashboard.putBoolean(prefix + "Has Faults", faults.hasAnyFault());
  }
}
