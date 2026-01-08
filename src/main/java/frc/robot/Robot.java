// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.HashMap;
import java.util.Optional;

import edu.wpi.first.epilogue.Epilogue;
import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.NotLogged;
import edu.wpi.first.epilogue.logging.errors.ErrorHandler;
import edu.wpi.first.math.Pair;
import edu.wpi.first.units.measure.Time;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.Subsystems;
import frc.robot.utils.DynamicTimedRobot;

@Logged
public class Robot extends DynamicTimedRobot {
  @Logged(name = "RobotContainer")
  private RobotContainer m_robotContainer;

  public Robot() {
    Constants.redAlliance = checkRedAlliance();
    
    m_robotContainer = new RobotContainer(this::setSubsystemConsumer);

    addAllSubsystems(m_robotContainer.getAllSubsystems());

    DriverStation.silenceJoystickConnectionWarning(true);

    Epilogue.configure(config -> {
      if (isSimulation()) {
        config.errorHandler = ErrorHandler.crashOnError();
      } else {
        config.errorHandler = ErrorHandler.printErrorMessages();
      }

      config.root = "Telemetry";

      config.minimumImportance = Constants.importance;
    });

    // Epilogue.bind(this);

    DataLogManager.start();

    DataLogManager.log("\nF  I  R  S  T    R  O  B  O  T  I  C  S    T  E  A  M\n______________   _  _____   _  _____   ______________\n\\_____________| / ||___  | / ||  _  | |_____________/\n \\_ _ _ _ _ _ | | |   / /  | || | | | | _ _ _ _ _ _/\n  \\ _ _ _ _ _ | | |  / /   | || |_| | | _ _ _ _ _ /\n   \\__________|_|_|_/_/___ |_||_____|_|__________/\n    \\____________________/ \\____________________/\n");
  }

  @Override
  public void robotInit() {}

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
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

  /** A map of all subsystems with their period and offset time  */
  public void addAllSubsystems(HashMap<Subsystems, Pair<Runnable, Pair<Time, Time>>> subsystems) {
    for (Subsystems key : subsystems.keySet()) {
      addSubsystem(key, subsystems.get(key).getFirst(), subsystems.get(key).getSecond().getFirst(), subsystems.get(key).getSecond().getSecond());
    }
  }

  @NotLogged
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
