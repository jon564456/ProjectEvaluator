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

import com.itsoeh.jbrigido.projectevaluator.config.DBEvaluador;
import com.itsoeh.jbrigido.projectevaluator.config.DBProyecto;
import com.itsoeh.jbrigido.projectevaluator.modelo.Evaluador;
import com.itsoeh.jbrigido.projectevaluator.modelo.Proyecto;

import java.util.ArrayList;

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
        Bundle datos = this.getArguments();
        if (datos != null) {
            selecionado = new Evaluador();
            selecionado.setId(Integer.parseInt(datos.getString("id")));
            selecionado.setNombre(datos.getString("name"));
            selecionado.setAppa(datos.getString("app"));
            selecionado.setApma(datos.getString("apm"));
            selecionado.setCorreo(datos.getString("mail"));
            selecionado.setGrado(datos.getString("grad"));
            selecionado.setProcedencia(datos.getString("pro"));
            selecionado.setProyectos(new DBEvaluador(this.getContext()).Asignados(selecionado.getId()));
        }
        ArrayList<Proyecto> opciones = new DBProyecto(this.getContext()).available();
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_expandable_list_item_1, opciones);
        sp1.setAdapter(arrayAdapter);
        sp2.setAdapter(arrayAdapter);
        sp3.setAdapter(arrayAdapter);

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
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Proyecto seleccionado = (Proyecto) parent.getItemAtPosition(position);
                indiceSpinner1 = seleccionado.getId();
                Log.e("Valor", indiceSpinner1 + "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

            }
        });
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Proyecto seleccionado = (Proyecto) parent.getItemAtPosition(position);
                indiceSpinner3 = seleccionado.getId();
                Log.e("Valor", indiceSpinner3 + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void asignarproyecto1() {
        DBEvaluador dbEvaluador = new DBEvaluador(this.getContext());
        try {
            dbEvaluador.asignarProyecto(indiceSpinner1, selecionado.getId());
            Log.e("Valor", indiceSpinner1 + "");
            Toast.makeText(this.getContext(), "Proyecto asignado correctamente", Toast.LENGTH_SHORT).show();
            sp2.setVisibility(View.VISIBLE);
            guardar2.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "No se pudo asignar el proyecto", Toast.LENGTH_SHORT).show();
        }

    }

    private void asignarproyecto2() {
        DBEvaluador dbEvaluador = new DBEvaluador(this.getContext());
        try {
            if (indiceSpinner1 == indiceSpinner2) {
                Toast.makeText(this.getContext(), "Asigna otro proyecto", Toast.LENGTH_SHORT).show();
                return;
            }
            dbEvaluador.asignarProyecto(indiceSpinner2, selecionado.getId());
            Log.e("Valor", indiceSpinner2 + "");
            Toast.makeText(this.getContext(), "Proyecto asignado correctamente", Toast.LENGTH_SHORT).show();
            sp3.setVisibility(View.VISIBLE);
            guardar3.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "No se pudo asignar el proyecto", Toast.LENGTH_SHORT).show();
        }
    }

    private void asignarproyecto3() {
        DBEvaluador dbEvaluador = new DBEvaluador(this.getContext());
        try {
            if (indiceSpinner1 == indiceSpinner3 || indiceSpinner3 == indiceSpinner2) {
                Toast.makeText(this.getContext(), "Asigna otro proyecto", Toast.LENGTH_SHORT).show();
                return;
            }
            dbEvaluador.asignarProyecto(indiceSpinner3, selecionado.getId());
            Toast.makeText(this.getContext(), "Proyecto asignado correctamente", Toast.LENGTH_SHORT).show();
            sp3.setVisibility(View.VISIBLE);
            guardar3.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "No se pudo asignar el proyecto", Toast.LENGTH_SHORT).show();
        }
    }
}