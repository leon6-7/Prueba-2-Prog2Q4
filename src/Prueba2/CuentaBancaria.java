/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Prueba2;
import java.util.ArrayList;
import java.util.Calendar;

public class CuentaBancaria {
    
    protected int numero;
    protected String cliente;
    protected double saldo;
    protected double tasa;
    protected Calendar creacion;
    protected TipoCuenta tipo;
    protected String moneda;
    protected String[] historial = new String[50];
    protected int mov =0;
    protected int maxMov=50;
    public CuentaBancaria(int numero, String cliente, String moneda){
    this.cliente= cliente;
    this.numero=numero;
    this.moneda=moneda;
    this.creacion= Calendar.getInstance();
    this.saldo = 0;
    this.tipo=TipoCuenta.CHEQUES;
    this.historial[mov]=("Cuenta creada con saldo inicial de 0. ");
    mov++;
    }
    public CuentaBancaria(int numero, String cliente, String moneda, TipoCuenta tipo){
    this.cliente= cliente;
    this.numero=numero;
    this.moneda=moneda;
    this.creacion= Calendar.getInstance();
    this.saldo = 0;
    this.tipo=tipo;
    this.historial[mov]=("Cuenta creada con saldo inicial de 0. ");
    mov++;
    }
    
    public void depositar(double monto){
    try{
        if(monto<=0){
            throw new IllegalArgumentException("Cantidad de monto illegal");
        }
        else{
            this.saldo+=monto;
        }
        registrarMovimiento("Deposito + "+monto);
    }catch(IllegalArgumentException e){System.out.println(e.getMessage());
    }
    }
    
    public boolean retirar(double monto){
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
    }
    return false;
    }
    
    public void mostrarHistorial(){
    System.out.println("=====HISTORIAL DE"+cliente.toUpperCase()+"=====");
    
    for(String n: historial){
        System.out.println(n);
    }
    
    }

    public double getSaldo() {
        return saldo;
    }
    
    public void registrarMovimiento(String linea) {
    try {
        if (mov >= 50) {
            throw new IllegalStateException("El historial de movimientos está completamente lleno.");
        }
        
        historial[mov]=linea;
        this.mov++;

    }catch (IllegalStateException e) {
        System.err.println("ERROR " + e.getMessage());
    }

}
     public void aplicarIntereses(){
        double interes=saldo*tasa;
        saldo=saldo+interes;
        registrarMovimiento("Intereses Aplicados +"+interes);
        
    
    }
    public String toString(){
        return String.format("Cuenta: #%d | %s | #%.2f", numero,cliente,saldo);
    
    }
    
    public void quienSoy(){
    System.out.println("CuentaBancaria (Clase base)");

    }
    
    }


