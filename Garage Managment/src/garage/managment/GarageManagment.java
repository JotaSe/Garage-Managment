/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package garage.managment;

import com.jotase.garage.POJO.Customer;
import com.jotase.garage.POJO.Vehicle;
import com.jotase.garage.hibernate.Connection;
import com.jotase.garage.view.Home;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel;

/**
 *
 * @author <@jota_Segovia>
 */
public class GarageManagment {

    /**
     * @param args the command line arguments
     */
    public static Home home;
    //Controller control = new Controller();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new GraphiteLookAndFeel());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Substance Graphite failed to initialize");
                }
                home = new Home();
                home.setVisible(true);
            }
        });
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
