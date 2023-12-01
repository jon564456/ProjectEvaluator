package com.itsoeh.jbrigido.projectevaluator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragProfile extends Fragment {
    private TextView tv_nombre;
    private EditText txt_nombre, txt_appa, txt_apma, txt_email, txt_contra;

    private Button guardar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static fragProfile newInstance(String param1, String param2) {
        fragProfile fragment = new fragProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_nombre = view.findViewById(R.id.pro_text_nombre_com);
        txt_nombre = view.findViewById(R.id.pro_text_nombre);
        txt_appa = view.findViewById(R.id.pro_text_appa);
        txt_apma = (EditText) view.findViewById(R.id.pro_text_apma);
        txt_email = view.findViewById(R.id.pro_text_email);
        txt_email.setEnabled(false);
        txt_contra = view.findViewById(R.id.pro_text_contra);
        guardar = view.findViewById(R.id.btn_pro_guardar);
        Bundle datos = this.getActivity().getIntent().getExtras();

        if (datos != null) {
            tv_nombre.setText(datos.getString("nombre") + " " + datos.getString("apepa") + " " + datos.getString("apema"));
            txt_nombre.setText(datos.getString("nombre"));
            txt_appa.setText(datos.getString("apepa"));
            txt_apma.setText(datos.getString("apema"));
            txt_email.setText(datos.getString("email"));
            txt_contra.setText(datos.getString("pass"));
        }

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue solicitud = VolleySingleton.getInstance(requireContext()).getRequestQueue();
                StringRequest request = new StringRequest(Request.Method.POST, API.ACTUALIZAR_ADMINISTRADOR, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respuesta = new JSONObject(response);
                            if (!respuesta.getBoolean("error")) {
                                JSONArray contenidoArray = respuesta.getJSONArray("contenido");
                                if (contenidoArray.length() > 0) {
                                    JSONObject contenido = contenidoArray.getJSONObject(0);
                                    int id = contenido.getInt("id");
                                    String nombre = contenido.getString("nom");
                                    String appa = contenido.getString("app");
                                    String apma = contenido.getString("apm");
                                    String email = contenido.getString("email");
                                    String contrasena = contenido.getString("contra");
                                    txt_nombre.setText(nombre);
                                    txt_appa.setText(appa);
                                    txt_apma.setText(apma);
                                    txt_email.setText(email);
                                    txt_contra.setText(contrasena);
                                    datos.putInt("id", id);
                                    datos.putString("nombre", nombre);
                                    datos.putString("apepa", appa);
                                    datos.putString("apema", apma);
                                    datos.putString("email", email);
                                    datos.putString("pass", contrasena);
                                    fragProfile.this.getActivity().getIntent().putExtras(datos);
                                }
                                Toast.makeText(tv_nombre.getContext(), "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(tv_nombre.getContext(), "Error al actualizar los datos ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(tv_nombre.getContext(), "Error al actualizar los datos" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        // Agrega el correo electrónico al mapa de parámetros
                        params.put("id", String.valueOf(datos.getInt("id")));
                        params.put("nom", txt_nombre.getText().toString());
                        params.put("app", txt_appa.getText().toString());
                        params.put("apm", txt_apma.getText().toString());
                        params.put("email", txt_email.getText().toString());
                        params.put("contra", txt_contra.getText().toString());
                        return params;
                    }
                };
                if (validar_vacios()) {
                    Toast.makeText(fragProfile.this.getContext(), "Completa los campos requeridos", Toast.LENGTH_SHORT).show();
                } else if (validar_nombre()) {
                    Toast.makeText(fragProfile.this.getContext(), "Los nombres, apellidos y/o no deben contener símbolos", Toast.LENGTH_SHORT).show();
                } else if (txt_contra.getText().length() < 8) {
                    Toast.makeText(fragProfile.this.getContext(), "La contraseña debe ser mayor a 8 caracteres", Toast.LENGTH_SHORT).show();
                } else {
                    solicitud.add(request);
                }
            }
        });

    }

    private boolean validar_nombre() {
        return !txt_nombre.getText().toString().trim().matches("^[A-Za-z]+( [A-Za-z]+)*$") || !txt_appa.getText().toString().trim().matches("^[A-Za-z]+( [A-Za-z]+)*$") || !txt_apma.getText().toString().trim().matches("^[A-Za-z]+( [A-Za-z]+)*$");
    }

    private boolean validar_correo() {
        return txt_email.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean validar_vacios() {
        String nombre = txt_nombre.getText().toString().trim();
        String apepa = txt_appa.getText().toString().trim();
        String contrasena = txt_contra.getText().toString().trim();
        return nombre.isEmpty() || apepa.isEmpty() || contrasena.isEmpty();
    }
}