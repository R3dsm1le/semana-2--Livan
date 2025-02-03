package com.alura.comex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class CalculadoraTopsTest {
    @InjectMocks
    private CalculadoraTops calculadoraTops;
    private List<Object[]> tops;

    private ProcesadorDeCsv procesadorDeCsv;
    private List<Pedido> pedidos;
    @Test
    public void debeGenerarElInformeApartirDelArchivoCSV(){
        //ARRANGE
        tops = new ArrayList<>();
        procesadorDeCsv = new ProcesadorDeCsv();
        pedidos = procesadorDeCsv.DevolverPedido();
        calculadoraTops = new CalculadoraTops();
        //ACT
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedidoActual = pedidos.get(i);
            calculadoraTops.setTopProductos(pedidoActual,tops);
        }
        //ASSSERT
        Assertions.assertNotNull(calculadoraTops.GetTops(tops));


    }
@Test
public void debeDevolverUnaListaConUnSoloElemento(){
    //ARRANGE
    pedidos = new ArrayList<>();
    tops = new ArrayList<>();
    Pedido pedido = new Pedido("Automotor", "Hyundai" , BigDecimal.valueOf(233340.67) , 56 , LocalDate.now() ,"Jose");
    pedidos.add(0 , pedido);
    calculadoraTops = new CalculadoraTops();

    //ACT
    for (int i = 0; i < pedidos.size(); i++) {
        Pedido pedidoActual = pedidos.get(i);
        calculadoraTops.setTopProductos(pedidoActual,tops);
    }
    //ASSSERT
    Assertions.assertThrows(soloExisteUnElementoException.class , () -> calculadoraTops.GetTops(tops));




}








}