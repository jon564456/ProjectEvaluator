package com.itsoeh.jbrigido.projectevaluator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.jbrigido.projectevaluator.adapters.AdapterProyecto;
import com.itsoeh.jbrigido.projectevaluator.modelo.Proyecto;

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
    private ArrayList<Proyecto> proyectos;
    private AdapterProyecto x;
    private RecyclerView reclista;
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
        reclista = view.findViewById(R.id.proy_reclis);
        reclista.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        proyectos = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            proyectos.add(
                    new Proyecto(i, "Proyecto", "Proyecto" + i, "Integrador", "lorem ipsum nfe", 4, 'A', "Pendiente", 100));
        }
        x = new AdapterProyecto(proyectos);
        reclista.setAdapter(x);
    }
}