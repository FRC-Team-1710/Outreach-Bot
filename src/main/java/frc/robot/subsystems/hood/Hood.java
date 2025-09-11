package frc.robot.subsystems.hood;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Mode;

import static edu.wpi.first.units.Units.Degrees;

import org.littletonrobotics.junction.Logger;

public class Hood extends SubsystemBase {
  private final HoodIOInputsAutoLogged inputs;
  private final HoodIO io;

  private HoodStates currentState = HoodStates.IDLE;

  public Hood(HoodIO io) {
    this.io = io;
    this.inputs = new HoodIOInputsAutoLogged();
    SmartDashboard.putNumber("ShootAngle", Constants.Hood.ShootAngle);
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Hood", inputs);

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
    return inputs.position.isNear(inputs.setpoint, Degrees.of(1.5)) || (Constants.currentMode == Mode.SIM);
  }
}
