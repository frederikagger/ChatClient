import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private Listen listen;
    private Send send;
    private Socket socket;
    private String username;
    private String ip;
    private int port;
    private GUI gui;
    private ExecutorService executor;

    public Client(String ip, int port, GUI gui) {
        try {
            this.gui = gui;
            this.socket = new Socket(ip, port); // establish a connection
        } catch (IOException e){
            new Client(ip, port, gui);
        }
        System.out.println("Connected");
        this.send = new Send(this.socket, this);
        this.listen = new Listen(this.socket, this.gui);
        this.ip = ip;
        this.port = port;
        executor = Executors.newFixedThreadPool(2);
        executor.execute(send);
        executor.execute(listen);
        executor.shutdown();
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public Listen getListen() {
        return listen;
    }

    public Send getSend() {
        return send;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /* public void setUsername(){
//        gui.getjTextArea().append("Enter username: \n");
        System.out.println("Enter username: ");
//        this.username = gui.getTextField().getText();
        this.username = scanner.next();
        while (this.username.length()>12){
            System.out.println("Username has to be less than 12 characters. Try again.\nEnter username: ");
            gui.getjTextArea().append("Username has to be less than 12 characters. Try again.\nEnter username: ");
            this.username = gui.getTextField().getText();
        }
    }*/
}