/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventanas.controller.utils;


import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.swing.JProgressBar;

/**
 *cmd /c mysql --password="+password+" --user="+login+" "+bd+" < " +nombrebackup
 * @author PROGRAMACION
 */
public class Restaurar {
        
    private int BUFFER = 10485760;  
    //para guardar en memmoria
    private StringBuffer temp = null;
    //para guardar el archivo SQL
    private FileWriter  fichero = null;
    private PrintWriter pw = null;
    private DBConnection con = DBConnection.getInstance();
    public void bat() throws IOException
    {
       /* Process run = Runtime.getRuntime().exec("mysql <"+file_backup);
        InputStream in = run.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        temp = new StringBuffer();
        int count;
        char[] cbuf = new char[BUFFER];
        while ((count = br.read(cbuf, 0, BUFFER)) != -1)
            temp.append(br);
        System.out.println(temp);
        br.close();
        in.close();     
     */   File ar = new File("rest.bat");
        
        
        Desktop.getDesktop().open(ar);
        
    }
    public void Progress() {
        JProgressBar current = new JProgressBar(0, 2); // Crear un JProgressBar con valores 0-2000
    current.setValue(0); // Fijar valor por defecto.

}
    public  boolean Backup( String user, String password, String db, String file_backup) throws FileNotFoundException, IOException, SQLException{
    boolean ok=false;
     
   //  Process run = Runtime.getRuntime().exec(""); 
    ScriptRunner runner = new ScriptRunner(con.getCon(),false,false);
    runner.runScript(new BufferedReader(new FileReader(file_backup)));
   // Conectar.instruccion.execute("\\. "+file_backup);
   //  bat();
   //  JProgressBar current = new JProgressBar(0, 2);
   //  current.setValue(0);
     
        
   //  bat();
    //         InputStream in = run.getInputStream();
   //     BufferedReader br = new BufferedReader(new InputStreamReader(in));

    //    temp = new StringBuffer();
        int count;
        char[] cbuf = new char[BUFFER];
    //    while ((count = br.read(cbuf, 0, BUFFER)) != -1)
     //       temp.append(br);
        System.out.println(temp);        
//C:\Program Files\MySQL\MySQL Server 5.5\bin
   /* fichero = new FileWriter("C:\\Users\\PROGRAMACION\\Documents\\rest.bat");
    pw = new PrintWriter(fichero); 
    pw.println(" mysql  --host=LENOVO-PC --user="+user+" --password="+password+" --port=3306  < "+file_backup);
    pw.write(" mysql  --host=LENOVO-PC --user="+user+" --password="+password+" --port=3306  < "+file_backup);
    bat();*/
    
    
    
    

    return ok; 
 }  
 

}