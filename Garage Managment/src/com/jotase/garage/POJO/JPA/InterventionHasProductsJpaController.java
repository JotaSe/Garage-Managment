/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.POJO.JPA;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.jotase.garage.POJO.Product;
import com.jotase.garage.POJO.Intervention;
import com.jotase.garage.POJO.InterventionHasProducts;
import com.jotase.garage.POJO.InterventionHasProductsId;
import com.jotase.garage.POJO.JPA.exceptions.NonexistentEntityException;
import com.jotase.garage.POJO.JPA.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author <@jota_Segovia>
 */
public class InterventionHasProductsJpaController implements Serializable {

    public InterventionHasProductsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InterventionHasProducts interventionHasProducts) throws PreexistingEntityException, Exception {
        if (interventionHasProducts.getId() == null) {
            interventionHasProducts.setId(new InterventionHasProductsId());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product product = interventionHasProducts.getProduct();
            if (product != null) {
                product = em.getReference(product.getClass(), product.getIdProduct());
                interventionHasProducts.setProduct(product);
            }
            Intervention intervention = interventionHasProducts.getIntervention();
            if (intervention != null) {
                intervention = em.getReference(intervention.getClass(), intervention.getId());
                interventionHasProducts.setIntervention(intervention);
            }
            em.persist(interventionHasProducts);
            if (product != null) {
                product.getInterventionHasProductses().add(interventionHasProducts);
                product = em.merge(product);
            }
            if (intervention != null) {
                intervention.getInterventionHasProductses().add(interventionHasProducts);
                intervention = em.merge(intervention);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInterventionHasProducts(interventionHasProducts.getId()) != null) {
                throw new PreexistingEntityException("InterventionHasProducts " + interventionHasProducts + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InterventionHasProducts interventionHasProducts) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InterventionHasProducts persistentInterventionHasProducts = em.find(InterventionHasProducts.class, interventionHasProducts.getId());
            Product productOld = persistentInterventionHasProducts.getProduct();
            Product productNew = interventionHasProducts.getProduct();
            Intervention interventionOld = persistentInterventionHasProducts.getIntervention();
            Intervention interventionNew = interventionHasProducts.getIntervention();
            if (productNew != null) {
                productNew = em.getReference(productNew.getClass(), productNew.getIdProduct());
                interventionHasProducts.setProduct(productNew);
            }
            if (interventionNew != null) {
                interventionNew = em.getReference(interventionNew.getClass(), interventionNew.getId());
                interventionHasProducts.setIntervention(interventionNew);
            }
            interventionHasProducts = em.merge(interventionHasProducts);
            if (productOld != null && !productOld.equals(productNew)) {
                productOld.getInterventionHasProductses().remove(interventionHasProducts);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld)) {
                productNew.getInterventionHasProductses().add(interventionHasProducts);
                productNew = em.merge(productNew);
            }
            if (interventionOld != null && !interventionOld.equals(interventionNew)) {
                interventionOld.getInterventionHasProductses().remove(interventionHasProducts);
                interventionOld = em.merge(interventionOld);
            }
            if (interventionNew != null && !interventionNew.equals(interventionOld)) {
                interventionNew.getInterventionHasProductses().add(interventionHasProducts);
                interventionNew = em.merge(interventionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                InterventionHasProductsId id = interventionHasProducts.getId();
                if (findInterventionHasProducts(id) == null) {
                    throw new NonexistentEntityException("The interventionHasProducts with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(InterventionHasProductsId id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InterventionHasProducts interventionHasProducts;
            try {
                interventionHasProducts = em.getReference(InterventionHasProducts.class, id);
                interventionHasProducts.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The interventionHasProducts with id " + id + " no longer exists.", enfe);
            }
            Product product = interventionHasProducts.getProduct();
            if (product != null) {
                product.getInterventionHasProductses().remove(interventionHasProducts);
                product = em.merge(product);
            }
            Intervention intervention = interventionHasProducts.getIntervention();
            if (intervention != null) {
                intervention.getInterventionHasProductses().remove(interventionHasProducts);
                intervention = em.merge(intervention);
            }
            em.remove(interventionHasProducts);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InterventionHasProducts> findInterventionHasProductsEntities() {
        return findInterventionHasProductsEntities(true, -1, -1);
    }

    public List<InterventionHasProducts> findInterventionHasProductsEntities(int maxResults, int firstResult) {
        return findInterventionHasProductsEntities(false, maxResults, firstResult);
    }

    private List<InterventionHasProducts> findInterventionHasProductsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from InterventionHasProducts as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public InterventionHasProducts findInterventionHasProducts(InterventionHasProductsId id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InterventionHasProducts.class, id);
        } finally {
            em.close();
        }
    }

    public int getInterventionHasProductsCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from InterventionHasProducts as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
