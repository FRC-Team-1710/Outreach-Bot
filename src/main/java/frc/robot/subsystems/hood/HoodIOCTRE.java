// package frc.robot.subsystems.hood;

// import com.ctre.phoenix6.BaseStatusSignal;
// import com.ctre.phoenix6.StatusSignal;
// import com.ctre.phoenix6.configs.TalonFXConfiguration;
// import com.ctre.phoenix6.controls.PositionVoltage;
// import com.ctre.phoenix6.hardware.TalonFX;
// import com.ctre.phoenix6.signals.InvertedValue;
// import com.ctre.phoenix6.signals.NeutralModeValue;

// import edu.wpi.first.units.measure.Angle;
// import edu.wpi.first.units.measure.AngularVelocity;
// import edu.wpi.first.units.measure.Current;
// import edu.wpi.first.units.measure.Voltage;
// import frc.robot.Constants;

// public class HoodIOCTRE implements HoodIO {
//   private final TalonFX hood = new TalonFX(10);
//   private final StatusSignal<Voltage> appliedVolts = hood.getMotorVoltage();
//   private final StatusSignal<Current> currentAmps = hood.getStatorCurrent();
//   private final StatusSignal<AngularVelocity> velocity = hood.getVelocity();
//   private final StatusSignal<Angle> position = hood.getPosition();

//   private double positionP = 2;
//   private double positionI = 0;
//   private double positionD = 0;

//   private final PositionVoltage request = new PositionVoltage(0).withSlot(0).withEnableFOC(true);

//   public HoodIOCTRE() {
//     var config = new TalonFXConfiguration();
//     config.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
//     config.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);
//     config.Slot0.withKP(positionP);
//     config.Slot0.withKI(positionI);
//     config.Slot0.withKD(positionD);

//     hood.getConfigurator().apply(config);

//     BaseStatusSignal.setUpdateFrequencyForAll(20.0, position, velocity, appliedVolts, currentAmps);
//     hood.optimizeBusUtilization();
//   }

//   public void updateInputs(HoodIOInputs inputs) {
//     BaseStatusSignal.refreshAll(position, velocity, appliedVolts, currentAmps);

//     inputs.setpoint = request.getPositionMeasure().div(Constants.Hood.ExtenderRatio);
//     inputs.position = position.getValue().div(Constants.Hood.ExtenderRatio);
//     inputs.velocity = velocity.getValue().div(Constants.Hood.ExtenderRatio);
//     inputs.appliedVolts = appliedVolts.getValue();
//     inputs.currentAmps = currentAmps.getValue();
//   }

//   @Override
//   public void setAngle(Angle angle) {
//     hood.setControl(request.withPosition(angle.times(Constants.Hood.ExtenderRatio)));
//   }
// }
