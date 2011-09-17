


import java.util.concurrent.Semaphore;

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
    
    Semaphore pisteDisponibili;

    
    public Aeroporto(int numeroPiste){
        pisteDisponibili = new Semaphore(numeroPiste);
    }
    
}
