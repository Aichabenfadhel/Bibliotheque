package com.example.demo1.Models;

public class Livre {

        protected int code ;
        private String titre , auteur,type ;
        private static int Nb_Livres =0;
        private int ISBN;
        private boolean disponibilite ;


        public Livre() {
            Nb_Livres ++ ;
            code=Nb_Livres;
            this.disponibilite=true;
        }

        public Livre(String titre, String auteur, int iSBN,String type) {
            super();
            this.titre = titre;
            this.auteur = auteur;
            ISBN = iSBN;
            this.type=type;
            this.disponibilite=true;
        }


        @Override
        public String toString() {
            return "Livre [code=" + code + ", titre=" + titre + ", auteur=" + auteur + ",diponibilité"+disponibilite+ "]";
        }

        int compare(Livre l1) {
            return (this.titre.compareToIgnoreCase(l1.titre));
        }
        static int compare(Livre l1,Livre l2) {
            return (l1.titre.compareToIgnoreCase(l2.titre));
        }

        public String getTitre() {
            return titre;
        }

        public void setTitre(String titre) {
            this.titre = titre;
        }

        public String getAuteur() {
            return auteur;
        }

        public void setAuteur(String auteur) {
            this.auteur = auteur;
        }


        public long getISBN() {
            return ISBN;
        }

        public void setISBN(int iSBN) {
            ISBN = iSBN;
        }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCode() {
        return  code;
    }

    public boolean isDisponibility() {
        return disponibilite;
    }

    public void setDisponibility(boolean disponibility) {
        this.disponibilite = disponibility;
    }

    public static void afficherLivres(Livre[] livres) {
            System.out.println("Liste des livres :");
            for (int i = 0; i < livres.length; i++) {
                System.out.println(livres[i]);
            }
        }

    public void setCode(int code) {
            this.code=code;
    }
}
