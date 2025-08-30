// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.simplySwerve;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import frc.robot.subsystems.simplySwerve.simplyModule.SimplyModuleSpeeds;
import java.util.function.Supplier;
import org.littletonrobotics.junction.Logger;

/** Add your docs here. */
public class SimplyKinematics {
  LinearVelocity speedAt12V = MetersPerSecond.of(5.16);
  AngularVelocity rotationAt12V = RotationsPerSecond.of(2.18);

  Translation2d centerOfRotation = new Translation2d();

  Translation2d[] moduleLocations;
  SimplyModuleSpeeds[] moduleSpeeds;
  SwerveModuleState[] logableStates;
  Supplier<Angle> gyroSupplier;

  public SimplyKinematics(Translation2d[] moduleLocations, Supplier<Angle> gyroSupplier) {
    this.moduleLocations = moduleLocations;
    this.gyroSupplier = gyroSupplier;
    this.moduleSpeeds = new SimplyModuleSpeeds[moduleLocations.length];
    this.logableStates = new SwerveModuleState[moduleLocations.length];
    for (int i = 0; i < moduleLocations.length; i++) {
      moduleSpeeds[i] = new SimplyModuleSpeeds();
      logableStates[i] = new SwerveModuleState(0, Rotation2d.kZero);
    }
  }

  public void setCenterOfRotation(Translation2d centerOfRotation) {
    this.centerOfRotation = centerOfRotation;
  }

  public SimplyModuleSpeeds[] getSpeeds(SimplySwerveRequest request) {
    if (!request.isDriveZero() || !request.isRotationZero()) {
      LinearVelocity maxVelocity = MetersPerSecond.of(0);
      double tempXSpd =
          request.isDriveZero()
              ? 0
              : (request.x > 1 ? 1 : request.x < -1 ? -1 : request.x)
                  * speedAt12V.in(MetersPerSecond);
      double tempYSpd =
          request.isDriveZero()
              ? 0
              : (request.y > 1 ? 1 : request.y < -1 ? -1 : request.y)
                  * speedAt12V.in(MetersPerSecond);
      double rotSpd =
          request.isRotationZero()
              ? 0
              : -(request.rotation > 1 ? 1 : request.rotation < -1 ? -1 : request.rotation)
                  * Units.degreesToRadians(rotationAt12V.in(DegreesPerSecond));
      double xSpd =
          (request.requestType == SimplySwerveRequest.RequestType.FIELD
              ? ((tempXSpd * Math.cos(Units.degreesToRadians(gyroSupplier.get().in(Degrees))))
                  + (tempYSpd * Math.sin(Units.degreesToRadians(gyroSupplier.get().in(Degrees)))))
              : tempXSpd);
      double ySpd =
          (request.requestType == SimplySwerveRequest.RequestType.FIELD
              ? ((-tempXSpd * Math.sin(Units.degreesToRadians(gyroSupplier.get().in(Degrees))))
                  + (tempYSpd * Math.cos(Units.degreesToRadians(gyroSupplier.get().in(Degrees)))))
              : tempYSpd);
      for (int i = 0; i < moduleSpeeds.length; i++) {
        double spdX = xSpd - (rotSpd * moduleLocations[i].minus(centerOfRotation).getY());
        double spdY = ySpd + (rotSpd * moduleLocations[i].minus(centerOfRotation).getX());
        LinearVelocity driveSpeed =
            MetersPerSecond.of(Math.sqrt(Math.pow(spdX, 2) + Math.pow(spdY, 2)));
        Angle turnAngle = Degrees.of(Units.radiansToDegrees(Math.atan2(spdY, spdX)));
        SimplyModuleSpeeds moduleSpeed =
            new SimplyModuleSpeeds().withSteerSetpoint(turnAngle).withDriveSpeed(driveSpeed);
        if (Math.abs(driveSpeed.in(MetersPerSecond)) > maxVelocity.in(MetersPerSecond)) {
          maxVelocity = MetersPerSecond.of(Math.abs(driveSpeed.in(MetersPerSecond)));
        }
        moduleSpeeds[i] = moduleSpeed;
        logableStates[i] =
            new SwerveModuleState(driveSpeed, Rotation2d.fromDegrees(turnAngle.in(Degrees)));
      }
      if (maxVelocity.in(MetersPerSecond) > speedAt12V.in(MetersPerSecond)) {
        double multiplier =
            Math.abs(speedAt12V.in(MetersPerSecond) / maxVelocity.in(MetersPerSecond));
        Logger.recordOutput("MaxVelocity", multiplier);
        for (int i = 0; i < moduleSpeeds.length; i++) {
          moduleSpeeds[i] =
              moduleSpeeds[i].withDriveSpeed(moduleSpeeds[i].getDriveVelocity().times(multiplier));
          logableStates[i] =
              new SwerveModuleState(
                  moduleSpeeds[i].getDriveVelocity(),
                  Rotation2d.fromDegrees(moduleSpeeds[i].getSteerSetpoint().in(Degrees)));
        }
      } else {
        Logger.recordOutput("MaxVelocity", 1);
      }
    } else {
      for (int i = 0; i < moduleLocations.length; i++) {
        moduleSpeeds[i] = moduleSpeeds[i]//.withSteerSetpoint(Degrees.of(0))
                .withDriveSpeed(MetersPerSecond.of(0));
        logableStates[i] =
            new SwerveModuleState(
                0, Rotation2d.fromDegrees(moduleSpeeds[i].getSteerSetpoint().in(Degrees)));
      }
    }
    Logger.recordOutput("RequestedSwerveStates", logableStates);
    return moduleSpeeds;
  }
}
