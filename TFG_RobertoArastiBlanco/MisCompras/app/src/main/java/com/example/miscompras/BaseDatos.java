package com.example.miscompras;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Categoria.class, Subcategoria.class, Producto.class, Entrada.class}, version = 2)
public abstract class BaseDatos extends RoomDatabase {
    public abstract DaoCompras daoCompras();
}
