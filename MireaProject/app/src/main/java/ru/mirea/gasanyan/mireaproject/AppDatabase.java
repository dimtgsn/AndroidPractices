package ru.mirea.gasanyan.mireaproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Owner.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase{
    public abstract OwnerDao ownerDao();
}
