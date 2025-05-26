package projet.interfaces;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import projet.MainApplication;

import java.io.File;

public class MenuUI {

    private Stage primaryStage;
    private AccueilUI accueilUI;
    private MainApplication mainApplication;

    // Définir le ratio de taille de la fenêtre par rapport à l'écran
    private final double WINDOW_WIDTH_RATIO = 0.7; // 70% de la largeur de l'écran
    private final double WINDOW_HEIGHT_RATIO = 0.8; // 80% de la hauteur de l'écran
    private final double MIN_WIDTH = 800;
    private final double MIN_HEIGHT = 600;

    public MenuUI(Stage primaryStage, AccueilUI accueilUI, MainApplication mainApplication) {
        this.primaryStage = primaryStage;
        this.accueilUI = accueilUI;
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
        File imagefile = new File("src/projet/ressources/menu.jpg");
        Image backgroundImage = new Image(imagefile.toURI().toString());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        root.setBackground(new Background(background));

        //Menu des options
        VBox menu = new VBox(40);
        menu.setAlignment(Pos.CENTER);
        menu.setMaxWidth(MIN_WIDTH/2);
        menu.setMinHeight(MIN_HEIGHT);
        menu.setStyle("-fx-background-color: hotpink");
        menu.setEffect(new DropShadow(50, Color.MAGENTA));
        menu.setPadding(new Insets(40));

        Label title = new Label("Que souhaitez-vous faire ?");
        title.setWrapText(true);
        title.setTextFill(Color.VIOLET);
        title.setEffect(new DropShadow(20, Color.HOTPINK));
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 42));

        Button events = new Button("Consulter les Évènements");
        events.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        events.setEffect(new DropShadow(10, Color.DEEPPINK));
        events.setFont(Font.font("Segeo UI", FontWeight.BOLD, 16));

        events.setOnAction(e -> {
            ListeUI listeUI = new ListeUI(primaryStage, this, mainApplication);
            listeUI.initialiseUI();
        });

        Button new_event = new Button("Organisez un Évènement");
        new_event.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        new_event.setEffect(new DropShadow(10, Color.DEEPPINK));
        new_event.setFont(Font.font("Segeo UI", FontWeight.BOLD, 16));

        new_event.setOnAction(e -> {
            LoginUI loginUI = new LoginUI(primaryStage, this, mainApplication);
            loginUI.initialiseUI();
        });

        Button back = new Button("Retour à l'Accueil");
        back.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        back.setEffect(new DropShadow(10, Color.DEEPPINK));
        back.setFont(Font.font("Segeo UI", FontWeight.BOLD, 16));

        back.setOnAction(e -> {
            accueilUI.initialiseUI();
        });

        //Ajout des éléments du menu
        menu.getChildren().addAll(title, events, new_event, back);

        //Pied de page
        Label footertitle = new Label("Venez profitez de cette vue évènementielle");
        footertitle.setTextFill(Color.VIOLET);
        footertitle.setFont(Font.font("Segeo Ui", FontWeight.LIGHT, 12));

        //Ajout des imbrications
        root.setCenter(menu);
        root.setBottom(footertitle);

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
