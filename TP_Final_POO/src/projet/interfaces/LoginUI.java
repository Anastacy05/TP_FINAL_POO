package projet.interfaces;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
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
import projet.mecanismes.ParticipantObserver;

import java.io.File;
import java.util.Objects;

public class LoginUI {

    private Stage primaryStage;
    private MenuUI menuUI;
    private EvenementUI evenementUI;
    private MainApplication mainApplication;
    private Evenement evenement = null;

    private final double WINDOW_WIDTH_RATIO = 0.7; // 70% de la largeur de l'écran
    private final double WINDOW_HEIGHT_RATIO = 0.8; // 80% de la hauteur de l'écran
    private final double MIN_WIDTH = 800;
    private final double MIN_HEIGHT = 600;

    public LoginUI(Stage primaryStage, MenuUI menuUI, MainApplication mainApplication) {
        this.primaryStage = primaryStage;
        this.menuUI = menuUI;
        this.mainApplication = mainApplication;
    }

    public LoginUI(Evenement evenement, Stage primaryStage, EvenementUI evenementUI, MainApplication mainApplication) {
        this.evenement = evenement;
        this.primaryStage = primaryStage;
        this.evenementUI = evenementUI;
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
        HBox root = new HBox();
        // Appliquer une image de fond
        File imagefile = new File("src/projet/ressources/login.jpg");
        Image backgroundImage = new Image(imagefile.toURI().toString());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        root.setBackground(new Background(background));

        VBox form = new VBox(40);
        form.setAlignment(Pos.CENTER);
        form.setMinWidth(MIN_WIDTH/2);
        form.setMinHeight(MIN_HEIGHT);
        form.setStyle("-fx-background-color: hotpink");
        form.setEffect(new DropShadow(50, Color.MAGENTA));
        form.setPadding(new Insets(40));

        Label question = new Label("Votre numéro identifiant");
        question.setTextFill(Color.VIOLET);
        question.setEffect(new DropShadow(20, Color.HOTPINK));
        question.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));

        TextField identifiant = new TextField();
        identifiant.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 20));
        identifiant.setPromptText("Le nombre fourni à votre enregistrement");
        identifiant.setMinWidth(question.getWidth() + 15);

        Label confirm = new Label("Confirmer le numéro");
        confirm.setTextFill(Color.VIOLET);
        confirm.setEffect(new DropShadow(20, Color.HOTPINK));
        confirm.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));

        TextField identifiant1 = new TextField();
        identifiant1.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 20));
        identifiant1.setPromptText("Le nombre fourni à votre enregistrement");
        identifiant1.setMinWidth(question.getWidth() + 15);

        Label erreur = new Label("");
        erreur.setTextFill(Color.BLACK);
        erreur.setEffect(new DropShadow(20, Color.BROWN));
        erreur.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));

        Button submit = new Button("Soumettre");
        submit.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
        submit.setEffect(new DropShadow(10, Color.DEEPPINK));
        submit.setFont(Font.font("Segeo UI", FontWeight.BOLD, 20));
        submit.disableProperty().bind((identifiant.textProperty().isEmpty()).or(identifiant1.textProperty().isEmpty()));
        submit.setOnAction(e -> {
            String id = identifiant.getText();
            if (!id.equals(identifiant1.getText())) {
                erreur.setText("Vous devez entrer le même identifiant deux fois");
            } else {
                if (evenement == null) {
                    Organisateur organisateur = mainApplication.loginOrganisateur(id);
                    if (organisateur != null) {
                        ListeUI listeUI = new ListeUI(primaryStage, menuUI, mainApplication);
                        listeUI.setOrganisateur(organisateur);
                        listeUI.initialiseUI();
                    } else {
                        erreur.setText("La connexion a échoué, vérifiez votre id");
                    }
                } else {
                    ParticipantObserver participant = mainApplication.loginParticipant(id, evenement);
                    if (participant != null) {
                        int i = mainApplication.getGestionEvenement().getEvenements().indexOf(evenement);
                        evenement.supprimerParticipant(participant);
                        mainApplication.getGestionEvenement().getEvenements().set(i, evenement);
                    } else {
                        erreur.setText("Aucun participant n'a cet id, vérifiez");
                    }
                }

            }
        });

        Button back = new Button();
        if (evenementUI == null) {
            back.setText("Retour au Menu");
            back.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
            back.setEffect(new DropShadow(10, Color.DEEPPINK));
            back.setFont(Font.font("Segeo UI", FontWeight.BOLD, 16));

            back.setOnAction(e -> {
                menuUI.initialiseUI();
            });
        } else {
            back.setText("Retour à l'Évènement");
            back.setStyle("-fx-background-color: purple; -fx-background-radius: 20px; -fx-cursor: hand; -fx-text-fill: white;");
            back.setEffect(new DropShadow(10, Color.DEEPPINK));
            back.setFont(Font.font("Segeo UI", FontWeight.BOLD, 16));

            back.setOnAction(e -> {
                evenementUI.initialiseUI();
            });
        }

        form.getChildren().addAll(question, identifiant, confirm, identifiant1, erreur, submit);

        if (evenement == null) {
            HBox register = new HBox(5);

            Label phrase = new Label("Vous n'avez pas encore d'identifiant ? ");
            phrase.setTextFill(Color.DARKMAGENTA);
            phrase.setFont(Font.font("Segeo UI", FontWeight.LIGHT, 12));

            Hyperlink link = new Hyperlink("Inscrivez-vous comme organisateur");
            link.setStyle("-fx-text-fill: blueviolet; -fx-font-size: 12px; -fx-border-color: transparent;");
            link.setOnMouseEntered(e -> link.setStyle("-fx-text-fill: black; -fx-underline: true; -fx-border-color: transparent;"));
            link.setOnMouseExited(e -> link.setStyle("-fx-text-fill: blueviolet; -fx-underline: false; -fx-border-color: transparent;"));
            link.setOnAction(e -> {
                ParticipantUI participantUI = new ParticipantUI(primaryStage, mainApplication);
                participantUI.setOrganisateur(true);
                participantUI.setMenuUI(menuUI);
                participantUI.initialiseUI();
            });

            register.getChildren().addAll(phrase, link);
            form.getChildren().add(register);
        }

        form.getChildren().add(back);
        root.getChildren().add(form);

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
