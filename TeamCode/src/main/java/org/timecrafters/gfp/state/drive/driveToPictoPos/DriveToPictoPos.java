package org.timecrafters.gfp.state.drive.driveToPictoPos;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by t420 on 10/17/2017.
 */

public class DriveToPictoPos extends Config {

    int vuMark;

    public DriveToPictoPos(Engine engine, int vuMark){
        super(engine);
        this.vuMark = vuMark;

    }

    public void exec(){

        switch (vuMark){

            //Glyph Right
            case 0:
                break;

            //Glyph Center
            case 1:
                break;

            //Glyph Left
            case 2:
                 break;
        }

    }
}