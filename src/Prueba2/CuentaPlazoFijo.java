package Prueba2;

import Prueba2.CuentaBancaria;
import Prueba2.TipoCuenta;
import java.util.Calendar;

public class CuentaPlazoFijo extends CuentaBancaria {

    private Calendar findePlazo;
    private double intereses;

    public CuentaPlazoFijo(int numero, String cliente, String moneda) {
        super(numero, cliente, moneda, TipoCuenta.PLAZO);
        this.findePlazo = null;
        this.intereses = 0;
    }

    public void setPlazo(int anio, int mes, int dia) {
        Calendar fecha = Calendar.getInstance();
        fecha.clear();
        fecha.setLenient(false);
        try {
            fecha.set(anio, mes - 1, dia);
            fecha.getTime(); // fuerza la validacion
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Fecha invalida: " + dia + "/" + mes + "/" + anio, e);
        }
        this.findePlazo = fecha;
    }

    public boolean estaVencida() {
        return findePlazo != null && !Calendar.getInstance().before(findePlazo);
    }

    private void consolidarIntereses() {
        if (intereses > 0) {
            saldo += intereses;
            registrarMovimiento("INTERESES ACREDITADOS AL SALDO +" + intereses + " -> saldo: " + saldo);
            intereses = 0;
        }
    }

    @Override
    public boolean retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor que 0 (recibido: " + monto + ")");
        }
        if (estaVencida()) {
            consolidarIntereses();
            return super.retirar(monto);
        }
        if (monto > intereses) {
            return false;
        }
        intereses -= monto;
        registrarMovimiento("RETIRO DE INTERESES -" + monto + " -> intereses: " + intereses);
        return true;
    }

    @Override
    public void registrarIntereses() {
        if (estaVencida()) {
            consolidarIntereses();
            super.registrarIntereses();
        } else {
            double interes = saldo * tipo.tasa();
            if (interes != 0) {
                intereses += interes;
                registrarMovimiento("INTERESES ACUMULADOS +" + interes + " -> intereses: " + intereses);
            }
        }
    }

    @Override
    public void reintegrar(double monto) {
        if (estaVencida()) {
            super.reintegrar(monto);
        } else {
            intereses += monto;
            registrarMovimiento("REVERSA +" + monto + " -> intereses: " + intereses);
        }
    }

    @Override
    public void quienSoy() {
        System.out.println("Soy una CuentaPlazoFijo");
    }

    @Override
    public String toString() {
        String fin = (findePlazo == null) ? "sin definir" : findePlazo.getTime().toString();
        return super.toString() + String.format(" | Intereses acumulados: %.2f | Fin de plazo: %s | Estado: %s",
                intereses, fin, estaVencida() ? "VENCIDA" : "VIGENTE");
    }
}
