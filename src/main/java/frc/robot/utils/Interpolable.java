package frc.robot.utils;

public interface Interpolable<T> {
  T interpolate(T other, double t);
}