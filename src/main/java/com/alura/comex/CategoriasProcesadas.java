package com.alura.comex;

import java.math.BigDecimal;
import java.util.HashSet;

public class CategoriasProcesadas {
    //se quita el Hashset<String> porque la abstraccion de la clase madre e hija no coinciden
    BigDecimal valorTotal;
    HashSet<String> categorias;


    public CategoriasProcesadas() {
       this.categorias = new HashSet<String>();
    }

    public BigDecimal getValorTotal(Pedido pedido) {
        valorTotal = pedido.getPrecio().multiply(new BigDecimal(pedido.getCantidad()));
        return valorTotal;
    }

    public boolean isMasBaratoQue(Pedido pedido ,  Pedido pedidobaratoactual) {
        if (pedido.getPrecio().multiply(new BigDecimal(pedido.getCantidad())).compareTo(pedidobaratoactual.getPrecio().multiply(new BigDecimal(pedidobaratoactual.getCantidad()))) < 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isMasCaroQue(Pedido pedido , Pedido pedidocaroactual) {
        if (pedido.getPrecio().multiply(new BigDecimal(pedido.getCantidad())).compareTo(pedidocaroactual.getPrecio().multiply(new BigDecimal(pedidocaroactual.getCantidad()))) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void agregar(String categoria){
        categorias.add(categoria);
    }

    public boolean contiene(String categoria){
        return categorias.contains(categoria);
    }



}
