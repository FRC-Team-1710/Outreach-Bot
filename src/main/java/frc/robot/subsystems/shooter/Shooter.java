package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Mode;
import frc.robot.subsystems.shooter.ShooterIO.ShooterIOInputs;

import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Volts;

public class Shooter {
  private final ShooterIOInputs inputs;
  private final ShooterIO io;

  private ShooterStates currentState = ShooterStates.OFF;

  public Shooter(ShooterIO io) {
    this.io = io;
    this.inputs = new ShooterIOInputs();
    SmartDashboard.putNumber("FlywheelShootSpeed", Constants.Flywheel.ShootSpeedRPM);
    SmartDashboard.putNumber("FlywheelIdleSpeed", Constants.Flywheel.IdleSpeedRPM);
  }

  public void periodic() {
    io.updateInputs(inputs);
    // Logger.processInputs("Shooter", inputs);

    // Constants.Flywheel.ShootSpeedRPM = SmartDashboard.getNumber("FlywheelShootSpeed", 0);
    // Constants.Flywheel.ShootSpeedRPM = SmartDashboard.getNumber("FlywheelIdleSpeed", 0);

    switch (currentState) {
      case OFF:
        io.setVoltage(Volts.of(Constants.Flywheel.IdleSpeedRPM / 6380 * 12));
        break;
      case INTAKE:
        io.setVoltage(Volts.of(Constants.Flywheel.IntakeSpeed * -12));
        break;
      case SHOOT:
        io.setVoltage(Volts.of(Constants.Flywheel.ShootSpeedRPM / 6380 * 12));
        // io.setSpeed(RotationsPerSecond.of(Constants.Flywheel.ShootSpeedRPM/60));
        break;
    }
  }

  public enum ShooterStates {
    OFF(),
    SHOOT(),
    INTAKE()
  }

  public void setState(ShooterStates state) {
    this.currentState = state;
  }

  public boolean atSetpoint() {
    return inputs.velocity.isNear(RotationsPerSecond.of(Constants.Flywheel.ShootSpeedRPM / 60), RotationsPerSecond.of(500 / 60)) || (Constants.currentMode == Mode.SIM);
  }
}
