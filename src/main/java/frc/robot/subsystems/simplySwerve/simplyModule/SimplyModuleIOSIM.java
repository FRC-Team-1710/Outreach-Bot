// package frc.robot.subsystems.simplySwerve.simplyModule;

// import static edu.wpi.first.units.Units.Degrees;
// import static edu.wpi.first.units.Units.Inches;
// import static edu.wpi.first.units.Units.Meters;
// import static edu.wpi.first.units.Units.MetersPerSecond;
// import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;
// import static edu.wpi.first.units.Units.Rotations;
// import static edu.wpi.first.units.Units.RotationsPerSecond;
// import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;

// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.math.system.plant.DCMotor;
// import edu.wpi.first.math.system.plant.LinearSystemId;
// import edu.wpi.first.units.measure.Distance;
// import edu.wpi.first.wpilibj.simulation.DCMotorSim;

// public class SimplyModuleIOSIM implements SimplyModuleIO {
//   private final double kDriveGearRatio = 5.90277777777778;
//   private final double kSteerGearRatio = 150.0 / 7.0;

//   private final Distance kWheelRadius = Inches.of(2);

//   private boolean invertSteer = false;

//   private PIDController steerPid = new PIDController(75, 0, 0);

//   private final DCMotorSim steerSim =
//       new DCMotorSim(
//           LinearSystemId.createDCMotorSystem(DCMotor.getKrakenX60Foc(1), 0.01, kSteerGearRatio),
//           DCMotor.getKrakenX60Foc(1));

//   private final DCMotorSim driveSim =
//       new DCMotorSim(
//           LinearSystemId.createDCMotorSystem(DCMotor.getKrakenX60Foc(1), 0.01, kDriveGearRatio),
//           DCMotor.getKrakenX60Foc(1));

//   private SimplyModuleSpeeds speeds = new SimplyModuleSpeeds();

//   private final int moduleId;

//   public SimplyModuleIOSIM(SimplyModuleConfig config) {
//     this.moduleId = config.moduleId;
//   }

//   @Override
//   public void updateInputs(SimplyModuleIOInputs inputs) {
//     invertSteer =
//         Math.abs(
//                 getShortestDistance(
//                     speeds.getSteerSetpoint().in(Degrees),
//                     steerSim.getAngularPosition().in(Degrees) % 360))
//             > Math.abs(
//                 getShortestDistance(
//                     speeds.getSteerSetpoint().in(Degrees),
//                     (steerSim.getAngularPosition().in(Degrees) + 180) % 360));

//     driveSim.setInputVoltage(
//         speeds.getDriveVelocity().in(MetersPerSecond) / 5.16 * (invertSteer ? -1 : 1));
//     driveSim.update(0.02);

//     steerSim.setInputVoltage(
//         steerPid.calculate(
//                 getShortestDistance(
//                     speeds.getSteerSetpoint().in(Degrees),
//                     (steerSim.getAngularPosition().in(Degrees) + (invertSteer ? 180 : 0)) % 360),
//                 0)
//             / 360);
//     steerSim.update(0.02);

//     inputs.wheelRotation =
//         Meters.of(
//             4
//                 * Math.PI
//                 * driveSim.getAngularPosition().in(Rotations)
//                 * kWheelRadius.in(Meters)
//                 * kDriveGearRatio);
//     inputs.driveVelocity =
//         MetersPerSecond.of(
//             4
//                 * Math.PI
//                 * driveSim.getAngularVelocity().in(RotationsPerSecond)
//                 * kWheelRadius.in(Meters)
//                 * kDriveGearRatio);
//     inputs.driveAcceleration =
//         MetersPerSecondPerSecond.of(
//             4
//                 * driveSim.getAngularAcceleration().in(RotationsPerSecondPerSecond)
//                 * kWheelRadius.in(Meters)
//                 * kDriveGearRatio);
//     inputs.rotation = Rotations.of(steerSim.getAngularPosition().in(Rotations));
//     inputs.rotationVelocity =
//         RotationsPerSecond.of(steerSim.getAngularVelocity().in(RotationsPerSecond));
//     inputs.rotationAcceleration =
//         RotationsPerSecondPerSecond.of(
//             steerSim.getAngularAcceleration().in(RotationsPerSecondPerSecond));
//     inputs.appliedVoltage = steerSim.getInputVoltage();
//     speeds.log(moduleId);
//   }

//   @Override
//   public void applySpeeds(SimplyModuleSpeeds speeds) {
//     this.speeds = speeds;
//   }

//   private double getShortestDistance(double angle1, double angle2) {
//     double diff = ((angle2 % 360 + 360) % 360) - ((angle1 % 360 + 360) % 360);

//     if (diff > 180) {
//       return diff - 360;
//     } else if (diff < -180) {
//       return diff + 360;
//     } else {
//       return diff;
//     }
//   }
// }
