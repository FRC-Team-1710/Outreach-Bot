// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.units.measure.Angle;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final double triggerDeadband = 0.07;
  public static final double controllerRumble = 0.75;

  public static final class Vision {
    public static final String kAprilTagCamera = "ICanSeeYou";
    public static final Translation3d cameraToShooter = new Translation3d(0, -0.2, 0); // Meters
    public static final Angle tolarence = Degrees.of(1.5);
    public static final double[][] wideCam = new double[][] {
      new double[] {20, 18.3},
      new double[] {15, 13.5},
      new double[] {10, 9.6},
      new double[] {5, 5},
      new double[] {0, 0},
      new double[] {-20, -18.3},
      new double[] {-15, -13.5},
      new double[] {-10, -9.6},
      new double[] {-5, -5}
    };
  }
  
  public static final class Intaker {
    public static final double IntakeSpeed = 0.5;
    public static final double SuckSpeed = 0.5;
  }
  
  public static final class Arm {
    public static final double ratio = 1 / 1;
    public static final double ArmUp = 0.0;
    public static final double ArmDown = 0.0;
    public static final double Offset = 0.0;
  }
  
  public static final class Shooter { // Angles are degrees
    public static final double shootSpeedRPM = 2000;
    public static double fastShootSpeedRPM = 3000;
    public static final double idleSpeedRPM = 0;
    public static final double intakeSpeedRPM = 1500;
    public static final double bufferRPM = 750;
    public static final double feedPower = 0.5;
    public static final double Shootangle = 25;
    public static final double extenderRatio = 20 / 1;
    public static final double Offset = 5;


    /** Target velocity and hood angle constants. Feet, Velocity, Angle */
    public static final double[][] velandang = new double[][] {
      new double[] {100, 2000, 40},
      // new double[] {0, 0, 0},
      // new double[] {0, 0, 0},
      // new double[] {0, 0, 0},
      // new double[] {0, 0, 0},
      // new double[] {0, 0, 0},
      // new double[] {0, 0, 0},
      // new double[] {0, 0, 0},
      // new double[] {0, 0, 0},
      new double[] {0, 2000, 40}
    };
  }

  public static final class Swerve {
    public static final double DRIVETRAIN_MAX_SPEED = 1;
    public static final double DRIVETRAIN_SLOW_SPEED = 0.5;

    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR = 2; // CAN
    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_ENCODER = 0; // Analog
    public static final int DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR = 3; // CAN
    public static final double FLOffset = 0;

    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR = 4; // CAN
    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_ENCODER = 1; // Analog
    public static final int DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR = 5; // CAN
    public static final double FROffset = 0;

    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR = 6; // CAN
    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_ENCODER = 2; // Analog
    public static final int DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR = 7; // CAN
    public static final double BLOffset = 0;

    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR = 8; // CAN
    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_ENCODER = 3; // Analog
    public static final int DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR = 9; // CAN
    public static final double BROffset = 0;
  }
}
