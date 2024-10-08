package com.itsoeh.jbrigido.projectevaluator.ui.crearcuenta;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;
import com.itsoeh.jbrigido.projectevaluator.ui.helpers.JavaMail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        String correo = text_email.getText().toString();
        String contrasena = text_password.getText().toString();
        /*---------------------------Validaciones--------------------------------------*/
        if (validar_vacios()) {
            showMessage("Completa los campos requeridos");
        } else if (validar_nombre()) {
            showMessage("Los nombre no deben contener simbolos");
        } else if (!validar_correo()) {
            showMessage("Ingrese un correo valido");
        } else if (validar_vacios()) {
            showMessage("Llene los campos obligatorio");
        } else if (validar_contrasena()) {
            showMessage("Las contraseñas no coinciden");
        } else if (text_password.getText().length() < 8) {
            showMessage("La contraseña debe ser mayor o igual a 8 caracteres");
        } else {


            //Solicita informacion a la base de datos en la nube
            RequestQueue solicitud = VolleySingleton.getInstance(this).getRequestQueue();
            StringRequest request = new StringRequest(Request.Method.POST, API.registrarUsuario,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject respuesta = new JSONObject(response);
                                if (!respuesta.getBoolean("error")) {
                                    JavaMail.sendEmail(correo, "Bienvenida a ProjectEvaluator", "Tu registro a sido exitoso, ya puedes ingresar al sistema. Gracias");
                                    Toast.makeText(CreateAccountActivity.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                                } else {
                                    String mensaje = respuesta.getString("message");
                                    Toast.makeText(CreateAccountActivity.this, mensaje, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(CreateAccountActivity.this, "Error al obtener respuesta.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CreateAccountActivity.this, "Error al obtener respuesta.", Toast.LENGTH_LONG).show();
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    // Mandamos los parametros para realizar la consulta
                    params.put("nombre", text_nombre.getText().toString());
                    params.put("apepa", text_apepa.getText().toString());
                    params.put("apema", text_apema.getText().toString());
                    params.put("correo", text_email.getText().toString());
                    params.put("contrasena", text_password.getText().toString());
                    return params;
                }
            };
            solicitud.add(request);
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

    //Validar que las contraseñas coincidan
    private boolean validar_contrasena() {
        String contrasena1 = text_password.getText().toString();
        String contrasena2 = text_password_confirmar.getText().toString();
        return !(contrasena1.equals(contrasena2));
    }

    //Valida que los nombre no contegan simbolos
    private boolean validar_nombre() {
        return !text_nombre.getText().toString().trim().matches("^[A-Za-z]+( [A-Za-z]+)*$") || !text_apepa.getText().toString().trim().matches("^[A-Za-z]+( [A-Za-z]+)*$") || !text_apema.getText().toString().trim().matches("^[A-Za-z]+([A-Za-z]+)*$");
    }

    //Valida el campo para validar correo
    private boolean validar_correo() {
        return text_email.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    //Valida vacios
    private boolean validar_vacios() {
        String nombre = text_nombre.getText().toString().trim();
        String apepa = text_apepa.getText().toString().trim();
        String correo = text_email.getText().toString().trim();
        String contrasena = text_password.getText().toString().trim();
        String contrasenados = text_password_confirmar.toString().trim();
        return nombre.isEmpty() || apepa.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || contrasenados.isEmpty();
    }

    ///metodo que realiza la funcion de mostrar un mensaje en pantalla
    public void showMessage(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sugerencia")
                .setMessage(text)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the click on the OK button
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}