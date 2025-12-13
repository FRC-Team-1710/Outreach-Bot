package frc.robot.utils.loggers;

import edu.wpi.first.epilogue.CustomLoggerFor;
import edu.wpi.first.epilogue.Epilogue;
import edu.wpi.first.epilogue.Logged.Importance;
import edu.wpi.first.epilogue.logging.ClassSpecificLogger;
import edu.wpi.first.epilogue.logging.EpilogueBackend;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.utils.drivers.SwerveModule;

@CustomLoggerFor(SwerveModule.class)
  public class SwerveModuleLogger extends ClassSpecificLogger<SwerveModule> {
    public SwerveModuleLogger() {
      super(SwerveModule.class);
    }
  
    @Override
    public void update(EpilogueBackend backend, SwerveModule module) {
      backend.log("CurrentAngle", module.getCurrentAngle());
      backend.log("CurrentVelocity", module.getCurrentVelocity());

      backend.log("TargetVelocity", module.getTargetVelocity().length);
      backend.log("TargetAngle", module.getTargetVelocity().getAngle().toDegrees());
    }
  }