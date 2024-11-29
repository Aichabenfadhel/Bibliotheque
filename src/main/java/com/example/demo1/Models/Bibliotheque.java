package com.example.demo1.Models;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bibliotheque {

    ArrayList<Livre> liste_livres ;
    ArrayList <Lecteur> lecteurs ;
    ArrayList <Lecteur> lecteursAbonnementEpuisé=new ArrayList<>() ;
    ArrayList<Lecteur> LecteursFideles = new ArrayList<>() ;
    static Map<Long, DetailEmprunt> mapEmprunts=new HashMap<Long,DetailEmprunt>();
    static Map <Long,Integer> mapLivres = new HashMap <Long,Integer>();



    public Bibliotheque() {
        super();
        liste_livres = new ArrayList<>();
        lecteurs = new ArrayList<>();
    }




    void ajouter_livre ( Livre l) {
        if(mapLivres.containsKey(l.getISBN())){
            int nbreExemplaires = mapLivres.get(l.getISBN());
            mapLivres.put(l.getISBN(), nbreExemplaires+1);
        }else {
            mapLivres.put(l.getISBN(),1);
        }
    }

    public static void emprunter(long cin, Livre l) {
        if (mapEmprunts.containsKey(cin)) {
            System.out.println("Le lecteur de cin "+ cin+" a deja emprunté un livre :/");
        }else {
            if(mapLivres.containsKey(l.getISBN()) && mapLivres.get(l.getISBN())>0) {
                int nbExemplairesDispo = mapLivres.get(l.getISBN()) -1 ;
                mapLivres.replace(l.getISBN(),nbExemplairesDispo );
                DetailEmprunt details = new DetailEmprunt(l);
                mapEmprunts.put(cin, details );
                System.out.println("Le lecteur de cin "+ cin+" a emprunté le livre " + l.getTitre());
            } else {
                System.out.println("Le livre "+ l.getTitre() + " n'est pas disponible :(");
            }
        }
    }

    public static void  retourner_livre( Lecteur lect, Livre l) {
        if(mapEmprunts.containsKey(lect.getCin())) {
            mapEmprunts.remove(lect.getCin());
            mapLivres.put(l.getISBN(), mapLivres.get(l.getISBN())+1);
        }
    }

    public int nombre_livres_empruntés() {
        return mapEmprunts.size();
    }

    public int nombre_livre_retours() {
        int nbreLivre = 0;
        LocalDate auj = LocalDate.now();

        for (DetailEmprunt detail : mapEmprunts.values()) {
            if (detail.getDateRetour().isBefore(auj.plusDays(7))) {
                nbreLivre++;
            }
        }
        return nbreLivre;
    }

    public int nombre_livre_retours2() {
        int nbreLivre = 0;
        LocalDate auj = LocalDate.now();

        for (DetailEmprunt detail : mapEmprunts.values()) {
            if (detail.getDateRetour().isBefore(auj.plusDays(8))) {
                nbreLivre++;
            }
        }
        return nbreLivre;
    }

    public ArrayList<Lecteur> lecteurs_fideles() {
        LocalDate now = LocalDate.now();

        for (Lecteur lecteur : lecteurs) {
            int nbreLivresEmpruntés = 0;

            for (DetailEmprunt emprunt :lecteur.getAbonn().listeEmprunts) {
                if (emprunt != null) {
                    nbreLivresEmpruntés=nbreLivresEmpruntés+1;
                }
            }

            LocalDate date_creation=lecteur.getAbonn().getDate_creation();

            int moisInscription = Period.between(date_creation, now).getMonths() + 1;
            double moyenneLivresParMois = (double) nbreLivresEmpruntés / moisInscription;
            if (moyenneLivresParMois >= 2.0) {
                LecteursFideles.add(lecteur);
            }
        }

        return LecteursFideles;
    }


    public void categories_livres() {
        int nbreLivrePolicier=0;
        int nbreLivreRomantique=0;
        int nbreLivreScFiction=0;

        for (Livre livre : liste_livres) {
            switch(livre.getClass().getSimpleName()) {
                case "Romantique": nbreLivreRomantique=nbreLivreRomantique+1; break;
                case "Policiere": nbreLivrePolicier= nbreLivrePolicier+1;break;
                case "SciencesFiction" : nbreLivreScFiction=nbreLivreScFiction+1;break;
            }
        }
        System.out.println("Nombre de livres Romantiques : "+ nbreLivreRomantique);
        System.out.println("Nombre de livres Policiers : "+ nbreLivrePolicier);
        System.out.println("Nombre de livres de Sciences Fiction : "+ nbreLivreScFiction);
    }

    public ArrayList<Lecteur> abonnements_epuises() {
        LocalDate now = LocalDate.now();
        for (Lecteur lecteur : lecteurs) {
            LocalDate date_creation=lecteur.getAbonn().getDate_creation();
            int periodeAbonnement = Period.between(date_creation, now).getYears();

            if (periodeAbonnement >=1) {
                lecteursAbonnementEpuisé.add(lecteur);
            }
        }
        return lecteursAbonnementEpuisé;
    }

    public ArrayList <Lecteur> chercherLecteur(String l){
        ArrayList <Lecteur> listecherchee =new ArrayList<>();
        for(Lecteur lecteur : lecteurs) {

            if(lecteur.getNom().compareToIgnoreCase(l)==0) {
                listecherchee.add(lecteur);
            }
        }

        return listecherchee;
    }

}
