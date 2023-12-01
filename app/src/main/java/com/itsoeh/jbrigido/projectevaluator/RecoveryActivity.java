package com.itsoeh.jbrigido.projectevaluator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecoveryActivity extends AppCompatActivity {

    //inicializacion de componetes
    private EditText text_correo;
    private Button btn_continuar;

    //creacion de la pantalla
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        text_correo = findViewById(R.id.rec_txt_correo);
        btn_continuar = findViewById(R.id.rec_btn_continuar);
        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text_correo.getText().toString().isEmpty()) {
                    Toast.makeText(RecoveryActivity.this, "Correo electrónico requerido", Toast.LENGTH_SHORT).show();
                } else if (!validar_correo()) {
                    Toast.makeText(RecoveryActivity.this, "Ingrese un correo valido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RecoveryActivity.this, "Se ha enviado un correo de recuperación", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //valida que el correo sea valido
    private boolean validar_correo() {
        return text_correo.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

}