package com.alura.comex;

import java.math.BigDecimal;
import java.util.HashSet;

public class CategoriasProcesadas extends HashSet<String> {


    public BigDecimal getValorTotal() {

        return null;
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

}
