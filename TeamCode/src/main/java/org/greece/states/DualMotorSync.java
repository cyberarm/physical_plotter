package org.greece.states;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.engine.State;

public class DualMotorSync extends State {
    private DcMotor leftMotor, rightMotor;

    private int lastLeftMotorEncoder, lastRightMotorEncoder = 0;
    private int initialLeftMotorEncoder, initialRightMotorEncoder = 0;
    public static int left = 0;
    public static int right= 1;
    private int biasedMotor;
    private  double motorPower;

    public DualMotorSync(double motorPower, int biasedMotor) {
        this.motorPower = motorPower;
        this.biasedMotor = biasedMotor;
    }

    @Override
    public void init() {
        leftMotor = engine.hardwareMap.dcMotor.get("leftMotor");
        rightMotor= engine.hardwareMap.dcMotor.get("rightMotor");

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        initialLeftMotorEncoder = leftMotor.getCurrentPosition();
        initialRightMotorEncoder= rightMotor.getCurrentPosition();

        if (biasedMotor == left) {
            leftMotor.setPower(motorPower);
            rightMotor.setPower(0);
        } else {
            leftMotor.setPower(0);
            rightMotor.setPower(motorPower);
        }

    }

    @Override
    public void exec() {
        int leftDifference, rightDifference, difference;

        leftDifference = (leftMotor.getCurrentPosition()-lastLeftMotorEncoder)-initialLeftMotorEncoder;
        rightDifference= (rightMotor.getCurrentPosition()-lastRightMotorEncoder)-initialRightMotorEncoder;
        difference = Math.abs(leftDifference-rightDifference);

        if (leftDifference < rightDifference) {
            getUnbiasedMotor().setPower(getUnbiasedMotor().getPower() - differenceModifier(difference));
        } else if (leftDifference > rightDifference) {
            getUnbiasedMotor().setPower(getUnbiasedMotor().getPower() + differenceModifier(difference));
        }


        // END
        lastLeftMotorEncoder = leftMotor.getCurrentPosition();
        lastLeftMotorEncoder = rightMotor.getCurrentPosition();

        engine.telemetry.addLine("Difference:");
        engine.telemetry.addData("", difference);
        engine.telemetry.addLine("__________________");
        engine.telemetry.addLine("Encoders:");
        engine.telemetry.addData("leftEncoder: ", leftMotor.getCurrentPosition());
        engine.telemetry.addData("rightEncoder: ", rightMotor.getCurrentPosition());
        engine.telemetry.addLine("__________________");
        engine.telemetry.addLine("Speed:");
        engine.telemetry.addData("leftSpeed: ", leftMotor.getPower());
        engine.telemetry.addData("rightSpeed: ", rightMotor.getPower());
        engine.telemetry.update();
    }

    private double differenceModifier(int difference) {
        return difference*0.000001;
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
