package Prueba2;

import Prueba2.CuentaAhorro;
import Prueba2.CuentaBancaria;
import Prueba2.CuentaCheques;
import Prueba2.CuentaPlazoFijo;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static int leerInt(String mensaje) {
        System.out.print(mensaje);
        return Integer.parseInt(sc.nextLine().trim());
    }

    private static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        return Double.parseDouble(sc.nextLine().trim());
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static void mostrarMenu() {
        System.out.println("\n===== BANCO FINANZAS DEL VALLE =====");
        System.out.println("1. Crear cuenta");
        System.out.println("2. Depositar");
        System.out.println("3. Retirar");
        System.out.println("4. Transferir");
        System.out.println("5. Aplicar intereses a todas las cuentas");
        System.out.println("6. Ver reporte ordenado por saldo y saldo total");
        System.out.println("7. Ver historial de una cuenta");
        System.out.println("8. Girar cheque");
        System.out.println("9. Salir");
    }

    public static void main(String[] args) {
        Banco banco = new Banco();
        int opcion = 0;

        do {
            mostrarMenu();
            try {
                opcion = leerInt("Opcion: ");
                switch (opcion) {
                    case 1:
                        crearCuenta(banco);
                        break;
                    case 2: {
                        CuentaBancaria c = banco.buscarPorNumero(leerInt("Numero de cuenta: "));
                        c.depositar(leerDouble("Monto a depositar: "));
                        System.out.println("Deposito realizado. Saldo actual: " + c.getSaldo());
                        break;
                    }
                    case 3: {
                        CuentaBancaria c = banco.buscarPorNumero(leerInt("Numero de cuenta: "));
                        if (c.retirar(leerDouble("Monto a retirar: "))) {
                            System.out.println("Retiro realizado. Saldo actual: " + c.getSaldo());
                        } else {
                            System.out.println("Retiro rechazado: fondos insuficientes (o disponibles solo en intereses).");
                        }
                        break;
                    }
                    case 4: {
                        int origen = leerInt("Numero de cuenta origen: ");
                        int destino = leerInt("Numero de cuenta destino: ");
                        double monto = leerDouble("Monto a transferir: ");
                        banco.transferir(origen, destino, monto);
                        System.out.println("Transferencia realizada.");
                        break;
                    }
                    case 5:
                        banco.aplicarInteresesGlobal();
                        System.out.println("Intereses aplicados.");
                        break;
                    case 6:
                        banco.generarReporte();
                        System.out.printf("SALDO TOTAL DEL BANCO: %.2f%n", banco.saldoTotalDelBanco());
                        break;
                    case 7:
                        banco.buscarPorNumero(leerInt("Numero de cuenta: ")).mostrarHistorial();
                        break;
                    case 8: {
                        CuentaBancaria c = banco.buscarPorNumero(leerInt("Numero de cuenta: "));
                        if (c instanceof CuentaCheques) {
                            double monto = leerDouble("Monto del cheque: ");
                            ((CuentaCheques) c).girarCheque(monto);
                            System.out.println("Cheque girado. Saldo actual: " + c.getSaldo());
                        } else {
                            System.out.println("La cuenta #" + c.getNumero() + " no es de tipo Cheques.");
                        }
                        break;
                    }
                    case 9:
                        System.out.println("Gracias por usar el sistema. Hasta luego.");
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                // Ultimo punto de captura de errores
                System.out.println("ERROR: " + e.getMessage());
            }
        } while (opcion != 9);
    }

    private static void crearCuenta(Banco banco) {
        System.out.println("Tipo de cuenta: 1) Ahorro  2) Cheques  3) Plazo Fijo");
        int tipo = leerInt("Tipo: ");
        if (tipo < 1 || tipo > 3) {
            throw new IllegalArgumentException("Tipo de cuenta invalido: " + tipo);
        }
        int numero = leerInt("Numero de cuenta: ");
        String cliente = leerTexto("Cliente: ");
        String moneda = leerTexto("Moneda: ");

        CuentaBancaria nueva;
        if (tipo == 1) {
            nueva = new CuentaAhorro(numero, cliente, moneda);
        } else if (tipo == 2) {
            double limite = leerDouble("Limite de sobregiro: ");
            nueva = new CuentaCheques(numero, cliente, moneda, limite);
        } else {
            CuentaPlazoFijo plazo = new CuentaPlazoFijo(numero, cliente, moneda);
            System.out.println("Fecha de vencimiento:");
            int anio = leerInt("  Anio: ");
            int mes = leerInt("  Mes (1-12): ");
            int dia = leerInt("  Dia: ");
            plazo.setPlazo(anio, mes, dia);
            nueva = plazo;
        }
        banco.agregarCuenta(nueva);
        System.out.print("Cuenta creada: ");
        nueva.quienSoy();
    }
}
