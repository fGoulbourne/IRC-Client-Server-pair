import java.util.ArrayList;

public class ServBase {
  public static ArrayList<ThreadedSocket>clientList = new ArrayList<>();

  public static void main(String[] args) throws Exception {
    SocketAdder addClients = new SocketAdder();
    addClients.start();


  }
}
