/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.POJO.JPA;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.jotase.garage.POJO.InterventionHasProducts;
import com.jotase.garage.POJO.JPA.exceptions.IllegalOrphanException;
import com.jotase.garage.POJO.JPA.exceptions.NonexistentEntityException;
import com.jotase.garage.POJO.Product;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author <@jota_Segovia>
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
        if (product.getInterventionHasProductses() == null) {
            product.setInterventionHasProductses(new HashSet<InterventionHasProducts>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<InterventionHasProducts> attachedInterventionHasProductses = new HashSet<InterventionHasProducts>();
            for (InterventionHasProducts interventionHasProductsesInterventionHasProductsToAttach : product.getInterventionHasProductses()) {
                interventionHasProductsesInterventionHasProductsToAttach = em.getReference(interventionHasProductsesInterventionHasProductsToAttach.getClass(), interventionHasProductsesInterventionHasProductsToAttach.getId());
                attachedInterventionHasProductses.add(interventionHasProductsesInterventionHasProductsToAttach);
            }
            product.setInterventionHasProductses(attachedInterventionHasProductses);
            em.persist(product);
            for (InterventionHasProducts interventionHasProductsesInterventionHasProducts : product.getInterventionHasProductses()) {
                Product oldProductOfInterventionHasProductsesInterventionHasProducts = interventionHasProductsesInterventionHasProducts.getProduct();
                interventionHasProductsesInterventionHasProducts.setProduct(product);
                interventionHasProductsesInterventionHasProducts = em.merge(interventionHasProductsesInterventionHasProducts);
                if (oldProductOfInterventionHasProductsesInterventionHasProducts != null) {
                    oldProductOfInterventionHasProductsesInterventionHasProducts.getInterventionHasProductses().remove(interventionHasProductsesInterventionHasProducts);
                    oldProductOfInterventionHasProductsesInterventionHasProducts = em.merge(oldProductOfInterventionHasProductsesInterventionHasProducts);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product persistentProduct = em.find(Product.class, product.getIdProduct());
            Set<InterventionHasProducts> interventionHasProductsesOld = persistentProduct.getInterventionHasProductses();
            Set<InterventionHasProducts> interventionHasProductsesNew = product.getInterventionHasProductses();
            List<String> illegalOrphanMessages = null;
            for (InterventionHasProducts interventionHasProductsesOldInterventionHasProducts : interventionHasProductsesOld) {
                if (!interventionHasProductsesNew.contains(interventionHasProductsesOldInterventionHasProducts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InterventionHasProducts " + interventionHasProductsesOldInterventionHasProducts + " since its product field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<InterventionHasProducts> attachedInterventionHasProductsesNew = new HashSet<InterventionHasProducts>();
            for (InterventionHasProducts interventionHasProductsesNewInterventionHasProductsToAttach : interventionHasProductsesNew) {
                interventionHasProductsesNewInterventionHasProductsToAttach = em.getReference(interventionHasProductsesNewInterventionHasProductsToAttach.getClass(), interventionHasProductsesNewInterventionHasProductsToAttach.getId());
                attachedInterventionHasProductsesNew.add(interventionHasProductsesNewInterventionHasProductsToAttach);
            }
            interventionHasProductsesNew = attachedInterventionHasProductsesNew;
            product.setInterventionHasProductses(interventionHasProductsesNew);
            product = em.merge(product);
            for (InterventionHasProducts interventionHasProductsesNewInterventionHasProducts : interventionHasProductsesNew) {
                if (!interventionHasProductsesOld.contains(interventionHasProductsesNewInterventionHasProducts)) {
                    Product oldProductOfInterventionHasProductsesNewInterventionHasProducts = interventionHasProductsesNewInterventionHasProducts.getProduct();
                    interventionHasProductsesNewInterventionHasProducts.setProduct(product);
                    interventionHasProductsesNewInterventionHasProducts = em.merge(interventionHasProductsesNewInterventionHasProducts);
                    if (oldProductOfInterventionHasProductsesNewInterventionHasProducts != null && !oldProductOfInterventionHasProductsesNewInterventionHasProducts.equals(product)) {
                        oldProductOfInterventionHasProductsesNewInterventionHasProducts.getInterventionHasProductses().remove(interventionHasProductsesNewInterventionHasProducts);
                        oldProductOfInterventionHasProductsesNewInterventionHasProducts = em.merge(oldProductOfInterventionHasProductsesNewInterventionHasProducts);
                    }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Set<InterventionHasProducts> interventionHasProductsesOrphanCheck = product.getInterventionHasProductses();
            for (InterventionHasProducts interventionHasProductsesOrphanCheckInterventionHasProducts : interventionHasProductsesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the InterventionHasProducts " + interventionHasProductsesOrphanCheckInterventionHasProducts + " in its interventionHasProductses field has a non-nullable product field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
