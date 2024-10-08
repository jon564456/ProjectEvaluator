package com.itsoeh.jbrigido.projectevaluator.ui.perfil;

import android.os.Bundle;
import android.util.Log;
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
import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private EditText txt_nombre, txt_appa, txt_apma;

    private Button guardar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
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
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        // Obtener referencias a los elementos de la interfaz de usuario
        txt_nombre = view.findViewById(R.id.pro_text_nombre);
        txt_appa = view.findViewById(R.id.pro_text_appa);
        txt_apma = (EditText) view.findViewById(R.id.pro_text_apma);
        guardar = view.findViewById(R.id.btn_pro_guardar);

        // Obtener datos del Intent que inició la actividad
        Bundle datos = this.getActivity().getIntent().getExtras();

        // Verificar si hay datos y actualizar la interfaz de usuario con esos datos
        if (datos != null) {

            txt_nombre.setText(datos.getString("nombre"));
            txt_appa.setText(datos.getString("apepa"));
            txt_apma.setText(datos.getString("apema"));
        }

        // Configurar el listener para el botón de guardar
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Configurar la solicitud Volley para actualizar los datos del administrador
                RequestQueue solicitud = VolleySingleton.getInstance(requireContext()).getRequestQueue();
                StringRequest request = new StringRequest(Request.Method.POST, API.actualizarInformacion, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Procesar la respuesta del servidor
                            JSONObject respuesta = new JSONObject(response);

                            if (!respuesta.getBoolean("error")) {
                                Toast.makeText(txt_nombre.getContext(), "Datos actualizados correctamente.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Mostrar mensaje de error
                                Toast.makeText(txt_nombre.getContext(), "Error al actualizar los datos.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // Mostrar mensaje de error en caso de excepción
                            Toast.makeText(txt_nombre.getContext(), "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud Volley (en este caso, no se hace nada)
                    }
                }) {
                    // Configurar los parámetros de la solicitud POST
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("correo", String.valueOf(datos.getString("correo")));
                        params.put("nombre", txt_nombre.getText().toString());
                        params.put("apepa", txt_appa.getText().toString());
                        params.put("apema", txt_apma.getText().toString());
                        return params;
                    }
                };

                // Validar campos antes de realizar la solicitud
                if (validar_nombre()) {
                    Toast.makeText(ProfileFragment.this.getContext(), "Los nombres, apellidos y/o no deben contener símbolos", Toast.LENGTH_SHORT).show();
                } else {
                    // Realizar la solicitud Volley
                    solicitud.add(request);
                }
            }
        });
    }

    // Método para validar que los campos de nombre y apellidos no contengan símbolos
    private boolean validar_nombre() {
        return !txt_nombre.getText().toString().trim().matches("^[A-Za-z]+( [A-Za-z]+)*$") || !txt_appa.getText().toString().trim().matches("^[A-Za-z]+( [A-Za-z]+)*$") || !txt_apma.getText().toString().trim().matches("^[A-Za-z]+( [A-Za-z]+)*$");
    }

    // Método para validar el formato del correo electrónico
    private void validar_correo() {
        //   return txt_email.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
}