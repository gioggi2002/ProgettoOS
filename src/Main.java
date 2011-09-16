
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
        // Creo l'aeroporto
        Aeroporto aeroporto = new Aeroporto(0);
        // Creo il gestore
        Gestore gestore = new Gestore(aeroporto);
        
        // Creo gli aerei
        Thread threadArray[] = new Thread[numAerei];
        for(i = 0; i < numAerei; ++i){
            
            threadArray[i] = new Aereo(gestore);
            threadArray[i].start();
            threadArray[i].setPriority(1);
        }
    }
}
