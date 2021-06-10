import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main class for Server
 */
public class Main {
    /**
     * Entry point for server program
     * @param args command line arguments
     */
    public static void main(String[] args) {
	    new Main().run();
    }

    /**
     * Main method  of server.
     * Wait for connection from clients and creates client Threads
     */
    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(socket);
                new Thread(clientThread).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
