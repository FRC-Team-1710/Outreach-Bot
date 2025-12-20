// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Seconds;

import java.util.Optional;

import edu.wpi.first.units.measure.Time;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.Subsystems;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.utils.DynamicTimedRobot;

public class Robot extends DynamicTimedRobot {
  @SuppressWarnings("unused")
  private RobotContainer m_robotContainer;

  private DriveSubsystem drive;

  private Time lastPeriod = Seconds.of(1);

  @Override
  public void robotInit() {
    Constants.redAlliance = checkRedAlliance();

    drive = new DriveSubsystem();
    
    m_robotContainer = new RobotContainer();

    DriverStation.silenceJoystickConnectionWarning(true);

    DataLogManager.log("\nF  I  R  S  T    R  O  B  O  T  I  C  S    T  E  A  M\n______________   _  _____   _  _____   ______________\n\\_____________| / ||___  | / ||  _  | |_____________/\n \\_ _ _ _ _ _ | | |   / /  | || | | | | _ _ _ _ _ _/\n  \\ _ _ _ _ _ | | |  / /   | || |_| | | _ _ _ _ _ /\n   \\__________|_|_|_/_/___ |_||_____|_|__________/\n    \\____________________/ \\____________________/\n");

    addSubsystem(Subsystems.Drive, drive::periodic, lastPeriod);

    SmartDashboard.putNumber("BruhPeriod", lastPeriod.in(Seconds));
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    if (SmartDashboard.getNumber("BruhPeriod", lastPeriod.in(Seconds)) != lastPeriod.in(Seconds)) {
      lastPeriod = Seconds.of(SmartDashboard.getNumber("BruhPeriod", lastPeriod.in(Seconds)));
      setSubsystem(Subsystems.Drive, lastPeriod);
    }
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    Constants.redAlliance = checkRedAlliance();
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    Constants.redAlliance = checkRedAlliance();
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}

  public static boolean checkRedAlliance() {
    Optional<Alliance> alliance = DriverStation.getAlliance();
    if (alliance.isPresent()) {
      return alliance.get() == DriverStation.Alliance.Red;
    } else {
      DataLogManager.log("ERROR: Alliance not found. Defaulting to Blue");
      return false;
    }
  }
}
