package com.example.miscompras;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "productos",
        foreignKeys = @ForeignKey(entity = Subcategoria.class,
                parentColumns = "id",
                childColumns = "id_subcategoria",
                onDelete = CASCADE))
public class Producto {
    @PrimaryKey(autoGenerate = true) @NonNull
    private int id;
    @NonNull
    private int id_subcategoria;
    @NonNull
    private String producto;

    public Producto(int id_subcategoria, String producto) {
        this.id_subcategoria = id_subcategoria;
        this.producto = producto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_subcategoria() {
        return id_subcategoria;
    }

    public void setId_subcategoria(int id_subcategoria) {
        this.id_subcategoria = id_subcategoria;
    }

    @NonNull
    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
}
