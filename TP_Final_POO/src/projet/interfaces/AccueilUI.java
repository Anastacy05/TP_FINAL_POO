package projet.interfaces;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import projet.MainApplication;

import java.io.File;
import java.util.Objects;

public class AccueilUI {

    private final Stage primaryStage;
    private final MainApplication mainApplication;

    // Définir le ratio de taille de la fenêtre par rapport à l'écran
    private final double WINDOW_WIDTH_RATIO = 0.7; // 70% de la largeur de l'écran
    private final double WINDOW_HEIGHT_RATIO = 0.8; // 80% de la hauteur de l'écran
    private final double MIN_WIDTH = 800;
    private final double MIN_HEIGHT = 600;

    public AccueilUI(Stage primaryStage, MainApplication mainApplication) {
        this.primaryStage = primaryStage;
        this.mainApplication = mainApplication;
        initialiseUI();
    }

    public void initialiseUI() {

        // Obtenir les dimensions de l'écran
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        // Calculer les dimensions de la fenêtre avec des valeurs minimales
        double windowWidth = Math.max(MIN_WIDTH, screenWidth * WINDOW_WIDTH_RATIO);
        double windowHeight = Math.max(MIN_HEIGHT, screenHeight * WINDOW_HEIGHT_RATIO);

        //Conteneur principal
        HBox root = new HBox();
        // Appliquer une image de fond
        File imagefile = new File("src/projet/ressources/accueil.jpg");
        Image backgroundImage = new Image(imagefile.toURI().toString());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        root.setBackground(new Background(background));

        //Sous-conteneur portant les infos utiles
        VBox prospectus = new VBox(90);
        prospectus.setAlignment(Pos.CENTER);
        prospectus.setMinWidth(MIN_WIDTH/2);
        prospectus.setMinHeight(MIN_HEIGHT);
        prospectus.setStyle("-fx-background-color : white;");
        prospectus.setEffect(new Lighting(new Light.Point(10, 20, 300, Color.HOTPINK)));
        prospectus.setPadding(new Insets(40));

        //Titre
        Label title = new Label("EVIE");
        title.setTextFill(Color.DARKMAGENTA);
        title.setEffect(new DropShadow(2, Color.DEEPPINK));
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 42));

        //Sous-titre
        Label subtitle = new Label("Application Évènementielle");
        subtitle.setTextFill(Color.DARKMAGENTA);
        subtitle.setFont(Font.font("Segeo UI", FontPosture.ITALIC, 20));

        //Bouton Start
        Button startbutton = new Button("Commencez l'expérience");
        startbutton.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        startbutton.setEffect(new DropShadow(10, Color.DEEPPINK));
        startbutton.setFont(Font.font("Segeo UI", FontWeight.BOLD, 16));

        //Action du bouton
        startbutton.setOnAction(e -> {
            MenuUI menuUI = new MenuUI(primaryStage, this, mainApplication);
            menuUI.initialiseUI();
        });

        //Survol du bouton
        startbutton.setOnMouseDragEntered(e -> {
            startbutton.setStyle("-fx-background-color: mediumpurple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        });
        startbutton.setOnMouseExited(e ->{
            startbutton.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: pink;");
        });

        //Pied de page
        Label footertitle = new Label("Organisez des Évènements, Participez-y et Informez-vous !");
        footertitle.setTextFill(Color.DARKMAGENTA);
        footertitle.setFont(Font.font("Segeo UI", FontWeight.LIGHT, 12));

        //Imbrication des objets
        prospectus.getChildren().addAll(title, subtitle, startbutton, footertitle);
        root.getChildren().add(prospectus);

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
