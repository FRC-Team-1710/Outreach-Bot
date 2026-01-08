package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Constants.Mode;
import frc.robot.subsystems.shooter.ShooterIO.ShooterIOInputs;

import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.Logged.Importance;
import edu.wpi.first.units.measure.Time;

@Logged
public class Shooter {
  @Logged(name = "Inputs", importance = Importance.INFO)
  private final ShooterIOInputs inputs;
  private Time lastPeriod = Seconds.of(0.02);
  private Time period = Seconds.of(0.02);
  
  @Logged(name = "IO", importance = Importance.INFO)
  private final ShooterIO io;

  @Logged(name = "CurrentState", importance = Importance.INFO)
  private ShooterStates currentState = ShooterStates.OFF;

  public Shooter(ShooterIO io) {
    this.io = io;
    this.inputs = new ShooterIOInputs();
    SmartDashboard.putNumber("FlywheelShootSpeed", Constants.Flywheel.ShootSpeedRPM);
  }

  public void periodic() {
    io.updateInputs(inputs);

    Constants.Flywheel.ShootSpeedRPM = SmartDashboard.getNumber("FlywheelShootSpeed", 0);

    switch (currentState) {
      case OFF:
        io.setVoltage(Volts.of(Constants.Flywheel.IdleSpeedRPM / 6380 * 12));
        break;
      case INTAKE:
        io.setVoltage(Volts.of(Constants.Flywheel.IntakeSpeed * -12));
        break;
      case SHOOT:
        io.setVoltage(Volts.of(Constants.Flywheel.ShootSpeedRPM / 6380 * 12));
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

  public enum ShooterStates {
    OFF(),
    SHOOT(),
    INTAKE()
  }

  public void setState(ShooterStates state) {
    this.currentState = state;
  }

  @Logged(name = "AtSetpoint", importance = Importance.INFO)
  public boolean atSetpoint() {
    return inputs.velocity.isNear(RotationsPerSecond.of(Constants.Flywheel.ShootSpeedRPM / 60), RotationsPerSecond.of(500 / 60)) || (Constants.currentMode == Mode.SIM);
  }
}
