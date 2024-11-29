package com.example.demo1.Controllers;

import com.example.demo1.Models.Abonnement;
import com.example.demo1.Models.Lecteur;
import com.example.demo1.Models.Livre;
import com.example.demo1.Services.AbonnementService;
import com.example.demo1.Services.LecteurService;
import com.example.demo1.Services.LivreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LecteurController {


    public Button retourBTN;
    public TableColumn <Lecteur,Integer> cinTableColumn;
    public TableColumn<Lecteur,String> nomTableColumn;
    public TableColumn<Lecteur,String> prenomTableColumn;
    public TableColumn idAbonnTableColumn;
    public Button afficherListeLecteursBTN;
    public TextField cinTextField;
    public TextField nomTextField;
    public TextField PrenomTextField;
    public Button ajouterlecteurButton;
    public TableView <Lecteur > lecteurTableView;
    public Button ajoutLectBTN;
    public Button suppLectBTN;
    public TextField suppCodeTextArea;
    public Button SupprimerBTN;
    public Button suppRetourBTN;
    public TextField fraisTextField;
    public TextField suppIdAbonnTextArea;
    public TextField suppCinTextArea;
    public Button listeAbonnBTN;
    public Button retourHomeBTN;
    public Button rechLecteursBTN;

    @FXML
    public void handleAjouterLecteurEtAbonn(ActionEvent event) {
        try {
            long cin = Long.parseLong(cinTextField.getText());
            String nom = nomTextField.getText();
            String prenom = PrenomTextField.getText();
            double frais = Double.parseDouble(fraisTextField.getText());


            if (nom.isEmpty() || prenom.isEmpty()) {
                showAlert("Veuillez remplir tous les champs obligatoires!");
                return;
            }

            Lecteur lecteur = new Lecteur(cin, nom, prenom);
            Abonnement abonnement = new Abonnement(frais, cin);
            LecteurService.ajouterLecteur(lecteur);
            abonnement.setCin(lecteur.getCin());
            AbonnementService.ajouterAbonnement(abonnement);
            showAlert("Lecteur ajouté avec succès!");
            clearFields();

        } catch (NumberFormatException e) {
            showAlert("Veuillez saisir des valeurs valides pour le CIN et les frais!");
        }
    }


    @FXML
    public void handleSupprimerLecteurEtAbonn(ActionEvent event){
        int idAbn = Integer.parseInt((suppIdAbonnTextArea.getText()));
        long cin = Long.parseLong(suppCinTextArea.getText());

        AbonnementService.supprimerAbonnement(idAbn);

        LecteurService.supprimerLecteur(cin);
        showAlert("Lecteur supprimé avec succès!");
        clearSuppFields();
    }


    @FXML
    public void showLecteurs() {
        List<Lecteur> lecteursList = LecteurService.getAllLecteurs();
        RechercheLecteurController.showTableLecteurContent(lecteursList, lecteurTableView, cinTableColumn, nomTableColumn, prenomTableColumn);

        lecteurTableView.refresh();
    }


    @FXML
    public void handleAfficherListeLecteurs(ActionEvent event) {
        showLecteurs();
    }

    public void handleAfficherFenetreLecteurs(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/GestionLecteurs.fxml")));
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void AfficherFenetreAjouterLecteur(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/ajoutLecteur.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void AfficherFenetreSupprimerLecteur(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/supprimerLecteur.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void AfficherFenetreAfficherAbonns(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/GestionAbonns.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void AfficherFenetreRechercheLecteur(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/rechercheLecteurs.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void AfficherFenetreHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/home.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void clearFields(){
        cinTextField.clear();
        fraisTextField.clear();
        nomTextField.clear();
        PrenomTextField.clear();
    }

    private void clearSuppFields(){
        suppCinTextArea.clear();
        suppIdAbonnTextArea.clear();

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Opération réussie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
