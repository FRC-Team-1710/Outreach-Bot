// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IntakerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class Shoot extends Command {
  ShooterSubsystem m_shooterSubsystem;
  IntakerSubsystem m_intakeSubsystem;
  XboxController controller;
  private boolean resetHood;

  /** Creates a new SHOOT. */
  public Shoot(ShooterSubsystem shooter, IntakerSubsystem intak, XboxController controller) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intakeSubsystem = intak;
    m_shooterSubsystem = shooter;
    this.controller = controller;
    resetHood = false;
    addRequirements(shooter, intak);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooterSubsystem.setFlywheelVelocity(Constants.Shooter.shootSpeedRPM);
    controller.setRumble(
        RumbleType.kBothRumble,
        m_shooterSubsystem.getFlywheelVelocity() / Constants.Shooter.shootSpeedRPM);
    if (m_shooterSubsystem.IsUpToSpeed()) {
      resetHood = true; // If it actually launched, bring hood back down
      m_intakeSubsystem.setInsideVel();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooterSubsystem.setFlywheelVelocity(Constants.Shooter.idleSpeedRPM);
    if (resetHood) {
      m_shooterSubsystem.setHoodPosition(Constants.Shooter.Offset);
    }
    m_intakeSubsystem.StopAll();
    controller.setRumble(RumbleType.kBothRumble, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
