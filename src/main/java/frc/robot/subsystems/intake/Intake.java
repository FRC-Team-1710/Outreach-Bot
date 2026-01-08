package frc.robot.subsystems.intake;

import frc.robot.Constants;
import frc.robot.subsystems.intake.IntakeIO.IntakeIOInputs;

import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.NotLogged;
import edu.wpi.first.epilogue.Logged.Importance;
import edu.wpi.first.units.measure.Time;

@Logged
public class Intake {
  private Time lastPeriod = Seconds.of(0.02);
  private Time period = Seconds.of(0.02);

  @Logged(name = "Inputs", importance = Importance.INFO)
  private final IntakeIOInputs inputs;
  @Logged(name = "IO", importance = Importance.INFO)
  private final IntakeIO io;

  @Logged(name = "State", importance = Importance.INFO)
  private IntakeStates currentState = IntakeStates.OFF;
  @Logged(name = "BallState", importance = Importance.INFO)
  private CurrentBallState currentBallState = CurrentBallState.NONE;

  public Intake(IntakeIO io) {
    this.io = io;
    this.inputs = new IntakeIOInputs();
  }

  public void periodic() {
    io.updateInputs(inputs);

    switch (currentState) {
      case OFF:
        io.setVoltage(Volts.of(0));
        break;
        case INTAKE:
          if (ballSecured()) {
            io.setVoltage(Volts.of(0));
          } else {
            io.setVoltage(Volts.of(Constants.Intake.IntakeSpeed * 12));
          }
          break;
          case SHOOTER_INTAKE:
            if (ballSecured()) {
              io.setVoltage(Volts.of(0));
            } else {
              io.setVoltage(Volts.of(Constants.Intake.IntakeSpeed * -12));
            }
            break;
      case OUTTAKE:
        io.setVoltage(Volts.of(Constants.Intake.IntakeSpeed * -12));
        break;
      case FEED:
        io.setVoltage(Volts.of(Constants.Intake.FeedSpeed * 12));
        break;
    }
  }

  public boolean periodChanged() {
    if (period.in(Seconds) != lastPeriod.in(Seconds)) {
      lastPeriod = period;
      return true;
    }
    return false;
  }

  public void setPeriod(Time period) {
    this.period = period;
  }

  public Time getPeriod() {
    return period;
  }

  public enum IntakeStates {
    OFF(),
    FEED(),
    INTAKE(),
    SHOOTER_INTAKE(),
    OUTTAKE()
  }

  public enum CurrentBallState {
    NONE(),
    SECURED()
  }

  public void setState(IntakeStates state) {
    this.currentState = state;
  }

  @NotLogged
  public boolean ballSecured() {
    return false;
    // return Constants.currentMode == Mode.SIM
    //     ? (currentBallState == CurrentBallState.SECURED)
    //     : (inputs.beamBroken);
  }

  public void advanceGamePiece() {
    currentBallState = switch (currentBallState) {
      case NONE -> CurrentBallState.SECURED;
      case SECURED -> CurrentBallState.NONE;
    };
  }
}
