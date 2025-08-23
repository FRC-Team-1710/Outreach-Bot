package frc.robot.subsystems.hood;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class HoodIOSIM implements HoodIO {
  private DCMotorSim sim =
      new DCMotorSim(
          LinearSystemId.createDCMotorSystem(DCMotor.getFalcon500(1), 0.004, Constants.Hood.ExtenderRatio), DCMotor.getFalcon500(1));

  private Voltage appliedVolts = Volts.of(0);

  private PIDController controller = new PIDController(0, 0, 0);

  public HoodIOSIM() {
    SmartDashboard.putNumber("kP", controller.getP());
    SmartDashboard.putNumber("kI", controller.getI());
    SmartDashboard.putNumber("kD", controller.getD());
  }

  @Override
  public void updateInputs(HoodIOInputs inputs) {
    sim.setInputVoltage(controller.calculate(sim.getAngularPosition().in(Degrees)));
    sim.update(0.02);

    inputs.setpoint = Degrees.of(controller.getSetpoint());
    inputs.position = sim.getAngularPosition();
    inputs.appliedVolts = appliedVolts;
    inputs.currentAmps = Amps.of(sim.getCurrentDrawAmps());
    inputs.velocity = sim.getAngularVelocity();

    controller.setP(SmartDashboard.getNumber("kP", controller.getP()));
    controller.setI(SmartDashboard.getNumber("kI", controller.getI()));
    controller.setD(SmartDashboard.getNumber("kD", controller.getD()));
  }

  @Override
  public void setAngle(Angle angle) {
    controller.setSetpoint(angle.in(Degrees));
  }
}
