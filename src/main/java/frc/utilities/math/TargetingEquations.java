// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.utilities.math;

import static edu.wpi.first.units.Units.Feet;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.units.measure.Distance;
import frc.utilities.util.TargetingTableMaker;

public class TargetingEquations {
    public static InterpolatingDoubleTreeMap[] map = TargetingTableMaker.generateShooterMap();

    public static double[] BestVelocityAndAngle(Distance distanceFromTarget) {
        return new double[] {map[0].get(distanceFromTarget.in(Feet)), map[1].get(distanceFromTarget.in(Feet))};
    }

    public static double BestAngle(Distance distanceFromTarget) {
        return map[1].get(distanceFromTarget.in(Feet));
    }

    public static double BestVelocity(Distance distanceFromTarget) {
        return map[0].get(distanceFromTarget.in(Feet));
    }
}
