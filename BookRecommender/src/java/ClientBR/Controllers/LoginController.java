package ClientBR.Controllers;

import ClientBR.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ClassiCondivise.UtenteRegistrato;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;



public class LoginController {
    
    @FXML private TextField fUserID;
    @FXML private TextField pfPassword;
    @FXML private Button btnAccedi;
    @FXML private Button btnRegistrati;
    @FXML private Button btnHome;
    @FXML private Label lblError;
    
    @FXML private void initialize(){
        Helpers.clearError(lblError);
    }

    @FXML private void onRegistrati(){
        SceneNavigator.switchToRegister();
        Helpers.clearError(lblError);
    }  

    @FXML private void onHome() {
        SceneNavigator.switchToHome();
        Helpers.clearError(lblError);
    }
    
    @FXML private void onAccedi() {

        String userId = fUserID.getText().trim();
        String pswd = pfPassword.getText();

        if (!Helpers.validPswd(pswd)) {
            Helpers.showError("La password devere essere di almeno 8 caratteri", lblError);
            return;
        }

        //TODO: metodi per controllare password e userID
        try {
            // TODO: integra la ricerca reale DB
        	InetAddress addr = InetAddress.getByName(null);
    		Socket socket=new Socket(addr, 8999);
    		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
    		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
    		UtenteRegistrato ur;
    		ur.setMail(userId);
    		ur.setUserId(userId);
    		ur.setPassoword(pswd);
    		out.writeObject("LOGIN");
    		out.writeObject(ur);
    		boolean ok = (boolean) in.readObject(); 
        } catch (Exception | IOException |ClassNotFoundException e) {
         
        } finally {
        	out.close();
			in.close();
			socket.close();
            btnCerca.setDisable(false); //riattiviamo pulsante
        }
        //STUB
        //se password e userId sono giusti
        if (ok) {
            SceneNavigator.setUserID(userId);
            SceneNavigator.switchToUtenteRegistrato();
        }




       
    }























}
