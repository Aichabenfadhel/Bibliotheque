package com.example.demo1.Models;

import java.time.LocalDate;

public class DetailEmprunt {

        private Livre livre;
        private LocalDate dateEmprunt ;
        private LocalDate dateRetour;
        private int cinLecteur;
        private int id_livre;

        public DetailEmprunt(Livre livre) {
            super();
            this.livre = livre;
            dateEmprunt = LocalDate.now();
            dateRetour = dateEmprunt.plusDays(7);
        }

    public DetailEmprunt( int cinLecteur, int id_livre) {
        this.cinLecteur = cinLecteur;
        this.id_livre = id_livre;
    }

    public DetailEmprunt(Livre livre, LocalDate d) {
            super();
            this.livre = livre;
            dateEmprunt = d;
            dateRetour = dateEmprunt.plusDays(7);
        }

    public DetailEmprunt() {

    }

    public DetailEmprunt(int idLecteur, int idLivre, LocalDate dateEmprunt) {
            this.id_livre=idLivre;
            this.cinLecteur=idLecteur;
            this.dateEmprunt=dateEmprunt;
            this.dateRetour=dateEmprunt.plusDays(15);
    }

    public LocalDate getDateRetour() {
            return dateRetour;
        }

        public void setDateRetour(LocalDate dateRetour) {
            this.dateRetour = dateRetour;
        }

        public LocalDate getDateEmprunt() {
            return dateEmprunt;
        }

        public void setDateEmprunt(LocalDate dateEmprunt) {
            this.dateEmprunt = dateEmprunt;
        }

        public Livre getLivre() {
            return livre;
        }

        public void setLivre(Livre livre) {
            this.livre = livre;
        }

    public int getCinLecteur() {
        return cinLecteur;
    }

    public void setCinLecteur(int cinLecteur) {
        this.cinLecteur = cinLecteur;
    }

    public int getId_livre() {
        return id_livre;
    }

    public void setId_livre(int id_livre) {
        this.id_livre = id_livre;
    }
}




