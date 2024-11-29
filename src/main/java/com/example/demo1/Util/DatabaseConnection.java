package com.example.demo1.Util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    String  BD="Biblio";
    String url="jdbc:mysql://localhost:3306/"+BD;
    String username="root";
    String password="";



    public Connection connexionBD() {

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connecté !");
            return (conn);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }



    }

//    public static void main(String[] args) {
//        DatabaseConnection conn =new DatabaseConnection();
//        conn.connexionBD();
//
//    }


}


