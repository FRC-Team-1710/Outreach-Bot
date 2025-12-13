package frc.robot.subsystems.shooter;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

public interface ShooterIO {
  public class ShooterIOInputs {
    public AngularVelocity velocity = DegreesPerSecond.of(0);
    public AngularVelocity setpoint = DegreesPerSecond.of(0);
    public Voltage appliedVolts = Volts.of(0);
    public Current currentAmps = Amps.of(0);
  }

  public default void updateInputs(ShooterIOInputs inputs) {}

  public default void setSpeed(AngularVelocity speed) {}

  public default void setVoltage(Voltage volts) {}
}
