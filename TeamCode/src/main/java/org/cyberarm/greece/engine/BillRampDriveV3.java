package org.cyberarm.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.engine.Engine;
import org.cyberarm.greece.states.BillMotorRamp;

@Disabled
//@TeleOp(name = "BillRampDriveV3")
public class BillRampDriveV3 extends Engine {
    @Override
    public void setup() {
        addState(new BillMotorRamp("leftMotor", 5000, 10_000));
    }
}
