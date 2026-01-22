
Este repositorio contiene la solución a los tres ejercicios

## 1. Procesamiento de Transacciones (CSV)
- Generación de 10,000 registros simulados
- Parseo seguro
- Manejo de errores
- Top 3 usuarios con mayor gasto
- Totales por usuario

## 2. API REST de Facturas (Spring Boot)
- Endpoint POST /facturas
- Validación con Bean Validation
- Reintentos al enviar a DIAN
- Estados:
  - CREADA
  - ENVIADA
  - PENDIENTE_ENVIO
- Manejo de errores

## 3. Concurrencia: SharedAccount
- 10+ hilos retirando dinero al mismo tiempo
- Uso de synchronized
- Prevención de Race Condition
- Test con ExecutorService

