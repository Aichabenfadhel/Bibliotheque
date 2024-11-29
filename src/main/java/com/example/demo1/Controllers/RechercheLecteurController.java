package com.example.demo1.Controllers;

import com.example.demo1.Models.Lecteur;
import com.example.demo1.Models.Livre;
import com.example.demo1.Services.LecteurService;
import com.example.demo1.Services.LivreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class RechercheLecteurController implements Initializable {
    public TableView <Lecteur> rechTableView;
    public TableColumn<Lecteur,Integer> cinRechColumn;
    public TableColumn <Lecteur,String> nomRechColumn;
    public TableColumn <Lecteur,String> prenomRechColumn;
    public Button rechLecteurBTN;
    public ChoiceBox <String> rechCritereChoiceBox;
    public TextField critereChoisiTextField;
    public Button retourBTN;

    private final String [] criteres = {"CIN","Nom","Prénom"};

    @Override
    public void initialize(URL arg , ResourceBundle resourceBundle){
        rechCritereChoiceBox.getItems().addAll(criteres);
    }

    public void rechercheLecteurSelonCritere(ActionEvent event) {
        try {
            String cRecherche = critereChoisiTextField.getText();
            String critere = rechCritereChoiceBox.getValue();
            List<Lecteur> lecteursList = null;

            if(Objects.equals(critere, "Nom")){
                lecteursList= LecteurService.rechercherLecteursParNom(cRecherche);
            } else if (Objects.equals(critere, "Prénom")) {
                lecteursList =LecteurService.rechercherLecteursParPrenom(cRecherche);
            } else {
                lecteursList= LecteurService.rechercherLecteursParCin(cRecherche);
            }
                showTableLecteurContent(lecteursList, rechTableView, cinRechColumn, nomRechColumn, prenomRechColumn);
            
        } catch (NumberFormatException e) {

            System.out.println("Veuillez entrer une valeur de recherche valide.");
        }
    }

    static void showTableLecteurContent(List<Lecteur> lecteursList, TableView<Lecteur> rechTableView, TableColumn<Lecteur, Integer> cinRechColumn, TableColumn<Lecteur, String> nomRechColumn, TableColumn<Lecteur, String> prenomRechColumn) {
        ObservableList<Lecteur> lecteurs = FXCollections.observableArrayList(lecteursList);
        rechTableView.setItems(lecteurs);


        rechTableView.setItems(lecteurs);


        cinRechColumn.setCellValueFactory(new PropertyValueFactory<>("cin"));
        nomRechColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomRechColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
    }

    public void AfficherFenetreHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/home.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
