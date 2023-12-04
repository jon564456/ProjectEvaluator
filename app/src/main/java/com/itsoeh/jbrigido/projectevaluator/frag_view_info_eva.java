package com.itsoeh.jbrigido.projectevaluator;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.DBEvaluador;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;
import com.itsoeh.jbrigido.projectevaluator.modelo.Evaluador;
import com.itsoeh.jbrigido.projectevaluator.modelo.Proyecto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag_view_info_eva#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_view_info_eva extends Fragment {


    private TextView txt_nombre, txt_correo, txt_grado, txt_procedencia;
    private NavController nav;
    private Spinner sp1, sp2, sp3;
    private ImageView guardar, guardar2, guardar3;

    private Evaluador selecionado;

    private int indiceSpinner1 = -1;
    private int indiceSpinner2 = -1;
    private int indiceSpinner3 = -1;
    ArrayList<Proyecto> opciones = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frag_view_info_eva() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_view_info_eva.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_view_info_eva newInstance(String param1, String param2) {
        frag_view_info_eva fragment = new frag_view_info_eva();
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
        return inflater.inflate(R.layout.fragment_frag_view_info_eva, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar elementos de la interfaz de usuario
        nav = Navigation.findNavController(view);
        txt_nombre = view.findViewById(R.id.info_eva_text_nombre);
        txt_correo = view.findViewById(R.id.info_eva_text_correo);
        txt_grado = view.findViewById(R.id.info_eva_text_grado);
        txt_procedencia = view.findViewById(R.id.info_eva_text_procedencia);
        guardar = view.findViewById(R.id.info_eva_btn_guardar_sp1);
        guardar2 = view.findViewById(R.id.info_eva_btn_guardar_sp2);
        guardar3 = view.findViewById(R.id.info_eva_btn_guardar_sp3);
        sp1 = view.findViewById(R.id.info_eva_pro_asig1);
        sp2 = view.findViewById(R.id.info_eva_pro_asig2);
        sp3 = view.findViewById(R.id.info_eva_pro_asig3);

        // Recuperar datos del evaluador de los argumentos
        Bundle datos = this.getArguments();
        if (datos != null) {
            selecionado = new Evaluador();
            selecionado.setId(Integer.parseInt(datos.getString("id")));
            selecionado.setNombre(datos.getString("nom"));
            selecionado.setAppa(datos.getString("app"));
            selecionado.setApma(datos.getString("apm"));
            selecionado.setCorreo(datos.getString("mail"));
            selecionado.setGrado(datos.getString("grad"));
            selecionado.setProcedencia(datos.getString("pro"));
            selecionado.setProyectos(new DBEvaluador(this.getContext()).Asignados(selecionado.getId()));
            txt_nombre.setText(selecionado.getNombre() + " " + selecionado.getAppa() + " " + selecionado.getApma());
            txt_correo.setText(selecionado.getCorreo());
            txt_grado.setText(selecionado.getGrado());
            txt_procedencia.setText(selecionado.getProcedencia());
        }

        // Poblar proyectos disponibles en los spinners
        listarDisponibles();

        // Listeners de clic en botones
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asignarproyecto1();
            }
        });
        guardar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asignarproyecto2();
            }
        });
        guardar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asignarproyecto3();
            }
        });

        // Listeners de selección de elementos en spinners
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Proyecto seleccionado = (Proyecto) parent.getItemAtPosition(position);
                indiceSpinner1 = seleccionado.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Proyecto seleccionado = (Proyecto) parent.getItemAtPosition(position);
                indiceSpinner2 = seleccionado.getId();
                Log.e("Valor", indiceSpinner2 + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Proyecto seleccionado = (Proyecto) parent.getItemAtPosition(position);
                indiceSpinner3 = seleccionado.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });
    }

    /**
     * Asigna un proyecto seleccionado al evaluador mediante una solicitud de red.
     *
     * @param eva ID del evaluador.
     * @param pro ID del proyecto a asignar.
     */
    public void guardarpro(int eva, int pro) {
        RequestQueue solicitud = VolleySingleton.getInstance(frag_view_info_eva.this.getContext()).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.POST, API.ASIGNAR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        Toast.makeText(frag_view_info_eva.this.getContext(), "Proyecto asignado correctamente", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(frag_view_info_eva.this.getContext(), "Hubo un error" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(frag_view_info_eva.this.getContext(), "Hubo un error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("eva", String.valueOf(eva));
                params.put("pro", String.valueOf(pro));
                return params;
            }
        };
        solicitud.add(request);

    }
    /**
     * Asigna el primer proyecto seleccionado al evaluador actual.
     * Muestra un mensaje de éxito y hace visibles los elementos relacionados con el segundo proyecto.
     * En caso de error, muestra un mensaje de fallo.
     */
    private void asignarproyecto1() {
        try {
            guardarpro(selecionado.getId(), indiceSpinner1);
            Toast.makeText(this.getContext(), "Proyecto asignado correctamente", Toast.LENGTH_SHORT).show();
            sp2.setVisibility(View.VISIBLE);
            guardar2.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "No se pudo asignar el proyecto", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Asigna el segundo proyecto seleccionado al evaluador actual.
     * Muestra un mensaje de éxito y hace visibles los elementos relacionados con el tercer proyecto.
     * En caso de error o si el segundo proyecto es igual al primero, muestra un mensaje de fallo.
     */
    private void asignarproyecto2() {
        listarDisponibles();
        try {
            if (indiceSpinner1 == indiceSpinner2) {
                Toast.makeText(this.getContext(), "Asigna otro proyecto", Toast.LENGTH_SHORT).show();
                return;
            }
            guardarpro(selecionado.getId(), indiceSpinner2);
            Toast.makeText(this.getContext(), "Proyecto asignado correctamente", Toast.LENGTH_SHORT).show();
            sp3.setVisibility(View.VISIBLE);
            guardar3.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "No se pudo asignar el proyecto", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Asigna el tercer proyecto seleccionado al evaluador actual.
     * Muestra un mensaje de éxito.
     * En caso de error o si el tercer proyecto es igual a alguno de los anteriores, muestra un mensaje de fallo.
     */
    private void asignarproyecto3() {
        listarDisponibles();
        try {
            if (indiceSpinner1 == indiceSpinner3 || indiceSpinner3 == indiceSpinner2) {
                Toast.makeText(this.getContext(), "Asigna otro proyecto", Toast.LENGTH_SHORT).show();
                return;
            }
            guardarpro(selecionado.getId(), indiceSpinner3);
            Toast.makeText(this.getContext(), "Proyecto asignado correctamente", Toast.LENGTH_SHORT).show();
            sp3.setVisibility(View.VISIBLE);
            guardar3.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "No se pudo asignar el proyecto", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Recupera la lista de proyectos disponibles desde el servidor y la muestra en los spinners correspondientes.
     */
    public void listarDisponibles() {
        ArrayList<Proyecto> list = new ArrayList<>();
        RequestQueue solicitud = VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.GET, API.LISTAR_DISPONIBLES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        JSONArray contenidoArray = respuesta.getJSONArray("contenido");
                        for (int i = 0; i < contenidoArray.length(); i++) {
                            Proyecto x = new Proyecto();
                            JSONObject atributos = contenidoArray.getJSONObject(i);
                            int id = atributos.getInt("id");
                            String nombre = atributos.getString("nombre");
                            String clave = atributos.getString("clave");
                            String grado = atributos.getString("grado");
                            String grupo = atributos.getString("grupo");
                            String descripcion = atributos.getString("descripcion");
                            String status = atributos.getString("status");
                            String categoria = atributos.getString("categoria");
                            x.setId(id);
                            x.setNombre(nombre);
                            x.setClave(clave);
                            x.setGrado(Integer.parseInt(grado));
                            x.setGrupo(grupo);
                            x.setDescripcion(descripcion);
                            x.setStatus(status);
                            x.setCategoria(categoria);
                            list.add(x);
                        }
                        opciones = list;
                        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_expandable_list_item_1, opciones);
                        sp1.setAdapter(arrayAdapter);
                        sp2.setAdapter(arrayAdapter);
                        sp3.setAdapter(arrayAdapter);
                    } else {
                        Toast.makeText(frag_view_info_eva.this.getContext(), "Ocurrió un error", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(frag_view_info_eva.this.getContext(), e.getMessage() + "", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(frag_view_info_eva.this.getContext(), "Hubo un error" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        solicitud.add(request);
    }

    /**
     * Carga la lista de proyectos disponibles desde el servidor y la muestra en los spinners correspondientes.
     * Similar a listarDisponibles, pero podría haber alguna lógica adicional específica para la carga de proyectos.
     */
    public void cargarProyectos() {
        ArrayList<Proyecto> list = new ArrayList<>();
        RequestQueue solicitud = VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.GET, API.LISTAR_DISPONIBLES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        JSONArray contenidoArray = respuesta.getJSONArray("contenido");
                        for (int i = 0; i < contenidoArray.length(); i++) {
                            Proyecto x = new Proyecto();
                            JSONObject atributos = contenidoArray.getJSONObject(i);
                            int id = atributos.getInt("id");
                            String nombre = atributos.getString("nombre");
                            String clave = atributos.getString("clave");
                            String grado = atributos.getString("grado");
                            String grupo = atributos.getString("grupo");
                            String descripcion = atributos.getString("descripcion");
                            String status = atributos.getString("status");
                            String categoria = atributos.getString("categoria");
                            x.setId(id);
                            x.setNombre(nombre);
                            x.setClave(clave);
                            x.setGrado(Integer.parseInt(grado));
                            x.setGrupo(grupo);
                            x.setDescripcion(descripcion);
                            x.setStatus(status);
                            x.setCategoria(categoria);
                            list.add(x);
                        }
                        opciones = list;
                        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_expandable_list_item_1, opciones);
                        sp1.setAdapter(arrayAdapter);
                        sp2.setAdapter(arrayAdapter);
                        sp3.setAdapter(arrayAdapter);
                    } else {
                        Toast.makeText(frag_view_info_eva.this.getContext(), "Ocurrió un error", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(frag_view_info_eva.this.getContext(), e.getMessage() + "", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(frag_view_info_eva.this.getContext(), "Hubo un error" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        solicitud.add(request);
    }

}
