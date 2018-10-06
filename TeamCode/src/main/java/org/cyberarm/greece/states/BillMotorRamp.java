package org.cyberarm.greece.states;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.cyberarm.engine.CyberarmState;

public class BillMotorRamp extends CyberarmState {
    private int travelDistance, travelOffset;
    private DcMotor motor;
    private long timeToRampMS;
    private long startTimeMs;
    private boolean hasRampedUp, hasTravelled = false;
    private double power;

    public BillMotorRamp(String motorName, long timeToRampMS, int travelDistance) {
        this.motor = cyberarmEngine.hardwareMap.dcMotor.get(motorName);
        this.timeToRampMS = timeToRampMS;
        this.travelDistance = travelDistance;
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void start() {
        startTimeMs = System.currentTimeMillis();
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void exec() {
        if (hasRampedUp && hasTravelled && motor.getPower() <= 0.0) { setFinished(true); }

        if (!hasRampedUp) {
            power = ((double) (System.currentTimeMillis() - startTimeMs) / (double) timeToRampMS);
            if (power >= 1.0) { hasRampedUp = true; travelOffset = motor.getCurrentPosition(); }
        } else if (hasRampedUp && !hasTravelled) {
            if (Math.abs(motor.getCurrentPosition()) >= relativeTravelDistance()) { hasTravelled = true; startTimeMs = System.currentTimeMillis(); }
        } else {
            // Ramp Down
            power = ((double) (System.currentTimeMillis() - startTimeMs) / (double) timeToRampMS);
            power = Math.abs(power-1.0);
        }


        power = Range.clip(power, -1.0, 1.0);
        motor.setPower(power);

        if (hasRampedUp && hasTravelled && power <= 0.0) { motor.setPower(0); }
    }

    public int relativeTravelDistance(int distance, boolean negate) {
        if (negate) {
            if (travelOffset > distance) {
                return travelOffset - distance;
            } else {
                return distance - travelOffset;
            }
        } else {
            if (travelOffset > distance) {
                return travelOffset + distance;
            } else {
                return distance + travelOffset;
            }
        }
    }

    public int relativeTravelDistance() {
        return relativeTravelDistance(travelDistance, false);
    }

        public void telemetry() {
        cyberarmEngine.telemetry.addLine("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        if (hasRampedUp) {
            cyberarmEngine.telemetry.addData("Ramped Up?", "✅");
        } else {
            cyberarmEngine.telemetry.addData("Ramped Up?", "X");
        }
        if (hasTravelled) {
            cyberarmEngine.telemetry.addData("Travelled?", "✅");
        } else {
            cyberarmEngine.telemetry.addData("Travelled?", "X");
        }

        if (!hasRampedUp) {
            cyberarmEngine.telemetry.addData("Power (ratio)", power);
            cyberarmEngine.telemetry.addData("Time elapsed", System.currentTimeMillis() - startTimeMs);
            cyberarmEngine.telemetry.addData("Ramp Time (ms)", timeToRampMS);
            cyberarmEngine.telemetry.addLine();
            cyberarmEngine.telemetry.addLine(progressBar(25, power * 100.0));

        } else if (hasRampedUp && !hasTravelled) {
            double distanceRatio = ((motor.getCurrentPosition()-travelOffset) / (double) travelDistance);
            cyberarmEngine.telemetry.addData("Distance (ratio)", distanceRatio);
            cyberarmEngine.telemetry.addData("Travelled", motor.getCurrentPosition()-travelOffset);
            cyberarmEngine.telemetry.addLine();
            cyberarmEngine.telemetry.addLine(progressBar(25, distanceRatio * 100.0));

        } else if (hasRampedUp && hasTravelled) {
            cyberarmEngine.telemetry.addData("Power (ratio)", power);
            cyberarmEngine.telemetry.addData("Time elapsed", System.currentTimeMillis() - startTimeMs);
            cyberarmEngine.telemetry.addData("Ramp Time (ms)", timeToRampMS);
            cyberarmEngine.telemetry.addLine();
            cyberarmEngine.telemetry.addLine(progressBar(25, Math.abs(power-1.0) * 100.0));
        } else if (getIsFinished()) {
            cyberarmEngine.telemetry.addData("Ramped Down?", "✅");
        }

        cyberarmEngine.telemetry.addLine("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
    }
}
