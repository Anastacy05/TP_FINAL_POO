package projet.serialisation;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import projet.composants.Concert;
import projet.composants.Conference;
import projet.composants.Organisateur;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Deserialiser {

    public static List<Concert> deserialiserConcerts(String file) throws IOException {
        File fichier = new File(file);
        if (!fichier.exists()) {
            throw new IOException("Le fichier " + file + " n'existe pas");
        }
        if (fichier.length() == 0) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        var listType = typeFactory.constructCollectionType(List.class, Concert.class);
        return objectMapper.readValue(fichier, listType);
    }

    public static List<Conference> deserialiserConference(String file) throws IOException{
        File fichier = new File(file);
        if (!fichier.exists()) {
            throw new IOException("Le fichier " + file + " n'existe pas");
        }
        if (fichier.length() == 0) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        var listType = typeFactory.constructCollectionType(List.class, Conference.class);
        return objectMapper.readValue(fichier, listType);
    }

    public static List<Organisateur> deserialiserOrganisateur(String file) throws IOException{
        File fichier = new File(file);
        if (!fichier.exists()) {
            throw new IOException("Le fichier " + file + " n'existe pas");
        }
        if (fichier.length() == 0) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        var listType = typeFactory.constructCollectionType(List.class, Organisateur.class);
        return objectMapper.readValue(fichier, listType);
    }

}
