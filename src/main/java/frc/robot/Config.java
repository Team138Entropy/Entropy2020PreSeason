/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import edu.wpi.first.wpilibj.Filesystem;

/**
 * Add your docs here.
 */
public class Config {
    Properties prop;
    Config(){
        reload();
    }

    void reload(){
        try (InputStream input = new FileInputStream(Filesystem.getDeployDirectory() + "/config.properties")) {
            prop = new Properties();

            // load a properties file
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    String getProp(String key){
        return prop.getProperty(key);
    }

    float getFloat(String key){
        return Float.parseFloat(getProp(key));
    }

    int getInt(String key){
        return Integer.parseInt(getProp(key));
    }

    boolean getBoolean(String key) {
        return Boolean.parseBoolean(getProp(key));
    }
}
