
import java.util.concurrent.Semaphore;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gioggi2002
 */
public class Aeroporto {
    int numeroPiste = 2;
    //int pisteInUso = 0;
    static Semaphore semaforo;
    
    public Aeroporto(int numeroPiste){
        semaforo = new Semaphore(this.numeroPiste);
    }
    
    static public boolean usoPista(){
        try{
            System.out.println(semaforo.availablePermits());
            semaforo.acquire();
            if (semaforo.availablePermits()<0){
            //System.out.println("Nessuna pista disponibile.");
            return false;}
            else System.out.println("Pista utilizzata con successo.");
            return true;
        }catch(InterruptedException e){return false;}
                
    }
    
    static public void rilascioPista(){
        semaforo.release();
    }
    
    /*
    public boolean assegnaPista(String aereo){
        if(this.pisteInUso <= numeroPiste) {
            System.out.println("Pista assegnata a " +aereo);
            this.pisteInUso++;
            return true;
        }
        return false;
    }
     */
}
