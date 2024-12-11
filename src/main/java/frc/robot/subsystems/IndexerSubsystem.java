// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexerSubsystem extends SubsystemBase {
  
  //Motor
  public CANSparkMax indexerMotor;

  public IndexerSubsystem() {
    //Motor
    indexerMotor = new CANSparkMax(Constants.IDs.indexerMotorCanID, MotorType.kBrushless);

    //CanSparkMax Setup
    indexerMotor.restoreFactoryDefaults();

    //Set Motor Idle Mode
    indexerMotor.setIdleMode(IdleMode.kCoast);

    //Set Motor Inversion
    indexerMotor.setInverted(false);

    //Burn Flash
    indexerMotor.burnFlash();
  }

  public void setIndexerPower(double Power) {
    indexerMotor.set(Power);
  }

  @Override
  public void periodic() {
  
  }
}
