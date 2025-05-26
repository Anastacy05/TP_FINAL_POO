package projet.composants;

import java.util.ArrayList;
import java.util.List;

public class Organisateur extends Participant{

    private final List<Evenement> evenements;

    public Organisateur(String id, String nom, String email) {
        super(id, nom, email);
        evenements = new ArrayList<>();
    }

    public void organiser(Evenement evenement) {
        evenements.add(evenement);
    }

    public List<Evenement> getEvenements() {
        return evenements;
    }
}
