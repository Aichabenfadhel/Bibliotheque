package com.example.demo1.Services;

import com.example.demo1.Models.DetailEmprunt;
import com.example.demo1.Models.Lecteur;
import com.example.demo1.Models.Livre;
import com.example.demo1.Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprunterService {

    public static void enregistrerEmpruntLivre(Lecteur lecteur, Livre livre) {
        DatabaseConnection conn = new DatabaseConnection();

        try (Connection connection = conn.connexionBD()) {
            String query = "INSERT INTO emprunt (idLecteur, idLivre, date_emprunt, date_retour) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, lecteur.getCin());
                preparedStatement.setInt(2, livre.getCode());
                preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                preparedStatement.setDate(4, java.sql.Date.valueOf(LocalDate.now().plusDays(15)));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'enregistrement de l'emprunt dans la base de données.");
        }
    }

    public static void retournerLivre(Lecteur lecteur, Livre livre) {
        DatabaseConnection conn = new DatabaseConnection();

        try (Connection connection = conn.connexionBD()) {
            String query = "DELETE FROM emprunt WHERE idLecteur = ? AND idLivre = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, lecteur.getCin());
                preparedStatement.setInt(2, livre.getCode());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de l'emprunt dans la base de données.");
        }
    }


    public static List<DetailEmprunt> getAllLivresEmpruntes() {
        DatabaseConnection conn =new DatabaseConnection();
        Connection connection =conn.connexionBD();
        List<DetailEmprunt> emprunts = new ArrayList<>();
        String query = "SELECT * FROM emprunt";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {

                int idLecteur = resultSet.getInt("idlecteur");
                int idLivre = resultSet.getInt("idlivre");
                LocalDate dateEmprunt = resultSet.getDate("date_emprunt").toLocalDate();
                LocalDate dateRetour = resultSet.getDate("date_retour").toLocalDate();


                DetailEmprunt emprunt = new DetailEmprunt(idLecteur, idLivre,dateEmprunt);

                emprunts.add(emprunt);


            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des livres depuis la base de données.");
        }

        return emprunts;

    }


//    public static void main(String[] args) {
//        Lecteur lect= LecteurService.getLecteurByCin(12345678);
//        Livre liv = LivreService.getLivreByTitre("Qui après nous vivrez");
//        EmprunterService.enregistrerEmpruntLivre(lect,liv);
//    }
}
