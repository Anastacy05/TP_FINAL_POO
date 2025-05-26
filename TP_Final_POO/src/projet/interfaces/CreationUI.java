package projet.interfaces;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import projet.MainApplication;
import projet.composants.Concert;
import projet.composants.Conference;
import projet.composants.Organisateur;

import java.io.File;

public class CreationUI {

    private Stage primaryStage;
    private MainApplication mainApplication;
    private Organisateur organisateur;
    private ListeUI listeUI;
    private String type = "Concert";

    private final double WINDOW_WIDTH_RATIO = 0.7; // 70% de la largeur de l'écran
    private final double WINDOW_HEIGHT_RATIO = 0.8; // 80% de la hauteur de l'écran
    private final double MIN_WIDTH = 800;
    private final double MIN_HEIGHT = 600;

    public CreationUI(Stage primaryStage, MainApplication mainApplication, ListeUI listeUI, Organisateur organisateur) {
        this.primaryStage = primaryStage;
        this.mainApplication = mainApplication;
        this.listeUI = listeUI;
        this.organisateur = organisateur;
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
        File imagefile = new File("src/projet/ressources/creation.jpg");
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

        Label title = new Label("NOUVEL ÉVÈNEMENT");
        title.setTextFill(Color.VIOLET);
        title.setEffect(new DropShadow(20, Color.HOTPINK));
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 42));

        Label nom = new Label("Le nom : ");
        Label date = new Label(" La date : ");
        Label lieu = new Label("Le lieu : ");
        Label capaciteMax = new Label("Le nombre max de participants :");

        TextField nom_ = new TextField();
        DatePicker date_ = new DatePicker();
        TextField lieu_ = new TextField();
        TextField capaciteMax_ = new TextField();

        form.add(title, 0, 0, 3, 1);
        form.add(nom, 0, 4);
        form.add(nom_, 1, 4);
        form.add(date, 0, 5);
        form.add(date_, 1, 5);
        form.add(lieu, 0, 6);
        form.add(lieu_, 1, 6);
        form.add(capaciteMax, 0, 7);
        form.add(capaciteMax_, 1, 7);

        Label genre_musical = new Label("Le genre musical : ");
        TextField genre_musical_ = new TextField();

        form.add(genre_musical, 0, 8);
        form.add(genre_musical_, 1, 8);

        Label theme = new Label("Le theme : ");
        TextField theme_ = new TextField();

        ComboBox<String> types = new ComboBox<>();
        types.getItems().addAll("Concert", "Conference");
        types.getSelectionModel().select(0);
        types.setOnAction(e -> {
            if (type.equals("Concert")) {
                form.getChildren().removeAll(genre_musical, genre_musical_);
            } else {
                form.getChildren().removeAll(theme, theme_);
            }
            type = types.getSelectionModel().getSelectedItem();
            if (type.equals("Concert")) {
                form.add(genre_musical, 0, 8);
                form.add(genre_musical_, 1, 8);
            } else {
                form.add(theme, 0, 8);
                form.add(theme_, 1, 8);
            }
        });
        form.add(types, 0, 2);

        Button submit = new Button("Valider");
        submit.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        submit.setEffect(new DropShadow(10, Color.DEEPPINK));
        submit.setFont(Font.font("Segeo UI", FontWeight.BOLD, 16));
        submit.disableProperty().bind(nom_.textProperty().isEmpty());
        submit.setOnAction(e -> {
            int id = mainApplication.getGestionEvenement().getEvenements().size();
            if (type.equals("Concert")) {
                Concert concert = new Concert(String.valueOf(id), nom_.getText(), organisateur, date_.getValue(), lieu_.getText(), Integer.parseInt(capaciteMax_.getText()), genre_musical_.getText());
                organisateur.getEvenements().add(concert);
                mainApplication.getGestionEvenement().getEvenements().add(concert);
            } else {
                Conference conference = new Conference(String.valueOf(id), nom_.getText(), organisateur, date_.getValue(), lieu_.getText(), Integer.parseInt(capaciteMax_.getText()), theme_.getText());
                organisateur.getEvenements().add(conference);
                mainApplication.getGestionEvenement().getEvenements().add(conference);
            }
            listeUI.initialiseUI();
        });
        form.add(submit, 0, 10);

        root.setCenter(form);

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

    public void changement() {}

}
