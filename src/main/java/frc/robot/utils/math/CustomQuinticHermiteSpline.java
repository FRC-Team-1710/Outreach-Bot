// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils.math;

public class CustomQuinticHermiteSpline {
  private double A, B, C, D, E, F;

  private double totalDeltaTime;

  /**
   * Constructs a QuinticHermiteSpline for 1D motion based on initial and final conditions.
   *
   * @param p0 Initial position.
   * @param v0 Initial velocity.
   * @param a0 Initial acceleration.
   * @param pf Final position.
   * @param vf Final velocity.
   * @param af Final acceleration.
   * @param deltaTime The total time duration for the spline segment.
   */
  public CustomQuinticHermiteSpline(
      double p0, double v0, double a0, double pf, double vf, double af, double deltaTime) {

    if (deltaTime <= 0) {
      throw new IllegalArgumentException("deltaTime must be positive. Time: " + deltaTime);
    }
    this.totalDeltaTime = deltaTime;

    // Calculate common powers of deltaTime
    double T2 = deltaTime * deltaTime;
    double T3 = T2 * deltaTime;
    double T4 = T3 * deltaTime;
    double T5 = T4 * deltaTime;

    F = p0;
    E = v0;
    D = a0 / 2.0;

    double R_P = pf - F - E * deltaTime - D * T2;
    double R_V = vf - E - 2 * D * deltaTime;
    double R_A = af - 2 * D;

    A = (6 * R_P / T5) - (3 * R_V / T4) + (0.5 * R_A / T3);
    B = (-15 * R_P / T4) + (7 * R_V / T3) - (1.5 * R_A / T2);
    C = (10 * R_P / T3) - (4 * R_V / T2) + (0.5 * R_A / deltaTime);
  }

  /**
   * Evaluates the position at a given time 't' along the spline.
   *
   * @param t The time at which to evaluate the position. Must be between 0 and totalDeltaTime.
   * @return The position at time 't'.
   */
  public double getPositionAtTime(double t) {
    if (t < 0 || t > totalDeltaTime) {
      throw new IllegalArgumentException("Time 't' must be within [0, totalDeltaTime].");
    }

    double t2 = t * t;
    double t3 = t2 * t;
    double t4 = t3 * t;
    double t5 = t4 * t;

    return A * t5 + B * t4 + C * t3 + D * t2 + E * t + F;
  }

  /**
   * Evaluates the velocity at a given time 't' along the spline.
   *
   * @param t The time at which to evaluate the velocity.
   * @return The velocity at time 't'.
   */
  public double getVelocityAtTime(double t) {
    if (t < 0 || t > totalDeltaTime) {
      throw new IllegalArgumentException("Time 't' must be within [0, totalDeltaTime].");
    }

    double t2 = t * t;
    double t3 = t2 * t;
    double t4 = t3 * t;

    return 5 * A * t4 + 4 * B * t3 + 3 * C * t2 + 2 * D * t + E;
  }

  /**
   * Evaluates the acceleration at a given time 't' along the spline.
   *
   * @param t The time at which to evaluate the acceleration.
   * @return The acceleration at time 't'.
   */
  public double getAccelerationAtTime(double t) {
    if (t < 0 || t > totalDeltaTime) {
      throw new IllegalArgumentException("Time 't' must be within [0, totalDeltaTime].");
    }

    double t2 = t * t;
    double t3 = t2 * t;

    return 20 * A * t3 + 12 * B * t2 + 6 * C * t + 2 * D;
  }
}
