package com.alura.comex;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcesadorDeXML implements ProcesadorStrategy{
    private List<Pedido> pedidos;

    public ProcesadorDeXML(){
        this.pedidos = new ArrayList<>();
    }

    @Override
    public ArrayList<Pedido> DevolverPedido() {
        XmlMapper xmlMapper = new XmlMapper();

        try {
            xmlMapper.registerModule(new JavaTimeModule());
            pedidos = xmlMapper.readValue(ClassLoader.getSystemResourceAsStream("pedidos.xml") ,new TypeReference<List<Pedido>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (ArrayList<Pedido>) pedidos;
    }
}
