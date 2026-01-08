package frc.robot.utils.loggers;

import edu.wpi.first.epilogue.CustomLoggerFor;
import edu.wpi.first.epilogue.Logged.Importance;
import edu.wpi.first.epilogue.logging.ClassSpecificLogger;
import edu.wpi.first.epilogue.logging.EpilogueBackend;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.utils.drivers.Gyroscope;

@CustomLoggerFor(Gyroscope.class)
  public class GyroLogger extends ClassSpecificLogger<Gyroscope> {
    public GyroLogger() {
      super(Gyroscope.class);
    }
  
    @Override
    public void update(EpilogueBackend backend, Gyroscope gyro) {
      backend.log("Rate(radps)", gyro.getRate());
      backend.log("Angle(degrees)", gyro.getAngle().toDegrees());
    }
  }