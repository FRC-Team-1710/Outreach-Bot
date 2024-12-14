package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmDaLog {
    public static void loggAll() {
        SmartDashboard.putString("Swerve Calibration/Value1", "https://docs.google.com/document/d/1-HPhrcYGxAi4Wp-iK5DzLxNZFJkpnPF0-LEoKPR5bMc/edit?tab=t.0");
        SmartDashboard.putString("Swerve Calibration/Value2", "Set all offsets to 0");
        SmartDashboard.putString("Swerve Calibration/Value3", "Deploy, don't enable");
        SmartDashboard.putString("Swerve Calibration/Value4", "Flip on its side");
        SmartDashboard.putString("Swerve Calibration/Value5", "Rotate wheels so they are parallel to the floor");
        SmartDashboard.putString("Swerve Calibration/Value6", "If front of robot is on your left, the gears are all facing up.");
        SmartDashboard.putString("Swerve Calibration/Value7", "If front of robot is on your right, the gears are all facing down.");
        SmartDashboard.putString("Swerve Calibration/Value8", "Look on the Swerve Calibration page for all motor angles");
        SmartDashboard.putString("Swerve Calibration/Value9", "Put those numbers into Constants");
        SmartDashboard.putString("Swerve Calibration/Value10", "Deploy and enable");
        SmartDashboard.putString("Swerve Calibration/Value11", "If any wheels not in line, add a negative to the angle offset");
        SmartDashboard.putString("Swerve Calibration/Value12", "If one spins the wrong direction, add 0.5 rotations to the offset");
        SmartDashboard.putBoolean("Slow Mode", false);
        SmartDashboard.putNumber("Total Current", 0);
        SmartDashboard.putNumber("Temps/Peak Temp", 0);
        SmartDashboard.putString("Case", "Unknown");

        SmartDashboard.putNumber("Left Joystick x", 0);
        SmartDashboard.putNumber("Left Joystick y", 0);
        SmartDashboard.putNumber("Rotation", 0);
        SmartDashboard.putNumber("Front Left", 0);
        SmartDashboard.putNumber("Front Right", 0);
        SmartDashboard.putNumber("Back Left", 0);
        SmartDashboard.putNumber("Back Right", 0);
        SmartDashboard.putNumber("Front Left Speed", 0);
        SmartDashboard.putNumber("Front Right Speed", 0);
        SmartDashboard.putNumber("Back Left Speed", 0);
        SmartDashboard.putNumber("Back Right Speed", 0);
        SmartDashboard.putNumber("Temps/Front/Right Angle", 0);
        SmartDashboard.putNumber("Temps/Front/Left Angle", 0);
        SmartDashboard.putNumber("Temps/Front/Right Drive", 0);
        SmartDashboard.putNumber("Temps/Front/Left Drive", 0);
        SmartDashboard.putNumber("Temps/Back/Right Angle", 0);
        SmartDashboard.putNumber("Temps/Back/Left Angle", 0);
        SmartDashboard.putNumber("Temps/Back/Right Drive", 0);
        SmartDashboard.putNumber("Temps/Back/Left Drive", 0);
        SmartDashboard.putNumber("Temps/Drivetrain Average", 0);
        SmartDashboard.putNumber("Drivetrain Current", 0);
        SmartDashboard.putNumber("Drive Speed (mph)", 0);

        SmartDashboard.putBoolean("Inside Beam Break", false);
        SmartDashboard.putNumber("Intake Left Current", 0);
        SmartDashboard.putNumber("Intake Right Current", 0);
        SmartDashboard.putNumber("Inside Intake Current", 0);
        SmartDashboard.putNumber("Temps/Intake L Temp. (Fahrenheit)", 0);
        SmartDashboard.putNumber("Temps/Intake R Temp. (Fahrenheit)", 0);
        SmartDashboard.putNumber("Temps/Feeder Temp. (Fahrenheit)", 0);

        SmartDashboard.putNumber("Over Pos P", 0);
        SmartDashboard.putNumber("Over Pos I", 0);
        SmartDashboard.putNumber("Over Pos D", 0);
        SmartDashboard.putBoolean("Over Bumper Coast", false);
        SmartDashboard.putBoolean("Over Bumper Intake Enabled", false);
        SmartDashboard.putBoolean("Arm up", false);
        SmartDashboard.putBoolean("Over Bumper Zeroed", false);
        SmartDashboard.putNumber("Arm Left Current", 0);
        SmartDashboard.putNumber("Arm Right Current", 0);
        SmartDashboard.putNumber("Over Bumper Current Position (Degrees)", 0);
        SmartDashboard.putNumber("Temps/Arm L Temp. (Fahrenheit)", 0);
        SmartDashboard.putNumber("Temps/Arm R Temp. (Fahrenheit)", 0);

        SmartDashboard.putBoolean("Hood Coast", false);
        SmartDashboard.putBoolean("Shooter Enabled", true);
        SmartDashboard.putNumber("Flywheel Velocity Setpoint", 0);
        SmartDashboard.putBoolean("Is Flywheel Up To Speed", false);
        SmartDashboard.putNumber("Hood Setpoint (Degrees)", 0);
        SmartDashboard.putNumber("Flywheel Current", 0);
        SmartDashboard.putNumber("Hood Current", 0);
        SmartDashboard.putNumber("Temps/Flywheel Temp. (Fahrenheit)", 0);
        SmartDashboard.putNumber("Temps/Hood Temp. (Fahrenheit)", 0);
        SmartDashboard.putBoolean("Hood Zeroed", false);
        SmartDashboard.putNumber("Hood Current Position (Degrees)", 0);
        SmartDashboard.putNumber("Flywheel Current Velocity (RPM)", 0);
    }
}
