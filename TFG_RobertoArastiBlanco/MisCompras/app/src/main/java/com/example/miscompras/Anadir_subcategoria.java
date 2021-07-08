package com.example.miscompras;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Anadir_subcategoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_subcategoria);
        TextView texto=findViewById(R.id.textViewAS);
        texto.setText("Introduce las subcategorias que quieras añadir a " + getIntent().getCharSequenceExtra("Categoria") + ".");
    }

    public void anadirSubcategoria(View view){
        EditText cuadro=findViewById(R.id.NuevaSubcategoria);
        String nuevaSubcategoria=cuadro.getText().toString();
        if(cuadro.getText().length()!=0){
            if(Inicio.baseDatos.daoCompras().listaSubcategoriasCompleta().contains(nuevaSubcategoria)){
                AlertDialog.Builder builder=new AlertDialog.Builder(Anadir_subcategoria.this);
                builder.setCancelable(false);
                if(Inicio.baseDatos.daoCompras().listaSubcategorias(getIntent().getIntExtra("id", 0)).contains(nuevaSubcategoria)){
                    builder.setMessage("La subcategoría que quieres crear ya existe en esta categoría");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                        }
                    });
                }else{
                    builder.setMessage("Ya existe una subcategoría con ese nombre en otra categoría\n"+
                            "¿Estas seguro de lo que estas haciendo?");
                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Inicio.baseDatos.daoCompras().guardarSubcategoria(new Subcategoria(getIntent().getIntExtra("id", 0), nuevaSubcategoria));
                            Toast.makeText(view.getContext(), nuevaSubcategoria + " añadido", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }
                AlertDialog alerta=builder.create();
                alerta.show();
            }else{
                Inicio.baseDatos.daoCompras().guardarSubcategoria(new Subcategoria(getIntent().getIntExtra("id", 0), nuevaSubcategoria));
                Toast.makeText(view.getContext(), nuevaSubcategoria + " añadido", Toast.LENGTH_SHORT).show();
            }
        }
        cuadro.getText().clear();
    }
}