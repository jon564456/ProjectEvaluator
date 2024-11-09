package com.itsoeh.jbrigido.projectevaluator.ui.opciones;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.ui.helpers.VerificarConexion;
import com.itsoeh.jbrigido.projectevaluator.ui.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_opciones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_opciones extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CardView btn_acerca, btn_cerrar, btnListar;

    public Fragment_opciones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_opciones.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_opciones newInstance(String param1, String param2) {
        Fragment_opciones fragment = new Fragment_opciones();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_acerca = view.findViewById(R.id.card_acerca_del_sistema);
        btn_cerrar = view.findViewById(R.id.card_cerrar);
        btnListar = view.findViewById(R.id.card_resultados);
        NavController nav = Navigation.findNavController(view);
        btn_acerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav.navigate(R.id.infoFragment);
            }
        });

        btn_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(btn_acerca.getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              nav.navigate(R.id.resultadosFragment);
            }
        });

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
        return inflater.inflate(R.layout.fragment_opciones, container, false);
    }
}