package com.example.miscompras;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

public class Generar_estadisticas extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_estadisticas);

        Spinner spinner1 = findViewById(R.id.spinner4);
        Spinner spinner2 = findViewById(R.id.spinner5);
        Spinner spinner3 = findViewById(R.id.spinner6);
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
        Spinner spinner1 = findViewById(R.id.spinner4);
        Spinner spinner2 = findViewById(R.id.spinner5);
        Spinner spinner3 = findViewById(R.id.spinner6);
        int id_categoria;
        int id_subcategoria;
        int id_producto;

        switch (parent.getId()) {
            case R.id.spinner4:
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
            case R.id.spinner5:
                id_categoria=Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner1.getSelectedItem().toString());
                id_subcategoria=Inicio.baseDatos.daoCompras().idSubcategoriaPorNombre(id_categoria, spinner2.getSelectedItem().toString());
                List<String> listaProductos = Inicio.baseDatos.daoCompras().listaProductos(id_subcategoria);
                listaProductos.add(0, "");
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaProductos);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(adapter2);
                break;
            case R.id.spinner6:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void estadisticas(View view){
        Spinner spinner1 = findViewById(R.id.spinner4);
        Spinner spinner2 = findViewById(R.id.spinner5);
        Spinner spinner3 = findViewById(R.id.spinner6);
        TextView texto1=findViewById(R.id.texto1);
        TextView texto2=findViewById(R.id.texto2);
        TextView texto3=findViewById(R.id.texto3);
        float precioMedio;
        float gastoMes;
        float porcentajeMensual;
        LocalDate principioMes=YearMonth.from(LocalDateTime.now()).atDay(1);
        int id_categoria=Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner1.getSelectedItem().toString());
        int id_subcategoria=Inicio.baseDatos.daoCompras().idSubcategoriaPorNombre(id_categoria, spinner2.getSelectedItem().toString());
        int id_producto=Inicio.baseDatos.daoCompras().idProductoPorNombre(id_subcategoria, spinner3.getSelectedItem().toString());
        if(id_subcategoria==0){
            texto1.setText("");
            gastoMes=Inicio.baseDatos.daoCompras().gastoCategoriaPeriodo(id_categoria, principioMes.toString(), LocalDateTime.now().toString());
            porcentajeMensual=gastoMes/Inicio.baseDatos.daoCompras().gastoTotalPeriodo(principioMes.toString(), LocalDateTime.now().toString())*100;
            texto2.setText("Gasto este mes: " + String.format("%.2f", gastoMes)+"€");
            texto3.setText("Porcentaje del gasto de este mes: "+String.format("%.2f", porcentajeMensual)+"%");
        } else if(id_producto==0){
            texto1.setText("");
            gastoMes=Inicio.baseDatos.daoCompras().gastoSubcategoriaPeriodo(id_subcategoria, principioMes.toString(), LocalDateTime.now().toString());
            porcentajeMensual=gastoMes/Inicio.baseDatos.daoCompras().gastoTotalPeriodo(principioMes.toString(), LocalDateTime.now().toString())*100;
            texto2.setText("Gasto este mes:" + String.format("%.2f", gastoMes)+"€");
            texto3.setText("Porcentaje del gasto de este mes: "+String.format("%.2f", porcentajeMensual)+"%");
        } else {
            precioMedio=Inicio.baseDatos.daoCompras().precioMedioProducto(id_producto);
            texto1.setText("Precio medio:" + String.format("%.2f", precioMedio)+"€");
            gastoMes=Inicio.baseDatos.daoCompras().gastoProductoPeriodo(id_producto, principioMes.toString(), LocalDateTime.now().toString());
            porcentajeMensual=gastoMes/Inicio.baseDatos.daoCompras().gastoTotalPeriodo(principioMes.toString(), LocalDateTime.now().toString())*100;
            texto2.setText("Gasto este mes: " + String.format("%.2f", gastoMes)+"€");
            texto3.setText("Porcentaje del gasto de este mes: "+String.format("%.2f", porcentajeMensual)+"%");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void estadisticasGenerales(View view){
        TextView texto1=findViewById(R.id.texto1);
        TextView texto2=findViewById(R.id.texto2);
        TextView texto3=findViewById(R.id.texto3);
        LocalDate principioMes=YearMonth.from(LocalDateTime.now()).atDay(1);
        float gastoMes=Inicio.baseDatos.daoCompras().gastoTotalPeriodo(principioMes.toString(), LocalDateTime.now().toString());
        String primeraFecha= Inicio.baseDatos.daoCompras().fechaMasAntigua();
        YearMonth primerMes=YearMonth.parse(primeraFecha.substring(0,7));
        YearMonth esteMes= YearMonth.now();
        int totalMeses=(int) primerMes.until(esteMes, ChronoUnit.MONTHS)+1;
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("presupuesto", Context.MODE_PRIVATE);
        float valorpresupuesto=preferences.getFloat("valor", 500.0f);
        texto1.setText("Total gasto este mes: " + String.format("%.2f", gastoMes)+"€");
        texto2.setText("Gasto mensual medio: " + String.format("%.2f", Inicio.baseDatos.daoCompras().gastoTotal()/totalMeses)+"€");
        texto3.setText("Porcentaje del presupuesto gastado: "+String.format("%.2f", gastoMes/valorpresupuesto*100)+"%");

    }

    public void grafico(View view){
        Intent intent = new Intent(this, Mostrar_grafico.class);
        startActivity(intent);
    }
}