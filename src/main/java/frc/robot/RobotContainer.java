// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.NotLogged;
import edu.wpi.first.epilogue.Logged.Importance;
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
import frc.robot.utils.DynamicTimedRobot.TimesConsumer;
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

@Logged
public class RobotContainer {
        // private final SimplySwerve drive;
        // private final SimplyEstimator estimator;

        @Logged(name = "Subsystems/Drive", importance = Importance.INFO)
        private final DriveSubsystem drive;
        @Logged(name = "Subsystems/Intake", importance = Importance.INFO)
        private final Intake intake;
        @Logged(name = "Subsystems/Shooter", importance = Importance.INFO)
        private final Shooter shooter;
        @Logged(name = "Subsystems/Hood", importance = Importance.INFO)
        private final Hood hood;

        @Logged(name = "Subsystems/Superstructure", importance = Importance.INFO)
        private final Superstructure superstructure;

        @NotLogged
        private final TunableController driver = new TunableController(0);

        /** Driver Start */
        @Logged(name = "Triggers/ResetGyro", importance = Importance.INFO)
        private final Trigger resetGyro = driver.start();
        /** Driver LT */
        @Logged(name = "Triggers/IntakeNormal", importance = Importance.INFO)
        private final Trigger intakeNormal = driver.leftTrigger();
        /** Driver RT */
        @Logged(name = "Triggers/IntakeThroughShooter", importance = Importance.INFO)
        private final Trigger intakeThroughShooter = driver.rightTrigger();
        /** Driver LB */
        @Logged(name = "Triggers/ManualOuttake", importance = Importance.INFO)
        private final Trigger manualOuttake = driver.leftBumper();
        /** Driver RB */
        @Logged(name = "Triggers/Shoot", importance = Importance.INFO)
        private final Trigger shoot = driver.rightBumper();
        /** Driver Y */
        @Logged(name = "Triggers/HoodUp", importance = Importance.INFO)
        private final Trigger hoodUp = driver.y();
        /** Driver X */
        @Logged(name = "Triggers/DefaultState", importance = Importance.INFO)
        private final Trigger defaultState = driver.x();
        /** Driver LT + RT */
        @Logged(name = "Triggers/PrepShot", importance = Importance.INFO)
        private final Trigger prepShot = intakeNormal.and(intakeThroughShooter);

        public RobotContainer(TimesConsumer consumer) {
                drive = new DriveSubsystem();
                switch (Constants.currentMode) {
                        case REAL:
                                intake = new Intake(new IntakeIOCTRE());
                                shooter = new Shooter(new ShooterIOCTRE());
                                hood = new Hood(new HoodIOCTRE());
                                break;
                        case SIM:
                                intake = new Intake(new IntakeIOSIM());
                                shooter = new Shooter(new ShooterIOSIM());
                                hood = new Hood(new HoodIOSIM());
                                break;
                        default:
                                intake = new Intake(new IntakeIO() {
                                });
                                shooter = new Shooter(new ShooterIO() {
                                });
                                hood = new Hood(new HoodIO() {
                                });
                                break;
                }

                superstructure = new Superstructure(drive, 
                intake, shooter, hood, driver, consumer);

                configureButtonBindings();
        }

        private void configureButtonBindings() {
                resetGyro.onTrue(Commands.runOnce(() -> drive.resetGyroscope()));

                intakeNormal.onTrue(superstructure.setWantedState(WantedState.INTAKE))
                                .onFalse(superstructure.setWantedState(WantedState.DEFAULT));

                intakeThroughShooter.onTrue(superstructure.setWantedState(WantedState.SHOOTER_INTAKE))
                                .onFalse(superstructure.setWantedState(WantedState.DEFAULT));

                manualOuttake.onTrue(superstructure.setWantedState(WantedState.MANUAL_OUTTAKE))
                                .onFalse(superstructure.setWantedState(WantedState.DEFAULT));

                shoot.onTrue(superstructure.setWantedState(WantedState.SHOOT))
                                .onFalse(superstructure.setWantedState(WantedState.DEFAULT));

                hoodUp.onTrue(superstructure.setWantedState(WantedState.HOOD_UP));

                defaultState.onTrue(superstructure.setWantedState(WantedState.DEFAULT));

                prepShot.onTrue(superstructure.setWantedState(WantedState.PREP_SHOT));
        }

        @NotLogged
        public HashMap<Subsystems, Pair<Runnable, Time>> getAllSubsystems() {
                HashMap<Subsystems, Pair<Runnable, Time>> map = new HashMap<>();
                map.put(Subsystems.Superstructure, new Pair<Runnable, Time>(superstructure::periodic, Milliseconds.of(20)));
                map.put(Subsystems.Drive, new Pair<Runnable, Time>(drive::periodic, Milliseconds.of(20)));
                map.put(Subsystems.Intake, new Pair<Runnable, Time>(intake::periodic, Milliseconds.of(20)));
                map.put(Subsystems.Shooter, new Pair<Runnable, Time>(shooter::periodic, Milliseconds.of(20)));
                map.put(Subsystems.Hood, new Pair<Runnable, Time>(hood::periodic, Milliseconds.of(20)));
                return map;
        }
}
