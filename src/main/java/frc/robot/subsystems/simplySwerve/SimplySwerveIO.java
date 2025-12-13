package frc.robot.subsystems.simplySwerve;

import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearAcceleration;
import edu.wpi.first.units.measure.LinearVelocity;

public interface SimplySwerveIO {
  // @AutoLog
  // public static class SimplySwerveIOInputs {
  //   SwerveModuleState[] logableStates;

  //   Pose2d pose = new Pose2d();

  //   LinearVelocity robotVelocity = MetersPerSecond.of(0);
  //   LinearAcceleration robotAcceleration = MetersPerSecondPerSecond.of(0);
  //   AngularVelocity rotationVelocity = DegreesPerSecond.of(0);
  //   AngularAcceleration rotationAcceleration = DegreesPerSecondPerSecond.of(0);
  // }

  // public default void updateInputs(SimplySwerveIOInputs inputs) {}

  // public default void run(SimplySwerveRequest request) {}

  // public default void resetGyro() {}
}
