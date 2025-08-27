package ClassiCondivise;

public class UtenteRegistrato implements Serializable{

	private static final long serialVersionUID = 1L;
    private String nomeCognome;
    private String codiceFiscale;
    private String mail;
    private String userId;
    private String password;
    private boolean controllo;

    public String getNomeCognome() {
        return this.nomeCognome;
    }

    public String getCodiceFiscale() {
    	return this.codiceFiscale;
    }
    public String getMail() {
        return this.mail;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getPassoword() {
        return this.password;
    }

    public void setNomeCognome(String nomeCognome) {
        this.nomeCognome = nomeCognome;
    }
    
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setUserId(String userId) {
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
