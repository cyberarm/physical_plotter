package org.cyberarm.driver;

import android.media.AudioManager;
import android.media.ToneGenerator;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.engine.CyberarmEngine;
import org.cyberarm.engine.Support;
import org.cyberarm.greece.statues.VirtualDCMotor;
import org.cyberarm.plotter.Decompiler;
import org.cyberarm.plotter.TCPServer.Server;

import java.io.IOException;


@TeleOp(name = "Plotter Driver")
public class Driver extends CyberarmEngine {
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
      driver = (Driver) CyberarmEngine.instance;
      driverStatic = (Driver) CyberarmEngine.instance;
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

