/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aeroporto;

/**
 *
 * @author gioggi2002
 */
public class Gestore extends Thread{
    private Aereoporto aereoporto = null;
    private boolean esegui;
    public Gestore(Aereoporto aereoporto){
        this.aereoporto = aereoporto;
        this.esegui = true;
    }
    
    @Override
    public void run(){
         while(this.esegui){
            try{
               this.aereoporto.gestisci();
            }catch(InterruptedException e){
                this.esegui = false;
            }
        }
    }
    
    // Metodo per la liberazione della pista
    // mediante l'uso del semaforo
    public void liberaPista(){
        this.aereoporto.piste.release();
    }
}
