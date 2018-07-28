package org.plotter.TCPServer;
import android.system.ErrnoException;

import java.util.UUID;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

public class Client {
  private Socket socket;
  public String uuid;
  public boolean authenticated = false;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;
  private boolean socketConnected = true;

  public Client(Socket client) throws IOException {
    this.uuid = (UUID.randomUUID()).toString();
    this.socket = client;
    this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    socket.setKeepAlive(true);
  }
  
  protected Socket getSocket() { return socket; }

  public String read() throws IOException,ErrnoException {
    String data = "";
    String readLine;

    while((readLine = bufferedReader.readLine()) != null) {
      data+=readLine;
      if (readLine.isEmpty()) {break;}
    }
    
    if (readLine == null) {socketConnected = false;}

    return data;
  }

  public void write(String string) throws IOException {
    bufferedWriter.write(string);
  }
  
  public void flush() throws IOException,SocketException {
    bufferedWriter.flush();
  }
  
  public boolean connected() {
    return socketConnected;
  }

  public void close() throws IOException {
    bufferedReader.close();
    bufferedWriter.close();
    socketConnected = false;

    socket.close();
  }
}
