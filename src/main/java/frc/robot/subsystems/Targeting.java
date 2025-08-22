// package frc.robot.subsystems;

// import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
// import edu.wpi.first.units.measure.Angle;
// import edu.wpi.first.units.measure.Distance;
// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// import static edu.wpi.first.units.Units.Degrees;
// import static edu.wpi.first.units.Units.Feet;
// import static edu.wpi.first.units.Units.Inches;
// import static edu.wpi.first.units.Units.Meters;

// import frc.robot.Constants;
// import frc.utils.util.TargetingTableMaker;

// import org.photonvision.PhotonCamera;
// import org.photonvision.targeting.PhotonPipelineResult;
// import org.photonvision.targeting.PhotonTrackedTarget;

// import java.util.ArrayList;
// import java.util.List;

// public class Targeting extends SubsystemBase {
//   private final PhotonCamera aprilTagCameraFront;

//   private final InterpolatingDoubleTreeMap map = TargetingTableMaker.generateTargetingMap();

//   private Angle gyro = Degrees.of(0);
//   private Angle currentAngle = Degrees.of(0);

//   private Timer timer = new Timer();

//   List<PhotonTrackedTarget> cameraTargets;

//   List<PhotonPipelineResult> cameraResults;

//   PhotonPipelineResult latestResult;

//   public Targeting() {
//     aprilTagCameraFront = new PhotonCamera(Constants.Vision.kAprilTagCamera);
//     latestResult = new PhotonPipelineResult();
//     cameraTargets = new ArrayList<>();
//     SmartDashboard.putNumber("PIDP", 0.025);
//     SmartDashboard.putNumber("PIDI", 0);
//     SmartDashboard.putNumber("PIDD", 0);
//   }

//   @Override
//   public void periodic() {
//     updateResults();
//   }

//   public Angle getRotationOffset() {
//     if (!cameraTargets.isEmpty()) {
//       currentAngle = Degrees.of((gyro.in(Degrees)-map.get(cameraTargets.get(0).getYaw())));
//       SmartDashboard.putNumber("Viewed angle", cameraTargets.get(0).getYaw());
//     }
//     return currentAngle;
//     //return Degrees.of(cameraTargets.get(0).getBestCameraToTarget().getTranslation().toTranslation2d().getAngle().getDegrees());
//   }

//   public Distance getDistanceToTag() {
//     if (cameraTargets.isEmpty()) {
//       return Feet.of(-1);
//     }
//     return Meters.of(cameraTargets.get(0).getBestCameraToTarget().getTranslation().getDistance(Constants.Vision.cameraToShooter));
//   }

//   public void setCurrentGyro(Angle gyro) {
//     this.gyro = gyro;
//   }

//   public boolean isInRange() {
//     if (cameraTargets.isEmpty()) {
//       return false;
//     }
//     return Degrees.of(map.get(cameraTargets.get(0).getYaw())).isNear(Degrees.of(0), Constants.Vision.tolarence);
//     //return Degrees.of(cameraTargets.get(0).getBestCameraToTarget().getTranslation().toTranslation2d().getAngle().getDegrees()).isNear(Degrees.of(0), Constants.Vision.tolarence);
//   }

//   /** Updates the local vision results variables */
//   private void updateResults() {
//     cameraResults = aprilTagCameraFront.getAllUnreadResults();
//     if (!cameraResults.isEmpty()) {
//       latestResult = cameraResults.get(cameraResults.size() - 1);
//     } else {
//       latestResult = new PhotonPipelineResult();
//     }
//     if (latestResult.hasTargets()) {
//       cameraTargets = latestResult.targets;
//     } else {
//     }
//     SmartDashboard.putBoolean("Is Robot In Range", isInRange());
//     SmartDashboard.putNumber("Target Distance", getDistanceToTag().in(Inches));
//   }
// }
