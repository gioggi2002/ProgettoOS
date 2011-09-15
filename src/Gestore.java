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
    
    public Gestore (String name, Aeroporto aeroporto){
        super(name);
        this.aeroporto = aeroporto;
    }
    
    public void run(){
        
    }
    
}
