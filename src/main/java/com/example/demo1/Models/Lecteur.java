package com.example.demo1.Models;

import java.util.Scanner;

public class Lecteur {


        private long cin ;
        private String nom ;
        private String prenom ;
        private Abonnement abonn;
        private long credit ;
        public Lecteur(long cin, String nom, String prenom) {

            this.cin = cin;
            this.nom = nom;
            this.prenom = prenom;
        }

        public Lecteur() {

        }

        public Lecteur(long cin, String nom, String prenom,Abonnement abonn,double frais ){

            this.cin = cin;
            this.nom = nom;
            this.prenom = prenom;
            this.abonn=abonn;
            this.abonn.setFrais_Abonnement(frais);
        }


        public Lecteur(long cin, String nom, String prenom, Abonnement abonn, double frais,long credit) {
            super();
            this.cin = cin;
            this.nom = nom;
            this.prenom = prenom;
            this.abonn = abonn;
            this.abonn.setFrais_Abonnement(frais);
            this.credit = credit;
        }

    public Lecteur(long cin, String nom, String prenom, long credit) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;

        this.credit = credit;
    }

    @Override
        public String toString() {
            return "Lecteur [cin=" + cin + ", nom=" + nom + ", prenom=" + prenom + "]";
        }

        public long getCin() {
            return cin;
        }

        public void setCin(long cin) {
            this.cin = cin;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }


        public Abonnement getAbonn() {
            return abonn;
        }

        public void setAbonn(Abonnement abonn) {
            this.abonn = abonn;
        }


        public long getCredit() {
            return credit;
        }
        public void setCredit(long credit) {
            this.credit = credit;
        }
        public double getFraisAbonnement() {
            if(abonn!=null) {

                return abonn.getFrais_Abonnement();}

            else {
                System.out.println("le lecteur n a pas un abonnement");
                return 0;
            }
        }

//        public long payement (long sommeVersee) throws CreditNegatifException {
//
//            if (sommeVersee > abonn.getFrais_Abonnement()) throw new CreditNegatifException("Credit negatif") ;
//            this.credit= (long) (abonn.getFrais_Abonnement()-sommeVersee);
//
//            return credit ;
//
//        }




    }


