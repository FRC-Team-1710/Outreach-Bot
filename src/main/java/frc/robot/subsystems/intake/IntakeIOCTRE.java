// package frc.robot.subsystems.intake;

// import com.ctre.phoenix6.BaseStatusSignal;
// import com.ctre.phoenix6.StatusSignal;
// import com.ctre.phoenix6.configs.TalonFXConfiguration;
// import com.ctre.phoenix6.hardware.TalonFX;
// import com.ctre.phoenix6.signals.InvertedValue;
// import com.ctre.phoenix6.signals.NeutralModeValue;
// import edu.wpi.first.units.measure.AngularVelocity;
// import edu.wpi.first.units.measure.Current;
// import edu.wpi.first.units.measure.Voltage;
// import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.Timer;

// import static edu.wpi.first.units.Units.Volts;

// public class IntakeIOCTRE implements IntakeIO {
//   private final TalonFX intake = new TalonFX(30);
//   private final DigitalInput breakingBeam = new DigitalInput(4);
//   final StatusSignal<AngularVelocity> velocity = intake.getVelocity();
//   final StatusSignal<Voltage> appliedVolts = intake.getMotorVoltage();
//   final StatusSignal<Current> currentAmps = intake.getStatorCurrent();

//   public Timer timer = new Timer();

//   public IntakeIOCTRE() {
//     var config = new TalonFXConfiguration();
//     config.Audio.AllowMusicDurDisable = true;
//     config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
//     config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

//     intake.getConfigurator().apply(config);

//     BaseStatusSignal.setUpdateFrequencyForAll(20.0, velocity, appliedVolts, currentAmps);
//     intake.optimizeBusUtilization();
//   }

//   public void updateInputs(IntakeIOInputs inputs) {
//     BaseStatusSignal.refreshAll(velocity, appliedVolts, currentAmps);

//     inputs.velocity = velocity.getValue();
//     inputs.appliedVolts = appliedVolts.getValue();
//     inputs.currentAmps = currentAmps.getValue();
//     inputs.beamBroken = !breakingBeam.get();
//   }

//   @Override
//   public void setVoltage(Voltage volts) {
//     intake.setVoltage(volts.in(Volts));
//   }
// }
