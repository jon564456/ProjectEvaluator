package com.itsoeh.jbrigido.projectevaluator.ui.resultado;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.itsoeh.jbrigido.projectevaluator.adapters.AdapterResultados;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;
import com.itsoeh.jbrigido.projectevaluator.modelo.Equipo;
import com.itsoeh.jbrigido.projectevaluator.modelo.Integrante;
import com.itsoeh.jbrigido.projectevaluator.modelo.Proyecto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultadoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultadoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText textSearch;
    private RecyclerView reclis;
    private AdapterResultados x;
    private ArrayList<Equipo> proyectos;

    public ResultadoFragment() {
        // Required empty public constructor
    }

    public static ResultadoFragment newInstance(String param1, String param2) {
        ResultadoFragment fragment = new ResultadoFragment();
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
        return inflater.inflate(R.layout.fragment_frag_resultados, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener referencias a los elementos de la interfaz de usuario
        reclis = view.findViewById(R.id.res_reclis);
        reclis.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        textSearch = view.findViewById(R.id.res_text_buscador);

        // Configurar el listener para el campo de búsqueda
        textSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        // Inicializar la lista de resultados
        listar();
    }

    // Método para filtrar la lista de resultados
    private void filter(String parametro) {
        ArrayList<Equipo> listFilter = new ArrayList<>();
        for (Equipo x : proyectos) {
            if (x.getProyecto().getNombre().toLowerCase().contains(parametro.toLowerCase())) {
                listFilter.add(x);
            }
        }
        // Actualizar el adaptador con la lista filtrada
        if (x != null) {
            x.filter(listFilter);
        }
    }

    // Método para obtener y mostrar la lista de resultados
    public void listar() {
        this.proyectos = new ArrayList<>();
        RequestQueue solicitud = VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.GET, API.listarResultados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Procesar la respuesta del servidor
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        // Obtener la lista de resultados
                        JSONArray contenidoArray = respuesta.getJSONArray("data");
                        for (int i = 0; i < contenidoArray.length(); i++) {
                            Equipo proyecto = new Equipo();
                            JSONObject contenido = contenidoArray.getJSONObject(i);
                            proyecto.getProyecto().setNombre(contenido.getString("proyecto"));
                            proyecto.getProyecto().setClave("clave");
                            proyecto.getProyecto().setGrado(contenido.getInt("grado"));
                            Integrante integrante = new Integrante();
                            integrante.setNombre(contenido.getString("lider"));
                            proyecto.getIntegrantes().add(integrante);
                            proyecto.getProyecto().setCalificacion((int) (contenido.getDouble("calificacion")));
                            proyectos.add(proyecto);
                        }
                        x = new AdapterResultados(proyectos);
                        reclis.setAdapter(x);
                    }
                } catch (JSONException e) {
                    // Manejar errores al procesar la respuesta del servidor
                    Toast.makeText(getContext(), "Error al obtener respuesta", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejar errores de la solicitud Volley
                Toast.makeText(getContext(), "Error al obtener respuesta", Toast.LENGTH_SHORT).show();
            }
        });
        // Agregar la solicitud a la cola de solicitudes
        solicitud.add(request);
    }

}