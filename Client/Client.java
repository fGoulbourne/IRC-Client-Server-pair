import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread{
  public static String addr;
  public static int port;

  public static void main(String[] args) throws Exception {
    //setting up the address
    Client client = new Client();
    Client.addr = args.length == 0 ? "localhost" : args[0];
    Client.port = args.length == 2 ? Integer.parseInt(args[1]) : 25565;
    client.start();

  }

  public void run() {
    //setting up our IO
    try{
      Socket client = new Socket(addr, port);
      BufferedReader clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
      PrintWriter clientOut = new PrintWriter(client.getOutputStream(), true);
      BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));

      //setting up our read threads
      Receiver readSys = new Receiver(sysIn);
      readSys.start();
      Receiver readServ = new Receiver(clientIn);
      readServ.start();
      readServ.setPriority(MAX_PRIORITY);

      String userIn;
      String servIn;

      //checking constantly for stuff to send/receive
      while (true) {
        if ((userIn = readSys.getLine()) != null) {
            clientOut.println(userIn);

        }
        if ((servIn = readServ.getLine()) != null) {
          System.out.println(servIn);
        }
        sleep(25);
      }
    } catch(Exception e) {                        //quits if any link closes
      System.out.println("Server Closed");
      System.exit(0);
    }
  }

}
