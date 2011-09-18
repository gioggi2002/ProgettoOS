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
    private long arrivo1;
    private long arrivo2;
    private long arrivo3;
    private long arrivo4;
    private long uscita1;
    private long uscita2;
    private long uscita3;
    private long uscita4;
    private long tempoMedioAttesa;
    private long tempoMedioAttesa2;
    private long tempoMedioAttesa3;
    private long tempoMedioAttesa4;
    private Aereoporto aereoporto = null;
    private Gestore gest = null;
    Condition condizione;
    Random rnd;
    
    public Aereo(int id, int peso, int tipo, Gestore gestore, Aereoporto aereoporto){
        this.id = id;
        this.peso = peso;
        this.tipo = tipo;
        this.priorita = 1;
        //this.tempoAtt = tempoAtt;
        //this.tempoDec = tempoDec;
        this.arrivo1 = 0;
        this.arrivo2 = 0;
        this.arrivo3 = 0;
        this.arrivo4 = 0;
        this.uscita1 = 0;
        this.uscita2 = 0;
        this.uscita3 = 0;
        this.uscita4 = 0;
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
        this.priorita = current.getPriority();
        switch(current.getPriority()) {
            case 1:
                this.arrivo1+=System.currentTimeMillis();
                System.out.println("Tempo entrata aereo "+this.id+" è "+System.currentTimeMillis());
                break;
            case 2:
                this.arrivo2+=System.currentTimeMillis();
                System.out.println("Tempo entrata aereo "+this.id+" è "+System.currentTimeMillis());
                break;
            case 3:
                this.arrivo3+=System.currentTimeMillis();
                System.out.println("Tempo entrata aereo "+this.id+" è "+System.currentTimeMillis());
                break;
            case 4:
                this.arrivo4+=System.currentTimeMillis();
                System.out.println("Tempo entrata aereo "+this.id+" è "+System.currentTimeMillis());
                break;
        }
        for(int i=0;i<2;i++){
            try{
                this.aereoporto.richiediServizio(this,current.getPriority());
                if(servizio == 0){
                    servizio = 1;
                    current.setPriority(current.getPriority()+2);
                    System.out.println("Nuova priorità aereo "+this.id+": "+current.getPriority());
                    this.priorita = current.getPriority();
                }else{
                    servizio = 0;
                    current.setPriority(current.getPriority()-2);
                    System.out.println("Nuova priorità aereo "+this.id+": "+current.getPriority());
                    this.priorita = current.getPriority();
                }
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }
        switch(current.getPriority()) {
            case 1:
                this.uscita1+=System.currentTimeMillis();
                System.out.println("Tempo uscita aereo "+this.id+" è "+System.currentTimeMillis());
                break;
            case 2:
                this.uscita2+=System.currentTimeMillis();
                System.out.println("Tempo uscita aereo "+this.id+" è "+System.currentTimeMillis());
                break;
            case 3:
                this.uscita3+=System.currentTimeMillis();
                System.out.println("Tempo uscita aereo "+this.id+" è "+System.currentTimeMillis());
                break;
            case 4:
                this.uscita4+=System.currentTimeMillis();
                //System.out.println("Tempo uscita aereo "+this.id+" è "+System.currentTimeMillis());
                break;
        }
        
}
        
    public void atterra() throws InterruptedException{
        //System.out.println("Aereo "+this.id+" in fase di atterraggio.");
        //int tempo;
        this.tempoAtt = 50+1*this.peso;
        Thread.sleep(this.tempoAtt);
        this.gest.liberaPista();
        System.out.println("Aereo "+this.id+" di peso "+this.peso+" e priorità "+this.getPriority()+" ha completato l'atterraggio in "+this.tempoAtt+"ms.");
    }
    public void decolla() throws InterruptedException{
        //System.out.println("Aereo "+this.id+" in fase di decollo.");
        int tempo;
        tempo = 20+1*this.peso;
        Thread.sleep(tempo);
        this.gest.liberaPista();
        System.out.println("Aereo "+this.id+" di peso "+this.peso+" e priorità "+this.getPriority()+" ha completato il decollo in "+tempo+"ms.");
    }
    
    public long calcoloTempoAttesa(){
        switch (this.priorita){
            case 1:
                this.tempoMedioAttesa = (this.uscita1 - this.arrivo1 - this.tempoAtt - this.tempoDec);
                break;
            case 2:
                this.tempoMedioAttesa = (this.uscita2 - this.arrivo2 - this.tempoAtt - this.tempoDec);
                break;
            case 3:
                this.tempoMedioAttesa = (this.uscita3 - this.arrivo3 - this.tempoAtt - this.tempoDec);
                break;
            case 4:
                this.tempoMedioAttesa = (this.uscita4 - this.arrivo4 - this.tempoAtt - this.tempoDec);
                break;
        }
        return this.tempoMedioAttesa;            
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
