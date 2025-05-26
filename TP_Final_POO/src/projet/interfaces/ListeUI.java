package projet.interfaces;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import java.util.List;
import java.util.Objects;

public class ListeUI {

    private Stage primaryStage;
    private MenuUI menuUI;
    private MainApplication mainApplication;
    private Organisateur organisateur = null;

    private final double WINDOW_WIDTH_RATIO = 0.7; // 70% de la largeur de l'écran
    private final double WINDOW_HEIGHT_RATIO = 0.8; // 80% de la hauteur de l'écran
    private final double MIN_WIDTH = 800;
    private final double MIN_HEIGHT = 600;

    public ListeUI(Stage primaryStage, MenuUI menuUI, MainApplication mainApplication) {
        this.primaryStage = primaryStage;
        this.menuUI = menuUI;
        this.mainApplication = mainApplication;
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
        File imagefile = new File("src/projet/ressources/liste.jpg");
        Image backgroundImage = new Image(imagefile.toURI().toString());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        root.setBackground(new Background(background));

        VBox liste = new VBox(20);
        liste.setAlignment(Pos.CENTER);
        liste.setMaxWidth(MIN_WIDTH/2);
        liste.setMinHeight(MIN_HEIGHT);
        liste.setStyle("-fx-background-color: hotpink");
        liste.setEffect(new DropShadow(50, Color.MAGENTA));
        liste.setPadding(new Insets(40));

        List<Evenement> evenements;
        if (organisateur == null) {
            evenements = mainApplication.getGestionEvenement().getEvenements();
        } else {
            evenements = organisateur.getEvenements();
        }

        if (evenements == null) {
            Label sorry = new Label("Aucune évènement programmé pour l'instant");
            sorry.setTextFill(Color.BLACK);
            sorry.setEffect(new DropShadow(20, Color.BROWN));
            sorry.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
            liste.getChildren().add(sorry);
        } else {
            for (Evenement evenement : evenements) {
                Button evbutton = getButton(evenement);
                liste.getChildren().add(evbutton);
            }
            ScrollPane scrollPane = new ScrollPane(liste);
            scrollPane.setFitToWidth(true);
        }

        root.setCenter(liste);

        Button back = new Button("Retour au Menu");
        back.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        back.setEffect(new DropShadow(10, Color.DEEPPINK));
        back.setFont(Font.font("Segeo UI", FontWeight.BOLD, 16));

        back.setOnAction(e -> {
            menuUI.initialiseUI();
        });
        root.setBottom(back);

        if (organisateur != null) {
            Button create = new Button("Nouvel Évènement");
            create.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
            create.setEffect(new DropShadow(10, Color.DEEPPINK));
            create.setFont(Font.font("Segeo UI", FontWeight.BOLD, 16));
            create.setOnAction(e -> {
                CreationUI creationUI = new CreationUI(primaryStage, mainApplication, this, organisateur);
            });
            root.setTop(create);
        }

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

    private Button getButton(Evenement evenement) {
        String evtitle = evenement.getClass().getName() + " " + evenement.getNom() + " organisé par " + evenement.getNomOrganisateur();
        Button evbutton = new Button(evtitle);
        evbutton.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        evbutton.setEffect(new DropShadow(10, Color.DEEPPINK));
        evbutton.setMinWidth(MIN_WIDTH/2 - 20);
        evbutton.setOnAction(e -> {
            if (organisateur == null) {
                EvenementUI evenementUI = new EvenementUI(evenement, primaryStage, mainApplication, this);
                evenementUI.initialiseUI();
            } else {
                ModifsUI modifsUI = new ModifsUI(evenement, primaryStage, mainApplication, this, organisateur);
            }
        });
        return evbutton;
    }

    public void setOrganisateur(Organisateur organisateur) {
        this.organisateur = organisateur;
    }
}
