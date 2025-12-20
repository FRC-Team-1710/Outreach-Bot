// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Milliseconds;

import java.util.HashMap;

import edu.wpi.first.math.Pair;
import edu.wpi.first.units.measure.Time;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeIO;
import frc.robot.subsystems.intake.IntakeIOCTRE;
import frc.robot.subsystems.intake.IntakeIOSIM;
import frc.robot.Constants.Subsystems;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Superstructure;
import frc.robot.subsystems.Superstructure.WantedState;
import frc.robot.subsystems.hood.Hood;
import frc.robot.subsystems.hood.HoodIO;
import frc.robot.subsystems.hood.HoodIOCTRE;
import frc.robot.subsystems.hood.HoodIOSIM;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.ShooterIO;
import frc.robot.subsystems.shooter.ShooterIOCTRE;
import frc.robot.subsystems.shooter.ShooterIOSIM;
import frc.robot.utils.TunableController;

public class RobotContainer {
        // private final SimplySwerve drive;
        // private final SimplyEstimator estimator;

        private final DriveSubsystem drive;
        private final Intake intake;
        private final Shooter shooter;
        private final Hood hood;

        private final Superstructure superstructure;

        private final TunableController driver = new TunableController(0);

        /** Driver Start */
        private final Trigger resetGyro = driver.start();
        /** Driver LT */
        private final Trigger intakeNormal = driver.leftTrigger();
        /** Driver RT */
        private final Trigger intakeThroughShooter = driver.rightTrigger();
        /** Driver LB */
        private final Trigger manualOuttake = driver.leftBumper();
        /** Driver RB */
        private final Trigger shoot = driver.rightBumper();
        /** Driver Y */
        private final Trigger hoodUp = driver.y();
        /** Driver X */
        private final Trigger defaultState = driver.x();
        /** Driver LT + RT */
        private final Trigger prepShot = intakeNormal.and(intakeThroughShooter);

        public RobotContainer(Robot robot) {
                drive = new DriveSubsystem();
                switch (Constants.currentMode) {
                        case REAL:
                        intake = new Intake(new IntakeIOCTRE());
                        shooter = new Shooter(new ShooterIOCTRE());
                        hood = new Hood(new HoodIOCTRE());
                                // SimplyModule module0 = new SimplyModule(
                                //                 new SimplyModuleIOCTRE(
                                //                                 new SimplyModuleConfig()
                                //                                                 .withDriveId(Constants.Swerve.DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR)
                                //                                                 .withSteerId(Constants.Swerve.DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR)
                                //                                                 .withModuleId(0)
                                //                                                 .withEncoderOffset(Degrees.of(0))),
                                //                 0);
                                // SimplyModule module1 = new SimplyModule(
                                //                 new SimplyModuleIOCTRE(
                                //                                 new SimplyModuleConfig()
                                //                                                 .withDriveId(Constants.Swerve.DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR)
                                //                                                 .withSteerId(Constants.Swerve.DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR)
                                //                                                 .withModuleId(1)
                                //                                                 .withEncoderOffset(Degrees.of(0))),
                                //                 1);
                                // SimplyModule module2 = new SimplyModule(
                                //                 new SimplyModuleIOCTRE(
                                //                                 new SimplyModuleConfig()
                                //                                                 .withDriveId(Constants.Swerve.DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR)
                                //                                                 .withSteerId(Constants.Swerve.DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR)
                                //                                                 .withModuleId(2)
                                //                                                 .withEncoderOffset(Degrees.of(0))),
                                //                 2);
                                // SimplyModule module3 = new SimplyModule(
                                //                 new SimplyModuleIOCTRE(
                                //                                 new SimplyModuleConfig()
                                //                                                 .withDriveId(Constants.Swerve.DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR)
                                //                                                 .withSteerId(Constants.Swerve.DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR)
                                //                                                 .withModuleId(3)
                                //                                                 .withEncoderOffset(Degrees.of(0))),
                                //                 3);
                                // var ctre = new SimplySwerveIOCTRE(
                                //         new Translation2d[] {
                                //                         new Translation2d(Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)), // fl
                                //                         new Translation2d(Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)), // fr
                                //                         new Translation2d(-Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)), // bl
                                //                         new Translation2d(-Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)) // br
                                //         },
                                //         SPI.Port.kMXP,
                                //         module0,
                                //         module1,
                                //         module2,
                                //         module3);
                                // drive = new SimplySwerve(ctre);

                                // estimator = new SimplyEstimator(drive, ctre::getRobotAngle);

                                // estimator.addModule(module0, new Translation2d(Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)));
                                // estimator.addModule(module1, new Translation2d(Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)));
                                // estimator.addModule(module2, new Translation2d(-Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)));
                                // estimator.addModule(module3, new Translation2d(-Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)));
                                break;
                        case SIM:
                        intake = new Intake(new IntakeIOSIM());
                        shooter = new Shooter(new ShooterIOSIM());
                        hood = new Hood(new HoodIOSIM());
                                // SimplyModule module0sim = new SimplyModule(
                                //                 new SimplyModuleIOSIM(
                                //                                 new SimplyModuleConfig()
                                //                                                 .withDriveId(Constants.Swerve.DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR)
                                //                                                 .withSteerId(Constants.Swerve.DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR)
                                //                                                 .withModuleId(0)
                                //                                                 .withEncoderOffset(Degrees.of(0))),
                                //                 0);
                                // SimplyModule module1sim = new SimplyModule(
                                //                 new SimplyModuleIOSIM(
                                //                                 new SimplyModuleConfig()
                                //                                                 .withDriveId(Constants.Swerve.DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR)
                                //                                                 .withSteerId(Constants.Swerve.DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR)
                                //                                                 .withModuleId(1)
                                //                                                 .withEncoderOffset(Degrees.of(0))),
                                //                 1);
                                // SimplyModule module2sim = new SimplyModule(
                                //                 new SimplyModuleIOSIM(
                                //                                 new SimplyModuleConfig()
                                //                                                 .withDriveId(Constants.Swerve.DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR)
                                //                                                 .withSteerId(Constants.Swerve.DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR)
                                //                                                 .withModuleId(2)
                                //                                                 .withEncoderOffset(Degrees.of(0))),
                                //                 2);
                                // SimplyModule module3sim = new SimplyModule(
                                //                 new SimplyModuleIOSIM(
                                //                                 new SimplyModuleConfig()
                                //                                                 .withDriveId(Constants.Swerve.DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR)
                                //                                                 .withSteerId(Constants.Swerve.DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR)
                                //                                                 .withModuleId(3)
                                //                                                 .withEncoderOffset(Degrees.of(0))),
                                //                 3);
                                // drive = new SimplySwerve(
                                //                 new SimplySwerveIOSIM(
                                //                                 new Translation2d[] {
                                //                                                 new Translation2d(Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)), // fl
                                //                                                 new Translation2d(Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)), // fr
                                //                                                 new Translation2d(-Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)), // bl
                                //                                                 new Translation2d(-Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)) // br
                                //                                 },
                                //                                 SPI.Port.kMXP,
                                //                                 module0sim,
                                //                                 module1sim,
                                //                                 module2sim,
                                //                                 module3sim));

                                // estimator = new SimplyEstimator(drive, drive::getPoseAngle);

                                // estimator.addModule(module0sim, new Translation2d(Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)));
                                // estimator.addModule(module1sim, new Translation2d(Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)));
                                // estimator.addModule(module2sim, new Translation2d(-Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)));
                                // estimator.addModule(module3sim, new Translation2d(-Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)));
                                break;
                        default:
                        intake = new Intake(new IntakeIO() {});
                        shooter = new Shooter(new ShooterIO() {});
                        hood = new Hood(new HoodIO() {});
                                // drive = new SimplySwerve(new SimplySwerveIO() {
                                // });
                                // estimator = new SimplyEstimator(drive, drive::getPoseAngle);
                                break;
                }

                superstructure = new Superstructure(drive, 
                intake, shooter, hood, driver, robot);

                configureButtonBindings();
        }

        private void configureButtonBindings() {
                resetGyro.onTrue(Commands.runOnce(() -> drive.resetGyroscope()));

                intakeNormal.onTrue(superstructure.setWantedState(WantedState.INTAKE)).onFalse(superstructure.setWantedState(WantedState.DEFAULT));

                intakeThroughShooter.onTrue(superstructure.setWantedState(WantedState.SHOOTER_INTAKE)).onFalse(superstructure.setWantedState(WantedState.DEFAULT));
                
                manualOuttake.onTrue(superstructure.setWantedState(WantedState.MANUAL_OUTTAKE)).onFalse(superstructure.setWantedState(WantedState.DEFAULT));

                shoot.onTrue(superstructure.setWantedState(WantedState.SHOOT)).onFalse(superstructure.setWantedState(WantedState.DEFAULT));

                hoodUp.onTrue(superstructure.setWantedState(WantedState.HOOD_UP));

                defaultState.onTrue(superstructure.setWantedState(WantedState.DEFAULT));

                prepShot.onTrue(superstructure.setWantedState(WantedState.PREP_SHOT));
        }

        public HashMap<Subsystems, Pair<Runnable, Pair<Time, Time>>> getAllSubsystems() {
                HashMap<Subsystems, Pair<Runnable, Pair<Time, Time>>> map = new HashMap<>();
                map.put(Subsystems.Drive, new Pair<Runnable,Pair<Time,Time>>(drive::periodic, new Pair<Time,Time>(Milliseconds.of(20), Milliseconds.of(0))));
                map.put(Subsystems.Intake, new Pair<Runnable,Pair<Time,Time>>(intake::periodic, new Pair<Time,Time>(Milliseconds.of(20), Milliseconds.of(0))));
                map.put(Subsystems.Hood, new Pair<Runnable,Pair<Time,Time>>(hood::periodic, new Pair<Time,Time>(Milliseconds.of(20), Milliseconds.of(0))));
                map.put(Subsystems.Shooter, new Pair<Runnable,Pair<Time,Time>>(shooter::periodic, new Pair<Time,Time>(Milliseconds.of(20), Milliseconds.of(0))));
                map.put(Subsystems.Superstructure, new Pair<Runnable,Pair<Time,Time>>(superstructure::periodic, new Pair<Time,Time>(Milliseconds.of(20), Milliseconds.of(0))));
                return map;
        }
}
