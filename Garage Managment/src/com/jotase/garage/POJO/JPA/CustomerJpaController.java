/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.POJO.JPA;

import com.jotase.garage.POJO.Customer;
import com.jotase.garage.POJO.JPA.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.jotase.garage.POJO.Vehicle;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author javierjose
 */
public class CustomerJpaController implements Serializable {

    public CustomerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Customer customer) {
        if (customer.getVehicles() == null) {
            customer.setVehicles(new HashSet<Vehicle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Vehicle> attachedVehicles = new HashSet<Vehicle>();
            for (Vehicle vehiclesVehicleToAttach : customer.getVehicles()) {
                vehiclesVehicleToAttach = em.getReference(vehiclesVehicleToAttach.getClass(), vehiclesVehicleToAttach.getIdVehicle());
                attachedVehicles.add(vehiclesVehicleToAttach);
            }
            customer.setVehicles(attachedVehicles);
            em.persist(customer);
            for (Vehicle vehiclesVehicle : customer.getVehicles()) {
                Customer oldCustomerOfVehiclesVehicle = vehiclesVehicle.getCustomer();
                vehiclesVehicle.setCustomer(customer);
                vehiclesVehicle = em.merge(vehiclesVehicle);
                if (oldCustomerOfVehiclesVehicle != null) {
                    oldCustomerOfVehiclesVehicle.getVehicles().remove(vehiclesVehicle);
                    oldCustomerOfVehiclesVehicle = em.merge(oldCustomerOfVehiclesVehicle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Customer customer) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer persistentCustomer = em.find(Customer.class, customer.getIdCostumer());
            Set<Vehicle> vehiclesOld = persistentCustomer.getVehicles();
            Set<Vehicle> vehiclesNew = customer.getVehicles();
            Set<Vehicle> attachedVehiclesNew = new HashSet<Vehicle>();
            for (Vehicle vehiclesNewVehicleToAttach : vehiclesNew) {
                vehiclesNewVehicleToAttach = em.getReference(vehiclesNewVehicleToAttach.getClass(), vehiclesNewVehicleToAttach.getIdVehicle());
                attachedVehiclesNew.add(vehiclesNewVehicleToAttach);
            }
            vehiclesNew = attachedVehiclesNew;
            customer.setVehicles(vehiclesNew);
            customer = em.merge(customer);
            for (Vehicle vehiclesOldVehicle : vehiclesOld) {
                if (!vehiclesNew.contains(vehiclesOldVehicle)) {
                    vehiclesOldVehicle.setCustomer(null);
                    vehiclesOldVehicle = em.merge(vehiclesOldVehicle);
                }
            }
            for (Vehicle vehiclesNewVehicle : vehiclesNew) {
                if (!vehiclesOld.contains(vehiclesNewVehicle)) {
                    Customer oldCustomerOfVehiclesNewVehicle = vehiclesNewVehicle.getCustomer();
                    vehiclesNewVehicle.setCustomer(customer);
                    vehiclesNewVehicle = em.merge(vehiclesNewVehicle);
                    if (oldCustomerOfVehiclesNewVehicle != null && !oldCustomerOfVehiclesNewVehicle.equals(customer)) {
                        oldCustomerOfVehiclesNewVehicle.getVehicles().remove(vehiclesNewVehicle);
                        oldCustomerOfVehiclesNewVehicle = em.merge(oldCustomerOfVehiclesNewVehicle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = customer.getIdCostumer();
                if (findCustomer(id) == null) {
                    throw new NonexistentEntityException("The customer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customer;
            try {
                customer = em.getReference(Customer.class, id);
                customer.getIdCostumer();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customer with id " + id + " no longer exists.", enfe);
            }
            Set<Vehicle> vehicles = customer.getVehicles();
            for (Vehicle vehiclesVehicle : vehicles) {
                vehiclesVehicle.setCustomer(null);
                vehiclesVehicle = em.merge(vehiclesVehicle);
            }
            em.remove(customer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Customer> findCustomerEntities() {
        return findCustomerEntities(true, -1, -1);
    }

    public List<Customer> findCustomerEntities(int maxResults, int firstResult) {
        return findCustomerEntities(false, maxResults, firstResult);
    }

    private List<Customer> findCustomerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Customer as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Customer findCustomer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Customer as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
