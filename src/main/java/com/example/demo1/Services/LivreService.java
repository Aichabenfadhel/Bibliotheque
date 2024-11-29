package com.example.demo1.Services;

import com.example.demo1.Models.Livre;
import com.example.demo1.Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LivreService {


    public static void ajouterLivre(Livre livre) {
         DatabaseConnection conn =new DatabaseConnection();
         Connection connection =conn.connexionBD();

        String query = "INSERT INTO livre (titre, auteur, isbn, type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setLong(3, livre.getISBN());
            preparedStatement.setString(4, livre.getType());

            preparedStatement.executeUpdate();
            System.out.println("Livre ajouté avec succès!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout du livre .");
        }
    }

    public static void supprimerLivre(int codeLivre) {
        DatabaseConnection conn =new DatabaseConnection();
        Connection connection =conn.connexionBD();
        String query = "DELETE FROM livre WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, codeLivre);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Livre supprimé avec succès!");
            } else {
                System.out.println("Aucun livre trouvé avec le code spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression du livre .");
        }


    }


    public static List<Livre> getAllLivres() {
        DatabaseConnection conn =new DatabaseConnection();
        Connection connection =conn.connexionBD();
        List<Livre> livres = new ArrayList<>();
        String query = "SELECT * FROM livre";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Livre livre = new Livre();
                livre.setCode(resultSet.getInt("Code"));
                livre.setTitre(resultSet.getString("Titre"));
                livre.setAuteur(resultSet.getString("Auteur"));
                livre.setISBN(resultSet.getInt("ISBN"));
                livre.setType(resultSet.getString("Type"));
                livre.setDisponibility((resultSet.getBoolean("disponibilite")));

                livres.add(livre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des livres depuis la base de données.");
        }
        return livres;
    }

    public static List<Livre> rechercherLivresParTitre(String titreRecherche) {
        return rechercherLivres(livres -> livres.filter(livre -> livre.getTitre().equalsIgnoreCase(titreRecherche)).collect(Collectors.toList()));
    }

    public static List<Livre> rechercherLivresParAuteur(String auteurRecherche) {
        return rechercherLivres(livres -> livres.filter(livre -> livre.getAuteur().equalsIgnoreCase(auteurRecherche)).collect(Collectors.toList()));
    }

    public static List<Livre> rechercherLivresParPremieresLettresTitre(String premieresLettres) {
        return rechercherLivres(livres -> livres.filter(livre -> livre.getTitre().startsWith(premieresLettres)).collect(Collectors.toList()));
    }

    private static List<Livre> rechercherLivres(java.util.function.Function<java.util.stream.Stream<Livre>, List<Livre>> filterFunction) {
        String query = "SELECT * FROM livre";
        DatabaseConnection conn = new DatabaseConnection();

        try (
                Connection connection = conn.connexionBD();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            List<Livre> livres = new ArrayList<>();
            while (resultSet.next()) {
                Livre livre = new Livre();
                livre.setCode(resultSet.getInt("Code"));
                livre.setTitre(resultSet.getString("Titre"));
                livre.setAuteur(resultSet.getString("Auteur"));
                livre.setISBN(resultSet.getInt("ISBN"));
                livre.setType(resultSet.getString("Type"));
                livre.setDisponibility((resultSet.getBoolean("disponibilite")));
                livres.add(livre);
            }


            return filterFunction.apply(livres.stream());

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des livres depuis la base de données.");
            return new ArrayList<>();
        }
    }

    public static Livre getLivreByTitre(String titre) {
        DatabaseConnection conn = new DatabaseConnection();
        try (Connection connection = conn.connexionBD();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM livre WHERE titre = ?")) {
            preparedStatement.setString(1, titre);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                Livre livre = new Livre();
                livre.setCode(resultSet.getInt("Code"));
                livre.setTitre(resultSet.getString("Titre"));
                livre.setAuteur(resultSet.getString("Auteur"));
                livre.setISBN(resultSet.getInt("ISBN"));
                livre.setType(resultSet.getString("Type"));
                livre.setDisponibility((resultSet.getBoolean("disponibilite")));
                System.out.println(livre);
                return livre;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void updateDisponibiliteInDatabase(Boolean disponibilite,int code) {
        DatabaseConnection conn =new DatabaseConnection();

        try (Connection connection =conn.connexionBD()) {
            String updateQuery = "UPDATE livre SET disponibilite = ? WHERE code = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setBoolean(1, disponibilite);
                preparedStatement.setInt(2, code);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour de la disponibilité dans la base de données.");
        }
    }
//    public static void main(String[] args) {
//        LivreService l =new LivreService();
//        Livre liv =new Livre("kiteb 7ayety","aicha",1234,"romantique");
//        ajouterLivre(liv);
//        Livre livmod =new Livre("kiteb 7ayety","aichoucha",12345,"horreur");
//        modifierLivre(livmod);
//        supprimerLivre(5);
//    }
}
