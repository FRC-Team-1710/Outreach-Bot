// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class ManualAim extends Command {
  ShooterSubsystem m_shooterSubsystem;
  private DoubleSupplier speed;
  private boolean hoodIsLocked = false;
  private DoubleSupplier LEFTspd;
  private DoubleSupplier RIGHTspd;
  private boolean reset = false;
  private boolean isIntaking;

  /** Is the left trigger being prioritized */
  boolean isItLeft = false;

  /** Creates a new ManualAim. */
  public ManualAim(ShooterSubsystem shot, DoubleSupplier lspd, DoubleSupplier rspd, BooleanSupplier a) {
    m_shooterSubsystem = shot;

    LEFTspd = lspd;
    RIGHTspd = rspd;
    isIntaking = a.getAsBoolean();

    SmartDashboard.putNumber("Hood Setpoint (Degrees)", 0);
    SmartDashboard.putBoolean("Left trigger", isItLeft);
    addRequirements(shot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (MathUtil.applyDeadband(LEFTspd.getAsDouble(), Constants.triggerDeadband) < MathUtil.applyDeadband(RIGHTspd.getAsDouble(), Constants.triggerDeadband)) {
      speed = RIGHTspd;
      reset = true;
      isItLeft = false;
    } else if (MathUtil.applyDeadband(LEFTspd.getAsDouble(), Constants.triggerDeadband) > MathUtil.applyDeadband(RIGHTspd.getAsDouble(), Constants.triggerDeadband)) {
      speed = LEFTspd;
      reset = false;
      isItLeft = true;
    } else {
      speed = LEFTspd;
    }
    SmartDashboard.putBoolean("Left trigger", isItLeft);

    double speedValue = MathUtil.applyDeadband(speed.getAsDouble(), Constants.triggerDeadband);
    speedValue = Math.pow(speedValue, 1);
    if (isItLeft) {
      speedValue = speedValue * -1; // Invert the power if it's the left trigger
    }
    speedValue = speedValue * 0.1;
    if (!isIntaking) {
      if (Math.abs(speedValue) > .0) {
        hoodIsLocked = false;
        m_shooterSubsystem.setHoodManual(speedValue);
      } else {
        if (m_shooterSubsystem.isItZeroed()) {
          if (!hoodIsLocked) {
            if (reset) {
              m_shooterSubsystem.setHoodPosition(m_shooterSubsystem.getHoodPosition());
              hoodIsLocked = true;
              reset = false;
            } else {
              hoodIsLocked = true;
              m_shooterSubsystem.setHoodManual(0);
            }
          }
        } else {
          m_shooterSubsystem.setHoodManual(0);
        }
      }
    }
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
