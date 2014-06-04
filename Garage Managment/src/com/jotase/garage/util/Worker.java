/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventanas.controller.utils;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author <@jota_Segovia>
 */
public class Worker extends SwingWorker<Double, Void> {

    private JProgressBar progreso;
    

    @Override
    protected Double doInBackground() throws Exception {
        progreso.setVisible(true);


        // Un simble bucle hasta 10, con esperas de un segundo entre medias.
        for (int i = 0; i < 100; i++) {
            try {
                
                Thread.sleep(100);
            } catch (InterruptedException e) {
  
            }
            progreso.setValue(i);
        }
        // El supuesto resultado de la operación.
        progreso.setVisible(false);
        return 100.0;
    }

}
