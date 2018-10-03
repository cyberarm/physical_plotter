package org.cyberarm.plotter;

import org.cyberarm.engine.Support;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Decompiler {
  private boolean ALLOW_FILE_ACCESS = false;
  public ArrayList<Event> events = new ArrayList<>();

  public Decompiler(String filename) {
    // Do stuff with file
    if (ALLOW_FILE_ACCESS) {
      Support.puts("Decompiler", "Parsing " + filename + "...");
      parseRCode(filename);
    } else {
      Support.puts("Decompiler", "Using sample RCode.");
      generateSampleRCode();
    }
  }

  void generateSampleRCode() {
    events.add(new Event("pen_up"));
    events.add(new Event("home"));
    events.add(new Event("move", 100, 100));
    events.add(new Event("move", 100, 200));
    events.add(new Event("move", 200, 200));
    events.add(new Event("move", 200, 100));
    events.add(new Event("move", 100, 100));
    events.add(new Event("pen_up"));
  }

  void parseRCode(String filename) {
    try {
      FileInputStream fileStream = new FileInputStream(filename);
      BufferedReader buffer = new BufferedReader(new InputStreamReader(fileStream));

      String line;
      while ((line = buffer.readLine()) != null) {
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
        break;
      case "pen_up":
        event = new Event("pen_up");
        break;
      case "pen_down":
        event = new Event("pen_down");
        break;
      case "move":
        event = new Event("move", Integer.parseInt(list[1]), Integer.parseInt(list[2]));
        break;
      default:
        break;
    }

    if (null != event) {
      events.add(event);
    }
  }
}
