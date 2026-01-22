package com.prueba.facturas;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Controlador REST que expone el endpoint para crear facturas.

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    // Servicio que contiene la lógica de negocio
    private final FacturaService service;

    // Inyección por constructor
    public FacturaController(FacturaService service) {
        this.service = service;
    }

    // Endpoint POST para crear una factura, Valida el cuerpo con @Valid.
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody @Valid Factura factura) {

        try {
            // Procesa la factura
            Factura result = service.procesar(factura);
            return ResponseEntity.ok(result);

        } catch (IllegalArgumentException e) {
            // Error de validación
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (RuntimeException e) {
            // Error externo (DIAN)
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("DIAN no disponible, factura guardada como PENDIENTE");
        }
    }
}
