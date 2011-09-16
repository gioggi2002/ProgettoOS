
import java.util.ArrayList;
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
    Aeroporto aeroporto;
    ArrayList priorita4;
    ArrayList priorita3;
    ArrayList priorita2;
    ArrayList priorita1;
    
    public Gestore (Aeroporto aeroporto){
        this.aeroporto = aeroporto;
        
    }
    
    @Override
    public void run(){
        
    }
    
    public void richiediServizio(int peso, int priorita){
        //Aeroporto.usoPista();
        if (this.aeroporto.usoPista() == true){
            //Aeroporto.rilascioPista();
            //System.out.println("Azione compiuta correttamente.");
            inserisciCoda(peso, priorita);
        }
        else inserisciCoda(peso, priorita);
    }
    
    public void fineServizio(){
        aeroporto.rilascioPista();
    }
    
    public void inserisciCoda(int peso, int priorita){
        System.out.println("Il peso è "+peso);
        switch (priorita) {
            case 4:
                priorita4.add(peso);
                System.out.println("Aggiunto alla coda 4");
                break;
            case 3:
                priorita3.add(peso);
                System.out.println("Aggiunto alla coda 3");
                break;
            case 2:
                priorita2.add(peso);
                System.out.println("Aggiunto alla coda 2");
                break;
            case 1:
                priorita1.add(peso);
                System.out.println("Aggiunto alla coda 1");
                break;        
        }
            
    }
    
}
