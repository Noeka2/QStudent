package com.example.qstudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.*;
public class MainActivity extends AppCompatActivity {
    Connection connect;
    Button btnconnexion;
    public static EditText Username,Password;
    public static TextView errors,charge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        btnconnexion=findViewById(R.id.btnstudent);
        btnconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetOn()==true){
                    Username = (EditText) findViewById(R.id.txtusernamme);
                    Password =(EditText)  findViewById(R.id.txtpass);
                    if(TextUtils.isEmpty(Username.getText().toString()) | TextUtils.isEmpty(Password.getText().toString())){
                        Toast.makeText(MainActivity.this, "Pas de champ vide svp!!", Toast.LENGTH_SHORT).show();
                    }else{
                        btnconnexion.setEnabled(false);
                        new Async().execute();
                    }

                }else{
                    Toast.makeText(MainActivity.this, "Pas de connexion internet", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    public void about(View v){
        Intent intent=new Intent(MainActivity.this,AboutActivity.class);
        startActivity(intent);
    }


    class Async extends AsyncTask<Void, Void, Void> {



        String records = "",error="";

        @Override

        protected Void doInBackground(Void... voids) {
            Username = (EditText) findViewById(R.id.txtusernamme);
            String uservalue = Username.getText().toString();

            Password =(EditText)  findViewById(R.id.txtpass);
            String passvalue = Password.getText().toString();

            charge=findViewById(R.id.txtcharge);
            errors=findViewById(R.id.txterror);

            charge.setText("Chargement...");
            errors.setText("");

            try

            {
                Class.forName("com.mysql.jdbc.Driver");

                Connection connection = DriverManager.getConnection("jdbc:mysql://196.1.137.136:3306/etudiantdb", "unimapon", "N@%l@m@p0n");
                if(connection!=null){
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM etudiant WHERE Username='"+ uservalue.toString() +"' and Password='"+ passvalue.toString() +"'");
                if(resultSet.next()) {
                    Intent intent=new Intent(MainActivity.this,StudentActivity.class);
                    intent.putExtra("mat",resultSet.getString(11));
                    intent.putExtra("nom",resultSet.getString(2));
                    intent.putExtra("sexe",resultSet.getString(3));
                    intent.putExtra("fac",resultSet.getString(4));
                    intent.putExtra("prom",resultSet.getString(5));
                    intent.putExtra("fil",resultSet.getString(6));
                    intent.putExtra("frais",resultSet.getString(7));
                    intent.putExtra("obs",resultSet.getString(8));
                    intent.putExtra("annee",resultSet.getString(12));
                    charge.setText("");
                    errors.setText("");
                    startActivity(intent);
                    btnconnexion.setEnabled(true);
                    finish();

                }
                else
                {
                    charge.setText("");
                    errors.setText("Mauvais identifiants!");
                    btnconnexion.setEnabled(true);
                }
                }else{
                    charge.setText("");
                    errors.setText("Désolé, erreur de connexion à la base de données");
                    btnconnexion.setEnabled(true);
                }
            }

            catch(Exception e)

            {
                charge.setText("");
                btnconnexion.setEnabled(true);
                if( e.getMessage()=="Only the original thread that created a view hierarchy can touch its views."){
                    errors.setText("");
                }else{
                    errors.setText(e.getMessage());
                }

            }

            return null;

        }



        @Override

        protected void onPostExecute(Void aVoid) {

          //  text.setText(records);

       //     if(error != "")

              //  errorText.setText(error);

            super.onPostExecute(aVoid);

        }





    }
    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            // Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            // Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

}