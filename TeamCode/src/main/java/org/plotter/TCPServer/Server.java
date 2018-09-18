package org.plotter.TCPServer;

import android.annotation.TargetApi;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.system.ErrnoException;
import android.util.Log;

import org.driver.Driver;
import org.engine.Engine;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;

import static java.lang.Thread.sleep;

public class Server {
  private ToneGenerator toneGenerator;
  private ServerSocket server;
  private int port;
  private boolean runServer = false;
  private Client activeClient;

  public Server(int port) throws IOException {
    int attempts = 0;
    while(attempts < 10) { // really big number
      try {
        toneGenerator = Engine.driverStatic.toneGenerator;
        break;
      } catch (NullPointerException err) {
        attempts++;
        try {
          sleep(100);
        } catch (InterruptedException e) {}
      }
    }
    this.server = new ServerSocket();
    server.setReuseAddress(true);
    this.port = port;
  }

  public void start() throws IOException {
    int attempts = 0;
    while(attempts < 10) {
      try {
        server.bind(new InetSocketAddress(port));
      } catch (SocketException e) {
        attempts++;
      }
    }

    if (server.isBound()) {
      runServer = true;
      run();
    } else {
      start(); // TODO: Infinite loop RISK.
    }
  }

  @TargetApi(21)
  private void run() throws IOException,SocketException {
    while (runServer) {
      if (!runServer || server.isClosed()) {
        break;
      }
      final Client client = new Client(server.accept());
      if (activeClient != null && activeClient.connected()) {

      } else {
        activeClient = client;
        new Thread(new Runnable() {
          public void run() {
            try {
              new Handler(client);
            }catch (SocketException e) {
              e.printStackTrace();
            } catch (ErrnoException e) {
              e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }).start();
      }

      try {
        sleep(10);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  public void stop() throws IOException {
    Log.i("DRIVER", "STOPPING SERVER...");
    runServer = false;
    if (activeClient != null){ activeClient.close(); }
    if (server != null) {
      server.close();
      while (!server.isClosed()) {
      }
      Log.i("DRIVER", "Server Stopped.");
      try {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 500);
        try { sleep(500); } catch (InterruptedException e) {}
        toneGenerator.release();
      } catch (NullPointerException e) {
      } catch (RuntimeException e) {
        // Released toneGenerator already.
      }
    }
  }
}
