package com.alura.comex;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;

public class CategoriasProcesadas {
    //se quita el Hashset<String> porque la abstraccion de la clase madre e hija no coinciden
    BigDecimal valorTotal;
    HashSet<String> categorias;
   public Comparator<Pedido> comparadorPorPrecioTotal ;



    public CategoriasProcesadas() {
       this.categorias = new HashSet<String>();
       this.comparadorPorPrecioTotal =  Comparator.comparing(pedido -> pedido.getPrecio().multiply(new BigDecimal(pedido.getCantidad())));
    }

    public BigDecimal getValorTotal(Pedido pedido) {
        valorTotal = pedido.getPrecio().multiply(new BigDecimal(pedido.getCantidad()));
        return valorTotal;
    }

    public boolean isMasBaratoQue(Pedido pedido ,  Pedido pedidobaratoactual) {
        return comparadorPorPrecioTotal.compare(pedido, pedidobaratoactual) < 0;
    }

    public boolean isMasCaroQue(Pedido pedido , Pedido pedidocaroactual) {
        return comparadorPorPrecioTotal.compare(pedido, pedidocaroactual) > 0;
    }

    public void agregar(String categoria){
        categorias.add(categoria);
    }

    public boolean contiene(String categoria){
        return categorias.contains(categoria);
    }



}
