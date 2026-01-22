import java.math.BigDecimal;
import java.time.LocalDate;

// Representa una transacción procesada desde el CSV.Puede ser válida o inválida, dependiendo de si pasó las validaciones.

public record TransactionRecord(

        // ID de la transacción
        String id,

        // Fecha de la transacción
        LocalDate fecha,

        // Monto de la transacción
        BigDecimal monto,

        // Moneda (USD, EUR, COP)
        String moneda,

        // Usuario que realizó la transacción
        String usuarioId,

        // Indica si la transacción es válida o no
        boolean valido,

        // Mensaje de error si la transacción es inválida
        String error
) {}
