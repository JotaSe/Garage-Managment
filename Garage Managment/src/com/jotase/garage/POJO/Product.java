package com.jotase.garage.POJO;
// Generated 08-jun-2014 17:19:09 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Product generated by hbm2java
 */
@Entity
@Table(name="product"
    ,catalog="garage"
    , uniqueConstraints = @UniqueConstraint(columnNames="name") 
)
public class Product  implements java.io.Serializable {


     private Integer idProduct;
     private String name;
     private Double cost;
     private Double vat;
     private Double price;
     private String description;
     private Boolean isService;
     private Double stock;
     private Set<InterventionHasProducts> interventionHasProductses = new HashSet<InterventionHasProducts>(0);

    public Product() {
    }

    public Product(String name, Double cost, Double vat, Double price, String description, Boolean isService, Double stock, Set<InterventionHasProducts> interventionHasProductses) {
       this.name = name;
       this.cost = cost;
       this.vat = vat;
       this.price = price;
       this.description = description;
       this.isService = isService;
       this.stock = stock;
       this.interventionHasProductses = interventionHasProductses;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="idProduct", unique=true, nullable=false)
    public Integer getIdProduct() {
        return this.idProduct;
    }
    
    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }
    
    @Column(name="name", unique=true, length=45)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="cost", precision=22, scale=0)
    public Double getCost() {
        return this.cost;
    }
    
    public void setCost(Double cost) {
        this.cost = cost;
    }
    
    @Column(name="vat", precision=22, scale=0)
    public Double getVat() {
        return this.vat;
    }
    
    public void setVat(Double vat) {
        this.vat = vat;
    }
    
    @Column(name="price", precision=22, scale=0)
    public Double getPrice() {
        return this.price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    @Column(name="description", length=45)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="isService")
    public Boolean getIsService() {
        return this.isService;
    }
    
    public void setIsService(Boolean isService) {
        this.isService = isService;
    }
    
    @Column(name="stock", precision=22, scale=0)
    public Double getStock() {
        return this.stock;
    }
    
    public void setStock(Double stock) {
        this.stock = stock;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="product")
    public Set<InterventionHasProducts> getInterventionHasProductses() {
        return this.interventionHasProductses;
    }
    
    public void setInterventionHasProductses(Set<InterventionHasProducts> interventionHasProductses) {
        this.interventionHasProductses = interventionHasProductses;
    }




}


