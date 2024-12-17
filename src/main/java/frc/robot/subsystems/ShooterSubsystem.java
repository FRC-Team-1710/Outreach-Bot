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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {

  public boolean isIntaking = false;

  //Motors
  private CANSparkMax hoodMotor;
  private CANSparkMax flywheelMotor;

  //Encoder
  private RelativeEncoder hoodEncoder;
  private RelativeEncoder flywheelEncoder;

  //Idle Mode
  private boolean hoodCoast = false; //If true, Coasts, If not, Brakes

  //PID
  private SparkPIDController hoodPID;
  private SparkPIDController flywheelPID;

  private double hoodPositionP = 0.175;
  private double hoodPositionI = 0;
  private double hoodPositionD = 0;

  private double flywheelVeloctiyP = 0;
  private double flywheelVeloctiyV = 0.0002;

  public ShooterSubsystem() {
    //Motors
    hoodMotor = new CANSparkMax(Constants.IDs.hoodMotorCanID, MotorType.kBrushless);
    flywheelMotor = new CANSparkMax(Constants.IDs.flywheelMotorCanID, MotorType.kBrushless);

    //Encoders
    hoodEncoder = hoodMotor.getEncoder();
    flywheelEncoder = flywheelMotor.getEncoder();

    //CanSparkMax Setup
    hoodMotor.restoreFactoryDefaults();
    flywheelMotor.restoreFactoryDefaults();

    //Set Motor Idle Mode
    flywheelMotor.setIdleMode(IdleMode.kCoast);

    //Set Motor Inversion
    hoodMotor.setInverted(true);
    flywheelMotor.setInverted(true);

    //PID
    hoodPID = hoodMotor.getPIDController();
    flywheelPID = flywheelMotor.getPIDController();

    hoodPID.setP(hoodPositionP, 0);
    hoodPID.setI(hoodPositionI, 0);
    hoodPID.setD(hoodPositionD, 0);

    flywheelPID.setP(flywheelVeloctiyP, 0);
    flywheelPID.setFF(flywheelVeloctiyV, 0);

    hoodEncoder.setMeasurementPeriod(16);
    hoodEncoder.setAverageDepth(2);

    flywheelEncoder.setMeasurementPeriod(16);
    flywheelEncoder.setAverageDepth(2);


    //Smart Dashboard
    SmartDashboard.putBoolean("Hood Coast", hoodCoast);

    SmartDashboard.putNumber("Flywheel Velo P", flywheelVeloctiyP);
    SmartDashboard.putNumber("Flywheel Velo V", flywheelVeloctiyV);

    SmartDashboard.putBoolean("isFlywheelToSpeed", hoodCoast);

    SmartDashboard.putNumber("Reference", 0);
    SmartDashboard.putNumber("Reference Hood", 0);

    flywheelMotor.setSmartCurrentLimit(40);
    hoodMotor.setSmartCurrentLimit(40);

    //Burn Flash
    hoodMotor.burnFlash();
    flywheelMotor.burnFlash();

    zeroHood();
  }

  /** Degrees */
  public void setHoodPostion(double Position) {
    hoodPID.setReference(Units.degreesToRotations(Position * Constants.ShooterSubsystemsConstants.hoodMotorRatio), ControlType.kPosition);
    SmartDashboard.putNumber("Reference Hood", Position);
  }

  /** RPM */
  public void setFlywheelVelocity(double RPM) {
    flywheelPID.setReference(RPM * Constants.ShooterSubsystemsConstants.flywheelMotorRatio, ControlType.kVelocity);
    SmartDashboard.putNumber("Reference", RPM);
  }

  public void zeroHood(){
    hoodEncoder.setPosition(0);
  }

  /** Boolean */
  public boolean isFlywheelToSpeed() {
    return Constants.ShooterSubsystemsConstants.flywheelShootPoint <= flywheelEncoder.getVelocity();
    
  }
  /** Degrees */
  public double getHoodPosition() {
    return Units.rotationsToDegrees(hoodEncoder.getPosition() / Constants.ShooterSubsystemsConstants.hoodMotorRatio);
  }

  public void updateIsIntaking(boolean input) {
    isIntaking = input;
  }

  public boolean isIntaking() {
    return isIntaking;
  }

  @Override
  public void periodic() {
    //PID
    tempPIDTuning();

    //Update Idle Mode
    if (SmartDashboard.getBoolean("Hood Coast", hoodCoast) != hoodCoast) { 
      hoodCoast = SmartDashboard.getBoolean("Hood Coast", hoodCoast);

      if (hoodCoast) {
        hoodMotor.setIdleMode(IdleMode.kCoast);
      } else {
        hoodMotor.setIdleMode(IdleMode.kBrake);
      }
    }
    
    //Smart Dashboard
    SmartDashboard.putBoolean("isFlywheelToSpeed", isFlywheelToSpeed());
    SmartDashboard.putNumber("Hood Degrees", getHoodPosition());
  }

  private void tempPIDTuning() {
    if (flywheelVeloctiyP != SmartDashboard.getNumber("Flywheel Velo P", flywheelVeloctiyP)) {
        flywheelVeloctiyP = SmartDashboard.getNumber("Flywheel Velo P", flywheelVeloctiyP);
        flywheelPID.setP(flywheelVeloctiyP, 0);
    }

    if (flywheelVeloctiyV != SmartDashboard.getNumber("Flywheel Velo I", flywheelVeloctiyV)) {
        flywheelVeloctiyV = SmartDashboard.getNumber("Flywheel Velo I", flywheelVeloctiyV);
        flywheelPID.setI(flywheelVeloctiyV, 0);
    }
  }
}
