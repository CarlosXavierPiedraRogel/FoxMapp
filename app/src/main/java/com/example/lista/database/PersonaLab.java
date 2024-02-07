package com.example.lista.database;
import android.annotation.SuppressLint;
import android.content.Context;
import androidx.room.Room;
import java.util.List;

public class PersonaLab {

    @SuppressLint("StaticFieldLeak")
    private static PersonaLab sPersonaLab;
    private PersonDAO mPersonaDao;


    public PersonaLab(Context context){
        Context appContext=context.getApplicationContext();
        PersonaDatabase database=
                Room.databaseBuilder(
                                appContext,PersonaDatabase.class,"persona")
                        .allowMainThreadQueries().build();
        mPersonaDao=database.getPersonaDAO();
    }

    public static PersonaLab get(Context context) {
        if (sPersonaLab == null) {
            sPersonaLab = new PersonaLab(context);
        }
        return sPersonaLab;
    }

    public List<Persona> getPersonas() {
        return mPersonaDao.getPersonas();
    }

    public Persona getPersona(String id) { return mPersonaDao.getPersona(id);}

    public void addPersona(Persona persona) {
        mPersonaDao.insertPersona(persona);
    }

    public void deletePersona(Persona persona) {
        mPersonaDao.deletePersona(persona);
    }

    public void updatePersona(Persona persona) {
        mPersonaDao.updatePersona(persona);
    }
}
