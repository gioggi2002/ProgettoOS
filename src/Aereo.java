/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gioggi2002
 */
public class Aereo extends Thread {
    double tipo;
    double azione;
    double peso;
    int id;
    
    public Aereo(int tipo, int azione, int peso, int id)
    {
        this.azione = Math.floor(Math.random() * 2);
        this.tipo = Math.floor(Math.random() * 2);
        this.peso = Math.floor(Math.random() * 100) + 100;
        
    }
    
}
