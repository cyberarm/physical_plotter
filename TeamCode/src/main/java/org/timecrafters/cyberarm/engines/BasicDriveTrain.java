package org.timecrafters.cyberarm.engines;

import android.media.AudioManager;
import android.media.ToneGenerator;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;

/**
 * Created by cyberarm on 11/22/2017.
 */

@Disabled//(name = "!!! TEST BasicDriveTrain TEST !!!")
public class BasicDriveTrain extends OpMode {
    public DcMotor dcFrontRight;
    public DcMotor dcFrontLeft;
    public DcMotor dcBackRight;
    public DcMotor dcBackLeft;

    public int targetDistance = 84*12;
    public double motorPower = 0.2;

//    public ToneGenerator toneGenerator;

    @Override
    public void init() {
        dcFrontRight = hardwareMap.dcMotor.get("dcFrontRight");
        dcFrontLeft  = hardwareMap.dcMotor.get("dcFrontLeft");

        dcBackRight  = hardwareMap.dcMotor.get("dcBackRight");
        dcBackLeft   = hardwareMap.dcMotor.get("dcBackLeft");

        dcBackLeft.setDirection(DcMotor.Direction.REVERSE);
        dcFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        dcFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//        toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
    }

    @Override
    public void start() {
        dcFrontRight.setPower(motorPower); dcFrontLeft.setPower(motorPower);
        dcBackRight.setPower(motorPower);  dcBackLeft.setPower(motorPower);

        dcFrontRight.setTargetPosition(targetDistance); dcFrontLeft.setTargetPosition(targetDistance);
        dcBackRight.setTargetPosition(targetDistance);  dcBackLeft.setTargetPosition(targetDistance);

        dcFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        dcBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void loop() {
        telemetry.addLine("Drive Train Encoders");
        telemetry.addData("FRight", dcFrontRight.getCurrentPosition());
        telemetry.addData("FLeft", dcFrontLeft.getCurrentPosition());
        telemetry.addLine();
        telemetry.addData("BRight", dcBackRight.getCurrentPosition());
        telemetry.addData("BLeft", dcBackLeft.getCurrentPosition());

//        if (getBatteryVoltage() < 11.5) {
//            toneGenerator.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 250);
//        }
    }

    @Override
    public void stop() {
        dcFrontRight.setPower(0.0);
        dcFrontLeft.setPower(0.0);

        dcBackRight.setPower(0.0);
        dcBackLeft.setPower(0.0);

//        toneGenerator.stopTone();
//        toneGenerator.release();
    }

    // https://github.com/ftctechnh/ftc_app/blob/master/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples/ConceptTelemetry.java#L167
//    double getBatteryVoltage() {
//        double result = Double.POSITIVE_INFINITY;
//        for (VoltageSensor sensor : hardwareMap.voltageSensor) {
//            double voltage = sensor.getVoltage();
//            if (voltage > 0) {
//                result = Math.min(result, voltage);
//            }
//        }
//        return result;
//    }
}
