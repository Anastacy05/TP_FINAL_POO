package projet.serialisation;

import com.fasterxml.jackson.databind.ObjectMapper;
import projet.composants.Evenement;
import projet.composants.Organisateur;

import java.io.File;
import java.util.List;

public class Serialiser {

    public static void serialiserEvenement(List<Evenement> evenements, String fileconcert, String fileconference) throws Exception {
        File fichierconcert = new File(fileconcert);
        File fichierconference = new File(fileconference);
        try {
			for (Evenement evenement : evenements) {
                ObjectMapper objectMapper = new ObjectMapper();
				if (evenement.getClass().getSimpleName().equals("Concert")) {
					objectMapper.writeValue(fichierconcert, evenement);
				} else {
					objectMapper.writeValue(fichierconference, evenement);
				}
			}
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void serialiserOrganisateur(List<Organisateur> organisateurs, String file) throws Exception {
        File fichier = new File(file);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(fichier, organisateurs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
