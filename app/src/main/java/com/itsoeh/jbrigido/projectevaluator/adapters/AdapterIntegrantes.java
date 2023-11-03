package com.itsoeh.jbrigido.projectevaluator.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.modelo.Integrante;

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
        private TextView text_nombre, text_matricula, text_correo;

        public ViewHolderIntegrante(@NonNull View itemView) {
            super(itemView);
            text_nombre = itemView.findViewById(R.id.item_inte_text_nombre);
            text_matricula = itemView.findViewById(R.id.item_inte_text_matricula);
            text_correo = itemView.findViewById(R.id.item_inte_text_correo);
        }

        public void setData(Integrante integrante) {
            text_matricula.setText(integrante.getMatricula());
            text_nombre.setText(integrante.getNombre());
            text_correo.setText(integrante.getCorreo());
        }
    }
}
