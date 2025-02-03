package com.alura.comex;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CalculadoraProductoMasCaro {

    public void setProductoMasCaro(Pedido pedido , List<Object[]> MasCaro){
        MasCaro.add(new Object[] {pedido.getCategoria() , pedido.getProducto() , pedido.getPrecio()});
    }

    public List<Object[]> GetMasCaro(List<Object[]> MasCaro) {
        // Primero agrupamos por categoría y encontramos el precio máximo en cada grupo
        return MasCaro.stream()
                .collect(Collectors.groupingBy(
                        // Key: Categoría (primer elemento del array)
                        item -> (String)item[0],
                        // Value: Collector que encuentra el elemento con precio máximo
                        Collectors.maxBy(Comparator.comparing(item -> (BigDecimal)item[2]))
                ))
                // Convertimos el Map a Stream de entries
                .entrySet().stream()
                // Ordenamos las categorías alfabéticamente
                .sorted(Map.Entry.comparingByKey())
                // Extraemos solo los valores (Optional<Object[]>)
                .map(Map.Entry::getValue)
                // Extraemos el Object[] del Optional
                .map(Optional::get)
                // Convertimos el Stream a List
                .collect(Collectors.toList());
    }


}
