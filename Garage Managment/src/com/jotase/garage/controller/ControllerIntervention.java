/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.controller;

import com.jotase.garage.POJO.Customer;
import com.jotase.garage.POJO.DAO.DAO;
import com.jotase.garage.POJO.Intervention;
import com.jotase.garage.POJO.InterventionHasProducts;
import com.jotase.garage.POJO.InterventionHasProductsId;
import com.jotase.garage.POJO.Invoice;
import com.jotase.garage.POJO.Product;
import com.jotase.garage.POJO.Vehicle;
import com.jotase.garage.hibernate.Connection;
import com.jotase.garage.util.Print;
import com.jotase.garage.view.ViewCustomer;
import com.jotase.garage.view.ViewIntervention;
import com.jotase.garage.view.ViewSearch;
import com.ventanas.controller.utils.Tabla;
import garage.managment.GarageManagment;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author <@jota_Segovia>
 */
public class ControllerIntervention extends Controller implements ControllerInterface<Intervention> {

    ViewIntervention view;
    Intervention intervention;
    boolean EDITABLE = false;
    DAO<Intervention> dao = new DAO<>();
    boolean enabled = true;
    Tabla table = new Tabla();
    Vehicle vehicle;

    public ControllerIntervention(ViewIntervention aThis) {
        this.view = aThis;

    }

    @Override
    public void enable(boolean b) {
        EDITABLE = false;
        view.getjTextField1().setEnabled(b);
        view.getjTextField2().setEnabled(b);
        view.getjTextField3().setEnabled(b);
        view.getjDateChooser1().setEnabled(b);
        view.getjDateChooser2().setEnabled(b);
        view.getjFormattedTextField1().setEnabled(b);
        view.getjFormattedTextField2().setEnabled(b);
        view.getjFormattedTextField3().setEnabled(b);
        view.getjButton5().setEnabled(b);
        view.getjButton6().setEnabled(b);

    }

    @Override
    public void edit() {
        enabled = true;
    }

    @Override
    public void save(Intervention t) {
        //addProducts();

        if (enabled) {
            if (EDITABLE) {
                System.out.println("update");

                if (dao.update(t)) {
                }
            } else {
                if (dao.insert(t)) {
                }

            }


            addProducts();
            if (!getInstance().getIsFinished()) {
                setBill();
            } else {
                clean();
            }

        } else {
            setEditFirst();
        }

    }

    @Override
    public void delete(Intervention t) {
        if (EDITABLE) {
            dao.delete(t);
        } else {
            setEditFirst();
        }
    }

    @Override
    public List<Intervention> getList(String query) {
        return dao.getList("from intervention " + query);
    }

    @Override
    public void set(Intervention t) {
        enable(false);
        EDITABLE = true;
        intervention = t;
        setVehicle();
        setProducts();
        // view.getjTextArea2().setText(intervention.getPhone2());
        calculateSubTotal();
    }

    @Override
    public Intervention get() {
        return intervention;
    }

    @Override
    public void clean() {
        intervention = null;
        view.getjTextField1().setText("");
        view.getjTextField2().setText("");
        view.getjTextField3().setText("");
        view.getjDateChooser1().cleanup();
        view.getjDateChooser2().cleanup();
        view.getjTextArea1().setText("");
        view.getjTextArea2().setText("");
        view.getjFormattedTextField2().setText("");
        view.getjFormattedTextField3().setText("1");
        view.getjFormattedTextField1().setText("");
        table.Clear(view.getjTable1());
    }

    @Override
    public void search() {
        String[] columns = {
            "", "name", "idNumber"
        };
        ViewSearch search = new ViewSearch(null, true, this, columns);
        search.setVisible(true);
        System.out.println("asdasd");
        Intervention intervention = (Intervention) search.controller.getValue();

        if (intervention != null) {
            System.out.println("id : " + intervention.getId());
            this.intervention = intervention;
            set(intervention);
        }
    }

    @Override
    public void setEditFirst() {
        JOptionPane.showMessageDialog(view, "You should press Edit button first");
    }

    public void checkProduct() {
        Product p = new Product();
        p.setName(view.getjTextField3().getText());
        p = (Product) Connection.getInstance().get("from Product where name ='" + p.getName() + "'");
        if (p == null) {
            searchForProduct();
        } else {
            view.getjTextField3().setText(p.getName());
            view.getjTextArea2().setText(p.getDescription());
            addProduct(p, null);
        }
    }

    public void searchForProduct() {
        String[] columns = {
            "", "name"
        };
        ViewSearch search = new ViewSearch(null, true, new ControllerProduct(null), columns);
        search.setVisible(true);
        System.out.println("asdasd");
        Product product = (Product) search.controller.getValue();

        if (product != null) {

            addProduct(product, null);
        }

    }

    public void checkVehicle() {
        vehicle = new Vehicle();
        vehicle.setRegistrationNumber(view.getjTextField1().getText());
        vehicle = (Vehicle) Connection.getInstance().get("from Vehicle where registrationNumber ='" + vehicle.getRegistrationNumber() + "'");
        if (vehicle == null) {
            searchForVehicle();
        } else {
            getInstance().setVehicle(vehicle);
            setVehicle();
        }
    }

    public void searchForVehicle() {

        String[] columns = {
            "", "name"
        };
        ViewSearch search = new ViewSearch(null, true, new ControllerVehicle(null), columns);
        search.setVisible(true);

        vehicle = (Vehicle) search.controller.getValue();

        if (vehicle != null) {

            getInstance().setVehicle(vehicle);
            setVehicle();
        }

    }

    public Intervention getInstance() {
        intervention = (intervention != null) ? intervention : new Intervention();
        return intervention;
    }

    public void addProduct(Product product, InterventionHasProducts ihp) {

        ihp = (ihp != null) ? ihp : new InterventionHasProducts(null,
                product,
                getInstance(),
                Double.valueOf(view.getjFormattedTextField3().getText()));
        ihp.setProduct(product);

        Object[] row = {
            ihp,
            ihp.getProduct().getName(),
            ihp.getProduct().getDescription(),
            ihp.getQty(),
            product.getPrice(),
            Double.valueOf(view.getjFormattedTextField3().getText()) * product.getPrice()
        };
        table.Agregar(view.getjTable1(), row);
        calculateSubTotal();
    }

    public void removeProduct() {
        table.removeSelectedRow(view.getjTable1(),
                view.getjTable1().getSelectedRow());
    }

    private void addProducts() {
        Set<InterventionHasProducts> set = new HashSet<>();
        for (int i = 0; i < view.getjTable1().getRowCount(); i++) {
            InterventionHasProducts ihp = (InterventionHasProducts) view.getjTable1().getValueAt(i, 0);
            ihp.setId(new InterventionHasProductsId(intervention.getId(), ihp.getProduct().getIdProduct()));
            Connection.getInstance().insert(ihp);
            set.add(ihp);
        }
        getInstance().setInterventionHasProductses(set);
    }

    private void calculateSubTotal() {
        double subtotal = 0.0d;
        for (int i = 0; i < view.getjTable1().getRowCount(); i++) {
            subtotal += (Double) view.getjTable1().getValueAt(i, view.getjTable1().getColumnCount() - 1);
        }
        view.getjFormattedTextField1().setText(subtotal + "");
        view.getjFormattedTextField2().setText((subtotal * GarageManagment.home.vat) + "");
    }

    private void setVehicle() {
        Customer customer = intervention.getVehicle().getCustomer();
        view.getjTextField1().setText(intervention.getVehicle().getRegistrationNumber());
        view.getjTextField2().setText(customer.getName() + " " + customer.getLastName());
        view.getjTextArea1().setText(intervention.getVehicle().getNotes());
    }

    private void setProducts() {
        for (InterventionHasProducts ihp : getInstance().getInterventionHasProductses()) {
            addProduct(ihp.getProduct(), ihp);
        }
    }

    public void setBill() {
        if (!getInstance().getIsFinished()) {
            int i = JOptionPane.showConfirmDialog(view, "Do you want to charge this intervention?");
            if (i == 0) {
                Invoice invoice = new Invoice(intervention,
                        new Date(),
                        view.getjTextArea1().getText(),
                        Double.valueOf(view.getjFormattedTextField1().getText()),
                        Double.valueOf(view.getjFormattedTextField2().getText()));
                Connection.getInstance().insert(invoice);
                print();

            }
        } else {
            JOptionPane.showMessageDialog(view, "This intervention is not saved");
        }

    }

    public void print() {
        Invoice invoice = new Invoice(intervention,
                new Date(),
                view.getjTextArea1().getText(),
                Double.valueOf(view.getjFormattedTextField1().getText()),
                Double.valueOf(view.getjFormattedTextField2().getText()));
        Print print = new Print();
        print.printInvoice(invoice);
    }
    public void sentToEmail(){
        
    }
}
