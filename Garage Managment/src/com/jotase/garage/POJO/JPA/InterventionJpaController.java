/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.POJO.JPA;

import com.jotase.garage.POJO.Intervention;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.jotase.garage.POJO.Vehicle;
import com.jotase.garage.POJO.Invoice;
import java.util.HashSet;
import java.util.Set;
import com.jotase.garage.POJO.InterventionHasProducts;
import com.jotase.garage.POJO.JPA.exceptions.IllegalOrphanException;
import com.jotase.garage.POJO.JPA.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author <@jota_Segovia>
 */
public class InterventionJpaController implements Serializable {

    public InterventionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Intervention intervention) {
        if (intervention.getInvoices() == null) {
            intervention.setInvoices(new HashSet<Invoice>());
        }
        if (intervention.getInterventionHasProductses() == null) {
            intervention.setInterventionHasProductses(new HashSet<InterventionHasProducts>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehicle vehicle = intervention.getVehicle();
            if (vehicle != null) {
                vehicle = em.getReference(vehicle.getClass(), vehicle.getIdVehicle());
                intervention.setVehicle(vehicle);
            }
            Set<Invoice> attachedInvoices = new HashSet<Invoice>();
            for (Invoice invoicesInvoiceToAttach : intervention.getInvoices()) {
                invoicesInvoiceToAttach = em.getReference(invoicesInvoiceToAttach.getClass(), invoicesInvoiceToAttach.getId());
                attachedInvoices.add(invoicesInvoiceToAttach);
            }
            intervention.setInvoices(attachedInvoices);
            Set<InterventionHasProducts> attachedInterventionHasProductses = new HashSet<InterventionHasProducts>();
            for (InterventionHasProducts interventionHasProductsesInterventionHasProductsToAttach : intervention.getInterventionHasProductses()) {
                interventionHasProductsesInterventionHasProductsToAttach = em.getReference(interventionHasProductsesInterventionHasProductsToAttach.getClass(), interventionHasProductsesInterventionHasProductsToAttach.getId());
                attachedInterventionHasProductses.add(interventionHasProductsesInterventionHasProductsToAttach);
            }
            intervention.setInterventionHasProductses(attachedInterventionHasProductses);
            em.persist(intervention);
            if (vehicle != null) {
                vehicle.getInterventions().add(intervention);
                vehicle = em.merge(vehicle);
            }
            for (Invoice invoicesInvoice : intervention.getInvoices()) {
                Intervention oldInterventionOfInvoicesInvoice = invoicesInvoice.getIntervention();
                invoicesInvoice.setIntervention(intervention);
                invoicesInvoice = em.merge(invoicesInvoice);
                if (oldInterventionOfInvoicesInvoice != null) {
                    oldInterventionOfInvoicesInvoice.getInvoices().remove(invoicesInvoice);
                    oldInterventionOfInvoicesInvoice = em.merge(oldInterventionOfInvoicesInvoice);
                }
            }
            for (InterventionHasProducts interventionHasProductsesInterventionHasProducts : intervention.getInterventionHasProductses()) {
                Intervention oldInterventionOfInterventionHasProductsesInterventionHasProducts = interventionHasProductsesInterventionHasProducts.getIntervention();
                interventionHasProductsesInterventionHasProducts.setIntervention(intervention);
                interventionHasProductsesInterventionHasProducts = em.merge(interventionHasProductsesInterventionHasProducts);
                if (oldInterventionOfInterventionHasProductsesInterventionHasProducts != null) {
                    oldInterventionOfInterventionHasProductsesInterventionHasProducts.getInterventionHasProductses().remove(interventionHasProductsesInterventionHasProducts);
                    oldInterventionOfInterventionHasProductsesInterventionHasProducts = em.merge(oldInterventionOfInterventionHasProductsesInterventionHasProducts);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Intervention intervention) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Intervention persistentIntervention = em.find(Intervention.class, intervention.getId());
            Vehicle vehicleOld = persistentIntervention.getVehicle();
            Vehicle vehicleNew = intervention.getVehicle();
            Set<Invoice> invoicesOld = persistentIntervention.getInvoices();
            Set<Invoice> invoicesNew = intervention.getInvoices();
            Set<InterventionHasProducts> interventionHasProductsesOld = persistentIntervention.getInterventionHasProductses();
            Set<InterventionHasProducts> interventionHasProductsesNew = intervention.getInterventionHasProductses();
            List<String> illegalOrphanMessages = null;
            for (InterventionHasProducts interventionHasProductsesOldInterventionHasProducts : interventionHasProductsesOld) {
                if (!interventionHasProductsesNew.contains(interventionHasProductsesOldInterventionHasProducts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InterventionHasProducts " + interventionHasProductsesOldInterventionHasProducts + " since its intervention field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (vehicleNew != null) {
                vehicleNew = em.getReference(vehicleNew.getClass(), vehicleNew.getIdVehicle());
                intervention.setVehicle(vehicleNew);
            }
            Set<Invoice> attachedInvoicesNew = new HashSet<Invoice>();
            for (Invoice invoicesNewInvoiceToAttach : invoicesNew) {
                invoicesNewInvoiceToAttach = em.getReference(invoicesNewInvoiceToAttach.getClass(), invoicesNewInvoiceToAttach.getId());
                attachedInvoicesNew.add(invoicesNewInvoiceToAttach);
            }
            invoicesNew = attachedInvoicesNew;
            intervention.setInvoices(invoicesNew);
            Set<InterventionHasProducts> attachedInterventionHasProductsesNew = new HashSet<InterventionHasProducts>();
            for (InterventionHasProducts interventionHasProductsesNewInterventionHasProductsToAttach : interventionHasProductsesNew) {
                interventionHasProductsesNewInterventionHasProductsToAttach = em.getReference(interventionHasProductsesNewInterventionHasProductsToAttach.getClass(), interventionHasProductsesNewInterventionHasProductsToAttach.getId());
                attachedInterventionHasProductsesNew.add(interventionHasProductsesNewInterventionHasProductsToAttach);
            }
            interventionHasProductsesNew = attachedInterventionHasProductsesNew;
            intervention.setInterventionHasProductses(interventionHasProductsesNew);
            intervention = em.merge(intervention);
            if (vehicleOld != null && !vehicleOld.equals(vehicleNew)) {
                vehicleOld.getInterventions().remove(intervention);
                vehicleOld = em.merge(vehicleOld);
            }
            if (vehicleNew != null && !vehicleNew.equals(vehicleOld)) {
                vehicleNew.getInterventions().add(intervention);
                vehicleNew = em.merge(vehicleNew);
            }
            for (Invoice invoicesOldInvoice : invoicesOld) {
                if (!invoicesNew.contains(invoicesOldInvoice)) {
                    invoicesOldInvoice.setIntervention(null);
                    invoicesOldInvoice = em.merge(invoicesOldInvoice);
                }
            }
            for (Invoice invoicesNewInvoice : invoicesNew) {
                if (!invoicesOld.contains(invoicesNewInvoice)) {
                    Intervention oldInterventionOfInvoicesNewInvoice = invoicesNewInvoice.getIntervention();
                    invoicesNewInvoice.setIntervention(intervention);
                    invoicesNewInvoice = em.merge(invoicesNewInvoice);
                    if (oldInterventionOfInvoicesNewInvoice != null && !oldInterventionOfInvoicesNewInvoice.equals(intervention)) {
                        oldInterventionOfInvoicesNewInvoice.getInvoices().remove(invoicesNewInvoice);
                        oldInterventionOfInvoicesNewInvoice = em.merge(oldInterventionOfInvoicesNewInvoice);
                    }
                }
            }
            for (InterventionHasProducts interventionHasProductsesNewInterventionHasProducts : interventionHasProductsesNew) {
                if (!interventionHasProductsesOld.contains(interventionHasProductsesNewInterventionHasProducts)) {
                    Intervention oldInterventionOfInterventionHasProductsesNewInterventionHasProducts = interventionHasProductsesNewInterventionHasProducts.getIntervention();
                    interventionHasProductsesNewInterventionHasProducts.setIntervention(intervention);
                    interventionHasProductsesNewInterventionHasProducts = em.merge(interventionHasProductsesNewInterventionHasProducts);
                    if (oldInterventionOfInterventionHasProductsesNewInterventionHasProducts != null && !oldInterventionOfInterventionHasProductsesNewInterventionHasProducts.equals(intervention)) {
                        oldInterventionOfInterventionHasProductsesNewInterventionHasProducts.getInterventionHasProductses().remove(interventionHasProductsesNewInterventionHasProducts);
                        oldInterventionOfInterventionHasProductsesNewInterventionHasProducts = em.merge(oldInterventionOfInterventionHasProductsesNewInterventionHasProducts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = intervention.getId();
                if (findIntervention(id) == null) {
                    throw new NonexistentEntityException("The intervention with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Intervention intervention;
            try {
                intervention = em.getReference(Intervention.class, id);
                intervention.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The intervention with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<InterventionHasProducts> interventionHasProductsesOrphanCheck = intervention.getInterventionHasProductses();
            for (InterventionHasProducts interventionHasProductsesOrphanCheckInterventionHasProducts : interventionHasProductsesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Intervention (" + intervention + ") cannot be destroyed since the InterventionHasProducts " + interventionHasProductsesOrphanCheckInterventionHasProducts + " in its interventionHasProductses field has a non-nullable intervention field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Vehicle vehicle = intervention.getVehicle();
            if (vehicle != null) {
                vehicle.getInterventions().remove(intervention);
                vehicle = em.merge(vehicle);
            }
            Set<Invoice> invoices = intervention.getInvoices();
            for (Invoice invoicesInvoice : invoices) {
                invoicesInvoice.setIntervention(null);
                invoicesInvoice = em.merge(invoicesInvoice);
            }
            em.remove(intervention);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Intervention> findInterventionEntities() {
        return findInterventionEntities(true, -1, -1);
    }

    public List<Intervention> findInterventionEntities(int maxResults, int firstResult) {
        return findInterventionEntities(false, maxResults, firstResult);
    }

    private List<Intervention> findInterventionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Intervention as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Intervention findIntervention(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Intervention.class, id);
        } finally {
            em.close();
        }
    }

    public int getInterventionCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Intervention as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
