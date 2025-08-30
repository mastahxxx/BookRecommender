package ClientBR.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ClientBR.SceneNavigator;
import ClientBR.Controllers.Helpers;
import ClassiCondivise.UtenteRegistrato;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class RegisterController {

    @FXML private TextField fNome;
    @FXML private TextField fCognome;
    @FXML private TextField fCodiceFiscale;
    @FXML private TextField fEmail;
    @FXML private TextField fUserID;
    @FXML private PasswordField pfPassword;
    @FXML private PasswordField pfConfermaPassword;
    @FXML private Label lblError;

    @FXML private void initialize() {
        Helpers.clearError(lblError);
        
    }

    //Bottoni torna, accedi e conferma
    @FXML private void onHome() {
        SceneNavigator.switchToHome();
        Helpers.clearError(lblError);
    }

    @FXML private void onAccedi(){
        SceneNavigator.switchToLogin();
        Helpers.clearError(lblError);
    }

    @FXML private void onConferma(){ //qui tutti i dati vengono salvati nelle proprie stringhe e fatto qualche controllo, manca usare i dati ricavati
        
        Helpers.clearError(lblError);

        String nome = fNome.getText().trim();
        String cognome = fCognome.getText().trim();
        String cf = fCodiceFiscale.getText().trim().toUpperCase();
        String email = fEmail.getText().trim();
        String userId = fUserID.getText().trim();
        String pswd = pfPassword.getText();
        String pswd2 = pfConfermaPassword.getText();
        boolean controllo = true;

        //validazioni base, lato client prima di consultare il db
        if (nome.isEmpty() || cognome.isEmpty() || cf.isEmpty() || email.isEmpty() || userId.isEmpty() || pswd.isEmpty() || pswd2.isEmpty() ) {
            Helpers.showError("completa tutti i campi", lblError);
            controllo = false;
            return;
        }

        if(!Helpers.validCF(cf)) {
            Helpers.showError("cofice fiscale errato, inserisci 16 caratteri alfanumerici",lblError);
            controllo = false;
            return;
        }

        if(!Helpers.validEmail(email)) {
            Helpers.showError("email non valida",lblError);
            controllo = false;
            return;
        }

        if (!pswd.equals(pswd2)) {
            Helpers.showError("le password non coincidono",lblError);
            controllo = false;
            return;
        }

        if(!Helpers.validPswd(pswd)) {
            Helpers.showError("password non valida: la password deve avere almeno 8 caratteri",lblError);
            controllo = false;
            return;
        }

        if(!Helpers.emailAlreadyUsed(email)){   
            Helpers.showError("email gia in uso",lblError);
			controllo = false;
            return;
        }


        if(!Helpers.userIDAlreadyUsed(userID)){  
        	Helpers.showError("UserId già in uso",lblError);
        	controllo = false;
        	return;
        }         

        boolean ok = false;
        if(controllo) {
            try {
            	InetAddress addr = InetAddress.getByName(null);
        		Socket socket=new Socket(addr, 8999);
        		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        		UtenteRegistrato ur = new UtenteRegistrato();
        		String nomeCognome = nome + " " + cognome;
        		ur.setNomeCognome(nomeCognome);
        		ur.setCodiceFiscale(cf);
        		ur.setMail(email);
        		ur.setUserId(userId);
        		ur.setPassoword(pswd);
        		out.writeObject("Registrazine");
        		out.writeObject(ur);
        		ok = (boolean) in.readObject(); 
                out.close();
    			in.close();
    			socket.close();
            } catch (Exception e) {
             
            }
        }
        
        if (ok) {
            Helpers.clearFields(fNome,fCognome,fEmail,fCodiceFiscale,fUserID,pfPassword,pfConfermaPassword);
            Helpers.showInfo("Registrazione effettuata, ora puoi accedere",lblError);
        } else {
            Helpers.showError("Registrazione non riuscita, UserID o email giaà in uso",lblError); //dipende dalla logica del DB
        }
    }
  
}
