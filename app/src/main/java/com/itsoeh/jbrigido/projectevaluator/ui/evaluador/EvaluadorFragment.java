package com.itsoeh.jbrigido.projectevaluator.ui.evaluador;

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
import com.itsoeh.jbrigido.projectevaluator.adapters.AdapterEvaluador;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;
import com.itsoeh.jbrigido.projectevaluator.modelo.Evaluador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EvaluadorFragment extends Fragment {

    private NavController nav;
    private ArrayList<Evaluador> evaluadores = new ArrayList<>();
    private EditText buscador;
    private RecyclerView rec_lista;
    private AdapterEvaluador x;

    public static EvaluadorFragment newInstance(String param1, String param2) {
        EvaluadorFragment fragment = new EvaluadorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_evaluador, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar NavController para la navegación
        nav = Navigation.findNavController(view);

        // Obtener referencias a los elementos de la interfaz de usuario
        buscador = view.findViewById(R.id.eva_txt_buscador);
        rec_lista = view.findViewById(R.id.eva_rec);
        rec_lista.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        // Llamar al método para listar evaluadores
        listar();

        // Configurar el listener para el campo de búsqueda
        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    // Método para filtrar la lista de evaluadores
    public void filter(String parametro) {
        ArrayList<Evaluador> listFilter = new ArrayList<>();
        for (Evaluador x : evaluadores) {
            if (x.getNombre().toLowerCase().contains(parametro.toLowerCase())) {
                listFilter.add(x);
            }
        }
        // Actualizar el adaptador con la lista filtrada
        if (x != null) {
            x.filter(listFilter);
        }
    }

    // Método para obtener y mostrar la lista de evaluadores
    public void listar() {
        this.evaluadores = new ArrayList<>();
        RequestQueue solicitud = VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.GET, API.LISTAR_EVALUADORES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Procesar la respuesta del servidor
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        // Obtener la lista de evaluadores
                        JSONArray contenidoArray = respuesta.getJSONArray("contenido");
                        for (int i = 0; i < contenidoArray.length(); i++) {
                            Evaluador x = new Evaluador();
                            JSONObject atributos = contenidoArray.getJSONObject(i);
                            int id = atributos.getInt("id");
                            String nombre = atributos.getString("nom");
                            String appa = atributos.getString("app");
                            String apma = atributos.getString("apm");
                            String email = atributos.getString("correo");
                            String especialidad = atributos.getString("especialidad");
                            String grado = atributos.getString("grado");
                            String procedencia = atributos.getString("procedencia");
                            x.setId(id);
                            x.setNombre(nombre);
                            x.setAppa(appa);
                            x.setApma(apma);
                            x.setCorreo(email);
                            x.setEspecialidad(especialidad);
                            x.setGrado(grado);
                            x.setProcedencia(procedencia);
                            evaluadores.add(x);
                        }
                        // Configurar el adaptador con la lista de evaluadores
                        x = new AdapterEvaluador(evaluadores);
                        rec_lista.setAdapter(x);
                    } else {
                        // Mostrar mensaje de error si hay un error en la respuesta
                        Toast.makeText(EvaluadorFragment.this.getContext(), "Ocurrió un error", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // Mostrar mensaje de error en caso de excepción JSON
                    Toast.makeText(EvaluadorFragment.this.getContext(), e.getMessage() + "", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Mostrar mensaje de error en caso de error de solicitud Volley
                Toast.makeText(EvaluadorFragment.this.getContext(), "Hubo un error " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        // Agregar la solicitud a la cola de solicitudes
        solicitud.add(request);
    }

}