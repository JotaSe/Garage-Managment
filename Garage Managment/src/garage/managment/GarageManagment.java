/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package garage.managment;

import com.jotase.garage.POJO.Vehicle;
import com.jotase.garage.hibernate.Connection;
import java.util.List;

/**
 *
 * @author <@jota_Segovia>
 */
public class GarageManagment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Vehicle> list = Connection.getInstance().getList("from vehicle");
        for(Vehicle vehicle : list){
            System.out.println("vehicle = " + vehicle);
        }
        // TODO code application logic here
    }
}
