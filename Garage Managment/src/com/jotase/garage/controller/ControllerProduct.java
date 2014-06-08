/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.controller;

import com.jotase.garage.POJO.DAO.DAO;
import com.jotase.garage.POJO.Product;
import com.jotase.garage.view.ViewProducts;
import com.jotase.garage.view.ViewSearch;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author <@jota_Segovia>
 */
public class ControllerProduct extends Controller implements ControllerInterface<Product>{
    ViewProducts view;
    Product product;
    boolean EDITABLE = false;
    DAO<Product> dao = new DAO<>();
    boolean enabled = true;


    public ControllerProduct(ViewProducts aThis) {
        this.view = aThis;
    }

    

    @Override
    public void enable(boolean b) {
        EDITABLE = false;
        view.getjTextField1().setEnabled(b);
        view.getjCheckBox1().setEnabled(b);
        view.getjFormattedTextField1().setEnabled(b);
        view.getjFormattedTextField2().setEnabled(b);
        view.getjFormattedTextField3().setEnabled(b);
        view.getjFormattedTextField4().setEnabled(b);
        view.getjTextArea1().setEnabled(b);
        

    }

    @Override
    public void edit() {
        enabled = true;
    }

    @Override
    public void save(Product t) {
        System.out.println("t = " + t.getIdProduct());
        if (enabled) {
            if (EDITABLE) {
                System.out.println("update");

                if (dao.update(t)) {
                    clean();
                }
            } else {
                if (dao.insert(t)) {
                    clean();
                }

            }
        } else {
            setEditFirst();
        }

    }

    @Override
    public void delete(Product t) {
        if (EDITABLE) {
            dao.delete(t);
        } else {
            setEditFirst();
        }
    }

    @Override
    public List<Product> getList(String query) {
        return dao.getList("from product " + query);
    }

    @Override
    public void set(Product t) {
        enable(false);
        EDITABLE = true;
        product = t;
        view.getjTextField1().setText(product.getName());
        view.getjTextArea1().setText(product.getDescription());
        view.getjCheckBox1().setSelected(product.getIsService());
        view.getjFormattedTextField1().setText(product.getCost()+"");
        view.getjFormattedTextField2().setText(product.getVat()+"");
        view.getjFormattedTextField3().setText(product.getPrice()+"");
        view.getjFormattedTextField4().setText(product.getStock()+"");
       
    }

    @Override
    public Product get() {
        return product;
    }

    @Override
    public void clean() {
        product = null;
        view.getjTextField1().setText("");
        view.getjFormattedTextField1().setText("");
        view.getjFormattedTextField2().setText("");
        view.getjFormattedTextField3().setText("");
        view.getjFormattedTextField4().setText("");
        view.getjTextArea1().setText("");
        view.getjCheckBox1().setSelected(false);
        
    }

    @Override
    public void search() {
        String[] columns = {
            "", "name", "description"
        };
        ViewSearch search = new ViewSearch(null, true, this, columns);
        search.setVisible(true);
        System.out.println("asdasd");
        Product product = (Product) search.controller.getValue();

        if (product != null) {
            
            this.product = product;
            set(product);
        }
    }

    @Override
    public void setEditFirst() {
        JOptionPane.showMessageDialog(view, "You should press Edit button first");
    }


}
