package org.cyberarm.plotter;

public class Event {
  public String type;
  public int x, y;

  public Event(String local_type, int local_x, int local_y) {
    type = local_type;
    x = local_x;
    y = local_y;
  }

  public Event(String local_type) {
    type = local_type;
  }
}
