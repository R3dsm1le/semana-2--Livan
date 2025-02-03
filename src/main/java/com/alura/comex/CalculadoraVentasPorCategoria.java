package com.alura.comex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculadoraVentasPorCategoria {


    public void setVentasPorCategoria(Pedido pedido , List<Object[]> ventascategorias){
        ventascategorias.add(new Object[]{pedido.getCategoria() , pedido.getCantidad() , pedido.getPrecio()});
    }


    public List<Object[]> Getventascategorias(List<Object[]> ventascategorias){
        List<Object[]> resultado = new ArrayList<>();

        Map<String, Object[]> resumen = ventascategorias.stream()
                .collect(Collectors.groupingBy(
                        obj -> (String) obj[0], // Agrupamos por categoría
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    String categoria = (String) list.get(0)[0];
                                    int cantidadTotal = list.stream()
                                            .mapToInt(obj -> (Integer) obj[1])
                                            .sum();
                                    BigDecimal precioTotal = list.stream()
                                            .map(obj -> (BigDecimal) obj[2])
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                                    return new Object[]{categoria, cantidadTotal, precioTotal};
                                }
                        )
                ));

        resultado.addAll(resumen.values());

        return resultado;


    }

}
