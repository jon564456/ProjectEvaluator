package com.itsoeh.jbrigido.projectevaluator.ui.proyecto;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
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
import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.adapters.AdapterEquipo;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;
import com.itsoeh.jbrigido.projectevaluator.modelo.Proyecto;
import com.itsoeh.jbrigido.projectevaluator.ui.helpers.VerificarConexion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProyectoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProyectoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<Proyecto> proyectos = new ArrayList<>();
    private AdapterEquipo x;
    private RecyclerView reclista;
    private NavController nav;
    private EditText buscador;
    private String mParam1;
    private String mParam2;
    private TextView txt_mensaje;

    public ProyectoFragment() {
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
    public static ProyectoFragment newInstance(String param1, String param2) {
        ProyectoFragment fragment = new ProyectoFragment();
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
        buscador = view.findViewById(R.id.pro_txt_buscador);
        reclista = view.findViewById(R.id.proy_reclis);
        reclista.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        txt_mensaje = view.findViewById(R.id.txt_mensaje);
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
        if (VerificarConexion.verificarConexion(requireContext())) {
            listar();
        } else {
            Toast.makeText(requireContext(), "Sin conexión a internet", Toast.LENGTH_LONG).show();
        }
    }

    // Método para filtrar la lista de proyectos según la clave
    private void filter(String parametro) {
        ArrayList<Proyecto> listFilter = new ArrayList<>();
        for (Proyecto x : proyectos) {
            if (x.getNombre().contains(parametro.toLowerCase())) {
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
        StringRequest request = new StringRequest(Request.Method.GET, API.listarProyectos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        // Obtener la lista de proyectos del servidor
                        JSONArray contenidoArray = respuesta.getJSONArray("data");

                        if (contenidoArray.length() > 0) {

                            for (int i = 0; i < contenidoArray.length(); i++) {
                                Proyecto p = new Proyecto();
                                JSONObject atributos = contenidoArray.getJSONObject(i);
                                // Configuración de atributos del proyecto
                                p.setClave(atributos.getString("clave"));
                                p.setNombre(atributos.getString("nombre"));
                                p.setCategoria(atributos.getString("categoria"));
                                p.setDescripcion(atributos.getString("descripcion"));
                                p.setGrado(atributos.getInt("semestre"));
                                p.setGrupo(atributos.getString("grupo"));
                                proyectos.add(p);
                                reclista.setVisibility(View.VISIBLE);
                                txt_mensaje.setVisibility(View.GONE);
                                // Configuración del adaptador y asignación a la vista
                                x = new AdapterEquipo(proyectos);
                                reclista.setAdapter(x);
                            }

                        } else {
                            reclista.setVisibility(View.GONE);
                            txt_mensaje.setVisibility(View.VISIBLE);
                        }

                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error en la consulta de datos.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error en la consulta de datos.", Toast.LENGTH_SHORT).show();
            }
        });

        // Agregar la solicitud a la cola
        solicitud.add(request);
    }

}
