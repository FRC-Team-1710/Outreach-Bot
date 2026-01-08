package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.NotLogged;
import edu.wpi.first.epilogue.Logged.Importance;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

import static edu.wpi.first.units.Units.Volts;

@Logged
public class ShooterIOCTRE implements ShooterIO {
  @Logged(name = "Motor", importance = Importance.INFO)
  private final TalonFX shooter = new TalonFX(31);
  @NotLogged
  private final StatusSignal<Voltage> appliedVolts = shooter.getMotorVoltage();
  @NotLogged
  private final StatusSignal<Current> currentAmps = shooter.getStatorCurrent();
  @NotLogged
  private final StatusSignal<AngularVelocity> velocity = shooter.getVelocity();

  @NotLogged
  private final VelocityVoltage request = new VelocityVoltage(0).withSlot(0).withEnableFOC(true);

  public ShooterIOCTRE() {
    var config = new TalonFXConfiguration();
    config.CurrentLimits.StatorCurrentLimitEnable = true;
    config.MotorOutput.withNeutralMode(NeutralModeValue.Coast);
    config.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);
    config.CurrentLimits.withStatorCurrentLimit(20);

    shooter.getConfigurator().apply(config);

    BaseStatusSignal.setUpdateFrequencyForAll(20.0, velocity, appliedVolts, currentAmps);
    shooter.optimizeBusUtilization();
  }

  public void updateInputs(ShooterIOInputs inputs) {
    BaseStatusSignal.refreshAll(velocity, appliedVolts, currentAmps);

    inputs.setpoint = request.getVelocityMeasure();
    inputs.velocity = velocity.getValue();
    inputs.appliedVolts = appliedVolts.getValue();
    inputs.currentAmps = currentAmps.getValue();
  }

  @Override
  public void setVoltage(Voltage volts) {
    shooter.setVoltage(volts.in(Volts));
  }

  @Override
  public void setSpeed(AngularVelocity velocity) {
    shooter.setControl(request.withVelocity(velocity));
  }
}
