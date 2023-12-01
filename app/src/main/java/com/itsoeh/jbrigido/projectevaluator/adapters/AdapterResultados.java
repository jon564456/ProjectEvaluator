package com.itsoeh.jbrigido.projectevaluator.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.config.DBProyecto;
import com.itsoeh.jbrigido.projectevaluator.modelo.Equipo;
import com.itsoeh.jbrigido.projectevaluator.modelo.Proyecto;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class AdapterResultados extends RecyclerView.Adapter<AdapterResultados.ViewHolderResultado> {

    private ArrayList<Equipo> equipos;

    public AdapterResultados(ArrayList<Equipo> equipos) {
        this.equipos = equipos;
    }

    @NonNull
    @Override
    public AdapterResultados.ViewHolderResultado onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resultado, parent, false);
        return new AdapterResultados.ViewHolderResultado(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterResultados.ViewHolderResultado holder, int position) {
        holder.setdata(equipos.get(position));
    }

    @Override
    public int getItemCount() {
        return equipos.size();
    }

    public class ViewHolderResultado extends RecyclerView.ViewHolder {
        private TextView textnombre, textres, textcat, textCali;

        public ViewHolderResultado(@NonNull View itemView) {
            super(itemView);
            textnombre = itemView.findViewById(R.id.res_nombre);
            textres = itemView.findViewById(R.id.res_nombre_respon);
            textcat = itemView.findViewById(R.id.res_categoria);
            textCali = itemView.findViewById(R.id.res_cali);
        }

        public void setdata(Equipo equipo) {
            if (equipo != null) {
                textnombre.setText(equipo.getProyecto().getNombre());
                textres.setText(equipo.getIntegrantes().get(0).getCorreo());
                textcat.setText(equipo.getProyecto().getCategoria());
                textCali.setText(equipo.getProyecto().getCalificacion() + "");
            }
        }

    }
    public void filter(ArrayList<Equipo> listFilter) {
        this.equipos = listFilter;
        notifyDataSetChanged();
    }

}
