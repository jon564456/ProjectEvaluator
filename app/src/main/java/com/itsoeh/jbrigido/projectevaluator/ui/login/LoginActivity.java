package com.itsoeh.jbrigido.projectevaluator.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;
import com.itsoeh.jbrigido.projectevaluator.ui.crearcuenta.CreateAccountActivity;
import com.itsoeh.jbrigido.projectevaluator.ui.main.MainActivity;
import com.itsoeh.jbrigido.projectevaluator.ui.recovery.RecoveryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    //Componentes utilizados en el activity
    private EditText text_usuario;
    private EditText text_contrasena;
    private Button btn_login;
    private TextView btn_forget, btn_register;
    private FirebaseAuth auth;

    //Inicializacion de la vista y componentes del activty
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

    //método que abre el menu registrar
    private void register() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    //método que abre el menu olvide mi contraseña
    private void forget() {
        Intent intent = new Intent(this, RecoveryActivity.class);
        startActivity(intent);
    }
    private void main() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    //método permite el acceso al menu principal
    private void login() {
        String correo = text_usuario.getText().toString().trim();
        String pass = text_contrasena.getText().toString().trim();
        //valida los vacios
        if (validar_vacios()) {
            //toast que muestra el mensaje
            Toast.makeText(this, "Correo y contraseña requeridos", Toast.LENGTH_LONG).show();
        } else
            //invoca el metodo signin que envia como parametro el correo y contrasena de tipo string
            signin(correo, pass);
    }

    //método sigin recibe como parametros correo y contrasena
    public void signin(String correo, String pass) {
        //Inicializacion de objetos para consulta a la base de dtos
        RequestQueue solicitud = VolleySingleton.getInstance(this).getRequestQueue();
        //uso de la api
        StringRequest request = new StringRequest(Request.Method.POST, API.BUSCAR_ADMINISTRADOR, new Response.Listener<String>() {
            //Relizamos la consulta
            @Override
            public void onResponse(String response) {
                try {
                    //obtenemos la respuesta
                    JSONObject respuesta = new JSONObject(response);
                    //si no hay error
                    if (!respuesta.getBoolean("error")) {
                        //obtiene el contenido
                        JSONArray contenidoArray = respuesta.getJSONArray("contenido");
                        //si el contenido es mayor a 0 entonces iobtiene los datos consultados
                        if (contenidoArray.length() > 0) {
                            JSONObject contenido = contenidoArray.getJSONObject(0);
                            int id = contenido.getInt("id");
                            String nombre = contenido.getString("nom");
                            String appa = contenido.getString("app");
                            String apma = contenido.getString("apm");
                            String email = contenido.getString("email");
                            String contrasena = contenido.getString("contra");
                            //verifica la contrasena para permitir el acces
                            if (contrasena.equals(pass)) {
                                Bundle datos = new Bundle();
                                datos.putInt("id", id);
                                datos.putString("nombre", nombre);
                                datos.putString("apepa", appa);
                                datos.putString("apema", apma);
                                datos.putString("email", email.toLowerCase());
                                datos.putString("pass", contrasena);
                                //usamos firebase para envia el correo
                                main();

                            } else {
                                //muestra mensaje
                                Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            //muestra mensaje
                            Toast.makeText(LoginActivity.this, "Usuario no registrado", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (JSONException e) {
                    //muestra mensaje
                    Toast.makeText(LoginActivity.this, "Usuario no registrado", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            //muestra mensaje en caso de un error en la consulta
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error -->" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            //prepara los datos a enviar
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Agrega el correo electrónico al mapa de parámetros
                params.put("email", correo); // Asegúrate de que "correo" sea la clave correcta en tu API
                return params;
            }
        };//envía la solicitud
        solicitud.add(request);
    }

    //método para validar vacios
    private boolean validar_vacios() {
        return text_usuario.getText().toString().isEmpty() || text_contrasena.getText().toString().isEmpty();
    }
}