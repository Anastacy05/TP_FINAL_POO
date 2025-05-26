package projet.interfaces;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
import projet.composants.Evenement;
import projet.composants.Organisateur;

import java.io.File;
import java.time.LocalDate;
import java.util.Objects;

public class ModifsUI {

    private Evenement evenement;
    private Stage primaryStage;
    private MainApplication mainApplication;
    private ListeUI listeUI;
    private Organisateur organisateur;

    private final double WINDOW_WIDTH_RATIO = 0.7; // 70% de la largeur de l'écran
    private final double WINDOW_HEIGHT_RATIO = 0.8; // 80% de la hauteur de l'écran
    private final double MIN_WIDTH = 800;
    private final double MIN_HEIGHT = 600;

    public ModifsUI(Evenement evenement, Stage primaryStage, MainApplication mainApplication, ListeUI listeUI, Organisateur organisateur) {
        this.evenement = evenement;
        this.primaryStage = primaryStage;
        this.mainApplication = mainApplication;
        this.listeUI = listeUI;
        this.organisateur = organisateur;

    }

    public void initialiseUI() {

        String[] infos = evenement.afficherInfos().split("//");
        int i = mainApplication.getGestionEvenement().getEvenements().indexOf(evenement);
        int j = organisateur.getEvenements().indexOf(evenement);

        // Obtenir les dimensions de l'écran
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        // Calculer les dimensions de la fenêtre avec des valeurs minimales
        double windowWidth = Math.max(MIN_WIDTH, screenWidth * WINDOW_WIDTH_RATIO);
        double windowHeight = Math.max(MIN_HEIGHT, screenHeight * WINDOW_HEIGHT_RATIO);

        //Conteneur principal
        BorderPane root = new BorderPane();
        // Appliquer une image de fond
        File imagefile = new File("src/projet/ressources/modifs.jpg");
        Image backgroundImage = new Image(imagefile.toURI().toString());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        root.setBackground(new Background(background));

        GridPane form = new GridPane();
        form.setMaxHeight(MIN_HEIGHT/2);
        form.setMinWidth(MIN_WIDTH);
        form.setStyle("-fx-background-color: hotpink");
        form.setEffect(new DropShadow(50, Color.MAGENTA));

        Label title = new Label("CHANGEZ CET ÉVÈNEMENT");
        title.setTextFill(Color.VIOLET);
        title.setEffect(new DropShadow(20, Color.HOTPINK));
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 42));

        Label nom = new Label("Le nom : ");
        Label date = new Label(" La date : ");
        Label lieu = new Label("Le lieu : ");
        Label capaciteMax = new Label("Le nombre max de participants :");

        TextField nom_ = new TextField(infos[0]);
        DatePicker date_ = new DatePicker(LocalDate.parse(infos[1]));
        TextField lieu_ = new TextField(infos[2]);
        TextField capaciteMax_ = new TextField(infos[3]);

        Label genre_musical = new Label("Le genre musical : ");
        TextField genre_musical_ = new TextField(infos[4]);

        Label theme = new Label("Le theme : ");
        TextField theme_ = new TextField(infos[4]);

        form.add(title, 0, 0, 3, 1);
        form.add(nom, 0, 1);
        form.add(nom_, 1, 1);
        form.add(date, 0, 2);
        form.add(date_, 1, 2);
        form.add(lieu, 0, 3);
        form.add(lieu_, 1, 3);
        form.add(capaciteMax, 0, 4);
        form.add(capaciteMax_, 1, 4);

        Button ajouter = new Button();
        ajouter.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        ajouter.setEffect(new DropShadow(10, Color.DEEPPINK));
        ajouter.setFont(Font.font("Segeo UI", FontWeight.BOLD, 16));

        Button cancel = new Button("Annuler");
        cancel.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        cancel.setEffect(new DropShadow(10, Color.DEEPPINK));
        cancel.setFont(Font.font("Segeo UI", FontWeight.BOLD, 16));
        cancel.setOnAction(e -> {
            evenement.annuler();
            mainApplication.getGestionEvenement().getEvenements().set(i, evenement);
            organisateur.getEvenements().set(j, evenement);
        });

        Button submit = new Button("Valider");
        submit.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        submit.setEffect(new DropShadow(10, Color.DEEPPINK));
        submit.setFont(Font.font("Segeo UI", FontWeight.BOLD, 16));
        submit.disableProperty().bind(nom_.textProperty().isEmpty());
        submit.setOnAction(e -> {
            String news = nom_.getText() + "//" + date_.getValue() + "//" + lieu_.getText() + "//" + capaciteMax_.getText();
            if (evenement.getClass().getSimpleName().equals("Concert")) {
                news += "//" + genre_musical_.getText();
            } else {
                news += "//" + theme_.getText();
            }
            evenement.Modifier(news);
            mainApplication.getGestionEvenement().getEvenements().set(i, evenement);
            organisateur.getEvenements().set(j, evenement);
            listeUI.initialiseUI();
        });

        if (evenement.getClass().getSimpleName().equals("Concert")) {
            ajouter.setText("Ajouter un Artiste");
            ajouter.setOnAction(e -> {
                ParticipantUI participantUI = new ParticipantUI(primaryStage, mainApplication);
                participantUI.setArtiste(true);
                participantUI.setModifsUI(this);
                participantUI.setEvenement(evenement);
                participantUI.initialiseUI();
            });

            form.add(genre_musical, 0, 5);
            form.add(genre_musical_, 1, 5);
        } else {
            ajouter.setText("Ajouter un Intervenant");
            ajouter.setOnAction(e -> {
                ParticipantUI participantUI = new ParticipantUI(primaryStage, mainApplication);
                participantUI.setIntervenant(true);
                participantUI.setModifsUI(this);
                participantUI.setEvenement(evenement);
                participantUI.initialiseUI();
            });
            form.add(theme, 0, 5);
            form.add(theme_, 1, 5);
        }

        form.add(ajouter, 0, 7);
        form.add(cancel, 0, 8);
        form.add(submit, 0, 9);

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
