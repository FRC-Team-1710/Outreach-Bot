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

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  //Motors
  private CANSparkMax leftIntakeMotor;
  private CANSparkMax rightIntakeMotor;
  private CANSparkMax leftIntakeArmMotor;
  private CANSparkMax rightIntakeArmMotor;

  //Encoders
  private RelativeEncoder leftIntakeEncoder;
  private RelativeEncoder rightIntakeEncoder;
  private RelativeEncoder leftIntakeArmEncoder;
  private RelativeEncoder rightIntakeArmEncoder;

  //Breaking Beam
  private DigitalInput breakingBeam;

  //PID
  private SparkPIDController rightIntakeArmPID;
  private SparkPIDController leftIntakeArmPID;

  private double rightIntakeArmPositionP = 0;
  private double rightIntakeArmPositionI = 0;
  private double rightIntakeArmPositionD = 0;

  private double leftIntakeArmPositionP = 0;
  private double leftIntakeArmPositionI = 0;
  private double leftIntakeArmPositionD = 0;

  public IntakeSubsystem() {
    /*
    //Motors
    leftIntakeMotor = new CANSparkMax(Constants.IDs.leftIntakeMotorCanID, MotorType.kBrushless);
    rightIntakeMotor = new CANSparkMax(Constants.IDs.rightIntakeMotorCanID, MotorType.kBrushless);
    leftIntakeArmMotor = new CANSparkMax(Constants.IDs.leftIntakeArmMotorCanID, MotorType.kBrushless);
    rightIntakeArmMotor = new CANSparkMax(Constants.IDs.rightIntakeArmMotorCanID, MotorType.kBrushless);

    //Encoders
    leftIntakeEncoder = leftIntakeMotor.getEncoder();
    rightIntakeEncoder = rightIntakeMotor.getEncoder();
    leftIntakeArmEncoder = leftIntakeArmMotor.getEncoder();
    rightIntakeArmEncoder = rightIntakeArmMotor.getEncoder(); */

    //Breaking Beam
    breakingBeam = new DigitalInput(Constants.IDs.breakingBeamDigitalInput);

    /*
    //CANSparkMax Setup
    leftIntakeMotor.restoreFactoryDefaults();
    rightIntakeMotor.restoreFactoryDefaults();
    leftIntakeArmMotor.restoreFactoryDefaults();
    rightIntakeArmMotor.restoreFactoryDefaults();

    //Set Motor Idle Mode
    leftIntakeMotor.setIdleMode(IdleMode.kCoast);
    rightIntakeMotor.setIdleMode(IdleMode.kCoast);
    leftIntakeArmMotor.setIdleMode(IdleMode.kBrake);
    rightIntakeArmMotor.setIdleMode(IdleMode.kBrake);

    //Set Motor Inversion
    leftIntakeMotor.setInverted(false);
    rightIntakeMotor.setInverted(true);
    leftIntakeArmMotor.setInverted(false);
    leftIntakeArmMotor.setInverted(true);

    //Burn Flash
    leftIntakeMotor.burnFlash();
    rightIntakeMotor.burnFlash();
    leftIntakeArmMotor.burnFlash();
    rightIntakeArmMotor.burnFlash(); */
  }

  /** Power 
  public void setIntakePower(double Power) {
    leftIntakeMotor.set(Power);
    rightIntakeMotor.set(Power);
  } 
  */

  /** Position 
  public void setIntakeArmPosition(double Position) {
    leftIntakeArmPID.setReference(Units.degreesToRotations(Position * Constants.IntakeSubsystemConstants.intakeArmMotorRatio), ControlType.kPosition);
    rightIntakeArmPID.setReference(Units.degreesToRotations(Position * Constants.IntakeSubsystemConstants.intakeArmMotorRatio), ControlType.kPosition);
  } */

  /** Boolean */
  public boolean beamBroken() {
    return !breakingBeam.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
