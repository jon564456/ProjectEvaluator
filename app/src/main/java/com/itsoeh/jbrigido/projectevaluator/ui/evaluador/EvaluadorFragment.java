package com.itsoeh.jbrigido.projectevaluator.ui.evaluador;

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
import com.itsoeh.jbrigido.projectevaluator.adapters.AdapterEvaluador;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;
import com.itsoeh.jbrigido.projectevaluator.modelo.Evaluador;
import com.itsoeh.jbrigido.projectevaluator.modelo.Proyecto;
import com.itsoeh.jbrigido.projectevaluator.ui.helpers.VerificarConexion;

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
    private TextView txt_mensaje;

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
        txt_mensaje = view.findViewById(R.id.txt_mensaje);
        // Llamar al método para listar evaluadores
        if (VerificarConexion.verificarConexion(requireContext())) {
            listar();
        } else {
            Toast.makeText(requireContext(), "Sin conexión a internet", Toast.LENGTH_LONG).show();
        }
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
        StringRequest request = new StringRequest(Request.Method.GET, API.listarEvaludares, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Procesar la respuesta del servidor
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        // Obtener la lista de evaluadores
                        JSONArray contenidoArray = respuesta.getJSONArray("data");
                        if (contenidoArray.length() > 0) {
                            for (int i = 0; i < contenidoArray.length(); i++) {
                                Evaluador x = new Evaluador();
                                JSONObject atributos = contenidoArray.getJSONObject(i);
                                x.setId(atributos.getInt("id"));
                                x.setUsername(atributos.getString("username"));
                                x.setNombre(atributos.getString("nombre"));
                                x.setApellidos(atributos.getString("apellidos"));
                                x.setCorreo(atributos.getString("correo"));
                                x.setProcedencia(atributos.getString("procedencia"));
                                x.setGrado(atributos.getString("grado"));
                                x.setEspecialidad(atributos.getString("especialidad"));
                                evaluadores.add(x);
                            }

                            // Configurar el adaptador con la lista de evaluadores
                            x = new AdapterEvaluador(evaluadores);
                            rec_lista.setAdapter(x);
                            rec_lista.setVisibility(View.VISIBLE);
                            txt_mensaje.setVisibility(View.GONE);
                        } else {
                            rec_lista.setVisibility(View.GONE);
                            txt_mensaje.setVisibility(View.VISIBLE);
                        }
                    } else {
                        // Mostrar mensaje de error si hay un error en la respuesta
                        Toast.makeText(EvaluadorFragment.this.getContext(), "Error al recuperar informaciòn.", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // Mostrar mensaje de error en caso de excepción JSON
                    Toast.makeText(EvaluadorFragment.this.getContext(), "Error al obtener respuesta." + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Mostrar mensaje de error en caso de error de solicitud Volley
                Toast.makeText(EvaluadorFragment.this.getContext(), "Error al obtener respuesta." + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        // Agregar la solicitud a la cola de solicitudes
        solicitud.add(request);
    }

}