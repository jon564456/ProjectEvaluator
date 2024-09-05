package com.itsoeh.jbrigido.projectevaluator.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

    //Inicializacion de la vista y componentes del activty
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        getSplashScreen().setOnExitAnimationListener(splashScreenView -> {
            final ObjectAnimator animator = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_Y,
                    0f,
                    splashScreenView.getHeight()
            );
            animator.setInterpolator(new AnticipateInterpolator());
            animator.setDuration(2000L);

            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    splashScreenView.remove();
                }
            });
            animator.start();
        });

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

    private void main(Bundle datos) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(datos);
        startActivity(intent);
    }

    //método permite el acceso al menu principal
    private void login() {
        String correo = text_usuario.getText().toString().trim();
        String pass = text_contrasena.getText().toString().trim();
        //valida los vacios
        if (validar_vacios()) {
            //toast que muestra el mensaje
            Toast.makeText(this, "Campos obliatorios", Toast.LENGTH_LONG).show();
        } else
            //invoca el metodo signin que envia como parametro el correo y contrasena de tipo string
            signin(correo, pass);
    }

    public void signin(String correo, String pass) {
        RequestQueue solicitud = VolleySingleton.getInstance(this).getRequestQueue();
        //uso de la api
        StringRequest request = new StringRequest(Request.Method.POST, API.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //obtenemos la respuesta
                    JSONObject respuesta = new JSONObject(response);
                    //si no hay error
                    if (!respuesta.getBoolean("error")) {
                        //obtiene el contenido
                        JSONArray contenidoArray = respuesta.getJSONArray("data");
                        //si el contenido es mayor a 0 entonces iobtiene los datos consultados
                        if (contenidoArray.length() > 0) {
                            JSONObject contenido = contenidoArray.getJSONObject(0);
                            String nombre = contenido.getString("nombre");
                            String appa = contenido.getString("apepa");
                            String apma = contenido.getString("apema");
                            Bundle datos = new Bundle();
                            datos.putString("correo", correo);
                            datos.putString("nombre", nombre);
                            datos.putString("apepa", appa);
                            datos.putString("apema", apma);
                            main(datos);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, "Correo o contraseña incorrectos.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Ocurriò un error no esperado.", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e("error",e.getMessage()+"");
                    Toast.makeText(LoginActivity.this, "Error en la recuperación de datos."+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error -->" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("correo", correo);
                params.put("pass", pass);
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