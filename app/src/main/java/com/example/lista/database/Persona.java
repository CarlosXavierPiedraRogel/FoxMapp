package com.example.lista.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "persona")
public class Persona {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nombre")
    @NonNull
    private String nombre;

    @ColumnInfo(name = "url_foto")
    private String url_foto;

    @ColumnInfo(name = "direccion")
    private String direccion;

    @ColumnInfo(name = "telefono")
    private String telefono;

    public Persona() {

    }

    // Modificar el constructor para aceptar la URL de la imagen
    public Persona(@NonNull String nombre, String url_foto, String direccion, String telefono) {
        this.nombre = nombre;
        this.url_foto = url_foto;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Método getter y setter para la URL de la imagen
    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


}
