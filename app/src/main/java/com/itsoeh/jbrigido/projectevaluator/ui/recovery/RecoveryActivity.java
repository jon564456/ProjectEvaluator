package com.itsoeh.jbrigido.projectevaluator.ui.recovery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;
import com.itsoeh.jbrigido.projectevaluator.ui.crearcuenta.CreateAccountActivity;
import com.itsoeh.jbrigido.projectevaluator.ui.helpers.JavaMail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
                    String correo = String.valueOf(text_correo.getText());
                    String nuevaContrasena = ramdonContrasena();
                    RequestQueue solicitud = VolleySingleton.getInstance(text_correo.getContext()).getRequestQueue();
                    StringRequest request = new StringRequest(Request.Method.POST, API.recuperarContrasena, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject respuesta = new JSONObject(response);
                                if (!respuesta.getBoolean("error")) {
                                    JavaMail.sendEmail(correo, "Restablecimiento de contraseña", "Su contraseña nueva es: " + nuevaContrasena+". Se recomienda cambiar al acceder a tu cuenta.");
                                    String mensaje = respuesta.getString("message");
                                    Toast.makeText(RecoveryActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                                } else {
                                    String mensaje = respuesta.getString("message");
                                    Toast.makeText(RecoveryActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(RecoveryActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RecoveryActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("correo", correo);
                            params.put("contrasena",nuevaContrasena);
                            return params;
                        }
                    };
                    solicitud.add(request);
                }
            }
        });
    }

    private String ramdonContrasena() {
        StringBuilder nuevaContrasena = new StringBuilder();

        while (nuevaContrasena.length() < 6) {
            int num = (int) (Math.random() * 9);
            System.out.println(num);
            nuevaContrasena.append(num);
        }
        return nuevaContrasena.toString();
    }

    //valida que el correo sea valido
    private boolean validar_correo() {
        return text_correo.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

}