package com.itsoeh.jbrigido.projectevaluator.ui.configuracion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.itsoeh.jbrigido.projectevaluator.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfiguracionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfiguracionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView btn_editar_perfil, btn_cambiar_contrasena, btn_informacion;

    public ConfiguracionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_configuracion.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfiguracionFragment newInstance(String param1, String param2) {
        ConfiguracionFragment fragment = new ConfiguracionFragment();
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
        super.onViewCreated(view, savedInstanceState);
        btn_editar_perfil = view.findViewById(R.id.btn_editar);
        btn_cambiar_contrasena = view.findViewById(R.id.btn_cambiar);
        btn_informacion = view.findViewById(R.id.btn_ver);

        NavController navController = Navigation.findNavController(view);

        btn_editar_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_to_editar_perfil);
            }
        });

        btn_cambiar_contrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_to_cambiar_password);
            }
        });

        btn_informacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_to_info);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_frag_configuracion, container, false);
    }
}