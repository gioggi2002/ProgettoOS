/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aeroporto;

import java.io.*;
import java.util.Random;

/**
 *
 * @author gioggi2002
 */
public class Main {
    public static void main(String[] args) {
        int numAerei;
        int peso;
        Aereoporto a = new Aereoporto();
        Gestore gestore = new Gestore(a);
        Random rnd = new Random();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        // Lettura dei dati e inizializzazione delle variabili
        try{
        System.out.println("Inserisci il numero di Aerei: ");
        String line = input.readLine();
            while(Integer.parseInt(line) <= 0){
                System.out.println("Devi inserire un intero maggiore di 0.");
                System.out.println("Inserisci il numero di Aerei: ");
                line = input.readLine();
            }
        numAerei= Integer.parseInt(line);
        Aereo aerei[] = new Aereo[numAerei];
        for(int i = 0; i < numAerei; i++){
                peso = ((rnd.nextInt(100)+1)+100);
                aerei[i] = new Aereo(i,peso,0,gestore,a);
        }
        for(int i = 0; i < numAerei; i++){
                aerei[i].start();
        }
        gestore.start();
        for(int i = 0; i < numAerei; i++){
            try{
                   aerei[i].join();

            }catch(InterruptedException e){
                    System.out.println(e);
            }
        }
        System.out.println();
        System.out.println("-----");
        System.out.println("Aerei terminati. Esecuzione completata.");
        
        gestore.interrupt();
        
        try{
             gestore.join();
           }catch(InterruptedException e){
            System.out.println(e);
           }
        }catch(IOException ex){
            System.out.println(ex);
        }
    }
}
