import java.io.IOException;
import java.net.ServerSocket;
import java.util.SplittableRandom;

public class SocketAdder extends Thread{

  //continuously tries to add a new client
  public void run() {
    while(true) {
      try {
        ServerSocket host = new ServerSocket(25565);
        ServBase.clientList.add(new ThreadedSocket(host.accept()));
        ServBase.clientList.get(ServBase.clientList.size()-1).start();
      } catch (IOException e) {}
    }
  }
}
