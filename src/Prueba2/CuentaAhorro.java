
package Prueba2;

import java.util.Calendar;

/**
 *
 * @author Abril
 */
public class CuentaAhorro extends CuentaBancaria{
    private Calendar ultimaModif;
    private int retirosRealizados=0;
    private static final int MAX_RETIRO_MES=3;
    public CuentaAhorro(int cuenta, String Cliente){
    super(cuenta, Cliente, 0.03);
    this.ultimaModif=Calendar.getInstance();
    }
    public boolean status(){
        Calendar fechaActual = Calendar.getInstance();
        fechaActual.add(Calendar.MONTH,-6);
        return ultimaModif.after(fechaActual);
    }
    
    public void aplicarIntereses(){
        if(status()){
        double interes=saldo*tasa;
        depositar(interes);
        historial.add("Intereses Aplicados +"+interes);
        }
    
    }
    
    //HACER EL RETIRAR DONDE SE MIRA EL STATUS Y HACER CUANTOS RETIROS REALIZADOS Y GUARDAR SU ULTIMO RETIRO  
}
