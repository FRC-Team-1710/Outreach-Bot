// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.simplySwerve.simplyModule;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearAcceleration;
import edu.wpi.first.units.measure.LinearVelocity;

public class SimplyModuleState {
  public Distance wheelRotation = Meters.of(0);
  public LinearVelocity driveVelocity = MetersPerSecond.of(0);
  public LinearAcceleration driveAcceleration = MetersPerSecondPerSecond.of(0);

  public Angle rotation = Degrees.of(0);
  public AngularVelocity rotationVelocity = DegreesPerSecond.of(0);
  public AngularAcceleration rotationAcceleration = DegreesPerSecondPerSecond.of(0);

  public SimplyModuleState() {}

  public SimplyModuleState withWheelRotation(Distance wheelRotation) {
    this.wheelRotation = wheelRotation;
    return this;
  }

  public SimplyModuleState withDriveVelocity(LinearVelocity driveVelocity) {
    this.driveVelocity = driveVelocity;
    return this;
  }

  public SimplyModuleState withDriveAcceleration(LinearAcceleration driveAcceleration) {
    this.driveAcceleration = driveAcceleration;
    return this;
  }

  public SimplyModuleState withRotation(Angle rotation) {
    this.rotation = rotation;
    return this;
  }

  public SimplyModuleState withRotationVelocity(AngularVelocity rotationVelocity) {
    this.rotationVelocity = rotationVelocity;
    return this;
  }

  public SimplyModuleState withRotationAcceleration(AngularAcceleration rotationAcceleration) {
    this.rotationAcceleration = rotationAcceleration;
    return this;
  }
}
