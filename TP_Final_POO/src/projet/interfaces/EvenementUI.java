package projet.interfaces;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

import java.io.File;
import java.util.Objects;

public class EvenementUI {

    private final Evenement evenement;
    private final Stage primaryStage;
    private final MainApplication mainApplication;
    private final ListeUI listeUI;

    // Définir le ratio de taille de la fenêtre par rapport à l'écran
    private final double WINDOW_WIDTH_RATIO = 0.7; // 70% de la largeur de l'écran
    private final double WINDOW_HEIGHT_RATIO = 0.8; // 80% de la hauteur de l'écran
    private final double MIN_WIDTH = 800;
    private final double MIN_HEIGHT = 600;

    public EvenementUI (Evenement evenement, Stage primaryStage, MainApplication mainApplication, ListeUI listeUI) {
        this.evenement = evenement;
        this.primaryStage = primaryStage;
        this.mainApplication = mainApplication;
        this.listeUI = listeUI;
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
        File imagefile = new File("src/projet/ressources/evenement.jpg");
        Image backgroundImage = new Image(imagefile.toURI().toString());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        root.setBackground(new Background(background));

        GridPane event = new GridPane();
        event.setVgap(10);
        event.setHgap(20);
        event.setMaxHeight(MIN_HEIGHT/2);
        event.setMinWidth(MIN_WIDTH);
        event.setStyle("-fx-background-color: hotpink");
        event.setEffect(new DropShadow(50, Color.MAGENTA));

        String[] details = evenement.afficherDetails().split("//");

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);

        Button attend = new Button("Participer");
        attend.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        attend.setEffect(new DropShadow(10, Color.DEEPPINK));
        attend.setFont(Font.font("Segeo UI", FontWeight.BOLD, 16));
        attend.setOnAction(e -> {
            ParticipantUI participantUI = new ParticipantUI(primaryStage, mainApplication);
            participantUI.setEvenementUI(this);
            participantUI.setEvenement(evenement);
            participantUI.initialiseUI();
        });

        Button deny = new Button ("Se Retirer");
        deny.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        deny.setEffect(new DropShadow(10, Color.DEEPPINK));
        deny.setFont(Font.font("Segeo UI", FontWeight.BOLD, 16));
        deny.setOnAction(e -> {
            LoginUI loginUI = new LoginUI(evenement, primaryStage, this, mainApplication);
            loginUI.initialiseUI();
        });

        buttons.getChildren().addAll(attend, deny);

        root.setCenter(event);
        root.setBottom(buttons);

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
