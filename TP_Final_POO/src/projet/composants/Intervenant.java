package projet.composants;

public class Intervenant extends Participant{

    private String domaine;

    public Intervenant(String id, String nom, String email) {
        super(id, nom, email);
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }
}
