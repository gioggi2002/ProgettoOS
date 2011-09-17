


import java.util.Random;
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
    public int priorita;
    private Gestore gestore = null;
    Aereo a;
    private Random randomizer = new Random();
    private Aeroporto aeroporto = null;
    
    
    //public Aereo(int id, double tipo, double azione, double peso)
    public Aereo(Gestore gestore, Aeroporto aeroporto)
    {
        this.gestore = gestore;
        this.aeroporto = aeroporto;
        
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
        
        priorita = current.getPriority();
        
        // Richiediamo il servizio
        try {
            this.gestore.richiediServizio(this, peso, priorita, id);
            //azione1(this, peso, priorita, id);
        }catch (InterruptedException e) {
            System.out.println(e);
        }
        
        // Determiniamo il servizio complementare
        if(azione == 1){
            azione = 0;
            System.out.println("L'aereo "+id+" richiede ora di atterrare.");
        }
        else {
            azione = 1;
            System.out.println("L'aereo "+id+" richiede ora di decollare.");
        }
        
        // Richiediamo il servizio complementare
        try {
            this.gestore.richiediServizio(this, peso, priorita, id);
            //azione1(this, peso, priorita, id);
        }catch (InterruptedException e) {
            System.out.println(e);
        }
        
    }
    
    public void azione1(Aereo a/*, int peso, int priorita, int id*/) throws InterruptedException{
        int tempo = 0;
        
        // Richiesta al gestore e blocco del thread corrente
        
        // Quando il gestore mi sblocca acquisisco la pista dell'aeroporto
        if (this.azione == 1)
                tempo = decollo();
            else tempo = atterraggio();
        
        // Comunico al gestore che ho finito e la pista può essere rilasciata
        
        //System.out.println("L'aereo ha completato l'azione in "+tempo+"ms");
        
    }
      
    public int decollo(){
        int tempo;
        // Sospendo il thread per completare il decollo
        tempo = 20+(1*this.peso);
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Aereo.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("L'aereo "+this.id+" e' decollato in "+tempo+"ms");
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
    
    public int getPeso(){
        return peso;
    }
}
