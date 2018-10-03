package org.timecrafters.gfp.state.drive;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;

/**
 * Created by t420 on 11/2/2017.
 */

public class DriveStraightForward extends Drive {

    private int delayBeforeStartTime = 0;
    public boolean firstRun = true;
    public DriveStraightForward(Engine engine, double power, int distance){
        super(engine);
        this.power = power;
        this.distance = distance;
    }
    public DriveStraightForward(Engine engine, double power, int distance,int sleeptimeMs){
        super(engine);
        this.power = power;
        this.distance = distance;
        this.delayBeforeStartTime = sleeptimeMs;
    }
    public DriveStraightForward(Engine engine, double power, int distance, boolean haltOnComplete){
        super(engine);
        this.power = power;
        this.distance = distance;
        this.haltOnComplete = haltOnComplete;
    }

    public void init(){
        super.init();
        //Front Left, Back Left, Front Right, Back Right
        setMotors(-1,-1,-1,-1);
        DcMotor[] motors = {dcFrontRight, dcFrontLeft};
        setReadMotors(motors);
    }
    @Override
    public void exec(){
        if(firstRun) {
            sleep(delayBeforeStartTime);
            super.exec();
        }else{
            super.exec();
        }
    }
}
