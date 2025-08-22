package frc.robot.subsystems.simplySwerve;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.sim.Pigeon2SimState;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.SPI.Port;
import frc.robot.Constants;
import frc.robot.subsystems.simplySwerve.simplyModule.SimplyModule;
import frc.robot.subsystems.simplySwerve.simplyModule.SimplyModuleSpeeds;

public class SimplySwerveIOSIM implements SimplySwerveIO {
  private final SimplyModule[] modules;

  private SimplySwerveRequest request = new SimplySwerveRequest();

  private final Pigeon2 pigeon;
  private Pigeon2SimState pigeonSimState;

  private final SimplyKinematics kinematics;

  public SimplySwerveIOSIM(Translation2d[] translations, Port navxid, SimplyModule... modules) {
    this.modules = modules;
    this.kinematics = new SimplyKinematics(translations, this::getRobotAngle);
    this.pigeon = new Pigeon2(0);
  }

  @Override
  public void updateInputs(SimplySwerveIOInputs inputs) {
    pigeonSimState = pigeon.getSimState();
    pigeonSimState.setRawYaw(inputs.pose.getRotation().getDegrees());
    SimplyModuleSpeeds[] speeds = kinematics.getSpeeds(request);
    inputs.logableStates = new SwerveModuleState[speeds.length];
    for (int i = 0; i < speeds.length; i++) {
      modules[i].applySpeeds(speeds[i]);
      inputs.logableStates[i] =
          new SwerveModuleState(
              modules[i].getVelocity(), Rotation2d.fromDegrees(modules[i].getAngle().in(Degrees)));
    }
  }

  @Override
  public void run(SimplySwerveRequest request) {
    this.request = request;
  }

  private Angle getRobotAngle() {
    return pigeon.getYaw().getValue().plus(Degrees.of(Constants.redAlliance ? 180 : 0));
  }
}
