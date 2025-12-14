package frc.robot.utils.loggers;

import edu.wpi.first.epilogue.CustomLoggerFor;
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
      backend.log("CurrentAngle(rads)", module.getCurrentAngle());

      backend.log("TargetVelocity(mps)", module.getTargetVelocity().length);
      backend.log("TargetAngle(deg)", module.getTargetVelocity().getAngle().toDegrees());
    }
  }