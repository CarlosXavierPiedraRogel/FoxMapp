package com.example.lista.Mapa;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

// Definición de la clase miposicion que implementa LocationListener
public class Mi_Posicion implements LocationListener {

    // Variables estáticas para almacenar la latitud, longitud, estado del GPS y las coordenadas
    public static double latitud;
    public static double longitud;
    public static boolean statusGPS;
    public static Location coordenadas;

    // Método llamado cuando la ubicación ha cambiado
    @Override
    public void onLocationChanged(Location loc) {
        // Actualizar las variables de latitud, longitud y coordenadas con los nuevos valores
        latitud = loc.getLatitude();
        longitud = loc.getLongitude();
        coordenadas = loc;
    }

    // Método llamado cuando el proveedor de ubicación (GPS) se habilita
    public void onProviderEnabled(String provider) {
        // Actualizar el estado del GPS a habilitado
        statusGPS = true;
    }

    // Método llamado cuando el proveedor de ubicación (GPS) se deshabilita
    public void onProviderDisabled(String provider) {
        // Actualizar el estado del GPS a deshabilitado
        statusGPS = false;
    }
}
