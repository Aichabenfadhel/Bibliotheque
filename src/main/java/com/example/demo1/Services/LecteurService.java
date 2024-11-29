package com.example.demo1.Services;

import com.example.demo1.Models.Abonnement;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LecteurService {

    public static void ajouterLecteur(Lecteur lecteur) {
        DatabaseConnection conn = new DatabaseConnection();
        Connection connection = conn.connexionBD();

        String queryLecteur = "INSERT INTO lecteur (cin, nom, prenom) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatementLecteur = connection.prepareStatement(queryLecteur)) {
            preparedStatementLecteur.setLong(1, lecteur.getCin());
            preparedStatementLecteur.setString(2, lecteur.getNom());
            preparedStatementLecteur.setString(3, lecteur.getPrenom());
            preparedStatementLecteur.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout du lecteur ");
        }


    }

    public static void supprimerLecteur(long cin) {
        String query = "DELETE FROM lecteur WHERE cin = ?";
        DatabaseConnection conn = new DatabaseConnection();

        try (
                Connection connection = conn.connexionBD();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, cin);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Lecteur supprimé avec succès!");
            } else {
                System.out.println("Aucun lecteur trouvé avec le cin spécifié.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression du lecteur .");
        }
    }



    public static List<Lecteur> getAllLecteurs() {
        DatabaseConnection conn = new DatabaseConnection();
        Connection connection = conn.connexionBD();
        List<Lecteur> lecteurs = new ArrayList<>();
        String query = "SELECT * FROM lecteur";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                long cin = resultSet.getLong("cin");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");


                Lecteur lecteur = new Lecteur(cin, nom, prenom);
                lecteurs.add(lecteur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des lecteurs depuis la base de données.");
        }

        return lecteurs;
    }

    private static Abonnement getAbonnementForLecteur(int cin) {
        DatabaseConnection conn = new DatabaseConnection();
        Connection connection = conn.connexionBD();
        String query = "SELECT * FROM abonnement WHERE cin_lecteur = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, cin);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    LocalDate dateCreation = resultSet.getDate("date_creation").toLocalDate();
                    double fraisAbonnement = resultSet.getDouble("frais_abonnement");


                    ArrayList<DetailEmprunt> listeEmprunts = getDetailEmpruntsForAbonnement(cin);

                    return new Abonnement(dateCreation, fraisAbonnement, listeEmprunts);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération de l'abonnement depuis la base de données.");
        }

        return null;
    }


    private static ArrayList<DetailEmprunt> getDetailEmpruntsForAbonnement(int cin) {
        DatabaseConnection conn = new DatabaseConnection();
        Connection connection = conn.connexionBD();
        String query = "SELECT * FROM emprunt WHERE id_abonnement = ?";

        ArrayList<DetailEmprunt> detailEmprunts = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, cin);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    int id_Livre = resultSet.getInt("id_livre");
                    int cinLecteur = resultSet.getInt("CIN_Lecteur");

                    DetailEmprunt detailEmprunt = new DetailEmprunt(id_Livre,cinLecteur);
                    detailEmprunts.add(detailEmprunt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des détails d'emprunt depuis la base de données.");
        }

        return detailEmprunts;
    }

    public static List<Lecteur> rechercherLecteursParCin(String cin) {
        Long cinValue = Long.parseLong(cin);
        return rechercherLecteurs(lecteurs -> lecteurs.filter(lecteur -> lecteur.getCin()==cinValue).collect(Collectors.toList()));
    }
    public static List<Lecteur> rechercherLecteursParPrenom(String prenom) {
        return rechercherLecteurs(lecteurs -> lecteurs.filter(lecteur -> lecteur.getPrenom().equalsIgnoreCase(prenom)).collect(Collectors.toList()));
    }

    public static List<Lecteur> rechercherLecteursParNom(String nom) {
        return rechercherLecteurs(lecteurs -> lecteurs.filter(lecteur -> lecteur.getNom().equalsIgnoreCase(nom)).collect(Collectors.toList()));
    }

    private static List<Lecteur> rechercherLecteurs(java.util.function.Function<java.util.stream.Stream<Lecteur>, List<Lecteur>> filterFunction) {
        String query = "SELECT * FROM lecteur";
        DatabaseConnection conn = new DatabaseConnection();

        try (
                Connection connection = conn.connexionBD();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            List<Lecteur> lecteurs = new ArrayList<>();
            while (resultSet.next()) {
                Lecteur lecteur =new Lecteur();
                lecteur.setCin(resultSet.getLong("Cin"));
                lecteur.setNom(resultSet.getString("Nom"));
                lecteur.setPrenom(resultSet.getString("Prenom"));
                lecteurs.add(lecteur);
            }


            return filterFunction.apply(lecteurs.stream());

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des livres depuis la base de données.");
            return new ArrayList<>();
        }
    }

    public static Lecteur getLecteurByCin(long cin) {
        DatabaseConnection conn = new DatabaseConnection();

        try (Connection connection = conn.connexionBD();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM lecteur WHERE cin = ?")) {
            preparedStatement.setLong(1, cin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return new Lecteur(resultSet.getLong("cin"), resultSet.getString("nom"), resultSet.getString("prenom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



//    public static void main(String[] args) {
//        List<Lecteur> lects= new ArrayList<>();
//        lects=getAllLecteurs();
//        System.out.println(lects);
//    }


}
