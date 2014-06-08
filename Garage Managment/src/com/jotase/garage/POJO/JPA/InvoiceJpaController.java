/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.POJO.JPA;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.jotase.garage.POJO.Intervention;
import com.jotase.garage.POJO.Invoice;
import com.jotase.garage.POJO.JPA.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author <@jota_Segovia>
 */
public class InvoiceJpaController implements Serializable {

    public InvoiceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Invoice invoice) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Intervention intervention = invoice.getIntervention();
            if (intervention != null) {
                intervention = em.getReference(intervention.getClass(), intervention.getId());
                invoice.setIntervention(intervention);
            }
            em.persist(invoice);
            if (intervention != null) {
                intervention.getInvoices().add(invoice);
                intervention = em.merge(intervention);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Invoice invoice) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Invoice persistentInvoice = em.find(Invoice.class, invoice.getId());
            Intervention interventionOld = persistentInvoice.getIntervention();
            Intervention interventionNew = invoice.getIntervention();
            if (interventionNew != null) {
                interventionNew = em.getReference(interventionNew.getClass(), interventionNew.getId());
                invoice.setIntervention(interventionNew);
            }
            invoice = em.merge(invoice);
            if (interventionOld != null && !interventionOld.equals(interventionNew)) {
                interventionOld.getInvoices().remove(invoice);
                interventionOld = em.merge(interventionOld);
            }
            if (interventionNew != null && !interventionNew.equals(interventionOld)) {
                interventionNew.getInvoices().add(invoice);
                interventionNew = em.merge(interventionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = invoice.getId();
                if (findInvoice(id) == null) {
                    throw new NonexistentEntityException("The invoice with id " + id + " no longer exists.");
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
            Invoice invoice;
            try {
                invoice = em.getReference(Invoice.class, id);
                invoice.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The invoice with id " + id + " no longer exists.", enfe);
            }
            Intervention intervention = invoice.getIntervention();
            if (intervention != null) {
                intervention.getInvoices().remove(invoice);
                intervention = em.merge(intervention);
            }
            em.remove(invoice);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Invoice> findInvoiceEntities() {
        return findInvoiceEntities(true, -1, -1);
    }

    public List<Invoice> findInvoiceEntities(int maxResults, int firstResult) {
        return findInvoiceEntities(false, maxResults, firstResult);
    }

    private List<Invoice> findInvoiceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Invoice as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Invoice findInvoice(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Invoice.class, id);
        } finally {
            em.close();
        }
    }

    public int getInvoiceCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Invoice as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
