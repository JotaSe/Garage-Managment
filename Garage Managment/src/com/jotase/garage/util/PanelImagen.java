/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.util;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

/**
 *
 * @author Gaming
 */
public class PanelImagen extends javax.swing.JDesktopPane{
    public JDesktopPane desk;
    public PanelImagen(){
        
    this.setSize(400,280);
}
    public void paintComponent (Graphics g){
    Dimension tamanio = getSize();
    ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/com/ventanas/view/icons/Ventanas2.png"));
    g.drawImage(imagenFondo.getImage(),0,0,tamanio.width, tamanio.height, null);
    setOpaque(false);
    super.paintComponent(g);
}
    
}
