/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.controller;

import com.jotase.garage.POJO.Customer;
import com.jotase.garage.POJO.DAO.DAO;
import com.jotase.garage.view.ViewSearch;
import com.jotase.garage.view.ViewCustomer;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author <@jota_Segovia>
 */
public class ControllerCustomer extends Controller implements ControllerInterface<Customer> {

    ViewCustomer view;
    Customer customer;
    boolean EDITABLE = false;
    DAO<Customer> dao = new DAO<>();
    boolean enabled = true;

    public ControllerCustomer(ViewCustomer view) {
        this.view = view;
    }

    @Override
    public void enable(boolean b) {
        EDITABLE = false;
        view.getjTextField1().setEnabled(b);
        view.getjTextField2().setEnabled(b);
        view.getjTextField3().setEnabled(b);
        view.getjTextField4().setEnabled(b);
        view.getjTextField6().setEnabled(b);
        view.getjTextField7().setEnabled(b);
        view.getjTextField8().setEnabled(b);
        view.getjTextField9().setEnabled(b);

    }

    @Override
    public void edit() {
        enabled = true;
    }

    @Override
    public void save(Customer t) {
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
    public void delete(Customer t) {
        if (EDITABLE) {
            dao.delete(t);
        } else {
            setEditFirst();
        }
    }

    @Override
    public List<Customer> getList(String query) {
        return dao.getList("from customer " + query);
    }

    @Override
    public void set(Customer t) {
        enable(false);
        EDITABLE = true;
        customer = t;
        view.getjTextField1().setText(customer.getName());
        view.getjTextField2().setText(customer.getLastName());
        view.getjTextField3().setText(customer.getIdNumber());
        view.getjTextField4().setText(customer.getAddress());
        view.getjTextField6().setText(customer.getPostalCode());
        view.getjTextField7().setText(customer.getEmail());
        view.getjTextField8().setText(customer.getPhone());
        view.getjTextField9().setText(customer.getPhone2());
    }

    @Override
    public Customer get() {
        return customer;
    }

    @Override
    public void clean() {
        customer = null;
        view.getjTextField1().setText("");
        view.getjTextField2().setText("");
        view.getjTextField3().setText("");
        view.getjTextField4().setText("");
        view.getjTextField6().setText("");
        view.getjTextField7().setText("");
        view.getjTextField8().setText("");
        view.getjTextField9().setText("");
    }

    @Override
    public void search() {
        String[] columns = {
            "", "name", "idNumber", "email"
        };
        ViewSearch search = new ViewSearch(null, true, this, columns);
        search.setVisible(true);
        System.out.println("asdasd");
        Customer customer = (Customer) search.controller.getValue();

        if (customer != null) {
            System.out.println("id : " + customer.getIdCostumer());
            this.customer = customer;
            set(customer);
        }
    }

    @Override
    public void setEditFirst() {
        JOptionPane.showMessageDialog(view, "You should press Edit button first");
    }
}
