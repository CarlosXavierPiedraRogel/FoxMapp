package com.example.lista.Mapa;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.lista.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class MainMapa extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private Button btnmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapita);

        // Obtener el fragmento del mapa asincrónicamente
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        // Inicializar el botón y establecer el OnClickListener
        btnmap = findViewById(R.id.btn_ubi);
        btnmap.setOnClickListener(this);
    }

    // Método llamado cuando el mapa está listo para ser usado
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = Objects.requireNonNull(googleMap);

        // Configurar un listener para el clic largo en el mapa
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                // Guardar la ubicación en las preferencias compartidas
                saveLocationToSharedPreferences(latLng.latitude, latLng.longitude);
                Toast.makeText(MainMapa.this, "Ubicación guardada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para guardar la ubicación en las preferencias compartidas
    private void saveLocationToSharedPreferences(double latitude, double longitude) {
        SharedPreferences sharedPreferences = getSharedPreferences("Ubicacion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putFloat("LATITUDE", (float) latitude);
        editor.putFloat("LONGITUDE", (float) longitude);

        editor.apply();
    }

    // Método para obtener la ubicación guardada desde las preferencias compartidas
    private LatLng getSavedLocationFromSharedPreferences() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        double latitude = sharedPreferences.getFloat("LATITUDE", 0.0f);
        double longitude = sharedPreferences.getFloat("LONGITUDE", 0.0f);

        if (latitude != 0.0 && longitude != 0.0) {
            return new LatLng(latitude, longitude);
        } else {
            return null;
        }
    }

    // Método para obtener la ubicación actual del usuario
    private void miPosicion() {
        LocationManager objLocation = null;
        LocationListener objLocListener;
        objLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        objLocListener = new Mi_Posicion();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Considerar solicitar permisos
            return;
        }

        // Solicitar actualizaciones de ubicación al LocationManager
        objLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, objLocListener);

        if (objLocation.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (Mi_Posicion.latitud != 0) {
                // Obtener la ubicación actual y mostrarla en el mapa
                double lat = Mi_Posicion.latitud;
                double lon = Mi_Posicion.longitud;
                LatLng ubi = new LatLng(lat, lon);
                mMap.addMarker(new MarkerOptions().position(ubi).title("Mi ubicación"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(ubi));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            } else {
                Toast.makeText(MainMapa.this, "La ubicación no está disponible", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Mostrar un diálogo si el GPS no está activado
            AlertDialog.Builder alert = new AlertDialog.Builder(MainMapa.this);
            alert.setTitle("GPS NO ESTÁ ACTIVO");
            alert.setMessage("Conectando con GPS");
            alert.setPositiveButton("OK", null);
            alert.create().show();
        }
    }

    // Método llamado cuando se hace clic en el botón
    @Override
    public void onClick(View v) {
        if (v == btnmap) {
            // Obtener la ubicación guardada y mostrarla en el mapa
            LatLng savedLocation = getSavedLocationFromSharedPreferences();

            if (savedLocation != null) {
                mMap.addMarker(new MarkerOptions().position(savedLocation).title("Ubicación guardada"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(savedLocation));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            } else {
                Toast.makeText(MainMapa.this, "No se ha guardado ninguna ubicación", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
