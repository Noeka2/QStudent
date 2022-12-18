package com.example.qstudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class StudentActivity extends AppCompatActivity {
    public  static TextView matricule,genre,frais,nom,faculte,promotion,filiere,annee,obs;
    Button btnscans,btnafficher;
    public static ImageView imageqr;
    EditText user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_student);

        imageqr=findViewById(R.id.qrimage);

        matricule=findViewById(R.id.mat);
        String mat=getIntent().getStringExtra("mat");
        matricule.setText(mat);

        String sexe=getIntent().getStringExtra("sexe");

        nom=findViewById(R.id.nom_frais);
        String noms=getIntent().getStringExtra("nom");
        String nomc=noms+"  ["+sexe+"]";
        nom.setText(nomc);



        faculte=findViewById(R.id.fac);
        String facul=getIntent().getStringExtra("fac");
        faculte.setText(facul);

        promotion=findViewById(R.id.prom);
        String promot=getIntent().getStringExtra("prom");
        promotion.setText(promot);


        filiere=findViewById(R.id.fil);
        String filier=getIntent().getStringExtra("fil");
        filiere.setText(filier);

        annee=findViewById(R.id.annee_ac);
        String anneeac=getIntent().getStringExtra("annee");
        annee.setText(anneeac);

        btnscans=findViewById(R.id.btnscans);
        btnscans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                Intent intent=new Intent(StudentActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        btnafficher=findViewById(R.id.btnaffiche);
        btnafficher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=getIntent().getStringExtra("mat");
                QRGEncoder encoder=new QRGEncoder(text,null, QRGContents.Type.TEXT,800);
                imageqr.setImageBitmap(encoder.getBitmap());
            }
        });
    }


}