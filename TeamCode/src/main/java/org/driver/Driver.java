package org.driver;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.driver.states.Home;
import org.driver.states.Move;
import org.driver.states.PenDown;
import org.driver.states.PenUp;
import org.engine.Engine;
import org.plotter.Decompiler;
import org.plotter.Event;

@TeleOp(name = "Plotter Driver")
public class Driver extends Engine {
    Decompiler decompiler;

    public Driver() {
        new Decompiler("/Download/compile.rcode");
    }

    @Override
    public void setProcesses() {
        for (int i = 0; i >= decompiler.events.size(); i++) {
            Event event = decompiler.events.get(i);
            switch (event.type){
                case "home":
                    addState(new Home(this));
                case "pen_up":
                    addState(new PenUp(this));
                case "pen_down":
                    addState(new PenDown(this));
                case "move":
                    addState(new Move(this, event.x, event.y));
                default:
            }
        }
    }
}
