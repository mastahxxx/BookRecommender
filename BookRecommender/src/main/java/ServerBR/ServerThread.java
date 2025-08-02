package ServerBR;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ClassiCondivise.Libreria;
import ClassiCondivise.Libro;
import ClassiCondivise.UtenteRegistrato;

public class ServerThread extends Thread {
    private DataBase db;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;

    public ServerThread(Socket s, DataBase d) {
        this.db = d;
        this.socket = s;
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        }catch(IOException e) {

        }
    }

    public void run() {
        try {
            while(true) {
                String request = "";
                request = (String) in.readObject();
                Libro l;
                Libreria libreria = new Libreria();
                UtenteRegistrato u;
                switch(request) {
                    case "END":
                        break;
                    case "CONSULTA REPOSITORY TITOLO":
                        l = (Libro) in.readObject();
                        Libro l2 = db.cercaLibroPerTitolo(l);
                        out.writeObject(l2);
                    case "CONSULTA REPOSITORY AUTORE":
                        Libro l = (Libro) in.readObject();
                        Libro l2 = db.cercaLibroPerTitolo(l);
                        out.writeObject(l2);
                    case "CONSULTA REPOSITORY ANNO E AUTORE":
                        Libro l = (Libro) in.readObject();
                        Libro l2 = db.cercaLibroPerTitolo(l);
                        out.writeObject(l2);

                    case "Registrazine":
                        u = (UtenteRegistrato) in.readObject();
                        db.insertUtente(u);
                        break;

                    case "INSETISCI VALUTAZIONE":
                        l = (Libro) in.readObject();
                        db.iserisciValutazioni(l);
                        break;

                    case "REGISTRA LIBRERIA":
                        libreria = (Libreria) in.readObject();
                        db.InserisciLibreria(libreria);
                    case "CONSIGLIA LIBRI":
                        String libriConsigliati = (String) in.readObject();
                        u = (UtenteRegistrato) in.readObject();
                        db.InserisciConsigli(u, libriConsigliati);

                    default:
                        break;
                }
            }

        }catch(IOException | ClassNotFoundException e) {}
        finally {
            try {
                socket.close();
            }catch(IOException e) {}

        }


    }

}
