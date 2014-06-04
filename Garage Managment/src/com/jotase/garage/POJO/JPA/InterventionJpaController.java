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
import com.jotase.garage.POJO.JPA.exceptions.NonexistentEntityException;
import java.util.HashSet;
import java.util.Set;
import com.jotase.garage.POJO.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author javierjose
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
        if (intervention.getProducts() == null) {
            intervention.setProducts(new HashSet<Product>());
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
            Set<Product> attachedProducts = new HashSet<Product>();
            for (Product productsProductToAttach : intervention.getProducts()) {
                productsProductToAttach = em.getReference(productsProductToAttach.getClass(), productsProductToAttach.getIdProduct());
                attachedProducts.add(productsProductToAttach);
            }
            intervention.setProducts(attachedProducts);
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
            for (Product productsProduct : intervention.getProducts()) {
                productsProduct.getInterventions().add(intervention);
                productsProduct = em.merge(productsProduct);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Intervention intervention) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Intervention persistentIntervention = em.find(Intervention.class, intervention.getId());
            Vehicle vehicleOld = persistentIntervention.getVehicle();
            Vehicle vehicleNew = intervention.getVehicle();
            Set<Invoice> invoicesOld = persistentIntervention.getInvoices();
            Set<Invoice> invoicesNew = intervention.getInvoices();
            Set<Product> productsOld = persistentIntervention.getProducts();
            Set<Product> productsNew = intervention.getProducts();
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
            Set<Product> attachedProductsNew = new HashSet<Product>();
            for (Product productsNewProductToAttach : productsNew) {
                productsNewProductToAttach = em.getReference(productsNewProductToAttach.getClass(), productsNewProductToAttach.getIdProduct());
                attachedProductsNew.add(productsNewProductToAttach);
            }
            productsNew = attachedProductsNew;
            intervention.setProducts(productsNew);
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
            for (Product productsOldProduct : productsOld) {
                if (!productsNew.contains(productsOldProduct)) {
                    productsOldProduct.getInterventions().remove(intervention);
                    productsOldProduct = em.merge(productsOldProduct);
                }
            }
            for (Product productsNewProduct : productsNew) {
                if (!productsOld.contains(productsNewProduct)) {
                    productsNewProduct.getInterventions().add(intervention);
                    productsNewProduct = em.merge(productsNewProduct);
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            Set<Product> products = intervention.getProducts();
            for (Product productsProduct : products) {
                productsProduct.getInterventions().remove(intervention);
                productsProduct = em.merge(productsProduct);
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
