package org.driver.states;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.engine.State;

public class Home extends BaseMover {

    public Home() {
        super(0, 0);
        useEndStops();
    }
}
