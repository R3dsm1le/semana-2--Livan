package com.alura.comex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.List;


class CalculadoraVentasPorCategoriaTest {

    @InjectMocks
    private CalculadoraVentasPorCategoria calculadoraVentasPorCategoria;

    private List<Object[]> ventascategorias;

    private ProcesadorDeCsv procesadorDeCsv;
    private List<Pedido> pedidos;
    @Test
   public void debeGenerarElInformeApartirDelArchivoCSV(){
      //ARRANGE
       ventascategorias = new ArrayList<>();
       procesadorDeCsv = new ProcesadorDeCsv();
       pedidos = procesadorDeCsv.DevolverPedido();
       calculadoraVentasPorCategoria = new CalculadoraVentasPorCategoria();
       //ACT
       for (int i = 0; i < pedidos.size(); i++) {
           Pedido pedidoActual = pedidos.get(i);
           calculadoraVentasPorCategoria.setVentasPorCategoria(pedidoActual,ventascategorias);
       }
        //ASSSERT
        Assertions.assertNotNull(calculadoraVentasPorCategoria.Getventascategorias(ventascategorias));


   }

@Test
   public void noDebeGenerarElInforme(){
//ARRANGE
       ventascategorias = new ArrayList<>();
       procesadorDeCsv = new ProcesadorDeCsv();
       pedidos = List.of();
       calculadoraVentasPorCategoria = new CalculadoraVentasPorCategoria();
       //ACT
       for (int i = 0; i < pedidos.size(); i++) {
           Pedido pedidoActual = pedidos.get(i);
           calculadoraVentasPorCategoria.setVentasPorCategoria(pedidoActual,ventascategorias);
       }
       //ASSSERT
       Assertions.assertThrows(noExisteArchivoException.class , () -> calculadoraVentasPorCategoria.Getventascategorias(ventascategorias));


   }

}