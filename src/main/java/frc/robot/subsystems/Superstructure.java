// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.subsystems;

// import org.littletonrobotics.junction.Logger;

// import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Commands;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.subsystems.intake.Intake;
// import frc.robot.subsystems.intake.Intake.IntakeStates;
// import frc.robot.subsystems.hood.Hood;
// import frc.robot.subsystems.hood.Hood.HoodStates;
// import frc.robot.subsystems.shooter.Shooter;
// import frc.robot.subsystems.shooter.Shooter.ShooterStates;
// // import frc.robot.subsystems.simplySwerve.SimplySwerveRequest;
// // import frc.robot.subsystems.simplySwerve.SimplySwerveRequest.RequestType;
// import frc.robot.utils.TunableController;

// public class Superstructure extends SubsystemBase {
//   // private final SimplySwerve drive;
//   private final DriveSubsystem drive;
//   private final Intake intake;
//   private final Shooter shooter;
//   private final Hood hood;
//   private final TunableController driver;

//   // private final SimplySwerveRequest request = new SimplySwerveRequest()
//   //     .withRequestType(RequestType.FIELD)
//   //     .withDeadband(0.1);

//   private WantedState wantedState = WantedState.DEFAULT;
//   private CurrentState currentState = CurrentState.IDLE;

//   public Superstructure(DriveSubsystem drive,//SimplySwerve drive
//    Intake intake, Shooter shooter, Hood hood, TunableController driver) {
//     this.drive = drive;
//     this.intake = intake;
//     this.shooter = shooter;
//     this.hood = hood;
//     this.driver = driver;
//   }

//   @Override
//   public void periodic() {
//     currentState = handleStateTransitions();
//     applyStates();

//     // Logger.recordOutput("Superstructure/WantedState", wantedState);
//     // Logger.recordOutput("Superstructure/CurrentState", currentState);
//   }

//   private CurrentState handleStateTransitions() {
//     CurrentState newState = CurrentState.IDLE;
//     switch (wantedState) {
//       case DEFAULT:
//         newState = intake.ballSecured() ? CurrentState.BALL_IDLE : CurrentState.IDLE;
//         break;
//       case HOOD_UP:
//         newState = CurrentState.HOOD_UP;
//         break;
//       case PREP_SHOT:
//         newState = intake.ballSecured() ? CurrentState.PREP_SHOT : CurrentState.IDLE;
//         break;
//       case SHOOT:
//         newState = CurrentState.SHOOT;
//         break;
//       case INTAKE:
//         newState = CurrentState.INTAKE;
//         break;
//       case SHOOTER_INTAKE:
//         newState = CurrentState.SHOOTER_INTAKE;
//         break;
//       case MANUAL_OUTAKE:
//         newState = CurrentState.MANUAL_OUTAKE;
//         break;
//     }
//     return newState;
//   }

//   private void applyStates() {
//     switch (currentState) {
//       case IDLE:
//         idle();
//         break;
//       case BALL_IDLE:
//         ballIdle();
//         break;
//       case HOOD_UP:
//         hoodUp();
//         break;
//       case PREP_SHOT:
//         prepShot();
//         break;
//       case SHOOT:
//         shoot();
//         break;
//       case INTAKE:
//         intake();
//         break;
//       case SHOOTER_INTAKE:
//         shooterIntake();
//         break;
//       case MANUAL_OUTAKE:
//         manualOutake();
//         break;
//     }
//   }

//   private void idle() {
//     intake.setState(IntakeStates.OFF);
//     shooter.setState(ShooterStates.OFF);
//     hood.setState(HoodStates.IDLE);
//     applyDrive();
//   }

//   private void ballIdle() {
//     intake.setState(IntakeStates.OFF);
//     shooter.setState(ShooterStates.OFF);
//     hood.setState(HoodStates.IDLE);
//     applyDrive();
//   }

//   private void hoodUp() {
//     intake.setState(IntakeStates.OFF);
//     shooter.setState(ShooterStates.OFF);
//     hood.setState(HoodStates.SHOOT);
//     applyDrive();
//   }

//   private void prepShot() {
//     intake.setState(IntakeStates.OFF);
//     shooter.setState(ShooterStates.SHOOT);
//     hood.setState(HoodStates.SHOOT);
//     applyDrive();
//   }

//   private void shoot() {
//     shooter.setState(ShooterStates.SHOOT);
//     hood.setState(HoodStates.SHOOT);
//     intake.setState((shooter.atSetpoint() && hood.atSetpoint()) ? IntakeStates.FEED : IntakeStates.OFF);
//     applyDrive();
//   }

//   private void intake() {
//     intake.setState(IntakeStates.INTAKE);
//     shooter.setState(ShooterStates.OFF);
//     hood.setState(HoodStates.IDLE);
//     applyDrive();
//   }

//   private void shooterIntake() {
//     intake.setState(IntakeStates.SHOOTER_INTAKE);
//     shooter.setState(ShooterStates.INTAKE);
//     hood.setState(HoodStates.IDLE);
//     applyDrive();
//     if (intake.ballSecured()) {
//       setState(WantedState.DEFAULT);
//     }
//   }

//   private void manualOutake() {
//     intake.setState(IntakeStates.OUTTAKE);
//     shooter.setState(ShooterStates.OFF);
//     hood.setState(HoodStates.IDLE);
//     applyDrive();
//   }

//   private void applyDrive() {
//     // drive.run(() -> request
//     //     .withX(-driver.customLeft().getY())
//     //     .withY(-driver.customLeft().getX())
//     //     .withRotation(driver.customRight().getX())).schedule();
//     drive.drive(new Translation2d(-driver.customLeft().getY(), -driver.customLeft().getX()), driver.customRight().getX(), true);
//   }

//   private void setState(WantedState state) {
//     this.wantedState = state;
//   }

//   public Command setWantedState(WantedState state) {
//     return Commands.runOnce(() -> setState(state));
//   }

//   public enum WantedState {
//     DEFAULT,
//     HOOD_UP,
//     PREP_SHOT,
//     SHOOT,
//     INTAKE,
//     SHOOTER_INTAKE,
//     MANUAL_OUTAKE
//   }

//   public enum CurrentState {
//     IDLE,
//     BALL_IDLE,
//     HOOD_UP,
//     PREP_SHOT,
//     SHOOT,
//     INTAKE,
//     SHOOTER_INTAKE,
//     MANUAL_OUTAKE
//   }
// }
