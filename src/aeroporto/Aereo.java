/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aeroporto;

import java.util.Random;
import java.util.concurrent.locks.Condition;

/**
 *
 * @author gioggi2002
 */
public class Aereo extends Thread {
    private int id;
    private int peso;
    private int tipo;
    private int priorita;
    private int servizio;
    private long tempoAtt;
    private long tempoDec;
    private Aereoporto aereoporto = null;
    private Gestore gest = null;
    Condition condizione;
    Random rnd;
    
    public Aereo(int id, int peso, int tipo, Gestore gestore, Aereoporto aereoporto){
        this.id = id;
        this.peso = peso;
        this.tipo = tipo;
        this.priorita = 1;
        this.gest = gestore;
        this.aereoporto = aereoporto;
        rnd = new Random();
        }

    @Override
    public void run(){
        
        // Generiamo i valori per gli attributi dell'aereo
        // e modifichiamo la priorità in base a questi
        Thread current = Thread.currentThread();
        id = (int) (current.getId()-10);
        current.setPriority(1);
        this.servizio=this.rnd.nextInt(2);
        if (this.servizio == 1)
            current.setPriority(current.getPriority()+2);
        this.tipo=this.rnd.nextInt(2);
        if (this.tipo == 1)
            current.setPriority(current.getPriority()+1);
        //System.out.println("Priorità dell'aereo "+this.id+" è "+current.getPriority());
        this.priorita = current.getPriority();
        
        // Ciclo for per richiesta del primo servizio e del suo complementare
        for(int i=0;i<2;i++){
            try{
                this.aereoporto.richiediServizio(this,current.getPriority());
                // Ciclo if per la modifica della priorita
                // a seconda del servizio complementare richiesto
                if(servizio == 0){
                    servizio = 1;
                    current.setPriority(current.getPriority()+2);
                    //System.out.println("Nuova priorità aereo "+this.id+": "+current.getPriority());
                    this.priorita = current.getPriority();
                }else{
                    servizio = 0;
                    current.setPriority(current.getPriority()-2);
                    //System.out.println("Nuova priorità aereo "+this.id+": "+current.getPriority());
                    this.priorita = current.getPriority();
                }
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }
}
    
    // Metodo per la simulazione del tempo d'atterraggio
    public void atterra() throws InterruptedException{
        this.tempoAtt = 50+1*this.peso;
        Thread.sleep(this.tempoAtt);
        this.gest.liberaPista();
        //System.out.println("Aereo "+this.id+" di peso "+this.peso+" e priorità "+this.getPriority()+" ha completato l'atterraggio in "+this.tempoAtt+"ms.");
    }
    
    // Metodo per la simulazione del tempo di decollo
    public void decolla() throws InterruptedException{
        this.tempoDec = 20+1*this.peso;
        Thread.sleep(this.tempoDec);
        this.gest.liberaPista();
        //System.out.println("Aereo "+this.id+" di peso "+this.peso+" e priorità "+this.getPriority()+" ha completato il decollo in "+this.tempoDec+"ms.");
    }
    
    // Metodo per il passaggio dell'attributo servizio
    public int getServizio(){
        return this.servizio;
    }
    
    // Metodo per il passaggio dell'attributo peso
    public int getPeso(){
        return this.peso;
    }
    
    // Metodo per il passaggio dell'attributo id
    // Usato per l'individuazione dell'aereo e la corretta stampa
    // delle stringhe di debug distribuite nel codice
    public int getIdAlt(){
        return this.id;
    }
    
    // Metodo per il passaggio dell'attributo priorita
    public int getPriorita(){
        return this.getPriority();
    }
    
    // Metodo per istanziare la condizione su cui sospendere l'aereo
    public void setCondition(Condition c){
        this.condizione = c;
    }
    
    // Metodo per restituire la condizione su cui è sospeso l'aereo
    public Condition getCondition(){
        return this.condizione;
    }
}
