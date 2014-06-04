/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventanas.controller.utils;

import java.awt.event.KeyEvent;
import javax.swing.JTable;

/**
 *
 * @author <@jota_Segovia>
 */
public class InputVerify {

    public enum TYPES {

        INTEGER, DOUBLE, TEXT
    }
    public char[] num = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-', 'J', 'V', 'E', 'j', 'v', 'e'};

    public void Validar_Key(java.awt.event.KeyEvent evt, char[] chars) {
        char car = evt.getKeyChar();
        boolean np = false;
        int c = 0;

        for (int i = 0; i < chars.length; i++) {
            if (car == chars[i]) {
                np = true;
            }

        }

        if (!np) {

            evt.consume();
        }
    }

    public void validate(TYPES type, KeyEvent e) {
        char[] chars;
        char caracter = e.getKeyChar();
        switch (type) {
            case INTEGER:
                if (((caracter < '0')
                        || (caracter > '9'))
                        && (caracter != '\b' /*corresponde a BACK_SPACE*/) && caracter != ',' && caracter != '\n' && caracter != '.') {
                    e.consume();  // ignorar el evento de teclado
                }
                break;
            case DOUBLE:
                if (((caracter < '0')
                        || (caracter > '9'))
                        && (caracter != '\b' /*corresponde a BACK_SPACE*/) && caracter != '.' && caracter != '\n') {
                    e.consume();  // ignorar el evento de teclado
                }
                break;
            case TEXT:
                break;
        }
    }

    public void cancelTableInput(JTable table) {
        if (table.isEditing() == true) {
            table.getCellEditor().stopCellEditing();
        }
    }
}
