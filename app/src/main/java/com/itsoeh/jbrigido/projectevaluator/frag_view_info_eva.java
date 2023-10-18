package com.itsoeh.jbrigido.projectevaluator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag_view_info_eva#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_view_info_eva extends Fragment {


    private TextView txt_nombre, txt_correo, txt_grado, txt_procedencia;
    private NavController nav;
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

        Bundle datos = this.getArguments();
        if (datos != null) {
            txt_nombre.setText(datos.getString("name") + " " + datos.get("app") + " " + datos.get("apm"));
            txt_correo.setText(datos.getString("mail"));
            txt_grado.setText(datos.getString("grad"));
            txt_procedencia.setText(datos.getString("pro"));
        }

    }
}