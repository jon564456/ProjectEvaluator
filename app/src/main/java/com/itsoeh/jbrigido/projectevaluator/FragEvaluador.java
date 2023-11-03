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

import com.itsoeh.jbrigido.projectevaluator.adapters.AdapterEvaluador;
import com.itsoeh.jbrigido.projectevaluator.config.DBEvaluador;
import com.itsoeh.jbrigido.projectevaluator.modelo.Evaluador;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragEvaluador#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragEvaluador extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private NavController nav;
    private ArrayList<Evaluador> evaluadores;
    private EditText buscador;
    private RecyclerView rec_lista;
    private AdapterEvaluador x;

    public FragEvaluador() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragEvaluador.
     */
    // TODO: Rename and change types and number of parameters
    public static FragEvaluador newInstance(String param1, String param2) {
        FragEvaluador fragment = new FragEvaluador();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_evaluador, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nav = Navigation.findNavController(view);
        buscador = view.findViewById(R.id.eva_text_buscador);
        rec_lista = view.findViewById(R.id.eva_rec);
        rec_lista.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
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
        evaluadores = new DBEvaluador(this.getContext()).all();
        x = new AdapterEvaluador(evaluadores);
        rec_lista.setAdapter(x);
    }

    public void filter(String parametro) {
        ArrayList<Evaluador> listFilter = new ArrayList<>();
        for (Evaluador x : evaluadores) {
            if (x.getNombre().toLowerCase().contains(parametro.toLowerCase())) {
                listFilter.add(x);
            }
        }
        x.filter(listFilter);
    }
}