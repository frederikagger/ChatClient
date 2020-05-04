import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener {
    private JFrame frame;
    private JButton send;
    private JButton connect;
    private JTextField textField;
    private JTextArea jTextArea;
    private JPanel jPanel;
    private MigLayout migLayout;
    private Client client;
    private JScrollPane scroll;
    private JLabel jLabel;
    private Protocol protocol = new Protocol();

    public GUI(String IP, int port) {
        this.migLayout = new MigLayout();
        this.frame = new JFrame("Chat");
        this.send = new JButton("   Send   ");
        this.connect = new JButton("Connect");
        this.textField = new JTextField(60);
        this.jTextArea = new JTextArea(10,60);
        this.scroll = new JScrollPane(jTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.jPanel = new JPanel();
        client = new Client(IP, port, this);
        jLabel = new JLabel("Enter username below and then click connect: ");
        jPanel.setLayout(migLayout);
        jPanel.add(jTextArea, "span");
        jPanel.add(jLabel, "span 2");
        jPanel.add(connect, "wrap");
        jPanel.add(textField, "span 2");
        jPanel.add(send);
        frame.add(scroll);
        frame.getContentPane().add(jPanel);
        // frame.getContentPane().add(scroll);
        frame.setSize(500,250);
        frame.setLocation(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        send.setBackground(Color.GRAY);
        connect.setBackground(Color.GRAY);
        send.addActionListener(this::actionPerformed);
        connect.addActionListener(this::actionPerformed);
        send.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"),"sendMessage");
        send.getActionMap().put("sendMessage", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DataOutputStream output = new DataOutputStream(client.getSocket().getOutputStream());
                    output.writeUTF(protocol.data(client.getUsername(), textField.getText()));
                    textField.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public JTextArea getjTextArea() {
        return jTextArea;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JPanel getjPanel() {
        return jPanel;
    }

    public JButton getConnect() {
        return connect;
    }

    public JLabel getjLabel() {
        return jLabel;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connect){
            client.setUsername(textField.getText());
            try {
                DataOutputStream output = new DataOutputStream(client.getSocket().getOutputStream());
                output.writeUTF(protocol.join(textField.getText(),client.getIp(),client.getPort()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        if (e.getSource() == send){
            try {
                DataOutputStream output = new DataOutputStream(client.getSocket().getOutputStream());
                output.writeUTF(protocol.data(client.getUsername(), textField.getText()));
                textField.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}