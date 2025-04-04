package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.Targeting;
import frc.utilities.math.Deadband;
import frc.utilities.math.TargetingEquations;

import static edu.wpi.first.units.Units.Degrees;

import java.util.function.DoubleSupplier;

public class Target extends Command {
  private final PIDController pid = new PIDController(0.025, 0, 0);

  
  private final DriveSubsystem m_drivetrainSubsystem;
  private final ShooterSubsystem shooter;
  private final IntakerSubsystem intake;
  private final Targeting targeting;
  
  private final DoubleSupplier m_translationXSupplier;
  private final DoubleSupplier m_translationYSupplier;
  private final DoubleSupplier m_rotationSupplier;
  
  private double MaxSpeed;
  
  public Target(
    DriveSubsystem drivetrainSubsystem,
    IntakerSubsystem intake,
    ShooterSubsystem shooter,
    Targeting targeting,
    DoubleSupplier translationXSupplier,
    DoubleSupplier translationYSupplier,
    DoubleSupplier rotationSupplier) {
      this.m_drivetrainSubsystem = drivetrainSubsystem;
      this.intake = intake;
      this.shooter = shooter;
      this.targeting = targeting;
      this.m_translationXSupplier = translationXSupplier;
      this.m_translationYSupplier = translationYSupplier;
      this.m_rotationSupplier = rotationSupplier;
      addRequirements(drivetrainSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pid.setP(SmartDashboard.getNumber("PIDP", 0));
    pid.setI(SmartDashboard.getNumber("PIDI", 0));
    pid.setD(SmartDashboard.getNumber("PIDD", 0));
    

    if (SmartDashboard.getBoolean("Slow Mode", true)) {
      MaxSpeed = Constants.Swerve.DRIVETRAIN_SLOW_SPEED;
    } else {
      MaxSpeed = Constants.Swerve.DRIVETRAIN_MAX_SPEED;
    }

    double forward = m_translationYSupplier.getAsDouble();
    // Filter out values less than 0.1
    forward = Deadband.deadband(forward, 0.1);
    // Square the forward stick but keep the sign
    forward = Math.copySign(Math.pow(forward, 2.0), forward)/4;

    double strafe = m_translationXSupplier.getAsDouble();
    // Filter out values less than 0.1
    strafe = Deadband.deadband(strafe, 0.1);
    // Square the strafe stick but keep the sign
    strafe = Math.copySign(Math.pow(strafe, 2.0), strafe)/4;

    double rotation;

    if (targeting.getRotationOffset().magnitude() == 180) {
      rotation = -m_rotationSupplier.getAsDouble();
      // Filter out values less than 0.15
      rotation = Deadband.deadband(rotation, 0.15);
      // Square the rotation stick but keep the sign
      rotation = Math.copySign(Math.pow(rotation, 2.0), rotation);
    } else {
      targeting.setCurrentGyro(m_drivetrainSubsystem.getGyro());
      rotation = -pid.calculate(m_drivetrainSubsystem.getGyro().in(Degrees), targeting.getRotationOffset().in(Degrees));
      SmartDashboard.putNumber("Robot Offset 2", m_drivetrainSubsystem.getGyro().in(Degrees)-targeting.getRotationOffset().in(Degrees));
    }

    shooter.setHoodPosition(TargetingEquations.BestAngle(targeting.getDistanceToTag()));

    shooter.setFlywheelVelocity(TargetingEquations.BestVelocity(targeting.getDistanceToTag()));

    if (targeting.isInRange() && shooter.IsUpToSpeed() && shooter.isAtAngle()) {
      intake.setInsideVel(Constants.Shooter.feedPower);
    }

    m_drivetrainSubsystem.drive(new Translation2d(forward, strafe).times(MaxSpeed), rotation, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setHoodPosition(Constants.Shooter.Offset);
    shooter.setFlywheelVelocity(Constants.Shooter.idleSpeedRPM);
    intake.setInsideVel(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
