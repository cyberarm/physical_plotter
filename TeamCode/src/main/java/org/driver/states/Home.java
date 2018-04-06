package org.driver.states;

import org.engine.State;
import org.engine.Support;

public class Home extends State {
    public Home() {
    }
    @Override
    public void exec() {
        Support.puts("Home", "Homing pen...");
        setFinished(true);
    }
}
