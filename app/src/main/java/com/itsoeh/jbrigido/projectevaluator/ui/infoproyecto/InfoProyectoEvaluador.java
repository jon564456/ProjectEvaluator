package com.itsoeh.jbrigido.projectevaluator.ui.infoproyecto;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.adapters.AdapterIntegrantes;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;
import com.itsoeh.jbrigido.projectevaluator.modelo.Integrante;
import com.itsoeh.jbrigido.projectevaluator.ui.helpers.ColorUtils;
import com.itsoeh.jbrigido.projectevaluator.ui.helpers.VerificarConexion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoProyectoEvaluador#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoProyectoEvaluador extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView txt_clave, txt_responsable, txt_titulo, txt_categoria, txt_grado, txt_mensaje;

    private RecyclerView rec_listIntegrantes;
    private ArrayList<Integrante> listaIntegrantes = new ArrayList<>();
    private Bundle datos;
    private LinearLayout identificador;
    private AdapterIntegrantes x;

    public InfoProyectoEvaluador() {
        // Required empty public constructor
    }

    public static InfoProyectoEvaluador newInstance(String param1, String param2) {
        InfoProyectoEvaluador fragment = new InfoProyectoEvaluador();
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

        return inflater.inflate(R.layout.fragment_frag_view_info_proyecto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener referencias a los elementos de la interfaz de usuario
        txt_clave = view.findViewById(R.id.txt_info_pro_clave);
        txt_titulo = view.findViewById(R.id.txt_info_pro_titulo);
        txt_responsable = view.findViewById(R.id.txt_info_pro_respon);
        txt_categoria = view.findViewById(R.id.txt_info_pro_cat);
        txt_grado = view.findViewById(R.id.txt_info_pro_grado);
        rec_listIntegrantes = view.findViewById(R.id.rec_pro_integrantes);
        identificador = view.findViewById(R.id.color_identificador);
        txt_mensaje = view.findViewById(R.id.txt_mensaje);

        // Obtener datos del Bundle
        datos = this.getArguments();

        if (datos != null) {
            // Asignar datos a los elementos de la interfaz de usuario
            txt_clave.setText(datos.getString("clave"));
            txt_titulo.setText(datos.getString("nombre"));
            if (VerificarConexion.verificarConexion(requireContext())) {
                cargarInformacionProyecto();
                listarIntegrantes();
            } else {
                Toast.makeText(requireContext(), "Sin conexión a internet", Toast.LENGTH_LONG).show();
            }
        }
        // Configurar el RecyclerView de integrantes
        rec_listIntegrantes.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        // Llamar al método para listar integrantes
        //listar(datos.getString("id"));
    }

    private void cargarInformacionProyecto() {

        RequestQueue solicitud = VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.POST, API.cargarInformacionProyecto, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        JSONArray contenido = respuesta.getJSONArray("data");
                        if (contenido.length() > 0) {
                            JSONObject object = contenido.getJSONObject(0);
                            txt_titulo.setText(object.getString("proyecto").trim());
                            txt_responsable.setText(object.getString("nombre").trim() + " " + object.getString("apellidos"));
                            txt_clave.setText(object.getString("clave"));
                            txt_categoria.setText(object.getString("categoria").trim());
                            txt_grado.setText(object.getString("semestre") + object.getString("grupo").trim());
                            ColorUtils.changeColor(identificador, Integer.parseInt(txt_grado.getText().toString().substring(0, 1)));
                        } else {
                            Toast.makeText(InfoProyectoEvaluador.this.getContext(), "Error al cargar la informaciòn.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(InfoProyectoEvaluador.this.getContext(), "Error al obtener respuesta.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InfoProyectoEvaluador.this.getContext(), "Error al obtener respuesta.", Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("clave", datos.getString("clave"));
                return params;
            }
        };
        solicitud.add(request);
    }

    // Método para listar integrantes del proyecto
    public void listarIntegrantes() {
        this.listaIntegrantes = new ArrayList<>();
        RequestQueue solicitud = VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.POST, API.listarIntegrante, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("respuesta", response);
                try {
                    // Procesar la respuesta del servidor
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        // Obtener la lista de integrantes
                        JSONArray contenidoArray = respuesta.getJSONArray("data");
                        if (contenidoArray.length() > 0) {
                            for (int i = 0; i < contenidoArray.length(); i++) {
                                Integrante x = new Integrante();
                                JSONObject consultado = contenidoArray.getJSONObject(i);
                                x.setNombre(consultado.getString("nombre").trim());
                                x.setMatricula(consultado.getInt("matricula"));
                                listaIntegrantes.add(x);
                            }
                            // Configurar el adaptador con la lista de integrantes
                            x = new AdapterIntegrantes(listaIntegrantes);
                            rec_listIntegrantes.setAdapter(x);
                            rec_listIntegrantes.setVisibility(View.VISIBLE);
                            txt_mensaje.setVisibility(View.GONE);
                        } else {
                            rec_listIntegrantes.setVisibility(View.GONE);
                            txt_mensaje.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    // Manejar errores al procesar la información JSON
                    Toast.makeText(InfoProyectoEvaluador.this.getContext(), "Error al obtener respuesta.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejar errores en la solicitud Volley
                Toast.makeText(InfoProyectoEvaluador.this.getContext(), "Error al obtener respuesta.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                // Parámetros para la solicitud POST
                Map<String, String> params = new HashMap<>();
                params.put("clave", datos.getString("clave"));
                return params;
            }
        };
        // Agregar la solicitud a la cola de solicitudes
        solicitud.add(request);
    }


}