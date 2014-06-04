/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventanas.controller.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Gaming
 */
public class Database {
   
        Archivo archivo = new Archivo();
        
        public String[] getDataBaseData() throws FileNotFoundException, IOException {
        //[hostname][databasename][ruta][clave]
        String[] datos = new String[4];
        datos[0] = archivo.Leer_Linea("config.txt", 2).substring(12);//
        datos[1] = archivo.Leer_Linea("config.txt", 3).substring(16);//
        datos[2] = archivo.Leer_Linea("config.txt", 4).substring(8);//
        datos[3] = archivo.Leer_Linea("config.txt", 5).substring(12);//
        return datos;
    }
}
