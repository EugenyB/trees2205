
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Main Class for Tree Client.
 * Built with Java Swing Library
 */
public class MainFrame extends JFrame {

    /**
     * Constructor for MainFrame
     * @param title window title
     */
    public MainFrame(String title) {
        super(title);
        init();
    }

    /**
     * Initializing for main window:
     * <ul>
     * <li>Creates window elements</li>
     * <li>Creates event handlers</li>
     * </ul>
     */
    private void init() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container pane = getContentPane();
        // top
        JPanel topPanel = new JPanel();
        pane.add(topPanel, BorderLayout.NORTH);
        addressField = new JTextField(10);
        topPanel.add(new JLabel("Server address"));
        topPanel.add(addressField);
        startButton = new JButton("Connect");
        startButton.addActionListener(a -> connectToServer());
        intButton = new JRadioButton("Integer", true);
        doubleButton = new JRadioButton("Double");
        stringButton = new JRadioButton("String");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(intButton);
        buttonGroup.add(doubleButton);
        buttonGroup.add(stringButton);
        topPanel.add(new JLabel("Tree element type"));
        topPanel.add(intButton);
        topPanel.add(doubleButton);
        topPanel.add(stringButton);
        topPanel.add(startButton);

        // center
        JScrollPane scrollPane = new JScrollPane();
        textArea = new JTextArea(10, 20);
        scrollPane.setViewportView(textArea);
        pane.add(scrollPane);
        textArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        textArea.setEditable(false);

        // bottom
        JPanel bottomPanel = new JPanel();
        pane.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(new JLabel("Element"));
        elementField = new JTextField(10);
        bottomPanel.add(elementField);
        addButton = new JButton("Add element");
        this.getRootPane().setDefaultButton(addButton);
        addButton.addActionListener(a -> processAdd());
        deleteButton = new JButton("Delete element");
        deleteButton.addActionListener(a -> processDelete());
        findButton = new JButton("Find element");
        findButton.addActionListener(a -> processFind());
        bottomPanel.add(addButton);
        bottomPanel.add(findButton);
        bottomPanel.add(deleteButton);
        addButton.setEnabled(false);
        findButton.setEnabled(false);
        deleteButton.setEnabled(false);
        elementField.setEnabled(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    if (socket!=null) socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        pack();
    }

    /**
     * Handles "Find element" button - sends query to Server with {@link #findElement(String) findElement}
     */
    private void processFind() {
        String text = elementField.getText();
        if (treeType.isValidType(text)) {
            findElement(text);
            elementField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Wrong type of Element");
        }
    }

    /**
     * Handles "Delete element" button - sends query to Server with {@link #deleteElement(String) deleteElement}
     */
    private void processDelete() {
        String text = elementField.getText();
        if (treeType.isValidType(text)) {
            deleteElement(text);
            elementField.setText("");
            drawTree();
        } else {
            JOptionPane.showMessageDialog(this, "Wrong type of Element");
        }
    }

    /**
     * Handles "Add element" button - sends query to Server with {@link #addElement(String) addElement}
     */
    private void processAdd() {
        String text = elementField.getText();
        if (treeType.isValidType(text)) {
            addElement(text);
            elementField.setText("");
            drawTree();
        } else {
            JOptionPane.showMessageDialog(this, "Wrong type of Element");
        }
    }

    /**
     * Sends request for tree representation.
     * Extracts representation from response and shows it in textArea
     * replaces "|" symbol in response to "\n"
     */
    private void drawTree() {
        try {
            out.println("draw");
            String response = in.readLine().replaceAll("\\|", "\n");
            SwingUtilities.invokeLater(()->textArea.setText(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends request for add element
     * @param text text representation of adding element
     */
    private void addElement(String text) {
        try {
            out.println("insert " + text);
            String response = in.readLine();
            JOptionPane.showMessageDialog(this, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends request for find element
     * @param text text representation of searching element
     */
    private void findElement(String text) {
        try {
            out.println("search " + text);
            String response = in.readLine();
            JOptionPane.showMessageDialog(this, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends request for delete element
     * @param text text representation of deleting element
     */
    private void deleteElement(String text) {
        try {
            out.println("delete " + text);
            String response = in.readLine();
            JOptionPane.showMessageDialog(this, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connects to server by IP address and port 1234
     */
    private void connectToServer() {
        String host = addressField.getText();
        try {
            socket = new Socket(host, 1234);
            JOptionPane.showMessageDialog(this, "Connected!");
            toggleControls();
            processConnection();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error while connecting to server");
        }
    }

    /**
     * Toggle buttons state to prevent second connection
     */
    private void toggleControls() {
        addressField.setEnabled(false);
        startButton.setEnabled(false);
        intButton.setEnabled(false);
        doubleButton.setEnabled(false);
        stringButton.setEnabled(false);
        addButton.setEnabled(true);
        findButton.setEnabled(true);
        deleteButton.setEnabled(true);
        elementField.setEnabled(true);
    }

    /**
     * Try to connect to server and sends TreeType for in
     */
    private void processConnection() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            in.readLine();
            if (intButton.isSelected()) {
                out.println("1");
                treeType = TreeType.IntegerTree;
            }
            else if (doubleButton.isSelected()) {
                out.println("2");
                treeType = TreeType.DoubleTree;
            }
            else {
                out.println("3");
                treeType = TreeType.StringTree;
            }
            String response = in.readLine();
            SwingUtilities.invokeLater(()->textArea.setText(response));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Program entry point
     * @param args command line args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame("Tree Client").setVisible(true));
    }

    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private JTextField addressField;
    private JTextArea textArea;
    private JButton startButton;
    private JRadioButton intButton;
    private JRadioButton doubleButton;
    private JRadioButton stringButton;
    private JTextField elementField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton findButton;

    private TreeType treeType;
}