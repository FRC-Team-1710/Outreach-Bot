// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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

public class ShooterSubsystem extends SubsystemBase {
  private CANSparkMax flyWheel;
  private CANSparkMax hoodMotor;

  private RelativeEncoder flyEncoder;
  private RelativeEncoder hoodEncoder;

  private SparkPIDController flyPID;
  private SparkPIDController hoodPID;

  private double positionP = 0; // TODO change this
  private double positionI = 0;
  private double positionD = 0;

  private double velocityP = 0; // TODO change this
  private double velocityI = 0;
  private double velocityD = 0;

  private double velocityP2 = 0; // TODO change this
  private double velocityI2 = 0;
  private double velocityD2 = 0;

  private double hoodRatio = Constants.Shooter.extenderRatio;
  /** THIS IS DEGREES */
  private double lastSetpoint = 0;
  private double flySetpoint = 0;
  private double PIDChange = Constants.Shooter.pidChange;

  private boolean HoodCoast = false;
  private boolean isZeroed = false;
  private boolean manualAim = false;

  private boolean ShooterEnabled = false; // TODO change this to pre-enable/disable the subsystem
  private boolean doublePID = false; // TODO tune this

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

    flyPID.setP(velocityP, 0);
    flyPID.setI(velocityI, 0);
    flyPID.setD(velocityD, 0);

    flyPID.setP(velocityP2, 1);
    flyPID.setI(velocityI2, 1);
    flyPID.setD(velocityD2, 1);

    hoodPID.setP(positionP, 0);
    hoodPID.setI(positionI, 0);
    hoodPID.setD(positionD, 0);

    flyEncoder.setMeasurementPeriod(16);
    hoodEncoder.setMeasurementPeriod(16);

    flyEncoder.setAverageDepth(2);
    hoodEncoder.setAverageDepth(2);

    flyWheel.setIdleMode(IdleMode.kCoast);
    hoodMotor.setIdleMode(IdleMode.kBrake);

    flyWheel.burnFlash();
    hoodMotor.burnFlash();

    SmartDashboard.putNumber("Hood Pos P", positionP);
    SmartDashboard.putNumber("Hood Pos I", positionI);
    SmartDashboard.putNumber("Hood Pos D", positionD);

    SmartDashboard.putNumber("Flywheel Vel P", velocityP);
    SmartDashboard.putNumber("Flywheel Vel I", velocityI);
    SmartDashboard.putNumber("Flywheel Vel D", velocityD);

    SmartDashboard.putNumber("Flywheel Vel P2", velocityP2);
    SmartDashboard.putNumber("Flywheel Vel I2", velocityI2);
    SmartDashboard.putNumber("Flywheel Vel D2", velocityD2);

    SmartDashboard.putBoolean("Hood Coast", HoodCoast);
    SmartDashboard.putBoolean("Shooter Enabled", ShooterEnabled);
    SmartDashboard.putBoolean("Double PID", doublePID);

    // NOTE: Everything past here is only here because
    // Advantage Kit needs it logged before it starts
    SmartDashboard.putBoolean("Is Flywheel Up To Speed", false);
    SmartDashboard.putNumber("Hood Setpoint (Degrees)", lastSetpoint);
    SmartDashboard.putNumber("Flywheel Current", flyWheel.getOutputCurrent());
    SmartDashboard.putNumber("Hood Current", hoodMotor.getOutputCurrent());
    SmartDashboard.putBoolean("Hood Zeroed", isZeroed);
    SmartDashboard.putNumber("Hood Current Position (Degrees)", Units.rotationsToDegrees(hoodEncoder.getPosition() / hoodRatio));
    SmartDashboard.putNumber("Flywheel Current Velocity (RPM)", flyEncoder.getVelocity());
    SmartDashboard.putNumber("Flywheel Velocity Setpoint", 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    tempPIDTuning();

    SmartDashboard.putNumber("Hood Setpoint (Degrees)", lastSetpoint);

    SmartDashboard.putNumber("Flywheel Current", flyWheel.getOutputCurrent());
    SmartDashboard.putNumber("Hood Current", hoodMotor.getOutputCurrent());

    SmartDashboard.putBoolean("Hood Zeroed", isZeroed);

    SmartDashboard.putNumber("Hood Current Position (Degrees)", Units.rotationsToDegrees(hoodEncoder.getPosition() / hoodRatio));
    SmartDashboard.putNumber("Flywheel Current Velocity (RPM)", flyEncoder.getVelocity());

    if (SmartDashboard.getBoolean("Double PID", doublePID) != doublePID) {
      doublePID = SmartDashboard.getBoolean("Double PID", doublePID);
    }

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

    if (flyEncoder.getVelocity() >= Constants.Shooter.bufferRPM) {
      SmartDashboard.putBoolean("Is Flywheel Up To Speed", true);
    } else {
      SmartDashboard.putBoolean("Is Flywheel Up To Speed", false);
    }

    if (ShooterEnabled) {
      if (!manualAim && isZeroed) {
        hoodPID.setReference(Units.degreesToRotations(lastSetpoint * hoodRatio), ControlType.kPosition);
      }
      flyPidLogic();
    } else {
      hoodMotor.stopMotor();
      flyWheel.stopMotor();
    }
  }

  /** Controls PID for the flywheel */
  private void flyPidLogic() {
    if (flyEncoder.getVelocity() >= PIDChange && doublePID) { // If it's faster than the change point, it uses new PID values
      flyPID.setReference(flySetpoint, ControlType.kVelocity, 1);
    } else {
      flyPID.setReference(flySetpoint, ControlType.kVelocity, 0);
    }
  }

  private void setHoodToBrake() {
    hoodMotor.setIdleMode(IdleMode.kBrake);
  }

  public void StopAll() {
    hoodMotor.stopMotor();
    flyWheel.stopMotor();
  }

  /** DEGREES! */
  public void setHoodPosition(double pos) {
    if (ShooterEnabled) {
      manualAim = false;
      lastSetpoint = pos;
    }
  }

  /** Degrees! */
  public double getHoodPosition() {
    return Units.rotationsToDegrees(hoodEncoder.getPosition() / hoodRatio);
  }

  /** Sets current position to the new 0 degree angle, IF YOU DON'T USE DEGREES, DON'T USE THIS */
  public void zeroHood(double degreeOffset) {
    if (ShooterEnabled) {
      manualAim = false;
      hoodEncoder.setPosition(Units.degreesToRotations(degreeOffset * hoodRatio));
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

    if (velocityI != SmartDashboard.getNumber("Flywheel Vel I", velocityI)) {
      velocityI = SmartDashboard.getNumber("Flywheel Vel I", velocityI);
      flyPID.setI(velocityI, 0);
    }

    if (velocityD != SmartDashboard.getNumber("Flywheel Vel D", velocityD)) {
      velocityD = SmartDashboard.getNumber("Flywheel Vel D", velocityD);
      flyPID.setD(velocityD, 0);
    }

    if (velocityP2 != SmartDashboard.getNumber("Flywheel Vel P2", velocityP2)) {
      velocityP2 = SmartDashboard.getNumber("Flywheel Vel P2", velocityP2);
      flyPID.setP(velocityP2, 1);
    }

    if (velocityI2 != SmartDashboard.getNumber("Flywheel Vel I2", velocityI2)) {
      velocityI2 = SmartDashboard.getNumber("Flywheel Vel I2", velocityI2);
      flyPID.setI(velocityI2, 1);
    }

    if (velocityD2 != SmartDashboard.getNumber("Flywheel Vel D2", velocityD2)) {
      velocityD2 = SmartDashboard.getNumber("Flywheel Vel D2", velocityD2);
      flyPID.setD(velocityD2, 1);
    }
  }
}
