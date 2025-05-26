package projet.serialisation;

import com.fasterxml.jackson.databind.ObjectMapper;
import projet.composants.Concert;
import projet.composants.Conference;
import projet.composants.Organisateur;

import java.io.File;
import java.util.List;

public class Serialiser {

    public static void serialiserConcert(Concert concert, String file) throws Exception {
        File fichier = new File(file);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(fichier, concert);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void serialiserConference(Conference conference, String file) throws Exception {
        File fichier = new File(file);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(fichier, conference);
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
