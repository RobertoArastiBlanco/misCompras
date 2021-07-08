package com.example.miscompras;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

public class Guardar_datos extends AppCompatActivity implements AdapterView.OnItemSelectedListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_datos);

        Spinner spinner1 = findViewById(R.id.spinner);
        Spinner spinner2 = findViewById(R.id.spinner2);
        Spinner spinner3 = findViewById(R.id.spinner3);
        List<String> listaCategorias=Inicio.baseDatos.daoCompras().listaCategorias();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);

        NumberPicker np1 = findViewById(R.id.numberPicker);
        NumberPicker np2 = findViewById(R.id.numberPicker2);
        NumberPicker np3 = findViewById(R.id.numberPicker3);
        NumberPicker np4 = findViewById(R.id.numberPicker4);
        NumberPicker np5 = findViewById(R.id.numberPicker5);

        np1.setMaxValue(9);
        np1.setMinValue(0);
        np2.setMaxValue(9);
        np2.setMinValue(0);
        np3.setMaxValue(9);
        np3.setMinValue(0);
        np4.setMaxValue(9);
        np4.setMinValue(0);
        np5.setMaxValue(9);
        np5.setMinValue(0);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Spinner spinner1 = findViewById(R.id.spinner);
        List<String> listaCategorias=Inicio.baseDatos.daoCompras().listaCategorias();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
    }

    public void borrarEntradas(View view){
        Intent intent = new Intent(this, Borrar_entradas.class);
        startActivity(intent);
    }

    public void anadirCatPantalla(View view){
        Intent intent = new Intent(this, Anadir_categoria.class);
        startActivity(intent);
    }

    public void anadirSubcatPantalla(View view){
        Intent intent = new Intent(this, Anadir_subcategoria.class);
        Spinner spinner1 = findViewById(R.id.spinner);
        if(spinner1.getSelectedItem()!=null){
            intent.putExtra("id", Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner1.getSelectedItem().toString()));
            intent.putExtra("Categoria", spinner1.getSelectedItem().toString());
            startActivity(intent);
        }else{
            Toast.makeText(view.getContext(), "Por favor, seleccione una categoría a la que añadir la subcategoría", Toast.LENGTH_SHORT).show();
        }
    }

    public void anadirProdPantalla(View view){
        Intent intent = new Intent(this, Anadir_producto.class);
        Spinner spinner1 = findViewById(R.id.spinner);
        Spinner spinner2 = findViewById(R.id.spinner2);
        if(spinner2.getSelectedItem()!=null){
            int id_subcategoria = Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner1.getSelectedItem().toString());
            intent.putExtra("id", Inicio.baseDatos.daoCompras().idSubcategoriaPorNombre(id_subcategoria, spinner2.getSelectedItem().toString()));
            intent.putExtra("Categoria", spinner1.getSelectedItem().toString());
            intent.putExtra("Subcategoria", spinner2.getSelectedItem().toString());
            startActivity(intent);
        }else{
            Toast.makeText(view.getContext(), "Por favor, seleccione una subcategoría a la que añadir el producto", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner1 = findViewById(R.id.spinner);
        Spinner spinner2 = findViewById(R.id.spinner2);
        Spinner spinner3 = findViewById(R.id.spinner3);
        int id_categoria;
        int id_subcategoria;
        int id_producto;

        switch (parent.getId()) {
            case R.id.spinner:
                id_categoria=Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner1.getSelectedItem().toString());
                List<String> listaSubcategorias = Inicio.baseDatos.daoCompras().listaSubcategorias(id_categoria);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaSubcategorias);
                ArrayAdapter<String> adapterVacio = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapterVacio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(adapterVacio);
                spinner2.setAdapter(adapter);
                break;
            case R.id.spinner2:
                id_categoria=Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner1.getSelectedItem().toString());
                id_subcategoria=Inicio.baseDatos.daoCompras().idSubcategoriaPorNombre(id_categoria, spinner2.getSelectedItem().toString());
                List<String> listaProductos = Inicio.baseDatos.daoCompras().listaProductos(id_subcategoria);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaProductos);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(adapter2);
                break;
            case R.id.spinner3:
                NumberPicker np1 = findViewById(R.id.numberPicker);
                NumberPicker np2 = findViewById(R.id.numberPicker2);
                NumberPicker np3 = findViewById(R.id.numberPicker3);
                NumberPicker np4 = findViewById(R.id.numberPicker4);
                NumberPicker np5 = findViewById(R.id.numberPicker5);
                id_categoria=Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner1.getSelectedItem().toString());
                id_subcategoria=Inicio.baseDatos.daoCompras().idSubcategoriaPorNombre(id_categoria, spinner2.getSelectedItem().toString());
                id_producto=Inicio.baseDatos.daoCompras().idProductoPorNombre(id_subcategoria, spinner3.getSelectedItem().toString());
                float ultimoPrecio = Inicio.baseDatos.daoCompras().ultimoPrecioProducto(id_producto);
                int cifra1 = (int) (ultimoPrecio/100);
                int cifra2 = (int) (ultimoPrecio/10-10*cifra1);
                int cifra3 = (int) (ultimoPrecio-100*cifra1-10*cifra2);
                int cifra4 = (int) (ultimoPrecio*10-1000*cifra1-100*cifra2-10*cifra3);
                int cifra5 = (int) (ultimoPrecio*100-10000*cifra1-1000*cifra2-100*cifra3-10*cifra4);

                np1.setValue(cifra1);
                np2.setValue(cifra2);
                np3.setValue(cifra3);
                np4.setValue(cifra4);
                np5.setValue(cifra5);

                break;
            }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void guardar(View view){
        Spinner spinner1 = findViewById(R.id.spinner);
        Spinner spinner2 = findViewById(R.id.spinner2);
        Spinner spinner3 = findViewById(R.id.spinner3);
        NumberPicker np1 = findViewById(R.id.numberPicker);
        NumberPicker np2 = findViewById(R.id.numberPicker2);
        NumberPicker np3 = findViewById(R.id.numberPicker3);
        NumberPicker np4 = findViewById(R.id.numberPicker4);
        NumberPicker np5 = findViewById(R.id.numberPicker5);

        if(spinner3.getSelectedItem()!=null) {
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("presupuesto", Context.MODE_PRIVATE);
            float valorpresupuesto = preferences.getFloat("valor", 500.0f);
            int id_categoria = Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner1.getSelectedItem().toString());
            int id_subcategoria = Inicio.baseDatos.daoCompras().idSubcategoriaPorNombre(id_categoria, spinner2.getSelectedItem().toString());
            int id_producto = Inicio.baseDatos.daoCompras().idProductoPorNombre(id_subcategoria, spinner3.getSelectedItem().toString());
            float precio = (float) (np1.getValue() * 10000 + np2.getValue() * 1000 + np3.getValue() * 100 + np4.getValue() * 10 + np5.getValue()) / (float) 100;
            Entrada entrada = new Entrada(id_producto, precio, LocalDateTime.now().toString());
            Inicio.baseDatos.daoCompras().guardarEntrada(entrada);
            float gastoTotalMes = Inicio.baseDatos.daoCompras().gastoTotalPeriodo(YearMonth.from(LocalDateTime.now()).atDay(1).toString(), LocalDateTime.now().toString());
            ;
            if (gastoTotalMes > valorpresupuesto) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Guardar_datos.this);
                builder.setCancelable(false);
                builder.setMessage("Te has pasado del presupuesto, sube el presupuesto o aprieta el cinturón");
                builder.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
                AlertDialog alerta = builder.create();
                alerta.show();
            }
        }else{
            Toast.makeText(view.getContext(), "Por favor, seleccione un producto", Toast.LENGTH_SHORT).show();
        }
    }
}