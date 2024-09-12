package com.itsoeh.jbrigido.projectevaluator.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.jbrigido.projectevaluator.R;
import com.itsoeh.jbrigido.projectevaluator.modelo.Evaluador;

import java.util.ArrayList;

public class AdapterEvaluador extends RecyclerView.Adapter<AdapterEvaluador.ViewHolderEvaluador> {


    private ArrayList<Evaluador> evaluadores;

    public AdapterEvaluador(ArrayList<Evaluador> list) {
        this.evaluadores = list;
    }

    @NonNull
    @Override
    public ViewHolderEvaluador onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluador, parent, false);
        return new ViewHolderEvaluador(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEvaluador holder, int position) {
        holder.setData(evaluadores.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return evaluadores.size();
    }

    public void filter(ArrayList<Evaluador> listFilter) {
        this.evaluadores = listFilter;
        notifyDataSetChanged();
    }

    private void show(View v, int position) {
        NavController nav = Navigation.findNavController(v);
        Bundle datos = new Bundle();
        Evaluador e = evaluadores.get(position);
        datos.putInt("id", e.getId());
        datos.putString("nombre", e.getNombre());
        datos.putString("apepa", e.getApellidos());
        datos.putString("correo", e.getCorreo());
        datos.putString("especialidad", e.getEspecialidad());
        datos.putString("grado", e.getGrado());
        datos.putString("procedencia", e.getProcedencia());
        nav.navigate(R.id.frag_view_info_eva, datos);
    }

    public class ViewHolderEvaluador extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView nombre, procedencia;
        private Evaluador evaluador;


        public ViewHolderEvaluador(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_evaluador);
            nombre = itemView.findViewById(R.id.txt_item_nombre_evaluador);
            procedencia = itemView.findViewById(R.id.txt_item_procedencia);
        }


        public void setData(Evaluador evaluador) {
            this.evaluador = evaluador;
            if (evaluador != null) {
                nombre.setText(new StringBuilder().
                        append(evaluador.getNombre())
                        .append(" ")
                        .append(evaluador.getApellidos())
                );
                procedencia.setText(evaluador.getProcedencia());
            }
        }
    }
}
