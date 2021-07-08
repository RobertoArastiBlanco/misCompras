package com.example.miscompras;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Mover_elementos extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mover_elementos);

        Spinner spinner1 = findViewById(R.id.spinner10);
        Spinner spinner2 = findViewById(R.id.spinner11);
        Spinner spinner3 = findViewById(R.id.spinner12);
        Spinner spinner4 = findViewById(R.id.spinner13);
        Spinner spinner5 = findViewById(R.id.spinner14);
        List<String> listaCategorias=Inicio.baseDatos.daoCompras().listaCategorias();
        if(listaCategorias.size()==0){
            listaCategorias.add("");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);
        spinner4.setOnItemSelectedListener(this);
        spinner5.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner1 = findViewById(R.id.spinner10);
        Spinner spinner2 = findViewById(R.id.spinner11);
        Spinner spinner3 = findViewById(R.id.spinner12);
        Spinner spinner4 = findViewById(R.id.spinner13);
        Spinner spinner5 = findViewById(R.id.spinner14);
        int id_categoria;
        int id_subcategoria;
        int id_categoria2;

        switch (parent.getId()) {
            case R.id.spinner10:
                id_categoria=Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner1.getSelectedItem().toString());
                List<String> listaSubcategorias = Inicio.baseDatos.daoCompras().listaSubcategorias(id_categoria);
                if(listaSubcategorias.size()==0){
                    listaSubcategorias.add("");
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaSubcategorias);
                ArrayAdapter<String> adapterVacio = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapterVacio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(adapterVacio);
                spinner2.setAdapter(adapter);
                break;
            case R.id.spinner11:
                id_categoria=Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner1.getSelectedItem().toString());
                id_subcategoria=Inicio.baseDatos.daoCompras().idSubcategoriaPorNombre(id_categoria, spinner2.getSelectedItem().toString());
                List<String> listaProductos = Inicio.baseDatos.daoCompras().listaProductos(id_subcategoria);
                listaProductos.add(0, "");
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaProductos);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(adapter2);
                break;
            case R.id.spinner12:
                break;
            case R.id.spinner13:
                id_categoria2=Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner4.getSelectedItem().toString());
                List<String> listaSubcategorias2 = Inicio.baseDatos.daoCompras().listaSubcategorias(id_categoria2);
                listaSubcategorias2.add(0, "");
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaSubcategorias2);
                spinner5.setAdapter(adapter3);
                break;
            case R.id.spinner14:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void mover(View view){
        Spinner spinner1 = findViewById(R.id.spinner10);
        Spinner spinner2 = findViewById(R.id.spinner11);
        Spinner spinner3 = findViewById(R.id.spinner12);
        Spinner spinner4 = findViewById(R.id.spinner13);
        Spinner spinner5 = findViewById(R.id.spinner14);

        int id_categoria = Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner1.getSelectedItem().toString());
        int id_subcategoria = Inicio.baseDatos.daoCompras().idSubcategoriaPorNombre(id_categoria, spinner2.getSelectedItem().toString());
        int id_producto = Inicio.baseDatos.daoCompras().idProductoPorNombre(id_subcategoria, spinner3.getSelectedItem().toString());
        int id_categoria2 = Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner4.getSelectedItem().toString());
        int id_subcategoria2 = Inicio.baseDatos.daoCompras().idSubcategoriaPorNombre(id_categoria2, spinner5.getSelectedItem().toString());

        if(id_categoria==0){
            Toast.makeText(view.getContext(), "Todavía no hay datos", Toast.LENGTH_SHORT).show();
        }else if(id_subcategoria==0){
            Toast.makeText(view.getContext(), "Por favor, seleccione un elemento a mover", Toast.LENGTH_SHORT).show();
        }else if(id_producto==0 && id_subcategoria2!=0){
            Toast.makeText(view.getContext(), "Por favor, seleccione un producto", Toast.LENGTH_SHORT).show();
        }else if(id_producto!=0 && id_subcategoria2==0){
            Toast.makeText(view.getContext(), "Por favor, seleccione una subcategoría a la que mover el producto", Toast.LENGTH_SHORT).show();
        }else if(id_categoria==id_categoria2 && id_producto==0 || id_subcategoria==id_subcategoria2){
            Toast.makeText(view.getContext(), "Ya se encuentra en ese sitio", Toast.LENGTH_SHORT).show();
        }else{
            if(id_producto==0){
                if(!Inicio.baseDatos.daoCompras().listaSubcategorias(id_categoria2).contains(spinner2.getSelectedItem().toString())){
                    Inicio.baseDatos.daoCompras().moverSubcategoria(id_subcategoria, id_categoria2);
                    Toast.makeText(view.getContext(), "Movimiento realizado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(view.getContext(), "Ya existe la subcategoría "+spinner2.getSelectedItem().toString()+" en "+spinner4.getSelectedItem().toString()+".", Toast.LENGTH_SHORT).show();
                }
            }else{
                if(!Inicio.baseDatos.daoCompras().listaProductos(id_subcategoria2).contains(spinner3.getSelectedItem().toString())){
                    Inicio.baseDatos.daoCompras().moverProducto(id_producto, id_subcategoria2);
                    Toast.makeText(view.getContext(), "Movimiento realizado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(view.getContext(), "Ya existe el producto "+spinner3.getSelectedItem().toString()+" en "+spinner5.getSelectedItem().toString()+".", Toast.LENGTH_SHORT).show();
                }
            }
            List<String> listaCategorias=Inicio.baseDatos.daoCompras().listaCategorias();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCategorias);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter);
            spinner4.setAdapter(adapter);
        }
    }
}