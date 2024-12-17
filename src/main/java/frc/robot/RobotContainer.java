// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.fasterxml.jackson.databind.introspect.AnnotationCollector.OneAnnotation;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.HoodDownCommand;
import frc.robot.commands.HoodUpCommand;
import frc.robot.commands.IntakeThroughShooterCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

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
    /* Controllers */
    private final Joystick driver = new Joystick(0);

    /* Analog */
    private final int leftVerticalAxis = XboxController.Axis.kLeftY.value;
    private final int leftHorizontalAxis = XboxController.Axis.kLeftX.value;
    private final int rightHorizontalAxis = XboxController.Axis.kRightX.value;
    private final int rightVerticalAxis = XboxController.Axis.kRightY.value;
    private final int leftTrigger = XboxController.Axis.kLeftTrigger.value;
    private final int rightTrigger = XboxController.Axis.kRightTrigger.value;

    /* DRIVER BUTTONS spotless:off */
    /** Driver X */
    //private final JoystickButton NAME = new JoystickButton(driver, XboxController.Button.kX.value);
    /** Driver A */
    private final JoystickButton IntakeThroughShooterButton = new JoystickButton(driver, XboxController.Button.kA.value);
    /** Driver B */
    //private final JoystickButton NAME = new JoystickButton(driver, XboxController.Button.kB.value);
    /** Driver Y */
    //private final JoystickButton NAME = new JoystickButton(driver, XboxController.Button.kY.value);
    /** Driver RB */
    private final JoystickButton HoodUpButton = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
    /** Driver LB */
    private final JoystickButton HoodDownButton = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    /** Driver LT */
    //private final Trigger NAME = new Trigger(() -> driver.getRawAxis(leftTrigger) > .5);
    /** Driver RT */
    private final Trigger ShootButton = new Trigger(() -> driver.getRawAxis(rightTrigger) > .5);
    /** Driver Up */
    //private final Trigger driverUp = new Trigger(() -> driver.getPOV() == 0);
    /** Driver Down */
    //private final Trigger driverDown = new Trigger(() -> driver.getPOV() == 180);
    /** Driver Right */
    //private final Trigger driverLeft = new Trigger(() -> driver.getPOV() == 90);
    /** Driver Left */
    //private final Trigger driverRight = new Trigger(() -> driver.getPOV() == 270);
    /** Driver Start */
    //private final JoystickButton resetOdom = new JoystickButton(driver, XboxController.Button.kStart.value);

    /*Subsystems */
    private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
    private final IndexerSubsystem m_indexerSubsystem = new IndexerSubsystem();
    private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();

    // The robot's subsystems and commands are defined here...
    private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();

    public final static XboxController d_controller = new XboxController(0);

    private final JoystickButton d_startButton = new JoystickButton(d_controller, XboxController.Button.kStart.value);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        m_driveSubsystem.setDefaultCommand(new DriveCommand(m_driveSubsystem, 
        () -> -d_controller.getLeftX(), 
        () -> -d_controller.getLeftY(), 
        () -> -d_controller.getRightX()));

        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        d_startButton
            .onTrue(new InstantCommand(() -> m_driveSubsystem.resetGyroscope()));

        ShootButton
            .whileTrue(new ShootCommand(m_shooterSubsystem, m_indexerSubsystem));

        IntakeThroughShooterButton
            .whileTrue(new IntakeThroughShooterCommand(m_indexerSubsystem, m_intakeSubsystem, m_shooterSubsystem));

        HoodUpButton
            .whileTrue(new HoodUpCommand(m_indexerSubsystem, m_shooterSubsystem));

        HoodDownButton
            .whileTrue(new HoodDownCommand(m_indexerSubsystem, m_shooterSubsystem));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    // public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    // }
}
