package org.driver;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.driver.states.BaseMover;
import org.driver.states.Home;
import org.driver.states.PenDown;
import org.driver.states.PenUp;
import org.driver.states.Wait;
import org.engine.Engine;
import org.engine.Support;
import org.plotter.Decompiler;
import org.plotter.Event;
import org.plotter.TCPServer.Server;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;


@TeleOp(name = "Plotter Driver")
public class Driver extends Engine {
  Decompiler decompiler;
  Server server;
  public boolean pendingWork = false;
  int xAxisStep = 1;
  int yAxisStep = 1;

  public Driver() {
    if (Driver.instance != null) {
      Driver.instance.stop();
    }
    Engine.instance = this;
    Driver.instance = this;

    Support.puts("Driver", "Starting server...");
    try {
      server = new Server(8962);
      new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            server.start();
          } catch (SocketException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }).start();
      Support.puts("Driver", "Server running.");
    } catch (IOException e) {

      e.printStackTrace();
    }
  }

  @Override
  public void init() {
    // RESET THE THINGS
//    hardwareMap.dcMotor.get("xAxis").setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//    hardwareMap.dcMotor.get("yAxis").setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//    hardwareMap.servo.get("svPen").setPosition(1.0);
    super.init();
  }

  @Override
  public void setup() {
    addState(new Wait(this));
  }

  @Override
  public void stop() {
    super.stop();

    Support.puts("Driver", "Stopping server...");
    try {
      if (server != null) {
        server.stop();
        Support.puts("Driver", "Stopped server.");
      }
    } catch (IOException e) {
      Support.puts("Driver", "Failed to stop server!");
      e.printStackTrace();
    }
  }
}
