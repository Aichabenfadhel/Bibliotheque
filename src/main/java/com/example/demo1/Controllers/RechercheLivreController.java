package com.example.demo1.Controllers;

import com.example.demo1.Models.Livre;
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

public class RechercheLivreController implements Initializable {
    public TableView<Livre> rechTableView;
    public TableColumn<Livre,Integer> codeRechColumn;
    public TableColumn <Livre,String> titreRechColumn;
    public TableColumn <Livre,String>auteurRechColumn;
    public TableColumn<Livre,Integer> isbnRechColumn;
    public TableColumn <Livre,String> typeRechColumn;
    public TableColumn<Livre,Boolean> dipoRechColumn;
    public Button rechLivreBTN;
    public ChoiceBox<String> rechCritereChoiceBox;
    private final String [] criteres = {"Titre","Auteur","Première lettre du titre"};
    public TextField critereChoisiTextField;
    public Button retourBTN;



    @Override
    public void initialize(URL arg , ResourceBundle resourceBundle){
        rechCritereChoiceBox.getItems().addAll(criteres);
    }

    public void rechercheLivreSelonCritere(ActionEvent event){
        String cRecherche = critereChoisiTextField.getText();
        String critere = rechCritereChoiceBox.getValue();
        List<Livre> livresRecherches;

        if(critere.equals("Titre")){
            livresRecherches= LivreService.rechercherLivresParTitre(cRecherche);
        } else if (critere.equals("Auteur")) {
            livresRecherches =LivreService.rechercherLivresParAuteur(cRecherche);
        }else {
            livresRecherches=  LivreService.rechercherLivresParPremieresLettresTitre(cRecherche);
        }

        iniList(livresRecherches, rechTableView, codeRechColumn, titreRechColumn, auteurRechColumn, isbnRechColumn, typeRechColumn,dipoRechColumn);

    }

    static void iniList(List<Livre> livresRecherches, TableView<Livre> rechTableView, TableColumn<Livre, Integer> codeRechColumn, TableColumn<Livre, String> titreRechColumn, TableColumn<Livre, String> auteurRechColumn, TableColumn<Livre, Integer> isbnRechColumn, TableColumn<Livre, String> typeRechColumn,TableColumn<Livre, Boolean>dispoRechColumn) {
        ObservableList<Livre> livres = FXCollections.observableArrayList(livresRecherches);
        rechTableView.setItems(livres);


        codeRechColumn.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("Code"));
        titreRechColumn.setCellValueFactory(new PropertyValueFactory<Livre, String>("Titre"));
        auteurRechColumn.setCellValueFactory(new PropertyValueFactory<Livre, String>("Auteur"));
        isbnRechColumn.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("ISBN"));
        typeRechColumn.setCellValueFactory(new PropertyValueFactory<Livre, String>("Type"));
        dispoRechColumn.setCellValueFactory(new PropertyValueFactory<Livre, Boolean>("disponibility"));
    }

    public void handleAfficherFenetreLivres(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/GestionLivre.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
