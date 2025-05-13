// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private DifferentialDrive differentialDrive;
  private WPI_TalonSRX leftMotorController;
  private WPI_TalonSRX rightMotorController;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    // declare motor controllers
    leftMotorController = new WPI_TalonSRX(1);
    rightMotorController = new WPI_TalonSRX(2);

    // set the right side as inverted
    rightMotorController.setInverted(true);

    // declare differential drive
    differentialDrive = new DifferentialDrive(leftMotorController, rightMotorController);
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
  }
}
