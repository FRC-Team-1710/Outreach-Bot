package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.utilities.math.Deadband;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DriveCommand extends Command {

  private final DriveSubsystem m_drivetrainSubsystem;

  private final DoubleSupplier m_translationXSupplier;
  private final DoubleSupplier m_translationYSupplier;
  private final DoubleSupplier m_rotationSupplier;
  private final boolean isIntaking;

  private double MaxSpeed;

  public DriveCommand(
      DriveSubsystem drivetrainSubsystem,
      DoubleSupplier translationXSupplier,
      DoubleSupplier translationYSupplier,
      DoubleSupplier rotationSupplier,
      BooleanSupplier isItIntaking) {
    this.m_drivetrainSubsystem = drivetrainSubsystem;
    this.m_translationXSupplier = translationXSupplier;
    this.m_translationYSupplier = translationYSupplier;
    this.m_rotationSupplier = rotationSupplier;
    isIntaking = isItIntaking.getAsBoolean();
    addRequirements(drivetrainSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (SmartDashboard.getBoolean("Slow Mode", true)) {
      MaxSpeed = Constants.Swerve.DRIVETRAIN_SLOW_SPEED;
    } else {
      MaxSpeed = Constants.Swerve.DRIVETRAIN_MAX_SPEED;
    }

    double forward = m_translationYSupplier.getAsDouble();
    // Filter out values less than 0.1
    forward = Deadband.deadband(forward, 0.1);
    // Square the forward stick but keep the sign
    forward = Math.copySign(Math.pow(forward, 2.0), forward);

    double strafe = m_translationXSupplier.getAsDouble();
    // Filter out values less than 0.1
    strafe = Deadband.deadband(strafe, 0.1);
    // Square the strafe stick but keep the sign
    strafe = Math.copySign(Math.pow(strafe, 2.0), strafe);

    double rotation = m_rotationSupplier.getAsDouble();
    // Filter out values less than 0.15
    rotation = Deadband.deadband(rotation, 0.15);
    // Square the rotation stick but keep the sign
    rotation = Math.copySign(Math.pow(rotation, 2.0), rotation);

    if (!isIntaking) {
      //m_drivetrainSubsystem.drive(new Translation2d(forward, strafe).times(MaxSpeed), rotation, true);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
