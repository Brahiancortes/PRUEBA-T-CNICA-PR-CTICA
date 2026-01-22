import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionService {

    public List<TransactionRecord> parseCsv(List<String> lineas) {

        return lineas.stream()
                .map(this::parseLinea)
                .toList();
    }

    private TransactionRecord parseLinea(String linea) {

        try {
            String[] p = linea.split(",");

            if (p.length != 5)
                return new TransactionRecord(null, null, null, null, null,
                        false, "Columnas incompletas -> " + linea);

            String id = p[0];
            LocalDate fecha = LocalDate.parse(p[1]);
            BigDecimal monto = new BigDecimal(p[2]);
            String moneda = p[3];
            String usuario = p[4];

            return new TransactionRecord(id, fecha, monto, moneda, usuario, true, null);

        } catch (Exception e) {
            return new TransactionRecord(null, null, null, null, null,
                    false, e.getMessage() + " -> " + linea);
        }
    }


    public Map<String, BigDecimal> totalPorUsuario(List<TransactionRecord> transacciones) {
        return transacciones.stream()
                .filter(TransactionRecord::valido) // solo válidos
                .filter(t -> "USD".equals(t.moneda())) // null-safe
                .filter(t -> t.monto().compareTo(BigDecimal.valueOf(100)) > 0)
                .collect(Collectors.groupingBy(
                        TransactionRecord::usuarioId,
                        Collectors.reducing(BigDecimal.ZERO,
                                TransactionRecord::monto,
                                BigDecimal::add)
                ));
    }


    public List<Map.Entry<String, BigDecimal>> top3Usuarios(Map<String, BigDecimal> totales) {
        return totales.entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .limit(3)
                .toList();
    }

    public void imprimirErrores(List<TransactionRecord> list) {
        System.out.println(" REGISTROS INVÁLIDOS:");
        list.stream()
                .filter(t -> !t.valido())
                .forEach(t -> System.out.println(t.error()));
    }



    public void imprimirResumen(List<TransactionRecord> transacciones) {

        System.out.println(" RESUMEN GENERAL");
        System.out.println("Total transacciones: " + transacciones.size());

        long validas = transacciones.stream()
                .filter(TransactionRecord::valido)
                .count();

        long mayores100USD = transacciones.stream()
                .filter(TransactionRecord::valido)
                .filter(t -> "USD".equals(t.moneda()))
                .filter(t -> t.monto().compareTo(BigDecimal.valueOf(100)) > 0)
                .count();

        System.out.println("Válidas: " + validas);
        System.out.println("Transacciones > 100 USD: " + mayores100USD);
    }

    public void imprimirTotales(Map<String, BigDecimal> totales) {
        System.out.println(" TOTAL POR USUARIO (USD > 100):");
        totales.forEach((u, m) ->
                System.out.println("Usuario " + u + " -> $" + m)
        );
    }

}
