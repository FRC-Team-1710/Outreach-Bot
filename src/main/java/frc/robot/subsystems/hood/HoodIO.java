package frc.robot.subsystems.hood;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.Logged.Importance;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

@Logged
public interface HoodIO {
  @Logged
  public class HoodIOInputs {
    @Logged(name = "Velocity(radps)", importance = Importance.INFO)
    public AngularVelocity velocity = DegreesPerSecond.of(0);
    @Logged(name = "Setpoint(radps)", importance = Importance.INFO)
    public Angle setpoint = Degrees.of(0);
    @Logged(name = "Position(rotations)", importance = Importance.INFO)
    public Angle position = Degrees.of(0);
    @Logged(name = "AppliedVoltage", importance = Importance.INFO)
    public Voltage appliedVolts = Volts.of(0);
    @Logged(name = "Current(amps)", importance = Importance.INFO)
    public Current currentAmps = Amps.of(0);
  }

  public default void updateInputs(HoodIOInputs inputs) {}

  public default void setAngle(Angle angle) {}
}
