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
    @FXML private Button btnCerca;
    
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
        boolean ok = false;
        try {
            // TODO: integra la ricerca reale DB
        	InetAddress addr = InetAddress.getByName(null);
    		Socket socket=new Socket(addr, 8999);
    		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
    		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
    		UtenteRegistrato ur = new UtenteRegistrato();
    		ur.setMail(userId);
    		ur.setUserId(userId);
    		ur.setPassoword(pswd);
    		out.writeObject("LOGIN");
    		out.writeObject(ur);
    		ok = (boolean) in.readObject(); 
            out.close();
			in.close();
			socket.close();
        } catch (Exception e) {
         
        } finally {
        	
            btnCerca.setDisable(false); //riattiviamo pulsante
        }
        
        //se password e userId sono giusti
        if (ok) {
            SceneNavigator.setUserID(userId);
            SceneNavigator.switchToUtenteRegistrato();
        }else {
        	Helpers.showError("credenziali sbagliate");
        }
      
    }























}
