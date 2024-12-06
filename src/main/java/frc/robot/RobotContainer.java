// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ManualAim;
import frc.robot.commands.Shoot;
import frc.robot.commands.TheIntakeCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakerSubsystem;
import frc.robot.subsystems.OverBumperSubsystem;
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
    // The robot's subsystems and commands are defined here...
    private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
    private final IntakerSubsystem m_intakeSubsystem = new IntakerSubsystem();
    private final OverBumperSubsystem m_overBumperSubsystem = new OverBumperSubsystem();
    private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();

    public final static XboxController Driver = new XboxController(0);

    /** Driver Start */
    private final JoystickButton resetGyro = new JoystickButton(Driver, XboxController.Button.kStart.value);
    /** Driver RB */
    private final JoystickButton intaker = new JoystickButton(Driver, XboxController.Button.kRightBumper.value);
    /** Driver Down */
    private final Trigger zeroArm = new Trigger(() -> Driver.getPOV() == 180);
    /** Driver Up */
    private final Trigger zeroExtender = new Trigger(() -> Driver.getPOV() == 0);
    /** Driver Back */
    private final JoystickButton zeroAll = new JoystickButton(Driver, XboxController.Button.kBack.value);
    /** Driver LB */
    private final JoystickButton shoot = new JoystickButton(Driver, XboxController.Button.kLeftBumper.value);
    /** Driver X */
    private final JoystickButton shooterdown = new JoystickButton(Driver, XboxController.Button.kX.value);
    /** Driver Y */
    private final JoystickButton shooterup = new JoystickButton(Driver, XboxController.Button.kY.value);
    /** Driver Right Stick */
    private final JoystickButton stopFlywheels = new JoystickButton(Driver, XboxController.Button.kRightStick.value);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        m_driveSubsystem.setDefaultCommand(new DriveCommand(m_driveSubsystem, 
        () -> -Driver.getLeftX(), 
        () -> -Driver.getLeftY(), 
        () -> -Driver.getRightX()));

        m_shooterSubsystem.setDefaultCommand(new ManualAim(m_shooterSubsystem,
        () -> Driver.getRawAxis(XboxController.Axis.kLeftTrigger.value), 
        () -> Driver.getRawAxis(XboxController.Axis.kRightTrigger.value)));

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
        //Reset Gyro
        resetGyro.onTrue(new InstantCommand(() -> m_driveSubsystem.resetGyroscope()));

        //Intaker
        intaker.whileTrue(new TheIntakeCommand(m_intakeSubsystem, m_overBumperSubsystem, Driver));

        //Zero arm and extender
        zeroAll.onTrue(new InstantCommand(() -> m_overBumperSubsystem.setZero(Constants.Arm.Offset))
            .alongWith(new InstantCommand(() -> m_shooterSubsystem.zeroHood(Constants.Shooter.Offset))))
            .onFalse(new InstantCommand(() -> m_overBumperSubsystem.setUporDown(true, Constants.Arm.ArmUp)));

        //Zero arm
        zeroArm.onTrue(new InstantCommand(() -> m_overBumperSubsystem.setZero(Constants.Arm.Offset)));

        //Zero extender
        zeroExtender.onTrue(new InstantCommand(() -> m_shooterSubsystem.zeroHood(Constants.Shooter.Offset)));

        //Shoot
        intaker.negate()
            .and(shoot)
            .whileTrue(new Shoot(m_shooterSubsystem, m_intakeSubsystem, Driver));
        
        //Bring shooter down
        shooterdown.onTrue(new InstantCommand(() -> m_shooterSubsystem.setHoodPosition(Constants.Shooter.Offset)));

        //Bring shooter up to a preset angle
        shooterup.onTrue(new InstantCommand(() -> m_shooterSubsystem.setHoodPosition(Constants.Shooter.Shootangle)));

        //Stop flywheel
        stopFlywheels.onTrue(new InstantCommand(() -> m_shooterSubsystem.setFlywheelVelocity(0)));
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
