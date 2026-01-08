package frc.robot.subsystems.intake;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.Volts;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.Logged.Importance;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

@Logged
public interface IntakeIO {
  @Logged
  public class IntakeIOInputs {
    @Logged(name = "Velocity(radps)", importance = Importance.INFO)
    public AngularVelocity velocity = DegreesPerSecond.of(0);
    @Logged(name = "AppliedVoltage", importance = Importance.INFO)
    public Voltage appliedVolts = Volts.of(0);
    @Logged(name = "Current(amps)", importance = Importance.INFO)
    public Current currentAmps = Amps.of(0);
    @Logged(name = "BeamBroken", importance = Importance.INFO)
    public boolean beamBroken = false;
  }

  public default void updateInputs(IntakeIOInputs inputs) {}

  public default void setVoltage(Voltage volts) {}
}
