package org.timecrafters.gfp.state.drive;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;

/**
 * Created by t420 on 11/2/2017.
 */

public class DriveStraightBackward extends Drive {

    public DriveStraightBackward(Engine engine, double power, int distance){
        super(engine);
        this.power = power;
        this.distance = distance;
    }

    public void init(){
        super.init();
        //Front Left, Back Left, Front Right, Back Right
        setMotors(1,1,1,1);
        DcMotor[] motors = {dcBackRight, dcBackLeft};
        setReadMotors(motors);
    }
}
