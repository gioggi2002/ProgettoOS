
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author gioggi2002
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numAerei;
        int i;
                
        System.out.println("Inserisci il numero di aerei richiesti: ");
        numAerei = Reader.readInt();
        System.out.println("Il numero letto e': "+numAerei+"\n");
        Aeroporto aeroporto = new Aeroporto(0);
        
        Thread threadArray[] = new Thread[numAerei];
        for(i = 0; i < numAerei; ++i){
            threadArray[i] = new Aereo(0,0,0,0);
            threadArray[i].start();
            threadArray[i].setPriority(1);
        }
    }
}
