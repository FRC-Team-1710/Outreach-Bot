package frc.robot.utils.loggers;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.epilogue.CustomLoggerFor;
import edu.wpi.first.epilogue.Epilogue;
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
        backend.log("StatorCurrent", spark.getOutputCurrent());
        backend.log("Position", spark.getEncoder().getPosition());
        backend.log("Velocity", spark.getEncoder().getVelocity());
        backend.log("Id", spark.getDeviceId());
    }
  }