// package frc.robot.subsystems.intake;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants;
// import frc.robot.Constants.Mode;

// import static edu.wpi.first.units.Units.Volts;

// import org.littletonrobotics.junction.AutoLogOutput;
// import org.littletonrobotics.junction.Logger;

// public class Intake extends SubsystemBase {
//   private final IntakeIOInputsAutoLogged inputs;
//   private final IntakeIO io;

//   private IntakeStates currentState = IntakeStates.OFF;
//   private CurrentBallState currentBallState = CurrentBallState.NONE;

//   public Intake(IntakeIO io) {
//     this.io = io;
//     this.inputs = new IntakeIOInputsAutoLogged();
//   }

//   @Override
//   public void periodic() {
//     io.updateInputs(inputs);
//     Logger.processInputs("Intake", inputs);

//     switch (currentState) {
//       case OFF:
//         io.setVoltage(Volts.of(0));
//         break;
//         case INTAKE:
//           if (ballSecured()) {
//             io.setVoltage(Volts.of(0));
//           } else {
//             io.setVoltage(Volts.of(Constants.Intake.IntakeSpeed * 12));
//           }
//           break;
//           case SHOOTER_INTAKE:
//             if (ballSecured()) {
//               io.setVoltage(Volts.of(0));
//             } else {
//               io.setVoltage(Volts.of(Constants.Intake.IntakeSpeed * -12));
//             }
//             break;
//       case OUTTAKE:
//         io.setVoltage(Volts.of(Constants.Intake.IntakeSpeed * -12));
//         break;
//       case FEED:
//         io.setVoltage(Volts.of(Constants.Intake.FeedSpeed * 12));
//         break;
//     }
//   }

//   public enum IntakeStates {
//     OFF(),
//     FEED(),
//     INTAKE(),
//     SHOOTER_INTAKE(),
//     OUTTAKE()
//   }

//   public enum CurrentBallState {
//     NONE(),
//     SECURED()
//   }

//   public void setState(IntakeStates state) {
//     this.currentState = state;
//   }

//   @AutoLogOutput
//   public boolean ballSecured() {
//     return Constants.currentMode == Mode.SIM
//         ? (currentBallState == CurrentBallState.SECURED)
//         : (inputs.beamBroken);
//   }

//   public void advanceGamePiece() {
//     currentBallState = switch (currentBallState) {
//       case NONE -> CurrentBallState.SECURED;
//       case SECURED -> CurrentBallState.NONE;
//     };
//   }
// }
