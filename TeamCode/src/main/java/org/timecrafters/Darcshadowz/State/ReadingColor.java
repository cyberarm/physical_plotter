package org.timecrafters.Darcshadowz.State;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by Dylan on 12/2/2017.
 */

public class ReadingColor extends Config{

    private ColorSensor colorSensor;
    private Servo servoBlade;
    private Servo servoWinch;
    private TouchSensor touchSensorBlade;
    private TouchSensor touchSensorWinch;

    private String targetColor;
    private  long milliseconds;
    private  long targetMilliseconds;

    public ReadingColor(Engine engine, String _targetColor) {

        super(engine);
        targetColor = _targetColor;

        colorSensor = engine.hardwareMap.colorSensor.get("ColorSensor");
        servoBlade = engine.hardwareMap.servo.get("servoBlade");
        servoWinch = engine.hardwareMap.servo.get("servoWinch");
        touchSensorBlade = engine.hardwareMap.touchSensor.get("touchSensorBlade");
        touchSensorWinch = engine.hardwareMap.touchSensor.get("touchSensorWinch");
        if (colorSensor.blue() >= 0.4) {

        }
    }

    @Override
    public void init() {
        super.init();
        milliseconds = System.currentTimeMillis();
    }

    @Override
    public void exec() {
        super.exec();
//        servoWinch.setPosition(0.1);
//        pause(800);
//        servoWinch.setPosition(0);
//        servoBlade.setPosition(0.1);
//        while (!touchSensorBlade.isPressed()) {
//        }
//        servoBlade.setPosition(0);
//        servoWinch.setPosition(0.1);
//        pause(1200);
//        servoWinch.setPosition(0);

        boolean isblue = false;

        if (colorSensor.blue() >= 0.4) {

            isblue = true;
        }
        switch (targetColor) {
            case "blue":
                if (isblue) {
                    servoBlade.setPosition(-0.1);
                    pause(700);
                    servoBlade.setPosition(0.1);
                    while (!touchSensorBlade.isPressed()) {
                    }
                    servoBlade.setPosition(0);
                } else {
                    servoBlade.setPosition(0.1);
                    pause(700);
                    servoBlade.setPosition(-0.1);
                    while (!touchSensorBlade.isPressed()) {
                    }
                    servoBlade.setPosition(0);
                }
                break;
            case "red":
                break;
            default:

        }

        setFinished(true);
    }

    void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {}
    }
}