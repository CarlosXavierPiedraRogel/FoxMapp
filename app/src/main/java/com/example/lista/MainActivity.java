package com.example.lista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lista.database.Persona;
import com.example.lista.database.PersonaLab;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener {

    private RecyclerView Lista;
    private ArrayList<Persona> listaPersonas = new ArrayList<>();
    private PersonaLab personaLab;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar la instancia de la base de datos
        personaLab = PersonaLab.get(this);

        // Referenciar el RecyclerView en el diseño
        Lista = findViewById(R.id.recyclerview);
        Lista.setLayoutManager(new LinearLayoutManager(this));

        // Obtener todas las personas y mostrarlas en el RecyclerView
        getAllPersonas();

        // Crear y configurar el adaptador
        adapter = new RecyclerAdapter(listaPersonas, this);
        adapter.setOnItemClickListener(this); // Establecer el listener de clic en el adaptador
        Lista.setAdapter(adapter);

    }

    // Método llamado cuando se hace clic en un elemento del RecyclerView
    @Override
    public void onItemClick(Persona persona) {
        // Abrir la actividad de información de contacto con la persona seleccionada
        Intent intent = new Intent(this, InfoLugar.class);
        startActivity(intent);
    }

    // Método para manejar la creación de un nuevo registro desde el menú
    public void OnCreateRegistro(MenuItem item) {
        try {
            // Abrir la actividad de registro
            Intent intent = new Intent(this, Registro.class);
            startActivity(intent);
        } catch (Exception e) {
            Log.d("TAG", "Error!", e);
        }
    }

    // Método para inflar el menú en la barra de acciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Método para obtener todas las personas de la base de datos
    public void getAllPersonas() {
        listaPersonas.clear();
        listaPersonas.addAll(personaLab.getPersonas());
    }

    // Método llamado al reanudar la actividad
    @Override
    protected void onResume() {
        super.onResume();
        getAllPersonas();
        adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
    }
}
