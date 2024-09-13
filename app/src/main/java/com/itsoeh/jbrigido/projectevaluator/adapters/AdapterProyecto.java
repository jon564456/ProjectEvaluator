package com.itsoeh.jbrigido.projectevaluator.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;
import com.itsoeh.jbrigido.projectevaluator.modelo.Proyecto;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AdapterProyecto extends RecyclerView.Adapter<AdapterProyecto.ViewHolderProyecto> {
    private ArrayList<Proyecto> proyectos;
    private int evaluador;

    public AdapterProyecto(ArrayList<Proyecto> proyectos, int evaluador) {
        this.proyectos = proyectos;
        this.evaluador = evaluador;
    }

    @NonNull
    @Override
    public AdapterProyecto.ViewHolderProyecto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proyecto_evaluador, parent, false);
        return new ViewHolderProyecto(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProyecto.ViewHolderProyecto holder, int position) {
        holder.setdata(proyectos.get(position));
        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(holder.eliminar.getContext())
                        .setTitle("Eliminar")
                        .setMessage("¿Desea continuar?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RequestQueue solicitud = VolleySingleton.getInstance(holder.eliminar.getContext()).getRequestQueue();
                                StringRequest request = new StringRequest(Request.Method.POST, API.eliminarAsignacion, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e("response", response);
                                        try {
                                            JSONObject respuesta = new JSONObject(response);
                                            if (!respuesta.getBoolean("error")) {
                                                String mensaje = respuesta.getString("message");
                                                Toast.makeText(holder.eliminar.getContext(), mensaje, Toast.LENGTH_LONG).show();
                                                proyectos.remove(proyectos.get(position));
                                                notifyItemRemoved(position);
                                            }
                                        } catch (JSONException e) {
                                            Toast.makeText(holder.eliminar.getContext(), "Error al eliminar", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(holder.eliminar.getContext(), "Error al eliminar", Toast.LENGTH_LONG).show();
                                    }
                                }) {
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("proyecto", String.valueOf(proyectos.get(position).getId()));
                                        params.put("evaluador", String.valueOf(evaluador));
                                        return params;
                                    }
                                };

                                solicitud.add(request);

                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
                ;
            }
        });
    }

    private void eliminar(Context context, int proyecto) {

    }

    @Override
    public int getItemCount() {
        return proyectos.size();
    }

    public class ViewHolderProyecto extends RecyclerView.ViewHolder {

        private TextView txt_nombre;
        private ImageView eliminar;
        private int evaluador;

        public ViewHolderProyecto(@NonNull View itemView) {
            super(itemView);
            txt_nombre = itemView.findViewById(R.id.txt_proyecto_titulo);
            eliminar = itemView.findViewById(R.id.btn_eliminar);
        }

        public void setdata(Proyecto proyecto) {
            if (proyecto != null) {
                txt_nombre.setText(proyecto.getNombre());
            }
        }

    }

}
