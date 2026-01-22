package com.prueba.facturas;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Repositorio simulado en memoria, ConcurrentHashMap para simular una base de datos.
@Repository
public class FacturaRepository {

    // guardar datos en memoria
    private final Map<String, Factura> db = new ConcurrentHashMap<>();

    //Guarda una factura en la base simulada.Simula una latencia de 100 ms.
    public void save(Factura factura) {

        // Simula tiempo de escritura en base de datos
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {}

        // Guarda o reemplaza la factura
        db.put(factura.id(), factura);
    }
}
