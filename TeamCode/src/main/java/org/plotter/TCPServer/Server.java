package org.plotter.TCPServer;

import android.annotation.TargetApi;
import android.system.ErrnoException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class Server {
  private ServerSocket server;
  private boolean runServer = false;
  private Client activeClient;

  public Server(int port) throws IOException {
    this.server = new ServerSocket(port);
  }

  public void start() throws IOException {
    runServer = true;
    run();
  }

  @TargetApi(21)
  private void run() throws IOException,SocketException {
    while (runServer) {
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
        Thread.sleep(10);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  public void stop() throws IOException {
    runServer = false;
    if (activeClient != null){ activeClient.close(); }
    if (server != null) {
      server.close();
//      while (!server.isClosed()) {
//      }
    }
  }
}
