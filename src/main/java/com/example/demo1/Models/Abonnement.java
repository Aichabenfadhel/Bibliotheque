package com.example.demo1.Models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Abonnement {

        private LocalDate date_creation ;
        private double frais_Abonnement;
    private long cin ;

        private int idAbonn ;

        ArrayList<DetailEmprunt> listeEmprunts = new ArrayList<>();

        public Abonnement(LocalDate date_creation, double frais_Abonnement, ArrayList<DetailEmprunt> listeEmprunts) {
            this.date_creation = date_creation;
            this.frais_Abonnement = frais_Abonnement;
            this.listeEmprunts = listeEmprunts;
        }
        public Abonnement(ArrayList<DetailEmprunt> listeEmprunts,LocalDate date_creation ) {
            this.date_creation = date_creation;
            this.listeEmprunts = listeEmprunts;
        }

    public Abonnement(LocalDate date_creation , Double frais_Abonnement ) {
        this.date_creation = date_creation;
        this.frais_Abonnement = frais_Abonnement;
    }

    public Abonnement(double frais_Abonnement, long cin) {
        this.frais_Abonnement = frais_Abonnement;
        this.cin = cin;
        this.date_creation= LocalDate.now();

    }

    public Abonnement(int idAbonn, Long cin , Double fraisAbonn, LocalDate dateCreation) {
            this.idAbonn=idAbonn;
            this.cin=cin;
        this.date_creation = dateCreation;
        this.frais_Abonnement = fraisAbonn;
    }

    public LocalDate getDate_creation() {
            return date_creation;
        }

        public void setDate_creation(LocalDate date_creation) {
            this.date_creation = date_creation;
        }

        public double getFrais_Abonnement() {
            return frais_Abonnement;
        }

        public void setFrais_Abonnement(double frais_Abonnement) {
            this.frais_Abonnement = frais_Abonnement;
        }

        public ArrayList<DetailEmprunt> getListeEmprunts() {
            return listeEmprunts;
        }

        public void setListeEmprunts(ArrayList<DetailEmprunt> listeEmprunts) {
            this.listeEmprunts = listeEmprunts;
        }


    public int getIdAbonn() {
            return this.idAbonn;
    }

    public void setCin(long cin) {
        this.cin = cin;
    }

    public void setIdAbonn(int idAbonn) {
        this.idAbonn = idAbonn;
    }

    public long getCin() {
        return cin;
    }

    @Override
    public String toString() {
        return "Abonnement{" +
                "date_creation=" + date_creation +
                ", frais_Abonnement=" + frais_Abonnement +
                ", cin=" + cin +
                ", idAbonn=" + idAbonn +
                '}';
    }
}


