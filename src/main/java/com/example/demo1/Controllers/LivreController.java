package com.example.demo1.Controllers;
import com.example.demo1.HelloApplication;
import com.example.demo1.Models.Livre;
import com.example.demo1.Services.LivreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LivreController  {

    public Button afficheLivreBTN;
    public Button ajoutBTN;
    public Button retourBTN;
    public TextField suppCodeBTN;
    public Button SupprimerBTN;
    public Button suppRetourBTN;
    public TextField suppCodeTextArea;
    public Button retourHomeBTN;
    public SplitMenuButton rechMenuBar;
    public Button rechercheBTN;
    public Button supprimerBTN;



    @FXML
    private TextField codeTextField;

    @FXML
    private TextField titreTextField;

    @FXML
    private TextField auteurTextField;

    @FXML
    private TextField isbnTextField;


    @FXML
    private Button ajouterButton;

    @FXML
    private Button supprimerButton;

    @FXML
    private Button modifierButton;

    @FXML
    private TableView<Livre> livreTable;

    @FXML
    private TableColumn<Livre, Integer> codeColumn;

    @FXML
    private TableColumn<Livre, String> titreColumn;

    @FXML
    private TableColumn<Livre, String> auteurColumn;

    @FXML
    private TableColumn<Livre, Integer> isbnColumn;

    @FXML
    private TableColumn<Livre, String> typeColumn;
    @FXML
    private TableColumn <Livre, Boolean> dipoColumn;


    public void handleAfficherListeLivre(ActionEvent event){
        showLivres();
    }

    public void handleAfficherFenetreLivres(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/GestionLivre.fxml")));
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void AfficherFenetreAjouterLivre(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/ajoutLivre.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void AfficherFenetreSupprimerLivre(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/supprimerLivre.fxml")));
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

    public void handleAfficherFenetreRechercheLivres(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/rechercheLivre.fxml")));
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void handleSupprimerLivre(ActionEvent event) {


        try {
            int selectedLivre = Integer.parseInt(suppCodeTextArea.getText());


            LivreService.supprimerLivre(selectedLivre);
            showAlert("Livre supprimé avec succès!");
            clearCodeField();



        } catch (NumberFormatException e) {

            System.out.println("Veuillez entrer un code de livre valide (nombre entier).");
        }

    }



    @FXML
    public void showLivres(){

            List<Livre> livresList = LivreService.getAllLivres();
        RechercheLivreController.iniList(livresList, livreTable, codeColumn, titreColumn, auteurColumn, isbnColumn, typeColumn,dipoColumn);

    }

    private void clearInputFields() {
        titreTextField.clear();
        auteurTextField.clear();
        isbnTextField.clear();

    }

    private void clearCodeField(){
        suppCodeTextArea.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Opération réussie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshTable() {
        livreTable.getItems().clear();
        List<Livre> livres = LivreService.getAllLivres();
        livreTable.getItems().addAll(livres);
    }




}
