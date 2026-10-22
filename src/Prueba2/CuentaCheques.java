
package Prueba2;
public class CuentaCheques extends CuentaBancaria {

    protected double limiteSobregiro;

    public CuentaCheques(
            int numero,
            String cliente,
            String moneda,
            double limiteSobregiro) {

        super(numero, cliente, moneda, TipoCuenta.CHEQUES);

        if (limiteSobregiro < 0) {
            throw new IllegalArgumentException(
                "El límite de sobregiro no puede ser negativo."
            );
        }

        this.limiteSobregiro = limiteSobregiro;
    }

    public void girarCheque(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException(
                "El monto del cheque debe ser mayor que cero."
            );
        }

        if (monto > saldo + limiteSobregiro) {
            throw new IllegalStateException(
                "Fondos insuficientes para girar el cheque"
            );
        }

        saldo -= monto;

        registrarMovimiento(
            "CHEQUE -" + monto + " -> saldo: " + saldo
        );
    }

    @Override
    public void registrarIntereses() {
        // Las cuentas de cheques no generan intereses.
    }

    @Override
    public void quienSoy() {
        System.out.println("Soy una CuentaCheques");
    }

    @Override
    public String toString() {
        return super.toString()
            + ", Limite de sobregiro: " + limiteSobregiro
            + ", Sobregiro disponible: "
            + Math.max(0, limiteSobregiro + saldo);
    }
}
