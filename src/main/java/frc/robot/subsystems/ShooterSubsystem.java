// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// hoodMotor.setSmartCurrentLimit(100); //TODO: Mess with this

package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.utilities.math.TempConvert;
import frc.utilities.util.GetHighest;

public class ShooterSubsystem extends SubsystemBase {
  private CANSparkMax flyWheel;
  private CANSparkMax hoodMotor;

  private RelativeEncoder flyEncoder;
  private RelativeEncoder hoodEncoder;

  private SparkPIDController flyPID;
  private SparkPIDController hoodPID;

  private double positionP = 0.175;
  private double positionI = 0;
  private double positionD = 0;

  private double velocityV = 0.001;
  private double velocityP = 0.0000005;

  private double hoodRatio = Constants.Shooter.extenderRatio;
  /** THIS IS DEGREES */
  private double lastSetpoint = 0;
  private double flySetpoint = 0;

  private boolean HoodCoast = false;
  private boolean isZeroed = false;
  private boolean manualAim = false;

  private boolean ShooterEnabled = true; // TODO change this to pre-enable/disable the subsystem

  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    flyWheel = new CANSparkMax(10, MotorType.kBrushless);
    hoodMotor = new CANSparkMax(31, MotorType.kBrushless);

    flyEncoder = flyWheel.getEncoder();
    hoodEncoder = hoodMotor.getEncoder();

    flyWheel.restoreFactoryDefaults();
    hoodMotor.restoreFactoryDefaults();

    flyPID = flyWheel.getPIDController();
    hoodPID = hoodMotor.getPIDController();

    flyPID.setFF(flySetpoint, 0);
    flyPID.setP(velocityV, 0);

    hoodPID.setP(positionP, 0);
    hoodPID.setI(positionI, 0);
    hoodPID.setD(positionD, 0);

    flyEncoder.setMeasurementPeriod(16);
    hoodEncoder.setMeasurementPeriod(16);

    flyEncoder.setAverageDepth(2);
    hoodEncoder.setAverageDepth(2);

    flyWheel.setIdleMode(IdleMode.kCoast);
    hoodMotor.setIdleMode(IdleMode.kBrake);

    hoodMotor.setInverted(true);
    flyWheel.setInverted(true);

    flyWheel.burnFlash();
    hoodMotor.burnFlash();

    SmartDashboard.putNumber("Hood Pos P", positionP);
    SmartDashboard.putNumber("Hood Pos I", positionI);
    SmartDashboard.putNumber("Hood Pos D", positionD);
    SmartDashboard.putNumber("Flywheel Vel P", velocityP);
    SmartDashboard.putNumber("Flywheel Vel V", velocityV);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Hood Setpoint (Degrees)", lastSetpoint);

    SmartDashboard.putNumber("Flywheel Current", flyWheel.getOutputCurrent());
    SmartDashboard.putNumber("Hood Current", hoodMotor.getOutputCurrent());

    SmartDashboard.putNumber("Temps/Flywheel Temp. (Fahrenheit)", TempConvert.CtoF(flyWheel.getMotorTemperature()));
    SmartDashboard.putNumber("Temps/Hood Temp. (Fahrenheit)", TempConvert.CtoF(hoodMotor.getMotorTemperature()));

    SmartDashboard.putBoolean("Hood Zeroed", isZeroed);

    SmartDashboard.putNumber("Hood Current Position (Degrees)", Units.rotationsToDegrees(hoodEncoder.getPosition() / hoodRatio));
    SmartDashboard.putNumber("Flywheel Current Velocity (RPM)", flyEncoder.getVelocity());

    if (SmartDashboard.getBoolean("Shooter Enabled", ShooterEnabled) != ShooterEnabled) {
      ShooterEnabled = SmartDashboard.getBoolean("Shooter Enabled", ShooterEnabled);
    }

    if (SmartDashboard.getBoolean("Hood Coast", HoodCoast) != HoodCoast) {
      HoodCoast = SmartDashboard.getBoolean("Hood Coast", HoodCoast);
      if (HoodCoast) {
        setHoodToCoast();
      } else {
        setHoodToBrake();
      }
      hoodMotor.burnFlash();
    }

    tempPIDTuning();
    

    if (flyEncoder.getVelocity() >= Constants.Shooter.bufferRPM) {
      SmartDashboard.putBoolean("Is Flywheel Up To Speed", true);
    } else {
      SmartDashboard.putBoolean("Is Flywheel Up To Speed", false);
    }

    if (ShooterEnabled) {
      if (!manualAim && isZeroed) {
        hoodPID.setReference(Units.degreesToRotations(lastSetpoint * hoodRatio), ControlType.kPosition);
      }
      if (flySetpoint != 0) {
        flyPID.setReference(flySetpoint, ControlType.kVelocity);
      } else {
        flyWheel.stopMotor();
      }
    } else {
      hoodMotor.stopMotor();
      flyWheel.stopMotor();
    }
  }

  private void setHoodToBrake() {
    hoodMotor.setIdleMode(IdleMode.kBrake);
  }

  public void Intake() {
    flyPID.setReference(Constants.Shooter.intakeSpeedRPM*-1, ControlType.kVelocity);
  }

  public void StopAll() {
    hoodMotor.stopMotor();
    flyWheel.stopMotor();
  }

  public double getHighestTemp() {
    return TempConvert.CtoF(GetHighest.getHighest(flyWheel.getMotorTemperature(), hoodMotor.getMotorTemperature()));
  }

  public double getAverageTemp() {
    return TempConvert.CtoF((flyWheel.getMotorTemperature() + hoodMotor.getMotorTemperature())/4);
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
    return Units.rotationsToDegrees(hoodEncoder.getPosition() / hoodRatio);
  }

  /** Sets current position to the new 0 degree angle
   * and sets the hodd to the offset
   */
  public void zeroHood(double degreeOffset) {
    if (ShooterEnabled) {
      manualAim = false;
      hoodEncoder.setPosition(0);
      lastSetpoint = degreeOffset;
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
  }

  /** RPM */
  public double getFlywheelVelocity() {
    return flyEncoder.getVelocity();
  }

  private void setHoodToCoast() {
    hoodMotor.setIdleMode(IdleMode.kCoast);
  }

  public boolean IsUpToSpeed() {
    if (flyEncoder.getVelocity() > Constants.Shooter.bufferRPM) {
      return true;
    } else {
      return false;
    }
  }

  

  private void tempPIDTuning() {
    if (positionP != SmartDashboard.getNumber("Hood Pos P", positionP)) {
      positionP = SmartDashboard.getNumber("Hood Pos P", positionP);
      hoodPID.setP(positionP, 0);
    }

    if (positionI != SmartDashboard.getNumber("Hood Pos I", positionI)) {
      positionI = SmartDashboard.getNumber("Hood Pos I", positionI);
      hoodPID.setI(positionI, 0);
    }

    if (positionD != SmartDashboard.getNumber("Hood Pos D", positionD)) {
      positionD = SmartDashboard.getNumber("Hood Pos D", positionD);
      hoodPID.setD(positionD, 0);
    }

    if (velocityP != SmartDashboard.getNumber("Flywheel Vel P", velocityP)) {
      velocityP = SmartDashboard.getNumber("Flywheel Vel P", velocityP);
      flyPID.setP(velocityP, 0);
    }

    if (velocityV != SmartDashboard.getNumber("Flywheel Vel V", velocityV)) {
      velocityV = SmartDashboard.getNumber("Flywheel Vel V", velocityV);
      flyPID.setI(velocityV, 0);
    }
  }
}
