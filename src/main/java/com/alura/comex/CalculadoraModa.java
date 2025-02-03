package com.alura.comex;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CalculadoraModa {

    public void SetMasRentables(Pedido pedido , List<Object[]> ModaClientes){
        ModaClientes.add(new Object[]{pedido.getCliente() , pedido.getCantidad() , pedido.getPrecio()});
    }

    public List<Object[]> GetModa(List<Object[]> ModaClientes){

        return ModaClientes.stream()
                .collect(Collectors.groupingBy(
                        arr -> (String)arr[0],
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                lista -> new Object[]{
                                        lista.get(0)[0], // cliente
                                        (long)lista.size(), // contador de pedidos
                                        lista.stream()
                                                .map(arr -> {
                                                    BigDecimal cantidad = BigDecimal.valueOf(((Number)arr[1]).longValue());
                                                    BigDecimal precio = (BigDecimal)arr[2];
                                                    return cantidad.multiply(precio);
                                                })
                                                .reduce(BigDecimal.ZERO, BigDecimal::add) // suma de multiplicaciones
                                }
                        )
                ))
                .values()
                .stream()
                // Ordenamos por total (posición 2) descendente y tomamos los primeros 2
                .sorted((a, b) ->
                        ((BigDecimal)b[2]).compareTo((BigDecimal)a[2])
                )
                .limit(2)
                // Ordenamos alfabéticamente por cliente
                .sorted((a, b) ->
                        ((String)a[0]).compareTo((String)b[0])
                )
                .collect(Collectors.toList());

    }







}
