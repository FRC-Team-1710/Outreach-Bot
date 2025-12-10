package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

public final class Constants {
  public static boolean redAlliance = false;
  
  public static final class Intake {
    public static final double IntakeSpeed = 0.5;
    public static final double FeedSpeed = 0.5;
  }
  
  public static final class Hood {
    public static final double Offset = 5;
    public static double ShootAngle = 30;
    public static final double ExtenderRatio = 20 / 1;
  }
  
  public static final class Flywheel {
    public static double ShootSpeedRPM = 3500;
    public static double IdleSpeedRPM = 0;
    public static final double BufferRPM = 500;
    public static final double IntakeSpeed = 0.25;
  }

  public static final class Swerve {
    public static final double DRIVETRAIN_MAX_SPEED = 1;
    public static final double DRIVETRAIN_SLOW_SPEED = 0.5;

    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR = 2;
    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_ENCODER = 0;
    public static final int DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR = 3;
    public static final double FLOffset = 0;

    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR = 4;
    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_ENCODER = 1;
    public static final int DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR = 5;
    public static final double FROffset = 0;

    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR = 6;
    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_ENCODER = 2;
    public static final int DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR = 7;
    public static final double BLOffset = 0;

    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR = 8;
    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_ENCODER = 3;
    public static final int DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR = 9;
    public static final double BROffset = 0;
  }

  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  public static enum Mode {
    REAL,
    SIM,
    REPLAY
  }
}
