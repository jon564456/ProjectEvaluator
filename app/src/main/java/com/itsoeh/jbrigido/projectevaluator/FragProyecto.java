package com.itsoeh.jbrigido.projectevaluator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.jbrigido.projectevaluator.adapters.AdapterEquipo;
import com.itsoeh.jbrigido.projectevaluator.config.DBEquipos;
import com.itsoeh.jbrigido.projectevaluator.modelo.Equipo;

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
    private ArrayList<Equipo> proyectos;
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
        nav = Navigation.findNavController(view);
        buscador = view.findViewById(R.id.proy_text_buscador);
        proyectos = new DBEquipos(this.getContext()).all();
        x = new AdapterEquipo(proyectos);
        reclista = view.findViewById(R.id.proy_reclis);
        reclista.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        reclista.setAdapter(x);

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

    private void filter(String parametro) {
        ArrayList<Equipo> listFilter = new ArrayList<>();
        for (Equipo x : proyectos) {
            if (x.getProyecto().getClave().toLowerCase().contains(parametro.toLowerCase())) {
                listFilter.add(x);
            }
        }
        x.filter(listFilter);
    }
}