package org.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.engine.Engine;
import org.greece.states.BillMotorRamp;

@TeleOp(name = "BillRampDriveV3")
public class BillRampDriveV3 extends Engine {
    @Override
    public void setup() {
        addState(new BillMotorRamp("leftMotor", 5000, 10_000));
    }
}
