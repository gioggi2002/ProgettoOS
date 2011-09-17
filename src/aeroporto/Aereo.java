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
    private int servizio;
    private Aereoporto aereoporto = null;
    private Gestore gest = null;
    Condition condizione;
    Random rnd;
    
    public Aereo(int id, int peso, int tipo, Gestore gestore, Aereoporto aereoporto){
        this.id = id;
        this.peso = peso;
        this.tipo = tipo;
        this.gest = gestore;
        this.aereoporto = aereoporto;
        rnd = new Random();
        }

    @Override
    public void run(){
        Thread current = Thread.currentThread();
        id = (int) (current.getId()-10);
        current.setPriority(1);
        this.servizio=this.rnd.nextInt(2);
        if (this.servizio == 1)
            current.setPriority(current.getPriority()+2);
        this.tipo=this.rnd.nextInt(2);
        if (this.tipo == 1)
            current.setPriority(current.getPriority()+1);
        System.out.println("Priorità dell'aereo "+this.id+" è "+current.getPriority());
        for(int i=0;i<2;i++){
            try{
                this.aereoporto.richiediServizio(this,current.getPriority());
                if(servizio == 0){
                    servizio = 1;
                }else{
                    servizio = 0;
                }
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }
    }
    public void Atterra() throws InterruptedException{
        //System.out.println("Aereo "+this.id+" in fase di atterraggio.");
        int tempo;
        tempo = 50+1*this.peso;
        Thread.sleep(tempo);
        this.gest.liberaPista();
        System.out.println("Aereo "+this.id+" di peso "+this.peso+" e priorità "+this.getPriority()+" ha completato l'atterraggio in "+tempo+"ms.");
    }
    public void Decolla() throws InterruptedException{
        //System.out.println("Aereo "+this.id+" in fase di decollo.");
        int tempo;
        tempo = 20+1*this.peso;
        Thread.sleep(tempo);
        this.gest.liberaPista();
        System.out.println("Aereo "+this.id+" di peso "+this.peso+" e priorità "+this.getPriority()+" ha completato il decollo in "+tempo+"ms.");
    }
    
    public int getServizio(){
        return this.servizio;
    }
    
    public int getPeso(){
        return this.peso;
    }
    
    public int getIdAlt(){
        return this.id;
    }
    
    public int getPriorita(){
        return this.getPriority();
    }
    
    public void setCondition(Condition c){
        this.condizione = c;
    }
    
    public Condition getCondition(){
        return this.condizione;
    }
}
