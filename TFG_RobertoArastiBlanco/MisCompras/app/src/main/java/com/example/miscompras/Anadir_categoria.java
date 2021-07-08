package com.example.miscompras;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Anadir_categoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_categoria);
    }

    public void anadirCategoria(View view){
        EditText cuadro=findViewById(R.id.NuevaCategoria);
        String nuevaCategoria=cuadro.getText().toString();
        if(cuadro.getText().length()!=0){
            if(Inicio.baseDatos.daoCompras().listaCategorias().contains(nuevaCategoria)){
                AlertDialog.Builder builder=new AlertDialog.Builder(Anadir_categoria.this);
                builder.setMessage("La categoría que quieres crear ya existe");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alerta=builder.create();
                alerta.show();
            }else{
                Inicio.baseDatos.daoCompras().guardarCategoria(new Categoria(nuevaCategoria));
                Toast.makeText(view.getContext(), nuevaCategoria + " añadido", Toast.LENGTH_SHORT).show();
            }
        }
        cuadro.getText().clear();
    }
}