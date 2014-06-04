/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventanas.controller.utils;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;

/**
 *
 * @author Gaming
 */
public class InternalFrame extends JInternalFrame{
       private JComponent Barra = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane();
       private Dimension dimBarra = null; 
       
       public void ocultarBarraTitulo(JInternalFrame frame)
       { 
            Barra = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane(); 
            dimBarra = Barra.getPreferredSize(); 
            Barra.setSize(0,0); 
            Barra.setPreferredSize(new Dimension(0,0)); 
            frame.repaint(); 
        }
       public void setIcon(JInternalFrame frame)
       {
        ImageIcon img = new ImageIcon(getClass().getResource("/com/ventanas/view/icons/iconVentana.png"));
        frame.setFrameIcon(img);
       }
    
}
