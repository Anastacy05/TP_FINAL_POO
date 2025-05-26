package projet.mecanismes;

import projet.composants.Evenement;
import projet.composants.Organisateur;

import java.util.ArrayList;
import java.util.List;

public class GestionEvenement {

    private static  GestionEvenement instance;

    private GestionEvenement() {}

    private List<Evenement> evenements = new ArrayList<>();
    private List<Organisateur> organisateurs = new ArrayList<>();

    public boolean AjouterEvenement(Evenement evenement) {
        if (evenements.isEmpty()) {
            evenements.add(evenement);
            return true;
        }
        boolean isin = false;
        for (Evenement ev : evenements) {
            if (ev.equals(evenement) || ev.getId().equals(evenement.getId())) {
                isin = true;
            }
        }
        if (!isin) {
            evenements.add(evenement);
            return true;
        }
        return false;
    }

    public void SupprimerEvenement(Evenement evenement) {
        evenements.remove(evenement);
    }

    public void RechercherEvenement(Evenement evenement) {};

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public boolean AjouterOrganisateur(Organisateur organisateur) {
        if (organisateurs.isEmpty()) {
            organisateurs.add(organisateur);
            return true;
        }
        boolean isin = false;
        for (Organisateur org : organisateurs) {
            if (org.equals(organisateur) || org.getNom().equals(organisateur.getNom())) {
                isin = true;
            }
        }
        if (!isin) {
            organisateurs.add(organisateur);
            return true;
        }
        return false;
    }

    public List<Organisateur> getOrganisateurs() {
        return organisateurs;
    }

    public static GestionEvenement getInstance() {
        if (instance == null) {
            instance = new GestionEvenement();
        }
        return instance;
    }

}
