/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.mecanum;


/**
 * See also https://www.roboteq.com/images/article-images/frontpage/wheel-rotations.jpg and https://hackster.imgix.net/uploads/attachments/269553/mecanumpatent_cW82CEGNkw.png?auto=compress%2Cformat
 * See https://www.roboteq.com/index.php/applications/applications-blog/entry/driving-mecanum-wheels-omnidirectional-robots and https://i.stack.imgur.com/HLqoR.png for math
 */
public class SmartMech implements MechControl {
    public SmartMech(){
        // ???
    }

    /**
     * This function converts a float from -1 to 1 for the x values of 2 sticks and determines a factor in the direction that the wheels should be driven.
     * This allows, for instance, a neutral stick 1 + a right stick 2 to drive it to the right at 1/2 speed.
     * @param stickX1 The X-axis value of the left joystick (scale from -1 to 1)
     * @param stickX2 The X-axis value of the right joystick (scale from -1 to 1)
     * @return A float from -1 to 1
     */
    public float convertSidewaysToOutputFactors(float stickX1, float stickX2){
        float sum = (stickX1 + stickX2) / 2;
        return sum;
    }

    
    @Override
    /**
     * 
     * @param stickX1 The X-axis value of the left joystick (scale from -1 to 1)
     * @param stick1Y The Y-axis value of the left joystick (scale from -1 to 1)
     * @param stickX2 The X-axis value of the right joystick (scale from -1 to 1)
     * @param stick2Y The Y-axis value of the right joystick (scale from -1 to 1)
     * @return A 2d array of the speed values (on a scale of -1 to 1, with -1 being full speed reverse) for the wheels of the robot.
     *  [0][x] is the top row and [1][x] is the bottom row.
     *  [x][0] is the left side and [x][1] is the right side.
     */
    public float[][] getWheelSpeeds(float stick1X, float stick1Y, float stick2X, float stick2Y){
        //TODO: We don't use 100% of our power when both sticks are pushed forwards.
        
        float[][] driveValues = {{0, 0}, {0, 0}};
        // System.out.println(Arrays.deepToString(driveValues));

        // ## FORWARD DRIVE ##
        // add the values for the forwards drive controls
        // front row
        driveValues[0][0] += stick1Y;// left wheel
        driveValues[0][1] += stick2Y;// right wheel

        // back row
        driveValues[1][0] += stick1Y;// left wheel
        driveValues[1][1] += stick2Y;// right wheel

        // ## STRAFE DRIVE ##
        // we get the sideways factor from the strafe movement
        // then it is added to the upper-left and the lower-right
        // and subtracted from lower-left and upper-right
        float sidewaysVector = convertSidewaysToOutputFactors(stick1X, stick2X);
        driveValues[0][0] += sidewaysVector;
        driveValues[0][1] -= sidewaysVector;
        driveValues[1][0] -= sidewaysVector;
        driveValues[1][1] += sidewaysVector;

        // ## DIAGONAL DRIVE ##
        // this is included as a product of the combination of forward drive and strafe drive

        // ## SPIN DRIVE ##
        // this is included as a product of the combination of forward drive and strafe drive

        // ## ADJUSTMENTS ##
        // the values are currently on a scale of -2 to 2, so we scale that back down to -1 to 1
        driveValues[0][0] /= 2;
        driveValues[0][1] /= 2;
        driveValues[1][0] /= 2;
        driveValues[1][1] /= 2;

        return driveValues;
    }
}
