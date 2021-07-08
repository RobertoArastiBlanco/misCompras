package com.example.miscompras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Inicio extends AppCompatActivity {
    public static BaseDatos baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        baseDatos = Room.databaseBuilder(getApplicationContext(), BaseDatos.class, "Base de datos").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        if(baseDatos.daoCompras().listaCategorias().size() != 0){
            findViewById(R.id.botonDemo).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(baseDatos.daoCompras().listaCategorias().size() != 0){
            findViewById(R.id.botonDemo).setVisibility(View.GONE);
        }
    }

    public void menu(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void acercaDe(View view){
        Intent intent = new Intent(this, Acerca_de.class);
        startActivity(intent);
    }

    public void cargarDemo(View view){
        Button demo=findViewById(R.id.botonDemo);
        demo.setVisibility(View.GONE);
        int id_categoria;
        int id_subcategoria;
        int id_producto;
        Toast.makeText(view.getContext(), "Cargando datos de prueba", Toast.LENGTH_SHORT).show();
        DaoCompras daoCompras = Inicio.baseDatos.daoCompras();
        ArrayList<Categoria> categoriasPrueba = new ArrayList<Categoria>();
        ArrayList<Subcategoria> subcategoriasPrueba = new ArrayList<Subcategoria>();
        ArrayList<Producto> productosPrueba = new ArrayList<Producto>();
        ArrayList<Entrada> entradasPrueba = new ArrayList<Entrada>();

        categoriasPrueba.add(new Categoria("Alimentacion"));
        categoriasPrueba.add(new Categoria("Deporte"));
        categoriasPrueba.add(new Categoria("Ocio"));
        daoCompras.guardarListaCategorias(categoriasPrueba);

        id_categoria=daoCompras.idCategoriaPorNombre("Alimentacion");
        subcategoriasPrueba.add(new Subcategoria(id_categoria, "Frutas"));
        subcategoriasPrueba.add(new Subcategoria(id_categoria, "Charcuteria"));
        id_categoria=daoCompras.idCategoriaPorNombre("Deporte");
        subcategoriasPrueba.add(new Subcategoria(id_categoria, "Tennis"));
        subcategoriasPrueba.add(new Subcategoria(id_categoria, "Running"));
        id_categoria=daoCompras.idCategoriaPorNombre("Ocio");
        subcategoriasPrueba.add(new Subcategoria(id_categoria, "Cine"));
        subcategoriasPrueba.add(new Subcategoria(id_categoria, "Lectura"));
        daoCompras.guardarListaSubcategorias(subcategoriasPrueba);

        id_categoria=daoCompras.idCategoriaPorNombre("Alimentacion");
        id_subcategoria=daoCompras.idSubcategoriaPorNombre(id_categoria,"Frutas");
        productosPrueba.add(new Producto(id_subcategoria, "Naranjas"));
        productosPrueba.add(new Producto(id_subcategoria, "Manzanas"));
        id_subcategoria=daoCompras.idSubcategoriaPorNombre(id_categoria,"Charcuteria");
        productosPrueba.add(new Producto(id_subcategoria, "Salami"));
        productosPrueba.add(new Producto(id_subcategoria, "Jamon de york"));
        id_categoria=daoCompras.idCategoriaPorNombre("Deporte");
        id_subcategoria=daoCompras.idSubcategoriaPorNombre(id_categoria,"Tennis");
        productosPrueba.add(new Producto(id_subcategoria, "Raquetas"));
        productosPrueba.add(new Producto(id_subcategoria, "Pelotas"));
        id_subcategoria=daoCompras.idSubcategoriaPorNombre(id_categoria,"Running");
        productosPrueba.add(new Producto(id_subcategoria, "Zapatillas"));
        productosPrueba.add(new Producto(id_subcategoria, "Leggings"));
        id_categoria=daoCompras.idCategoriaPorNombre("Ocio");
        id_subcategoria=daoCompras.idSubcategoriaPorNombre(id_categoria,"Cine");
        productosPrueba.add(new Producto(id_subcategoria, "Palomitas"));
        productosPrueba.add(new Producto(id_subcategoria, "Entradas"));
        id_subcategoria=daoCompras.idSubcategoriaPorNombre(id_categoria,"Lectura");
        productosPrueba.add(new Producto(id_subcategoria, "Libros"));
        productosPrueba.add(new Producto(id_subcategoria, "Revistas"));
        daoCompras.guardarListaProductos(productosPrueba);


        id_categoria=daoCompras.idCategoriaPorNombre("Alimentacion");
        id_subcategoria=daoCompras.idSubcategoriaPorNombre(id_categoria,"Frutas");
        id_producto=daoCompras.idProductoPorNombre(id_subcategoria, "Naranjas");
        entradasPrueba.add(new Entrada(id_producto, 3.05f, "2021-04-16 19:31:29"));
        entradasPrueba.add(new Entrada(id_producto, 2.93f, "2021-04-17 11:31:29"));
        id_producto=daoCompras.idProductoPorNombre(id_subcategoria, "Manzanas");
        entradasPrueba.add(new Entrada(id_producto, 2.30f, "2021-04-16 19:31:32"));
        id_subcategoria=daoCompras.idSubcategoriaPorNombre(id_categoria,"Charcuteria");
        id_producto=daoCompras.idProductoPorNombre(id_subcategoria, "Salami");
        entradasPrueba.add(new Entrada(id_producto, 1.25f, "2021-04-16 19:31:35"));
        entradasPrueba.add(new Entrada(id_producto, 1.25f, "2021-04-17 11:31:32"));
        id_producto=daoCompras.idProductoPorNombre(id_subcategoria, "Jamon de york");
        entradasPrueba.add(new Entrada(id_producto, 1.33f, "2021-04-16 19:31:38"));
        id_categoria=daoCompras.idCategoriaPorNombre("Ocio");
        id_subcategoria=daoCompras.idSubcategoriaPorNombre(id_categoria,"Lectura");
        id_producto=daoCompras.idProductoPorNombre(id_subcategoria, "Libros");
        entradasPrueba.add(new Entrada(id_producto, 23.45f, "2021-04-16 19:31:41"));
        id_producto=daoCompras.idProductoPorNombre(id_subcategoria, "Revistas");
        entradasPrueba.add(new Entrada(id_producto, 3.99f, "2021-04-16 19:31:44"));
        entradasPrueba.add(new Entrada(id_producto, 3.50f, "2021-04-17 11:31:35"));
        entradasPrueba.add(new Entrada(id_producto, 3.55f,  "2021-04-17 11:31:38"));

        daoCompras.guardarListaEntradas(entradasPrueba);

        Toast.makeText(view.getContext(), "Datos de prueba cargados", Toast.LENGTH_SHORT).show();
    }


}