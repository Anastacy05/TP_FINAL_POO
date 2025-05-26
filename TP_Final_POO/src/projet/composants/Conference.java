package projet.composants;

import projet.mecanismes.ParticipantObserver;

import java.time.LocalDate;
import java.util.List;

public class Conference extends Evenement{

    private String theme;
    private List<Intervenant> intervenants;

    public Conference (String id, String nom, Organisateur organisateur, LocalDate date, String lieu, int capaciteMax, String theme) {
        super(id, nom, organisateur, date, lieu, capaciteMax);
        this.theme = theme;
    }

    public List<Intervenant> getIntervenants() {
        return intervenants;
    }

    public String afficherDetails() {
        String details = super.afficherDetails();
        return details + "//" + theme + "//" + intervenants;
    }

    public String afficherInfos() {
        String infos = super.afficherInfos();
        return infos + "//" + theme;
    }

    public String[] Modifier(String news) {
        String[] result = super.Modifier(news);
        if (result == null) {
            return null;
        }
        String rapport = result[5];
        if (!theme.equals(result[4])) {
            theme = result[4];
            rapport += "//theme";
            rapport += theme;
        }
        for (ParticipantObserver participantObserver : intervenants) {
            notifierParticipant(participantObserver, rapport);
        }
        return new String[]{"ok", "yes"};
    }

    public boolean ajouterIntervenant(Intervenant intervenant) {
        if (this.getParticipants().size() + intervenants.size() < this.getCapaciteMax()) {
            intervenants.add(intervenant);
            return true;
        }//Exception a creer
        return false;
    }

    public void supprimerIntervenant(Intervenant intervenant) {
        intervenants.remove(intervenant);
    }

    public String getTheme() {
        return theme;
    }

}
