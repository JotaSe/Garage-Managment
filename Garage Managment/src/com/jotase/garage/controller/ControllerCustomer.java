/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.controller;

import com.jotase.garage.POJO.Customer;
import com.jotase.garage.POJO.DAO.DAO;
import com.jotase.garage.view.ViewCustomer;
import java.util.List;

/**
 *
 * @author <@jota_Segovia>
 */
public class ControllerCustomer implements ControllerInterface<Customer> {

    ViewCustomer view;
    Customer customer;
    boolean EDITABLE = false;
    DAO<Customer> dao = new DAO<>();

    public ControllerCustomer(ViewCustomer view) {
        this.view = view;
    }

    @Override
    public void enable(boolean b) {
        EDITABLE = b;
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
        EDITABLE = true;
    }

    @Override
    public void save(Customer t) {
        dao.update(t);
    }

    @Override
    public void delete(Customer t) {
        dao.delete(t);
    }

    @Override
    public List<Customer> getList(String query) {
        return dao.getList("from customer " + query);
    }

    @Override
    public void set(Customer t) {
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
    public void search(){
        
    }
}
