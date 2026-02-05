package frc.robot.subsystems.intake;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.NotLogged;
import edu.wpi.first.epilogue.Logged.Importance;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.Volts;

@Logged
public class IntakeIOCTRE implements IntakeIO {
  @Logged(name = "Motor", importance = Importance.INFO)
  private final TalonFX intake = new TalonFX(30);
  @Logged(name = "Motor2", importance = Importance.INFO)
  private final SparkMax intake2 = new SparkMax(50, MotorType.kBrushless);
  @Logged(name = "Motor3", importance = Importance.INFO)
  private final SparkMax intake3 = new SparkMax(51, MotorType.kBrushless);
  @Logged(name = "OverBumperL", importance = Importance.INFO)
  private final TalonFX overBumperL = new TalonFX(41);
  @Logged(name = "OverBumperF", importance = Importance.INFO)
  private final TalonFX overBumperF = new TalonFX(40);
  @Logged(name = "Beam", importance = Importance.INFO)
  private final DigitalInput breakingBeam = new DigitalInput(4);
  @NotLogged
  final StatusSignal<AngularVelocity> velocity = intake.getVelocity();
  @NotLogged
  final StatusSignal<Voltage> appliedVolts = intake.getMotorVoltage();
  @NotLogged
  final StatusSignal<Current> currentAmps = intake.getStatorCurrent();
  @NotLogged
  final StatusSignal<Angle> position = overBumperL.getPosition();
  @NotLogged
  final StatusSignal<Double> coolPosition = overBumperL.getClosedLoopReference();

  @Logged(name = "Timer", importance = Importance.INFO)
  public Timer timer = new Timer();

  private final MotionMagicVoltage positionVoltage = new MotionMagicVoltage(0).withSlot(1);

  TalonFXConfiguration config = new TalonFXConfiguration();

  public IntakeIOCTRE() {
    config.Audio.AllowMusicDurDisable = true;
    config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

    intake.getConfigurator().apply(config);

    config.Feedback.SensorToMechanismRatio = 18;
    config.Slot0.kP = 17;

    config.Slot1.kP = 0;
    config.Slot1.kI = 0;
    config.Slot1.kD = 0;
    config.Slot1.kS = 0;
    config.Slot1.kG = 0;
    config.Slot1.kV = 0;
    config.Slot1.kA = 0;
    config.Slot1.GravityType = GravityTypeValue.Arm_Cosine;
    config.MotionMagic.MotionMagicAcceleration = 0;
    config.MotionMagic.MotionMagicCruiseVelocity = 0;

    SmartDashboard.putNumber("kPP", 5);
    SmartDashboard.putNumber("kII", 0);
    SmartDashboard.putNumber("kDD", 0);
    SmartDashboard.putNumber("kSS", 0);
    SmartDashboard.putNumber("kGG", 0.25);
    SmartDashboard.putNumber("kVV", 3.125);
    SmartDashboard.putNumber("kAA", 0);
    SmartDashboard.putNumber("kAAAA", 1);
    SmartDashboard.putNumber("kVVVV", 0.75);

    overBumperL.getConfigurator().apply(config);
    overBumperF.getConfigurator().apply(config);

    BaseStatusSignal.setUpdateFrequencyForAll(50.0, velocity, appliedVolts, currentAmps, position, coolPosition);
    intake.optimizeBusUtilization();

    overBumperF.setControl(new Follower(41, true));

    overBumperL.setPosition(0);
    overBumperF.setPosition(0);
  }

  public void updateInputs(IntakeIOInputs inputs) {
    BaseStatusSignal.refreshAll(velocity, appliedVolts, currentAmps, position, coolPosition);

    inputs.velocity = velocity.getValue();
    inputs.appliedVolts = appliedVolts.getValue();
    inputs.currentAmps = currentAmps.getValue();
    inputs.beamBroken = !breakingBeam.get();

    SmartDashboard.putNumber("OJFEOINFIOBN", coolPosition.getValueAsDouble());

    if (SmartDashboard.getNumber("kPP", 0) != config.Slot1.kP
    || SmartDashboard.getNumber("kII", 0) != config.Slot1.kI
    || SmartDashboard.getNumber("kDD", 0) != config.Slot1.kD
    || SmartDashboard.getNumber("kSS", 0) != config.Slot1.kS
    || SmartDashboard.getNumber("kGG", 0) != config.Slot1.kG
    || SmartDashboard.getNumber("kVV", 0) != config.Slot1.kV
    || SmartDashboard.getNumber("kAA", 0) != config.Slot1.kA
    || SmartDashboard.getNumber("kAAAA", 0) != config.MotionMagic.MotionMagicAcceleration
    || SmartDashboard.getNumber("kVVVV", 0) != config.MotionMagic.MotionMagicCruiseVelocity) {
      config.Slot1.kP = SmartDashboard.getNumber("kPP", 0);
      config.Slot1.kI = SmartDashboard.getNumber("kII", 0);
      config.Slot1.kD = SmartDashboard.getNumber("kDD", 0);
      config.Slot1.kS = SmartDashboard.getNumber("kSS", 0);
      config.Slot1.kG = SmartDashboard.getNumber("kGG", 0);
      config.Slot1.kV = SmartDashboard.getNumber("kVV", 0);
      config.Slot1.kA = SmartDashboard.getNumber("kAA", 0);
      config.MotionMagic.MotionMagicAcceleration = SmartDashboard.getNumber("kAAAA", 0);
      config.MotionMagic.MotionMagicCruiseVelocity = SmartDashboard.getNumber("kVVVV", 0);
      overBumperL.getConfigurator().apply(config);
      overBumperF.getConfigurator().apply(config);
    }
  }

  @Override
  public void setVoltage(Voltage volts) {
    intake.setVoltage(volts.in(Volts));
    intake2.setVoltage(-volts.in(Volts) / 2);
    intake3.setVoltage(volts.in(Volts) / 2);
  }

  @Override
  public void setAngle(Angle angle) {
    overBumperL.setControl(positionVoltage.withPosition(angle));
  }
}
