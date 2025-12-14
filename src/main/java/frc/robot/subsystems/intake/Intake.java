package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.intake.IntakeIO.IntakeIOInputs;

import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.NotLogged;
import edu.wpi.first.epilogue.Logged.Importance;

@Logged
public class Intake extends SubsystemBase {
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

  @Override
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
