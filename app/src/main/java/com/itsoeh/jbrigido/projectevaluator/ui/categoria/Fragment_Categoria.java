package com.itsoeh.jbrigido.projectevaluator.ui.categoria;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.adapters.AdapterAtributos;
import com.itsoeh.jbrigido.projectevaluator.adapters.AdapterCategoria;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;
import com.itsoeh.jbrigido.projectevaluator.modelo.Atributo;
import com.itsoeh.jbrigido.projectevaluator.modelo.CategoriaProyecto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Categoria#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Categoria extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView btn_agregar;
    private EditText txt_buscardor;
    private ArrayList<CategoriaProyecto> lista_categorias;
    private AdapterCategoria adapterCategoria;
    private RecyclerView rec_lista;
    private PopupWindow popupWindow;

    public Fragment_Categoria() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_categoria.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Categoria newInstance(String param1, String param2) {
        Fragment_Categoria fragment = new Fragment_Categoria();
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
        txt_buscardor = view.findViewById(R.id.pro_txt_buscador_categoria);
        btn_agregar = view.findViewById(R.id.btn_agregar_categoria);
        rec_lista = view.findViewById(R.id.rec_lista_categoria);
        rec_lista.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        listarCategorias();
        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarPopup(view);
            }
        });

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

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categoria, container, false);
    }


    public void filter(String parametro) {
        ArrayList<CategoriaProyecto> listFilter = new ArrayList<>();
        for (CategoriaProyecto x : lista_categorias) {
            if (x.getDescripcion().toLowerCase().contains(parametro.toLowerCase())) {
                listFilter.add(x);
            }
        }
        // Actualizar el adaptador con la lista filtrada
        if (adapterCategoria != null) {
            adapterCategoria.filter(listFilter);
        }
    }


    private void mostrarPopup(View view) {
        LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popView = inflater.inflate(R.layout.popup_registrar_categoria, null);

        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        EditText txt_descripcion_pop = popView.findViewById(R.id.txt_categoria_pop);

        Button btn_aceptar = popView.findViewById(R.id.btn_categoria_aceptar_pop);
        Button btn_cancelar = popView.findViewById(R.id.btn_categoria_cancelar_pop);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue solicitud = VolleySingleton.getInstance(requireContext()).getRequestQueue();
                StringRequest request = new StringRequest(Request.Method.POST, API.agregarCategoria, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPUESTA", response);
                        try {
                            JSONObject respuesta = new JSONObject(response);
                            if (!respuesta.getBoolean("error")) {
                                String mensaje = respuesta.getString("message");
                                Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show();
                                listarCategorias();
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
                        HashMap<String, String> params = new HashMap<>();
                        String descipcion = txt_descripcion_pop.getText().toString();
                        params.put("descripcion", descipcion);
                        return params;
                    }
                };
                solicitud.add(request);

            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });


    }

    private void listarCategorias() {
        lista_categorias = new ArrayList<>();
        RequestQueue solicitud = VolleySingleton.getInstance(requireContext()).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.POST, API.listarCategoria, new Response.Listener<String>() {
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
                                CategoriaProyecto cp = new CategoriaProyecto();
                                cp.setId(atributoJson.getInt("id"));
                                cp.setDescripcion(atributoJson.getString("descripcion"));
                                lista_categorias.add(cp);
                            }
                            adapterCategoria = new AdapterCategoria(lista_categorias);
                            rec_lista.setAdapter(adapterCategoria);
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