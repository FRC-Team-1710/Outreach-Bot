// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.utilities.util;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import frc.robot.Constants;

public class TargetingTableMaker {
    /** Returns map of hood angles and velocities, trust. map[0].get(foots) = velocity | map[1].get(foots) = angle */
    public static InterpolatingDoubleTreeMap[] generateMap() {
        InterpolatingDoubleTreeMap[] map = {new InterpolatingDoubleTreeMap(), new InterpolatingDoubleTreeMap()};
        double[][] constants = Constants.Shooter.velandang;
        for (int i = 0; i < constants.length; i++) {
            map[0].put(constants[i][0], constants[i][1]);
            map[1].put(constants[i][0], constants[i][2]);
        }
        return map;
    }
}
