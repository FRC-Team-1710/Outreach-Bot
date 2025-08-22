// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.simplySwerve;

/** Add your docs here. */
public class SimplySwerveRequest {
  public RequestType requestType = RequestType.FIELD;
  public double x = 0;
  public double y = 0;
  public double rotation = 0;
  public double deadband = 0;

  public SimplySwerveRequest() {}

  public SimplySwerveRequest withRequestType(RequestType requestType) {
    this.requestType = requestType;
    return this;
  }

  public boolean isDriveZero() {
    return Math.abs(Math.sqrt((x * x) + (y * y))) <= deadband;
  }

  public boolean isRotationZero() {
    return Math.abs(rotation) <= deadband;
  }

  public SimplySwerveRequest withX(double x) {
    this.x = x;
    return this;
  }

  public SimplySwerveRequest withY(double y) {
    this.y = y;
    return this;
  }

  public SimplySwerveRequest withRotation(double rotation) {
    this.rotation = rotation;
    return this;
  }

  public SimplySwerveRequest withDeadband(double deadband) {
    this.deadband = deadband;
    return this;
  }

  public enum RequestType {
    FIELD(),
    ROBOT(),
    SYSID()
  }
}
