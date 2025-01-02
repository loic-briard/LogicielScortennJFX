package Main;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class InternetChecker {
    public static boolean isInternetAvailable() {
        try (Socket socket = new Socket()) {
            SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53); // Serveur DNS de Google
            socket.connect(socketAddress, 2000); // Timeout de 2 secondes
            return true;
        } catch (IOException e) {
            return false;
        }
    }	
}
