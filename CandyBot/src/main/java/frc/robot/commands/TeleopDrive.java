// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import javax.xml.xpath.XPath;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class TeleopDrive extends Command {
  /** Creates a new TeleopDrive. */
  private final Drivetrain drivetrain;
  private final DoubleSupplier forwardSpeedSupplier;
  private final DoubleSupplier turnSpeedSupplier;
  private final BooleanSupplier goFastSupplier;

  public TeleopDrive(Drivetrain drivetrain, DoubleSupplier forwardSpeedSupplier, DoubleSupplier turnSpeedSupplier,
      BooleanSupplier goFastSupplier) {
    this.drivetrain = drivetrain;
    this.forwardSpeedSupplier = forwardSpeedSupplier;
    this.turnSpeedSupplier = turnSpeedSupplier;
    this.goFastSupplier = goFastSupplier;
    addRequirements(this.drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //apply deadband to speeds returned from the joystick
    double forwardSpeed = -MathUtil.applyDeadband(forwardSpeedSupplier.getAsDouble(), 0.05);
    double turnSpeed = -MathUtil.applyDeadband(turnSpeedSupplier.getAsDouble(), 0.05);

    //if they are holding the go fast button, do not slow it down.
    if(goFastSupplier.getAsBoolean() == false){
      //divide speed in half
      forwardSpeed = forwardSpeed * 0.75;
      turnSpeed = turnSpeed * 0.75;
    }

    drivetrain.drive(forwardSpeed, turnSpeed);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //stop the robot
    drivetrain.drive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
