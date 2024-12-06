// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.io.Console;

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

public class ShooterSubsystem extends SubsystemBase {
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

  private double hoodPositionP = 0;
  private double hoodPositionI = 0;
  private double hoodPositionD = 0;

  private double flywheelVeloctiyP = 0.00000001;
  private double flywheelVeloctiyI = 0;
  private double flywheelVeloctiyD = 0;

  public ShooterSubsystem() {
    //Motors
    hoodMotor = new CANSparkMax(3, MotorType.kBrushless);
    flywheelMotor = new CANSparkMax(4, MotorType.kBrushless);

    //Encoders
    hoodEncoder = hoodMotor.getEncoder();
    flywheelEncoder = flywheelMotor.getEncoder();

    //CanSparkMax Setup
    hoodMotor.restoreFactoryDefaults();
    flywheelMotor.restoreFactoryDefaults();

    //Set Motor Idle Mode
    flywheelMotor.setIdleMode(IdleMode.kBrake);

    //Set Motor Inversion
    hoodMotor.setInverted(false);
    flywheelMotor.setInverted(false);

    //PID
    hoodPID = hoodMotor.getPIDController();
    flywheelPID = flywheelMotor.getPIDController();

    hoodPID.setP(hoodPositionP, 0);
    hoodPID.setI(hoodPositionI, 0);
    hoodPID.setD(hoodPositionD, 0);

    flywheelPID.setP(flywheelVeloctiyP, 0);
    flywheelPID.setI(flywheelVeloctiyI, 0);
    flywheelPID.setD(flywheelVeloctiyD, 0);

    //Smart Dashboard
    SmartDashboard.putBoolean("Hood Coast", hoodCoast);

    SmartDashboard.putNumber("Flywheel Velo P", flywheelVeloctiyP);
    SmartDashboard.putNumber("Flywheel Velo I", flywheelVeloctiyI);
    SmartDashboard.putNumber("Flywheel Velo D", flywheelVeloctiyD);

    SmartDashboard.putBoolean("isFlywheelToSpeed", hoodCoast);

    SmartDashboard.putNumber("Refrence", 0);

    //Burn Flash
    hoodMotor.burnFlash();
    flywheelMotor.burnFlash();
  }

  /** DEGREES */
  public void setHoodPostion(double Position) {
    hoodPID.setReference(Units.degreesToRotations(Position * Constants.ShooterSubsystemsConstants.hoodMotorRatio), ControlType.kPosition);
  }

  public void setFlywheelVelocity(double RPM) {
    flywheelPID.setReference(RPM * Constants.ShooterSubsystemsConstants.flywheelMotorRatio, ControlType.kVelocity);
    SmartDashboard.putNumber("Refrence", RPM);
  }

  public boolean isFlywheelToSpeed() {
    return Constants.ShooterSubsystemsConstants.FlywheelMaxSpeed <= flywheelEncoder.getVelocity();
    
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
  }

  private void tempPIDTuning() {
    if (flywheelVeloctiyP != SmartDashboard.getNumber("Flywheel Velo P", flywheelVeloctiyP)) {
        flywheelVeloctiyP = SmartDashboard.getNumber("Flywheel Velo P", flywheelVeloctiyP);
        flywheelPID.setP(flywheelVeloctiyP, 0);
    }

    if (flywheelVeloctiyI != SmartDashboard.getNumber("Flywheel Velo I", flywheelVeloctiyI)) {
        flywheelVeloctiyI = SmartDashboard.getNumber("Flywheel Velo I", flywheelVeloctiyI);
        flywheelPID.setI(flywheelVeloctiyI, 0);
    }

    if (flywheelVeloctiyD != SmartDashboard.getNumber("Flywheel Velo D", flywheelVeloctiyD)) {
        flywheelVeloctiyD = SmartDashboard.getNumber("Flywheel Velo D", flywheelVeloctiyD);
        flywheelPID.setD(flywheelVeloctiyD, 0);
    }
}
}
