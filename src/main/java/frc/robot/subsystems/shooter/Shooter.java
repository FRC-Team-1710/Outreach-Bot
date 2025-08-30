package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Mode;

import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.Logger;

public class Shooter extends SubsystemBase {
  private final ShooterIOInputsAutoLogged inputs;
  private final ShooterIO io;

  private ShooterStates currentState = ShooterStates.OFF;

  public Shooter(ShooterIO io) {
    this.io = io;
    this.inputs = new ShooterIOInputsAutoLogged();
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Shooter", inputs);

    switch (currentState) {
      case OFF:
        io.setVoltage(Volts.of(0));
        break;
      case INTAKE:
        io.setVoltage(Volts.of(Constants.Flywheel.IntakeSpeed * -12));
        break;
      case SHOOT:
        io.setVoltage(Volts.of(Constants.Flywheel.ShootSpeedRPM / 6380 * 12));
        // io.setSpeed(RotationsPerSecond.of(Constants.Flywheel.ShootSpeedRPM/60));
        break;
    }
  }

  public enum ShooterStates {
    OFF(),
    SHOOT(),
    INTAKE()
  }

  public void setState(ShooterStates state) {
    this.currentState = state;
  }

  public boolean atSetpoint() {
    return inputs.velocity.isNear(inputs.setpoint, RotationsPerSecond.of(500 / 60)) || (Constants.currentMode == Mode.SIM);
  }
}
