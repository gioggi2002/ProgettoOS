
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

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
    Semaphore semaforo;
    private ReentrantLock lockAccesso = null;
    private HashMap<Aereo,Condition> priorita1 = null;
    private HashMap<Aereo,Condition> priorita2 = null;
    private HashMap<Aereo,Condition> priorita3 = null;
    private HashMap<Aereo,Condition> priorita4 = null;
    private HashMap<Gestore,Condition> gestore = null;
    private int richiesteAerei;
    private int aereiServiti4;
    private int aereiServiti3;
    private int aereiServiti2;
    private int aereiServiti1;
    private long tempoUscita4;
    private long tempoUscita3;
    private long tempoUscita2;
    private long tempoUscita1;
    
    public Aeroporto(int numeroPiste){
        semaforo = new Semaphore(this.numeroPiste);
        this.lockAccesso = new ReentrantLock();
        this.priorita1 = new HashMap<Aereo,Condition>();
        this.priorita2 = new HashMap<Aereo,Condition>();
        this.priorita3 = new HashMap<Aereo,Condition>();
        this.priorita4 = new HashMap<Aereo,Condition>();
        int richiesteAerei = 0;
        this.gestore = new HashMap<Gestore,Condition>();
    }
    
    public void richiediServizio(Aereo a, int peso, int priorita, int id)
            throws InterruptedException{
        boolean ritorno = false;
        // Inizio sezione critica
        
        // Acquisizione lock
        this.lockAccesso.lock();
        try {
            int pesoAereo = peso;
            ritorno = true;
            this.richiesteAerei++;
            
            System.out.println("Il peso è "+peso);
            switch (priorita) {
                case 4:
                    this.priorita4.put(a,this.lockAccesso.newCondition());
                    System.out.println("Aereo "+id+" aggiunto alla coda 4");
                    this.priorita4.get(a).await();
                    break;
                case 3:
                    this.priorita3.put(a,this.lockAccesso.newCondition());
                    System.out.println("Aereo "+id+" aggiunto alla coda 3");
                    this.priorita3.get(a).await();
                    break;
                case 2:
                    this.priorita2.put(a,this.lockAccesso.newCondition());
                    System.out.println("Aereo "+id+" aggiunto alla coda 2");
                    this.priorita2.get(a).await();
                    break;
                case 1:
                    this.priorita1.put(a,this.lockAccesso.newCondition());
                    System.out.println("Aereo "+id+" aggiunto alla coda 1");
                    this.priorita1.get(a).await();
                    break;
                default:
                    ritorno = false;
                    break;
            }
            return; // ritorno;
        }finally{
            this.lockAccesso.unlock();
        }
    }
    
    
    public void gestioneGestore(Gestore gestore) throws InterruptedException{
        // Inizio sezione critica
        this.lockAccesso.lock();
        try{
            if (this.richiesteAerei == 0){
                this.gestore.put(gestore,this.lockAccesso.newCondition());
                this.gestore.get(gestore).await();
            }else{
                // Seleziono l'aereo in base alla priorità
                Aereo maxPriorita = selezioneAereo();
                // Concedo il servizio all'aereo
                
                // Diminuisco il numero delle richieste in attesa
                this.richiesteAerei--;
                // Rimuovo l'aereo dalla coda
                this.rimuoviAereo(maxPriorita);
            }
        }finally{
            this.lockAccesso.unlock();
        }
    }
    
    private Aereo selezioneAereo(){
        Aereo daServire = null;
        while (daServire == null){
            if(!this.priorita4.isEmpty())
            { 
                Set provvAereo = this.priorita4.keySet();
                Iterator<Aereo> i = provvAereo.iterator();
                daServire=i.next();
            }else{
                if(!this.priorita3.isEmpty())
                {
                    Set provvAereo = this.priorita3.keySet();
                    Iterator<Aereo> i = provvAereo.iterator();
                    daServire=i.next();
                }else{
                    if(!this.priorita2.isEmpty())
                    {
                        Set provvAereo = this.priorita2.keySet();
                        Iterator<Aereo> i = provvAereo.iterator();
                        daServire=i.next();
                    }else{
                        if(!this.priorita1.isEmpty())
                        {
                            Set provvAereo = this.priorita1.keySet();
                            Iterator<Aereo> i = provvAereo.iterator();
                            daServire=i.next();
                        } // Fine iterazione coda priorità1
                        } // Fine else coda priorità2
                    } // Fine else coda priorità3
                } // Fine else coda priorità4
        } // Fine ciclo while
        return daServire; // Ritorno l'aereo che ha accesso alla pista
    } // Fine metodo
    
    private void rimuoviAereo(Aereo a){
        Aereo daRimuovere = a;
        switch(daRimuovere.getPriority()){
            case 4:
                this.priorita4.get(daRimuovere).signal();
                this.priorita4.remove(daRimuovere);
                this.aereiServiti4++;
                this.tempoUscita4+=System.currentTimeMillis();
                break;
            case 3:
                this.priorita3.get(daRimuovere).signal();
                this.priorita3.remove(daRimuovere);
                this.aereiServiti3++;
                this.tempoUscita3+=System.currentTimeMillis();
                break;
            case 2:
                this.priorita2.get(daRimuovere).signal();
                this.priorita2.remove(daRimuovere);
                this.aereiServiti2++;
                this.tempoUscita2+=System.currentTimeMillis();
                break;
            case 1:
                this.priorita1.get(daRimuovere).signal();
                this.priorita1.remove(daRimuovere);
                this.aereiServiti1++;
                this.tempoUscita1+=System.currentTimeMillis();
                break;
        }   
    }
    
    public Gestore svegliaGestore(){
        Gestore g = null;
        this.gestore.get(g).signal();
        this.gestore.remove(g);
        return g;
    }
    
    /*public void calcoloAttesa(){
        if(this.aereiServiti4!=0)
        System.out.println("Il gestore ha servito "+this.aereiServiti4+" per un tempo medio d'attesa di "+(this.tempoUscita4-this.tempoArrivo4)/this.aereiServiti4);
    }*/
    
}
