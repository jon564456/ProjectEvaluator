package com.itsoeh.jbrigido.projectevaluator.ui.atributos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.adapters.AdapterAtributos;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;
import com.itsoeh.jbrigido.projectevaluator.modelo.Atributo;
import com.itsoeh.jbrigido.projectevaluator.modelo.CategoriaProyecto;
import com.itsoeh.jbrigido.projectevaluator.modelo.Evaluador;
import com.itsoeh.jbrigido.projectevaluator.ui.helpers.VerificarConexion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Atributos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Atributos extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView btn_agregar;
    private PopupWindow popupAtributos;
    private int indice = -1;
    private EditText txt_buscardor;
    private ArrayList<Atributo> lista_atriibutos;
    private AdapterAtributos adapterAtributos;
    private RecyclerView rec_lista;
    private ArrayList<CategoriaProyecto> listaCategorias = new ArrayList<>();
    private Spinner spinner_categoria;

    public Fragment_Atributos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Atributos.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Atributos newInstance(String param1, String param2) {
        Fragment_Atributos fragment = new Fragment_Atributos();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        txt_buscardor = view.findViewById(R.id.pro_txt_buscador_atributo);
        btn_agregar = view.findViewById(R.id.btn_agregar);
        rec_lista = view.findViewById(R.id.rec_lista_atributo);
        rec_lista.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));

        txt_buscardor.addTextChangedListener(new TextWatcher() {
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

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarPopup(view);
            }
        });
        if (VerificarConexion.verificarConexion(this.requireContext())) {
            listarAtributos();
        } else {
            Toast.makeText(requireContext(), "No hay conexión a internet", Toast.LENGTH_LONG).show();
        }


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_atributos, container, false);
    }

    //PopupWindow para registrar un nuevo atributo
    private void mostrarPopup(View view) {

        LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_registrar_atributo, null);
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int heigth = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        popupAtributos = new PopupWindow(popupView, width, heigth, focusable);
        popupAtributos.showAtLocation(view, Gravity.CENTER, 0, 0);
        spinner_categoria = popupView.findViewById(R.id.spinner_atributo_categoria);
        if (VerificarConexion.verificarConexion(requireContext())) {
            cargarCategoria(listaCategorias);
        } else {
            Toast.makeText(requireContext(), "Sin conexión a internet", Toast.LENGTH_LONG).show();
        }
        Button aceptar = popupView.findViewById(R.id.btn_aceptar_pop);
        EditText txt_nombreResponsable = popupView.findViewById(R.id.txt_responsable_pop);
        EditText txt_descripcion = popupView.findViewById(R.id.txt_descripcion_pop);
        EditText txt_puntuacion = popupView.findViewById(R.id.txt_puntuacion_popup);


        spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indice = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VerificarConexion.verificarConexion(requireContext())) {
                    RequestQueue solicitud = VolleySingleton.getInstance(requireContext()).getRequestQueue();
                    StringRequest request = new StringRequest(Request.Method.POST, API.agregarAtributo, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject respuesta = new JSONObject(response);
                                if (!respuesta.getBoolean("error")) {
                                    String mensaje = respuesta.getString("message");
                                    Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show();
                                    listarAtributos();
                                } else {
                                    Toast.makeText(requireContext(), "Guardado fallido", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                Toast.makeText(requireContext(), "Error al guardar " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            String responsable = txt_nombreResponsable.getText().toString();
                            String descipcion = txt_descripcion.getText().toString();
                            String puntuacion = txt_puntuacion.getText().toString();
                            CategoriaProyecto cp = listaCategorias.get(indice);
                            String categoria = String.valueOf(cp.getId());
                            HashMap<String, String> params = new HashMap<>();
                            params.put("responsable", responsable);
                            params.put("descripcion", descipcion);
                            params.put("puntuacion", puntuacion);
                            params.put("categoria", categoria);
                            return params;
                        }
                    };
                    solicitud.add(request);
                } else {
                    Toast.makeText(requireContext(), "Sin conexión a internet", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button cancelar = popupView.findViewById(R.id.btn_cancelar_pop);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupAtributos.dismiss();
            }
        });

    }

    // Método para filtrar la lista de evaluadores
    public void filter(String parametro) {
        ArrayList<Atributo> listFilter = new ArrayList<>();
        for (Atributo x : lista_atriibutos) {
            if (x.getResponsable().toLowerCase().contains(parametro.toLowerCase())) {
                listFilter.add(x);
            }
        }
        // Actualizar el adaptador con la lista filtrada
        if (adapterAtributos != null) {
            adapterAtributos.filter(listFilter);
        }
    }

    //Metodo para cargar las categorias
    private void cargarCategoria(ArrayList<CategoriaProyecto> categoriaProyectos) {
        RequestQueue solicitud = VolleySingleton.getInstance(requireContext()).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.POST, API.listarCategoria, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        JSONArray contenidoArray = respuesta.getJSONArray("data");
                        if (contenidoArray.length() > 0) {
                            for (int i = 0; i < contenidoArray.length(); i++) {
                                JSONObject categoriaJson = contenidoArray.getJSONObject(i);
                                CategoriaProyecto categoriaProyecto = new CategoriaProyecto(
                                        categoriaJson.getInt("id"),
                                        categoriaJson.getString("descripcion")
                                );
                                categoriaProyectos.add(categoriaProyecto);
                            }
                        }

                        ArrayAdapter adapter = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listaCategorias);
                        spinner_categoria.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    Toast.makeText(requireContext(), "Error al cargar spinner de categoria", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Error al cargar spinner de categoria", Toast.LENGTH_LONG).show();
            }
        });
        solicitud.add(request);
    }

    private void listarAtributos() {
        lista_atriibutos = new ArrayList<>();
        RequestQueue solicitud = VolleySingleton.getInstance(requireContext()).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.POST, API.listarAtributo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("respuesta", response);
                try {
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        JSONArray contenidoArray = respuesta.getJSONArray("data");
                        if (contenidoArray.length() > 0) {
                            for (int i = 0; i < contenidoArray.length(); i++) {
                                JSONObject atributoJson = contenidoArray.getJSONObject(i);
                                Atributo atributo = new Atributo(
                                        atributoJson.getInt("id"),
                                        atributoJson.getString("responsable"),
                                        atributoJson.getString("descripcion"),
                                        atributoJson.getInt("puntuacion"),
                                        new CategoriaProyecto(atributoJson.getInt("idcategoria"),
                                                atributoJson.getString("categoria"))
                                );
                                lista_atriibutos.add(atributo);
                            }
                            adapterAtributos = new AdapterAtributos(lista_atriibutos);
                            rec_lista.setAdapter(adapterAtributos);
                        }
                    }

                } catch (JSONException e) {
                    Toast.makeText(requireContext(), "Error al cargar información", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Error al cargar información", Toast.LENGTH_LONG).show();
            }
        });
        solicitud.add(request);
    }

}