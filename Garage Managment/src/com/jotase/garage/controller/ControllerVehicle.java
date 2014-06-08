/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.controller;

import com.jotase.garage.POJO.DAO.DAO;
import com.jotase.garage.POJO.Vehicle;
import com.jotase.garage.POJO.Vehicletype;
import com.jotase.garage.hibernate.Connection;
import com.jotase.garage.view.ViewSearch;
import com.jotase.garage.view.ViewVehicle;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author <@jota_Segovia>
 */
public class ControllerVehicle extends Controller implements ControllerInterface<Vehicle> {

    ViewVehicle view;
    Vehicle vehicle;
    boolean EDITABLE = false;
    DAO<Vehicle> dao = new DAO<>();
    boolean enabled = true;

    public ControllerVehicle(ViewVehicle aThis) {
        this.view = aThis;
    }

    public void loadTypes() {
        List<Vehicletype> list = Connection.getInstance().getList("from Vehicletype");
        view.getjComboBox1().removeAllItems();
        for (Vehicletype type : list) {
            view.getjComboBox1().addItem(type.getName());
        }
    }

    @Override
    public void enable(boolean b) {
        EDITABLE = false;
        view.getjTextField1().setEnabled(b);
        view.getjTextField2().setEnabled(b);
        view.getjTextField3().setEnabled(b);
        view.getjTextField4().setEnabled(b);
        view.getjTextField6().setEnabled(b);
        view.getjComboBox1().setEnabled(b);
        view.getjFormattedTextField1().setEnabled(b);
        view.getjTextField5().setEnabled(b);

    }

    @Override
    public void edit() {
        enabled = true;
    }

    @Override
    public void save(Vehicle t) {
        t.setVehicletype((Vehicletype) Connection.getInstance().get(t, "where name ='" + t.getVehicletype().getName() + ""));

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
    public void delete(Vehicle t) {
        if (EDITABLE) {
            dao.delete(t);
        } else {
            setEditFirst();
        }
    }

    @Override
    public List<Vehicle> getList(String query) {
        return dao.getList("from vehicle " + query);
    }

    @Override
    public void set(Vehicle t) {
        enable(false);
        EDITABLE = true;
        vehicle = t;
        view.getjTextField1().setText(vehicle.getRegistrationNumber());
        view.getjTextField2().setText(vehicle.getModel());
        view.getjTextField3().setText(vehicle.getColor());
        view.getjTextField4().setText(vehicle.getConstructionsYear() + "");
        //view.getjTextField6().setText(vehicle.getPostalCode());
        view.getjFormattedTextField1().setText(vehicle.getKmTraveled() + "");
        view.getjComboBox1().setSelectedItem(vehicle.getVehicletype().getName());
        //view.getjTextField8().setText(vehicle.getPhone());
        // view.getjTextField9().setText(vehicle.getPhone2());
    }

    @Override
    public Vehicle get() {
        return vehicle;
    }

    @Override
    public void clean() {
        vehicle = null;
        view.getjTextField1().setText("");
        view.getjTextField2().setText("");
        view.getjTextField3().setText("");
        view.getjTextField4().setText("");
        view.getjTextField6().setText("");
        view.getjTextField5().setText("");
        view.getjFormattedTextField1().setText("");
        
    }

    @Override
    public void search() {
        String[] columns = {
            "", "name", "idNumber", "email"
        };
        ViewSearch search = new ViewSearch(null, true, this, columns);
        search.setVisible(true);
        System.out.println("asdasd");
        Vehicle vehicle = (Vehicle) search.controller.getValue();

        if (vehicle != null) {

            this.vehicle = vehicle;
            set(vehicle);
        }
    }

    @Override
    public void setEditFirst() {
        JOptionPane.showMessageDialog(view, "You should press Edit button first");
    }
}
