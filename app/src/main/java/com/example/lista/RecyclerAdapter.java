package com.example.lista;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lista.database.Persona;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Persona> datos;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Persona persona);
    }

    public RecyclerAdapter(ArrayList<Persona> data, Context context) {
        this.datos = data;
        this.context = context;
    }

    // Método para establecer el listener de clic en el RecyclerView
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Inflar el diseño del elemento de la lista
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Establecer el nombre en el TextView correspondiente
        holder.txtNombre.setText(datos.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        // Devolver la cantidad de elementos en el conjunto de datos
        return datos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtNombre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Context ctx = view.getContext();
            if (position != RecyclerView.NO_POSITION) {
                // Obtener la persona seleccionada
                Persona personaSeleccionada = datos.get(position);

                // Si hay un listener registrado, llamar a onItemClick
                if (listener != null) {
                    listener.onItemClick(personaSeleccionada);
                }

                // Abrir la actividad de información del lugar
                Intent intent = new Intent(ctx, InfoLugar.class);
                intent.putExtra("id", position);
                ctx.startActivity(intent);
            }
        }
    }
}
