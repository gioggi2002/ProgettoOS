
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gioggi2002
 */
public class Gestore extends Thread {
    Aeroporto aeroporto;
    private boolean run;    
    
    public Gestore (Aeroporto aeroporto){
        this.aeroporto = aeroporto;
        this.run = true;
    }
    
    @Override
    public void run(){
        /* finche non arriva una terminazione differita cerca di servire */
        while(this.run){
            try{
                this.aeroporto.gestioneGestore(this);
            }catch(InterruptedException e){
                this.run=false;
            }
        }
    }
    
        
}
