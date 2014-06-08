/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.controller;

import com.jotase.garage.POJO.Customer;
import com.jotase.garage.POJO.DAO.CustomerDAO;
import com.jotase.garage.POJO.DAO.DAO;
import com.jotase.garage.POJO.Product;
import com.jotase.garage.hibernate.Connection;
import com.jotase.garage.view.ViewSearch;

import com.ventanas.controller.utils.Tabla;
import java.util.List;

/**
 *
 * @author <@jota_Segovia>
 */
public class ControllerSearch {

    Controller controller;
    Object value;
    String[] columns;
    ViewSearch view;
    Tabla table = new Tabla();

    public ControllerSearch(Controller controller, String[] columns, ViewSearch view) {
        this.controller = controller;
        this.columns = columns;
        this.view = view;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
        this.view.dispose();
    }

    public void addFilters(String[] columns) {
        this.view.getjComboBox1().removeAllItems();
        this.view.getjComboBox1().addItem("All");
        for (int i = 0; i < columns.length; i++) {
            if (!columns[i].isEmpty()) {
                this.view.getjComboBox1().addItem(columns[i]);
            }
        }
    }

    public void search(String filter) {
        table.Clear(this.view.getjTable1());
        String query;
        String where = (this.view.getjComboBox1().getSelectedItem().toString().equalsIgnoreCase("all"))
                ? null : this.view.getjComboBox1().getSelectedItem().toString();
        table.setColumns(this.view.getjTable1(), columns);
        if (controller instanceof ControllerCustomer) {
            query = "from Customer ";
            if (where != null) {
                query = query + " where " + where + " Like '%" + filter + "%'";
            }
            List<Customer> list = Connection.getInstance().getList(query);

            for (int i = 0; i < list.size(); i++) {
                Customer customer = list.get(i);
                customer.getIdCostumer();
                Object[] row = {
                    customer,
                    customer.getName() + " " + customer.getLastName(),
                    customer.getIdNumber(),
                    customer.getEmail()
                };
                table.Agregar(this.view.getjTable1(), row);
            }


        } else if (controller instanceof ControllerProduct) {
            query = "from Product ";
            if (where != null) {
                query = query + " where " + where + " Like '%" + filter + "%'";
            }
            List<Product> list = Connection.getInstance().getList(query);
            //
            for (Product product : list) {
                Object[] row = {
                    product,
                    product.getName(),
                    product.getDescription()
                };
                table.Agregar(this.view.getjTable1(), row);
            }

        }

        this.view.getjTable1().getColumnModel().getColumn(0).setMaxWidth(0);
    }
}
