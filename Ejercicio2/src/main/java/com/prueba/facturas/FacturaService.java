package com.prueba.facturas;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;

// Servicio que contiene la lógica de negocio para procesar facturas.

@Service
public class FacturaService {

    private final FacturaRepository repo;
    private final DianClient dian;

    // Inyección de dependencias por constructor
    public FacturaService(FacturaRepository repo, DianClient dian) {
        this.repo = repo;
        this.dian = dian;
    }

    //Procesa una factura:
     //1. Valida el total
     //2. Guarda como CREADA
     //3. Intenta enviarla a la DIAN con reintentos
     //4. Si falla, la guarda como PENDIENTE_ENVIO

    public Factura procesar(Factura input) {

        // Validación manual del total
        if (input.total().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Total inválido");

        // 1. Guardar primero como CREADA
        Factura guardada = new Factura(input.id(), input.total(), "CREADA");
        repo.save(guardada);

        int reintentos = 3;

        // 2. Reintentar hasta 3 veces enviar a la DIAN
        for (int i = 1; i <= reintentos; i++) {
            try {

                dian.enviar(guardada);

                // 3. Si se envía correctamente, guardar como ENVIADA
                Factura enviada = new Factura(
                        guardada.id(),
                        guardada.total(),
                        "ENVIADA"
                );

                repo.save(enviada);
                return enviada;

            } catch (Exception e) {

                // 4. Si es el último intento, se marca como pendiente
                if (i == reintentos) {

                    Factura pendiente = new Factura(
                            guardada.id(),
                            guardada.total(),
                            "PENDIENTE_ENVIO"
                    );

                    repo.save(pendiente);
                    throw new RuntimeException("503 DIAN no disponible");
                }
            }
        }

        return guardada;
    }
}
