package com.itsoeh.jbrigido.projectevaluator;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.jbrigido.projectevaluator.adapters.AdapterIntegrantes;
import com.itsoeh.jbrigido.projectevaluator.config.DBEquipos;
import com.itsoeh.jbrigido.projectevaluator.modelo.Integrante;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag_view_info_proyecto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_view_info_proyecto extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView txt_clave, txt_responsable, txt_titulo, txt_categoria, txt_grado;

    private RecyclerView rec_listIntegrantes;
    private ArrayList<Integrante> listaIntegrantes;


    private AdapterIntegrantes x;

    public frag_view_info_proyecto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_view_info_proyecto.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_view_info_proyecto newInstance(String param1, String param2) {
        frag_view_info_proyecto fragment = new frag_view_info_proyecto();
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

        return inflater.inflate(R.layout.fragment_frag_view_info_proyecto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txt_clave = view.findViewById(R.id.info_pro_clave);
        txt_titulo = view.findViewById(R.id.info_pro_titulo);
        txt_responsable = view.findViewById(R.id.info_pro_respon);
        txt_categoria = view.findViewById(R.id.info_pro_cat);
        txt_grado = view.findViewById(R.id.info_pro_grado);
        rec_listIntegrantes = view.findViewById(R.id.rec_pro_integrantes);
        Bundle datos = this.getArguments();
        if (datos != null) {
            txt_clave.setText(datos.getString("clave"));
            txt_titulo.setText(datos.getString("nombre"));
            txt_responsable.setText(datos.getString("responsable"));
            txt_categoria.setText(datos.getString("cat"));
            txt_grado.setText(datos.getString("grado"));
            listaIntegrantes = new DBEquipos(this.getContext()).listMembers(Integer.parseInt(datos.getString("id")));
        }
        rec_listIntegrantes.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        x = new AdapterIntegrantes(listaIntegrantes);
        rec_listIntegrantes.setAdapter(x);
    }
}