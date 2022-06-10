package ru.mirea.gasanyan.mireaproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface OwnerDao {
    @Query("SELECT * FROM owner")
    List<Owner> getAll();
    @Query("SELECT * FROM owner WHERE id = :id")
    Owner getById(long id);
    @Insert
    void insert(Owner owner);
    @Update
    void update(Owner owner);
    @Delete
    void delete(Owner owner);
}
