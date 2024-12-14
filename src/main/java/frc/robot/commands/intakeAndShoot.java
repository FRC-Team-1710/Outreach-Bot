// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IntakerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class intakeAndShoot extends Command {
  IntakerSubsystem m_intakeSubsystem;
  ShooterSubsystem m_shooterSubsystem;
  private double state;
  public final Timer timer = new Timer();

  /** Creates a new intakeAndShoot. */
  public intakeAndShoot(IntakerSubsystem intake, ShooterSubsystem shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intakeSubsystem = intake;
    m_shooterSubsystem = shooter;
    state = 1;
    addRequirements(intake, shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (state == 1) {
      m_intakeSubsystem.SetAll();
      m_shooterSubsystem.setFlywheelVelocity(0);
      if (m_shooterSubsystem.isItZeroed()) {
        m_shooterSubsystem.setHoodPosition(Constants.Shooter.Offset);
      }
      if (m_intakeSubsystem.intakeBreak()) {
        state = 2;
      }
    } else if (state == 2) {
      m_intakeSubsystem.StopAll();
      if (SmartDashboard.getBoolean("Slow Flywheel", true)) {
        m_shooterSubsystem.setFlywheelVelocity(Constants.Shooter.shootSpeedRPM);
      } else {
        m_shooterSubsystem.setFlywheelVelocity(Constants.Shooter.fastShootSpeedRPM);
      }
      if (m_shooterSubsystem.isItZeroed()) {
        m_shooterSubsystem.setHoodPosition(Constants.Shooter.Shootangle);
      }
      if (m_shooterSubsystem.IsUpToSpeed()) {
        state = 3;
      }
    } else if (state == 3) {
      m_intakeSubsystem.SetAll();
      if (SmartDashboard.getBoolean("Slow Flywheel", true)) {
        m_shooterSubsystem.setFlywheelVelocity(Constants.Shooter.shootSpeedRPM);
      } else {
        m_shooterSubsystem.setFlywheelVelocity(Constants.Shooter.fastShootSpeedRPM);
      }
      if (m_shooterSubsystem.isItZeroed()) {
        m_shooterSubsystem.setHoodPosition(Constants.Shooter.Shootangle);
      }
      if (!m_intakeSubsystem.intakeBreak()) {
        state = 4;
        timer.reset();
        timer.start();
      }
    } else if (state == 4) {
      if (timer.get() >= 0.2) {
        state = 1;
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intakeSubsystem.StopAll();
    m_shooterSubsystem.setFlywheelVelocity(0);
    m_shooterSubsystem.setHoodPosition(Constants.Shooter.Offset);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
