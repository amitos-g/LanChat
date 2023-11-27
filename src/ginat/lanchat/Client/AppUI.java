package ginat.lanchat.Client;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static javax.swing.JOptionPane.showMessageDialog;

public class AppUI {

    private String name = "@";
    private JFrame frame;
    private JMenuBar menuBar;
    private JScrollPane scrollPane;
    private JTextArea textField;
    private JTextArea inputField;

    public AppUI() {
        initTextField();
        initInputField();
        initMenu();
        initFrame();
    }

    public void welcome(String message){
        showMessageDialog(null, message);
    }
    public void error(){
        showMessageDialog(null, "The Chat Is Closed");
        System.exit(0);
    }
    public String promptForName(){
        var nameFrame = new JFrame();
        nameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nameFrame.setSize(1200, 600);
        nameFrame.setLayout(null);
        nameFrame.setResizable(false);
        nameFrame.setLocationRelativeTo(null);

        var inputLabel = new JLabel("What's Your Name?");
        inputLabel.setFont(new Font("Arial", Font.BOLD,30));
        inputLabel.setBounds(465, 100, 500, 30);


        var inputName = new JTextArea();
        inputName.setLineWrap(false);
        inputName.setWrapStyleWord(false);
        inputName.setFont(new Font("Arial",Font.PLAIN,20));
        inputName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        inputName.setBounds(500, 200, 200, 30);
        inputName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if (inputName.getText().strip().isBlank()) {
                        e.consume();
                        return;
                    }

                    name = inputName.getText();
                    nameFrame.dispose();
                    start();
                    e.consume();
                }
            }
        });


        nameFrame.add(inputName);
        nameFrame.add(inputLabel);
        nameFrame.setVisible(true);
        while (name.equals("@") && nameFrame.isActive()) {

        }
        return name;
    }



    public void start(){
        frame.setVisible(true);
    }
    private void initInputField(){
        inputField = new JTextArea();
        inputField.setLineWrap(false);
        inputField.setWrapStyleWord(false);
        inputField.setFont(new Font("Arial",Font.PLAIN,20));
        inputField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        inputField.addKeyListener(new KeyAdapter() {


            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if (inputField.getText().strip().isBlank()) {
                        e.consume();
                        return;

                    }
                    String message = formatMessage(inputField.getText());
                    Client.sendToServer(message);
                    inputField.setText(" ");
                    inputField.removeAll();
                    e.consume();
                }
            }

        });
    }
    private void initTextField() {
        textField = new JTextArea();
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        textField.setEditable(false);
        textField.setFont(new Font("Arial",Font.BOLD,14));
        scrollPane = new JScrollPane(textField);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Set the caret to always scroll to the bottom
        ((DefaultCaret) textField.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }
    private void initMenu() {
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Exit");
        menu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                System.exit(0);
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });
        menuBar.add(menu);
    }

    private void initFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 600);
        frame.setLayout(null);

        menuBar.setBounds(0, 0, 1200, 30);

        // Set bounds for scroll pane
        scrollPane.setBounds(100, 60, 1000, 400);

        inputField.setBounds(100, 475, 1000, 30);

        // Add components to the frame
        frame.add(menuBar);
        frame.add(scrollPane);
        frame.add(inputField);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    public JFrame getFrame() {
        return this.frame;
    }


    public String formatMessage(String message) {
        return "%s -> %s\n\n".formatted(name, message);

    }
    public void appendMessage(String message){
        textField.append(message);
    }
    public void appendMessageName(String name,String message){
        textField.append("~%s~ \n%s\n\n".formatted(name, message));
    }



}
