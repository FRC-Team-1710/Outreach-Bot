// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Seconds;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.NotLogged;
import edu.wpi.first.epilogue.Logged.Importance;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Time;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Constants;
import frc.robot.utils.drivers.Gyroscope;
import frc.robot.utils.drivers.Mk2SwerveModuleBuilder;
import frc.robot.utils.drivers.NavX;
import frc.robot.utils.drivers.SwerveModule;
import frc.robot.utils.math.Vector2;

@Logged
public class DriveSubsystem {
  private Time lastPeriod = Seconds.of(0.02);
  private Time period = Seconds.of(0.02);
@Logged(name = "TrackWidth", importance = Importance.DEBUG)
  private static final double TRACKWIDTH = Units.inchesToMeters(23);
  @Logged(name = "TrackWidth", importance = Importance.DEBUG)
  private static final double WHEELBASE = Units.inchesToMeters(23);

  @Logged(name = "TrackWidth", importance = Importance.DEBUG)
  private static final double FRONT_LEFT_ANGLE_OFFSET = Math.toRadians(Constants.Swerve.FLOffset);
  @Logged(name = "TrackWidth", importance = Importance.DEBUG)
  private static final double FRONT_RIGHT_ANGLE_OFFSET = Math.toRadians(Constants.Swerve.FROffset);
  @Logged(name = "TrackWidth", importance = Importance.DEBUG)
  private static final double BACK_LEFT_ANGLE_OFFSET = Math.toRadians(Constants.Swerve.BLOffset);
  @Logged(name = "TrackWidth", importance = Importance.DEBUG)
  private static final double BACK_RIGHT_ANGLE_OFFSET = Math.toRadians(Constants.Swerve.BROffset);

  @Logged(name = "BackLeftAngle", importance = Importance.INFO)
  private SparkMax backLeftAngle = new SparkMax(Constants.Swerve.DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR,MotorType.kBrushless);
  @Logged(name = "BackRightAngle", importance = Importance.INFO)
  private SparkMax backRightAngle = new SparkMax(Constants.Swerve.DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR,MotorType.kBrushless);
  @Logged(name = "BackLeftDrive", importance = Importance.INFO)
  private TalonFX backLeftDrive = new TalonFX(Constants.Swerve.DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR, "rio");
  @Logged(name = "BackRightDrive", importance = Importance.INFO)
  private TalonFX backRightDrive = new TalonFX(Constants.Swerve.DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR, "rio");
  @Logged(name = "FrontLeftAngle", importance = Importance.INFO)
  private SparkMax frontLeftAngle = new SparkMax(Constants.Swerve.DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR,MotorType.kBrushless);
  @Logged(name = "FrontRightAngle", importance = Importance.INFO)
  private SparkMax frontRightAngle = new SparkMax(Constants.Swerve.DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR,MotorType.kBrushless);
  @Logged(name = "FrontLeftDrive", importance = Importance.INFO)
  private TalonFX frontLeftDrive = new TalonFX(Constants.Swerve.DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR, "rio");
  @Logged(name = "FrontRightDrive", importance = Importance.INFO)
  private TalonFX frontRightDrive = new TalonFX(Constants.Swerve.DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR, "rio");

  /** Front left swerve module object */
  @Logged(name = "FrontLeftModule", importance = Importance.INFO)
  private final SwerveModule frontLeftModule = new Mk2SwerveModuleBuilder(
      new Vector2(TRACKWIDTH / 2.0, WHEELBASE / 2.0))
      .angleEncoder(
          new AnalogInput(Constants.Swerve.DRIVETRAIN_FRONT_LEFT_ANGLE_ENCODER),
          FRONT_LEFT_ANGLE_OFFSET)
      .angleMotor(frontLeftAngle, Mk2SwerveModuleBuilder.MotorType.NEO)
      .driveMotor(frontLeftDrive, Mk2SwerveModuleBuilder.MotorType.FALCON_500)
      .build();
  /** Front right swerve module object */
  @Logged(name = "FrontRightModule", importance = Importance.INFO)
  private final SwerveModule frontRightModule = new Mk2SwerveModuleBuilder(
      new Vector2(TRACKWIDTH / 2.0, -WHEELBASE / 2.0))
      .angleEncoder(
          new AnalogInput(Constants.Swerve.DRIVETRAIN_FRONT_RIGHT_ANGLE_ENCODER),
          FRONT_RIGHT_ANGLE_OFFSET)
      .angleMotor(frontRightAngle, Mk2SwerveModuleBuilder.MotorType.NEO)
      .driveMotor(frontRightDrive, Mk2SwerveModuleBuilder.MotorType.FALCON_500)
      .build();
  /** Back left swerve module object */
  @Logged(name = "BackLeftModule", importance = Importance.INFO)
  private final SwerveModule backLeftModule = new Mk2SwerveModuleBuilder(
      new Vector2(-TRACKWIDTH / 2.0, WHEELBASE / 2.0))
      .angleEncoder(
          new AnalogInput(Constants.Swerve.DRIVETRAIN_BACK_LEFT_ANGLE_ENCODER),
          BACK_LEFT_ANGLE_OFFSET)
      .angleMotor(backLeftAngle, Mk2SwerveModuleBuilder.MotorType.NEO)
      .driveMotor(backLeftDrive, Mk2SwerveModuleBuilder.MotorType.FALCON_500)
      .build();
  /** Back right swerve module object */
  @Logged(name = "BackRightModule", importance = Importance.INFO)
  private final SwerveModule backRightModule = new Mk2SwerveModuleBuilder(
      new Vector2(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0))
      .angleEncoder(
          new AnalogInput(Constants.Swerve.DRIVETRAIN_BACK_RIGHT_ANGLE_ENCODER),
          BACK_RIGHT_ANGLE_OFFSET)
      .angleMotor(backRightAngle, Mk2SwerveModuleBuilder.MotorType.NEO)
      .driveMotor(backRightDrive, Mk2SwerveModuleBuilder.MotorType.FALCON_500)
      .build();

  /** Ratios for swerve calculations */
  @NotLogged
  public final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
      new Translation2d(TRACKWIDTH / 2.0, WHEELBASE / 2.0),
      new Translation2d(TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
      new Translation2d(-TRACKWIDTH / 2.0, WHEELBASE / 2.0),
      new Translation2d(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0));

      @Logged(name = "Gyro", importance = Importance.INFO)
  private final Gyroscope gyroscope = new NavX(SPI.Port.kMXP);

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    gyroscope.calibrate();
    gyroscope.setInverted(true); // You might not need to invert the gyro

    // Use addRequirements() here to declare subsystem dependencies.
    frontLeftModule.setName("Front Left");
    frontRightModule.setName("Front Right");
    backLeftModule.setName("Back Left");
    backRightModule.setName("Back Right");
  }

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

  public Time getPeriod() {
    return period;
  }

  public void periodic() {
    frontLeftModule.updateSensors();
    frontRightModule.updateSensors();
    backLeftModule.updateSensors();
    backRightModule.updateSensors();

    frontLeftModule.updateState(TimedRobot.kDefaultPeriod);
    frontRightModule.updateState(TimedRobot.kDefaultPeriod);
    backLeftModule.updateState(TimedRobot.kDefaultPeriod);
    backRightModule.updateState(TimedRobot.kDefaultPeriod);
  }

  @NotLogged
  public Angle getGyro() {
    return Degrees.of(gyroscope.getAngle().toDegrees());
  }

  /**
   * Method for controlling all modules
   *
   * <p>
   * The rotation value is multiplied by 2 and then divided by the hypotenuse of
   * the WHEELBASE
   * and TRACKWIDTH.
   * The speed is then calculated using the ChassisSpeeds class. Finally, the
   * speeds are put into an
   * array and set using {@link #setTargetVelocity(speed, angle)}.
   *
   * <p>
   * Also, the gyroscope is be reset here when the correct button is pressed
   *
   * @param translation   The forward and strafe values sent through the
   *                      Translation2d class
   * @param rotation      The rotation value.
   * @param fieldOriented Boolean value that determines whether field orientation
   *                      is used
   */
  public void drive(Translation2d translation, double rotation, boolean fieldOriented) {
    rotation *= 2.0 / Math.hypot(WHEELBASE, TRACKWIDTH);
    rotation *= .5;
    // SmartDashboard.putNumber("Left Joystick x", translation.getX());
    // SmartDashboard.putNumber("Left Joystick y", translation.getY());
    // SmartDashboard.putNumber("Rotation", rotation);

    ChassisSpeeds speeds;
    if (fieldOriented) {
      speeds = ChassisSpeeds.fromFieldRelativeSpeeds(
          translation.getX(),
          translation.getY(),
          rotation,
          Rotation2d.fromDegrees(gyroscope.getAngle().toDegrees()));
    } else {
      speeds = new ChassisSpeeds(-translation.getX(), -translation.getY(), rotation);
    }

    SwerveModuleState[] states = kinematics.toSwerveModuleStates(speeds);

    frontLeftModule.setTargetVelocity(states[0].speedMetersPerSecond, states[0].angle.getRadians());
    frontRightModule.setTargetVelocity(
        states[1].speedMetersPerSecond, states[1].angle.getRadians());
    backLeftModule.setTargetVelocity(states[2].speedMetersPerSecond, states[2].angle.getRadians());
    backRightModule.setTargetVelocity(states[3].speedMetersPerSecond, states[3].angle.getRadians());
  }

  public void resetGyroscope() {
    gyroscope.setAdjustmentAngle(gyroscope.getUnadjustedAngle());
  }
}
