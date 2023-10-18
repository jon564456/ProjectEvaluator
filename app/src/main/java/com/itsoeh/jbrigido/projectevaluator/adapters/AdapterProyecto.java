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
import com.itsoeh.jbrigido.projectevaluator.modelo.Proyecto;

import java.util.ArrayList;


public class AdapterProyecto extends RecyclerView.Adapter<AdapterProyecto.ViewHolderProyecto> {
    private ArrayList<Proyecto> proyectos;

    public AdapterProyecto(ArrayList<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    @NonNull
    @Override
    public AdapterProyecto.ViewHolderProyecto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proyecto, parent, false);
        return new ViewHolderProyecto(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProyecto.ViewHolderProyecto holder, int position) {
        holder.setdata(proyectos.get(position));
        holder.ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(view, position);
            }
        });

    }

    private void show(View v, int pos) {
        NavController nav = Navigation.findNavController(v);
        Bundle datos = new Bundle();
        Proyecto proyecto = proyectos.get(pos);
        datos.putString("clave", proyecto.getClave());
        // datos.putString("res", proyecto.getIntegrantes().get(0).getNombre() + " " + proyecto.getIntegrantes().get(0).getAppa() + " " + proyecto.getIntegrantes().get(0).getApma());
        datos.putString("titulo", proyecto.getNombre());
        datos.putString("cat", proyecto.getCategoria());
        datos.putString("grado", proyecto.getGrado() + " " + proyecto.getGrupo());
        nav.navigate(R.id.frag_view_info_proyecto, datos);
    }

    @Override
    public int getItemCount() {
        return proyectos.size();
    }

    public class ViewHolderProyecto extends RecyclerView.ViewHolder {

        private TextView text_nombre, text_clave, text_cat, text_gradogrupo;
        private ImageView ver;

        public ViewHolderProyecto(@NonNull View itemView) {
            super(itemView);
            text_nombre = itemView.findViewById(R.id.proy_text_nombre);
            text_clave = itemView.findViewById(R.id.proy_text_clave);
            text_cat = itemView.findViewById(R.id.proy_text_cat);
            text_gradogrupo = itemView.findViewById(R.id.proy_text_gradogrupo);
            ver = itemView.findViewById(R.id.proy_item_ver);
        }

        public void setdata(Proyecto proyecto) {
            if (proyecto != null) {
                text_nombre.setText(proyecto.getNombre());
                text_clave.setText(proyecto.getClave());
                text_cat.setText(proyecto.getCategoria());
                text_gradogrupo.setText(proyecto.getGrado() + " " + proyecto.getGrupo());
            }
        }

    }
}
