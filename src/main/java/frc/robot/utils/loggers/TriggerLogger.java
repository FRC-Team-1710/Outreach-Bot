package frc.robot.utils.loggers;

import edu.wpi.first.epilogue.CustomLoggerFor;
import edu.wpi.first.epilogue.logging.ClassSpecificLogger;
import edu.wpi.first.epilogue.logging.EpilogueBackend;
import edu.wpi.first.wpilibj2.command.button.Trigger;

@CustomLoggerFor(Trigger.class)
  public class TriggerLogger extends ClassSpecificLogger<Trigger> {
    public TriggerLogger() {
      super(Trigger.class);
    }
  
    @Override
    public void update(EpilogueBackend backend, Trigger trigger) {
       backend.log("Activated", trigger.getAsBoolean());
    }
  }