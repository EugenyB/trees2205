import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;

public class ClientThread implements Runnable {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private TreeType type;


    private Tree<Integer> integerTree;
    private Tree<Double> doubleTree;
    private Tree<String> stringTree;

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
            if (val == 1) {
                integerTree = new Tree<>();
                type = TreeType.INTEGER;
                out.println("Integer Tree created!");
            } else if (val == 2){
                doubleTree = new Tree<>();
                type = TreeType.DOUBLE;
                out.println("Double Tree created!");
            } else {
                stringTree = new Tree<>();
                type = TreeType.STRING;
                out.println("String Tree created!");
            }
            while (true) {
                String command = in.readLine().trim();
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
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteFromTree(String s) {
        switch (type) {
            case INTEGER: {
                Integer value = Integer.parseInt(s);
                integerTree.delete(value);
            }
            case DOUBLE: {
                Double value = Double.parseDouble(s);
                doubleTree.search(value);
            }
            case STRING:
                stringTree.search(s);
        }
    }

    private boolean searchInTree(String s) {
        switch (type) {
            case INTEGER: {
                Integer value = Integer.parseInt(s);
                return integerTree.search(value);
            }
            case DOUBLE: {
                Double value = Double.parseDouble(s);
                return doubleTree.search(value);
            }
            case STRING:
                return stringTree.search(s);
        }
        return false;
    }

    private String drawTree() {
        switch (type) {
            case INTEGER:
                return integerTree.draw();
            case DOUBLE:
                return doubleTree.draw();
            case STRING:
                return stringTree.draw();
        }
        return "Unknown type of tree";
    }

    private boolean addInTree(String s) {
        switch (type) {
            case INTEGER: {
                Integer value = Integer.valueOf(s);
                return integerTree.insert(value);
            }
            case DOUBLE: {
                Double value = Double.valueOf(s);
                return doubleTree.insert(value);
            }
            case STRING: {
                return stringTree.insert(s);
            }
        }
        return false;
    }


}
