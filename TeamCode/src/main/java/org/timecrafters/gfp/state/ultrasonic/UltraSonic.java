package org.timecrafters.gfp.state.ultrasonic;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by t420 on 11/16/2017.
 */

public class UltraSonic extends Config {

    private boolean runUntillDistance;
    private int runDistance;

    private int currentDistance;
    private double[] distances;

    private ModernRoboticsI2cRangeSensor sensor;

    private int filterLength;

    public UltraSonic(Engine engine, int readings){
        super(engine);
        distances = new double[readings];

        filterLength = distances.length - (int)(distances.length * 0.2)*2;
    }

    public void exec(){

        //Take readings to fill distances array
        for(int i = 0; i < distances.length; i ++){
            double distance = sensor.cmUltrasonic();

            if(distance > 0 && distance < 255) {
                distances[i] = distance;
            }
        }

        //Cut off bottom 20%
        for(int i = 0; i < distances.length*0.2; i ++){
            distances[i] = 404;
        }

        //Chop off top 20%
        for(int i = distances.length; i >= distances.length - (distances.length*0.2); i ++){
            distances[i] = 404;
        }

        //Average whats left
        double average = 0;
        int length = 0;
        for(int i = 0; i < distances.length; i ++){
            if(distances[i] != 404){
                average += distances[i];
                length++;
            }
        }

        average/=length;

        if(runUntillDistance){
            if(average <= runDistance){
                setFinished(true);
            }
        }
    }

    public void runUntillDistance(int runDistance){
        this.runDistance = runDistance;
        runUntillDistance = true;
    }

    public void setSensor(ModernRoboticsI2cRangeSensor sensor){
        this.sensor = sensor;
    }

}
