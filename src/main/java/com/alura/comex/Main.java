package com.alura.comex;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        ProcesadorDeCsv procesador = new ProcesadorDeCsv();
        ArrayList<Pedido> pedidos = procesador.DevolverPedido();
        InformeSintetico informe = new InformeSintetico();


        int totalDeProductosVendidos = 0;
        int totalDePedidosRealizados = 0;
        BigDecimal montoDeVentas = BigDecimal.ZERO;
        Pedido pedidoMasBarato = null;
        Pedido pedidoMasCaro = null;

        CategoriasProcesadas categoriasProcesadas = new CategoriasProcesadas();
        int totalDeCategorias = 0;

        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedidoActual = pedidos.get(i);

            if (pedidoActual == null) {
                break;
            }

            if (pedidoMasBarato == null || categoriasProcesadas.isMasBaratoQue(pedidoActual , pedidoMasBarato ) ) {
                pedidoMasBarato = pedidoActual;
            }

            if (pedidoMasCaro == null || categoriasProcesadas.isMasCaroQue(pedidoActual , pedidoMasCaro )) {
                pedidoMasCaro = pedidoActual;
            }

            montoDeVentas = montoDeVentas.add(categoriasProcesadas.getValorTotal(pedidoActual));
            totalDeProductosVendidos += pedidoActual.getCantidad();
            totalDePedidosRealizados++;

            if (!categoriasProcesadas.contains(pedidoActual.getCategoria())) {
              totalDeCategorias++;
              categoriasProcesadas.add(pedidoActual.getCategoria());
            }
        }

        System.out.println("#### INFORME DE VALORES TOTALES");
        System.out.printf("- TOTAL DE PEDIDOS REALIZADOS: %s\n", totalDePedidosRealizados);
        System.out.printf("- TOTAL DE PRODUCTOS VENDIDOS: %s\n", totalDeProductosVendidos);
        System.out.printf("- TOTAL DE CATEGORIAS: %s\n", totalDeCategorias);
        System.out.printf("- MONTO DE VENTAS: %s\n", informe.formateoDeMontoDeVentas(montoDeVentas)); //Pueden cambiar el Locale a la moneda de su pais, siguiendo esta documentación: https://www.oracle.com/java/technologies/javase/java8locales.html
        System.out.printf("- PEDIDO MAS BARATO: %s (%s)\n", informe.formateoDePedidoMasBarato(pedidoMasBarato), pedidoMasBarato.getProducto());
        System.out.printf("- PEDIDO MAS CARO: %s (%s)\n", informe.formateoDePedidoMasCaro(pedidoMasCaro), pedidoMasCaro.getProducto());
    }


}
