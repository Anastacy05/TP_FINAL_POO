package projet.composants;

import projet.mecanismes.ParticipantObserver;

import java.time.LocalDate;
import java.util.List;

public class Concert extends Evenement{

    private String genre_musical;
    private List<Artiste> artistes;

    public Concert (String id, String nom, Organisateur organisateur, LocalDate date, String lieu, int capaciteMax, String genremusical) {
        super(id, nom, organisateur, date, lieu, capaciteMax);
        this.genre_musical = genremusical;
    }

    public List<Artiste> getArtistes() {
        return artistes;
    }

    public String afficherDetails() {
        String details = super.afficherDetails();
        return details + "//" + genre_musical + "//" + artistes;
    }

    @Override
    public String afficherInfos() {
        String infos = super.afficherInfos();
        return infos + "//" + genre_musical;
    }

    public String[] Modifier(String news) {
        String[] result = super.Modifier(news);
        if (result == null) {
            return null;
        }
        String rapport = result[5];
        if (!genre_musical.equals(result[4])) {
            genre_musical = result[4];
            rapport += "//theme";
            rapport += genre_musical;
        }
        for (ParticipantObserver participantObserver : artistes) {
            notifierParticipant(participantObserver, rapport);
        }
        return new String[]{"ok", "yes"};
    }

    public boolean ajouterArtiste(Artiste artiste) {
        if (this.getParticipants().size() + artistes.size() < this.getCapaciteMax()) {
            artistes.add(artiste);
            return true;
        }//Exception a creer
        return false;
    }

    public void supprimerArtiste(Artiste artiste) {
        artistes.remove(artiste);
    }

    public String getGenre_musical() {
        return genre_musical;
    }

}
