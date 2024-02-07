package com.example.lista;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lista.Mapa.Mi_Posicion;
import com.example.lista.database.Persona;
import com.example.lista.database.PersonaLab;

public class InfoLugar extends AppCompatActivity {
    PersonaLab personaLab;
    Persona persona;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_lugar);

        // Inicializar la instancia de la base de datos
        personaLab = new PersonaLab(this);

        // Obtener los datos extras de la intención
        Bundle extras = getIntent().getExtras();

        // Obtener el ID de la persona seleccionada
        int id = extras.getInt("id");

        // Inicializar la instancia de Persona
        persona = new Persona();
        try {
            // Obtener la persona de la base de datos utilizando el ID
            persona = personaLab.getPersona(String.valueOf(id + 1));

            // Obtener los datos de la persona
            String nombre = persona.getNombre();
            String direccion = persona.getDireccion();
            String numeroCelular = persona.getTelefono();

            // Referenciar los TextView en el diseño
            TextView txtNom = findViewById(R.id.txt_nom);
            TextView txtDir = findViewById(R.id.txt_direccion);
            TextView txtNum = findViewById(R.id.txt_num);
            TextView txtLatitud = findViewById(R.id.txt_latitud); // Agregado para la latitud
            TextView txtLongitud = findViewById(R.id.txt_longitud); // Agregado para la longitud

            // Mostrar los datos de la persona en los TextView
            txtNom.setText(nombre);
            txtDir.setText(direccion);
            txtNum.setText(numeroCelular);

            // Obtener la ubicación guardada desde las preferencias compartidas
            SharedPreferences sharedPreferences = getSharedPreferences("Ubicacion", Context.MODE_PRIVATE);
            double latitude = sharedPreferences.getFloat("LATITUDE", 0.0f);
            double longitude = sharedPreferences.getFloat("LONGITUDE", 0.0f);

            // Mostrar los datos de la persona en los TextView
            txtLatitud.setText(String.valueOf(latitude)); // Mostrar la latitud
            txtLongitud.setText(String.valueOf(longitude)); // Mostrar la longitud

        } catch (Exception e) {
            // Manejar excepciones si ocurrieran al obtener la información de la persona
        }
    }
}
