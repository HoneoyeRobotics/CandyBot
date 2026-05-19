// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */
  public Shooter() {}

  private WPI_VictorSPX shooterMotor = new WPI_VictorSPX(Constants.CanIDs.ShooterMotor);

  public void Shoot(double Speed){
    shooterMotor.set(Speed);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
