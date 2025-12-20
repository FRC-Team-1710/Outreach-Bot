// package frc.robot.subsystems.simplySwerve;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.units.measure.Angle;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Commands;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// import static edu.wpi.first.units.Units.Degrees;

// import java.util.function.Supplier;
// import org.littletonrobotics.junction.Logger;

// public class SimplySwerve extends SubsystemBase {
//   private final SimplySwerveIO io;
//   private final SimplySwerveIOInputsAutoLogged inputs;

//   public SimplySwerve(SimplySwerveIO io) {
//     this.io = io;
//     this.inputs = new SimplySwerveIOInputsAutoLogged();
//   }

//   @Override
//   public void periodic() {
//     io.updateInputs(inputs);
//     // Logger.processInputs("SimplySwerve", inputs);
//   }

//   public Command run(Supplier<SimplySwerveRequest> requestSupplier) {
//     return Commands.run(() -> io.run(requestSupplier.get()), this);
//   }

//   public Pose2d getPose() {
//     return inputs.pose;
//   }

//   public Angle getPoseAngle() {
//     return Degrees.of(inputs.pose.getRotation().getDegrees());
//   }

//   public void setPose(Pose2d pose) {
//     inputs.pose = pose;
//   }

//   public void resetGyro() {
//     io.resetGyro();
//   }
// }
