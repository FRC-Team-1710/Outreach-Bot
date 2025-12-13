// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.simplySwerve;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Mode;
import frc.robot.subsystems.simplySwerve.simplyModule.SimplyModule;
import frc.robot.subsystems.simplySwerve.simplyModule.SimplyModuleState;
import frc.robot.utils.math.CustomQuinticHermiteSpline;

// i, carter eilert, will never write code implementing radians
// from now until the end of time. radians use pi which means
// everything about them is bummy. im not gonna look at a number
// and convert it to degrees using rad180/π=deg. i want to just
// look at a number and know where it is in a unit that is actually
// civilized. if i ever write something that returns radians with
// no other option such as Math.atan2(double, double), i will use
// Units.radiansToDegrees(double) before using it, that is how much
// i dispise radians. thanks for reading my rant :)
public class SimplyEstimator extends SubsystemBase {
  // private int resolution = 10;

  // private final SimplySwerve drive;

  // private Pose2d currentPose = new Pose2d();

  // private final Supplier<Angle> gyroSupplier;

  // private SimplyModule[] modules = new SimplyModule[0];
  // private SimplyModuleState[] previousStates = new SimplyModuleState[0];
  // private SimplyModuleState[] currentStates = new SimplyModuleState[0];
  // private Pose2d[] moduleEstimations = new Pose2d[0];
  // private Translation2d[] moduleLocations = new Translation2d[0];

  // private long delayStart = 0;
  // private long delayEnd = 0;
  // private double deltaTime = 0;

  // private final Timer beginTimer = new Timer();

  // public SimplyEstimator(SimplySwerve drive, Supplier<Angle> gyroSupplier) {
  //   this.drive = drive;
  //   beginTimer.reset();
  //   beginTimer.start();
  //   this.gyroSupplier = gyroSupplier;
  // }

  // public void addModule(SimplyModule module, Translation2d moduleLocation) {
  //   var clone = modules.clone();
  //   var locationsClone = moduleLocations.clone();
  //   modules = new SimplyModule[clone.length + 1];
  //   previousStates = new SimplyModuleState[modules.length + 1];
  //   currentStates = new SimplyModuleState[modules.length + 1];
  //   moduleEstimations = new Pose2d[modules.length + 1];
  //   moduleLocations = new Translation2d[modules.length + 1];
  //   for (int i = 0; i < clone.length; i++) {
  //     modules[i] = clone[i];
  //     moduleLocations[i] = locationsClone[i];
  //     previousStates[i] = new SimplyModuleState();
  //     currentStates[i] = new SimplyModuleState();
  //     moduleEstimations[i] = new Pose2d(0, 0, Rotation2d.kZero);
  //   }
  //   modules[clone.length] = module;
  //   moduleLocations[clone.length] = moduleLocation;
  //   previousStates[clone.length] = new SimplyModuleState();
  //   currentStates[clone.length] = new SimplyModuleState();
  //   moduleEstimations[clone.length] = new Pose2d(0, 0, Rotation2d.kZero);
  // }

  // @Override
  // public void periodic() {
  //   delayStart = RobotController.getFPGATime();
  //   deltaTime = (((double) delayStart - (double) delayEnd)) / 1000000;
  //   for (int i = 0; i < modules.length; i++) {
  //     currentStates[i] =
  //         modules[i]
  //             .getModuleState()
  //             .withRotation(modules[i].getAngle().plus(currentPose.getRotation().getMeasure()));
  //   }
  //   currentPose = drive.getPose();
  //   if (beginTimer.hasElapsed(2)) {
  //     estimate();
  //     drive.setPose(currentPose);
  //     if(!beginTimer.hasElapsed(2.25)) {
  //       drive.setPose(new Pose2d());
  //     }
  //   }
  //   previousStates = currentStates.clone();
  //   delayEnd = RobotController.getFPGATime();
  // }

  // private void estimate() {
  //   for (int i = 0; i < modules.length; i++) {
  //     double p0Steer = previousStates[i].rotation.in(Rotations);
  //     double v0Steer = previousStates[i].rotationVelocity.in(RotationsPerSecond);
  //     double a0Steer = previousStates[i].rotationAcceleration.in(RotationsPerSecondPerSecond);
  //     double pfSteer = currentStates[i].rotation.in(Rotations);
  //     double vfSteer = currentStates[i].rotationVelocity.in(RotationsPerSecond);
  //     double afSteer = currentStates[i].rotationAcceleration.in(RotationsPerSecondPerSecond);

  //     CustomQuinticHermiteSpline steerSpline =
  //         new CustomQuinticHermiteSpline(
  //             p0Steer, v0Steer, a0Steer, pfSteer, vfSteer, afSteer, deltaTime);

  //     double p0Wheel = 0.0;
  //     double v0Wheel = previousStates[i].driveVelocity.in(MetersPerSecond);
  //     double a0Wheel = previousStates[i].driveAcceleration.in(MetersPerSecondPerSecond);
  //     double pfWheel =
  //         currentStates[i].wheelRotation.minus(previousStates[i].wheelRotation).in(Meters);
  //     double vfWheel = currentStates[i].driveVelocity.in(MetersPerSecond);
  //     double afWheel = currentStates[i].driveAcceleration.in(MetersPerSecondPerSecond);

  //     CustomQuinticHermiteSpline driveSpline =
  //         new CustomQuinticHermiteSpline(
  //             p0Wheel, v0Wheel, a0Wheel, pfWheel, vfWheel, afWheel, (deltaTime));

  //     moduleEstimations[i] = getModulePose(i);
  //     for (int v = 1; v <= resolution; v++) {
  //       moduleEstimations[i] =
  //           new Pose2d(
  //                   moduleEstimations[i].getX(),
  //                   moduleEstimations[i].getY(),
  //                   Rotation2d.fromRotations(
  //                       steerSpline.getPositionAtTime(
  //                           deltaTime / resolution * v > deltaTime
  //                               ? deltaTime
  //                               : deltaTime / resolution * v)))
  //               .plus(
  //                   new Transform2d(
  //                       new Translation2d(
  //                           driveSpline.getPositionAtTime(
  //                                   deltaTime / resolution * v > deltaTime
  //                                       ? deltaTime
  //                                       : deltaTime / resolution * v)
  //                               - driveSpline.getPositionAtTime(deltaTime / resolution * (v - 1)),
  //                           Rotation2d.kZero),
  //                       Rotation2d.kZero));
  //     }
  //     Logger.recordOutput("Module" + i + "EstimatedPose", moduleEstimations[i]);
  //   }
  //   double avgX = 0;
  //   double avgY = 0;
  //   double rotation = 0;
  //   for (int i = 0; i < modules.length; i++) {
  //     avgX += moduleEstimations[i].getX();
  //     avgY += moduleEstimations[i].getY();
  //   }
  //   rotation = (Constants.currentMode == Mode.SIM ? 
  //       -Units.radiansToDegrees(Math.atan2(
  //           moduleEstimations[0].getX() - moduleEstimations[(0 + 1) % (modules.length - 1)].getX(),
  //           moduleEstimations[0].getY() - moduleEstimations[(0 + 1) % (modules.length - 1)].getY())) : gyroSupplier.get().in(Degrees));
  //   currentPose =
  //       new Pose2d(
  //           avgX / modules.length,
  //           avgY / modules.length,
  //           Rotation2d.fromDegrees(rotation)
  //               .plus(Rotation2d.fromDegrees(0)));
  // }

  // private Pose2d getModulePose(int num) {
  //   return currentPose.plus(
  //       new Transform2d(new Pose2d(), new Pose2d(moduleLocations[num], Rotation2d.kZero)));
  // }
}
