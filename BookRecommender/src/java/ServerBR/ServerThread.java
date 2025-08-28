//<<<<<<< HEAD:BookRecommender/src/main/java/ServerBR/ServerThread.java
//	package ServerBR;
//=======

package ServerBR;
//>>>>>>> 17c85385528a759bc8ef0ed772bb2b9da97f2a28:BookRecommender/src/java/ServerBR/ServerThread.java

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ClassiCondivise.Libreria;
import ClassiCondivise.Libro;
import ClassiCondivise.UtenteRegistrato;
import ClientBR.Controllers.Helpers;

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
     public ServerThread(Socket s) {
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
                Libro l = new Libro();
                Libro l2 = new Libro();
                Libreria libreria = new Libreria();
                UtenteRegistrato u;
                boolean esito;
                switch(request) {
                    case "END":
                        break;
                    case "CONSULTA REPOSITORY TITOLO":
                        l = (Libro) in.readObject();
                       // Libro l2 = db.cercaLibroPerTitolo(l);
                        out.writeObject(l2);
                    case "CONSULTA REPOSITORY AUTORE":
                         l = (Libro) in.readObject();
                      //  Libro l2 = db.cercaLibroPerAutore(l);
                       // out.writeObject(l2);
                    case "CONSULTA REPOSITORY ANNO E AUTORE":
                         l = (Libro) in.readObject();
                       // Libro l2 = db.cercaLibroPerAutoreAnno(l);
                        out.writeObject(l2);

                    case "Registrazine":
                        u = (UtenteRegistrato) in.readObject();
                        
                        System.exit(1);
                       // esito = db.insertUtente(u);
                       // out.writeObject(esito);
                        break;
                    case "LOGIN":
                    	u = (UtenteRegistrato) in.readObject();
                    	////esito = db.login(u);
                       // out.writeObject(esito);
                       System.out.println("FERNANDO MENDOSA");
                        break;
               
                    case "INSETISCI VALUTAZIONE":
                        l = (Libro) in.readObject();
                      //  esito = db.iserisciValutazioni(l);
                       // out.writeObject(esito);
                        break;

                    case "REGISTRA LIBRERIA":
                        libreria = (Libreria) in.readObject();
                       // db.InserisciLibreria(libreria);
                    case "CONSIGLIA LIBRI":
                        String libriConsigliati = (String) in.readObject();
                        u = (UtenteRegistrato) in.readObject();
                      //  esito = db.InserisciConsigli(u, libriConsigliati);
                       // out.writeObject(esito);
                        break;
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

