package com.example.miscompras;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Borrar_elementos extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar_elementos);

        Spinner spinner1 = findViewById(R.id.spinner7);
        Spinner spinner2 = findViewById(R.id.spinner8);
        Spinner spinner3 = findViewById(R.id.spinner9);
        List<String> listaCategorias=Inicio.baseDatos.daoCompras().listaCategorias();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner1 = findViewById(R.id.spinner7);
        Spinner spinner2 = findViewById(R.id.spinner8);
        Spinner spinner3 = findViewById(R.id.spinner9);
        int id_categoria;
        int id_subcategoria;
        int id_producto;

        switch (parent.getId()) {
            case R.id.spinner7:
                id_categoria=Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner1.getSelectedItem().toString());
                List<String> listaSubcategorias = Inicio.baseDatos.daoCompras().listaSubcategorias(id_categoria);
                listaSubcategorias.add(0, "");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaSubcategorias);
                ArrayAdapter<String> adapterVacio = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapterVacio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(adapterVacio);
                spinner2.setAdapter(adapter);
                break;
            case R.id.spinner8:
                id_categoria=Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner1.getSelectedItem().toString());
                id_subcategoria=Inicio.baseDatos.daoCompras().idSubcategoriaPorNombre(id_categoria, spinner2.getSelectedItem().toString());
                List<String> listaProductos = Inicio.baseDatos.daoCompras().listaProductos(id_subcategoria);
                listaProductos.add(0, "");
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaProductos);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(adapter2);
                break;
            case R.id.spinner9:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void borrarElemento(View view){
        Spinner spinner1 = findViewById(R.id.spinner7);
        Spinner spinner2 = findViewById(R.id.spinner8);
        Spinner spinner3 = findViewById(R.id.spinner9);
        if(spinner1.getSelectedItem()!=null) {
            int id_categoria = Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner1.getSelectedItem().toString());
            int id_subcategoria = Inicio.baseDatos.daoCompras().idSubcategoriaPorNombre(id_categoria, spinner2.getSelectedItem().toString());
            int id_producto = Inicio.baseDatos.daoCompras().idProductoPorNombre(id_subcategoria, spinner3.getSelectedItem().toString());
            String opcionMover = ". Puedes mover elementos de sitio para evitarlo con la opción \"Mover elementos\". ";
            AlertDialog.Builder builder = new AlertDialog.Builder(Borrar_elementos.this);
            builder.setCancelable(false);
            if (id_subcategoria == 0) {
                builder.setMessage("Al borrar esta categoria se eliminaran tambien todas las " +
                        "subcategorias, productos y entradas de " +
                        spinner1.getSelectedItem().toString() + opcionMover +
                        " ¿Estas seguro?");
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Inicio.baseDatos.daoCompras().borrarCategoria(id_categoria);
                        Toast.makeText(view.getContext(), spinner1.getSelectedItem().toString() + " eliminado", Toast.LENGTH_SHORT).show();
                        List<String> listaCategorias = Inicio.baseDatos.daoCompras().listaCategorias();
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listaCategorias);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(adapter);
                        dialog.cancel();
                    }
                });
            } else if (id_producto == 0) {
                builder.setMessage("Al borrar esta subcategoria se eliminaran tambien todos los " +
                        "productos y entradas de " +
                        spinner2.getSelectedItem().toString() + opcionMover +
                        " ¿Estas seguro?");
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Inicio.baseDatos.daoCompras().borrarSubcategoria(id_subcategoria);
                        Toast.makeText(view.getContext(), spinner2.getSelectedItem().toString() + " eliminado", Toast.LENGTH_SHORT).show();
                        List<String> listaCategorias = Inicio.baseDatos.daoCompras().listaCategorias();
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listaCategorias);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(adapter);
                        dialog.cancel();
                    }
                });
            } else {
                builder.setMessage("Al borrar este producto se eliminaran tambien todas las " +
                        "entradas de " +
                        spinner3.getSelectedItem().toString() + opcionMover +
                        " ¿Estas seguro?");
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Inicio.baseDatos.daoCompras().borrarProducto(id_producto);
                        Toast.makeText(view.getContext(), spinner3.getSelectedItem().toString() + " eliminado", Toast.LENGTH_SHORT).show();
                        List<String> listaCategorias = Inicio.baseDatos.daoCompras().listaCategorias();
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listaCategorias);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(adapter);
                        dialog.cancel();
                    }
                });
            }
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alerta = builder.create();
            alerta.show();
        }else{
            Toast.makeText(view.getContext(), "Por favor, seleccione un elemento", Toast.LENGTH_SHORT).show();
        }
    }

}