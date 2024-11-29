package com.example.demo1.Controllers;

import com.example.demo1.Models.Bibliotheque;
import com.example.demo1.Models.DetailEmprunt;
import com.example.demo1.Models.Lecteur;
import com.example.demo1.Models.Livre;
import com.example.demo1.Services.EmprunterService;
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
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class EmprunterController {

    public TextField cinTextField;
    public TextField titreTextField;
    public Button emprunterBTN;
    public Button retourBTN;
    public Button listeLivreBTN;
    public TableView<DetailEmprunt> livreEmpTable;
    public TableColumn <DetailEmprunt,Integer> idLecteurColumn;
    public TableColumn <DetailEmprunt,Integer> idLivreColumn;
    public TableColumn <DetailEmprunt, Date>DateEmpColumn;
    public TableColumn <DetailEmprunt,Date>DateRetourColumn;
    public Button listelivresEmpBTN;
    public TextField cinRetTextField;
    public TextField titreRetTextField;
    public Button retournerBTN;
    public Button listelivEmpBTN;



    @FXML
    void handleEmprunterLivre(ActionEvent event) {
        try {
            long cin = Long.parseLong(cinTextField.getText());
            String titre=titreTextField.getText();

            Lecteur lecteur = LecteurService.getLecteurByCin(cin);
            Livre livre = LivreService.getLivreByTitre(titre);

            if (lecteur != null && livre != null ) {
                if(livre.isDisponibility()) {
                    livre.setDisponibility(false);
                    LivreService.updateDisponibiliteInDatabase(false, livre.getCode());
                    Bibliotheque.emprunter(lecteur.getCin(), livre);
                    EmprunterService.enregistrerEmpruntLivre(lecteur, livre);
                    showAlert("Emprunt enregistré avec succès!");
                    clearInputFields();
                }else{
                    showAlert("Livre Non Disponible!");
                }
            } else {
                showAlert("Lecteur ou livre non trouvé.");
            }
        } catch (NumberFormatException e) {
            showAlert("Veuillez entrer des valeurs valides pour le CIN et le titre du livre.");
        }
    }

    @FXML
    void handleRetournerLivre(ActionEvent event) {
        try {
            long cin = Long.parseLong(cinRetTextField.getText());
            String titre=titreRetTextField.getText();

            Lecteur lecteur = LecteurService.getLecteurByCin(cin);
            Livre livre = LivreService.getLivreByTitre(titre);

            if (lecteur != null && livre != null) {
                livre.setDisponibility(true);
                LivreService.updateDisponibiliteInDatabase(true,livre.getCode());
               Bibliotheque.retourner_livre(lecteur,livre);
               EmprunterService.retournerLivre(lecteur,livre);
                showAlert("Livre retourné avec succès!");
                clearRetFields();
            } else {
                showAlert("Lecteur ou livre non trouvé.");
            }
        } catch (NumberFormatException e) {
            showAlert("Veuillez entrer des valeurs valides pour le CIN et le titre.");
        }
    }


    @FXML
    public void showLivresEmpruntes(ActionEvent event) {
        List<DetailEmprunt> livresList = EmprunterService.getAllLivresEmpruntes();
        ObservableList<DetailEmprunt> livresEmp = FXCollections.observableArrayList(livresList);
        livreEmpTable.setItems(livresEmp);

        idLecteurColumn.setCellValueFactory(new PropertyValueFactory<>("cinLecteur"));
        idLivreColumn.setCellValueFactory(new PropertyValueFactory<>("id_livre"));
        DateEmpColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        DateRetourColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetour"));
    }


    public void AfficherFenetreHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/home.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleAfficherFenetreLivres(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/GestionLivre.fxml")));
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void AfficherFenetreEmprunter(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/EmprunterLivre.fxml")));
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void AfficherFenetreListeLivresEmpruntes(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/livresEmpruntes.fxml")));
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Opération réussie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearInputFields() {
       cinTextField.clear();
       titreTextField.clear();

    }
    private void clearRetFields() {
        cinRetTextField.clear();
        titreRetTextField.clear();

    }
}

