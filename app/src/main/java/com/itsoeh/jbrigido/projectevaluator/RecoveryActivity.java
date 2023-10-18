package com.itsoeh.jbrigido.projectevaluator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecoveryActivity extends AppCompatActivity {


    private EditText text_correo;
    private Button btn_continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        text_correo = findViewById(R.id.rec_txt_correo);
        btn_continuar = findViewById(R.id.rec_btn_continuar);
        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private boolean validar_correo() {
        return text_correo.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

}