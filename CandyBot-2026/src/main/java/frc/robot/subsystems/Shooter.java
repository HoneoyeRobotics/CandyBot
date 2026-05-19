// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

  public VictorSPX shooterMotor = new VictorSPX(Constants.CanIDs.Shooter);
  public VictorSPX feederMotor = new VictorSPX(Constants.CanIDs.Feeder);

  /** Creates a new Shooter. */
  public Shooter() {
  }

  public void runShooter(double speed) {
    shooterMotor.set(ControlMode.PercentOutput, speed);

  }

  public void runFeeder(double speed) {
    feederMotor.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
