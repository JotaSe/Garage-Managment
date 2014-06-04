package com.ventanas.controller.utils;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SONEVIEW
 */
public class Tabla {
  
DecimalFormat f = new DecimalFormat("###,##0.00");

    public String[] getRowAt(int row, JTable table) {
         String[] result = new String[table.getColumnCount()];

         for (int i = 0; i < table.getColumnCount(); i++) {
             result[i] = table.getModel().getValueAt(row, i).toString();
         }

         return result;
    }

public void Agregar(javax.swing.JTable tabla, Object[] rowData)
{
    DefaultTableModel model = (DefaultTableModel) tabla.getModel();
    model.addRow(rowData);
    tabla.setModel(model);
}

public void removeSelectedRow(javax.swing.JTable tabla, int row)
{
DefaultTableModel model = (DefaultTableModel) tabla.getModel();
model.removeRow(row);
tabla.setModel(model);
}
public void eliminarFila(javax.swing.JTable  tabla,Object id,int columna){
    DefaultTableModel model=(DefaultTableModel) tabla.getModel();
    for(int i=0;i<model.getRowCount();i++){
        if(id.equals(model.getValueAt(i, columna)))
            model.removeRow(i);
    }
}

public void Clear(javax.swing.JTable tabla)
{
    DefaultTableModel model = (DefaultTableModel) tabla.getModel();
    while(model.getRowCount() > 0){model.removeRow(0);}
    tabla.setModel(model);
}

public void setColumns(javax.swing.JTable tabla, String[] columnas)
{
    DefaultTableModel model = (DefaultTableModel) tabla.getModel();
    model.setColumnCount(0);
    for (int i = 0; i < (columnas.length); i++) {
        model.addColumn(columnas[i]);
    }
    tabla.setModel(model);
}
 
public  int  getSizeTable(javax.swing.JTable tabla){
    DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
    return modelo.getRowCount();
}

public  void  setRowTable(javax.swing.JTable tabla,int ini,int fila,Object [] dato){
    DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
    for(int i=ini;i<modelo.getColumnCount();i++){
    modelo.setValueAt(dato[i], fila, i);
    
    }
    tabla.setModel(modelo);
}
 public void ordenarColumna(javax.swing.JTable tabla)
 {
    TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(tabla.getModel());
    //lQueOrdena.addRowSorterListene(tabla);
    tabla.setRowSorter(elQueOrdena);
 }
// public void comboQuery(JComboBox combo,String query,String campo) throws SQLException{
//    ResultSet rs= Main.database.genericQuery(query);
//    combo.removeAllItems();
//    while(rs.next()){
//        combo.addItem(rs.getString(campo));
//    }
// }
// public void comboQueryAll(JComboBox combo,String query,String []campo,char separador) throws SQLException{
//    ResultSet rs= Main.database.genericQuery(query);
//    combo.removeAllItems();
//    while(rs.next()){
//        String aux="";
//        for(int i=0;i<campo.length;i++){
//            if(i==0){
//                aux+=rs.getString(campo[i]);
//            }else{
//                aux+=" "+separador+" "+rs.getString(campo[i]);
//            }
//        }
//        combo.addItem(aux);
//    }
// }
 
 public JTextField  getJtextfieldTable(JTable tabla,int row,int column ){
    return (JTextField) tabla.getCellRenderer(row, column);
 }
 Format validar=new Format();
 public void  numeroICelda(JTable tabla ,int row, int column, final int length){
     TableCellEditor  a= tabla.getCellEditor(row, column);
        final JTextField txt=(JTextField) a.getTableCellEditorComponent(tabla, 1, true,row, column);
        txt.addFocusListener(new FocusListener(){

         @Override
         public void focusGained(FocusEvent e) {
             validar.Validar_Pasar_Focus(txt);
         }

         @Override
         public void focusLost(FocusEvent e) {
           //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }
        });
        txt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //System.out.println("asdfasd------");
                validar.Validar_Key(e, new char[]{'0','1','2','3','4','5','6','7','8','9'},txt.getText());
                validar.Validar_Key_Lenght(e, txt.getText().length(), length);
            }

            @Override
            public void keyPressed(KeyEvent e) {
              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
             throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

         
        });
 }

  public void numeroFCelda(JTable tabla ,int row, int column,final int length){
     TableCellEditor  a= tabla.getCellEditor(row, column);
     final JTextField txt=(JTextField) a.getTableCellEditorComponent(tabla, 1, true,row, column);
     a.addCellEditorListener(new CellEditorListener() {

         @Override
         public void editingStopped(ChangeEvent e) {
            txt.setText("");
             // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public void editingCanceled(ChangeEvent e) {
          //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }
     });
     txt.addFocusListener(new FocusListener(){

         @Override
         public void focusGained(FocusEvent e) {
             validar.Validar_Pasar_Focus(txt);
         }

         @Override
         public void focusLost(FocusEvent e) {
           //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }
        });
        txt.addKeyListener(new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            //System.out.println("asdfasd------");
            validar.Validar_Key(e, new char[]{'0','1','2','3','4','5','6','7','8','9',','},txt.getText());
            validar.Validar_Key_Lenght(e, txt.getText().length(), length);
        }

        @Override
        public void keyPressed(KeyEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyReleased(KeyEvent e) {
      //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }


    });
 }
//  public  void agregarPress(JTable tabla,int deb,int cre, KeyEvent evt, JLabel label)
//    {
//      
//        int c = tabla.getSelectedColumn();
//        int r = tabla.getSelectedRow();
//        if(evt.getKeyChar()==127)
//        {
//            if(r!=0)
//            {
//                removeSelectedRow(tabla, r);
//                setConcepto(tabla,label);
//                
//            }
//        }
//        char caracter = evt.getKeyChar();
//        try {
//            if (((caracter > '0')
//                    || (caracter < '9'))
//                    && (caracter != '\b' /*corresponde a BACK_SPACE*/) && caracter != ',' && caracter != '\n' && c == deb || c == cre) {
//                evt.consume();  // ignorar el evento de teclado
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        
//        if((evt.getKeyChar()==10 || evt.getKeyChar()==9) && c == 0){
//            try {
//                 ResultSet rrr = Conectar.instruccion3.executeQuery("select nombre from cuentacontable where codigo = '"+tabla.getValueAt(r,c).toString()+"'");   
//                 while(rrr.next()){
//                     tabla.setValueAt(rrr.getString("nombre"),r,1); 
//                 }
//                 
//
//                 //masterTableCheque.changeSelection(r, 1, true, false);
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//
//        }
//
//      
//        if((evt.getKeyChar()==10 || evt.getKeyChar()==9) && c == cre){
//
//                
//                try {
//                    if (tabla.getValueAt(r, deb).toString().equals("0,00") && tabla.getValueAt(r, cre).toString().equals("0,00")) {
//                        System.out.println("deb y credi == 0");
//                        
//                    } 
//                    else
//                    {
//                     String valor = tabla.getValueAt(r, c).toString().replace(".", "").replace(",", ".");
//                    tabla.setValueAt((f.format(Double.valueOf(valor))), r, c);
//                    //masterTableCheque.setValueAt(f.format(Double.valueOf(masterTableCheque.getValueAt(r,c).toString().replace(".","").replace(",","."))).toString(),r,c);
//                    setConcepto(tabla, label); 
//                    if(r== tabla.getRowCount() - 1 && !label.getText().equals("Diferencia: 0,00"))
//                    Agregar(tabla,new Object[]{null,null,"0,00","0,00"});
//                    }
//
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null, "Valores no permitidos");
//                    tabla.setValueAt("", r, c);
//                    
//                    
//                }
//   
// 
//            
//            //masterTableCheque.changeSelection(r+1, 0, true, false);
//            //table.Agregar(masterTableCheque,new Object[]{null,null,"0,00","0,00"});
//        }
//        if((evt.getKeyChar()==10 || evt.getKeyChar()==9) && c == deb){
//            //masterTableCheque.setValueAt("0,00", r, 3);
//            try {
//            tabla.setValueAt((f.format(Double.valueOf(tabla.getValueAt(r,c).toString().replace(".","").replace(",",".")))),r,c);
//            setConcepto(tabla,label);                
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null,"Valores no permitidos");
//                 tabla.setValueAt("",r,c);
//              
//            }
//
//            
//            //masterTableCheque.changeSelection(r, 3, true, false);
//        }    
//    }
//    public void agregarClick(JTable tabla,int deb,int cre, JLabel label)
//    {
//        int r = tabla.getRowCount() - 1 ;
//        System.out.println(r);
//        try {
//          if(!tabla.getValueAt((tabla.getRowCount()-1), deb).toString().equals("") && !tabla.getValueAt((tabla.getRowCount()-1), cre).toString().equals(""))
//        {
//            System.out.println("No estan vacias");
//        
//                if(tabla.getValueAt((tabla.getRowCount()-1), deb).toString().equals("0,00") && tabla.getValueAt((tabla.getRowCount()-1), cre).toString().equals("0,00") )
//                    
//            {
//               
//               
//            }
//            else {
//                    if( !label.getText().equals("Diferencia: 0,00")) {
//                         Agregar(tabla,new Object[]{null,null,"0,00","0,00"});
//                System.out.println("diferentes de  0");
//            }
//            }
//        }   
//        } catch (Exception e) {
//        }    
//    }  
      private void setConcepto(JTable tabla, JLabel label)
    {
        double creditos = 0.0,debitos = 0.0,diferencia;
        for (int row = 0; row < tabla.getRowCount(); row++)
        {
              debitos = debitos + Double.valueOf(tabla.getValueAt(row, 2).toString().replace(".", "").replace(",", "."));
              creditos = creditos + Double.valueOf(tabla.getValueAt(row, 3).toString().replace(".", "").replace(",", ".")); 
        }
        diferencia = (debitos - creditos);
        if(diferencia<0){diferencia = diferencia * -1;}
        label.setText("Diferencia: "+f.format(diferencia));
    }
      public void insertCombo(JTable tabla,JComboBox combo, int col)
      {
           KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
           KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);  
           //new S15WorkingBackspace(combo);
           tabla.getColumnModel().getColumn(col).setCellEditor(new ComboBoxCellEditor(combo));
           InputMap im = tabla.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
           im.put(enter, im.get(tab));     
          
      }  
            public void insertNull(JTable tabla,JComboBox combo, int col)
      {
           KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
           KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);  
           //new S15WorkingBackspace(combo);
           //tabla.getColumnModel().getColumn(col).setCellEditor();
           InputMap im = tabla.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
           im.put(enter, im.get(tab));     
          
      } 
}

