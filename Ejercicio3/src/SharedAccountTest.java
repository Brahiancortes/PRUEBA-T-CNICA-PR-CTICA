import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SharedAccountTest {

    public static void main(String[] args) throws InterruptedException {

        // Cuenta compartida por todos los hilos
        SharedAccount cuenta = new SharedAccount();

        // Pool de hilos (simula múltiples personas retirando al mismo tiempo)
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Creamos 30 tareas que intentan retirar $100 cada una
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                cuenta.retirar(100);
            });
        }
        //cambiar cantidad en el ciclo for un en el ejecutador del servicio para corroborar que el mono no puede pasar de 0$

        // Indica que no se aceptan más tareas
        executor.shutdown();

        // Espera hasta que todos los hilos terminen (máx 5 segundos)
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Muestra el saldo final
        System.out.println("\nSALDO FINAL: " + cuenta.getSaldo());
    }
}
