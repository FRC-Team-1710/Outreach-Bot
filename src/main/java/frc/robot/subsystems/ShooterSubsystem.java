// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you  modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.utilities.math.TempConvert;

public class ShooterSubsystem extends SubsystemBase {
  private TalonFX flyWheel;
  private TalonFX hoodMotor;

  private PositionVoltage hoodRequest;
  private ProfiledPIDController flyController;

  private double positionP = 2;
  private double positionI = 0;
  private double positionD = 0;

  private double velocityP = 0.05;
  private double velocityAcel = 10000;
  private double velocityVel = 2500;

  private double hoodRatio = Constants.Shooter.extenderRatio;
  /** THIS IS DEGREES */
  private double lastSetpoint = 0;
  private double flySetpoint = 0;

  // private boolean HoodCoast = false;
  private boolean isZeroed = true;
  private boolean manualAim = false;

  private boolean ShooterEnabled = true; // Change this to pre-enable/disable the subsystem

  TalonFXConfiguration positionConfig = new TalonFXConfiguration();
  TalonFXConfiguration velocityConfig = new TalonFXConfiguration();

  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    flyWheel = new TalonFX(31);
    hoodMotor = new TalonFX(10);

    velocityConfig.CurrentLimits.withStatorCurrentLimit(20);
    velocityConfig.CurrentLimits.StatorCurrentLimitEnable = true;
    velocityConfig.MotorOutput.withNeutralMode(NeutralModeValue.Coast);
    velocityConfig.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);

    flyWheel.getConfigurator().apply(velocityConfig);

    positionConfig.Slot0.withKP(positionP);
    positionConfig.Slot0.withKI(positionI);
    positionConfig.Slot0.withKD(positionD);
    positionConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
    positionConfig.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);

    hoodMotor.getConfigurator().apply(positionConfig);

    flyWheel.setPosition(0);
    hoodMotor.setPosition(0);

    hoodRequest = new PositionVoltage(0);
    flyController = new ProfiledPIDController(velocityP, 0, 0, new Constraints(velocityVel, velocityAcel));

    SmartDashboard.putNumber("Hood Pos P", positionP);
    SmartDashboard.putNumber("Hood Pos I", positionI);
    SmartDashboard.putNumber("Hood Pos D", positionD);
    SmartDashboard.putNumber("Flywheel Vel P", velocityP);
    SmartDashboard.putNumber("Flywheel Vel Acel", velocityAcel);
    SmartDashboard.putNumber("Flywheel Vel Vel", velocityVel);
    SmartDashboard.putBoolean("Slow Flywheel", true);
    SmartDashboard.putNumber("Flywheel speed (RPM)", 2000);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Hood Setpoint (Degrees)", lastSetpoint);
    SmartDashboard.putNumber("Fly Setpoint (RPM)", flySetpoint);

    SmartDashboard.putNumber("Flywheel Current", flyWheel.getStatorCurrent().getValueAsDouble());
    // SmartDashboard.putNumber("Hood Current", hoodMotor.getOutputCurrent());

    // SmartDashboard.putNumber("Temps/Flywheel Temp. (Fahrenheit)", TempConvert.CtoF(flyWheel.getMotorTemperature()));
    // SmartDashboard.putNumber("Temps/Hood Temp. (Fahrenheit)", TempConvert.CtoF(hoodMotor.getMotorTemperature()));

    SmartDashboard.putBoolean("Hood Zeroed", isZeroed);

    SmartDashboard.putNumber("Hood Current Position (Degrees)", Units.rotationsToDegrees(hoodMotor.getPosition().getValueAsDouble() / hoodRatio));
    SmartDashboard.putNumber("Flywheel Current Velocity (RPM)", flyWheel.getVelocity().getValueAsDouble()*60);

    if (SmartDashboard.getBoolean("Shooter Enabled", ShooterEnabled) != ShooterEnabled) {
      ShooterEnabled = SmartDashboard.getBoolean("Shooter Enabled", ShooterEnabled);
    }

    // if (SmartDashboard.getBoolean("Hood Coast", HoodCoast) != HoodCoast) {
    //   HoodCoast = SmartDashboard.getBoolean("Hood Coast", HoodCoast);
    //   if (HoodCoast) {
    //     setHoodToCoast();
    //   } else {
    //     setHoodToBrake();
    //   }
    //   hoodMotor.burnFlash();
    // }

    tempPIDTuning();
    

    if (IsUpToSpeed()) {
      SmartDashboard.putBoolean("Is Flywheel Up To Speed", true);
    } else {
      SmartDashboard.putBoolean("Is Flywheel Up To Speed", false);
    }

    // SmartDashboard.putBoolean("Is At Angle", isAtAngle());

    // if (ShooterEnabled) {
    //   if (!manualAim && isZeroed) {
    //     hoodPID.setReference(Units.degreesToRotations(lastSetpoint * hoodRatio), ControlType.kPosition);
    //   }
    SmartDashboard.putNumber("Flywheel literal Setpoint", flyController.getSetpoint().position);

      if (flySetpoint != 0) {
        flyWheel.setVoltage(flyController.calculate(flyWheel.getVelocity().getValueAsDouble()*60));
      } else {
        flyController.reset(flyWheel.getVelocity().getValueAsDouble()*60);
        flyWheel.stopMotor();
      }
    // } else {
    //   hoodMotor.stopMotor();
    //   flyWheel.stopMotor();
    // }
  }

  public boolean isAtAngle() {
    return Degrees.of(Units.rotationsToDegrees(hoodMotor.getPosition().getValueAsDouble() / hoodRatio)).isNear(Degrees.of(lastSetpoint), Degrees.of(2));
  }

  public void Intake() {
    flySetpoint = Constants.Shooter.intakeSpeedRPM*-1;
  }

  public void StopAll() {
    hoodMotor.stopMotor();
    flyWheel.stopMotor();
  }

  /** DEGREES */
  public void setHoodPosition(double pos) {
    if (ShooterEnabled) {
      manualAim = false;
      lastSetpoint = pos;
      hoodMotor.setControl(hoodRequest.withPosition((pos*hoodRatio)/360));
    }
  }

  /** Degrees */
  public double getHoodPosition() {
    return Units.rotationsToDegrees(hoodMotor.getPosition().getValueAsDouble() / hoodRatio);
  }

  /** Sets current position to the new 0 degree angle
   * and sets the hodd to the offset
   */
  public void zeroHood(double degreeOffset) {
    if (ShooterEnabled) {
      manualAim = false;
      hoodMotor.setPosition(0);
      lastSetpoint = degreeOffset;
      hoodMotor.setControl(hoodRequest.withPosition((degreeOffset*hoodRatio)/360));
      isZeroed = true;
    }
  }

  public void setHoodManual(double speed) {
    if (ShooterEnabled) {
      manualAim = true;
      hoodMotor.set(speed);
    }
  }

  public boolean isItZeroed() {
    return isZeroed;
  }

  /** RPM */
  public void setFlywheelVelocity(double vel) {
    SmartDashboard.putNumber("Flywheel Velocity Setpoint", vel);
    flySetpoint = vel;
    flyController.setGoal(vel);
  }

  /** RPM */
  public double getFlywheelVelocity() {
    return flyWheel.getVelocity().getValueAsDouble();
  }

  public boolean IsUpToSpeed() {
    if (flyWheel.getVelocity().getValueAsDouble()*60 > flySetpoint - Constants.Shooter.bufferRPM && flySetpoint > 0) {
      return true;
    } else {
      return false;
    }
  }

  

  private void tempPIDTuning() {
    if (positionP != SmartDashboard.getNumber("Hood Pos P", positionP)) {
      positionP = SmartDashboard.getNumber("Hood Pos P", positionP);
      positionConfig.Slot0.withKP(positionP);
      hoodMotor.getConfigurator().apply(positionConfig);
    }

    if (positionI != SmartDashboard.getNumber("Hood Pos I", positionI)) {
      positionI = SmartDashboard.getNumber("Hood Pos I", positionI);
      positionConfig.Slot0.withKI(positionI);
      hoodMotor.getConfigurator().apply(positionConfig);
    }

    if (positionD != SmartDashboard.getNumber("Hood Pos D", positionD)) {
      positionD = SmartDashboard.getNumber("Hood Pos D", positionD);
      positionConfig.Slot0.withKD(positionD);
      hoodMotor.getConfigurator().apply(positionConfig);
    }

    if (velocityP != SmartDashboard.getNumber("Flywheel Vel P", velocityP)) {
      velocityP = SmartDashboard.getNumber("Flywheel Vel P", velocityP);
      flyController.setP(velocityP);
    }

    if (velocityAcel != SmartDashboard.getNumber("Flywheel Vel Acel", 0)) {
      velocityAcel = SmartDashboard.getNumber("Flywheel Vel Acel", 0);
      flyController.setConstraints(new Constraints(velocityVel, velocityAcel));
    }

    if (velocityVel != SmartDashboard.getNumber("Flywheel Vel Vel", 0)) {
      velocityVel = SmartDashboard.getNumber("Flywheel Vel Vel", 0);
      flyController.setConstraints(new Constraints(velocityVel, velocityAcel));
    }

    if (Constants.Shooter.fastShootSpeedRPM != SmartDashboard.getNumber("Flywheel speed (RPM)", 2000)) {
      Constants.Shooter.fastShootSpeedRPM = SmartDashboard.getNumber("Flywheel speed (RPM)", 2000);
    }
  }
}
