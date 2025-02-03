package com.alura.comex;

import java.util.*;

public class CalculadoraClientesFieles {




    public void setClientesFieles(Pedido pedido , List<Object[]>  ClientesFieles){
        ClientesFieles.add(new Object[]{pedido.getCliente() , pedido.getCantidad()});
    }


    public List<Object[]> getClientesFieles(List<Object[]>  ClientesFieles) {
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
