package com.alejandro_bm.apiexampleliverpool;

/**
 * Created by RaiserAlex on 29/03/17 in hour 15.
 */

public class Producto {
    String titulo,
           precio,
           ubicacion,
           imagen;

    Producto(String titulo, String precio, String ubicacion, String imagen){
        this.titulo = titulo;
        this.precio = precio;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
    }
}
