package org.cyberarm.driver;

import android.media.AudioManager;
import android.media.ToneGenerator;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.driver.states.BaseMover;
import org.cyberarm.driver.states.Home;
import org.cyberarm.driver.states.PenDown;
import org.cyberarm.driver.states.PenUp;
import org.cyberarm.driver.states.Wait;
import org.cyberarm.engine.Engine;
import org.cyberarm.engine.Support;
import org.cyberarm.greece.statues.VirtualDCMotor;
import org.cyberarm.plotter.Decompiler;
import org.cyberarm.plotter.Event;
import org.cyberarm.plotter.TCPServer.Server;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;


@TeleOp(name = "Plotter Driver")
public class Driver extends Engine {
  Decompiler decompiler;
  public Server server;
  public boolean pendingWork = false;
  public boolean offlineDebugging = false;
  public VirtualDCMotor xAxisV, yAxisV;
  int xAxisStep = 1;
  int yAxisStep = 1;
  public ToneGenerator toneGenerator;

  public Driver() {
    Driver.instance = this;
    try {
      driver = (Driver) Engine.instance;
      driverStatic = (Driver) Engine.instance;
    } catch (ClassCastException err) {}

    this.toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, 50);

    if (offlineDebugging) {
      xAxisV = new VirtualDCMotor("xAxis");
      yAxisV = new VirtualDCMotor("yAxis");
    }

    Support.puts("Driver", "Starting server...");
    try {
      server = new Server(8962);
      new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            server.start();
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
    hardwareMap.dcMotor.get("xAxis").setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    hardwareMap.dcMotor.get("yAxis").setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    hardwareMap.dcMotor.get("xAxis").setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    hardwareMap.dcMotor.get("yAxis").setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    hardwareMap.crservo.get("svPen").setPower(-0.5);
    super.init();
  }

  @Override
  public void setup() {}

  @Override
  public void stop() {
    super.stop();
  }
}

