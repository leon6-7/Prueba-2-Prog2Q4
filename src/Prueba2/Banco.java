package Prueba2;
//Aquie esta todo lo recursivo
import Prueba2.CuentaBancaria;

public class Banco {

    private CuentaBancaria[] cuentas = new CuentaBancaria[20];
    private int cantidadCuentas = 0;

    public void agregarCuenta(CuentaBancaria c) {
        if (c == null) {
            throw new IllegalArgumentException("La cuenta no puede ser nula");
        }
        if (cantidadCuentas >= cuentas.length) {
            throw new IllegalStateException("El banco no admite mas cuentas (maximo " + cuentas.length + ")");
        }
        for (int i = 0; i < cantidadCuentas; i++) {
            if (cuentas[i].getNumero() == c.getNumero()) {
                throw new IllegalArgumentException("Ya existe una cuenta con el numero " + c.getNumero());
            }
        }
        cuentas[cantidadCuentas] = c;
        cantidadCuentas++;
    }

    public CuentaBancaria buscarPorNumero(int numero) {
        return buscarPorNumero(numero, 0);
    }

    private CuentaBancaria buscarPorNumero(int numero, int indice) {
        if (indice >= cantidadCuentas) {
            throw new IllegalArgumentException("No existe una cuenta con el numero " + numero);
        }
        if (cuentas[indice].getNumero() == numero) {
            return cuentas[indice];
        }
        return buscarPorNumero(numero, indice + 1);
    }

    public void ordenarPorSaldo() {
        ordenarDesde(0);
    }

    private void ordenarDesde(int inicio) {
        if (inicio >= cantidadCuentas - 1) {
            return;
        }
        int menor = indiceDelMenor(inicio, inicio + 1);
        CuentaBancaria temp = cuentas[inicio];
        cuentas[inicio] = cuentas[menor];
        cuentas[menor] = temp;
        ordenarDesde(inicio + 1);
    }

    private int indiceDelMenor(int menor, int actual) {
        if (actual >= cantidadCuentas) {
            return menor;
        }
        if (cuentas[actual].getSaldo() < cuentas[menor].getSaldo()) {
            menor = actual;
        }
        return indiceDelMenor(menor, actual + 1);
    }

    // ---------- Operaciones ----------
    public void transferir(int numeroOrigen, int numeroDestino, double monto) {
        if (numeroOrigen == numeroDestino) {
            throw new IllegalArgumentException("La cuenta origen y destino no pueden ser la misma");
        }
        CuentaBancaria origen = buscarPorNumero(numeroOrigen);
        CuentaBancaria destino = buscarPorNumero(numeroDestino);

        if (!origen.retirar(monto)) {
            throw new IllegalStateException("Fondos insuficientes en la cuenta origen #" + numeroOrigen);
        }
        try {
            destino.depositar(monto);
        } catch (IllegalArgumentException | IllegalStateException e) {
            origen.reintegrar(monto);
            throw e;
        }
    }

    public void aplicarInteresesGlobal() {
        for (int i = 0; i < cantidadCuentas; i++) {
            try {
                cuentas[i].registrarIntereses();
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("No se pudo aplicar intereses a la cuenta #"
                        + cuentas[i].getNumero() + ": " + e.getMessage());
            }
        }
    }

    public double saldoTotalDelBanco() {
        return saldoTotal(0);
    }

    private double saldoTotal(int indice) {
        if (indice >= cantidadCuentas) {
            return 0;
        }
        return cuentas[indice].getSaldo() + saldoTotal(indice + 1);
    }

    public void generarReporte() {
        ordenarPorSaldo();
        System.out.println("===== REPORTE DE CUENTAS (menor a mayor saldo) =====");
        if (cantidadCuentas == 0) {
            System.out.println("(el banco no tiene cuentas)");
        }
        for (int i = 0; i < cantidadCuentas; i++) {
            System.out.println(cuentas[i]);
        }
    }
}
