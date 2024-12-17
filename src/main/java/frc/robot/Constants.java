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

//DRIVE CONSTANTS
    public static final double DRIVETRAIN_MAX_SPEED = 0.75;

    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR = 2; // CAN
    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_ENCODER = 0; // Analog
    public static final int DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR = 3; // CAN

    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR = 8; // CAN
    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_ENCODER = 3; // Analog
    public static final int DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR = 9; // CAN

    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR = 4; // CAN
    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_ENCODER = 1; // Analog
    public static final int DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR = 5; // CAN

    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR = 6; // CAN
    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_ENCODER = 2; // Analog
    public static final int DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR = 7; // CAN

    public static class OperatorConstants {
        public static final int kDriverControllerPort = 0;
      }

      public static class IDs {
        public static final int indexerMotorCanID = 30; //CAN

        public static final int hoodMotorCanID = 31; //CAN
        public static final int flywheelMotorCanID = 10; //CAN
        public static final int breakingBeamDigitalInput = 1; //Digital Input

        public static final int leftIntakeMotorCanID = 0; //CAN
        public static final int rightIntakeMotorCanID = 0; //CAN
        public static final int leftIntakeArmMotorCanID = 0; //CAN
        public static final int rightIntakeArmMotorCanID = 0; //CAN
      }
    
      public static class ShooterSubsystemsConstants {
        public static final double hoodMotorRatio = (20/1);
        public static final double hoodMotorMax = 40;
        public static final double hoodMotorMin = 5;
        public static final double flywheelMotorRatio = (1/1);
        public static final double flywheelShootSpeed = 200; //RPM
        public static final double flywheelShootPoint = 150; //RPM
        public static final double flywheelIdleSpeed = 0;
      }
    
      public static class IndexerSubsystemsConstants {
        public static final double indexerShootPower = 0.5;
        public static final double indexerIntakeThroughShooterPower = -0.5;
      }

      public static class IntakeSubsystemConstants {
        public static final double intakeArmMotorRatio = (1/1);
      }
}
