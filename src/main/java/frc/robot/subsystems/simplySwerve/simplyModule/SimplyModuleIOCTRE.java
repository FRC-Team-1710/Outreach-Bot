package frc.robot.subsystems.simplySwerve.simplyModule;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;

import com.ctre.phoenix6.configs.ClosedLoopGeneralConfigs;
import com.ctre.phoenix6.configs.CustomParamsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SimplyModuleIOCTRE implements SimplyModuleIO {
  private final double kDriveGearRatio = 5.90277777777778;
  private final double kSteerGearRatio = 150.0 / 7.0;

  private final Distance kWheelRadius = Inches.of(2);

  private boolean invertSteer = false;

  private SimplyModuleSpeeds speeds = new SimplyModuleSpeeds();

  private final int moduleId;

  private final TalonFX drive;
  private final TalonFX steer;

  private final PositionTorqueCurrentFOC request = new PositionTorqueCurrentFOC(0).withSlot(0);

  public SimplyModuleIOCTRE(SimplyModuleConfig config) {
    this.moduleId = config.moduleId;
    drive = new TalonFX(config.driveId);
    steer = new TalonFX(config.steerId);

    var steerConfig = new TalonFXConfiguration().withClosedLoopGeneral(new ClosedLoopGeneralConfigs().withContinuousWrap(true));
  }

  @Override
  public void updateInputs(SimplyModuleIOInputs inputs) {
    invertSteer =
        Math.abs(
                getShortestDistance(
                    speeds.getSteerSetpoint().in(Degrees),
                    steer.getPosition().getValue().in(Degrees) % 360))
            > Math.abs(
                getShortestDistance(
                    speeds.getSteerSetpoint().in(Degrees),
                    (steer.getPosition().getValue().in(Degrees) + 180) % 360));

    drive.setVoltage(speeds.getDriveVelocity().in(MetersPerSecond) / 5.16 * (invertSteer ? -1 : 1));

    steer.setControl(request.withPosition(speeds.getSteerSetpoint().in(Rotations)%0.5));

    inputs.wheelRotation =
        Meters.of(
            4
                * Math.PI
                * drive.getPosition().getValue().in(Rotations)
                * kWheelRadius.in(Meters)
                * kDriveGearRatio);
    inputs.driveVelocity =
        MetersPerSecond.of(
            4
                * Math.PI
                * drive.getVelocity().getValue().in(RotationsPerSecond)
                * kWheelRadius.in(Meters)
                * kDriveGearRatio);
    inputs.driveAcceleration =
        MetersPerSecondPerSecond.of(
            4
                * drive.getAcceleration().getValue().in(RotationsPerSecondPerSecond)
                * kWheelRadius.in(Meters)
                * kDriveGearRatio);
    inputs.rotation = Rotations.of(steer.getPosition().getValue().in(Rotations));
    inputs.rotationVelocity =
        RotationsPerSecond.of(steer.getVelocity().getValue().in(RotationsPerSecond));
    inputs.rotationAcceleration =
        RotationsPerSecondPerSecond.of(
            steer.getAcceleration().getValue().in(RotationsPerSecondPerSecond));
    inputs.appliedVoltage = steer.getMotorVoltage().getValueAsDouble();
    speeds.log(moduleId);
  }

  @Override
  public void applySpeeds(SimplyModuleSpeeds speeds) {
    this.speeds = speeds;
  }

  private double getShortestDistance(double angle1, double angle2) {
    double diff = ((angle2 % 360 + 360) % 360) - ((angle1 % 360 + 360) % 360);

    if (diff > 180) {
      return diff - 360;
    } else if (diff < -180) {
      return diff + 360;
    } else {
      return diff;
    }
  }
}
