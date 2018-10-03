package org.timecrafters.gfp.engines;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.Teleop.Arm;
import org.timecrafters.gfp.Teleop.Brake;
import org.timecrafters.gfp.Teleop.JewelBump;
import org.timecrafters.gfp.Teleop.DriveTrain;
import org.timecrafters.gfp.Teleop.Grabbers;
import org.timecrafters.gfp.Teleop.RelicGrabber;
import org.timecrafters.gfp.Teleop.Winch;
import org.timecrafters.gfp.config.HardWareConfig;

/**
 * Created by t420 on 10/5/2017.
 */
@TeleOp(name="Main Teleop")
public class TeleopEngine extends Engine {

    public void setProcesses(){
        hardWareConfig = new HardWareConfig(this);
        addState(hardWareConfig);
//        addState(new HardWareConfig(this));
        addState(new DriveTrain(this));
        addThreadedState(new Grabbers(this,0.3));
        addThreadedState(new Winch(this,1.0));
        addThreadedState(new Arm(this,1.0,0.3));
        addThreadedState(new Brake(this));
        //addThreadedState(new JewelBump(this,1.0,1.0));
        addThreadedState(new RelicGrabber(this,1.0));

    }

}
