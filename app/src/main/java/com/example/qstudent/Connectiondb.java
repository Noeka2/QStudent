package com.example.qstudent;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connectiondb {

    public static Connection conClass(){
        Connection con=null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://196.1.137.136:3306/etudiantdb", "unimapon", "N@%l@m@p0n");
        }
        catch (Exception ex)
        {
            Log.e("Erreur : ", ex.getMessage());
        }
        return con;
    }
}
