package com.prueba.facturas;

import org.springframework.stereotype.Component;

import java.util.Random;

// Simula un servicio externo de la DIAN

@Component
public class DianClient {

    // Generador de números aleatorios para simular fallos
    private final Random random = new Random();

    //Simula el envío de una factura a la DIAN, Tarda 500 ms y falla el 20% de las veces.

    public void enviar(Factura factura) {

        // Simula latencia de red
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {}

        // Simula fallo aleatorio (20%)
        if (random.nextInt(100) < 20) {
            throw new RuntimeException("DIAN no disponible");
        }
    }
}
