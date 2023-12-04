package com.itsoeh.jbrigido.projectevaluator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragInicio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragInicio extends Fragment {

    private TextView textnombre;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView totalusuarios, totalAlumno, totalEvaluador, totalproyecto, totalnoevaluado, totalevaluado;

    public FragInicio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment navInicio.
     */
    // TODO: Rename and change types and number of parameters
    public static FragInicio newInstance(String param1, String param2) {
        FragInicio fragment = new FragInicio();
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
        return inflater.inflate(R.layout.fragment_nav_inicio, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicialización de vistas
        textnombre = view.findViewById(R.id.nav_text_nombre);
        totalAlumno = view.findViewById(R.id.nav_txt_alumno);
        totalusuarios = view.findViewById(R.id.nav_txt_usuario_total);
        totalEvaluador = view.findViewById(R.id.nav_txt_evaluador);
        totalnoevaluado = view.findViewById(R.id.nav_txt_no_evaluado);
        totalevaluado = view.findViewById(R.id.nav_txt_evaluado);
        totalproyecto = view.findViewById(R.id.nav_txt_proyecto_total);

        // Obtener datos del Intent
        Bundle datos = getActivity().getIntent().getExtras();
        if (datos != null) {
            datos.getInt("id");  // Se obtiene el id, pero no se está utilizando
            textnombre.setText(datos.getString("nombre") + " " + datos.getString("apepa") + " " + datos.getString("apema"));
            datos.getString("email");  // Se obtiene el email, pero no se está utilizando
            datos.getString("pass");   // Se obtiene la contraseña, pero no se está utilizando
        }

        // Llamar al método para obtener y mostrar la lista
        listar();
    }

    // Método para obtener y mostrar el conteo de alumnos y evaluadores
    public void listar() {
        RequestQueue solicitud = VolleySingleton.getInstance(this.getContext()).getRequestQueue();

        // Solicitud para contar el número de alumnos
        StringRequest request = new StringRequest(Request.Method.GET, API.CONTAR_ALUMNNOS, new Response.Listener<String>() {
            int res = 0;

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        JSONArray contenidoArray = respuesta.getJSONArray("contenido");
                        JSONObject x = contenidoArray.getJSONObject(0);
                        res = x.getInt("total");
                    } else {
                        Toast.makeText(FragInicio.this.getContext(), "Ocurrió un error", Toast.LENGTH_LONG).show();
                    }
                    totalAlumno.setText(res + "");  // Establecer el texto con el total de alumnos
                } catch (JSONException e) {
                    Toast.makeText(FragInicio.this.getContext(), e.getMessage() + "", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FragInicio.this.getContext(), "Hubo un error" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Agregar la solicitud a la cola
        solicitud.add(request);

        // Solicitud para contar el número de evaluadores
        request = new StringRequest(Request.Method.GET, API.CONTAR_EVALUADORES, new Response.Listener<String>() {
            int res = 0;

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        JSONArray contenidoArray = respuesta.getJSONArray("contenido");
                        JSONObject x = contenidoArray.getJSONObject(0);
                        res = x.getInt("total");
                    } else {
                        Toast.makeText(FragInicio.this.getContext(), "Ocurrió un error", Toast.LENGTH_LONG).show();
                    }
                    totalEvaluador.setText(res + "");  // Establecer el texto con el total de evaluadores
                    int total = (Integer.parseInt(totalAlumno.getText().toString()) + Integer.parseInt(totalEvaluador.getText().toString()));
                    totalusuarios.setText(total + "");  // Establecer el texto con el total de usuarios (alumnos + evaluadores)
                } catch (JSONException e) {
                    Toast.makeText(FragInicio.this.getContext(), e.getMessage() + "", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FragInicio.this.getContext(), "Hubo un error" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Agregar la solicitud a la cola
        solicitud.add(request);
    }
}