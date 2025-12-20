package frc.robot.subsystems.hood;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Mode;
import frc.robot.subsystems.hood.HoodIO.HoodIOInputs;

import static edu.wpi.first.units.Units.Degrees;

public class Hood {
  private final HoodIOInputs inputs;
  private final HoodIO io;

  private HoodStates currentState = HoodStates.IDLE;

  public Hood(HoodIO io) {
    this.io = io;
    this.inputs = new HoodIOInputs();
    SmartDashboard.putNumber("ShootAngle", Constants.Hood.ShootAngle);
  }

  public void periodic() {
    io.updateInputs(inputs);
    // Logger.processInputs("Hood", inputs);

    Constants.Hood.ShootAngle = SmartDashboard.getNumber("ShootAngle", 0);

    switch (currentState) {
      case IDLE:
        io.setAngle(Degrees.of(Constants.Hood.Offset));
        break;
      case SHOOT:
        io.setAngle(Degrees.of(Constants.Hood.ShootAngle));
        break;
    }
  }

  public enum HoodStates {
    IDLE(),
    SHOOT()
  }

  public void setState(HoodStates state) {
    this.currentState = state;
  }

  public boolean atSetpoint() {
    return inputs.position.isNear(inputs.setpoint, Degrees.of(2)) || (Constants.currentMode == Mode.SIM);
  }
}
