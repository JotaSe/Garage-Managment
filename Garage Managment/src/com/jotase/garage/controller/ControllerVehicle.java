/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.controller;

import com.jotase.garage.POJO.Customer;
import com.jotase.garage.POJO.DAO.DAO;
import com.jotase.garage.POJO.Vehicle;
import com.jotase.garage.POJO.Vehicletype;
import com.jotase.garage.hibernate.Connection;
import com.jotase.garage.util.Archivo;
import com.jotase.garage.view.ViewSearch;
import com.jotase.garage.view.ViewVehicle;
import java.awt.Dimension;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
    Customer customer;

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
        view.getjDateChooser1().setEnabled(b);
        view.getjTextField6().setEnabled(b);
        view.getjComboBox1().setEnabled(b);
        view.getjFormattedTextField1().setEnabled(b);
        view.getjTextField5().setEnabled(b);
        view.getjTextArea1().setEnabled(b);

    }

    @Override
    public void edit() {
        enabled = true;
    }

    @Override
    public void save(Vehicle t) {
        t.setVehicletype((Vehicletype) Connection.getInstance().get("from Vehicletype where name ='" + t.getVehicletype().getName() + "'"));
        t.setCustomer(customer);
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
        view.getjDateChooser1().setDate(vehicle.getConstructionsYear());
        //view.getjTextField6().setText(vehicle.getPostalCode());
        view.getjFormattedTextField1().setText(vehicle.getKmTraveled() + "");
        view.getjComboBox1().setSelectedItem(vehicle.getVehicletype().getName());
        if (vehicle.getImage() != null) {
            view.getjLabel5().setText("");
            ImageIcon image = new ImageIcon(vehicle.getImage());
            view.getjLabel5().setIcon(image);
        }
        view.getjTextArea1().setText(vehicle.getNotes());
        customer = vehicle.getCustomer();
        view.getjTextField5().setText(customer.getIdNumber());
        view.getjTextField6().setText(customer.getName() + " " + customer.getLastName());
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
        view.getjFormattedTextField1().setText("");
        view.getjTextField6().setText("");
        view.getjTextField5().setText("");
        view.getjFormattedTextField1().setText("");
        view.getjTextArea1().setText("");
        view.getjLabel5().setText("No Image");

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

    public void checkCustomer() {
        customer = new Customer();
        customer.setIdNumber(view.getjTextField5().getText());
        customer = (Customer) Connection.getInstance().get("from Customer where idNumber ='" + customer.getIdNumber() + "'");
        if (customer == null) {
            searchForCustomer();
        } else {
            setCustomer();
        }
    }

    public void searchForCustomer() {
        String[] columns = {
            "", "name"
        };
        ViewSearch search = new ViewSearch(null, true, new ControllerCustomer(null), columns);
        search.setVisible(true);
        System.out.println("asdasd");
        Customer customer = (Customer) search.controller.getValue();

        if (customer != null) {

            this.customer = customer;
            setCustomer();
        }

    }

    private void setCustomer() {
        view.getjTextField5().setText(customer.getIdNumber());
        view.getjTextField6().setText(customer.getName() + " " + customer.getLastName());
    }

    public void setImage() {
        System.out.println("najarse");
        Archivo a = new Archivo();
        File f = new File(a.getDir("Imagen", "PNG image (*.png) ", "png"));


        byte[] imgDataBa = new byte[(int) f.length()];
        try {
            FileInputStream fis = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int length = fis.available();
            imgDataBa = new byte[length];
            for (int readNum; (readNum = fis.read(imgDataBa)) != -1;) {
                bos.write(imgDataBa, 0, readNum);
                System.out.println("read " + readNum + " bytes,");
            }
            imgDataBa = bos.toByteArray();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ControllerVehicle.class.getName()).log(Level.SEVERE, null, ex);
        }



        vehicle = (get() != null) ? get() : new Vehicle();
        vehicle.setImage(imgDataBa);
        ImageIcon imgThisImg = null;

        imgThisImg = new ImageIcon(imgDataBa);



        view.getjLabel5().setText("");
        view.getjLabel5().setIcon(imgThisImg);



    }

    private void resize(ImageIcon imgThisImg, Dimension d) {
    }
}
