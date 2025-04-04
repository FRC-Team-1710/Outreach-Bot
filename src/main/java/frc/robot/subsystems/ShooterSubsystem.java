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

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.utilities.math.TempConvert;

public class ShooterSubsystem extends SubsystemBase {
  // private TalonFX flyWheel;
  // private TalonFX hoodMotor;

  private MotionMagicVelocityVoltage flywheelRequest;
  private PositionVoltage hoodRequest;

  private double positionP = 0;
  private double positionI = 0;
  private double positionD = 0;

  private double velocityV = 0;
  private double velocityP = 0;

  private double hoodRatio = Constants.Shooter.extenderRatio;
  /** THIS IS DEGREES */
  private double lastSetpoint = 0;
  private double flySetpoint = 0;

  // private boolean HoodCoast = false;
  private boolean isZeroed = false;
  private boolean manualAim = false;

  private boolean ShooterEnabled = true; // Change this to pre-enable/disable the subsystem

  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    // flyWheel = new TalonFX(31);
    // hoodMotor = new TalonFX(10);

    TalonFXConfiguration config = new TalonFXConfiguration();
    config.Slot0.withKV(velocityV);
    config.Slot0.withKP(velocityP);
    config.MotorOutput.withNeutralMode(NeutralModeValue.Coast);
    config.MotorOutput.withInverted(InvertedValue.CounterClockwise_Positive);
    // config.sta
    // flyWheel.configure(config, null, null);

    // config = new SparkMaxConfig();
    // config.closedLoop.p(positionP);
    // config.closedLoop.i(positionI);
    // config.closedLoop.d(positionD);
    // config.encoder.uvwMeasurementPeriod(16);
    // config.encoder.uvwAverageDepth(2);
    // config.idleMode(IdleMode.kBrake);
    // config.inverted(true);
    // hoodMotor.configure(config, null, null);

    // flyPID = flyWheel.getClosedLoopController();
    // hoodPID = hoodMotor.getClosedLoopController();

    SmartDashboard.putNumber("Hood Pos P", positionP);
    SmartDashboard.putNumber("Hood Pos I", positionI);
    SmartDashboard.putNumber("Hood Pos D", positionD);
    SmartDashboard.putNumber("Flywheel Vel P", velocityP);
    SmartDashboard.putNumber("Flywheel Vel V", velocityV);
    SmartDashboard.putBoolean("Slow Flywheel", true);
    SmartDashboard.putNumber("Flywheel speed (RPM)", 2000);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Hood Setpoint (Degrees)", lastSetpoint);
    SmartDashboard.putNumber("Fly Setpoint (RPM)", flySetpoint);

    // SmartDashboard.putNumber("Flywheel Current", flyWheel.getOutputCurrent());
    // SmartDashboard.putNumber("Hood Current", hoodMotor.getOutputCurrent());

    // SmartDashboard.putNumber("Temps/Flywheel Temp. (Fahrenheit)", TempConvert.CtoF(flyWheel.getMotorTemperature()));
    // SmartDashboard.putNumber("Temps/Hood Temp. (Fahrenheit)", TempConvert.CtoF(hoodMotor.getMotorTemperature()));

    SmartDashboard.putBoolean("Hood Zeroed", isZeroed);

    // SmartDashboard.putNumber("Hood Current Position (Degrees)", Units.rotationsToDegrees(hoodEncoder.getPosition() / hoodRatio));
    // SmartDashboard.putNumber("Flywheel Current Velocity (RPM)", flyEncoder.getVelocity());

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

    // tempPIDTuning();
    

    // if (flyEncoder.getVelocity() > flySetpoint - Constants.Shooter.bufferRPM && flySetpoint > 0) {
    //   SmartDashboard.putBoolean("Is Flywheel Up To Speed", true);
    // } else {
    //   SmartDashboard.putBoolean("Is Flywheel Up To Speed", false);
    // }

    // SmartDashboard.putBoolean("Is At Angle", isAtAngle());

    // if (ShooterEnabled) {
    //   if (!manualAim && isZeroed) {
    //     hoodPID.setReference(Units.degreesToRotations(lastSetpoint * hoodRatio), ControlType.kPosition);
    //   }
    //   if (flySetpoint != 0) {
    //     flyPID.setReference(flySetpoint, ControlType.kVelocity);
    //   } else {
    //     flyWheel.stopMotor();
    //   }
    // } else {
    //   hoodMotor.stopMotor();
    //   flyWheel.stopMotor();
    // }
  }

  public boolean isAtAngle() {
    return false;//Degrees.of(Units.rotationsToDegrees(hoodEncoder.getPosition() / hoodRatio)).isNear(Degrees.of(lastSetpoint), Degrees.of(2));
  }

  // private void setHoodToBrake() {
  //   hoodMotor.setIdleMode(IdleMode.kBrake);
  // }

  public void Intake() {
    flySetpoint = Constants.Shooter.intakeSpeedRPM*-1;
  }

  public void StopAll() {
    // hoodMotor.stopMotor();
    // flyWheel.stopMotor();
  }

  /** DEGREES */
  public void setHoodPosition(double pos) {
    if (ShooterEnabled) {
      manualAim = false;
      lastSetpoint = pos;
    }
  }

  /** Degrees */
  public double getHoodPosition() {
    return 0;//Units.rotationsToDegrees(hoodEncoder.getPosition() / hoodRatio);
  }

  /** Sets current position to the new 0 degree angle
   * and sets the hodd to the offset
   */
  public void zeroHood(double degreeOffset) {
    if (ShooterEnabled) {
      manualAim = false;
      //hoodEncoder.setPosition(0);
      lastSetpoint = degreeOffset;
      isZeroed = true;
    }
  }

  public void setHoodManual(double speed) {
    if (ShooterEnabled) {
      manualAim = true;
      // hoodMotor.set(speed);
    }
  }

  public boolean isItZeroed() {
    return isZeroed;
  }

  /** RPM */
  public void setFlywheelVelocity(double vel) {
    SmartDashboard.putNumber("Flywheel Velocity Setpoint", vel);
    flySetpoint = vel;
  }

  /** RPM */
  public double getFlywheelVelocity() {
    return 0;//flyEncoder.getVelocity();
  }

  // private void setHoodToCoast() {
  //   hoodMotor.setIdleMode(IdleMode.kCoast);
  // }

  public boolean IsUpToSpeed() {
    // if (flyEncoder.getVelocity() > flySetpoint - Constants.Shooter.bufferRPM && flySetpoint > 0) {
    //   return true;
    // } else {
      return false;
    // }
  }

  

  // private void tempPIDTuning() {
  //   if (positionP != SmartDashboard.getNumber("Hood Pos P", positionP)) {
  //     positionP = SmartDashboard.getNumber("Hood Pos P", positionP);
  //     hoodPID.setP(positionP, 0);
  //   }

  //   if (positionI != SmartDashboard.getNumber("Hood Pos I", positionI)) {
  //     positionI = SmartDashboard.getNumber("Hood Pos I", positionI);
  //     hoodPID.setI(positionI, 0);
  //   }

  //   if (positionD != SmartDashboard.getNumber("Hood Pos D", positionD)) {
  //     positionD = SmartDashboard.getNumber("Hood Pos D", positionD);
  //     hoodPID.setD(positionD, 0);
  //   }

  //   if (velocityP != SmartDashboard.getNumber("Flywheel Vel P", velocityP)) {
  //     velocityP = SmartDashboard.getNumber("Flywheel Vel P", velocityP);
  //     flyPID.setP(velocityP, 0);
  //   }

  //   if (velocityV != SmartDashboard.getNumber("Flywheel Vel V", velocityV)) {
  //     velocityV = SmartDashboard.getNumber("Flywheel Vel V", velocityV);
  //     flyPID.setFF(velocityV, 0);
  //   }

  //   if (Constants.Shooter.fastShootSpeedRPM != SmartDashboard.getNumber("Flywheel speed (RPM)", 2000)) {
  //     Constants.Shooter.fastShootSpeedRPM = SmartDashboard.getNumber("Flywheel speed (RPM)", 2000);
  //   }
  // }
}
