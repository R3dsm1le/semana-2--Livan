package com.alura.comex;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class InformeSintetico {


    private String montoFormateado;
    private String pedidoMasBaratoFormateado;
    private String pedidoMasCaroFormateado;


    public String formateoDeMontoDeVentas(BigDecimal monto){
       montoFormateado = NumberFormat.getCurrencyInstance(new Locale("es", "PA")).format(monto.setScale(2, RoundingMode.HALF_DOWN));
      return montoFormateado;
    }

    public String formateoDePedidoMasBarato(Pedido pedido){
       pedidoMasBaratoFormateado = NumberFormat.getCurrencyInstance(new Locale("es", "PA")).format(pedido.getPrecio().multiply(new BigDecimal(pedido.getCantidad())).setScale(2, RoundingMode.HALF_DOWN));
        return pedidoMasBaratoFormateado;
    }

    public String formateoDePedidoMasCaro(Pedido pedido){
    this.pedidoMasCaroFormateado = NumberFormat.getCurrencyInstance(new Locale("es", "PA")).format(pedido.getPrecio().multiply(new BigDecimal(pedido.getCantidad())).setScale(2, RoundingMode.HALF_DOWN));
    return pedidoMasCaroFormateado;
    }



}
