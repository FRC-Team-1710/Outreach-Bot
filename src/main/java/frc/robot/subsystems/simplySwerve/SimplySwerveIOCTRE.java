package frc.robot.subsystems.simplySwerve;

import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.SPI.Port;
import frc.robot.Constants;
import frc.robot.subsystems.simplySwerve.simplyModule.SimplyModule;
import frc.robot.subsystems.simplySwerve.simplyModule.SimplyModuleSpeeds;
import frc.robot.utils.drivers.NavX;

public class SimplySwerveIOCTRE implements SimplySwerveIO {
  private final NavX navx;

  private final SimplyModule[] modules;

  private final SimplyKinematics kinematics;

  private SimplySwerveRequest request = new SimplySwerveRequest();
  
  public SimplySwerveIOCTRE(Translation2d[] translations, Port navxid, SimplyModule... modules) {
    this.navx = new NavX(navxid);
    this.modules = modules;
    this.kinematics = new SimplyKinematics(translations, this::getRobotAngle);
  }

  @Override
  public void updateInputs(SimplySwerveIOInputs inputs) {
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

  @Override
  public void resetGyro() {
    navx.setAdjustmentAngle(navx.getUnadjustedAngle());
  }

  private Angle getRobotAngle() {
    return Degrees.of(navx.getAngle().toDegrees()).plus(Degrees.of(Constants.redAlliance ? 180 : 0));
  }
}
