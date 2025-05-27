package projet.interfaces;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import projet.MainApplication;
import projet.composants.*;
import projet.mecanismes.ParticipantObserver;

import java.io.File;

public class ParticipantUI {

    private boolean organisateur = false;
    private boolean artiste = false;
    private boolean intervenant = false;

    private final Stage primaryStage;
    private EvenementUI evenementUI = null;
    private Evenement evenement = null;
    private ModifsUI modifsUI = null;
    private MenuUI menuUI = null;
    private final MainApplication mainApplication;

    private final double WINDOW_WIDTH_RATIO = 0.7; // 70% de la largeur de l'écran
    private final double WINDOW_HEIGHT_RATIO = 0.8; // 80% de la hauteur de l'écran
    private final double MIN_WIDTH = 800;
    private final double MIN_HEIGHT = 600;

    public ParticipantUI(Stage primaryStage, MainApplication mainApplication) {
        this.primaryStage = primaryStage;
        this.mainApplication = mainApplication;
    }

    public void setOrganisateur(boolean organisateur) {
        this.organisateur = organisateur;
    }

    public void setArtiste(boolean artiste) {
        this.artiste = artiste;
    }

    public void setIntervenant(boolean intervenant) {
        this.intervenant = intervenant;
    }

    public void setModifsUI(ModifsUI modifsUI) {
        this.modifsUI = modifsUI;
    }

    public void setEvenementUI(EvenementUI evenementUI) {
        this.evenementUI = evenementUI;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public void setMenuUI(MenuUI menuUI) {
        this.menuUI = menuUI;
    }

    public void initialiseUI() {

        // Obtenir les dimensions de l'écran
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        // Calculer les dimensions de la fenêtre avec des valeurs minimales
        double windowWidth = Math.max(MIN_WIDTH, screenWidth * WINDOW_WIDTH_RATIO);
        double windowHeight = Math.max(MIN_HEIGHT, screenHeight * WINDOW_HEIGHT_RATIO);

        //Conteneur principal
        BorderPane root = new BorderPane();
        // Appliquer une image de fond
        File imagefile = new File("src/projet/ressources/participant.jpg");
        Image backgroundImage = new Image(imagefile.toURI().toString());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        root.setBackground(new Background(background));

        GridPane grid = new GridPane(10, 20);
        grid.setMaxWidth(MIN_WIDTH/2);
        grid.setMaxHeight(MIN_HEIGHT-20);
        grid.setStyle("-fx-background-color: hotpink");
        grid.setEffect(new DropShadow(50, Color.MAGENTA));
        grid.setPadding(new Insets(40));

        Label title = new Label();
        title.setTextFill(Color.DARKMAGENTA);
        title.setEffect(new DropShadow(2, Color.DEEPPINK));
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));

        Label nom = new Label("Le nom : ");
        TextField nom_ = new TextField();
        Label email = new Label("L'adresse email");
        TextField email_ = new TextField();

        Button submit = new Button("Valider");
        submit.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        submit.setEffect(new DropShadow(10, Color.DEEPPINK));
        submit.setFont(Font.font("Segeo UI", FontWeight.BOLD, 20));
        submit.disableProperty().bind((nom_.textProperty().isEmpty()).or(email_.textProperty().isEmpty()));

        Label result = new Label();
        result.setTextFill(Color.BLACK);
        result.setFont(Font.font("Segeo UI", FontWeight.LIGHT, 14));

        grid.add(title, 0, 0, 3, 1);
        grid.add(nom, 0, 1);
        grid.add(nom_, 1, 1);
        grid.add(email, 0, 2);
        grid.add(email_, 1, 2);

        Button back = new Button();
        back.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        back.setEffect(new DropShadow(10, Color.DEEPPINK));
        back.setFont(Font.font("Segeo UI", FontWeight.BOLD, 16));

        if (organisateur) {
            title.setText("Inscrivez-vous en tant qu'Organisateur");
            grid.add(submit, 0, 4);
            grid.add(result, 0, 5);
            submit.setOnAction(e -> {
                String id = String.valueOf(mainApplication.getGestionEvenement().getOrganisateurs().size());
                Organisateur organisateur1 = new Organisateur(id, nom_.getText(), email_.getText());
                mainApplication.getGestionEvenement().AjouterOrganisateur(organisateur1);
                result.setText("L'id correspondant à ce nouvel organisateur est " + id);
            });
            back.setText("Retour au Menu");
            back.setOnAction(e -> {
                menuUI.initialiseUI();
            });
            grid.add(back, 0, 6);
        } else {
            if (artiste) {
                title.setText("Ajouter un Artiste à ce Concert");
                Label type = new Label("Le type");
                TextField type_ = new TextField("chanteur, danseur, pianiste, ...");
                grid.add(type, 0, 3);
                grid.add(type_, 1, 3);
                grid.add(submit, 0, 5);
                grid.add(result, 0, 6);
                submit.setOnAction(e -> {
                    int i = mainApplication.getGestionEvenement().getEvenements().indexOf(evenement);
                    int j = evenement.getOrganisateur().getEvenements().indexOf(evenement);
                    String id = String.valueOf(evenement.getArtistes().size());
                    Artiste artiste1 = new Artiste(id, nom_.getText(), email_.getText());
                    artiste1.setType(type_.getText());
                    evenement.ajouterArtiste(artiste1);
                    mainApplication.getGestionEvenement().getEvenements().set(i, evenement);
                    evenement.getOrganisateur().getEvenements().set(j, evenement);
                    result.setText("L'id correspondant à ce nouvel artiste est " + id);
                });
                back.setText("Retour au Formulaire de Modifs");
                back.setOnAction(e -> {
                    modifsUI.initialiseUI();
                });
                grid.add(back, 0, 7);
            } else {
                if (intervenant) {
                    title.setText("Ajouter un Intervenant à cette Conférence");
                    Label domaine = new Label("Le domaine");
                    TextField domaine_ = new TextField("science, philo, politique, ...");
                    grid.add(domaine, 0, 3);
                    grid.add(domaine_, 1, 3);
                    grid.add(submit, 0, 5);
                    grid.add(result, 0, 6);
                    submit.setOnAction(e -> {
                        int i = mainApplication.getGestionEvenement().getEvenements().indexOf(evenement);
                        int j = evenement.getOrganisateur().getEvenements().indexOf(evenement);
                        String id = String.valueOf(evenement.getIntervenants().size());
                        Intervenant intervenant1 = new Intervenant(id, nom_.getText(), email_.getText());
                        intervenant1.setDomaine(domaine_.getText());
                        evenement.ajouterIntervenant(intervenant1);
                        mainApplication.getGestionEvenement().getEvenements().set(i, evenement);
                        evenement.getOrganisateur().getEvenements().set(j, evenement);
                        result.setText("L'id correspondant à ce nouvel intervenant est " + id);
                    });
                    back.setText("Retour au Formulaire de Modifs");
                    back.setOnAction(e -> {
                        modifsUI.initialiseUI();
                    });
                    grid.add(back, 0, 7);
                } else {
                    title.setText("Participez à cet Évènement");
                    grid.add(submit, 0, 4);
                    grid.add(result, 0, 5);
                    submit.setOnAction(e -> {
                        int i = mainApplication.getGestionEvenement().getEvenements().indexOf(evenement);
                        int j = evenement.getOrganisateur().getEvenements().indexOf(evenement);
                        String id = String.valueOf(evenement.getParticipants().size());
                        ParticipantObserver participant = new Participant(id, nom_.getText(), email_.getText());
                        evenement.ajouterParticipant(participant);
                        mainApplication.getGestionEvenement().getEvenements().set(i, evenement);
                        evenement.getOrganisateur().getEvenements().set(j, evenement);
                        result.setText("L'id correspondant à ce nouveau participant est " + id);
                    });
                    back.setText("Retour à l'Affiche");
                    back.setOnAction(e -> {
                        evenementUI.initialiseUI();
                    });
                    grid.add(back, 0, 6);
                }
            }
        }

        root.setCenter(grid);

        // Créer la scène avec une taille adaptative
        Scene scene = new Scene(root, windowWidth, windowHeight);
        primaryStage.setTitle("EVIE - Évènements");
        primaryStage.setScene(scene);

        // Définir les dimensions minimales pour éviter des problèmes de mise en page
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);

        // Ne plus utiliser le mode plein écran
        primaryStage.setMaximized(false);

        // Centrer la fenêtre sur l'écran
        primaryStage.centerOnScreen();

        primaryStage.show();

    }
}
