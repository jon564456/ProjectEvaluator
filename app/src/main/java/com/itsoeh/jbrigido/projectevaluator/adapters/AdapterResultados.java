package com.itsoeh.jbrigido.projectevaluator.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.modelo.Equipo;
import com.itsoeh.jbrigido.projectevaluator.modelo.Proyecto;
import com.itsoeh.jbrigido.projectevaluator.ui.helpers.ColorUtils;

import java.util.ArrayList;

public class AdapterResultados extends RecyclerView.Adapter<AdapterResultados.ViewHolderResultado> {

    private ArrayList<Equipo> proyectos;

    public AdapterResultados(ArrayList<Equipo> proyectos) {
        this.proyectos = proyectos;
    }

    @NonNull
    @Override
    public AdapterResultados.ViewHolderResultado onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resultado, parent, false);
        return new AdapterResultados.ViewHolderResultado(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterResultados.ViewHolderResultado holder, int position) {
        holder.setdata(proyectos.get(position));
    }

    @Override
    public int getItemCount() {
        return proyectos.size();
    }

    public class ViewHolderResultado extends RecyclerView.ViewHolder {
        private TextView txt_nombre, txt_res, txt_cali;
        private LinearLayout backgroundIdentificador;

        public ViewHolderResultado(@NonNull View itemView) {
            super(itemView);
            txt_nombre = itemView.findViewById(R.id.txt_item_nombre_proyecto);
            txt_res = itemView.findViewById(R.id.txt_item_lider);
            txt_cali = itemView.findViewById(R.id.txt_item_resultado);
            backgroundIdentificador = itemView.findViewById(R.id.card_color_resultado_semestre);
        }

        public void setdata(Equipo proyecto) {
            if (proyecto != null) {
                txt_nombre.setText(proyecto.getProyecto().getNombre());
                txt_res.setText(proyecto.getIntegrantes().get(0).getNombre());
                txt_cali.setText(String.valueOf(proyecto.getProyecto().getCalificacion()));
                ColorUtils.changeColor(backgroundIdentificador,proyecto.getProyecto().getGrado());
            }
        }
    }

    public void filter(ArrayList<Equipo> listFilter) {
        this.proyectos = listFilter;
        notifyDataSetChanged();
    }
}
