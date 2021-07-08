package com.example.miscompras;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "subcategorias",
        foreignKeys = @ForeignKey(entity = Categoria.class,
        parentColumns = "id",
        childColumns = "id_categoria",
        onDelete = CASCADE))
public class Subcategoria {
    @PrimaryKey(autoGenerate = true) @NonNull
    private int id;
    @NonNull
    private int id_categoria;
    @NonNull
    private String subcategoria;

    public Subcategoria(int id_categoria, String subcategoria) {
        this.id_categoria = id_categoria;
        this.subcategoria = subcategoria;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
