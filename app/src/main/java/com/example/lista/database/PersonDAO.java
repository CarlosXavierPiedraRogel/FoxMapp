package com.example.lista.database;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface PersonDAO {

    @Query("SELECT * FROM persona")
    List<Persona> getPersonas();

    @Query("SELECT * FROM persona WHERE id LIKE :uuid")
    Persona getPersona(String uuid);

    @Insert
    void insertPersona(Persona persona);

    @Update
    void updatePersona(Persona persona);

    @Delete
    void deletePersona(Persona persona);
}
