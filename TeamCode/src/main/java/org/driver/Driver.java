package org.driver;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.driver.states.Home;
import org.driver.states.BaseMover;
import org.driver.states.PenDown;
import org.driver.states.PenUp;
import org.engine.Engine;
import org.engine.Support;
import org.plotter.Decompiler;
import org.plotter.Event;


@TeleOp(name = "Plotter Driver")
public class Driver extends Engine {
    public static Driver instance;
    Decompiler decompiler;
    int xAxisStep = 1;
    int yAxisStep = 1;

    public Driver() {
        instance = this;

        Support.puts("Driver", "Loading file...");
        decompiler = new Decompiler("/Download/compile.rcode");
        Support.puts("Driver", "Loaded file, has "+ decompiler.events.size() +" events.");
    }

    @Override
    public void setProcesses() {
        Support.puts("Driver", "Setting states...");
        for (int i = 0; i < decompiler.events.size(); i++) {
            Event event = decompiler.events.get(i);
            switch (event.type.toLowerCase().trim()){
                case "home":
                    Support.puts("Driver", "Added HOME");
                    addState(new Home());
                    break;
                case "pen_up":
                    Support.puts("Driver", "Added PEN_UP");
                    addState(new PenUp());
                    break;
                case "pen_down":
                    Support.puts("Driver", "Added PEN_DOWN");
                    addState(new PenDown());
                    break;
                case "move":
                    Support.puts("Driver", "Added MOVE: " +event.x+ ":"+ event.y);
                    addState(new BaseMover(event.x, event.y));
                    break;
                default:
                    Support.puts("Driver", "Unknown instruction: " +event.type);
                    break;
            }
        }
        Support.puts("Driver", "Done setting states.");
    }
}
