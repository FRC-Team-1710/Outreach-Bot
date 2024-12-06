// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

public class ManualAim extends Command {
  ShooterSubsystem m_shooterSubsystem;
  private DoubleSupplier speed;
  private boolean hoodIsLocked = false;

  /** Is the left trigger being prioritized */
  boolean isItLeft = false;

  /** Creates a new ManualAim. */
  public ManualAim(ShooterSubsystem shot, DoubleSupplier LEFTspd, DoubleSupplier RIGHTspd) {
    m_shooterSubsystem = shot;

    if (LEFTspd.getAsDouble() > RIGHTspd.getAsDouble()) {
      this.speed = LEFTspd;
      isItLeft = true; //TODO change this to invert the controlls
    } else {
      this.speed = RIGHTspd;
      isItLeft = false; //TODO change this to invert the controlls
    }
    
    SmartDashboard.putNumber("Set Extender Angle", 0);
    SmartDashboard.putBoolean("Left trigger", isItLeft);
    addRequirements(m_shooterSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speedValue = MathUtil.applyDeadband(speed.getAsDouble(), Constants.triggerDeadband);
    speedValue = Math.pow(speedValue, 3);
    if (isItLeft) {
      speedValue = speedValue*-1; // Invert the power if it's the left trigger
    }
    if (Math.abs(speedValue) > .0) {
      hoodIsLocked = false;
      m_shooterSubsystem.setHoodManual(speedValue);
    } else {
      if (m_shooterSubsystem.isItZeroed()) {
          if (!hoodIsLocked) {
              m_shooterSubsystem.setHoodPosition(m_shooterSubsystem.getHoodPosition());
              hoodIsLocked = true;
          }
      } else {
        m_shooterSubsystem.setHoodManual(0);
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
