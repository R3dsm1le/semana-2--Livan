package com.alura.comex;

import java.util.List;
import java.util.stream.Collectors;

public class CalculadoraTops {


    public void setTopProductos(Pedido pedido, List<Object[]> Tops){
        Tops.add( new Object[]{pedido.getProducto() , pedido.getCantidad()});
    }

    public List<Object[]> GetTops( List<Object[]> Tops){
        return Tops.stream()
                // Agrupamos por producto (primer elemento del array) y sumamos cantidades
                .collect(Collectors.groupingBy(
                        arr -> arr[0],  // Agrupamos por producto
                        Collectors.summingInt(arr -> (Integer)arr[1])  // Sumamos cantidades
                ))
                // Convertimos el mapa a stream de Object[]
                .entrySet().stream()
                // Ordenamos por cantidad (segundo elemento) en orden descendente
                .sorted((e1, e2) -> ((Integer)e2.getValue()).compareTo((Integer)e1.getValue()))
                // Tomamos los primeros 3
                .limit(3)
                // Convertimos cada entrada a Object[]
                .map(entry -> new Object[]{entry.getKey(), entry.getValue()})
                // Recolectamos en una lista
                .collect(Collectors.toList());
    }


}
