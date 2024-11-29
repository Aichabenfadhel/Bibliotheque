package com.example.demo1.Services;

import com.example.demo1.Models.Abonnement;
import com.example.demo1.Models.Lecteur;
import com.example.demo1.Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AbonnementService {
    public static void ajouterAbonnement(Abonnement abonnement) {
        String query = "INSERT INTO abonnement ( cin_lecteur ,frais_abonnement, date_creation) VALUES (?, ?, ?)";
        DatabaseConnection conn =new DatabaseConnection();

        try (
              Connection connection =conn.connexionBD();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {


            preparedStatement.setLong(1,abonnement.getCin());
            preparedStatement.setDouble(2, abonnement.getFrais_Abonnement());
            preparedStatement.setDate(3, java.sql.Date.valueOf(abonnement.getDate_creation()));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void supprimerAbonnement(int idAbonnement) {
        String query = "DELETE FROM abonnement WHERE idabonn = ?";
        DatabaseConnection conn =new DatabaseConnection();
        try (Connection connection =conn.connexionBD();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idAbonnement);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Abonnement supprimé avec succès!");
            } else {
                System.out.println("Aucun abonnement trouvé avec le cin spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static List<Abonnement> getAllAbonnements() {
        DatabaseConnection conn = new DatabaseConnection();
        Connection connection = conn.connexionBD();
        List<Abonnement> abonns = new ArrayList<>();
        String query = "SELECT * FROM abonnement";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {


                long cin = resultSet.getLong("CIN_Lecteur");
                int id_Abonn= resultSet.getInt("idAbonn");
                Double fraisAbonn= resultSet.getDouble("frais_Abonnement");
                LocalDate date_creation =resultSet.getDate("date_creation").toLocalDate();

                Abonnement abonn = new Abonnement(id_Abonn,cin,fraisAbonn,date_creation);
                abonns.add(abonn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des abonnements ");
        }

        return abonns;
    }

    public static void main(String[] args) {
        List<Abonnement> abons =new ArrayList<>();
        abons=getAllAbonnements();
        System.out.println(abons);
    }


}
