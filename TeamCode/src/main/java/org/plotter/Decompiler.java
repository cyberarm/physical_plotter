package org.plotter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Decompiler {
    public ArrayList<Event> events = new ArrayList<>();

    public Decompiler(String filename) {
        // Do stuff with file
        parseRCode(filename);
    }

    void parseRCode(String filename) {
        try {
            FileInputStream fileStream = new FileInputStream(filename);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fileStream));

            String line;
            while((line = buffer.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException error) {
            // Stuff
        }
    }

    void processLine(String line) {
        String[] list = line.split(" ");
        Event event = null;

        switch (list[0]) {
            case "home":
                event = new Event("home");
            case "pen_up":
                event = new Event("pen_up");
            case "pen_down":
                event = new Event("pen_down");
            case "move":
                event = new Event("move", Integer.parseInt(list[1]), Integer.parseInt(list[2]));
            default:
        }

        if (null != event) {
            events.add(event);
        }
    }
}
