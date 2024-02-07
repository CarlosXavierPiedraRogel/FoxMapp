package com.example.lista;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lista.database.Persona;
import com.example.lista.database.PersonaLab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

// Definición de la clase principal MainActivity que extiende AppCompatActivity e implementa View.OnClickListener
public class Camara extends AppCompatActivity implements View.OnClickListener {
    private Button mButtonOpenImage;
    private ImageView mImageView;
    private TextView mTextView;

    private final int REQUEST_CODE_CAMERA = 1;
    private boolean control = false;
    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            .getAbsolutePath() + "/ImageSites/";

    private File file = new File(ruta_fotos.toString());

    String imageFileName = "imageSites";

    // Método llamado cuando se crea la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camara);

        // Inicialización de vistas y configuración de listeners
        mButtonOpenImage = findViewById(R.id.button_foto);
        mImageView = findViewById(R.id.imageView_foto);
        mTextView = findViewById(R.id.txt_no_image);
        mButtonOpenImage.setOnClickListener(this);
        file.mkdirs(); // Crear directorio para almacenar imágenes
        loadImageFromStorage(); // Cargar imagen previamente guardada
    }

    // Método llamado cuando se hace clic en alguna vista
    @Override
    public void onClick(View v) {
        if (v == mButtonOpenImage) {
            // Lanzar la cámara del sistema
            launchSystemCamera();
            camaraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
        }
    }

    // Inicializar el lanzador de la cámara para obtener resultados
    ActivityResultLauncher<Intent> camaraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Acciones a realizar cuando la cámara ha devuelto el resultado
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        // Mostrar la imagen capturada en el ImageView
                        mImageView.setImageBitmap((Bitmap) extras.get("data"));
                        mImageView.setVisibility(View.VISIBLE);
                        mTextView.setVisibility(View.GONE);
                        control = true;
                    }
                }
            });

    // Lanzar la cámara del sistema
    private void launchSystemCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(takePictureIntent, REQUEST_CODE_CAMERA);
        }
    }

    // Iniciar una actividad con resultado
    private void startActivity(Intent takePictureIntent, int requestCodeCamera) {
        // Puedes realizar acciones adicionales antes de iniciar la actividad, por ejemplo, agregar extras al intent
        startActivityForResult(takePictureIntent, requestCodeCamera);
    }

    // Crear el menú de opciones en la barra superior
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camara, menu);
        return true;
    }

    // Manejar las opciones del menú
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.action_delete) {
            // Eliminar la foto
            eliminarFoto();
        }
        if (item.getItemId() == R.id.action_save) {
            // Guardar la imagen
            guardarImagen();
        }

        return true;
    }

    // Guardar la imagen en la memoria externa
    private void guardarImagen() {
        FileOutputStream fos = null;
        try {
            if (control) {
                // Obtener la imagen del ImageView y comprimirla en formato PNG
                Bitmap imagen = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
                File image = new File(file, imageFileName + ".jpg");
                fos = new FileOutputStream(image);
                imagen.compress(Bitmap.CompressFormat.PNG, 100, fos);

                // Cerrar el flujo de salida
                fos.close();

                // Mostrar mensaje de éxito
                Toast.makeText(getApplicationContext(), "LA IMAGEN SE GUARDO CON EXITO", Toast.LENGTH_SHORT).show();

                // Obtener la URL de la imagen
                String imageURL = image.getAbsolutePath();

                // Crear una instancia de Persona con la URL de la imagen y guardarla en la base de datos
                Persona persona = new Persona("Nombre de la persona", imageURL, "Dirección de la persona", "Teléfono de la persona");
                // Obtener la instancia de la base de datos y guardar la persona
                PersonaLab.get(getApplicationContext()).addPersona(persona);
            } else {
                // Mostrar mensaje si no hay imagen para guardar
                Toast.makeText(getApplicationContext(), "NO HAY IMAGEN PARA GUARDAR", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    // Eliminar la foto almacenada
    private void eliminarFoto() {
        if (control) {
            // Mostrar un diálogo de confirmación para borrar la imagen
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Esta seguro de borrar la imagen?");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Borrar la imagen y reiniciar las vistas
                    File f = new File(file, imageFileName + ".jpg");
                    f.delete();
                    mImageView.setImageBitmap(null);
                    mTextView.setVisibility(View.VISIBLE);
                    control = false;
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // No hacer nada si se selecciona "No" en el diálogo
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            // Mostrar mensaje si no hay foto para eliminar
            Toast.makeText(this, "No hay foto", Toast.LENGTH_SHORT).show();
        }
    }

    // Cargar la imagen desde la memoria externa al iniciar la aplicación
    private void loadImageFromStorage() {
        try {
            File f = new File(file, imageFileName + "jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            if (b != null) {
                // Mostrar la imagen en el ImageView y ocultar el TextView
                mImageView.setImageBitmap(b);
                mTextView.setVisibility(View.GONE);
                control = true;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
