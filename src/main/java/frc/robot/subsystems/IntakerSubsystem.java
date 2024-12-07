// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakerSubsystem extends SubsystemBase {
  // private CANSparkMax intakeL;
  // private CANSparkMax intakeR;
  private CANSparkMax feeder;

  private DigitalInput feederBeamBreak;

  private boolean OverBumperEnabled = false; //TODO change this

  /** Creates a new IntakerSubsystem. */
  public IntakerSubsystem() {
    // intakeL = new CANSparkMax(98, MotorType.kBrushless); // TODO change this
    // intakeR = new CANSparkMax(97, MotorType.kBrushless); // TODO change this
    feeder = new CANSparkMax(30, MotorType.kBrushless); // TODO change this

    feederBeamBreak = new DigitalInput(0); // TODO change this

    // intakeL.setIdleMode(IdleMode.kCoast);
    // intakeR.setIdleMode(IdleMode.kCoast);
    feeder.setIdleMode(IdleMode.kCoast);
    // intakeR.setInverted(true);  // TODO change this to the inverted motor

    // intakeR.follow(intakeL);

    // intakeL.burnFlash();
    // intakeR.burnFlash();
    feeder.burnFlash();

    SmartDashboard.putData(this);
  }

  public boolean intakeBreak() {
    return !feederBeamBreak.get();
  }

  public void SetAll() {
    if (OverBumperEnabled) {
      // intakeL.set(Constants.Intaker.IntakeSpeed);
    }
    feeder.set(Constants.Intaker.SuckSpeed);
  }

  public void StopAll() {
    if (OverBumperEnabled) {
      // intakeL.stopMotor();
    }
    feeder.stopMotor();
  }

  public void setInsideVel() {
    feeder.set(Constants.Intaker.SuckSpeed);
  }

  @Override
  public void periodic() {
    OverBumperEnabled = SmartDashboard.getBoolean("Over Bumper Intake Enabled", false);

    SmartDashboard.putBoolean("Inside Beam Break", intakeBreak());

    // SmartDashboard.putNumber("Intake Left Current", intakeL.getOutputCurrent());
    // SmartDashboard.putNumber("Intake Right Current", intakeR.getOutputCurrent());
    SmartDashboard.putNumber("Inside Intake Current", feeder.getOutputCurrent());
  }
}
