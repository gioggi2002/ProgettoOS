
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gioggi2002
 */
public class Aereo extends Thread {
    public int id;
    private int tipo;
    private int azione;
    private int peso;
    private Gestore gestore = null;
    
    
    //public Aereo(int id, double tipo, double azione, double peso)
    public Aereo(Gestore gestore)
    {
        this.gestore = gestore;
        
    }
    
   
    @Override
    public void run(){
        
        // Otteniamo l'id del thread
        Thread current = Thread.currentThread();
        id = (int) (current.getId()-10);
        // Otteniamo il tipo di aereo (di linea/privato)
        tipo = (int) (Math.random()*2);
        if (tipo == 1) {
            System.out.println("L'aereo "+id+" e' un aereo di linea. ");
            System.out.println("L'aereo "+id+" conserva priorita': "+current.getPriority());
        }
        else {
            // Aumentiamo la priorità per l'aereo privato
            System.out.println("L'aereo "+id+" e' un aereo privato. ");
            current.setPriority(current.getPriority()+1);
            System.out.println("L'aereo "+id+" guadagna priorita': "+current.getPriority());
        }
        // Otteniamo la richiesta dell'aereo (decollo/atterraggio)
        azione = (int) (Math.random()*2);
        if (azione == 1) {
            // Aumentiamo la priorità per l'aereo in atterraggio
            System.out.println("L'azione richiesta dall'aereo "+id+" e' l'atterraggio.");
            current.setPriority(current.getPriority()+2);
            System.out.println("L'aereo "+id+" guadagna priorita': "+current.getPriority());
        }
        else { 
            System.out.println("L'azione richiesta dall'aereo "+id+" e' il decollo.");
            System.out.println("L'aereo "+id+" conserva priorita': "+current.getPriority());
        }
        // Otteniamo il peso dell'aereo
        peso = (int) (100+Math.random()*100);
        //System.out.println("Il peso dell'aereo "+id+" e': "+peso);
        
        // Richiediamo il servizio
        azione1();
        
        if(azione == 1){
            azione = 0;
            System.out.println("L'aereo "+id+" richiede ora di atterrare.");
        }
        else {
            azione = 1;
            System.out.println("L'aereo "+id+" richiede ora di decollare.");
        }
        
        // Richiediamo il servizio complementare
        azione1();
        
    }
    
    public void azione1(){
        int tempo = 0;
        
        // Richiesta al gestore e blocco del thread corrente
        gestore.richiediServizio();
        
        // Quando il gestore mi sblocca acquisisco la pista dell'aeroporto
        if (azione == 1)
                tempo = decollo();
            else tempo = atterraggio();
        
        // Comunico al gestore che ho finito e la pista può essere rilasciata
        gestore.fineServizio();
        
        //System.out.println("L'aereo ha completato l'azione in "+tempo+"ms");
        
    }
      
    public int decollo(){
        int tempo;
        // Sospendo il thread per completare il decollo
        tempo = 20+(1*peso);
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Aereo.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("L'aereo "+id+" e' decollato in "+tempo+"ms");
        return tempo;
    }
    
    public int atterraggio(){
        int tempo;
        // Sospendo il thread per completare l'atterraggio
        tempo = 50+(1*peso);
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Aereo.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("L'aereo "+id+" e' atterrato in "+tempo+"ms");
        return tempo;
    }
}
