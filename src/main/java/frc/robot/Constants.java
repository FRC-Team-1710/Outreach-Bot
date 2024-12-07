// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final double triggerDeadband = 0.07; // TODO change this
  public static final double controllerRumble = 0.75;

  public static final class Intaker {
    public static final double IntakeSpeed = 0.5; // TODO change this
    public static final double SuckSpeed = 0.25; // TODO change this
  }

  public static final class Arm { // EVERYTHING IS DEGREES
    public static final double ratio = 1 / 1; // TODO change this
    public static final double ArmUp = 0.0; // TODO change this
    public static final double ArmDown = 0.0; // TODO change this
    public static final double Offset = 0.0; // TODO change this
  }

  public static final class Shooter { // Angles are degrees
    public static final double shootSpeedRPM = 5000; // TODO change this
    public static final double idleSpeedRPM = 1000; // TODO change this
    public static final double bufferRPM = 4500; // TODO change this
    public static final double pidChange = 2000; // TODO change this
    public static final double feedPower = 0.5; // TODO change this
    public static final double Shootangle = 0.0; // TODO change this
    public static final double extenderRatio = 1 / 1; // TODO change this
    public static final double Offset = 0.0; // TODO change this
  }

  public static final class Swerve {
    public static final double DRIVETRAIN_MAX_SPEED = 1;
    public static final double DRIVETRAIN_SLOW_SPEED = 0.5;

    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR = 4; // CAN
    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_ENCODER = 2; // Analog
    public static final int DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR = 5; // CAN
    public static final double FLOffset = 0;

    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR = 2; // CAN
    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_ENCODER = 1; // Analog
    public static final int DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR = 3; // CAN
    public static final double FROffset = 0;

    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR = 6; // CAN
    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_ENCODER = 3; // Analog
    public static final int DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR = 7; // CAN
    public static final double BLOffset = 0;

    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR = 8; // CAN
    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_ENCODER = 4; // Analog
    public static final int DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR = 9; // CAN
    public static final double BROffset = 0;
}
}
