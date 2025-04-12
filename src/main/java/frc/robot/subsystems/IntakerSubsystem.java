// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.utilities.math.TempConvert;

public class IntakerSubsystem extends SubsystemBase {
  // private CANSparkMax intakeL;
  // private CANSparkMax intakeR;
  private TalonFX feeder;

  private DigitalInput feederBeamBreak;

  // DO NOT CHANGE THIS, go to OverBumperSubsystem
  private boolean OverBumperEnabled = false; 

  /** Creates a new IntakerSubsystem. */
  public IntakerSubsystem() {
    // intakeL = new CANSparkMax(0, MotorType.kBrushless); // TODO change this
    // intakeR = new CANSparkMax(0, MotorType.kBrushless); // TODO change this
    feeder = new TalonFX(30);

    feederBeamBreak = new DigitalInput(4);

    TalonFXConfiguration config = new TalonFXConfiguration();
    config.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
    config.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);

    // intakeL.setIdleMode(IdleMode.kCoast);
    // intakeR.setIdleMode(IdleMode.kCoast);
    // intakeR.setInverted(true);  // TODO change this to the inverted motor

    // intakeR.follow(intakeL);

    // intakeL.burnFlash();
    // intakeR.burnFlash();
    feeder.getConfigurator().apply(config);
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

  public void setInsideVel(double val) {
    feeder.set(val);
  }

  @Override
  public void periodic() {
    if (SmartDashboard.getBoolean("Over Bumper Intake Enabled", OverBumperEnabled) != OverBumperEnabled) {
      OverBumperEnabled = SmartDashboard.getBoolean("Over Bumper Intake Enabled", OverBumperEnabled);
    }

    SmartDashboard.putBoolean("Inside Beam Break", intakeBreak());

    // SmartDashboard.putNumber("Intake Left Current", intakeL.getOutputCurrent());
    // SmartDashboard.putNumber("Intake Right Current", intakeR.getOutputCurrent());
    SmartDashboard.putNumber("Inside Intake Current", feeder.getStatorCurrent().getValueAsDouble());

    // SmartDashboard.putNumber("Temps/Intake L Temp. (Fahrenheit)", TempConvert.CtoF(intakeL.getMotorTemperature()));
    // SmartDashboard.putNumber("Temps/Intake R Temp. (Fahrenheit)", TempConvert.CtoF(intakeR.getMotorTemperature()));
    SmartDashboard.putNumber("Temps/Feeder Temp. (Fahrenheit)", TempConvert.CtoF(feeder.getDeviceTemp().getValueAsDouble()));
  }
}
