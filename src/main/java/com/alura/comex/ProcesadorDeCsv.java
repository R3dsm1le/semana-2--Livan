package com.alura.comex;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProcesadorDeCsv {

    public ArrayList<Pedido> DevolverPedido() {
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try (InputStreamReader inputReader = new InputStreamReader(
                ClassLoader.getSystemResourceAsStream("pedidos.csv"));
             CSVReader csvReader = new CSVReaderBuilder(inputReader)
                     .withSkipLines(1) // Salta la primera línea (encabezados)
                     .build()) {

            List<String[]> registros = csvReader.readAll();

            for (String[] registro : registros) {
                pedidos.add(crearPedido(registro));
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo pedidos.csv", e);
        } catch (CsvException e) {
            throw new RuntimeException("Error al procesar el archivo CSV", e);
        }

        return pedidos;
    }

    private Pedido crearPedido(String[] registro) {
        return new Pedido(
                registro[0],  // categoria
                registro[1],  // producto
                registro[5],  // cliente
                new BigDecimal(registro[2]),  // precio
                Integer.parseInt(registro[3]),  // cantidad
                LocalDate.parse(registro[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"))  // fecha
        );
    }

}
