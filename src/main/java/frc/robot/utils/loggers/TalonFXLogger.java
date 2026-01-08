package frc.robot.utils.loggers;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.epilogue.CustomLoggerFor;
import edu.wpi.first.epilogue.Logged.Importance;
import edu.wpi.first.epilogue.logging.ClassSpecificLogger;
import edu.wpi.first.epilogue.logging.EpilogueBackend;
import edu.wpi.first.wpilibj2.command.button.Trigger;

@CustomLoggerFor(TalonFX.class)
  public class TalonFXLogger extends ClassSpecificLogger<TalonFX> {
    public TalonFXLogger() {
      super(TalonFX.class);
    }
  
    @Override
    public void update(EpilogueBackend backend, TalonFX talon) {
      backend.log("Id", talon.getDeviceID());
      backend.log("ClosedLoopError", talon.getClosedLoopError().getValueAsDouble());
      backend.log("IsProLicensed", talon.getIsProLicensed().getValueAsDouble());
      backend.log("MotorVoltage", talon.getMotorVoltage().getValueAsDouble());
      backend.log("Velocity(rps)", talon.getVelocity().getValueAsDouble());
      backend.log("StatorCurrent(amps)", talon.getStatorCurrent().getValueAsDouble());
      backend.log("Position(rotations)", talon.getPosition().getValueAsDouble());
    }
  }