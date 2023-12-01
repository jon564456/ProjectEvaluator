package com.itsoeh.jbrigido.projectevaluator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText text_correo;
    private Button btn_continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        // Inicializa los elementos de la interfaz de usuario
        text_correo = findViewById(R.id.rec_txt_correo);
        btn_continuar = findViewById(R.id.rec_btn_continuar);

        // Configura el listener para el botón de continuar
        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verifica si el campo de correo electrónico está vacío
                if (text_correo.getText().toString().isEmpty()) {
                    Toast.makeText(CreateAccountActivity.this, "Correo electrónico requerido", Toast.LENGTH_SHORT).show();
                } else if (!validar_correo()) {
                    // Verifica si el correo electrónico ingresado es válido
                    Toast.makeText(CreateAccountActivity.this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show();
                } else {
                    // Muestra un mensaje de éxito si todo está bien
                    Toast.makeText(CreateAccountActivity.this, "Se ha enviado un correo de recuperación", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Método para validar el formato del correo electrónico
    private boolean validar_correo() {
        // Utiliza una expresión regular para verificar si el correo tiene un formato válido
        return text_correo.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
}