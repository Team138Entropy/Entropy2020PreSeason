/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.mecanum;

/**
 * An interface representing a mecanum wheel control scheme.
 */
public interface MechControl {
    /**
     * Gets the speeds of each wheel given the supplied stick values.
     * MAKE SURE TO @Override THIS.
     * @param stickX1 The X-axis value of the left joystick (scale from -1 to 1)
     * @param stick1Y The Y-axis value of the left joystick (scale from -1 to 1)
     * @param stickX2 The X-axis value of the right joystick (scale from -1 to 1)
     * @param stick2Y The Y-axis value of the right joystick (scale from -1 to 1)
     * @return A 2d array of the speed values (on a scale of -1 to 1, with -1 being full speed reverse) for the wheels of the robot.
     *  [0][x] is the top row and [1][x] is the bottom row.
     *  [x][0] is the left side and [x][1] is the right side.
     */
    public float[][] getWheelSpeeds(float stick1X, float stick1Y, float stick2X, float stick2Y);
}
