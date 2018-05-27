package org.container;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

public abstract class Actor {
    protected Container container;
    protected HardwareMap hardware;
    protected boolean isSleeping, isCompleted = false;
    protected long wakeupTime;

    public Actor() {
        container= ContainerGlobal.activeContainer;
        hardware =  container.hardwareMap;
        setup();
    }

    public abstract void setup();

    public abstract void update();

    public abstract void buttonUp(Gamepad gamepad, String button);

    // Actor will not be 'updated' for milliseconds.
    // Avoid using to pause execution of a block, use delay instead.
    public void sleep(long milliseconds) {
        isSleeping = true;
        wakeupTime = System.currentTimeMillis()+milliseconds;
    }

    // Halt execution of Container until milliseconds has passed.
    public void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean completed() {
        return isCompleted;
    }

    public DcMotor motor(String name) {
        return hardware.dcMotor.get(name);
    }

    public Servo servo(String name) {
        return hardware.servo.get(name);
    }

    public CRServo continuousServo(String name) {
        return hardware.crservo.get(name);
    }

    public double battery(String motorControllerName) {
        // This assumes things.
        return hardware.voltageSensor.get(motorControllerName).getVoltage();
    }

    public <Any> Any sensor(String type, String name, Any i2cAddress) {
        switch(type) {
            case "touchSensor":
                return (Any)((TouchSensor) hardware.touchSensor.get(name));
            case "colorSensor":
                return (Any)((ColorSensor) hardware.colorSensor.get(name));
            case "distanceSensor":
                return (Any)((OpticalDistanceSensor) hardware.opticalDistanceSensor.get(name));
            case "ultrasonicSensor":
                return (Any)((UltrasonicSensor) hardware.ultrasonicSensor.get(name));
            default:
                return null;
        }
    }
}
