/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.POJO.JPA;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.jotase.garage.POJO.Customer;
import com.jotase.garage.POJO.Vehicletype;
import com.jotase.garage.POJO.Intervention;
import com.jotase.garage.POJO.JPA.exceptions.NonexistentEntityException;
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
public class VehicleJpaController implements Serializable {

    public VehicleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vehicle vehicle) {
        if (vehicle.getInterventions() == null) {
            vehicle.setInterventions(new HashSet<Intervention>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customer = vehicle.getCustomer();
            if (customer != null) {
                customer = em.getReference(customer.getClass(), customer.getIdCostumer());
                vehicle.setCustomer(customer);
            }
            Vehicletype vehicletype = vehicle.getVehicletype();
            if (vehicletype != null) {
                vehicletype = em.getReference(vehicletype.getClass(), vehicletype.getId());
                vehicle.setVehicletype(vehicletype);
            }
            Set<Intervention> attachedInterventions = new HashSet<Intervention>();
            for (Intervention interventionsInterventionToAttach : vehicle.getInterventions()) {
                interventionsInterventionToAttach = em.getReference(interventionsInterventionToAttach.getClass(), interventionsInterventionToAttach.getId());
                attachedInterventions.add(interventionsInterventionToAttach);
            }
            vehicle.setInterventions(attachedInterventions);
            em.persist(vehicle);
            if (customer != null) {
                customer.getVehicles().add(vehicle);
                customer = em.merge(customer);
            }
            if (vehicletype != null) {
                vehicletype.getVehicles().add(vehicle);
                vehicletype = em.merge(vehicletype);
            }
            for (Intervention interventionsIntervention : vehicle.getInterventions()) {
                Vehicle oldVehicleOfInterventionsIntervention = interventionsIntervention.getVehicle();
                interventionsIntervention.setVehicle(vehicle);
                interventionsIntervention = em.merge(interventionsIntervention);
                if (oldVehicleOfInterventionsIntervention != null) {
                    oldVehicleOfInterventionsIntervention.getInterventions().remove(interventionsIntervention);
                    oldVehicleOfInterventionsIntervention = em.merge(oldVehicleOfInterventionsIntervention);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vehicle vehicle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehicle persistentVehicle = em.find(Vehicle.class, vehicle.getIdVehicle());
            Customer customerOld = persistentVehicle.getCustomer();
            Customer customerNew = vehicle.getCustomer();
            Vehicletype vehicletypeOld = persistentVehicle.getVehicletype();
            Vehicletype vehicletypeNew = vehicle.getVehicletype();
            Set<Intervention> interventionsOld = persistentVehicle.getInterventions();
            Set<Intervention> interventionsNew = vehicle.getInterventions();
            if (customerNew != null) {
                customerNew = em.getReference(customerNew.getClass(), customerNew.getIdCostumer());
                vehicle.setCustomer(customerNew);
            }
            if (vehicletypeNew != null) {
                vehicletypeNew = em.getReference(vehicletypeNew.getClass(), vehicletypeNew.getId());
                vehicle.setVehicletype(vehicletypeNew);
            }
            Set<Intervention> attachedInterventionsNew = new HashSet<Intervention>();
            for (Intervention interventionsNewInterventionToAttach : interventionsNew) {
                interventionsNewInterventionToAttach = em.getReference(interventionsNewInterventionToAttach.getClass(), interventionsNewInterventionToAttach.getId());
                attachedInterventionsNew.add(interventionsNewInterventionToAttach);
            }
            interventionsNew = attachedInterventionsNew;
            vehicle.setInterventions(interventionsNew);
            vehicle = em.merge(vehicle);
            if (customerOld != null && !customerOld.equals(customerNew)) {
                customerOld.getVehicles().remove(vehicle);
                customerOld = em.merge(customerOld);
            }
            if (customerNew != null && !customerNew.equals(customerOld)) {
                customerNew.getVehicles().add(vehicle);
                customerNew = em.merge(customerNew);
            }
            if (vehicletypeOld != null && !vehicletypeOld.equals(vehicletypeNew)) {
                vehicletypeOld.getVehicles().remove(vehicle);
                vehicletypeOld = em.merge(vehicletypeOld);
            }
            if (vehicletypeNew != null && !vehicletypeNew.equals(vehicletypeOld)) {
                vehicletypeNew.getVehicles().add(vehicle);
                vehicletypeNew = em.merge(vehicletypeNew);
            }
            for (Intervention interventionsOldIntervention : interventionsOld) {
                if (!interventionsNew.contains(interventionsOldIntervention)) {
                    interventionsOldIntervention.setVehicle(null);
                    interventionsOldIntervention = em.merge(interventionsOldIntervention);
                }
            }
            for (Intervention interventionsNewIntervention : interventionsNew) {
                if (!interventionsOld.contains(interventionsNewIntervention)) {
                    Vehicle oldVehicleOfInterventionsNewIntervention = interventionsNewIntervention.getVehicle();
                    interventionsNewIntervention.setVehicle(vehicle);
                    interventionsNewIntervention = em.merge(interventionsNewIntervention);
                    if (oldVehicleOfInterventionsNewIntervention != null && !oldVehicleOfInterventionsNewIntervention.equals(vehicle)) {
                        oldVehicleOfInterventionsNewIntervention.getInterventions().remove(interventionsNewIntervention);
                        oldVehicleOfInterventionsNewIntervention = em.merge(oldVehicleOfInterventionsNewIntervention);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vehicle.getIdVehicle();
                if (findVehicle(id) == null) {
                    throw new NonexistentEntityException("The vehicle with id " + id + " no longer exists.");
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
            Vehicle vehicle;
            try {
                vehicle = em.getReference(Vehicle.class, id);
                vehicle.getIdVehicle();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vehicle with id " + id + " no longer exists.", enfe);
            }
            Customer customer = vehicle.getCustomer();
            if (customer != null) {
                customer.getVehicles().remove(vehicle);
                customer = em.merge(customer);
            }
            Vehicletype vehicletype = vehicle.getVehicletype();
            if (vehicletype != null) {
                vehicletype.getVehicles().remove(vehicle);
                vehicletype = em.merge(vehicletype);
            }
            Set<Intervention> interventions = vehicle.getInterventions();
            for (Intervention interventionsIntervention : interventions) {
                interventionsIntervention.setVehicle(null);
                interventionsIntervention = em.merge(interventionsIntervention);
            }
            em.remove(vehicle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vehicle> findVehicleEntities() {
        return findVehicleEntities(true, -1, -1);
    }

    public List<Vehicle> findVehicleEntities(int maxResults, int firstResult) {
        return findVehicleEntities(false, maxResults, firstResult);
    }

    private List<Vehicle> findVehicleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Vehicle as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Vehicle findVehicle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vehicle.class, id);
        } finally {
            em.close();
        }
    }

    public int getVehicleCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Vehicle as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
