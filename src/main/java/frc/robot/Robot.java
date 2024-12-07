// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.DriverStation.MatchType;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.urcl.URCL;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends LoggedRobot {
  private RobotContainer m_robotContainer;

  private boolean logStarted = false;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    LiveWindow.disableAllTelemetry();
    LiveWindow.setEnabled(false);

    DriverStation.silenceJoystickConnectionWarning(true);

    URCL.start();

    DataLogManager.log("\nF  I  R  S  T    R  O  B  O  T  I  C  S    T  E  A  M\n ______________   _  _____   _  _____   ______________\n\\_____________| / ||___  | / ||  _  | |_____________/\n \\_ _ _ _ _ _ | | |   / /  | || | | | | _ _ _ _ _ _/\n  \\ _ _ _ _ _ | | |  / /   | || |_| | | _ _ _ _ _ /\n   \\__________|_|_|_/_/___ |_||_____|_|__________/\n    \\____________________/ \\____________________/\n");

    SmartDashboard.putString("Swerve Calibration/Value1", "https://docs.google.com/document/d/1-HPhrcYGxAi4Wp-iK5DzLxNZFJkpnPF0-LEoKPR5bMc/edit?tab=t.0");
    SmartDashboard.putString("Swerve Calibration/Value2", "Set all offsets to 0");
    SmartDashboard.putString("Swerve Calibration/Value3", "Deploy, don't enable");
    SmartDashboard.putString("Swerve Calibration/Value4", "Flip on its side");
    SmartDashboard.putString("Swerve Calibration/Value5", "Rotate wheels so they are parallel to the floor");
    SmartDashboard.putString("Swerve Calibration/Value6", "If front of robot is on your left, the gears are all facing up.");
    SmartDashboard.putString("Swerve Calibration/Value7", "If front of robot is on your right, the gears are all facing down.");
    SmartDashboard.putString("Swerve Calibration/Value8", "Look on the Swerve Calibration page for all motor angles");
    SmartDashboard.putString("Swerve Calibration/Value9", "Put those numbers into Constants");
    SmartDashboard.putString("Swerve Calibration/Value10", "Deploy and enable");
    SmartDashboard.putString("Swerve Calibration/Value11", "If any wheels not in line, add a negative to the angle offset");
    SmartDashboard.putString("Swerve Calibration/Value12", "If one spins the wrong direction, add 0.5 rotations to the offset");

    SmartDashboard.putString("Controlls/Start Button", "Reset Gyro");
    SmartDashboard.putString("Controlls/Right Bumper", "Intake");
    SmartDashboard.putString("Controlls/Dpad Down", "Zero Arm");
    SmartDashboard.putString("Controlls/Dpad Up", "Zero Hood");
    SmartDashboard.putString("Controlls/Back Button", "Zero Arm and Hood");
    SmartDashboard.putString("Controlls/Left Bumper", "Shoot");
    SmartDashboard.putString("Controlls/X", "Hood Down");
    SmartDashboard.putString("Controlls/Y", "Hood to Preset Position");
    SmartDashboard.putString("Controlls/Right Stick Button", "Stop Flywheels");
    SmartDashboard.putString("Controlls/Triggers", "Manual Aim for Hood");
    SmartDashboard.putBoolean("Slow Mode", false);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();

    SmartDashboard.putNumber("Total Current",
        SmartDashboard.getNumber("Flywheel Current", 0)
        + SmartDashboard.getNumber("Hood Current", 0)
        + SmartDashboard.getNumber("Arm Left Current", 0)
        + SmartDashboard.getNumber("Arm Right Current", 0)
        + SmartDashboard.getNumber("Intake Left Current", 0)
        + SmartDashboard.getNumber("Intake Right Current", 0)
        + SmartDashboard.getNumber("Inside Intake Current", 0)
        + SmartDashboard.getNumber("Drivetrain Current", 0));

    if (!logStarted && DriverStation.isDSAttached()) {
      DataLogManager.start("/media/sda1/logs/", DateTimeFormatter.ofPattern("yyyy-MM-dd__HH-mm-ss").format(LocalDateTime.now()) + ".wpilog");
      DriverStation.startDataLog(DataLogManager.getLog());
      logStarted = true;
    }
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    m_robotContainer.stopAll();
  }

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {}

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
