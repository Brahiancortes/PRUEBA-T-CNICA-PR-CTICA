public class SharedAccount {

    // Saldo disponible
    private int saldo = 1000;

    // rtod sincronizado para retirar dinero... synchronized asegura que solo un hilo a la vez pueda modificar el saldo y evita race conditions.

    public synchronized boolean retirar(int monto) {

        // Verifica si hay saldo suficiente
        if (saldo >= monto) {

            // Descuenta el monto
            saldo -= monto;

            // Mensaje informativo
            System.out.println(Thread.currentThread().getName()
                    + " retiró $" + monto + " | Saldo: " + saldo);

            return true;

        } else {

            // No hay saldo suficiente
            System.out.println(Thread.currentThread().getName()
                    + " NO pudo retirar. Saldo: " + saldo);

            return false;
        }
    }

    // Retorna el saldo final de la cuenta

    public int getSaldo() {
        return saldo;
    }
}
