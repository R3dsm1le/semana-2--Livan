package com.alura.comex;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ProcesadorDeJson implements ProcesadorStrategy {
    private List<Pedido> pedidos;

public ProcesadorDeJson(){
    this.pedidos = new ArrayList<>();
}
    @Override
    public ArrayList<Pedido> DevolverPedido() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.registerModule(new JavaTimeModule());
             pedidos =  objectMapper.readValue(ClassLoader.getSystemResourceAsStream("pedidos.json"), new TypeReference <List<Pedido>>() {
            });
            } catch (StreamReadException ex) {
            throw new RuntimeException(ex);
        } catch (DatabindException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return (ArrayList<Pedido>) pedidos;
}


}
