/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.mecanum;

/**
 * Add your docs here.
 */
public class StupidMech implements MechControl{
    public float[][] getWheelSpeeds(float stick1X, float stick1Y, float stick2X, float stick2Y){
        return new float[][]{
            new float[]{stick1X, stick2X}, 
            new float[]{stick1Y, stick2Y}
        };
    }
}
