package org.timecrafters.cyberarm.engines;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Cyberarm on 1/27/2018.
 */

@Autonomous(name = "~!Complete Self Test!~")
public class CompleteSelfTest extends OpMode {
    DcMotor dcFrontRight; DcMotor dcFrontLeft;
    DcMotor dcBackRight;  DcMotor dcBackLeft;

    DcMotor dcArm; DcMotor dcWinch;

    DcMotor dcLeftGrabber; DcMotor dcRightGrabber;

    CRServo crBeam;  CRServo crFlipper;
    CRServo crRelic;

    ColorSensor colorSensor; TouchSensor flipperTouch;
    TouchSensor beamTouch;   TouchSensor winchTouch;

    ArrayList motors = new ArrayList(); ArrayList servos = new ArrayList(); ArrayList sensors = new ArrayList();

    boolean completedMotors, completedServos, completedSensors, locked;

    int currentIndex = 0, initialPosition = 0, encoderMinimumTarget = 100;
    long currentMilliseconds, failedDeviceTime;

    String lastFailedDevice = "";

    @Override
    public void init() {
        dcFrontLeft = getMotor("dcFrontLeft"); dcFrontRight = getMotor("dcFrontRight");
        dcBackLeft = getMotor("dcBackLeft");   dcBackRight = getMotor("dcBackRight");
        dcArm = getMotor("dcArm");             dcWinch = getMotor("dcWinch");
        dcLeftGrabber = getMotor("dcLeftGrabber"); dcRightGrabber = getMotor("dcRightGrabber");

        dcFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        dcFrontRight.setDirection(DcMotor.Direction.FORWARD);
        dcBackRight.setDirection(DcMotor.Direction.FORWARD);
        dcBackLeft.setDirection(DcMotor.Direction.REVERSE);
        dcWinch.setDirection(DcMotor.Direction.REVERSE);
        dcArm.setDirection(DcMotor.Direction.FORWARD);
        dcLeftGrabber.setDirection(DcMotor.Direction.FORWARD); // Might be inverse
        dcRightGrabber.setDirection(DcMotor.Direction.REVERSE); // Might be inverse


        dcFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcWinch.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcLeftGrabber.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcRightGrabber.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        crBeam = getcrServo("crBeam"); crFlipper = getcrServo("crFlipper");
        crRelic = getcrServo("crGrabber");

        colorSensor = hardwareMap.colorSensor.get("colorSensor");

        beamTouch = hardwareMap.touchSensor.get("beamTouch");
        flipperTouch = hardwareMap.touchSensor.get("flipperTouch");
        winchTouch = hardwareMap.touchSensor.get("winchTouch");
        // [type => "motor", object => dcFrontLeft, timeToRun => 100 (ms)]

        testMotor(dcFrontRight, 500, "dcFrontRight");  testMotor(dcFrontLeft, 500, "dcFrontLeft");
        testMotor(dcBackRight, 500, "dcBackRight");    testMotor(dcBackLeft, 500, "dcBackLeft");
        testMotor(dcArm, 500, "dcArm");                testMotor(dcWinch, 500, "dcWinch");
        testMotor(dcLeftGrabber, 500, "dcLeftGrabber");testMotor(dcRightGrabber, 500, "dcRightGrabber");

        testSensor("color", colorSensor, "colorSensor");
        testSensor("touch", beamTouch, "beamTouch");
        testSensor("touch", flipperTouch, "flipperTouch");
        testSensor("touch", winchTouch, "winchTouch");

        completedMotors = false;
        completedServos = false;
        completedSensors= false;
        locked = false;
    }

    @Override
    public void loop() {
        if (!completedMotors) {
            handleMotors();
//        } else if (!completedServos) {
//            handleServos();
        }
        else if (!completedSensors) {
//        if (!completedSensors) {
            handleSensors();
        } else {
            stop();
        }
    }

    @Override
    public void stop() {
        telemetry.addLine("Halted.");
        dcFrontRight.setPower(0); dcFrontLeft.setPower(0);
        dcBackRight.setPower(0);  dcBackLeft.setPower(0);
        dcArm.setPower(0); dcWinch.setPower(0);
        dcLeftGrabber.setPower(0); dcRightGrabber.setPower(0);

        crBeam.setPower(0);
        crFlipper.setPower(0);
        crRelic.setPower(0);
    }

    void testMotor(DcMotor motor, long milliseconds, String name) {
        Object[] array = new Object[4];
        array[0] = motor;
        array[1] = milliseconds;
        array[2] = Math.abs(motor.getCurrentPosition());
        array[3] = name;
        motors.add(array);
    }
    void testSensor(String type, Object sensor, String name) {
        Object[] array = new Object[3];
        array[0] = type;
        array[1] = sensor;
        array[2] = name;
        sensors.add(array);
    }

    DcMotor getMotor(String motor) throws IllegalArgumentException {
        return hardwareMap.dcMotor.get(motor);
    }
    CRServo getcrServo(String servo) throws IllegalArgumentException {
        return hardwareMap.crservo.get(servo);
    }
    Servo getServo(String servo) throws IllegalArgumentException {
        return hardwareMap.servo.get(servo);
    }

    void panic(String panicMessage) {
        stop();
        throw (new RuntimeException("PANIC: "+panicMessage));
    }

    void retryThenPanic(String panicMessage, String failedDevice) {
        if (!failedDevice.equals(lastFailedDevice)) {
            lastFailedDevice = failedDevice;
            failedDeviceTime = System.currentTimeMillis();
            telemetry.addData("lastFailedDevice=", failedDevice);
            telemetry.addData("failedDeviceTime=", failedDeviceTime);
        }
        if (failedDevice == lastFailedDevice && System.currentTimeMillis()-failedDeviceTime >= 5000) {
            panic(panicMessage);
        } else {
            telemetry.addData("PANIC_then_RETRY", panicMessage);
            // Let loop continue
        }
    }

    void handleMotors() {
        Object[] activeObject = (Object[]) motors.get(currentIndex);
        long activeMotorMilliseconds = (long) activeObject[1];
        DcMotor activeMotor = (DcMotor) activeObject[0];
        int activeMotorPosition = (int) activeObject[2];
        if (!locked) {
            currentMilliseconds = System.currentTimeMillis();
            activeMotor.setPower(0.5);
            locked = true;
        } else {
            if (System.currentTimeMillis() - currentMilliseconds >= activeMotorMilliseconds) {
                if (Math.abs(activeMotor.getCurrentPosition()) >= activeMotorPosition+encoderMinimumTarget) {
                    activeMotor.setPower(0);
                    currentIndex++;
                    if (currentIndex >= motors.size()-1) {
                        currentIndex = 0;
                        completedMotors = true;
                    }
                    locked = false;
                } else {
                    panic("Motor ("+activeObject[3]+") encoder failed to reach '"+encoderMinimumTarget+"' in "+activeMotorMilliseconds+"ms");
                }
            }
        }
    }
    void handleServos() {}
    void handleSensors() {
        Object[] activeObject = (Object[]) sensors.get(currentIndex);
        String activeObjectType = (String) activeObject[0];
        if (activeObjectType.equals("color")) {
            Log.d("TEST", "HANDLE SENSORS COLOR SENSOR");
            ColorSensor localColorSensor = (ColorSensor) activeObject[1];
            if (localColorSensor.red() == 0.0 && localColorSensor.green() == 0.0 && localColorSensor.blue() == 0.0) {
                retryThenPanic("Color sensor (" + activeObject[2] + ") didn't detect color (red " + localColorSensor.red() +
                        ", green " + localColorSensor.green() + ", blue " + localColorSensor.blue() + ")", (String) activeObject[2]);
            } else {
                currentIndex++;
                if (currentIndex > sensors.size() - 1) {
                    currentIndex = 0;
                    completedSensors = true;
                }
            }
        } else if (activeObjectType.equals("touch")) {
            TouchSensor localTouchSensor = (TouchSensor) activeObject[1];
            if (!localTouchSensor.isPressed()) {
                retryThenPanic("Touch sensor (" + activeObject[2] + ") is not pressed!", (String) activeObject[2]);
            } else {
                currentIndex++;
                if (currentIndex >= sensors.size() - 1) {
                    currentIndex = 0;
                    completedSensors = true;
                }
            }
        } else {
            if (sensors.size() == 0) {
                completedSensors = true;
            }
        }
    }
}
