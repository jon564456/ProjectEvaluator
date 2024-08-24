package com.itsoeh.jbrigido.projectevaluator.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.modelo.Integrante;
import com.itsoeh.jbrigido.projectevaluator.ui.helpers.ColorUtils;

import java.util.ArrayList;

public class AdapterIntegrantes extends RecyclerView.Adapter<AdapterIntegrantes.ViewHolderIntegrante> {
    private ArrayList<Integrante> integrantes;

    public AdapterIntegrantes(ArrayList<Integrante> integrantes) {
        this.integrantes = integrantes;
    }

    @NonNull
    @Override
    public AdapterIntegrantes.ViewHolderIntegrante onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_integrante, parent, false);
        return new ViewHolderIntegrante(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterIntegrantes.ViewHolderIntegrante holder, int position) {
        holder.setData(integrantes.get(position));
    }

    @Override
    public int getItemCount() {
        return integrantes.size();
    }

    public class ViewHolderIntegrante extends RecyclerView.ViewHolder {
        private TextView txt_nombre, txt_matricula;
        public ViewHolderIntegrante(@NonNull View itemView) {
            super(itemView);
            txt_nombre = itemView.findViewById(R.id.txt_item_nombre);
            txt_matricula = itemView.findViewById(R.id.txt_item_matricula);
        }

        public void setData(Integrante integrante) {
            txt_nombre.setText(integrante.getNombre());
            txt_matricula.setText(String.valueOf(integrante.getMatricula()));
        }
    }
}
