// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.io.Console;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends Command {
  
  ShooterSubsystem shooterSubsytem;
  IndexerSubsystem indexerSubsystem;

  public ShootCommand(ShooterSubsystem m_shooterSubsytem, IndexerSubsystem m_indexerSubsystem) {
    shooterSubsytem = m_shooterSubsytem;
    indexerSubsystem = m_indexerSubsystem;
    addRequirements(m_shooterSubsytem, m_indexerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooterSubsytem.setFlywheelVelocity(Constants.ShooterSubsystemsConstants.FlywheelShootSpeed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (shooterSubsytem.isFlywheelToSpeed()) {
      indexerSubsystem.setIndexerPower(Constants.IndexerSubsystemsConstants.IndexerMaxSpeed);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooterSubsytem.setFlywheelVelocity(Constants.ShooterSubsystemsConstants.FlywheelIdleSpeed);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
