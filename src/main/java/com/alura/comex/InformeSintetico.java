package com.alura.comex;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;



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


    //Operaciones a externalizar
    private SumarMontos suma;
    private CalculadoraClientesFieles calculadoraClientesFieles;
    private List<Object[]> ClientesFieles;

    private List<Object[]> ventascategorias;
    private CalculadoraVentasPorCategoria calculadoraVentasPorCategoria;


    private List<Object[]>Tops;
    private CalculadoraTops calculadoraTops;

    private List<Object[]>MasCaro;
    private CalculadoraProductoMasCaro calculadoraProductoMasCaro;

    private List<Object[]> ModaClientes;
    private CalculadoraModa calculadoraModa;



public InformeSintetico(){
    this.ClientesFieles = new ArrayList<>();
    this.ModaClientes = new ArrayList<>();
    this.MasCaro = new ArrayList<>();
    this.Tops = new ArrayList<>();
    this.ventascategorias = new ArrayList<>();
    this.montoDeVentas = BigDecimal.ZERO;
    this.totalDePedidosRealizados = new AtomicInteger(0);
    this.totalDeProductosVendidos = new AtomicInteger(0);
    this.totalDeCategorias = new AtomicInteger(0);
    suma = new SumarMontos();
    calculadoraClientesFieles = new CalculadoraClientesFieles();
    calculadoraVentasPorCategoria = new CalculadoraVentasPorCategoria();
    calculadoraTops = new CalculadoraTops();
    calculadoraProductoMasCaro = new CalculadoraProductoMasCaro();
    calculadoraModa = new CalculadoraModa();
}
    //Metodos exteriorizados-------------------------------------------------
    public void sumarMontos(BigDecimal monto){
    suma.SumarMontosDeVentas(monto , this.montoDeVentas);
    }

    public void setClientesFieles(Pedido pedido){
        calculadoraClientesFieles.setClientesFieles(pedido , this.ClientesFieles);
    }

    public List<Object[]> getClientesFieles() {
        return calculadoraClientesFieles.getClientesFieles(this.ClientesFieles);
    }

    public void setVentasPorCategoria(Pedido pedido){
        calculadoraVentasPorCategoria.setVentasPorCategoria(pedido , this.ventascategorias);
    }

    public List<Object[]> Getventascategorias(){
        return calculadoraVentasPorCategoria.Getventascategorias(this.ventascategorias);
    }


    public void setTopProductos(Pedido pedido){
        calculadoraTops.setTopProductos(pedido , this.Tops);
    }

    public List<Object[]> GetTops(){
        return calculadoraTops.GetTops(this.Tops);
    }



    public void setProductoMasCaro(Pedido pedido){
        calculadoraProductoMasCaro.setProductoMasCaro(pedido , this.MasCaro);
    }

    public List<Object[]> GetMasCaro() {
        return calculadoraProductoMasCaro.GetMasCaro(this.MasCaro);
    }


    public void SetMasRentables(Pedido pedido){
        calculadoraModa.SetMasRentables(pedido , this.ModaClientes);
    }

    public List<Object[]> GetModa(){
        return calculadoraModa.GetModa(this.ModaClientes);
    }

    //--------------------------------------------------------------


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
