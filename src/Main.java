import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
	    new Main().run();
    }

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

    private void test() {
        Tree<Integer> tree = new Tree<>(); // 70, 60, 85, 87, 35, 68, 72
        tree.insert(70);
        tree.insert(60);
        tree.insert(85);
        tree.insert(87);
        tree.insert(35);
        tree.insert(68);
        tree.insert(72);
        tree.insert(86);
        tree.insert(90);

        System.out.println(tree.draw());
        System.out.println(tree.traverse());

        tree.delete(85);

        System.out.println(tree.draw());
        System.out.println(tree.traverse());
    }
}
