/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.POJO.JPA;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.jotase.garage.POJO.Intervention;
import com.jotase.garage.POJO.JPA.exceptions.NonexistentEntityException;
import com.jotase.garage.POJO.Product;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author javierjose
 */
public class ProductJpaController implements Serializable {

    public ProductJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) {
        if (product.getInterventions() == null) {
            product.setInterventions(new HashSet<Intervention>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Intervention> attachedInterventions = new HashSet<Intervention>();
            for (Intervention interventionsInterventionToAttach : product.getInterventions()) {
                interventionsInterventionToAttach = em.getReference(interventionsInterventionToAttach.getClass(), interventionsInterventionToAttach.getId());
                attachedInterventions.add(interventionsInterventionToAttach);
            }
            product.setInterventions(attachedInterventions);
            em.persist(product);
            for (Intervention interventionsIntervention : product.getInterventions()) {
                interventionsIntervention.getProducts().add(product);
                interventionsIntervention = em.merge(interventionsIntervention);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product persistentProduct = em.find(Product.class, product.getIdProduct());
            Set<Intervention> interventionsOld = persistentProduct.getInterventions();
            Set<Intervention> interventionsNew = product.getInterventions();
            Set<Intervention> attachedInterventionsNew = new HashSet<Intervention>();
            for (Intervention interventionsNewInterventionToAttach : interventionsNew) {
                interventionsNewInterventionToAttach = em.getReference(interventionsNewInterventionToAttach.getClass(), interventionsNewInterventionToAttach.getId());
                attachedInterventionsNew.add(interventionsNewInterventionToAttach);
            }
            interventionsNew = attachedInterventionsNew;
            product.setInterventions(interventionsNew);
            product = em.merge(product);
            for (Intervention interventionsOldIntervention : interventionsOld) {
                if (!interventionsNew.contains(interventionsOldIntervention)) {
                    interventionsOldIntervention.getProducts().remove(product);
                    interventionsOldIntervention = em.merge(interventionsOldIntervention);
                }
            }
            for (Intervention interventionsNewIntervention : interventionsNew) {
                if (!interventionsOld.contains(interventionsNewIntervention)) {
                    interventionsNewIntervention.getProducts().add(product);
                    interventionsNewIntervention = em.merge(interventionsNewIntervention);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = product.getIdProduct();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
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
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getIdProduct();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            Set<Intervention> interventions = product.getInterventions();
            for (Intervention interventionsIntervention : interventions) {
                interventionsIntervention.getProducts().remove(product);
                interventionsIntervention = em.merge(interventionsIntervention);
            }
            em.remove(product);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Product as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Product findProduct(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Product as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
