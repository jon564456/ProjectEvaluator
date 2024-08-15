package com.itsoeh.jbrigido.projectevaluator.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.modelo.Equipo;

import java.util.ArrayList;

public class AdapterEquipo extends RecyclerView.Adapter<AdapterEquipo.ViewHolderEquipo> {

    private ArrayList<Equipo> listaEquipos;

    public AdapterEquipo(ArrayList<Equipo> x) {
        this.listaEquipos = x;
    }

    @NonNull
    @Override
    public ViewHolderEquipo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proyecto, parent, false);
        return new AdapterEquipo.ViewHolderEquipo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEquipo holder, int position) {
        holder.setdata(listaEquipos.get(position));
        holder.ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaEquipos.size();
    }

    public void filter(ArrayList<Equipo> listFilter) {
        this.listaEquipos = listFilter;
        notifyDataSetChanged();
    }

    private void show(View v, int position) {
        NavController nav = Navigation.findNavController(v);
        Bundle datos = new Bundle();
        Equipo e = listaEquipos.get(position);
        datos.putString("id", e.getProyecto().getId() + "");
        datos.putString("clave", e.getProyecto().getClave());
        datos.putString("responsable", e.getIntegrantes().get(0).getNombre() + " " + e.getIntegrantes().get(0).getAppa() + " " + e.getIntegrantes().get(0).getApma());
        datos.putString("nombre", e.getProyecto().getNombre());
        datos.putString("cat", e.getProyecto().getCategoria());
        datos.putString("grado", e.getProyecto().getGrado() + " " + e.getProyecto().getGrupo());
        nav.navigate(R.id.frag_view_info_proyecto, datos);
    }

    public class ViewHolderEquipo extends RecyclerView.ViewHolder {

        private TextView txt_proyecto, txt_clave, txt_cat, txt_gradogrupo;
        private ImageView ver;

        public ViewHolderEquipo(@NonNull View itemView) {
            super(itemView);
           /* txt_proyecto = itemView.findViewById(R.id.proy_text_nombre);
            txt_clave = itemView.findViewById(R.id.proy_text_clave);
            txt_cat = itemView.findViewById(R.id.proy_text_cat);
            txt_gradogrupo = itemView.findViewById(R.id.proy_text_gradogrupo);
            ver = itemView.findViewById(R.id.proy_item_ver);*/
        }

        public void setdata(Equipo equipo) {
            if (equipo != null) {
                txt_proyecto.setText(equipo.getProyecto().getNombre());
                txt_clave.setText(equipo.getProyecto().getClave());
                txt_cat.setText(equipo.getProyecto().getCategoria());
                txt_gradogrupo.setText(equipo.getProyecto().getGrado() + " " + equipo.getProyecto().getGrupo());
            }
        }

    }
}
