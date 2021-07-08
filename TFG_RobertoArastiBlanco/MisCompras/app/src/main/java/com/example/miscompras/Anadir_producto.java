package com.example.miscompras;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Anadir_producto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_producto);
        TextView texto=findViewById(R.id.textViewAP);
        texto.setText("Introduce los productos que quieras añadir a " + getIntent().getCharSequenceExtra("Subcategoria") + " de " + getIntent().getCharSequenceExtra("Categoria") +".");
    }

    public void anadirProducto(View view){
        EditText cuadro=findViewById(R.id.NuevoProducto);
        String nuevoProducto=cuadro.getText().toString();
        if(cuadro.getText().length()!=0){
            if(Inicio.baseDatos.daoCompras().listaProductosCompleta().contains(nuevoProducto)){
                AlertDialog.Builder builder=new AlertDialog.Builder(Anadir_producto.this);
                builder.setCancelable(false);
                if(Inicio.baseDatos.daoCompras().listaProductos(getIntent().getIntExtra("id", 0)).contains(nuevoProducto)){
                    builder.setMessage("El producto que quieres crear ya existe en esta subcategoría");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                        }
                    });
                }else{
                    builder.setMessage("Ya existe un producto con ese nombre en otra subcategoría\n"+
                            "¿Estas seguro de lo que estas haciendo?");
                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Inicio.baseDatos.daoCompras().guardarProducto(new Producto(getIntent().getIntExtra("id", 0), nuevoProducto));
                            Toast.makeText(view.getContext(), nuevoProducto + " añadido", Toast.LENGTH_SHORT).show();
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
                Inicio.baseDatos.daoCompras().guardarProducto(new Producto(getIntent().getIntExtra("id", 0), nuevoProducto));
                Toast.makeText(view.getContext(), nuevoProducto + " añadido", Toast.LENGTH_SHORT).show();
            }
        }
        cuadro.getText().clear();
    }
}