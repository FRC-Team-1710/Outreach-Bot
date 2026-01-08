package frc.robot.subsystems.hood;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Constants.Mode;
import frc.robot.subsystems.hood.HoodIO.HoodIOInputs;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Seconds;

import edu.wpi.first.units.measure.Time;
import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.NotLogged;
import edu.wpi.first.epilogue.Logged.Importance;

@Logged
public class Hood {
  @Logged(name = "LastPeriod", importance = Importance.INFO)
  private Time lastPeriod = Seconds.of(0.02);
  @Logged(name = "Period", importance = Importance.CRITICAL)
  private Time period = Seconds.of(0.02);

  @Logged(name = "Inputs", importance = Importance.INFO)
  private final HoodIOInputs inputs;
  @Logged(name = "IO", importance = Importance.INFO)
  private final HoodIO io;

  @Logged(name = "CurrentState", importance = Importance.INFO)
  private HoodStates currentState = HoodStates.IDLE;

  public Hood(HoodIO io) {
    this.io = io;
    this.inputs = new HoodIOInputs();
    SmartDashboard.putNumber("ShootAngle", Constants.Hood.ShootAngle);
  }

  public void periodic() {
    io.updateInputs(inputs);

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

  @Logged(name = "PeriodChanged", importance = Importance.INFO)
  public boolean periodChanged() {
    if (period.in(Seconds) != lastPeriod.in(Seconds)) {
      lastPeriod = period;
      return true;
    }
    return false;
  }

  public void setPeriod(Time period) {
    this.period = period;
  }

  @NotLogged
  public Time getPeriod() {
    return period;
  }

  public enum HoodStates {
    IDLE(),
    SHOOT()
  }

  public void setState(HoodStates state) {
    this.currentState = state;
  }

  @Logged(name = "AtSetpoint", importance = Importance.INFO)
  public boolean atSetpoint() {
    return inputs.position.isNear(inputs.setpoint, Degrees.of(2)) || (Constants.currentMode == Mode.SIM);
  }
}
