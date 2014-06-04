/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.POJO.JPA;

import com.jotase.garage.POJO.JPA.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.jotase.garage.POJO.Vehicle;
import com.jotase.garage.POJO.Vehicletype;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author javierjose
 */
public class VehicletypeJpaController implements Serializable {

    public VehicletypeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vehicletype vehicletype) {
        if (vehicletype.getVehicles() == null) {
            vehicletype.setVehicles(new HashSet<Vehicle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Vehicle> attachedVehicles = new HashSet<Vehicle>();
            for (Vehicle vehiclesVehicleToAttach : vehicletype.getVehicles()) {
                vehiclesVehicleToAttach = em.getReference(vehiclesVehicleToAttach.getClass(), vehiclesVehicleToAttach.getIdVehicle());
                attachedVehicles.add(vehiclesVehicleToAttach);
            }
            vehicletype.setVehicles(attachedVehicles);
            em.persist(vehicletype);
            for (Vehicle vehiclesVehicle : vehicletype.getVehicles()) {
                Vehicletype oldVehicletypeOfVehiclesVehicle = vehiclesVehicle.getVehicletype();
                vehiclesVehicle.setVehicletype(vehicletype);
                vehiclesVehicle = em.merge(vehiclesVehicle);
                if (oldVehicletypeOfVehiclesVehicle != null) {
                    oldVehicletypeOfVehiclesVehicle.getVehicles().remove(vehiclesVehicle);
                    oldVehicletypeOfVehiclesVehicle = em.merge(oldVehicletypeOfVehiclesVehicle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vehicletype vehicletype) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehicletype persistentVehicletype = em.find(Vehicletype.class, vehicletype.getId());
            Set<Vehicle> vehiclesOld = persistentVehicletype.getVehicles();
            Set<Vehicle> vehiclesNew = vehicletype.getVehicles();
            Set<Vehicle> attachedVehiclesNew = new HashSet<Vehicle>();
            for (Vehicle vehiclesNewVehicleToAttach : vehiclesNew) {
                vehiclesNewVehicleToAttach = em.getReference(vehiclesNewVehicleToAttach.getClass(), vehiclesNewVehicleToAttach.getIdVehicle());
                attachedVehiclesNew.add(vehiclesNewVehicleToAttach);
            }
            vehiclesNew = attachedVehiclesNew;
            vehicletype.setVehicles(vehiclesNew);
            vehicletype = em.merge(vehicletype);
            for (Vehicle vehiclesOldVehicle : vehiclesOld) {
                if (!vehiclesNew.contains(vehiclesOldVehicle)) {
                    vehiclesOldVehicle.setVehicletype(null);
                    vehiclesOldVehicle = em.merge(vehiclesOldVehicle);
                }
            }
            for (Vehicle vehiclesNewVehicle : vehiclesNew) {
                if (!vehiclesOld.contains(vehiclesNewVehicle)) {
                    Vehicletype oldVehicletypeOfVehiclesNewVehicle = vehiclesNewVehicle.getVehicletype();
                    vehiclesNewVehicle.setVehicletype(vehicletype);
                    vehiclesNewVehicle = em.merge(vehiclesNewVehicle);
                    if (oldVehicletypeOfVehiclesNewVehicle != null && !oldVehicletypeOfVehiclesNewVehicle.equals(vehicletype)) {
                        oldVehicletypeOfVehiclesNewVehicle.getVehicles().remove(vehiclesNewVehicle);
                        oldVehicletypeOfVehiclesNewVehicle = em.merge(oldVehicletypeOfVehiclesNewVehicle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vehicletype.getId();
                if (findVehicletype(id) == null) {
                    throw new NonexistentEntityException("The vehicletype with id " + id + " no longer exists.");
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
            Vehicletype vehicletype;
            try {
                vehicletype = em.getReference(Vehicletype.class, id);
                vehicletype.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vehicletype with id " + id + " no longer exists.", enfe);
            }
            Set<Vehicle> vehicles = vehicletype.getVehicles();
            for (Vehicle vehiclesVehicle : vehicles) {
                vehiclesVehicle.setVehicletype(null);
                vehiclesVehicle = em.merge(vehiclesVehicle);
            }
            em.remove(vehicletype);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vehicletype> findVehicletypeEntities() {
        return findVehicletypeEntities(true, -1, -1);
    }

    public List<Vehicletype> findVehicletypeEntities(int maxResults, int firstResult) {
        return findVehicletypeEntities(false, maxResults, firstResult);
    }

    private List<Vehicletype> findVehicletypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Vehicletype as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Vehicletype findVehicletype(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vehicletype.class, id);
        } finally {
            em.close();
        }
    }

    public int getVehicletypeCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Vehicletype as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
