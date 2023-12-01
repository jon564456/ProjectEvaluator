package com.itsoeh.jbrigido.projectevaluator;

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
 * Use the {@link fragResultados#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragResultados extends Fragment {

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
    private ArrayList<Equipo> equipos;

    public fragResultados() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragResultados.
     */
    // TODO: Rename and change types and number of parameters
    public static fragResultados newInstance(String param1, String param2) {
        fragResultados fragment = new fragResultados();
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
        reclis = view.findViewById(R.id.res_reclis);
        reclis.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        textSearch = view.findViewById(R.id.res_buscar_pro  );
        listar();
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
    }

    private void filter(String parametro) {
        ArrayList<Equipo> listFilter = new ArrayList<>();
        for (Equipo x : equipos) {
            if (x.getProyecto().getNombre().toLowerCase().contains(parametro.toLowerCase())) {
                listFilter.add(x);
            }
        }
        if (x != null) {
            x.filter(listFilter);
        }
    }

    public void listar() {
        this.equipos = new ArrayList<>();
        RequestQueue solicitud = VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.GET, API.LISTAR_CALIFICACIONES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        JSONArray contenidoArray = respuesta.getJSONArray("contenido");
                        for (int i = 0; i < contenidoArray.length(); i++) {
                            Equipo equipo = new Equipo();
                            JSONObject contenido = contenidoArray.getJSONObject(i);
                            Integrante in = new Integrante();
                            Proyecto pr = new Proyecto();
                            int id = contenido.getInt("id");
                            String nombre = contenido.getString("nombre");
                            String categoria = contenido.getString("categoria");
                            String correo = contenido.getString("correo");
                            int calificacion = contenido.getInt("promedio");
                            ArrayList<Integrante> list = new ArrayList<>();
                            in.setCorreo(correo);
                            pr.setNombre(nombre);
                            pr.setId(id);
                            pr.setCalificacion(calificacion);
                            pr.setCategoria(categoria);
                            equipo.setProyecto(pr);
                            list.add(in);
                            equipo.setIntegrantes(list);
                            equipos.add(equipo);
                        }
                    }
                    x = new AdapterResultados(equipos);
                    reclis.setAdapter(x);
                } catch (
                        JSONException e) {
                    Toast.makeText(getContext(), "Hubo un error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Hubo un error " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        solicitud.add(request);
    }
}