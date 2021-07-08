package com.example.miscompras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void guardarDatos(View view){
        Intent intent = new Intent(this, Guardar_datos.class);
        startActivity(intent);
    }

    public void generarEstadisticas(View view){
        if(Inicio.baseDatos.daoCompras().listaEntradas().size() == 0){
            Toast.makeText(view.getContext(), "Introduzca alguna entrada de gasto", Toast.LENGTH_SHORT).show();
        }else{
        Intent intent = new Intent(this, Generar_estadisticas.class);
        startActivity(intent);
        }
    }

    public void exportarDatos(View view){
        Intent intent = new Intent(this, Exportar_datos.class);
        startActivity(intent);
    }

    public void marcarPresupuesto(View view){
        Intent intent = new Intent(this, Marcar_presupuesto.class);
        startActivity(intent);
    }

    public void borrarElementos(View view){
        Intent intent = new Intent(this, Borrar_elementos.class);
        startActivity(intent);
    }

    public void moverElementos(View view){
        Intent intent = new Intent(this, Mover_elementos.class);
        startActivity(intent);
    }
}