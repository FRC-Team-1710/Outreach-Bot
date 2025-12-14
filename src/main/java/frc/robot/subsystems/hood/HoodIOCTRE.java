package frc.robot.subsystems.hood;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.NotLogged;
import edu.wpi.first.epilogue.Logged.Importance;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants;

@Logged
public class HoodIOCTRE implements HoodIO {
  @Logged(name = "Motor", importance = Importance.INFO)
  private final TalonFX hood = new TalonFX(10);
  @NotLogged
  private final StatusSignal<Voltage> appliedVolts = hood.getMotorVoltage();
  @NotLogged
  private final StatusSignal<Current> currentAmps = hood.getStatorCurrent();
  @NotLogged
  private final StatusSignal<AngularVelocity> velocity = hood.getVelocity();
  @NotLogged
  private final StatusSignal<Angle> position = hood.getPosition();

  @Logged(name = "P", importance = Importance.DEBUG)
  private double positionP = 2;
  @Logged(name = "I", importance = Importance.DEBUG)
  private double positionI = 0;
  @Logged(name = "D", importance = Importance.DEBUG)
  private double positionD = 0;

  @NotLogged
  private final PositionVoltage request = new PositionVoltage(0).withSlot(0).withEnableFOC(true);

  public HoodIOCTRE() {
    var config = new TalonFXConfiguration();
    config.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
    config.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);
    config.Slot0.withKP(positionP);
    config.Slot0.withKI(positionI);
    config.Slot0.withKD(positionD);

    hood.getConfigurator().apply(config);

    BaseStatusSignal.setUpdateFrequencyForAll(20.0, position, velocity, appliedVolts, currentAmps);
    hood.optimizeBusUtilization();
  }

  public void updateInputs(HoodIOInputs inputs) {
    BaseStatusSignal.refreshAll(position, velocity, appliedVolts, currentAmps);

    inputs.setpoint = request.getPositionMeasure().div(Constants.Hood.ExtenderRatio);
    inputs.position = position.getValue().div(Constants.Hood.ExtenderRatio);
    inputs.velocity = velocity.getValue().div(Constants.Hood.ExtenderRatio);
    inputs.appliedVolts = appliedVolts.getValue();
    inputs.currentAmps = currentAmps.getValue();
  }

  @Override
  public void setAngle(Angle angle) {
    hood.setControl(request.withPosition(angle.times(Constants.Hood.ExtenderRatio)));
  }
}
