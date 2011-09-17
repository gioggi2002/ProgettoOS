

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author gioggi2002
 */
public class Reader {
    
    public static int readInt(){
      int _int = 0;
      String _String;
        
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      try
      {
          _String = br.readLine();
          _int = Integer.parseInt(_String);
      }
      catch (IOException e1)
      {
         System.out.println ("errore di flusso");
      }
      catch (NumberFormatException e2)
      {
         System.out.println ("errore di input da tastiera");
         return(0);
      }
 
      return(_int);
   }

}
