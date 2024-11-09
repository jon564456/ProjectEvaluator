package com.itsoeh.jbrigido.projectevaluator.adapters;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.itsoeh.jbrigido.projectevaluator.modelo.CategoriaProyecto;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.ViewHolderCategoria> {
    private ArrayList<CategoriaProyecto> categoriaProyectos;
    private PopupWindow popupWindow;

    public AdapterCategoria(ArrayList<CategoriaProyecto> categoriaProyectos) {
        this.categoriaProyectos = categoriaProyectos;
    }

    @NonNull
    @Override
    public ViewHolderCategoria onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new ViewHolderCategoria(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategoria holder, int position) {
        CategoriaProyecto seleccionada = categoriaProyectos.get(position);
        holder.setData(seleccionada);

    }

    @Override
    public int getItemCount() {
        return categoriaProyectos.size();
    }

    public void filter(ArrayList<CategoriaProyecto> listFilter) {
        this.categoriaProyectos = listFilter;
        notifyDataSetChanged();
    }

    public class ViewHolderCategoria extends RecyclerView.ViewHolder {
        TextView descripcion;
        ImageView btn_opciones;
        CategoriaProyecto categoriaProyecto;

        public ViewHolderCategoria(@NonNull View itemView) {
            super(itemView);
            descripcion = itemView.findViewById(R.id.txt_item_categoria);
            btn_opciones = itemView.findViewById(R.id.btn_opciones_categoria);
            btn_opciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mostrarPopup(view);
                }
            });
        }

        public void setData(CategoriaProyecto categoria) {
            if (categoria != null) {
                descripcion.setText(categoria.getDescripcion());
                categoriaProyecto = categoria;
            }
        }

        private void mostrarPopup(View view) {
            LayoutInflater inflater = (LayoutInflater) descripcion.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popView = inflater.inflate(R.layout.popup_registrar_categoria, null);

            popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            EditText txt_descripcion_pop = popView.findViewById(R.id.txt_categoria_pop);
            Button btn_eliminar = popView.findViewById(R.id.btn_categoria_eliminar_pop);
            btn_eliminar.setVisibility(View.VISIBLE);
            Button btn_aceptar = popView.findViewById(R.id.btn_categoria_aceptar_pop);
            Button btn_cancelar = popView.findViewById(R.id.btn_categoria_cancelar_pop);

            txt_descripcion_pop.setText(categoriaProyecto.getDescripcion());

            btn_eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = API.eliminarCategoria + categoriaProyecto.getId();
                    RequestQueue solicitud = VolleySingleton.getInstance(btn_aceptar.getContext()).getRequestQueue();
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("respuesta", response);
                            try {
                                JSONObject respuesta = new JSONObject(response);
                                if (!respuesta.getBoolean("error")) {
                                    String mensaje = respuesta.getString("message");
                                    Toast.makeText(btn_aceptar.getContext(), mensaje, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(btn_aceptar.getContext(), "Esta categoria ya está asignada, no se puede eliminar", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                Toast.makeText(btn_aceptar.getContext(), "Error al eliminar esta categoria ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(btn_aceptar.getContext(), "Error al guardar", Toast.LENGTH_LONG).show();
                        }
                    });
                    solicitud.add(request);
                }
            });

            btn_aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RequestQueue solicitud = VolleySingleton.getInstance(btn_aceptar.getContext()).getRequestQueue();
                    StringRequest request = new StringRequest(Request.Method.POST, API.actualizarCategoria, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject respuesta = new JSONObject(response);
                                if (!respuesta.getBoolean("error")) {
                                    String mensaje = respuesta.getString("message");
                                    Toast.makeText(btn_aceptar.getContext(), mensaje, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(btn_aceptar.getContext(), "Guardado fallído", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                Toast.makeText(btn_aceptar.getContext(), "Error al guardar ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(btn_aceptar.getContext(), "Error al guardar", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            String id = String.valueOf(categoriaProyecto.getId());
                            String descipcion = txt_descripcion_pop.getText().toString();
                            params.put("id", id);
                            params.put("descripcion", descipcion);
                            return params;
                        }
                    };
                    solicitud.add(request);

                }
            });

            btn_cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });


        }
    }

}
