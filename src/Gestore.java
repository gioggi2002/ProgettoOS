
import java.util.concurrent.Semaphore;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gioggi2002
 */
public class Gestore extends Thread {
    int threadAttivi = 0;
    //static Semaphore semaforoGestore;
    Aeroporto aeroporto;
    
    public Gestore (int threadAttivi){
    //    semaforoGestore = new Semaphore(this.threadAttivi);
        Aeroporto aeroporto = new Aeroporto(2);
    }
    
    @Override
    public void run(){
        
    }
    
    public void richiediServizio(){
        //Aeroporto.usoPista();
        if (this.aeroporto.usoPista() == true){
            //Aeroporto.rilascioPista();
            //System.out.println("Azione compiuta correttamente.");
        }
        else System.out.println("Piste occupate.");
    }
    
    public void fineServizio(){
        aeroporto.rilascioPista();
    }
    
}
