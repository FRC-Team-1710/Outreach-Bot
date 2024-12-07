// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class OverBumperSubsystem extends SubsystemBase {
  // private CANSparkMax armLeft;
  // private CANSparkMax armRight;

  // private RelativeEncoder encoderL;

  // private SparkPIDController armPID;

  private double positionP = 0; // TODO change this
  private double positionI = 0;
  private double positionD = 0;

  // private double gearRatio = Constants.Arm.ratio;

  // private boolean armCoast = false;
  // private boolean isArmUp = false;
  // private boolean zeroed = false;
  private boolean OverBumperEnabled = false; // TODO change this to pre-enable/disable the subsystem

  /** Creates a new OverBumperSubsystem. */
  public OverBumperSubsystem() {
    // armLeft = new CANSparkMax(0, MotorType.kBrushless); // TODO change this
    // armRight = new CANSparkMax(0, MotorType.kBrushless); // TODO change this

    // encoderL = armLeft.getEncoder();

    // armLeft.restoreFactoryDefaults();
    // armRight.restoreFactoryDefaults();

    // armPID = armLeft.getPIDController();

    // armPID.setP(positionP, 0);
    // armPID.setI(positionI, 0);
    // armPID.setD(positionD, 0);

    // encoderL.setMeasurementPeriod(16);

    // encoderL.setAverageDepth(2);

    // armLeft.setIdleMode(IdleMode.kBrake);
    // armRight.setIdleMode(IdleMode.kBrake);

    // armRight.follow(armLeft);

    // armLeft.burnFlash();
    // armRight.burnFlash();

    SmartDashboard.putNumber("Over Pos P", positionP);
    SmartDashboard.putNumber("Over Pos I", positionI);
    SmartDashboard.putNumber("Over Pos D", positionD);

    SmartDashboard.putBoolean("Over Bumper Coast", false);
    SmartDashboard.putBoolean("Over Bumper Intake Enabled", OverBumperEnabled);
    // NOTE: Do not change this value to enable/disable the over bumper subsystem,
    // Change the boolean named OverBumperEnabled

    // NOTE: Everything past here is only here because
    // Advantage Kit needs it logged before it starts
    SmartDashboard.putBoolean("Arm up", false);
    SmartDashboard.putBoolean("Over Bumper Zeroed", false);
    SmartDashboard.putNumber("Arm Left Current", 0);
    SmartDashboard.putNumber("Arm Right Current", 0);
    SmartDashboard.putNumber("Over Bumper Current Position (Degrees)", 0);
  }

  public void StopAll() {
    // armLeft.stopMotor();
    // armRight.stopMotor;
  }

  /** THE OFFSET IS IN DEGREES */
  public void setZero(double offset) {
    //if (OverBumperEnabled) { // If it's enabled
      // encoderL.setPosition(Units.degreesToRotations(offset));
      // armPID.setReference(Units.degreesToRotations(offset), CANSparkMax.ControlType.kPosition);
      // zeroed = true;
      // isArmUp = false;
    //}
  }

  public void setArmToBrake() {
    // armLeft.setIdleMode(IdleMode.kBrake);
    // armRight.setIdleMode(IdleMode.kBrake);
  }

  public void setArmToCoast() {
    // armLeft.setIdleMode(IdleMode.kCoast);
    // armRight.setIdleMode(IdleMode.kCoast);
  }

  @Override
  public void periodic() {
    // // This method will be called once per scheduler run
    // tempPIDTuning();

    // SmartDashboard.putBoolean("Arm up", isArmUp);

    // SmartDashboard.putBoolean("Over Bumper Zeroed", zeroed);

    // SmartDashboard.putNumber("Arm Left Current", armLeft.getOutputCurrent());
    // SmartDashboard.putNumber("Arm Right Current", armRight.getOutputCurrent());

    // SmartDashboard.putNumber("Over Bumper Current Position (Degrees)", Units.rotationsToDegrees(encoderL.getPosition()/gearRatio));

    // if (SmartDashboard.getBoolean("Over Bumper Intake Enabled", OverBumperEnabled) != OverBumperEnabled) {
    //   OverBumperEnabled = SmartDashboard.getBoolean("Over Bumper Intake Enabled", OverBumperEnabled);
    //   armLeft.stopMotor();
    //   armRight.stopMotor();
    // }

    // if (SmartDashboard.getBoolean("Over Bumper Coast", armCoast) != armCoast) {
    //   armCoast = SmartDashboard.getBoolean("Over Bumper Coast", armCoast);
    //   if (armCoast) {
    //       setArmToCoast();
    //   } else {
    //       setArmToBrake();
    //   }
    //   armLeft.burnFlash();
    //   armRight.burnFlash();
    // }
  }

  /** DEGREES */
  public void setUporDown(boolean up, double distance) {
    // if (OverBumperEnabled && zeroed) { //If the subsystem is enabled and it has been zeroed
    //   armPID.setReference(Units.degreesToRotations(distance*gearRatio), CANSparkMax.ControlType.kPosition);
    //   isArmUp = up;
    // }
  }

  private void tempPIDTuning() {
    // if (positionP != SmartDashboard.getNumber("Over Pos P", positionP)) {
    //     positionP = SmartDashboard.getNumber("Over Pos P", positionP);
    //     armPID.setP(positionP, 0);
    // }

    // if (positionI != SmartDashboard.getNumber("Over Pos I", positionI)) {
    //     positionI = SmartDashboard.getNumber("Over Pos I", positionI);
    //     armPID.setI(positionI, 0);
    // }

    // if (positionD != SmartDashboard.getNumber("Over Pos D", positionD)) {
    //     positionD = SmartDashboard.getNumber("Over Pos D", positionD);
    //     armPID.setD(positionD, 0);
    // }
  }
}
