
package Prueba2;

import java.util.Calendar;

public class CuentaAhorro extends CuentaBancaria{
    private Calendar ultimoUso;
    public CuentaAhorro(int cuenta, String Cliente, String moneda){
    super(cuenta, Cliente, moneda, TipoCuenta.AHORRO);
    this.ultimoUso=Calendar.getInstance();
    }

    public boolean status(){
        Calendar fechaActual = Calendar.getInstance();
        fechaActual.add(Calendar.MONTH,-6);
        return ultimoUso.after(fechaActual);
    }
    @Override
    public void registrarIntereses(){
        if(status()){
        double interes=saldo*tasa;
        depositar(interes);
        registrarMovimiento("Intereses Aplicados +"+interes);
        }
    }
    @Override
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
    @Override
    public void quienSoy(){
    System.out.println("(Soy una CuentaAhorro");
    }
    @Override
    public void depositar(double monto){
    if(status()){try{
        if(monto<=0){
            throw new IllegalArgumentException("Cantidad de monto illegal");
        }
        else{
            this.saldo+=monto;
        }
        registrarMovimiento("Deposito + "+monto);
    }catch(IllegalArgumentException e){System.out.println(e.getMessage());
    }}}
    
    @Override
    public boolean retirar(double monto){
    if(status()){
    try{if(monto>0&&this.saldo>=monto){
        this.saldo-=monto;
        registrarMovimiento("Retiro - "+monto);
        
        return true;}
    
        else if(monto<=0){
            throw new IllegalArgumentException("Error en la cantidad del monto");
        }
    
        System.out.println("monto insuficiente");
        return false;
        
    }catch(IllegalArgumentException E){
    System.out.println(E.getMessage());
    }}
    return false;
     
    }
}
