package com.example.demo1.Controllers;

import com.example.demo1.Models.Abonnement;
import com.example.demo1.Models.Lecteur;
import com.example.demo1.Services.AbonnementService;
import com.example.demo1.Services.LecteurService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AbonnementController {

    public TableView abonnTableView;
    public TableColumn idAbonnTableColumn;
    public TableColumn cinTableColumn;
    public TableColumn fraisTableColumn;
    public TableColumn dateCTableColumn;
    public Button afficherListeAbonnBTN;

    public Button retourAbnBTN;

    @FXML
    public void showAbonnements() {
        List<Abonnement> abonnementsList = AbonnementService.getAllAbonnements();
        ObservableList<Abonnement> abonns = FXCollections.observableArrayList(abonnementsList);
        abonnTableView.setItems(abonns);


        abonnTableView.setItems(abonns);

        idAbonnTableColumn.setCellValueFactory((new PropertyValueFactory<>("idAbonn")));
        cinTableColumn.setCellValueFactory(new PropertyValueFactory<>("cin"));
        fraisTableColumn.setCellValueFactory(new PropertyValueFactory<>("frais_Abonnement"));
        dateCTableColumn.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

        abonnTableView.refresh();
    }


    @FXML
    public void handleAfficherListeAbonnements(ActionEvent event) {
        showAbonnements();
    }

    public void handleAfficherFenetreLecteurs(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/GestionLecteurs.fxml")));
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
