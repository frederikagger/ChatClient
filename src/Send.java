import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Send implements Runnable{
    private Socket socket;
    private DataOutputStream output;
    private Scanner scanner;
    private Client client;

    public Send(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            scanner = new Scanner(System.in); // takes input from terminal
            output = new DataOutputStream(socket.getOutputStream());  // sends output to the socket
            sendMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessages() throws IOException {
        String line = "";
        while (!line.equalsIgnoreCase("Quit")){ // keep reading until "Quit" is input
            try {
                line = scanner.nextLine();
                output.writeUTF(line);
            }
            catch(IOException i){
                i.printStackTrace();
                break;
            }
        }
        output.close(); // close the connection
        socket.close();
        scanner.close();

    }
}
