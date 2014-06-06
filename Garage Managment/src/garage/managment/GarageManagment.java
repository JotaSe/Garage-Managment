/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package garage.managment;

import com.jotase.garage.POJO.Customer;
import com.jotase.garage.POJO.Vehicle;
import com.jotase.garage.hibernate.Connection;
import com.jotase.garage.view.Home;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author <@jota_Segovia>
 */
public class GarageManagment {

    /**
     * @param args the command line arguments
     */
    public static Home home;
    public static void main(String[] args) {
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                home = new Home();
                home.setVisible(true);
               // BasicConfigurator.configure();

            }
        });
        
        
        
        
//        insertVehicle();
//        List<Vehicle> list = Connection.getInstance().getList("from Vehicle v");
//        for (Vehicle vehicle : list) {
//            System.out.println("vehicle = " + vehicle.getRegistrationNumber());
//        }
//        System.out.println(" \n\nCustomer : ");
//        List<Customer> listc = Connection.getInstance().getList("from Customer");
//        for (Customer customer : listc) {
//            System.out.println("Customer ;"+customer.getName() +" "+customer.getLastName() );
//        }
        // TODO code application logic here
    }

    static void insertVehicle() {
        Vehicle v = new Vehicle();
        v.setRegistrationNumber("xxxx-232");
        v.setColor("blue");
        Connection.getInstance().insert(v);
        Customer c = new Customer();
        c.setName("Elvio");
        c.setLastName("Lado");
        Connection.getInstance().insert(c);


    }
}
