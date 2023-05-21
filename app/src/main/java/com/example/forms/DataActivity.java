package com.example.forms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class DataActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textViewNome, textViewIdade, textViewCurso, textViewRA, textViewSexo, textViewEmail;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        String filename = "my_image.png";
        File filePath = new File(getFilesDir(), filename);

        Bitmap originalBitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath());
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 1024, 1024, false); // specify the desired width and height of the image

        imageView = findViewById(R.id.imageViewFoto);
        textViewNome = findViewById(R.id.textViewNome);
        textViewIdade = findViewById(R.id.textViewIdade);
        textViewCurso = findViewById(R.id.textViewCurso);
        textViewRA = findViewById(R.id.textViewRA);
        textViewSexo = findViewById(R.id.textViewSexo);
        textViewEmail = findViewById(R.id.textViewEmail);

        Intent intent = getIntent();

        String nome = intent.getStringExtra("nome");
        String idade = intent.getStringExtra("idade");
        String curso = intent.getStringExtra("curso");
        String ra = intent.getStringExtra("ra");
        String sexo = intent.getStringExtra("sexo");
        String Email = intent.getStringExtra("Email");
        Bitmap imageBitmap = intent.getParcelableExtra("imageBitmap");

        imageView.setImageBitmap(resizedBitmap);
        textViewNome.setText("Nome: " + nome);
        textViewIdade.setText("Idade: " + idade);
        textViewCurso.setText("Curso: " + curso);
        textViewRA.setText("RA: " + ra);
        textViewEmail.setText("Email" + Email);

        if(sexo.equals("M")) {
            textViewSexo.setText("Sexo: Masculino");
        } else if(sexo.equals("F")) {
            textViewSexo.setText("Sexo: Feminino");
        }
    }
}