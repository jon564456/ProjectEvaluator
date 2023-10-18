package com.itsoeh.jbrigido.projectevaluator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itsoeh.jbrigido.projectevaluator.config.DBusuario;
import com.itsoeh.jbrigido.projectevaluator.modelo.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText text_usuario;
    private EditText text_contrasena;
    private Button btn_login;
    private TextView btn_forget, btn_register;

    public static Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        text_usuario = findViewById(R.id.login_txt_usuario);
        text_contrasena = findViewById(R.id.login_txt_contrasena);
        btn_login = findViewById(R.id.login_btn_login);
        btn_register = findViewById(R.id.login_btn_register);
        btn_forget = findViewById(R.id.login_btn_forget);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forget();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private void register() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    private void forget() {
        Intent intent = new Intent(this, RecoveryActivity.class);
        startActivity(intent);
    }

    private void login() {
        DBusuario dbUsuario = new DBusuario(this);
        String correo = text_usuario.getText().toString().trim();
        String pass = text_contrasena.getText().toString().trim();
        if (validar_vacios() != false) {
            Toast.makeText(this, "Existen campos vacios.", Toast.LENGTH_LONG).show();
            return;
        }
        if (dbUsuario.buscarContrasenia(correo) != null) {
            if (pass.equals(dbUsuario.buscarContrasenia(correo)[1])) {
                Intent intent = new Intent(this, MainActivity.class);
                dbUsuario.UserLogeado(correo);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Contraseña incorrecta.", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Usuario no registrado.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validar_vacios() {
        return text_usuario.getText().toString().isEmpty() || text_contrasena.getText().toString().isEmpty();
    }
}