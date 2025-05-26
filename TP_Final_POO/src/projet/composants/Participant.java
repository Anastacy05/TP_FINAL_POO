package projet.composants;

import projet.mecanismes.ParticipantObserver;

public class Participant implements ParticipantObserver {

    private final String id;
    private final String nom;
    private final String email;

    public Participant(String id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void informer(String news) {

    }
}
