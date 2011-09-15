
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
    private int numeroPiste = 2;
    int pisteInUso = 0;
    Semaphore semaforo;
    
    public Aeroporto(int piste){
        this.semaforo = new Semaphore(numeroPiste);
    }
    
    public boolean usoPista(){
        try{
            semaforo.acquire();
            if (semaforo.availablePermits()==0)
            return true;
        }catch(InterruptedException e){}
        return false;
    }
    
    public void rilascioPista(){
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
