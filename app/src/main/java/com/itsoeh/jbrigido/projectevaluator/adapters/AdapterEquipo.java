package com.itsoeh.jbrigido.projectevaluator.adapters;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.modelo.Proyecto;
import com.itsoeh.jbrigido.projectevaluator.ui.helpers.ColorUtils;

import java.util.ArrayList;

public class AdapterEquipo extends RecyclerView.Adapter<AdapterEquipo.ViewHolderEquipo> {

    private ArrayList<Proyecto> listaEquipos;

    public AdapterEquipo(ArrayList<Proyecto> x) {
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
        holder.cardView.setOnClickListener(new View.OnClickListener() {
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

    public void filter(ArrayList<Proyecto> listFilter) {
        this.listaEquipos = listFilter;
        notifyDataSetChanged();

    }

    private void show(View v, int position) {
        NavController nav = Navigation.findNavController(v);
        Bundle datos = new Bundle();
        Proyecto p = listaEquipos.get(position);
        datos.putString("clave", p.getClave());
        datos.putString("nombre", p.getNombre());
        nav.navigate(R.id.frag_view_info_proyecto, datos);
    }

    public class ViewHolderEquipo extends RecyclerView.ViewHolder {

        private TextView txt_proyecto, txt_clave, txt_cat, txt_gradogrupo;
        private CardView cardView;
        private LinearLayout backgroundIdentificador;

        public ViewHolderEquipo(@NonNull View itemView) {
            super(itemView);
            txt_proyecto = itemView.findViewById(R.id.txt_item_titulo);
            txt_clave = itemView.findViewById(R.id.txt_item_clave);
            cardView = itemView.findViewById(R.id.card_proyecto);
            backgroundIdentificador = itemView.findViewById(R.id.card_color_semestre);
        }

        public void setdata(Proyecto proyecto) {
            if (proyecto != null) {
                txt_proyecto.setText(proyecto.getNombre());
                txt_clave.setText(proyecto.getClave());
                ColorUtils.changeColor(backgroundIdentificador, proyecto.getGrado());
            }
        }
    }
}
