import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Listen implements Runnable {
    private Socket socket;
    private DataInputStream input;
    private GUI gui;

    public Listen(Socket socket, GUI gui) {
        this.socket = socket;
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream())); // takes input from the server socket
            readMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessages() throws IOException {
        String line = "";  // reads message from client
        while (socket.isConnected()) {
            try {
                line = input.readUTF();
                gui.getjTextArea().append(line+"\n");
                if (line.equals("J_OK")){
                    gui.getjPanel().remove(gui.getjLabel());
                    gui.getjPanel().remove(gui.getConnect());
                    gui.getClient().setUsername(gui.getTextField().getText());
                    gui.getTextField().setText("");
                }
                System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
                input.close();
                break;
            }
        }
    }
}