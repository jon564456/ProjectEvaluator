package com.itsoeh.jbrigido.projectevaluator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itsoeh.jbrigido.projectevaluator.config.DBusuario;
import com.itsoeh.jbrigido.projectevaluator.modelo.Usuario;

public class CreateAccountActivity extends AppCompatActivity {


    private EditText text_nombre;
    private EditText text_apepa;
    private EditText text_apema;
    private EditText text_email;
    private EditText text_password;
    private EditText text_password_confirmar;
    private Button btn_registrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        btn_registrar = findViewById(R.id.reg_btn_registrar);
        text_nombre = findViewById(R.id.reg_txt_nombre);
        text_apepa = findViewById(R.id.reg_txt_apepa);
        text_apema = findViewById(R.id.reg_txt_apema);
        text_email = findViewById(R.id.reg_txt_email);
        text_password = findViewById(R.id.reg_txt_pass);
        text_password_confirmar = findViewById(R.id.reg_txt_pass_confirmar);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });
    }

    private void registrar() {
        DBusuario dbUsuario = new DBusuario(this);
        Usuario u = new Usuario();
        try {
            if (validar_vacios() != false) {
                Toast.makeText(this, "Completa los campos requeridos", Toast.LENGTH_LONG).show();
                return;
            }
            if (validar_contrasena() != true) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                return;
            }
            if (validar_nombre() == false) {
                Toast.makeText(this, "Los nombres y/o no deben contener símbolos", Toast.LENGTH_LONG).show();
                return;
            }
            if (validar_correo() != true) {
                Toast.makeText(this, "Ingrese un correo valido", Toast.LENGTH_LONG).show();
                return;
            }
            if (validar_contrasena() == true && !(text_password.getText().length() >= 8)) {
                Toast.makeText(this, "La contraseña debe ser mayor a 8 caracteres.", Toast.LENGTH_LONG).show();
                return;
            }
            u.setNombre(text_nombre.getText().toString().trim().substring(0, 1) + text_nombre.getText().toString().trim().substring(1));
            u.setAppa(text_apepa.getText().toString().trim().substring(0, 1) + text_apepa.getText().toString().trim().substring(1));
            u.setApma(text_apema.getText().toString().trim().substring(0, 1) + text_apema.getText().toString().trim().substring(1));
            u.setCorreo(text_email.getText().toString().trim().substring(0, 1) + text_email.getText().toString().trim().substring(1));
            u.setContrasena(text_password.getText().toString().trim());
            dbUsuario.guardar(u);
            limpiar();
            Toast.makeText(this, "Registro exitoso.Se envío un correo de verficación.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al guardar. ", Toast.LENGTH_LONG).show();
        }
    }

    public void limpiar() {
        text_nombre.setText("");
        text_apepa.setText("");
        text_apema.setText("");
        text_password.setText("");
        text_password_confirmar.setText("");
        text_email.setText("");
    }

    private boolean validar_contrasena() {
        String contrasena1 = text_password.getText().toString();
        String contrasena2 = text_password_confirmar.getText().toString();
        return contrasena1.equals(contrasena2);
    }

    private boolean validar_nombre() {
        return text_nombre.getText().toString().trim().matches("^[A-Za-z]+( [A-Za-z]+)*$") ||
                text_apepa.getText().toString().trim().matches("^[A-Za-z]+( [A-Za-z]+)*$") ||
                text_apema.getText().toString().trim().matches("^[A-Za-z]+( [A-Za-z]+)*$");
    }

    private boolean validar_correo() {
        return text_email.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean validar_vacios() {
        String nombre = text_nombre.getText().toString().trim();
        String apepa = text_apepa.getText().toString().trim();
        String apema = text_apema.getText().toString().trim();
        String correo = text_email.getText().toString().trim();
        String contrasena = text_password.getText().toString().trim();
        String contrasenados = text_password_confirmar.toString().trim();
        return nombre.isEmpty() || apepa.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || contrasenados.isEmpty();
    }
}