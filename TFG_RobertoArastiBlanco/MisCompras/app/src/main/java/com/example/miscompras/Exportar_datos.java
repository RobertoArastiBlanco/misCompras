package com.example.miscompras;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Exportar_datos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportar_datos);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void exportar(View view) throws IOException {
        String nombreArchivo=(LocalDateTime.now().toString()+".csv").replaceAll(":","_");
        File archivo = new File(getExternalFilesDir(null), nombreArchivo);
        FileOutputStream outputStream=new FileOutputStream(archivo);
        List<String> entradas= new ArrayList<String>();
        List<EntradaCompleta> entradasCompletas= Inicio.baseDatos.daoCompras().listaEntradas();
        for(EntradaCompleta entrada: entradasCompletas){
            entradas.add(entrada.toString());
        }
        String contenido="Categoria,Subcategoria,Producto,Precio,Fecha\n";
        for(String str: entradas) {
            contenido=contenido+str+System.lineSeparator();
        }
        outputStream.write(contenido.getBytes());
        outputStream.close();
        Toast.makeText(view.getContext(), "Guardado en "+archivo.getAbsolutePath(), Toast.LENGTH_SHORT).show();
    }
}