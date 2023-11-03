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

import com.itsoeh.jbrigido.projectevaluator.adapters.AdapterResultados;
import com.itsoeh.jbrigido.projectevaluator.config.DBEquipos;
import com.itsoeh.jbrigido.projectevaluator.modelo.Equipo;

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
        textSearch = view.findViewById(R.id.proy_text_buscador);
        equipos = new DBEquipos(this.getContext()).listQualification();
        x = new AdapterResultados(equipos);
        reclis.setAdapter(x);
    }
}