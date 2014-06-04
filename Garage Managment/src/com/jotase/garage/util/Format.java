/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventanas.controller.utils;


import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author SONEVIEW
 */
public class Format {
    DecimalFormat entrada= new DecimalFormat("###,##0.00");
    DecimalFormat salida=new DecimalFormat("###,##0.00");
    public char [] num = {'0','1','2','3','4','5','6','7','8','9','.','-','J','V','E','j','v','e'};
    public double doubleEntrada(String number)
    {
        System.out.println("number ....= " + number);
        System.out.println(Double.valueOf((number.replace(".","").replace(",","."))));
        return Double.valueOf((number.replace(".","").replace(",",".")));
    }
    public double doubleEntrada(Object number)
    {   
        System.out.println(number.toString().replace("."," ").replace(",",".").replace(" ","."));
        return Double.valueOf(number.toString().replace(".","").replace(",","."));
    }    
    public String doubleSalida(Object number)
    {
        
        return entrada.format(number).replace(","," ").replace('.', ',').replace(" ",".");
    }
   
    public void Validar_Pasar_Focus(JTextField jText){
        jText.requestFocus();
        jText.setSelectionStart(0);
    }
    public void Validar_Pasar_Focus_Check(KeyEvent evt,Component com){
        if(evt.getKeyChar()==KeyEvent.VK_ENTER){
            com.requestFocus();
        }
    }
    public String cambioAComa(double can){
        return entrada.format(can);
    }
    public void validarNumeroF(JTextField txt){
        
        if(!Validar_Txt_Float_T(txt.getText())){
             txt.setText("0,0");
        
        
        }
    }
     public boolean validarNumeroI(JTextField txt){
        
        if(!Validar_Txt_Int_T(txt.getText()))
        {  txt.setText("0");
            return true;
        }
        return false;
        
    }
   
    public void validarPorc(JTextField txt,JComboBox cb){
        if(cb.getSelectedIndex()==0){
        if(Validar_Dev_Float_Ent(txt)>99.99f){
            JOptionPane.showMessageDialog(null, "EL porcentaje no debe de pasar el 99.99%");
        txt.setText("99,99");}
       } 
    }
    public void Validar_Pasar_Focus_Check(KeyEvent evt,JTextField com){
        if(evt.getKeyChar()==KeyEvent.VK_ENTER){
            com.requestFocus();
            com.setSelectionStart(0);
        }
    }
    
     public void Validar_Pasar_Focus_Check_Tab(KeyEvent evt,Component com){
        if(evt.getKeyChar()==KeyEvent.VK_TAB){
            com.requestFocus();
        }
    }
    public void Validar_Pasar_Focus_Check_Tab(KeyEvent evt,JTextField com){
        if(evt.getKeyChar()==KeyEvent.VK_TAB){
            com.requestFocus();
            com.setSelectionStart(0);
        }
    }
    
    public void Validar_Botton_Enter(KeyEvent evt,JButton com){
        if(evt.getKeyChar()==KeyEvent.VK_ENTER){
            com.doClick();
            
        }
    }
    
    //VALIDA FECHA DESDE Y HASTA
    public boolean Validar_Fecha(JTextField fdesde, JTextField fhasta)
    {
        if(fdesde.getText().compareTo(fhasta.getText())>0)
            return true;
        return false;
    }
    
    public  String  Retornar_el_String(JDateChooser cale, String format) {
       try{
        
        return new SimpleDateFormat(format).format(cale.getDate());
       
       }catch(Exception Ex){
           return "";
       }
       
           
       
        
    }
    
    public  boolean Validar_Nan(javax.swing.JTextField txt){
        String nan=txt.getText().replace(",", ".");
        if(Double.isNaN(Double.parseDouble(nan)+0.0f)||Double.isInfinite(Double.parseDouble(nan)+0.0f)){
            txt.setText("0,0");
            return true;
            
        }
        return false;
        
    }
    /*
     * 
     *@param validadcion con los numeros y el reemplazar el punto por la com
     */
    public void Validar_Key(java.awt.event.KeyEvent evt, char[] chars,String txt1)
    {    if(!txt1.contains(",")){
        if(evt.getKeyChar()==(int)'.'){
           evt.setKeyChar(',');}
         
        }else if(evt.getKeyChar()==(int)',')
                    evt.setKeyChar('.');
        
        char car = evt.getKeyChar();
        boolean np = false;
        int c=0;
        
        for (int i = 0; i < chars.length; i++) {
            if(car == chars[i]){np=true;}
       
        }
           
        if(!np){
            
            evt.consume();}
    }
    public  void Validar_Key(java.awt.event.KeyEvent evt, char[] chars)
    {
        char car = evt.getKeyChar();
        boolean np = false;
        int c=0;
        
        for (int i = 0; i < chars.length; i++) {
            if(car == chars[i]){np=true;}
       
        }
           
        if(!np){
            
            evt.consume();}
    }
    
    public  void Validar_Mayuscula(JTextField txt){
        
        String   carac=txt.getText();
        String Mcarac="";
        boolean flag=false;
        for(int i=0;i<carac.length();i++){
            if(i==0){
                Mcarac=carac.substring(0,1).toUpperCase()+carac.substring(1, carac.length());
                flag=true;
            }
            if(carac.charAt(i)==' '){
                if(i<carac.length()-1){
                 if(flag){
                    Mcarac=Mcarac.substring(0,i+1)+Mcarac.substring(i+1,i+2).toUpperCase()+Mcarac.substring((i+2), Mcarac.length());
                 }else{
                     Mcarac=carac.substring((i+1), (i+2)).toUpperCase()+carac.substring((i+2), carac.length());
                }
                }

            }

        }
        
         txt.setText(Mcarac);
        
    }
    
     public  void Validar_Key_SINSpace(java.awt.event.KeyEvent evt){
         String carac="ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz1234567890-/\\_";
         boolean np=false;
         char car = evt.getKeyChar();
         for(int i=0;i<carac.length();i++){
             if(carac.charAt(i)==car)
                 np=true;
         }
         if(!np) 
            evt.consume();
         
         
     }
     
     public  void Validar_Key(java.awt.event.KeyEvent evt){
         String carac="ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz1234567890-/\\_áéíóúÁÉÍÓÚ.,;: ";
         boolean np=false;
         char car = evt.getKeyChar();
         for(int i=0;i<carac.length();i++){
             if(carac.charAt(i)==car)
                 np=true;
         }
         if(!np) 
            evt.consume();
    }
     
    public String  Validar_Clave(javax.swing.JTextField evt){
         char carac[]=evt.getText().toCharArray();
        
         
         
         
         for(int i=0;i<carac.length;i++){
             if(carac[i]==(char)34)
                carac[i]=' ';
             if(carac[i]==(char)39)
                carac[i]=' ';
             
         }
         
         return String.valueOf(carac);
         
         
     }
    
   public  void Validar_Key_Lenght(java.awt.event.KeyEvent evt,int txt,int lenght)
    {     
        if(txt>lenght){evt.consume();}
    }
    public  void verificarTabla(final JTable tabla, final int d, final int c)
    {   
        tabla.addKeyListener(new KeyAdapter()
        {   
        public void keyTyped(KeyEvent e)
        {
            if(tabla.isCellEditable(tabla.getSelectedRow(), c)  || tabla.isCellEditable(tabla.getSelectedRow(), d))
            {
                System.out.println("AJA");
            char caracter = e.getKeyChar();

      // Verificar si la tecla pulsada no es un digito
            if(((caracter < '0') ||
              (caracter > '9')) &&
              (caracter != '\b' /*corresponde a BACK_SPACE*/) && caracter != ',' && caracter != '\n')
             {
             e.consume();  // ignorar el evento de teclado
             } 
            }
 
        }
        });
    }
    
    public  boolean Validar_Txt_Float(javax.swing.JTextField texto)
    {
        try{
            
            if(Float.parseFloat(String.valueOf(Validar_Dev_Float_Ent(texto.getText())))>= 0){texto.setForeground(Color.black);}
            return true;
        }catch(Exception e){
            
            
            
            return false;}
    }
    
    public  boolean Validar_Txt_Float(String texto)
    {
        try{  
            if(Float.parseFloat(String.valueOf(Validar_Dev_Float_Ent(texto)))>= 0){return true;}
            
        }catch(Exception e){return false;}
        return false;
    }
    public  boolean Validar_Txt_Float_T(String texto)
    {
        try{  
            if(Float.parseFloat(String.valueOf(Validar_Dev_Float_Ent(texto)))> 0){return true;}
            
        }catch(Exception e){return false;}
        return false;
    }
    
  
    
    public  float Validar_Dev_Float_Ent(javax.swing.JTextField texto){
      try{
        return entrada.parse(texto.getText()).floatValue();
        }catch(ParseException e){
          return 0.0f;
      }
    }
    
      public  float Validar_Dev_Float_Ent(String texto){
        try{
            return entrada.parse(texto).floatValue();
        }catch(ParseException e){
          return 0.0f;
        }
    }
       public  String Validar_Dev_Float_Sal(float texto){
        try{
          return salida.format(texto);
        }catch(Exception e){
          return "";
        }
    }
      
     public  double Validar_Dev_Double_Ent(javax.swing.JTextField texto){
       try{
          
           if(texto.getText().equals(""))
               return 0.0D;
           else
         return entrada.parse(texto.getText()).doubleValue();
       }catch(Exception e){
           e.printStackTrace();
          return 0.0D;
      }
    }
     
     public  double Validar_Dev_Double_Sal(String texto) {
       try{
           System.out.println(""+salida.format(texto));
         return salida.parse(texto).doubleValue();
       }catch(ParseException e){
           e.printStackTrace();
           return 0;
       }
     }
     
   
      
      public double Validar_Dev_Double_Ent(String texto){
        try{
            return entrada.parse(texto).doubleValue();
        }catch(Exception e){
            e.printStackTrace();
          return 0.0D;
      }
    }
      
      
      public  int Validar_Dev_Int(javax.swing.JTextField texto){
         try{
          
           if(texto.getText().equals(""))
               return 0;
           else
         return entrada.parse(texto.getText()).intValue();
       }catch(Exception e){
           e.printStackTrace();
          return 0;
      }
    }
      public  int Validar_Dev_Int(String texto){
         try{
          
           if(texto.equals(""))
               return 0;
           else
         return entrada.parse(texto).intValue();
       }catch(Exception e){
           e.printStackTrace();
          return 0;
      }
    }
      
    public  boolean Validar_Txt_Double(javax.swing.JTextField texto)
    {
        try{
            if(Validar_Dev_Double_Ent(texto) >= 0){texto.setForeground(Color.black);}
            return true;
        }catch(Exception e){texto.setForeground(Color.red);return false;}
    } 
    
     public  boolean Validar_Txt_Double(javax.swing.JSpinner texto)
    {
        try{
            if(Double.parseDouble(texto.getValue().toString().replace(',', '.')) >= 0){texto.setForeground(Color.black);}
            return true;
        }catch(Exception e){texto.setForeground(Color.red);return false;}
    }
    
    
    public  boolean Validar_Txt_Int(javax.swing.JTextField texto)
    {
        try{
            if(Integer.parseInt(texto.getText()) >= 0){texto.setForeground(Color.black);}
            
            return true;
        }catch(Exception e){texto.setForeground(Color.red);return false;}
    }
    public  boolean Validar_Txt_Int(String texto)
    {
        try{
            if(Integer.parseInt(texto) >= 0)
            return true;
        }catch(Exception e){return false;}
        return false;
    }
    public  boolean Validar_Txt_Int_T(String texto)
    {
        try{
            if(Validar_Txt_Int(texto))
            return true;
        }catch(Exception e){return false;}
        return false;
    }
    public void limpiarRojo(JPanel c){
        
        for(int i=0;i<c.getComponents().length;i++){
            if(c.getComponents()[i].equals(new JTextField())){
        JTextField aux=(JTextField)c.getComponents()[i];
        aux.setForeground(Color.black);}
        }
    }
    
   
    public void validarCelular(KeyEvent evt,JTextField jTextField3){
        char[] chars = {'0','1','2','3','4','5','6','7','8','9','+','-'};
        String txt=jTextField3.getText();
        int c=0;
        for(int i=0;i<txt.length();i++){
            if(txt.charAt(i)=='+'){
                c++;
            }
            if(c>0){
                if(evt.getKeyChar()=='+'){
                    evt.consume();
                }
            }
        }
        Validar_Key(evt,chars);
        Validar_Key_Lenght(evt, jTextField3.getText().length(),49);
    }
    public  boolean Validar_Txt_String(javax.swing.JTextField texto)
    {
        if(!texto.getText().equals("")){return true;}
        else{return false;}
    }
    
    public  boolean Validar_TxtArea_String(javax.swing.JTextArea texto)
    {
        if(!texto.getText().equals("")){return true;}
        else{return false;}
    }
    public  boolean Validar_jComboBox_NotNull(javax.swing.JComboBox combo)
    {
        System.out.println(combo.getName()+".SelectedIndex: "+combo.getSelectedIndex());
        if(combo.getSelectedIndex() >= 0){return true;}
        else{return false;}
    }
    public  boolean Validar_SpinField_Int(com.toedter.components.JSpinField texto)
    {
        try{
            texto.getValue();
            texto.setForeground(Color.black);
            return true;
        }
        catch(NumberFormatException e){return false;}
    }
    
}
