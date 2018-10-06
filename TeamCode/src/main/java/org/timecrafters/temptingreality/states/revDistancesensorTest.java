package org.timecrafters.temptingreality.states;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**
 * Created by t420-1 on 7/11/2018.
 */

public class revDistancesensorTest extends State {
public DistanceSensor distanceSensor1;
public DistanceSensor distanceSensor2;
public DistanceSensor distanceSensor3;
public DistanceSensor distanceSensor4;
public boolean firstrun = true;
public double distanceHightZero = 0.0;
    public revDistancesensorTest(Engine engine)


    {
        this.engine = engine;

    }

    @Override
    public void init() {
        super.init();
        distanceSensor1 = engine.hardwareMap.get(DistanceSensor.class,"distance0");
        distanceSensor2 = engine.hardwareMap.get(DistanceSensor.class,"distance1");
        distanceSensor3 = engine.hardwareMap.get(DistanceSensor.class,"distance2");
        distanceSensor4 = engine.hardwareMap.get(DistanceSensor.class,"distance3");

    }

    @Override
    public void exec() {
        if (firstrun == true){
           distanceHightZero = (distanceSensor1.getDistance(DistanceUnit.MM)+distanceSensor2.getDistance(DistanceUnit.MM)+distanceSensor3.getDistance(DistanceUnit.MM)+distanceSensor4.getDistance(DistanceUnit.MM))/4;
           firstrun = false;
        }
    }
    public void telemetry() {
        engine.telemetry.addData("distance 1",distanceSensor1.getDistance(DistanceUnit.MM));
        engine.telemetry.addData("distance 2",distanceSensor2.getDistance(DistanceUnit.MM));
        engine.telemetry.addData("distance 3",distanceSensor3.getDistance(DistanceUnit.MM));
        engine.telemetry.addData("distance 4",distanceSensor4.getDistance(DistanceUnit.MM));
        engine.telemetry.addData("distance zero",distanceHightZero);
        engine.telemetry.addData("distance - hight",distanceHightZero-distanceSensor1.getDistance(DistanceUnit.MM));
        engine.telemetry.update();
    }
}