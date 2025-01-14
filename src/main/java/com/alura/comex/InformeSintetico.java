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

public InformeSintetico(){
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







}
