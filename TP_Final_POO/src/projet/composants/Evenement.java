package projet.composants;

import projet.mecanismes.EvenementObservable;
import projet.mecanismes.ParticipantObserver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Evenement implements EvenementObservable {

    private final String id;
    private String nom;
    private final Organisateur organisateur;
    private LocalDate date;
    private String lieu;
    private int capaciteMax;
    private List<ParticipantObserver> participants;
    private int nbplacesdispo;
    private boolean annule;

    public Evenement (String id, String nom, Organisateur organisateur, LocalDate date, String lieu, int capaciteMax) {
        this.id = id;
        this.nom = nom;
        this.organisateur = organisateur;
        this.date = date;
        this.lieu = lieu;
        this.capaciteMax = capaciteMax;
        nbplacesdispo = capaciteMax;
        this.annule = false;
    }

    public boolean ajouterParticipant(ParticipantObserver participant) {
        if (participants.size()<capaciteMax) {
            participants.add(participant);
            nbplacesdispo --;
            return true;
        }//Exception a creer
        return false;
    }

    public void supprimerParticipant(ParticipantObserver participant) {
        if (participants.contains(participant)) {
            nbplacesdispo++;
            participants.remove(participant);
        }
    }

    public void annuler() {
        annule = true;
        for (ParticipantObserver participant : participants) {
            notifierParticipant(participant, "annule");
        }
    }

    public String afficherDetails() {
        return nom + "//" + date + "//" + lieu + "//" + capaciteMax + "//" + nbplacesdispo;
    }

    public String afficherInfos() {
        return nom + "//" + date + "//" + lieu + "//" + capaciteMax;
    }

    public String[] Modifier(String news) {
        String ancient = afficherInfos();
        if (ancient.equals(news)) {
            return null;
        }
        String rapport = "";
        String[] news_liste = news.split("//");
        if (!nom.equals(news_liste[0])) {
            nom = news_liste[0];
            rapport += "nom:";
            rapport += nom;
        }
        if (!date.equals(LocalDateTime.parse(news_liste[1]))) {
            date = LocalDate.parse(news_liste[1]);
            rapport += "//date:";
            rapport += news_liste[1];
        }
        if (!lieu.equals(news_liste[2])) {
            lieu = news_liste[2];
            rapport += "//lieu:";
            rapport += lieu;
        }
        if (capaciteMax!=Integer.parseInt(news_liste[3])) {
            int cap = capaciteMax;
            capaciteMax = Integer.parseInt(news_liste[3]);
            nbplacesdispo = nbplacesdispo - cap + capaciteMax;
            rapport += "//capaciteMax:";
            rapport += news_liste[3];
            rapport += "//nbplacesdispo:";
            rapport += String.valueOf(nbplacesdispo);
        }

        String[] news_final = new String[news_liste.length + 1];
        for (int i=0; i< news_liste.length; i++) {
            news_final[i] = news_liste[i];
        }
        news_final[news_liste.length] = rapport;
        return news_final;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getNomOrganisateur() {
        return organisateur.getNom();
    }

    public Organisateur getOrganisateur() {
        return organisateur;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public List<ParticipantObserver> getParticipants() {
        return participants;
    }

    public List<Artiste> getArtistes() {
        return null;
    }
    public boolean ajouterArtiste(Artiste artiste) {
        return false;
    }

    public List<Intervenant> getIntervenants() {
        return null;
    }
    public boolean ajouterIntervenant(Intervenant intervenant) {
        return false;
    }
}
