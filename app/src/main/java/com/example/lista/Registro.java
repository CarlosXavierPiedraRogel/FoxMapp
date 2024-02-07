package com.example.lista;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lista.Mapa.MainMapa;
import com.example.lista.database.Persona;
import com.example.lista.database.PersonaLab;

public class Registro extends AppCompatActivity  implements  View.OnClickListener{

    private EditText txtNombre, txtDirrecion, txtNumero;
    private Button btnGuardar, btnAgregarImagen, btnUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        // Inicialización de vistas
        txtNombre = findViewById(R.id.txt_nombre);
        txtNumero = findViewById(R.id.txt_numero);
        txtDirrecion = findViewById(R.id.txt_direccion);
        btnGuardar = findViewById(R.id.btn_agregar);
        btnAgregarImagen = findViewById(R.id.button_agregar_img);
        btnUbicacion = findViewById(R.id.btnubicacion);

        btnGuardar.setOnClickListener(this);
        btnAgregarImagen.setOnClickListener(this);
        btnUbicacion.setOnClickListener(this);

      /*  // Configuración del botón "Guardar" para responder al clic
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarContacto();
            }
        });

        // Configuración del botón "Agregar Imagen" para abrir la actividad de la cámara
        btnAgregarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para abrir la actividad de la cámara
                Intent intent = new Intent(Registro.this, Camara.class);
                startActivity(intent);
            }
        });*/
    }
    public void onClick(View view){
        if(view==btnAgregarImagen){
            // Lógica para abrir la actividad de la cámara
            Intent intent = new Intent(Registro.this, Camara.class);
            startActivity(intent);
        }

        if(view==btnUbicacion){
            // Lógica para abrir la actividad del mapa
            Intent intent = new Intent(Registro.this, MainMapa.class);
            startActivity(intent);
        }

        if(view==btnGuardar){
            guardarContacto();
        }
    }


    // Método para guardar el contacto en la base de datos
    private void guardarContacto() {
        // Obtener datos de las vistas
        String nombre = txtNombre.getText().toString();
        String direccion = txtDirrecion.getText().toString();
        String numero = txtNumero.getText().toString();

        // Verificar que los campos obligatorios no estén vacíos
        if (!nombre.isEmpty() && !numero.isEmpty()) {
            // Crear una nueva persona con los datos ingresados
            Persona nuevaPersona = new Persona(nombre, null, direccion, numero);

            // Agregar la nueva persona a la base de datos
            PersonaLab.get(this).addPersona(nuevaPersona);

            // Establecer el resultado como RESULT_OK y finalizar la actividad
            setResult(RESULT_OK);
            finish();
        }
    }
}
