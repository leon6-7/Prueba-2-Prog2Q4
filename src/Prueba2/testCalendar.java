
package Prueba2;
import java.util.Calendar;
public class testCalendar {
    static void main(){
       
        Calendar fecha =Calendar.getInstance();
        int year=fecha.get(Calendar.YEAR); //.get es para extrarer informacion de la fecha solicitada
        System.out.println(year);
    
    }
}
