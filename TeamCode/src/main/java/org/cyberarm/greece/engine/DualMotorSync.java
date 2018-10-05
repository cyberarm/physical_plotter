package org.cyberarm.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
//@TeleOp(name = "DualMotorSync")
public class DualMotorSync extends OpMode {
    private DcMotor leftMotor, rightMotor;

    private long lastMilliseconds;
    private int lastLeftMotorEncoder, lastRightMotorEncoder = 0;
    private int initialLeftMotorEncoder, initialRightMotorEncoder = 0;
    private int sleepTimeMs = 500;
    public static int left = 0;
    public static int right= 1;
    private int biasedMotor;
    private  double motorPower;
    private boolean firstRun = false;
    private DualMotorSync engine;

//    public DualMotorSync(double motorPower, int biasedMotor) {
////        this.motorPower = motorPower;
////        this.biasedMotor = biasedMotor;
////        this.cyberarmEngine = this;
//        // Biased motor?
//        /*
//            The biased motor is the one who's speed is known and that the unbiased motor tries to match.
//         */
//    }

    @Override
    public void init() {
        engine = this;
        lastMilliseconds = System.currentTimeMillis();
        leftMotor = engine.hardwareMap.dcMotor.get("leftMotor");
        rightMotor= engine.hardwareMap.dcMotor.get("rightMotor");

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorPower = 0.4;
        biasedMotor = left;

        initialLeftMotorEncoder = leftMotor.getCurrentPosition();
        initialRightMotorEncoder= rightMotor.getCurrentPosition();
    }

    @Override
    public void start() {
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        int leftDifference, rightDifference, difference = 0;
        if (!firstRun) {
            if (biasedMotor == left) {
                leftMotor.setPower(motorPower);
                rightMotor.setPower(0);
            } else {
                leftMotor.setPower(0);
                rightMotor.setPower(motorPower);
            }

            firstRun = true;
        }
        if (System.currentTimeMillis()-lastMilliseconds > sleepTimeMs) {

            leftDifference = leftMotor.getCurrentPosition();
            rightDifference = rightMotor.getCurrentPosition();
            difference = leftDifference - rightDifference;

            if (leftDifference < rightDifference) {
                setPower(getUnbiasedMotor(), differenceModifier());
            } else if (leftDifference > rightDifference) {
                setPower(getUnbiasedMotor(), differenceModifier());
            }

            lastMilliseconds = System.currentTimeMillis();


            // END
            lastLeftMotorEncoder = leftMotor.getCurrentPosition();
            lastLeftMotorEncoder = rightMotor.getCurrentPosition();

            engine.telemetry.addLine("Difference:");
            engine.telemetry.addData("l-r", difference);
            engine.telemetry.addData("left", leftDifference);
            engine.telemetry.addData("right", rightDifference);
            engine.telemetry.addData("multiplier", differenceModifier());
            engine.telemetry.addLine("__________________");
            engine.telemetry.addLine("Encoders:");
            engine.telemetry.addData("leftEncoder: ", leftMotor.getCurrentPosition());
            engine.telemetry.addData("rightEncoder: ", rightMotor.getCurrentPosition());
            engine.telemetry.addLine("__________________");
            engine.telemetry.addLine("Speed:");
            engine.telemetry.addData("leftSpeed: ", leftMotor.getPower());
            engine.telemetry.addData("rightSpeed: ", rightMotor.getPower());
        }
    }

    private void setPower(DcMotor motor, double target) {
        target = Math.abs(target);
        double power = 0.0;
        if (target < -1.000) {
            power = -1.0;
        } else if (target > 1.000) {
            power = 1.0;
        } else if (target != 0.000) {
            power = target;
        }
        motor.setPower(power);
    }

    private double differenceModifier() {
        double leftV = ((double) getBiasedMotor().getCurrentPosition() + (double) lastLeftMotorEncoder)/(sleepTimeMs/1000.0);
        double rightV = ((double) getUnbiasedMotor().getCurrentPosition() + (double) lastRightMotorEncoder)/(sleepTimeMs/1000.0);

        return (leftV/rightV);
//        return difference*0.000001;
    }

    private DcMotor getBiasedMotor() {
        if (biasedMotor == left) {
            return leftMotor;
        } else {
            return rightMotor;
        }
    }
    private DcMotor getUnbiasedMotor() {
        if (biasedMotor == left) {
            return rightMotor;
        } else {
            return leftMotor;
        }
    }
}
