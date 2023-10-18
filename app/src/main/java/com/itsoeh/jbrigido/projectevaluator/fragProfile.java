package com.itsoeh.jbrigido.projectevaluator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragProfile extends Fragment {
    private TextView tv_nombre;
    private EditText nombre, appa, apma, email, contra;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static fragProfile newInstance(String param1, String param2) {
        fragProfile fragment = new fragProfile();
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
        return inflater.inflate(R.layout.fragment_frag_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_nombre = view.findViewById(R.id.pro_text_nombre_com);
        nombre = view.findViewById(R.id.pro_text_nombre);
        appa = view.findViewById(R.id.pro_text_appa);
        apma = (EditText) view.findViewById(R.id.pro_text_apma);
        email = view.findViewById(R.id.pro_text_email);
        contra = view.findViewById(R.id.pro_text_contra);
        tv_nombre.setText(LoginActivity.usuario.getNombre() + " " + LoginActivity.usuario.getAppa() + " " + LoginActivity.usuario.getApma());
        nombre.setText(LoginActivity.usuario.getNombre());
        appa.setText(LoginActivity.usuario.getAppa());
        apma.setText(LoginActivity.usuario.getApma());
        email.setText(LoginActivity.usuario.getCorreo());
        contra.setText(LoginActivity.usuario.getContrasena());
    }
}