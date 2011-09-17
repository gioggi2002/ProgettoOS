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
    /**
     * @param args the command line arguments
     */
    
    public Aereoporto(){
        this.numPiste = 2;
        this.accesso = new ReentrantLock();
        this.aerei = new Semaphore(0);
        this.piste = new Semaphore(this.numPiste);
    }
    
    public void richiediServizio(Aereo a,int priorita) throws InterruptedException{
        this.accesso.lock();
        try{
            this.aerei.release();
            //System.out.println("Aereo "+a.getIdAlt()+" di peso "+a.getPeso()+" messo in lista "+priorita);
            switch(priorita){
                case 1:
                    lista1.add(a);
                    Collections.sort(lista1,new MyComparator());
                    a.setCondition(this.accesso.newCondition());
                    a.getCondition().await();
                    break;
                case 2:
                    lista2.add(a);
                    Collections.sort(lista2,new MyComparator());
                    a.setCondition(this.accesso.newCondition());
                    a.getCondition().await();
                    break;
                case 3:
                    lista3.add(a);
                    Collections.sort(lista3,new MyComparator());
                    a.setCondition(this.accesso.newCondition());
                    a.getCondition().await();
                    break;
                case 4:
                    lista4.add(a);
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
            if(daServire.getServizio() == 0)
                daServire.Atterra();
            else
                daServire.Decolla();
            
            //System.out.println("L'aereo "+daServire.getIdAlt()+" di peso "+daServire.getPeso()+" ha completato l'azione.");
            daServire.getCondition().signal();
            
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
            }else{
                if(!this.lista3.isEmpty()){
                    daServire = this.lista3.getFirst();
                    //System.out.println("Trovato aereo in lista 3.");
                    this.lista3.removeFirst();
                }else{
                    if(!this.lista2.isEmpty()){
                        daServire=this.lista2.getFirst();
                        //System.out.println("Trovato aereo in lista 2.");
                        this.lista2.removeFirst();
                    }else{
                        if(!this.lista1.isEmpty()){
                            daServire = this.lista1.getFirst();
                            //System.out.println("Trovato aereo in lista 1.");
                            this.lista1.removeFirst();
                        }
                    }
                }
            }
        }
        return daServire;
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