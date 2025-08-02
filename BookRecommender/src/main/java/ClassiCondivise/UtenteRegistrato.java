package ClassiCondivise;

public class UtenteRegistrato extends Utente{

    private String nomeCognome;
    private String mail;
    private String userId;
    private String password;
    private boolean controllo;

    public String nomeCognome() {
        return this.nomeCognome;
    }

    public String getmail() {
        return this.mail;
    }

    public String getuserId() {
        return this.userId;
    }

    public String getPassoword() {
        return this.password;
    }

    public void setnomeCognome(String nomeCognome) {
        this.nomeCognome = nomeCognome;
    }

    public void setmail(String mail) {
        this.mail = mail;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }

    public void setPassoword(String password) {
        this.password = password;
    }

    public boolean getControllo() {
        return this.controllo;
    }

    public void setControllo(boolean c) {
        this.controllo = c;
    }


}
