package frc.robot.subsystems;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static edu.wpi.first.units.Units.Degrees;
 
 import frc.robot.Constants;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.util.ArrayList;
import java.util.List;
 
 public class Targeting extends SubsystemBase {
     private final PhotonCamera aprilTagCameraFront;

       List<PhotonTrackedTarget> cameraTargets;

       List<PhotonPipelineResult> cameraResults;

       PhotonPipelineResult latestResult;
 
     public Targeting() {
         aprilTagCameraFront = new PhotonCamera(Constants.Vision.kAprilTagCamera);
     }

  @Override
  public void periodic() {
    updateResults();
  }

  public Measure<Angle> getRotationOffset() {
    if (cameraTargets.isEmpty()) {
      return Degrees.of(0);
    }
    return Degrees.of(latestResult.getBestTarget().bestCameraToTarget.getRotation().toRotation2d().getDegrees());
  }

  /** Updates the local vision results variables */
  private void updateResults() {
    cameraResults = aprilTagCameraFront.getAllUnreadResults();
    latestResult = cameraResults.get(cameraResults.size() - 1);
    if (!cameraResults.isEmpty()) {
      latestResult = cameraResults.get(cameraResults.size() - 1);
    } else {
      latestResult = new PhotonPipelineResult();
    }
    if (latestResult.hasTargets()) {
      cameraTargets = latestResult.targets;
    } else {
      cameraTargets = new ArrayList<>();
    }
    SmartDashboard.putNumber("Robot Offset", getRotationOffset().magnitude());
  }
 }
 