package frc.robot.utils.loggers;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.epilogue.CustomLoggerFor;
import edu.wpi.first.epilogue.Logged.Importance;
import edu.wpi.first.epilogue.logging.ClassSpecificLogger;
import edu.wpi.first.epilogue.logging.EpilogueBackend;
import edu.wpi.first.wpilibj2.command.button.Trigger;

@CustomLoggerFor(SparkMax.class)
  public class SparkMaxLogger extends ClassSpecificLogger<SparkMax> {
    public SparkMaxLogger() {
      super(SparkMax.class);
    }
  
    @Override
    public void update(EpilogueBackend backend, SparkMax spark) {
        backend.log("AppliedDutyCycle", spark.getAppliedOutput());
        backend.log("StatorCurrent(amps)", spark.getOutputCurrent());
        backend.log("Position(rotations)", spark.getEncoder().getPosition());
        backend.log("Velocity(rpm)", spark.getEncoder().getVelocity());
        backend.log("Id", spark.getDeviceId());
    }
  }