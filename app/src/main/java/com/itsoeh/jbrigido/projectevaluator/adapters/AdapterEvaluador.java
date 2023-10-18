package com.itsoeh.jbrigido.projectevaluator.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        holder.ver.setOnClickListener(new View.OnClickListener() {
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
        datos.putString("name", e.getNombre());
        datos.putString("app", e.getAppa());
        datos.putString("apm", e.getApma());
        datos.putString("mail", e.getCorreo());
        datos.putString("esp", e.getEspecialidad());
        datos.putString("grad", e.getGrado());
        datos.putString("pro", e.getProcedencia());
        nav.navigate(R.id.frag_view_info_eva,datos);
    }

    public class ViewHolderEvaluador extends RecyclerView.ViewHolder {

        private Bitmap icon_dato;
        private TextView nombre, procedencia, area;
        private ImageView ver;
        private Evaluador evaluador;


        public ViewHolderEvaluador(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.eva_item_nombre);
            procedencia = itemView.findViewById(R.id.eva_item_procedencia);
            area = itemView.findViewById(R.id.eva_item_especialidad);
            ver = itemView.findViewById(R.id.eva_item_ver);
            icon_dato = BitmapFactory.decodeResource(itemView.getResources(), R.drawable.school);
        }


        public void setData(Evaluador evaluador) {
            this.evaluador = evaluador;
            if (evaluador != null) {
                nombre.setText(evaluador.getNombre().toString()
                        + " " + evaluador.getAppa().toString()
                        + " " + evaluador.getApma().toString());
                procedencia.setText(evaluador.getProcedencia().toString());
                area.setText(evaluador.getEspecialidad().toString());
            }
        }
    }
}
