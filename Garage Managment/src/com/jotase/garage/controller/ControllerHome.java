/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.controller;

import com.jotase.garage.view.View;
import com.jotase.garage.view.ViewCustomer;
import com.jotase.garage.view.ViewIntervention;
import com.jotase.garage.view.ViewProducts;
import com.jotase.garage.view.ViewVehicle;
import javax.swing.JInternalFrame;

/**
 *
 * @author <@jota_Segovia>
 */
public class ControllerHome {

    ViewCustomer viewCustomer;
    ViewIntervention viewIntervention;
    ViewProducts viewProducts;
    ViewVehicle viewVehicle;

    public void show(View view) {
        switch (view) {
            case Costumer:
                viewCustomer = new ViewCustomer();
                viewCustomer.setVisible(true);
                break;
            case Intervention:
                viewIntervention = new ViewIntervention();
                viewIntervention.setVisible(true);
                break;
            case Products:
                viewProducts = new ViewProducts();
                viewProducts.setVisible(true);
                break;
            case Vehicle:
                viewVehicle = new ViewVehicle();
                break;


        }
    }
}
