package frc.robot.subsystems.intake;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class IntakeIOSIM implements IntakeIO {
  private DCMotorSim sim =
      new DCMotorSim(
          LinearSystemId.createDCMotorSystem(DCMotor.getFalcon500(1), 0.004, 1), DCMotor.getFalcon500(1));

  private Voltage appliedVolts = Volts.of(0);

  @Override
  public void updateInputs(IntakeIOInputs inputs) {
    sim.setInputVoltage(appliedVolts.in(Volts));
    sim.update(0.02);

    inputs.appliedVolts = appliedVolts;
    inputs.currentAmps = Amps.of(sim.getCurrentDrawAmps());
    inputs.velocity = sim.getAngularVelocity();
  }

  @Override
  public void setVoltage(Voltage volts) {
    appliedVolts = volts;
  }
}
