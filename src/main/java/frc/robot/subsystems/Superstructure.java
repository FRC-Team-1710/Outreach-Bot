// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Milliseconds;

import java.util.HashMap;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.Time;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Intake.IntakeStates;
import frc.robot.Constants.Subsystems;
import frc.robot.subsystems.hood.Hood;
import frc.robot.subsystems.hood.Hood.HoodStates;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.Shooter.ShooterStates;
import frc.robot.utils.DynamicTimedRobot.TimesConsumer;
// import frc.robot.subsystems.simplySwerve.SimplySwerveRequest;
// import frc.robot.subsystems.simplySwerve.SimplySwerveRequest.RequestType;
import frc.robot.utils.TunableController;

public class Superstructure {
  // private final SimplySwerve drive;
  private final DriveSubsystem drive;
  private final Intake intake;
  private final Shooter shooter;
  private final Hood hood;
  private final TunableController driver;

  private final TimesConsumer consumer;

  // private final SimplySwerveRequest request = new SimplySwerveRequest()
  // .withRequestType(RequestType.FIELD)
  // .withDeadband(0.1);

  private WantedState wantedState = WantedState.DEFAULT;
  private CurrentState currentState = CurrentState.IDLE;

  private final HashMap<Subsystems, Pair<Time, Time>> currentTimesMap = new HashMap<>();
  private final HashMap<Subsystems, Pair<Time, Time>> wantedTimesMap = new HashMap<>();

  public Superstructure(DriveSubsystem drive, // SimplySwerve drive
      Intake intake, Shooter shooter, Hood hood, TunableController driver, TimesConsumer consumer) {
    this.drive = drive;
    this.intake = intake;
    this.shooter = shooter;
    this.hood = hood;
    this.driver = driver;
    this.consumer = consumer;

    currentTimesMap.put(Subsystems.Superstructure, new Pair<Time, Time>(Milliseconds.of(20), Milliseconds.of(0)));
    currentTimesMap.put(Subsystems.Drive, new Pair<Time, Time>(Milliseconds.of(20), Milliseconds.of(0)));
    currentTimesMap.put(Subsystems.Intake, new Pair<Time, Time>(Milliseconds.of(20), Milliseconds.of(0)));
    currentTimesMap.put(Subsystems.Hood, new Pair<Time, Time>(Milliseconds.of(20), Milliseconds.of(0)));
    currentTimesMap.put(Subsystems.Shooter, new Pair<Time, Time>(Milliseconds.of(20), Milliseconds.of(0)));
  }

  public void periodic() {
    wantedTimesMap.put(Subsystems.Drive, new Pair<Time, Time>(Milliseconds.of(20), Milliseconds.of(0)));
    wantedTimesMap.put(Subsystems.Superstructure, new Pair<Time, Time>(Milliseconds.of(20), Milliseconds.of(0)));
    wantedTimesMap.put(Subsystems.Intake, new Pair<Time, Time>(Milliseconds.of(50), Milliseconds.of(0)));
    wantedTimesMap.put(Subsystems.Hood, new Pair<Time, Time>(Milliseconds.of(50), Milliseconds.of(0)));
    wantedTimesMap.put(Subsystems.Shooter, new Pair<Time, Time>(Milliseconds.of(50), Milliseconds.of(0)));
    currentState = handleStateTransitions();
    applyStates();

    for (Subsystems key : currentTimesMap.keySet()) {
      if (currentTimesMap.get(key).getFirst().in(Milliseconds) != wantedTimesMap.get(key).getFirst().in(Milliseconds)) {
        consumer.accept(key, wantedTimesMap.get(key).getFirst(), wantedTimesMap.get(key).getSecond());
        System.out.println(key + " changed period from " + currentTimesMap.get(key).getFirst() + " to " + wantedTimesMap.get(key).getFirst());
        currentTimesMap.put(key, wantedTimesMap.get(key));
      }
    }
  }

  private CurrentState handleStateTransitions() {
    CurrentState newState = CurrentState.IDLE;
    switch (wantedState) {
      case DEFAULT:
        newState = intake.ballSecured() ? CurrentState.BALL_IDLE : CurrentState.IDLE;
        break;
      case HOOD_UP:
        newState = CurrentState.HOOD_UP;
        break;
      case PREP_SHOT:
        newState = intake.ballSecured() ? CurrentState.PREP_SHOT : CurrentState.IDLE;
        break;
      case SHOOT:
        newState = CurrentState.SHOOT;
        break;
      case INTAKE:
        newState = CurrentState.INTAKE;
        break;
      case SHOOTER_INTAKE:
        newState = CurrentState.SHOOTER_INTAKE;
        break;
      case MANUAL_OUTTAKE:
        newState = CurrentState.MANUAL_OUTTAKE;
        break;
    }
    return newState;
  }

  private void applyStates() {
    switch (currentState) {
      case IDLE:
        idle();
        break;
      case BALL_IDLE:
        ballIdle();
        break;
      case HOOD_UP:
        hoodUp();
        break;
      case PREP_SHOT:
        prepShot();
        wantedTimesMap.put(Subsystems.Shooter, new Pair<Time,Time>(Milliseconds.of(20), Milliseconds.of(0)));
        wantedTimesMap.put(Subsystems.Hood, new Pair<Time,Time>(Milliseconds.of(20), Milliseconds.of(0)));
        break;
      case SHOOT:
        shoot();
        wantedTimesMap.put(Subsystems.Shooter, new Pair<Time,Time>(Milliseconds.of(20), Milliseconds.of(0)));
        wantedTimesMap.put(Subsystems.Hood, new Pair<Time,Time>(Milliseconds.of(20), Milliseconds.of(0)));
        wantedTimesMap.put(Subsystems.Intake, new Pair<Time,Time>(Milliseconds.of(20), Milliseconds.of(0)));
        break;
      case INTAKE:
        intake();
        wantedTimesMap.put(Subsystems.Intake, new Pair<Time,Time>(Milliseconds.of(20), Milliseconds.of(0)));
        break;
      case SHOOTER_INTAKE:
        shooterIntake();
        wantedTimesMap.put(Subsystems.Shooter, new Pair<Time,Time>(Milliseconds.of(20), Milliseconds.of(0)));
        wantedTimesMap.put(Subsystems.Intake, new Pair<Time,Time>(Milliseconds.of(20), Milliseconds.of(0)));
        break;
      case MANUAL_OUTTAKE:
        manualOuttake();
        break;
    }
  }

  private void idle() {
    intake.setState(IntakeStates.OFF);
    shooter.setState(ShooterStates.OFF);
    hood.setState(HoodStates.IDLE);
    applyDrive();
  }

  private void ballIdle() {
    intake.setState(IntakeStates.OFF);
    shooter.setState(ShooterStates.OFF);
    hood.setState(HoodStates.IDLE);
    applyDrive();
  }

  private void hoodUp() {
    intake.setState(IntakeStates.OFF);
    shooter.setState(ShooterStates.OFF);
    hood.setState(HoodStates.SHOOT);
    applyDrive();
  }

  private void prepShot() {
    intake.setState(IntakeStates.OFF);
    shooter.setState(ShooterStates.SHOOT);
    hood.setState(HoodStates.SHOOT);
    applyDrive();
  }

  private void shoot() {
    shooter.setState(ShooterStates.SHOOT);
    hood.setState(HoodStates.SHOOT);
    intake.setState((shooter.atSetpoint() && hood.atSetpoint()) ? IntakeStates.FEED : IntakeStates.OFF);
    applyDrive();
  }

  private void intake() {
    intake.setState(IntakeStates.INTAKE);
    shooter.setState(ShooterStates.OFF);
    hood.setState(HoodStates.IDLE);
    applyDrive();
  }

  private void shooterIntake() {
    intake.setState(IntakeStates.SHOOTER_INTAKE);
    shooter.setState(ShooterStates.INTAKE);
    hood.setState(HoodStates.IDLE);
    applyDrive();
    if (intake.ballSecured()) {
      setState(WantedState.DEFAULT);
    }
  }

  private void manualOuttake() {
    intake.setState(IntakeStates.OUTTAKE);
    shooter.setState(ShooterStates.OFF);
    hood.setState(HoodStates.IDLE);
    applyDrive();
  }

  private void applyDrive() {
    // drive.run(() -> request
    // .withX(-driver.customLeft().getY())
    // .withY(-driver.customLeft().getX())
    // .withRotation(driver.customRight().getX())).schedule();
    drive.drive(new Translation2d(-driver.customLeft().getY(), -driver.customLeft().getX()),
        driver.customRight().getX(), true);
  }

  private void setState(WantedState state) {
    this.wantedState = state;
  }

  public Command setWantedState(WantedState state) {
    return Commands.runOnce(() -> setState(state));
  }

  public enum WantedState {
    DEFAULT,
    HOOD_UP,
    PREP_SHOT,
    SHOOT,
    INTAKE,
    SHOOTER_INTAKE,
    MANUAL_OUTTAKE
  }

  public enum CurrentState {
    IDLE,
    BALL_IDLE,
    HOOD_UP,
    PREP_SHOT,
    SHOOT,
    INTAKE,
    SHOOTER_INTAKE,
    MANUAL_OUTTAKE
  }
}
