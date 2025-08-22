package frc.robot.subsystems.simplySwerve.simplyModule;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class SimplyModule extends SubsystemBase {
  private final SimplyModuleIO io;
  private final SimplyModuleIOInputsAutoLogged inputs;

  private final int moduleId;

  public SimplyModule(SimplyModuleIO io, int moduleId) {
    this.io = io;
    this.inputs = new SimplyModuleIOInputsAutoLogged();
    this.moduleId = moduleId;
    io.setModuleId(moduleId);
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("SimplyModule" + moduleId, inputs);
  }

  public void applySpeeds(SimplyModuleSpeeds speeds) {
    io.applySpeeds(speeds);
  }

  public Angle getAngle() {
    return inputs.rotation;
  }

  public LinearVelocity getVelocity() {
    return inputs.driveVelocity;
  }

  public LinearVelocity getLocation() {
    return inputs.driveVelocity;
  }

  public SimplyModuleState getModuleState() {
    return new SimplyModuleState()
        .withRotation(inputs.rotation)
        .withDriveVelocity(inputs.driveVelocity)
        .withWheelRotation(inputs.wheelRotation)
        .withRotationVelocity(inputs.rotationVelocity)
        .withDriveAcceleration(inputs.driveAcceleration)
        .withRotationAcceleration(inputs.rotationAcceleration);
  }
}
