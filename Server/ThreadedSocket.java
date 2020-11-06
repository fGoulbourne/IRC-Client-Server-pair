import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadedSocket extends Thread{
  public Socket client;
  private BufferedReader servIn;
  private PrintWriter servOut;
  private String line;
  private Receiver readClnt;

  //creates our writers and readers for each socket to stop a lockup
  public ThreadedSocket(Socket socket) {
    client = socket;
    try {
      servIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
      servOut = new PrintWriter(client.getOutputStream(), true);

    } catch(IOException e) {}

  }
  public void print(String text) {
    servOut.println(text);
  }

  public void run() {
    String userIn;


    //prints when we get a new message
    while(true) {
      try {
        line = servIn.readLine();
        sleep(10);
        if(line != null) {
          for(ThreadedSocket skt : ServBase.clientList) {
            if(skt != this) {
              skt.print(line);
            }
          }
          line = null;
        }
      } catch(Exception e) {
        break;
      }
    }
  }
}
