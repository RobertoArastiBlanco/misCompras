package com.example.miscompras;

public class EntradaCompleta {
    public String categoria;
    public String subcategoria;
    public String producto;
    public String precio;
    public String fecha;

    public EntradaCompleta(String categoria, String subcategoria, String producto, String precio, String fecha) {
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.producto = producto;
        this.precio = precio;
        this.fecha = fecha;
    }

    public String toString() {
        String categoriacorregida;
        String subcategoriacorregida;
        String productocorregido;

        if(categoria.contains(",")){
            categoriacorregida = "\""+categoria+"\"";
        }else{
            categoriacorregida = categoria;
        }
        if(subcategoria.contains(",")){
            subcategoriacorregida = "\""+subcategoria+"\"";
        }else{
            subcategoriacorregida = subcategoria;
        }
        if(producto.contains(",")){
            productocorregido = "\""+producto+"\"";
        }else{
            productocorregido = producto;
        }
        return categoriacorregida+","+subcategoriacorregida+","+productocorregido+","+precio+","+fecha;
    }
}
