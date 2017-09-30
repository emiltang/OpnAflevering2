package assignment2.Client;

import javax.swing.*;
import java.awt.*;

/**
 * GUI for the application, built using WindowMaker.
 * All logic is in ClientController
 *
 * @author ups
 */
@SuppressWarnings("WeakerAccess")
public class ClientGUI {

    private JFrame frame;
    private JTextField txtName;
    private JTextField txtAmount;
    private JTextField txtPrefix;

    /**
     * Create the application.
     */
    public ClientGUI() {
        initialize();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ClientGUI window = new ClientGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 352);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblInventory = new JLabel("Inventory");
        lblInventory.setBounds(6, 6, 61, 16);
        frame.getContentPane().add(lblInventory);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(6, 30, 262, 242);
        frame.getContentPane().add(scrollPane);

        final JTextPane entriesDisplay = new JTextPane();
        entriesDisplay.setText("entries");
        scrollPane.setViewportView(entriesDisplay);

        JLabel lblItem = new JLabel("Name");
        lblItem.setBounds(292, 41, 49, 16);
        frame.getContentPane().add(lblItem);

        txtName = new JTextField();
        txtName.setText("name");
        txtName.setBounds(342, 35, 102, 28);
        frame.getContentPane().add(txtName);
        txtName.setColumns(10);

        JLabel lblQuantity = new JLabel("Quantity");
        lblQuantity.setBounds(292, 86, 61, 16);
        frame.getContentPane().add(lblQuantity);

        txtAmount = new JTextField();
        txtAmount.setText("amount");
        txtAmount.setBounds(352, 80, 92, 28);
        frame.getContentPane().add(txtAmount);
        txtAmount.setColumns(10);

        JButton btnIncrease = new JButton("Increase");
        btnIncrease.addActionListener(e -> ClientController.get().increaseAction(txtName.getText(), txtAmount.getText()));
        btnIncrease.setBounds(302, 124, 117, 29);
        frame.getContentPane().add(btnIncrease);

        JButton btnDecrease = new JButton("Decrease");
        btnDecrease.addActionListener(e -> ClientController.get().decreaseAction(txtName.getText(), txtAmount.getText()));
        btnDecrease.setBounds(302, 153, 117, 29);
        frame.getContentPane().add(btnDecrease);

        JLabel lblFilter = new JLabel("Filter:");
        lblFilter.setBounds(6, 274, 41, 16);
        frame.getContentPane().add(lblFilter);

        txtPrefix = new JTextField();
        txtPrefix.setText("prefix");
        txtPrefix.setBounds(59, 268, 92, 28);
        frame.getContentPane().add(txtPrefix);
        txtPrefix.setColumns(10);

        JLabel statusArea = new JLabel("Status: initializing");
        statusArea.setBounds(6, 302, 438, 16);
        frame.getContentPane().add(statusArea);

        ClientController.get().setInventoryDisplay(entriesDisplay);
        ClientController.get().setStatusArea(statusArea);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> ClientController.get().searchAction(txtPrefix.getText()));
        btnSearch.setBounds(147, 269, 69, 29);
        frame.getContentPane().add(btnSearch);

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> {
            ClientController.get().clearAction();
            txtPrefix.setText("");
        });
        btnClear.setBounds(207, 269, 61, 29);
        frame.getContentPane().add(btnClear);

    }

}
