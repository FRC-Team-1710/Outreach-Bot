// package frc.robot.subsystems.hood;

// import static edu.wpi.first.units.Units.Amps;
// import static edu.wpi.first.units.Units.Degrees;
// import static edu.wpi.first.units.Units.DegreesPerSecond;
// import static edu.wpi.first.units.Units.Volts;

// import org.littletonrobotics.junction.AutoLog;

// import edu.wpi.first.units.measure.Angle;
// import edu.wpi.first.units.measure.AngularVelocity;
// import edu.wpi.first.units.measure.Current;
// import edu.wpi.first.units.measure.Voltage;

// public interface HoodIO {
//   @AutoLog
//   public class HoodIOInputs {
//     public AngularVelocity velocity = DegreesPerSecond.of(0);
//     public Angle setpoint = Degrees.of(0);
//     public Angle position = Degrees.of(0);
//     public Voltage appliedVolts = Volts.of(0);
//     public Current currentAmps = Amps.of(0);
//   }

//   public default void updateInputs(HoodIOInputs inputs) {}

//   public default void setAngle(Angle angle) {}
// }
