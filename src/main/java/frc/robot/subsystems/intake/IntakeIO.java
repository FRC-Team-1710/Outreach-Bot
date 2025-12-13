package frc.robot.subsystems.intake;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

public interface IntakeIO {
  public class IntakeIOInputs {
    public AngularVelocity velocity = DegreesPerSecond.of(0);
    public Voltage appliedVolts = Volts.of(0);
    public Current currentAmps = Amps.of(0);
    public boolean beamBroken = false;
  }

  public default void updateInputs(IntakeIOInputs inputs) {}

  public default void setVoltage(Voltage volts) {}
}
