package com.prueba.facturas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Clase principal que arranca la aplicación Spring Boot.
@SpringBootApplication
public class FacturasApplication {

    // metodo main que inicia el servidor embebidoy carga todo el contexto de Spring.
    public static void main(String[] args) {
        SpringApplication.run(FacturasApplication.class, args);
    }
}
