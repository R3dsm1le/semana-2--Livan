package com.alura.comex;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class InformeSintetico {


    private String montoFormateado;
    private String pedidoMasBaratoFormateado;
    private String pedidoMasCaroFormateado;
    private int totalDeProductosVendidos ;
    private AtomicInteger totalDePedidosRealizados ;
    private BigDecimal montoDeVentas ;
    private Pedido pedidoMasBarato ;
    private Pedido pedidoMasCaro ;
    private int totalDeCategorias ;



public InformeSintetico(){

    this.montoDeVentas = BigDecimal.ZERO;
    this.totalDePedidosRealizados = new AtomicInteger(0);
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
        this.totalDeProductosVendidos = totalDeProductosVendidos + cantidad;
    }

    public int getTotalDeProductosVendidos() {
        return totalDeProductosVendidos;
    }

    public void incrementoDePedidosRealizados(){
    totalDePedidosRealizados.incrementAndGet();
    }

    public int getTotalDePedidosRealizados() {
        return totalDePedidosRealizados.get();
    }

    public void incrementoDeCategoriasRealizadas(){
        totalDeCategorias = totalDeCategorias + 1;
    }

    public int getTotalDeCategorias() {
        return totalDeCategorias;
    }

    public void SumarMontosDeVentas(BigDecimal monto){
        this.montoDeVentas = montoDeVentas.add(monto);
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











}
