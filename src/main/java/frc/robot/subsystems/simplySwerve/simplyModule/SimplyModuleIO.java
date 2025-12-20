// package frc.robot.subsystems.simplySwerve.simplyModule;

// import static edu.wpi.first.units.Units.Degrees;
// import static edu.wpi.first.units.Units.DegreesPerSecond;
// import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
// import static edu.wpi.first.units.Units.Meters;
// import static edu.wpi.first.units.Units.MetersPerSecond;
// import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;

// import edu.wpi.first.units.measure.Angle;
// import edu.wpi.first.units.measure.AngularAcceleration;
// import edu.wpi.first.units.measure.AngularVelocity;
// import edu.wpi.first.units.measure.Distance;
// import edu.wpi.first.units.measure.LinearAcceleration;
// import edu.wpi.first.units.measure.LinearVelocity;
// import org.littletonrobotics.junction.AutoLog;

// public interface SimplyModuleIO {
//   @AutoLog
//   public static class SimplyModuleIOInputs {
//     Distance wheelRotation = Meters.of(0);
//     LinearVelocity driveVelocity = MetersPerSecond.of(0);
//     LinearAcceleration driveAcceleration = MetersPerSecondPerSecond.of(0);

//     Angle rotation = Degrees.of(0);
//     AngularVelocity rotationVelocity = DegreesPerSecond.of(0);
//     AngularAcceleration rotationAcceleration = DegreesPerSecondPerSecond.of(0);

//     double appliedVoltage = 0;
//   }

//   public default void updateInputs(SimplyModuleIOInputs inputs) {}

//   public default void applySpeeds(SimplyModuleSpeeds speeds) {}

//   public default void setModuleId(int moduleId) {}
// }
