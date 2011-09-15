
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
    int id;
    int tipo;
    int azione;
    int peso;
    
    
    public Aereo(int id, double tipo, double azione, double peso)
    {
        
        
    }
    
   
    @Override
    public void run(){
        
        // Otteniamo l'id del thread
        Thread current = Thread.currentThread();
        id = (int) (current.getId()-9);
        // Otteniamo il tipo di aereo (di linea/privato)
        tipo = (int) (Math.random()*2);
        if (tipo == 1)
            System.out.println("L'aereo "+id+" e' un aereo di linea. ");
        else System.out.println("L'aereo "+id+" e' un aereo privato. ");
        // Otteniamo la richiesta dell'aereo (decollo/atterraggio)
        azione = (int) (Math.random()*2);
        if (azione == 1)
            System.out.println("L'azione richiesta dall'aereo "+id+" e' il decollo. ");
        else System.out.println("L'azione richiesta dall'aereo "+id+" e' l'atterraggio. ");
        // Otteniamo il peso dell'aereo
        peso = (int) (100+Math.random()*100);
        System.out.println("Il peso dell'aereo "+id+" e': "+peso);
        
        richiediServizio();
        
    }
    
    public void richiediServizio(){
        
        // Richiesta al gestore
        
        // Se la richiesta è accettata blocco il gestore
        
        // Procedo con fase di decollo o atterraggio
        if (azione == 1)
            decollo();
        else atterraggio();
        
        // Rilascio il gestore
        
    }
    
    public void richiediServizio2(){
        
        // Richiesta al gestore
        
        // Se la richiesta è accettata blocco il gestore
        
        // Procedo con la richiesta complementare
        if (azione == 1)
            atterraggio();
        else decollo();
        
        // Rilascio il gestore
        
    }
    
    public void decollo(){
        int tempo;
        // Sospendo il thread per completare il decollo
        tempo = 20+(1*peso);
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Aereo.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("L'aereo "+id+" e' decollato in "+tempo+"ms");
    }
    
    public void atterraggio(){
        int tempo;
        // Sospendo il thread per completare l'atterraggio
        tempo = 50+(1*peso);
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Aereo.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("L'aereo "+id+" e' atterrato in "+tempo+"ms");
    }
}
