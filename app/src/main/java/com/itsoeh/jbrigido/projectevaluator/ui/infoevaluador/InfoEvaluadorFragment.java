package com.itsoeh.jbrigido.projectevaluator.ui.infoevaluador;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.itsoeh.jbrigido.projectevaluator.R;
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
 * Use the {@link InfoEvaluadorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoEvaluadorFragment extends Fragment {


    private TextView txt_nombre, txt_correo, txt_grado, txt_procedencia;
    private NavController nav;
    private Spinner sp1;
    private Button btn_guardar;

    private Evaluador selecionado;

    private int indiceSpinner1 = -1;
    ArrayList<Proyecto> opciones = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public InfoEvaluadorFragment() {
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
    public static InfoEvaluadorFragment newInstance(String param1, String param2) {
        InfoEvaluadorFragment fragment = new InfoEvaluadorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        txt_nombre = view.findViewById(R.id.txt_info_eva_nombre);
        txt_correo = view.findViewById(R.id.txt_info_eva_correo);
        txt_grado = view.findViewById(R.id.txt_info_eva_grado);
        txt_procedencia = view.findViewById(R.id.txt_info_eva_procedencia);
        btn_guardar = view.findViewById(R.id.btn_info_evaluador_guardar);
        sp1 = view.findViewById(R.id.info_eva_pro_asig1);

        // Recuperar datos del evaluador de los argumentos
        Bundle datos = this.getArguments();
        if (datos != null) {
            selecionado = new Evaluador();
            selecionado.setId(datos.getInt("id"));
            selecionado.setNombre(datos.getString("nombre"));
            selecionado.setAppa(datos.getString("apepa"));
            selecionado.setApma(datos.getString("apema"));
            selecionado.setCorreo(datos.getString("correo"));
            selecionado.setGrado(datos.getString("grado"));
            selecionado.setProcedencia(datos.getString("procedencia"));
            //selecionado.setProyectos(new DBEvaluador(this.getContext()).Asignados(selecionado.getId()));
            txt_nombre.setText(new StringBuilder().
                    append(selecionado.getNombre())
                    .append(" ")
                    .append(selecionado.getAppa())
                    .append(" ")
                    .append(selecionado.getApma()));
            txt_correo.setText(selecionado.getCorreo());
            txt_grado.setText(selecionado.getGrado());
            txt_procedencia.setText(selecionado.getProcedencia());
        }

        // Poblar proyectos disponibles en los spinners
        listarDisponibles();
    }

    private void listarDisponibles() {
        ArrayList<Proyecto> list = new ArrayList<>();
        RequestQueue solicitud = VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.GET, API.listarDisponibles, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        JSONArray contenidoArray = respuesta.getJSONArray("data");
                        for (int i = 0; i < contenidoArray.length(); i++) {
                            Proyecto x = new Proyecto();
                            JSONObject atributos = contenidoArray.getJSONObject(i);
                            x.setId(atributos.getInt("id"));
                            x.setNombre(atributos.getString("nombre"));
                            x.setClave(atributos.getString("clave"));
                            list.add(x);
                        }
                        opciones = list;
                        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones);
                        sp1.setAdapter(arrayAdapter);
                    } else {
                        Toast.makeText(InfoEvaluadorFragment.this.getContext(), "Ocurrió un error", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(InfoEvaluadorFragment.this.getContext(), e.getMessage() + "", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InfoEvaluadorFragment.this.getContext(), "Hubo un error" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        solicitud.add(request);
    }
}
