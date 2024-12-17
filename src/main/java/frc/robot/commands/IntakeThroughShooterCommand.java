// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class IntakeThroughShooterCommand extends Command {

  IndexerSubsystem indexerSubsystem;
  IntakeSubsystem intakeSubsystem;
  ShooterSubsystem shooterSubsystem;

  public IntakeThroughShooterCommand(IndexerSubsystem m_IndexerSubsystem, IntakeSubsystem m_IntakeSubsystem, ShooterSubsystem m_ShooterSubsystem) {
    indexerSubsystem = m_IndexerSubsystem;
    intakeSubsystem = m_IntakeSubsystem;
    shooterSubsystem = m_ShooterSubsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    indexerSubsystem.setIndexerPower(Constants.IndexerSubsystemsConstants.indexerIntakeThroughShooterPower);
    shooterSubsystem.isIntaking = true;
    SmartDashboard.putBoolean("Intaking Through Shooter", true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    indexerSubsystem.setIndexerPower(0);
    shooterSubsystem.isIntaking = true;
    SmartDashboard.putBoolean("Intaking Through Shooter", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return intakeSubsystem.beamBroken();
  }
}
