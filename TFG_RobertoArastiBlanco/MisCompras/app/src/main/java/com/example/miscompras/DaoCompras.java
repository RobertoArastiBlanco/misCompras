package com.example.miscompras;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoCompras {

    @Query("SELECT id FROM categorias WHERE categoria = :categoria")
    public int idCategoriaPorNombre(String categoria);

    @Query("SELECT id FROM subcategorias WHERE id_categoria = :id_categoria AND subcategoria = :subcategoria")
    public int idSubcategoriaPorNombre(int id_categoria, String subcategoria);

    @Query("SELECT id FROM productos WHERE id_subcategoria = :id_subcategoria AND producto = :producto")
    public int idProductoPorNombre(int id_subcategoria, String producto);

    @Query("SELECT categoria FROM categorias ORDER BY categoria")
    public List<String> listaCategorias();

    @Query("SELECT subcategoria FROM subcategorias")
    public List<String> listaSubcategoriasCompleta();

    @Query("SELECT subcategoria FROM subcategorias WHERE id_categoria = :id_categoria ORDER BY subcategoria")
    public List<String> listaSubcategorias(int id_categoria);

    @Query("SELECT producto FROM productos")
    public List<String> listaProductosCompleta();

    @Query("SELECT producto FROM productos WHERE id_subcategoria = :id_subcategoria ORDER BY producto")
    public List<String> listaProductos(int id_subcategoria);

    @Query("SELECT categorias.categoria,subcategorias.subcategoria,productos.producto\n" +
            ",entradas.precio,entradas.fecha\n" +
            "FROM entradas LEFT JOIN productos ON productos.id = entradas.id_producto \n" +
            "LEFT JOIN subcategorias ON productos.id_subcategoria = subcategorias.id\n" +
            "LEFT JOIN categorias ON subcategorias.id_categoria = categorias.id")
    public List<EntradaCompleta> listaEntradas();

    @Query("SELECT precio\n" +
            "FROM entradas\n" +
            "WHERE id_producto = :id_producto AND fecha = (select max(fecha)\n" +
            "    from entradas\n" +
            "    where id_producto = :id_producto\n" +
            "    group by id_producto)")
    public float ultimoPrecioProducto(int id_producto);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void guardarEntrada(Entrada entrada);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void guardarCategoria(Categoria categoria);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void guardarSubcategoria(Subcategoria subcategoria);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void guardarProducto(Producto producto);


    @Query("SELECT AVG(precio)\n" +
            "FROM entradas\n" +
            "WHERE id_producto = :id_producto \n" +
            "GROUP BY id_producto")
    public float precioMedioProducto(int id_producto);

    @Query("SELECT SUM(precio)\n" +
            "FROM entradas LEFT JOIN productos ON productos.id = entradas.id_producto\n" +
            "LEFT JOIN subcategorias on productos.id_subcategoria = subcategorias.id\n" +
            "WHERE id_categoria = :id_categoria AND fecha > :fechaIni AND fecha < :fechaFin \n")
    public float gastoCategoriaPeriodo(int id_categoria, String fechaIni, String fechaFin);

    @Query("SELECT SUM(precio)\n" +
            "FROM entradas LEFT JOIN productos ON productos.id = entradas.id_producto\n" +
            "WHERE id_subcategoria = :id_subcategoria AND fecha > :fechaIni AND fecha < :fechaFin \n")
    public float gastoSubcategoriaPeriodo(int id_subcategoria, String fechaIni, String fechaFin);

    @Query("SELECT SUM(precio)\n" +
            "FROM entradas\n" +
            "WHERE id_producto = :id_producto\n" +
            "AND fecha > :fechaIni AND fecha < :fechaFin")
    public float gastoProductoPeriodo(int id_producto, String fechaIni, String fechaFin);

    @Query("SELECT SUM(precio)\n" +
            "FROM entradas\n" +
            "WHERE fecha > :fechaIni AND fecha < :fechaFin")
    public float gastoTotalPeriodo(String fechaIni, String fechaFin);

    @Query("SELECT SUM(precio)\n" +
            "FROM entradas\n")
    public float gastoTotal();

    @Query("SELECT MIN(fecha)\n" +
            "FROM entradas\n")
    public String fechaMasAntigua();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void guardarListaEntradas(List<Entrada> entrada);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void guardarListaCategorias(List<Categoria> categoria);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void guardarListaSubcategorias(List<Subcategoria> subcategoria);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void guardarListaProductos(List<Producto> producto);

    @Query("DELETE FROM entradas WHERE id_producto=:idProducto AND precio = :precio AND fecha=:fecha")
    public void borrarEntrada(int idProducto, float precio, String fecha);

    @Query("DELETE FROM categorias WHERE id =:id")
    public void borrarCategoria(int id);

    @Query("DELETE FROM subcategorias WHERE id =:id")
    public void borrarSubcategoria(int id);

    @Query("DELETE FROM productos WHERE id =:id")
    public void borrarProducto(int id);

    @Query("UPDATE subcategorias SET id_categoria = :idCategoria WHERE id =:idSubcategoria")
    public void moverSubcategoria(int idSubcategoria, int idCategoria);

    @Query("UPDATE productos SET id_subcategoria = :idSubcategoria WHERE id =:idProducto")
    public void moverProducto(int idProducto, int idSubcategoria);
}

