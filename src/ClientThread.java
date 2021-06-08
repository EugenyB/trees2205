import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private TreeType treeType;

    private Tree<?> tree;

    public ClientThread(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            out.println("Tree. Select type: 1 - Integer, 2 - Double, 3 (or other) - String");
            String s = in.readLine();
            int val = Integer.parseInt(s.trim());
            tree = new Tree<>();
            if (val == 1) {
                treeType = TreeType.IntegerTree;
                out.println("Integer Tree created!");
            } else if (val == 2){
                treeType = TreeType.DoubleTree;
                out.println("Double Tree created!");
            } else {
                treeType = TreeType.StringTree;
                out.println("String Tree created!");
            }
            while (true) {
                String line = in.readLine();
                if (line == null) break;
                String command = line.trim();
                if (command.toLowerCase().startsWith("exit")) {
                    out.println("bye");
                    break;
                }
                if (command.toLowerCase().startsWith("insert")) {
                    String[] s1 = command.split(" ");
                    if (addInTree(s1[1])) {
                        out.println("Successfully inserted");
                    } else {
                        out.println("Insertion failed");
                    }
                } else if (command.toLowerCase().startsWith("draw")) {
                    String result = drawTree();
                    out.println(result);
                } else if (command.toLowerCase().startsWith("search")) {
                    String[] s1 = command.split(" ");
                    if (searchInTree(s1[1])) {
                        out.println("tree contains " + s1[1]);
                    } else {
                        out.println("tree doesn't contain " + s1[1]);
                    }
                } else if (command.toLowerCase().startsWith("delete")) {
                    String[] s1 = command.split(" ");
                    if (searchInTree(s1[1])) {
                        deleteFromTree(s1[1]);
                        out.println(s1[1] + " was deleted");
                    } else {
                        out.println("tree doesn't contain " + s1[1]);
                    }
                } else {
                    out.println("Unknown command");
                }
            }
        } catch (IOException ex) {
            //ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteFromTree(String s) {
        tree.delete(treeType.getValue(s));
    }

    private boolean searchInTree(String s) {
        try {
            return tree.search(treeType.getValue(s));
        } catch (Exception e) {
            return false;
        }
    }

    private String drawTree() {
        return tree.draw();
    }

    private boolean addInTree(String s) {
        return tree.insert(treeType.getValue(s));
    }


}
