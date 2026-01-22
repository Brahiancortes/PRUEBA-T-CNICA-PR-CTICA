import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        // 1. Genera datos simulados en formato CSV
        List<String> csv = generarCSV();

        // 2. Servicio que procesa las transacciones
        TransactionService service = new TransactionService();

        // 3. Parsea el CSV a objetos TransactionRecord
        List<TransactionRecord> transacciones = service.parseCsv(csv);

        // 4. Muestra cuántos registros válidos fueron procesados
        System.out.println("TOTAL REGISTROS PROCESADOS: " + transacciones.size());

        // 5. Calcula el total gastado por cada usuario
        Map<String, BigDecimal> totales = service.totalPorUsuario(transacciones);

        // 6. Imprime todas las transacciones válidas
        service.imprimirResumen(transacciones);

        // 7. Imprime el total por usuario
        service.imprimirTotales(totales);

        // 8. Obtiene el Top 3 de usuarios con mayor gasto
        var top3 = service.top3Usuarios(totales);

        System.out.println("TOP 3 USUARIOS CON MAYOR GASTO:");
        top3.forEach(e ->
                System.out.println("Usuario: " + e.getKey() + " -> $" + e.getValue())
        );

        // 9. Muestra los registros inválidos encontrados
        service.imprimirErrores(transacciones);
    }

    /**
     * Genera datos simulados en formato CSV
     * con errores intencionales al final.
     */
    private static List<String> generarCSV() {
        List<String> data = new ArrayList<>();
        Random random = new Random();

        String[] monedas = {"USD", "EUR", "COP"};
        int usuarios = 10000;

        // Genera 10,000 registros válidos
        for (int i = 1; i <= 10000; i++) {

            String id = String.valueOf(i);
            LocalDate fecha = LocalDate.now().minusDays(random.nextInt(365));
            BigDecimal monto = BigDecimal.valueOf(random.nextInt(2000) + 1);
            String moneda = monedas[random.nextInt(monedas.length)];
            String usuario = "u" + (random.nextInt(usuarios) + 1);

            data.add(id + "," + fecha + "," + monto + "," + moneda + "," + usuario);
        }

        // ERRORES INTENCIONALES para validar el manejo de excepciones
        data.add("ABC,2024-99-99,200,USD,u1");      // fecha inválida
        data.add("10001,2024-01-10,NO_NUM,USD,u2"); // monto inválido
        data.add("10002,2024-02-01,300,XXX,u3");    // moneda inválida

        return data;
    }
}
