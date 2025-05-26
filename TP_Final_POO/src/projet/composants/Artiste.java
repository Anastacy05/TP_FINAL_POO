package projet.composants;

public class Artiste extends Participant{

    private String type;

    public Artiste (String id, String nom, String email) {
        super(id, nom, email);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
