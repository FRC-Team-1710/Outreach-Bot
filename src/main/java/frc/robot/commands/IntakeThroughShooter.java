// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IntakerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class IntakeThroughShooter extends Command {
  ShooterSubsystem m_ShooterSubsystem;
  IntakerSubsystem m_IntakeSubsystem;
  XboxController controller;
  
  /** Creates a new IntakeThroughShooter. */
  public IntakeThroughShooter(ShooterSubsystem shoot, IntakerSubsystem intake, XboxController cont) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_ShooterSubsystem = shoot;
    m_IntakeSubsystem = intake;
    controller = cont;
    addRequirements(shoot, intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    controller.setRumble(RumbleType.kBothRumble, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_ShooterSubsystem.isItZeroed()) {
      m_ShooterSubsystem.setHoodPosition(Constants.Shooter.Offset);
    }
    if (!m_IntakeSubsystem.intakeBreak()) {
      m_IntakeSubsystem.setInsideVel(Constants.Shooter.feedPower*-1);
      m_ShooterSubsystem.Intake();
    } else {
      m_IntakeSubsystem.StopAll();
      m_ShooterSubsystem.setFlywheelVelocity(Constants.Shooter.idleSpeedRPM);
      controller.setRumble(RumbleType.kBothRumble, Constants.controllerRumble);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_IntakeSubsystem.StopAll();
    m_ShooterSubsystem.setFlywheelVelocity(Constants.Shooter.idleSpeedRPM);
    controller.setRumble(RumbleType.kBothRumble, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
