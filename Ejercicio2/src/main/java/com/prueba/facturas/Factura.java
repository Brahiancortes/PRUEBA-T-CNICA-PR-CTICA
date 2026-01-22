package com.prueba.facturas;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

// Representa una factura del sistema, Se usa tanto para recibir datos del cliente como para guardar el estado del proceso.

public record Factura(

        // Identificador de la factura
        String id,

        // Total de la factura (debe ser positivo)
        @Positive(message = "El total debe ser positivo")
        BigDecimal total,

        // Estado actual: CREADA, ENVIADA o PENDIENTE_ENVIO
        String estado
) {}
