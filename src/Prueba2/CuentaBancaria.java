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
    protected ArrayList<String> historial;
    
    public CuentaBancaria(int numero, String cliente, double tasa){
    this.cliente= cliente;
    this.numero=numero;
    this.tasa=tasa;
    this.creacion= Calendar.getInstance();
    this.saldo = 500;
    this.historial= new ArrayList<>();
    this.historial.add("Cuenta creada con saldo inicial e 500Lps. ");
    }
    
    
    public void depositar(double monto){
    try{
        if(monto<=0){System.out.println("Insuficiente Saldo");}
        else{this.saldo+=monto;}
        historial.add("Deposito + "+monto);
    }catch(Exception e){System.out.println(e.getMessage());
    }
    }
    
    public boolean retirar(double monto){
    if(monto>0&&this.saldo>=monto){
        this.saldo-=monto;
        historial.add("Retiro - "+monto);
        return true;
    }
    historial.add("Retiro Fallido: -"+monto);
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
    
    public String toString(){
        return String.format("Cuenta: #%d | %s | #%.2f", numero,cliente,saldo);
    
    }
    
    public void transferencia(double monto, CuentaBancaria destino){
    if(this.retirar(monto)){
        destino.depositar(monto);
    
    this.historial.add("Transferencia Enviada a # "+ destino.numero+" : "+monto);
        }else{
    System.out.println("Transferencia Fallida");
    }
    }
}
