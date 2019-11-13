/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.mecanum;

import java.util.Arrays;

/**
 * Test mechanm wheel interface implementations.
 */
public class TestMech {
    
    public static void main(String[] args){
        Float[] speeds;
        if(args.length == 4){
            speeds = Arrays.stream(args).map(Float::parseFloat).toArray(Float[]::new);
        }else{
            // default to forward drive (when using smart mech)
            speeds = new Float[]{0f, 1f, 0f, 1f};
        }

        // you can also use new StupidMech();
        MechControl stick = new SmartMech();
        float[][] values = stick.getWheelSpeeds(speeds[0], speeds[1], speeds[2], speeds[3]);

        System.out.println("(" + values[0][0] + ")-###^###-(" + values[0][1] + ")");
        System.out.println("(" + values[1][0] + ")-###^###-(" + values[1][1] + ")");
    }
}
