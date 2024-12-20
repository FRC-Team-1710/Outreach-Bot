// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IndexerSubsystem;

public class OuttakeCommand extends Command {

  IndexerSubsystem indexerSubsystem;

  public OuttakeCommand(IndexerSubsystem m_IndexerSubsystem) {
    indexerSubsystem = m_IndexerSubsystem;
    addRequirements(m_IndexerSubsystem,indexerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    indexerSubsystem.setIndexerPower(Constants.IndexerSubsystemsConstants.outtakePower);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    indexerSubsystem.setIndexerPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
