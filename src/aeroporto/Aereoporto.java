/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aeroporto;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author gioggi2002
 */
public class Aereoporto {
    private int numPiste;
    private ReentrantLock accesso;
    private Semaphore aerei ;
    Semaphore piste ;
    LinkedList<Aereo> lista1 = new LinkedList();
    LinkedList<Aereo> lista2 = new LinkedList();
    LinkedList<Aereo> lista3 = new LinkedList();
    LinkedList<Aereo> lista4 = new LinkedList();
    private int serviti1;
    private int serviti2;
    private int serviti3;
    private int serviti4;
    private long uscita1;
    private long uscita2;
    private long uscita3;
    private long uscita4;
    private long arrivo1;
    private long arrivo2;
    private long arrivo3;
    private long arrivo4;
    private long tempoMedioAttesa;
    /**
     * @param args the command line arguments
     */
    
    public Aereoporto(){
        this.numPiste = 2;
        this.accesso = new ReentrantLock();
        this.aerei = new Semaphore(0);
        this.piste = new Semaphore(this.numPiste);
        this.serviti1 = 0;
        this.serviti2 = 0;
        this.serviti3 = 0;
        this.serviti4 = 0;
        this.uscita1 = 0;
        this.uscita2 = 0;
        this.uscita3 = 0;
        this.uscita4 = 0;
        this.arrivo1 = 0;
        this.arrivo2 = 0;
        this.arrivo3 = 0;
        this.arrivo4 = 0;
        this.tempoMedioAttesa = 0;
    }
    
    public void richiediServizio(Aereo a,int priorita) throws InterruptedException{
        this.accesso.lock();
        try{
            this.aerei.release();
            //System.out.println("Aereo "+a.getIdAlt()+" di peso "+a.getPeso()+" messo in lista "+priorita);
            switch(priorita){
                case 1:
                    lista1.add(a);
                    this.arrivo1+=System.currentTimeMillis();
                    //System.out.println("Tempo di entrata nella coda 1: "+System.currentTimeMillis());
                    Collections.sort(lista1,new MyComparator());
                    a.setCondition(this.accesso.newCondition());
                    a.getCondition().await();
                    break;
                case 2:
                    lista2.add(a);
                    this.arrivo2+=System.currentTimeMillis();
                    //System.out.println("Tempo di entrata nella coda 2: "+System.currentTimeMillis());
                    Collections.sort(lista2,new MyComparator());
                    a.setCondition(this.accesso.newCondition());
                    a.getCondition().await();
                    break;
                case 3:
                    lista3.add(a);
                    this.arrivo3+=System.currentTimeMillis();
                    //System.out.println("Tempo di entrata nella coda 3: "+System.currentTimeMillis());
                    Collections.sort(lista3,new MyComparator());
                    a.setCondition(this.accesso.newCondition());
                    a.getCondition().await();
                    break;
                case 4:
                    lista4.add(a);
                    this.arrivo4=System.currentTimeMillis();
                    //System.out.println("Tempo di entrata nella coda 4: "+System.currentTimeMillis());
                    Collections.sort(lista4,new MyComparator());
                    a.setCondition(this.accesso.newCondition());
                    a.getCondition().await();
                    break;
                    
            }           

        }finally{
            this.accesso.unlock();
        }
    }
    
    public void gestisci()throws InterruptedException{
        this.aerei.acquire();
        
        this.accesso.lock();
        try{
            this.piste.acquire();
            //System.out.println("Pista libera e acquisita.");
            Aereo daServire = Sveglia();
            //System.out.println("Momento in cui viene eseguita l'azione: "+System.currentTimeMillis());
            if(daServire.getServizio() == 0)
                daServire.decolla();
            else
                daServire.atterra();
            
            //System.out.println("L'aereo "+daServire.getIdAlt()+" di peso "+daServire.getPeso()+" ha completato l'azione.");
            daServire.getCondition().signal();

            this.tempoMedioAttesa = daServire.calcoloTempoAttesa();
        }finally{
            this.accesso.unlock();
        }
    }
    
    Aereo Sveglia(){
        Aereo daServire = null;
        while(daServire == null){
            if(!this.lista4.isEmpty()){
                daServire = this.lista4.getFirst();
                //System.out.println("Trovato aereo in lista 4.");
                this.lista4.removeFirst();
                this.uscita4 = (System.currentTimeMillis());
                //System.out.println("Tempo di uscita dalla coda: "+System.currentTimeMillis());
                this.serviti4++;
            }else{
                if(!this.lista3.isEmpty()){
                    daServire = this.lista3.getFirst();
                    //System.out.println("Trovato aereo in lista 3.");
                    this.lista3.removeFirst();
                    this.uscita3+=System.currentTimeMillis();
                    //System.out.println("Tempo di uscita: "+System.currentTimeMillis());
                    this.serviti3++;
                }else{
                    if(!this.lista2.isEmpty()){
                        daServire=this.lista2.getFirst();
                        //System.out.println("Trovato aereo in lista 2.");
                        this.lista2.removeFirst();
                        this.uscita2+=System.currentTimeMillis();
                        //System.out.println("Tempo di uscita: "+System.currentTimeMillis());
                        this.serviti2++;
                    }else{
                        if(!this.lista1.isEmpty()){
                            daServire = this.lista1.getFirst();
                            //System.out.println("Trovato aereo in lista 1.");
                            this.lista1.removeFirst();
                            this.uscita1+=System.currentTimeMillis();
                            //System.out.println("Tempo di uscita: "+System.currentTimeMillis());
                            this.serviti1++;
                        }
                    }
                }
            }
        }
        return daServire;
    }
    
    public void tempoAttesa(){
        System.out.println();
        System.out.println("-----");
        System.out.println();
        if(this.serviti4!=0)
        System.out.println("Richieste a priorità 4 servite= "+this.serviti4+" tempo medio="+(this.uscita4-this.arrivo4)/this.serviti4+" ms");
        if(this.serviti3!=0)
        System.out.println("Richieste a priorità 3 servite= "+this.serviti3+" tempo medio="+(this.uscita3-this.arrivo3)/this.serviti3+" ms");
        if(this.serviti2!=0)
        System.out.println("Richieste a priorità 2 servite= "+this.serviti2+" tempo medio="+(this.uscita2-this.arrivo2)/this.serviti2+" ms");
        if(this.serviti1!=0)
        System.out.println("Richieste a priorità 1 servite= "+this.serviti1+" tempo medio="+(this.uscita1-this.arrivo1)/this.serviti1+" ms");
    }
}

class MyComparator implements Comparator{
    @Override
    public int compare(Object a,Object b){
      Aereo a1 = (Aereo)a;
      Aereo b1 = (Aereo)b;
      if (a1.getPeso() < b1.getPeso()) return 1;
      else return -1;  
    }
}