// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private TalonFX feeder;

  private DigitalInput feederBeamBreak;

  public Intake() {
    feeder = new TalonFX(30);
    feederBeamBreak = new DigitalInput(4);

    TalonFXConfiguration config = new TalonFXConfiguration();
    config.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
    config.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);

    feeder.getConfigurator().apply(config);
  }

  public boolean intakeBreak() {
    return !feederBeamBreak.get();
  }

  public void setInsideSpeed(double val) {
    feeder.set(val);
  }

  @Override
  public void periodic() {
    Logger.recordOutput("Intake/BeamBreak", intakeBreak());
  }
}
