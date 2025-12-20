// package frc.robot.subsystems.simplySwerve.simplyModule;

// import static edu.wpi.first.units.Units.Degrees;
// import static edu.wpi.first.units.Units.Inches;
// import static edu.wpi.first.units.Units.Meters;
// import static edu.wpi.first.units.Units.MetersPerSecond;
// import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;
// import static edu.wpi.first.units.Units.Rotations;
// import static edu.wpi.first.units.Units.RotationsPerSecond;
// import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;

// import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
// import com.ctre.phoenix6.configs.MotorOutputConfigs;
// import com.ctre.phoenix6.configs.Slot0Configs;
// import com.ctre.phoenix6.configs.TalonFXConfiguration;
// import com.ctre.phoenix6.controls.PositionVoltage;
// import com.ctre.phoenix6.hardware.TalonFX;
// import com.ctre.phoenix6.signals.InvertedValue;
// import com.ctre.phoenix6.signals.NeutralModeValue;

// import edu.wpi.first.units.measure.Distance;

// public class SimplyModuleIOCTRE implements SimplyModuleIO {
//   private final double kDriveGearRatio = 5.90277777777778;
//   private final double kSteerGearRatio = 150.0 / 7.0;

//   private final Distance kWheelRadius = Inches.of(2);

//   private boolean invertSteer = false;

//   private SimplyModuleSpeeds speeds = new SimplyModuleSpeeds();

//   private final int moduleId;

//   private final TalonFX drive;
//   private final TalonFX steer;

//   //TODO remember to swich FOC
//   private final PositionVoltage request = new PositionVoltage(0).withSlot(0).withEnableFOC(false);

//   public SimplyModuleIOCTRE(SimplyModuleConfig config) {
//     this.moduleId = config.moduleId;
//     drive = new TalonFX(config.driveId);
//     steer = new TalonFX(config.steerId);

//     var steerConfig = new TalonFXConfiguration()
//         .withSlot0(
//             new Slot0Configs()
//                 .withKP(3)
//                 .withKI(0)
//                 .withKD(0))
//         .withCurrentLimits(
//             new CurrentLimitsConfigs()
//                 .withStatorCurrentLimitEnable(false)
//                 .withSupplyCurrentLimitEnable(false))
//         .withMotorOutput(
//             new MotorOutputConfigs()
//                 .withInverted(InvertedValue.Clockwise_Positive)
//                 .withNeutralMode(NeutralModeValue.Coast));

//     var driveConfig = new TalonFXConfiguration()
//         .withCurrentLimits(
//             new CurrentLimitsConfigs()
//                 .withStatorCurrentLimit(40)
//                 .withStatorCurrentLimitEnable(true)
//                 .withSupplyCurrentLimitEnable(false))
//         .withMotorOutput(
//             new MotorOutputConfigs()
//                 .withInverted(InvertedValue.Clockwise_Positive)
//                 .withNeutralMode(NeutralModeValue.Brake));

//     steer.getConfigurator().apply(steerConfig);
//     drive.getConfigurator().apply(driveConfig);
//   }

//   @Override
//   public void updateInputs(SimplyModuleIOInputs inputs) {
//     // invertSteer = Math.abs(
//     //     getShortestDistance(
//     //         speeds.getSteerSetpoint().in(Degrees),
//     //         getSteerPosition())) > Math.abs(
//     //             getShortestDistance(
//     //                 speeds.getSteerSetpoint().in(Degrees),
//     //                 (getSteerPosition() + 180) % 360));

//     drive.setVoltage(speeds.getDriveVelocity().in(MetersPerSecond) / 5.16 * (invertSteer ? -1 : 1));

//     steer.setControl(request.withPosition((speeds.getSteerSetpoint().in(Rotations) * kSteerGearRatio)));

//     inputs.wheelRotation = Meters.of(
//         4
//             * Math.PI
//             * drive.getPosition().getValue().in(Rotations)
//             * kWheelRadius.in(Meters)
//             * kDriveGearRatio);
//     inputs.driveVelocity = MetersPerSecond.of(
//         4
//             * Math.PI
//             * drive.getVelocity().getValue().in(RotationsPerSecond)
//             * kWheelRadius.in(Meters)
//             * kDriveGearRatio);
//     inputs.driveAcceleration = MetersPerSecondPerSecond.of(
//         4
//             * drive.getAcceleration().getValue().in(RotationsPerSecondPerSecond)
//             * kWheelRadius.in(Meters)
//             * kDriveGearRatio);
//     inputs.rotation = Rotations.of(getSteerPosition() / 360);
//     inputs.rotationVelocity = RotationsPerSecond.of(steer.getVelocity().getValue().in(RotationsPerSecond) / kSteerGearRatio);
//     inputs.rotationAcceleration = RotationsPerSecondPerSecond.of(
//         steer.getAcceleration().getValue().in(RotationsPerSecondPerSecond) / kSteerGearRatio);
//     inputs.appliedVoltage = steer.getMotorVoltage().getValueAsDouble();
//     speeds.log(moduleId);
//   }

//   @Override
//   public void applySpeeds(SimplyModuleSpeeds speeds) {
//     this.speeds = speeds;
//   }

//   @SuppressWarnings("unused")
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

//   private double getSteerPosition() {
//     return (steer.getPosition().getValue().in(Degrees) / kSteerGearRatio) % 360;
//   }
// }
