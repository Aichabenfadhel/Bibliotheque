package com.example.demo1.Controllers;

import com.example.demo1.Models.Livre;
import com.example.demo1.Services.LivreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AjouterLivreController implements Initializable {

    public Button ajouterButton;
    @FXML
    private TextField titreTextField;

    @FXML
    private TextField auteurTextField;

    @FXML
    private TextField isbnTextField;


    @FXML
    public ChoiceBox<String> typeChoiceBox;

    private final String [] types = {"Romantique","Science Fiction","Policier"};




    @Override
    public void initialize(URL arg , ResourceBundle resourceBundle){
        typeChoiceBox.getItems().addAll(types);
    }

    @FXML
    public void handleAjouterLivre(ActionEvent event) {
        String titre = titreTextField.getText();
        String auteur = auteurTextField.getText();
        String isbnText = isbnTextField.getText();
        String type = typeChoiceBox.getValue();


        if (titre.isEmpty() || auteur.isEmpty() || isbnText.isEmpty() || type == null) {
            showAlert("Veuillez remplir tous les champs!");
            return;
        }

        try {
            int isbn = Integer.parseInt(isbnText);

            Livre newLivre = new Livre(titre, auteur, isbn, type);
            LivreService.ajouterLivre(newLivre);
            showAlert("Livre ajouté avec succès!");
            clearInputFields();

        } catch (NumberFormatException e) {
            showAlert("ISBN doit être un nombre entier valide!");
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Opération réussie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearInputFields() {
        titreTextField.clear();
        auteurTextField.clear();
        isbnTextField.clear();

    }
    public void handleAfficherFenetreLivres(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/GestionLivre.fxml")));
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
