package com.example.forms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNome, editTextIdade, editTextCurso, editTextRA;
    private RadioGroup radioGroupSexo;
    private RadioButton radioButtonMasculino, radioButtonFeminino;
    private Button buttonFoto, buttonSubmit, textViewEmail;


    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextIdade = findViewById(R.id.editTextIdade);
        editTextCurso = findViewById(R.id.editTextCurso);
        editTextRA = findViewById(R.id.editTextRA);

        radioGroupSexo = findViewById(R.id.radioGroupSexo);
        radioButtonMasculino = findViewById(R.id.radioButtonMasculino);
        radioButtonFeminino = findViewById(R.id.radioButtonFeminino);

        buttonFoto = findViewById(R.id.buttonFoto);
        buttonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");

            String filename = "my_image.png";
            FileOutputStream outputStream;
            try {
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendData() {
        String nome = editTextNome.getText().toString().trim();
        String idade = editTextIdade.getText().toString().trim();
        String curso = editTextCurso.getText().toString().trim();
        String ra = editTextRA.getText().toString().trim();
        String sexo = radioButtonMasculino.isChecked() ? "masculino" : "feminino";
        Bitmap foto = imageBitmap;

        // Verifica se o nome contém apenas letras
        if (!nome.matches("[a-zA-Z]+")) {
            Toast.makeText(MainActivity.this, "Apenas letras são permitidas no campo nome", Toast.LENGTH_SHORT).show();
            return;
        }
        if (idade.isEmpty() || !idade.matches("\\d{1,2}")) {
            Toast.makeText(MainActivity.this, "Apenas números são permitidos no campo \"idade\" e o valor deve ter no máximo 2 dígitos", Toast.LENGTH_LONG).show();
            return;
        }
        if (!radioButtonMasculino.isChecked() && !radioButtonFeminino.isChecked()) {
            Toast.makeText(MainActivity.this, "Selecione uma opção de sexo", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(MainActivity.this, DataActivity.class);
        intent.putExtra("nome", nome);
        intent.putExtra("idade", editTextIdade.getText().toString());
        intent.putExtra("curso", editTextCurso.getText().toString());
        intent.putExtra("ra", editTextRA.getText().toString());
        intent.putExtra("sexo", radioButtonMasculino.isChecked() ? "masculino" : "feminino");
        intent.putExtra("foto", (Parcelable) null);
        startActivity(intent);
    }

}






