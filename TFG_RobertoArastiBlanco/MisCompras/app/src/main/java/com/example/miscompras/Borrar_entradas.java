package com.example.miscompras;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Borrar_entradas extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar_entradas);

        Spinner spinnerEntradas = findViewById(R.id.spinnerEntradas);
        List<String> listaEntradas= new ArrayList<String>();
        List<EntradaCompleta> entradasCompletas= Inicio.baseDatos.daoCompras().listaEntradas();
        for(EntradaCompleta entrada: entradasCompletas){
            listaEntradas.add(entrada.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaEntradas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEntradas.setAdapter(adapter);
        spinnerEntradas.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void borrarEntrada(View view){
        if(Inicio.baseDatos.daoCompras().listaEntradas().size()!=0){
            Spinner spinnerEntradas = findViewById(R.id.spinnerEntradas);
            List<String> coincidencias = new ArrayList<String>();
            List<String> coincidenciasSinComillas = new ArrayList<String>();
            Matcher m = Pattern.compile("\"[^\"]+\"|[^,]+").matcher(spinnerEntradas.getSelectedItem().toString());
            while (m.find()) {
                coincidencias.add(m.group());
            }
            for (String cadena : coincidencias) {
                if (cadena.matches("\"[^\"]+\"")) {
                    coincidenciasSinComillas.add(cadena.substring(1, cadena.length() - 1));
                } else {
                    coincidenciasSinComillas.add(cadena);
                }
            }
            int idCategoria = Inicio.baseDatos.daoCompras().idCategoriaPorNombre(coincidenciasSinComillas.get(0));
            int idSubcategoria = Inicio.baseDatos.daoCompras().idSubcategoriaPorNombre(idCategoria, coincidenciasSinComillas.get(1));
            int idProducto = Inicio.baseDatos.daoCompras().idProductoPorNombre(idSubcategoria, coincidenciasSinComillas.get(2));
            Inicio.baseDatos.daoCompras().borrarEntrada(idProducto, Float.parseFloat(coincidenciasSinComillas.get(3)), coincidenciasSinComillas.get(4));
            Toast.makeText(view.getContext(), coincidenciasSinComillas.toString() + " eliminada", Toast.LENGTH_SHORT).show();
            List<String> listaEntradas = new ArrayList<String>();
            List<EntradaCompleta> entradasCompletas = Inicio.baseDatos.daoCompras().listaEntradas();
            for (EntradaCompleta entrada : entradasCompletas) {
                listaEntradas.add(entrada.toString());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaEntradas);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerEntradas.setAdapter(adapter);
        }else{
            Toast.makeText(view.getContext(), "No hay entradas que eliminar", Toast.LENGTH_SHORT).show();
        }
    }
}