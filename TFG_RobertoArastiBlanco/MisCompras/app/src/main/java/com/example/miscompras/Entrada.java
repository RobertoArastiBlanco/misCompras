package com.example.miscompras;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "entradas",
        primaryKeys = {"id_producto", "precio", "fecha"},
        foreignKeys = @ForeignKey(entity = Producto.class,
                parentColumns = "id",
                childColumns = "id_producto",
                onDelete = CASCADE))
public class Entrada {
    @NonNull
    private int id_producto;

    private float precio;

    @NonNull
    private String fecha;

    public Entrada(int id_producto, float precio, String fecha) {
        this.id_producto = id_producto;
        this.precio = precio;
        this.fecha = fecha;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @NonNull
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

