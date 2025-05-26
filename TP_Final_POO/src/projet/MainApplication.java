package projet;

import javafx.application.Application;
import javafx.stage.Stage;
import projet.composants.Concert;
import projet.composants.Conference;
import projet.composants.Evenement;
import projet.composants.Organisateur;
import projet.interfaces.AccueilUI;
import projet.mecanismes.GestionEvenement;
import projet.mecanismes.ParticipantObserver;
import projet.serialisation.Deserialiser;
import projet.serialisation.Serialiser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainApplication extends Application {

    private final GestionEvenement gestionEvenement = GestionEvenement.getInstance();

    @Override
    public void start(Stage stage) throws Exception {
        List<Concert> concerts;
        List<Conference> conferences;
        try {
            concerts = Deserialiser.deserialiserConcerts("src/concert.json");
            conferences = Deserialiser.deserialiserConference("src/conference.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (concerts!=null) {
            gestionEvenement.getEvenements().addAll(concerts);
        }
        if (conferences!=null) {
            gestionEvenement.getEvenements().addAll(conferences);
        }
        List<Organisateur> organisateurs;
        try {
            organisateurs = Deserialiser.deserialiserOrganisateur("src/organisateur.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (organisateurs!= null) {
            gestionEvenement.getOrganisateurs().addAll(organisateurs);
        }
        AccueilUI accueilUI = new AccueilUI(stage, this);
        accueilUI.initialiseUI();
    }

    /*@Override
    public void stop() throws Exception {
        File file;
        boolean result;

        file = new File("src/concert.json"); result = file.delete(); result = file.createNewFile();
        file = new File("src/conference.json"); result = file.delete(); result = file.createNewFile();
        for (Evenement evenement : gestionEvenement.getEvenements()) {
            if (evenement.getClass().getSimpleName().equals("Concert")) {
                Serialiser.serialiserConcert(evenement, "src/concert.json");
            } else {
                Serialiser.serialiserConference(evenement, "src/conference.json");
            }
        }

        file = new File("src/organisateur.json");
        result = file.delete();
        result = file.createNewFile();
        Serialiser.serialiserOrganisateur(gestionEvenement.getOrganisateurs(), "src/organisateur.json");
        super.stop();
    }*/

    public ParticipantObserver loginParticipant(String id, Evenement evenement) {
        if (gestionEvenement.getEvenements().contains(evenement)) {
            for (ParticipantObserver participant : evenement.getParticipants()) {
                if (participant.getId().equals(id)) {
                    return participant;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public Organisateur loginOrganisateur(String id) {
        for (Organisateur organisateur : gestionEvenement.getOrganisateurs()) {
            if (organisateur.getId().equals(id)) {
                return organisateur;
            }
        }
        return null;
    }

    public GestionEvenement getGestionEvenement() {
        return gestionEvenement;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
