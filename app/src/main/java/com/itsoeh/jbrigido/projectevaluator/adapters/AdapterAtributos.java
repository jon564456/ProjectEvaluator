package com.itsoeh.jbrigido.projectevaluator.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import com.itsoeh.jbrigido.projectevaluator.modelo.Atributo;
import com.itsoeh.jbrigido.projectevaluator.modelo.CategoriaProyecto;
import com.itsoeh.jbrigido.projectevaluator.modelo.Proyecto;
import com.itsoeh.jbrigido.projectevaluator.ui.helpers.ColorUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterAtributos extends RecyclerView.Adapter<AdapterAtributos.ViewHolderCategoria> {

    private ArrayList<Atributo> atributos;
    private PopupWindow menuWindow;
    private Spinner spinner_categoria;
    private ArrayList<CategoriaProyecto> listaCategorias;
    private int indice = -1;
    private int indicemenu = 0;

    public AdapterAtributos(ArrayList<Atributo> atributos) {
        this.atributos = atributos;
    }

    @NonNull
    @Override
    public ViewHolderCategoria onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_atributos, parent, false);
        return new AdapterAtributos.ViewHolderCategoria(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategoria holder, int position) {
        Atributo selecionado = atributos.get(position);
        indicemenu = position;
        holder.setdata(selecionado);
    }

    @Override
    public int getItemCount() {
        return atributos.size();
    }

    public void filter(ArrayList<Atributo> listFilter) {
        this.atributos = listFilter;
        notifyDataSetChanged();
    }

    public class ViewHolderCategoria extends RecyclerView.ViewHolder {

        private TextView txt_descrippcion, txt_categoria;
        private ImageView btn_editar;

        public ViewHolderCategoria(@NonNull View itemView) {
            super(itemView);

            txt_descrippcion = itemView.findViewById(R.id.txt_item_atributo);
            txt_categoria = itemView.findViewById(R.id.txt_item_atributo_categoria);
            btn_editar = itemView.findViewById(R.id.btn_eliminar);


            btn_editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mostrarPopUp(view);
                }
            });

        }

        private void mostrarPopUp(View view) {

            LayoutInflater inflater = (LayoutInflater) btn_editar.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popUp = inflater.inflate(R.layout.popup_registrar_atributo, null);
            menuWindow = new PopupWindow(popUp, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            menuWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            Button btn_aceptar = popUp.findViewById(R.id.btn_aceptar_pop);
            Button btn_cancelar = popUp.findViewById(R.id.btn_cancelar_pop);
            Button btn_eliminar = popUp.findViewById(R.id.btn_eliminar_pop);
            EditText txt_nombreResponsable = popUp.findViewById(R.id.txt_responsable_pop);
            EditText txt_descripcion = popUp.findViewById(R.id.txt_descripcion_pop);
            EditText txt_puntuacion = popUp.findViewById(R.id.txt_puntuacion_popup);
            spinner_categoria = popUp.findViewById(R.id.spinner_atributo_categoria);
            btn_eliminar.setVisibility(View.VISIBLE);
            cargarCategoria();
            txt_nombreResponsable.setText(atributos.get(indicemenu).getResponsable());
            txt_descripcion.setText(atributos.get(indicemenu).getDescripcion());
            txt_puntuacion.setText(atributos.get(indicemenu).getPuntuacion() + "");

            spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    indice = i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btn_cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuWindow.dismiss();
                }
            });

            btn_eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = API.eliminarAtributo + atributos.get(indicemenu).getId();
                    RequestQueue solicitud = VolleySingleton.getInstance(btn_aceptar.getContext()).getRequestQueue();
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject respuesta = new JSONObject(response);
                                if (!respuesta.getBoolean("error")) {
                                    String mensaje = respuesta.getString("message");
                                    Toast.makeText(btn_aceptar.getContext(), mensaje, Toast.LENGTH_LONG).show();
                                    notifyItemRemoved(indice);
                                } else {
                                    Toast.makeText(btn_aceptar.getContext(), "Guardado fallido", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                Toast.makeText(btn_aceptar.getContext(), "Error al guardar " + e.getMessage(), Toast.LENGTH_LONG).show();
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

                    AlertDialog.Builder alert = new AlertDialog.Builder(btn_eliminar.getContext());
                    CategoriaProyecto seleccionado = (CategoriaProyecto) spinner_categoria.getSelectedItem();
                    alert.setTitle("Confirmar cambios");
                    alert.setMessage("Responsable: " + txt_nombreResponsable.getText().toString() + "\n" +
                            "Descripcion: " + txt_descripcion.getText().toString() + "\n" +
                            "Puntuación: " + txt_puntuacion.getText().toString() + "\n" +
                            "Categoria: " + seleccionado.getDescripcion());
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            RequestQueue solicitud = VolleySingleton.getInstance(btn_aceptar.getContext()).getRequestQueue();
                            StringRequest request = new StringRequest(Request.Method.POST, API.actualizarAtributo, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("respuesta", response);
                                    try {
                                        JSONObject respuesta = new JSONObject(response);
                                        if (!respuesta.getBoolean("error")) {
                                            String mensaje = respuesta.getString("message");
                                            Toast.makeText(btn_aceptar.getContext(), mensaje, Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(btn_aceptar.getContext(), "Actualización fallida", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (JSONException e) {
                                        Toast.makeText(btn_aceptar.getContext(), "Error al guardar " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                                    String responsable = txt_nombreResponsable.getText().toString();
                                    String descipcion = txt_descripcion.getText().toString();
                                    String puntuacion = txt_puntuacion.getText().toString();
                                    CategoriaProyecto cp = listaCategorias.get(indice);
                                    String categoria = String.valueOf(cp.getId());
                                    HashMap<String, String> params = new HashMap<>();
                                    params.put("id", String.valueOf(atributos.get(indicemenu).getId()));
                                    params.put("responsable", responsable);
                                    params.put("descripcion", descipcion);
                                    params.put("puntuacion", puntuacion);
                                    params.put("categoria", categoria);
                                    return params;
                                }
                            };
                            solicitud.add(request);
                        }
                    });

                    alert.show();
                }
            });


        }

        //Metodo para cargar las categorias
        private void cargarCategoria() {

            listaCategorias = new ArrayList<>();
            RequestQueue solicitud = VolleySingleton.getInstance(btn_editar.getContext()).getRequestQueue();
            StringRequest request = new StringRequest(Request.Method.POST, API.listarCategoria, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject respuesta = new JSONObject(response);
                        if (!respuesta.getBoolean("error")) {
                            JSONArray contenidoArray = respuesta.getJSONArray("data");
                            if (contenidoArray.length() > 0) {
                                for (int i = 0; i < contenidoArray.length(); i++) {
                                    JSONObject categoriaJson = contenidoArray.getJSONObject(i);
                                    CategoriaProyecto categoriaProyecto = new CategoriaProyecto(
                                            categoriaJson.getInt("id"),
                                            categoriaJson.getString("descripcion")
                                    );
                                    listaCategorias.add(categoriaProyecto);
                                }
                            }

                            ArrayAdapter adapter = new ArrayAdapter(btn_editar.getContext(), android.R.layout.simple_spinner_dropdown_item, listaCategorias);
                            spinner_categoria.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(btn_editar.getContext(), "Error al cargar spinner de categoria", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(btn_editar.getContext(), "Error al cargar spinner de categoria", Toast.LENGTH_LONG).show();
                }
            });
            solicitud.add(request);
        }

        public void setdata(Atributo atributo) {
            if (atributo != null) {
                txt_descrippcion.setText(atributo.getDescripcion());
                txt_categoria.setText(atributo.getCategoriaProyecto().getDescripcion());
            }
        }
    }

}
