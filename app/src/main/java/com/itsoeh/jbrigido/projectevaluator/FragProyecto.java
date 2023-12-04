package com.itsoeh.jbrigido.projectevaluator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.jbrigido.projectevaluator.adapters.AdapterEquipo;
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
 * Use the {@link FragProyecto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragProyecto extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<Equipo> proyectos = new ArrayList<>();
    private AdapterEquipo x;
    private RecyclerView reclista;
    private NavController nav;
    private EditText buscador;
    private String mParam1;
    private String mParam2;

    public FragProyecto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_proyecto.
     */
    // TODO: Rename and change types and number of parameters
    public static FragProyecto newInstance(String param1, String param2) {
        FragProyecto fragment = new FragProyecto();
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
        return inflater.inflate(R.layout.fragment_frag_proyecto, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicialización de vistas
        nav = Navigation.findNavController(view);
        buscador = view.findViewById(R.id.proy_text_buscador);
        reclista = view.findViewById(R.id.proy_reclis);
        reclista.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        // Configuración del filtro al cambiar el texto en el buscador
        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Método antes de cambiar el texto
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Método durante el cambio de texto
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Método después de que el texto ha cambiado, llama al filtro
                filter(editable.toString());
            }
        });

        // Llamada al método para obtener y mostrar la lista de proyectos
        listar();
    }

    // Método para filtrar la lista de proyectos según la clave
    private void filter(String parametro) {
        ArrayList<Equipo> listFilter = new ArrayList<>();
        for (Equipo x : proyectos) {
            if (x.getProyecto().getClave().toLowerCase().contains(parametro.toLowerCase())) {
                listFilter.add(x);
            }
        }
        // Si x no es nulo, llama al método filter del adaptador
        if (x != null) {
            x.filter(listFilter);
        }
    }

    // Método para obtener y mostrar la lista de proyectos
    public void listar() {
        this.proyectos = new ArrayList<>();
        RequestQueue solicitud = VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.GET, API.LISTAR_EQUIPOS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        // Obtener la lista de proyectos del servidor
                        JSONArray contenidoArray = respuesta.getJSONArray("contenido");
                        for (int i = 0; i < contenidoArray.length(); i++) {
                            Equipo x = new Equipo();
                            Proyecto p = new Proyecto();
                            Integrante in = new Integrante();
                            JSONObject atributos = contenidoArray.getJSONObject(i);

                            // Configuración de atributos del proyecto
                            p.setId(atributos.getInt("pid"));
                            p.setNombre(atributos.getString("pnombre"));
                            p.setClave(atributos.getString("pclave"));
                            p.setCategoria(atributos.getString("pcategoria"));
                            p.setDescripcion(atributos.getString("pdescripcion"));
                            p.setGrado(atributos.getInt("pgrado"));
                            p.setGrupo(atributos.getString("pgrupo"));
                            p.setStatus(atributos.getString("pstatus"));

                            // Configuración de atributos del responsable
                            JSONArray responsable = atributos.getJSONArray("responsables");
                            JSONObject intres = responsable.getJSONObject(0);
                            if (intres.getString("rid").equals("")) {
                                in.setId(0);
                            } else {
                                in.setId(Integer.parseInt(intres.getString("rid")));
                            }
                            if (intres.getString("rmatri").equals("")) {
                                in.setId(0);
                            } else {
                                in.setId(Integer.parseInt(intres.getString("rmatri")));
                            }
                            in.setNombre(intres.getString("rnombre"));
                            in.setAppa(intres.getString("rapepa"));
                            in.setApma(intres.getString("rapema"));
                            in.setCorreo(intres.getString("rcorreo"));

                            // Configuración del equipo
                            x.getIntegrantes().add(in);
                            x.setProyecto(p);
                            proyectos.add(x);
                        }

                        // Configuración del adaptador y asignación a la vista
                        x = new AdapterEquipo(proyectos);
                        reclista.setAdapter(x);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error en la consulta de datos" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error en la consulta de datos" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Agregar la solicitud a la cola
        solicitud.add(request);
    }

}
