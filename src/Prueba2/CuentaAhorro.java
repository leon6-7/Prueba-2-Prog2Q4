
package Prueba2;

import java.util.Calendar;

public class CuentaAhorro extends CuentaBancaria{
    private Calendar ultimoUso;
    private int retirosRealizados=0;
    private static final int MAX_RETIRO_MES=3;
    public CuentaAhorro(int cuenta, String Cliente, String moneda){
    super(cuenta, Cliente, moneda, TipoCuenta.AHORRO);
    this.ultimoUso=Calendar.getInstance();
    }
    public void registrarMovimiento(String linea) {
    try {
        if (mov >= 50) {
            throw new IllegalStateException("El historial de movimientos está completamente lleno.");
        }
        
        historial[mov]=linea;
        ultimoUso=Calendar.getInstance();
        this.mov++;

    }catch (IllegalStateException e) {
        System.err.println("ERROR " + e.getMessage());
    }

}
    public boolean status(){
        Calendar fechaActual = Calendar.getInstance();
        fechaActual.add(Calendar.MONTH,-6);
        return ultimoUso.after(fechaActual);
    }
    
    public void aplicarIntereses(){
        if(status()){
        double interes=saldo*tasa;
        depositar(interes);
        registrarMovimiento("Intereses Aplicados +"+interes);
        }
    
    }
    
}
