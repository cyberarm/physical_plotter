package org.cyberarm.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.cyberarm.engine.CyberarmEngine;
import org.cyberarm.greece.states.BillMotorRamp;

@Disabled
//@TeleOp(name = "BillRampDriveV3")
public class BillRampDriveV3 extends CyberarmEngine {
    @Override
    public void setup() {
        addState(new BillMotorRamp("leftMotor", 5000, 10_000));
    }
}
