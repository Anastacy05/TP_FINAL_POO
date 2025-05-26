module TP.Final.POO {

    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    opens projet.composants;
    opens projet.interfaces;
    opens projet.mecanismes;
    opens projet.serialisation;
    opens projet;

}