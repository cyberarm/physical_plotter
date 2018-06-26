package org.greece.states;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.engine.State;

public class BillMotorRamp extends State {
    private int travelDistance;
    private DcMotor motor;
    private long timeToRampMS;
    private long startTimeMs;
    private double power;

    public BillMotorRamp(String motorName, long timeToRampMS, int travelDistance) {
        this.motor = engine.hardwareMap.dcMotor.get(motorName);
        this.timeToRampMS = timeToRampMS;
        this.travelDistance = travelDistance;
    }

    @Override
    public void start() {
        startTimeMs = System.currentTimeMillis();
    }

    @Override
    public void exec() {
        power = ((double) (System.currentTimeMillis()-startTimeMs) / (double) timeToRampMS);
        power = Range.clip(power, -1.0, 1.0);
        motor.setPower(power);

        engine.addTelemetry(new Runnable() {
            @Override
            public void run() {
                engine.telemetry.addLine("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                engine.telemetry.addData("Power (ratio)", power);
                engine.telemetry.addData("Time elapsed", System.currentTimeMillis()-startTimeMs);
                engine.telemetry.addData("Ramp Time (ms)", timeToRampMS);
                engine.telemetry.addLine();
                int devisor = 5;
                int percent = (int) ((power*100.0)/devisor);
                String progress = new String(""+percent*devisor+"% ");
                for (int i = 0; i < percent; i++) {
                    progress+="☼";
                }
                engine.telemetry.addLine(progress);
                engine.telemetry.addLine("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            }
        });
    }
}
