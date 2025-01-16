package com.alura.comex;
import ch.qos.logback.core.joran.sanity.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;


public class InformeSintetico {


    private String montoFormateado;
    private String pedidoMasBaratoFormateado;
    private String pedidoMasCaroFormateado;
    private AtomicInteger totalDeProductosVendidos ;
    private AtomicInteger totalDePedidosRealizados ;
    private BigDecimal montoDeVentas ;
    private Pedido pedidoMasBarato ;
    private Pedido pedidoMasCaro ;
    private AtomicInteger totalDeCategorias ;
    private BinaryOperator<BigDecimal> sumar;
    private List<Object[]> ClientesFieles;
    private List<Object[]> ventascategorias;
    private List<Object[]>Tops;
    private List<Object[]>MasCaro;
    private List<Object[]> ModaClientes;

public InformeSintetico(){
    this.ModaClientes = new ArrayList<>();
    this.MasCaro = new ArrayList<>();
    this.Tops = new ArrayList<>();
    this.ventascategorias = new ArrayList<>();
    this.montoDeVentas = BigDecimal.ZERO;
    this.totalDePedidosRealizados = new AtomicInteger(0);
    this.totalDeProductosVendidos = new AtomicInteger(0);
    this.totalDeCategorias = new AtomicInteger(0);
    this.sumar = BigDecimal::add;
    this.ClientesFieles = new ArrayList<>();
}

    public String formateoDeMontoDeVentas(){
       montoFormateado = NumberFormat.getCurrencyInstance(new Locale("es", "PA")).format(montoDeVentas.setScale(2, RoundingMode.HALF_DOWN));
      return montoFormateado;
    }

    public String formateoDePedidoMasBarato(){
       pedidoMasBaratoFormateado = NumberFormat.getCurrencyInstance(new Locale("es", "PA")).format(pedidoMasBarato.getPrecio().multiply(new BigDecimal(pedidoMasBarato.getCantidad())).setScale(2, RoundingMode.HALF_DOWN));
        return pedidoMasBaratoFormateado;
    }

    public String formateoDePedidoMasCaro(){
    this.pedidoMasCaroFormateado = NumberFormat.getCurrencyInstance(new Locale("es", "PA")).format(pedidoMasCaro.getPrecio().multiply(new BigDecimal(pedidoMasCaro.getCantidad())).setScale(2, RoundingMode.HALF_DOWN));
    return pedidoMasCaroFormateado;
    }

    public void calculoDeTotalDeProductosVendidos(int cantidad){
        this.totalDeProductosVendidos.updateAndGet(actual -> actual + cantidad);
    }


    public int getTotalDeProductosVendidos() {
        return totalDeProductosVendidos.get();
    }

    public void incrementoDePedidosRealizados(){
    totalDePedidosRealizados.incrementAndGet();
    }

    public int getTotalDePedidosRealizados() {
        return totalDePedidosRealizados.get();
    }

    public void incrementoDeCategoriasRealizadas(){
        totalDeCategorias.incrementAndGet();
    }

    public int getTotalDeCategorias() {
        return totalDeCategorias.get();
    }

    public void SumarMontosDeVentas(BigDecimal monto){
        this.montoDeVentas = sumar.apply(this.montoDeVentas , monto);
    }

    public Pedido getPedidoMasBarato() {
        return pedidoMasBarato;
    }

    public void setPedidoMasBarato(Pedido pedidoMasBarato) {
        this.pedidoMasBarato = pedidoMasBarato;
    }

    public Pedido getPedidoMasCaro() {
        return pedidoMasCaro;
    }

    public void setPedidoMasCaro(Pedido pedidoMasCaro) {
        this.pedidoMasCaro = pedidoMasCaro;
    }


    public void setClientesFieles(Pedido pedido){
     this.ClientesFieles.add(new Object[]{pedido.getCliente() , pedido.getCantidad()});

    }

    public List<Object[]> getClientesFieles() {

        Set<String> nombresClientes = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        List<Object[]> clientesFielesSinDuplicados = new ArrayList<>();

        for (Object[] cliente : ClientesFieles) {
            String nombreCliente = (String) cliente[0];

            // Manejo de nulls:  Si el nombre es null, no se considera duplicado
            if (nombreCliente == null || nombresClientes.add(nombreCliente)) {
                clientesFielesSinDuplicados.add(cliente);
            }
        }
        Collections.sort(clientesFielesSinDuplicados, Comparator.comparing(o -> (String) o[0], String.CASE_INSENSITIVE_ORDER));

        return clientesFielesSinDuplicados;
}



    public void setVentasPorCategoria(Pedido pedido){
        this.ventascategorias.add(new Object[]{pedido.getCategoria() , pedido.getCantidad() , pedido.getPrecio()});
    }


    public List<Object[]> Getventascategorias(){
        List<Object[]> resultado = new ArrayList<>();

        Map<String, Object[]> resumen = this.ventascategorias.stream()
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

    public void setTopProductos(Pedido pedido){
    this.Tops.add( new Object[]{pedido.getProducto() , pedido.getCantidad()});
    }

    public List<Object[]> GetTops(){
        return this.Tops.stream()
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


        public void setProductoMasCaro(Pedido pedido){
        this.MasCaro.add(new Object[] {pedido.getCategoria() , pedido.getProducto() , pedido.getPrecio()});
        }

    public List<Object[]> GetMasCaro() {
        // Primero agrupamos por categoría y encontramos el precio máximo en cada grupo
        return this.MasCaro.stream()
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



    public void SetMasRentables(Pedido pedido){
    this.ModaClientes.add(new Object[]{pedido.getCliente() , pedido.getCantidad() , pedido.getPrecio()});
    }

    public List<Object[]> GetModa(){

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
