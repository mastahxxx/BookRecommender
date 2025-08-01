package ClientBR;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import ClassiCondivise.Libreria;
import ClassiCondivise.Libro;
import ClassiCondivise.UtenteRegistrato;
import ServerBR.DataBase;

public class Client {
    private DataBase db;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;

    public Client(Socket s, DataBase d) {
        this.db = d;
        this.socket = s;
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {

        }
    }

    public void exec(){
        InetAddress addr = InetAddress.getByName(null);
        Socket clientSocket = new Socket(addr, 8080);
        System.out.println();
    }
}
