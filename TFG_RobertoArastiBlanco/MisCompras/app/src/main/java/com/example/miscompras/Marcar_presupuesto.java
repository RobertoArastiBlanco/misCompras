package com.example.miscompras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

public class Marcar_presupuesto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_presupuesto);
        NumberPicker np1 = findViewById(R.id.numberPicker6);
        NumberPicker np2 = findViewById(R.id.numberPicker7);
        NumberPicker np3 = findViewById(R.id.numberPicker8);
        NumberPicker np4 = findViewById(R.id.numberPicker9);
        NumberPicker np5 = findViewById(R.id.numberPicker10);

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

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("presupuesto", Context.MODE_PRIVATE);
        float valorpresupuesto=preferences.getFloat("valor", 500.0f);

        int cifra1 = (int) (valorpresupuesto/100);
        int cifra2 = (int) (valorpresupuesto/10-10*cifra1);
        int cifra3 = (int) (valorpresupuesto-100*cifra1-10*cifra2);
        int cifra4 = (int) (valorpresupuesto*10-1000*cifra1-100*cifra2-10*cifra3);
        int cifra5 = (int) (valorpresupuesto*100-10000*cifra1-1000*cifra2-100*cifra3-10*cifra4);

        np1.setValue(cifra1);
        np2.setValue(cifra2);
        np3.setValue(cifra3);
        np4.setValue(cifra4);
        np5.setValue(cifra5);
    }

    public void establecerPresupuesto(View view){

        NumberPicker np1 = findViewById(R.id.numberPicker6);
        NumberPicker np2 = findViewById(R.id.numberPicker7);
        NumberPicker np3 = findViewById(R.id.numberPicker8);
        NumberPicker np4 = findViewById(R.id.numberPicker9);
        NumberPicker np5 = findViewById(R.id.numberPicker10);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("presupuesto", Context.MODE_PRIVATE);
        float presupuesto = (float)(np1.getValue()*10000 + np2.getValue()*1000 + np3.getValue()*100 + np4.getValue()*10 + np5.getValue())/(float)100;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("valor", presupuesto);
        editor.commit();
        Toast.makeText(view.getContext(), "Nuevo presupuesto:"+String.valueOf(presupuesto)+"€", Toast.LENGTH_SHORT).show();

        /*
        int id_categoria=Inicio.baseDatos.daoCompras().idCategoriaPorNombre(spinner1.getSelectedItem().toString());
        int id_subcategoria=Inicio.baseDatos.daoCompras().idSubcategoriaPorNombre(id_categoria, spinner2.getSelectedItem().toString());
        int id_producto=Inicio.baseDatos.daoCompras().idProductoPorNombre(id_subcategoria, spinner3.getSelectedItem().toString());
        float precio = (float)(np1.getValue()*10000 + np2.getValue()*1000 + np3.getValue()*100 + np4.getValue()*10 + np5.getValue())/(float)100;
        Entrada entrada = new Entrada(id_producto, precio, LocalDateTime.now().toString());
        Inicio.baseDatos.daoCompras().guardarEntrada(entrada);
         */
    }
}