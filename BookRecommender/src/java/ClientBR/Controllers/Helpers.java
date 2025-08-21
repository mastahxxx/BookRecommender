package ClientBR.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Helpers {  // Helpers, metodi di supporto
                                                                  

public static void clearError(Label lblError) { //serve per resettare in automatico il lable di errore
        lblError.setText("");          
}

public static void showError(String err, Label lblError) {
    lblError.setText(err);
    lblError.setStyle("-fx-text-fill: red;"); //gli errori verranno mostrati in rosso
}

public static void showInfo(String info, Label lblError){
    lblError.setText(info);
    lblError.setStyle("-fx-text-fill: green;"); //le info verranno mostrati in verde
}


public static void clearFields(TextInputControl... controls) { //pulisce tutti i fields a schermo
    if (controls == null) return;
    for (TextInputControl c : controls) {
        if (c != null) c.clear();
    }
}

//controllo codice fiscale secondo le seguenti regole: caratteri validi da A a Z e cifre da 0 a 9, esattamente 16 caratteri.
public static boolean validCF(String CF) { 
    return CF.matches("[A-Z0-9]{16}$"); 

}

//controllo password, almeno 8 caratteri, di qualsiasi tipo
public static boolean validPswd(String psw) {
    return psw.matches("^.{8,}$");
}

//controllo mail basilare, es: a@b.c
public static boolean validEmail(String mail) {
    return mail.matches(".+@.+\\..+" );  
}

//classi da fare col DB
//TODO public static boolean emailAlreadyUsed
//TODO public static boolean userIDAlreadyUsed
//TODO public static boolean uSerIDIsCorrect
//TODO public static boolean passwordISCorrect
}
