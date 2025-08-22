// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.simplySwerve.SimplyEstimator;
import frc.robot.subsystems.simplySwerve.SimplySwerve;
import frc.robot.subsystems.simplySwerve.SimplySwerveIO;
import frc.robot.subsystems.simplySwerve.SimplySwerveIOCTRE;
import frc.robot.subsystems.simplySwerve.SimplySwerveIOSIM;
import frc.robot.subsystems.simplySwerve.SimplySwerveRequest;
import frc.robot.subsystems.simplySwerve.SimplySwerveRequest.RequestType;
import frc.robot.subsystems.simplySwerve.simplyModule.SimplyModule;
import frc.robot.subsystems.simplySwerve.simplyModule.SimplyModuleConfig;
import frc.robot.subsystems.simplySwerve.simplyModule.SimplyModuleIOCTRE;
import frc.robot.subsystems.simplySwerve.simplyModule.SimplyModuleIOSIM;
import frc.robot.utils.TunableController;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
        private final SimplySwerve drive;
        private final SimplyEstimator estimator;
        private final Intake intake;

        private final TunableController driver = new TunableController(0);

        private final SimplySwerveRequest request = new SimplySwerveRequest().withRequestType(RequestType.FIELD)
                        .withDeadband(0.1);

        // /** Driver Start */
        // private final JoystickButton resetGyro = new JoystickButton(Driver,
        // XboxController.Button.kStart.value);
        // /** Driver RB */
        // private final JoystickButton intake = new JoystickButton(Driver,
        // XboxController.Button.kRightBumper.value);
        // /** Driver Down */
        // private final Trigger zeroArm = new Trigger(() -> Driver.getPOV() == 180);
        // /** Driver Up */
        // private final Trigger zeroExtender = new Trigger(() -> Driver.getPOV() == 0);
        // /** Driver RT */
        // private final Trigger target = new Trigger(() -> Driver.getRightTriggerAxis()
        // > 0.75);
        // /** Driver Back */
        // private final JoystickButton zeroAll = new JoystickButton(Driver,
        // XboxController.Button.kBack.value);
        // /** Driver LB */
        // private final JoystickButton outake = new JoystickButton(Driver,
        // XboxController.Button.kLeftBumper.value);
        // /** Driver A */
        // private final JoystickButton shootNow = new JoystickButton(Driver,
        // XboxController.Button.kA.value);
        // /** Driver B */
        // private final JoystickButton intakeThroughShooter = new
        // JoystickButton(Driver, XboxController.Button.kB.value);
        // /** Driver X */
        // private final JoystickButton shooterdown = new JoystickButton(Driver,
        // XboxController.Button.kX.value);
        // /** Driver Y */
        // private final JoystickButton shooterup = new JoystickButton(Driver,
        // XboxController.Button.kY.value);
        // /** Driver Right Stick */
        // private final JoystickButton stopFlywheels = new JoystickButton(Driver,
        // XboxController.Button.kRightStick.value);

        public RobotContainer() {
                intake = new Intake();
                switch (Constants.currentMode) {
                        case REAL:
                                SimplyModule module0 = new SimplyModule(
                                                new SimplyModuleIOCTRE(
                                                                new SimplyModuleConfig()
                                                                                .withDriveId(Constants.Swerve.DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR)
                                                                                .withSteerId(Constants.Swerve.DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR)
                                                                                .withModuleId(0)
                                                                                .withEncoderOffset(Degrees.of(0))),
                                                0);
                                SimplyModule module1 = new SimplyModule(
                                                new SimplyModuleIOCTRE(
                                                                new SimplyModuleConfig()
                                                                                .withDriveId(Constants.Swerve.DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR)
                                                                                .withSteerId(Constants.Swerve.DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR)
                                                                                .withModuleId(1)
                                                                                .withEncoderOffset(Degrees.of(0))),
                                                1);
                                SimplyModule module2 = new SimplyModule(
                                                new SimplyModuleIOCTRE(
                                                                new SimplyModuleConfig()
                                                                                .withDriveId(Constants.Swerve.DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR)
                                                                                .withSteerId(Constants.Swerve.DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR)
                                                                                .withModuleId(2)
                                                                                .withEncoderOffset(Degrees.of(0))),
                                                2);
                                SimplyModule module3 = new SimplyModule(
                                                new SimplyModuleIOCTRE(
                                                                new SimplyModuleConfig()
                                                                                .withDriveId(Constants.Swerve.DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR)
                                                                                .withSteerId(Constants.Swerve.DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR)
                                                                                .withModuleId(3)
                                                                                .withEncoderOffset(Degrees.of(0))),
                                                3);
                                drive = new SimplySwerve(
                                                new SimplySwerveIOCTRE(
                                                                new Translation2d[] {
                                                                                new Translation2d(Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)), // fl
                                                                                new Translation2d(Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)), // fr
                                                                                new Translation2d(-Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)), // bl
                                                                                new Translation2d(-Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)) // br
                                                                },
                                                                SPI.Port.kMXP,
                                                                module0,
                                                                module1,
                                                                module2,
                                                                module3));

                                estimator = new SimplyEstimator(drive);

                                estimator.addModule(module0, new Translation2d(Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)));
                                estimator.addModule(module1, new Translation2d(Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)));
                                estimator.addModule(module2, new Translation2d(-Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)));
                                estimator.addModule(module3, new Translation2d(-Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)));
                                break;
                        case SIM:
                                SimplyModule module0sim = new SimplyModule(
                                                new SimplyModuleIOSIM(
                                                                new SimplyModuleConfig()
                                                                                .withDriveId(Constants.Swerve.DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR)
                                                                                .withSteerId(Constants.Swerve.DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR)
                                                                                .withModuleId(0)
                                                                                .withEncoderOffset(Degrees.of(0))),
                                                0);
                                SimplyModule module1sim = new SimplyModule(
                                                new SimplyModuleIOSIM(
                                                                new SimplyModuleConfig()
                                                                                .withDriveId(Constants.Swerve.DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR)
                                                                                .withSteerId(Constants.Swerve.DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR)
                                                                                .withModuleId(1)
                                                                                .withEncoderOffset(Degrees.of(0))),
                                                1);
                                SimplyModule module2sim = new SimplyModule(
                                                new SimplyModuleIOSIM(
                                                                new SimplyModuleConfig()
                                                                                .withDriveId(Constants.Swerve.DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR)
                                                                                .withSteerId(Constants.Swerve.DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR)
                                                                                .withModuleId(2)
                                                                                .withEncoderOffset(Degrees.of(0))),
                                                2);
                                SimplyModule module3sim = new SimplyModule(
                                                new SimplyModuleIOSIM(
                                                                new SimplyModuleConfig()
                                                                                .withDriveId(Constants.Swerve.DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR)
                                                                                .withSteerId(Constants.Swerve.DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR)
                                                                                .withModuleId(3)
                                                                                .withEncoderOffset(Degrees.of(0))),
                                                3);
                                drive = new SimplySwerve(
                                                new SimplySwerveIOSIM(
                                                                new Translation2d[] {
                                                                                new Translation2d(Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)), // fl
                                                                                new Translation2d(Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)), // fr
                                                                                new Translation2d(-Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)), // bl
                                                                                new Translation2d(-Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)) // br
                                                                },
                                                                SPI.Port.kMXP,
                                                                module0sim,
                                                                module1sim,
                                                                module2sim,
                                                                module3sim));

                                estimator = new SimplyEstimator(drive);

                                estimator.addModule(module0sim, new Translation2d(Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)));
                                estimator.addModule(module1sim, new Translation2d(Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)));
                                estimator.addModule(module2sim, new Translation2d(-Units.inchesToMeters(11.5), Units.inchesToMeters(11.5)));
                                estimator.addModule(module3sim, new Translation2d(-Units.inchesToMeters(11.5), -Units.inchesToMeters(11.5)));
                                break;
                        default:
                                drive = new SimplySwerve(new SimplySwerveIO() {
                                });
                                estimator = new SimplyEstimator(drive);
                                break;
                }

                configureButtonBindings();
        }

        private void configureButtonBindings() {
                drive.setDefaultCommand(drive.run(() -> request
                                .withX(-driver.customLeft().getY())
                                .withY(-driver.customLeft().getX())
                                .withRotation(driver.customRight().getX())));
        }
}
