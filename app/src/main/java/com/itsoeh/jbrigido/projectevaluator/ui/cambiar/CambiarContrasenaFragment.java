package com.itsoeh.jbrigido.projectevaluator.ui.cambiar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;
import com.itsoeh.jbrigido.projectevaluator.ui.helpers.JavaMail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CambiarContrasenaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CambiarContrasenaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText txt_contrasena, txt_confirmar;
    private Button btn_guardar;
    ;

    public CambiarContrasenaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cambiarContrasenaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CambiarContrasenaFragment newInstance(String param1, String param2) {
        CambiarContrasenaFragment fragment = new CambiarContrasenaFragment();
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

        txt_contrasena = view.findViewById(R.id.txt_nueva_contrasena);
        txt_confirmar = view.findViewById(R.id.txt_confirmar_contrasena);
        btn_guardar = view.findViewById(R.id.btn_guardar);
        Bundle datos = this.getActivity().getIntent().getExtras();
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String correo = "";
                if (datos != null) {
                    correo = datos.getString("correo");
                }
                String contrasena = txt_contrasena.getText().toString().trim();
                String confirmar = txt_confirmar.getText().toString().trim();
                if (contrasena.equals(confirmar)) {
                    RequestQueue solicitud = VolleySingleton.getInstance(btn_guardar.getContext()).getRequestQueue();
                    String finalCorreo = correo;
                    StringRequest request = new StringRequest(Request.Method.POST, API.recuperarContrasena, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject respuesta = new JSONObject(response);
                                if (!respuesta.getBoolean("error")) {
                                    Toast.makeText(btn_guardar.getContext(), "Actualizaciòn de contraseña exitoso.", Toast.LENGTH_LONG).show();
                                    JavaMail.sendEmail(finalCorreo, "Cambio de contraseña", "El cambio de contraseña fue exitoso");
                                } else {
                                    Toast.makeText(btn_guardar.getContext(), "Actualizaciòn de contraseña fallido.", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(btn_guardar.getContext(), "Error al obtener respuesta.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(btn_guardar.getContext(), "Error al obtener respuesta." + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("correo", finalCorreo);
                            params.put("contrasena", contrasena);
                            return params;
                        }
                    };
                    solicitud.add(request);
                } else {
                    Toast.makeText(getContext(), "Las contraseñas no coinciden. Intente nuevamente.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cambiar_contrasena, container, false);
    }
}
