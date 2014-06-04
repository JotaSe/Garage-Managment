/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventanas.controller.utils;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
/**
 *
 * @author Javier
 */

public class BackUp {

    private int BUFFER = 10485760;  
    //para guardar en memoria
    private StringBuffer temp = null;
    //para guardar el archivo SQL
    private FileWriter  fichero = null;
    private PrintWriter pw = null;
    public boolean CrearBackup(String host, String port, String user, String password, String db, String dir){
    boolean ok=false;
    //mysqldump -u root -a -c -B nombreBD > "ruta"
    try{       
        //sentencia para crear el BackUp
//C:\Program Files\MySQL\MySQL Server 5.5\bin
        Process run = Runtime.getRuntime().exec(
        "C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump --host=" + host + " --port=" + port +
        " --user=" + user + " --password=" + password +
        "  --add-drop-database --complete-insert --extended-insert -B  " +
        " --default-character-set=utf8 --single-transaction=TRUE --routines --events " + db);
        //se guarda en memoria el backup
        InputStream in = run.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        temp = new StringBuffer();
        int count;
        char[] cbuf = new char[BUFFER];
        while ((count = br.read(cbuf, 0, BUFFER)) != -1) {
            temp.append(cbuf, 0, count);
        }
        br.close();
        in.close();        
        /* se crea y escribe el archivo SQL */
        fichero = new FileWriter(dir);
        pw = new PrintWriter(fichero);                                                    
        pw.println(temp.toString());  
        ok=true;
   }
    catch (Exception ex){
            ex.printStackTrace();
    } finally {
       try {           
         if (null != fichero)
              fichero.close();
       } catch (Exception e2) {
           e2.printStackTrace();
       }
    }   
    return ok; 
 }  
 
    
}

