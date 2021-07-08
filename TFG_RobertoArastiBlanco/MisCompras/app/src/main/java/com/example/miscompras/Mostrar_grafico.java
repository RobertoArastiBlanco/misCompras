package com.example.miscompras;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Mostrar_grafico extends AppCompatActivity {

    PieChart grafico;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_grafico);

        grafico=(PieChart) findViewById(R.id.graficoTarta);

        grafico.getDescription().setText("Gastos absolutos por categoría");
        grafico.setRotationEnabled(false);
        grafico.setHoleRadius(25);
        grafico.setHoleColor(0xffffffff);
        grafico.setTransparentCircleAlpha(0);
        grafico.setCenterText("Categorías");
        grafico.setCenterTextSize(10);

        meterDatos();
        grafico.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String texto=h.toString();
                int x=Integer.parseInt(texto.substring(texto.indexOf("x:")+3, texto.indexOf(".")));
                String categoria=Inicio.baseDatos.daoCompras().listaCategorias().get(x);
                texto=categoria+":"+e.toString().substring(e.toString().indexOf("y:")+3)+"€";
                Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void meterDatos() {
        ArrayList<PieEntry> y=new ArrayList<PieEntry>();
        ArrayList<Integer> colores=new ArrayList<Integer>();

        String primeraFecha= Inicio.baseDatos.daoCompras().fechaMasAntigua();
        int id_categoria=0;
        int i=0;

        for(String categoria:Inicio.baseDatos.daoCompras().listaCategorias()){
            id_categoria=Inicio.baseDatos.daoCompras().idCategoriaPorNombre(categoria);
            y.add(new PieEntry(Inicio.baseDatos.daoCompras().gastoCategoriaPeriodo(id_categoria, primeraFecha, LocalDateTime.now().toString()),i));
            colores.add(Color.HSVToColor(new float[]{(float) ((131*i)%360), 1, 1}));
            i++;
        }

        PieDataSet conjuntoDatos=new PieDataSet(y,"Gasto categorías");
        conjuntoDatos.setSliceSpace(2);
        conjuntoDatos.setValueTextSize(12);
        conjuntoDatos.setColors(colores);
        PieData datos=new PieData(conjuntoDatos);
        grafico.setData(datos);
        grafico.invalidate();
    }
}