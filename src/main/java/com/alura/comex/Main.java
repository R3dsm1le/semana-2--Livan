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
        CategoriasProcesadas categoriasProcesadas = new CategoriasProcesadas();


        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedidoActual = pedidos.get(i);

            if (pedidoActual == null) {
                break;
            }

            if (informe.getPedidoMasBarato() == null || categoriasProcesadas.isMasBaratoQue(pedidoActual , informe.getPedidoMasBarato() ) ) {
                informe.setPedidoMasBarato(pedidoActual);
            }

            if (informe.getPedidoMasCaro() == null || categoriasProcesadas.isMasCaroQue(pedidoActual , informe.getPedidoMasCaro() )) {
                informe.setPedidoMasCaro(pedidoActual);
            }

            informe.SumarMontosDeVentas(categoriasProcesadas.getValorTotal(pedidoActual));
            informe.calculoDeTotalDeProductosVendidos(pedidoActual.getCantidad());
            informe.incrementoDePedidosRealizados();
            informe.setClientesFieles(pedidoActual);
            informe.setVentasPorCategoria(pedidoActual);
            informe.setTopProductos(pedidoActual);

            if (!categoriasProcesadas.contiene(pedidoActual.getCategoria())) {
              informe.incrementoDeCategoriasRealizadas();
              categoriasProcesadas.agregar(pedidoActual.getCategoria());
            }


        }

        System.out.println("#### INFORME DE VALORES TOTALES");
        System.out.printf("- TOTAL DE PEDIDOS REALIZADOS: %s\n", informe.getTotalDePedidosRealizados());
        System.out.printf("- TOTAL DE PRODUCTOS VENDIDOS: %s\n", informe.getTotalDeProductosVendidos());
        System.out.printf("- TOTAL DE CATEGORIAS: %s\n", informe.getTotalDeCategorias());
        System.out.printf("- MONTO DE VENTAS: %s\n", informe.formateoDeMontoDeVentas()); //Pueden cambiar el Locale a la moneda de su pais, siguiendo esta documentación: https://www.oracle.com/java/technologies/javase/java8locales.html
        System.out.printf("- PEDIDO MAS BARATO: %s (%s)\n", informe.formateoDePedidoMasBarato(), informe.getPedidoMasBarato().getProducto());
        System.out.printf("- PEDIDO MAS CARO: %s (%s)\n", informe.formateoDePedidoMasCaro(), informe.getPedidoMasCaro().getProducto());

        System.out.println("\n#### INFORME DE CLIENTES FIELES");


        for (Object[] cliente : informe.getClientesFieles()){
            System.out.println("Cliente:" + cliente[0] +  "\nNro pedidos : " + cliente[1] + "\n");
        }
System.out.println("size :" + informe.getClientesFieles().size());



        // Para imprimir los resultados
        for (Object[] dato : informe.Getventascategorias()) {
            System.out.printf("\nCATEGORIA: %s, \nCANTIDAD VENDIDA: %d, \nMONTO: %s%n",
                    dato[0], dato[1], ((BigDecimal)dato[2]).toString());
        }



        System.out.println("\n#### TOP MAS VENDIDO");


        for(Object[] resultado : informe.GetTops()) {
            System.out.println("\nProducto: " + resultado[0] + ", \nCantidad: " + resultado[1]);
        }




    }







}
