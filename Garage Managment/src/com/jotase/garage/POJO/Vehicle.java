package com.jotase.garage.POJO;
// Generated 08-jun-2014 17:19:09 by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Vehicle generated by hbm2java
 */
@Entity
@Table(name="vehicle"
    ,catalog="garage"
    , uniqueConstraints = @UniqueConstraint(columnNames="registrationNumber") 
)
public class Vehicle  implements java.io.Serializable {


     private Integer idVehicle;
     private Customer customer;
     private Vehicletype vehicletype;
     private String registrationNumber;
     private String color;
     private String model;
     private Double kmTraveled;
     private Date constructionsYear;
     private byte[] image;
     private String notes;
     private Set<Intervention> interventions = new HashSet<Intervention>(0);

    public Vehicle() {
    }

    public Vehicle(Customer customer, Vehicletype vehicletype, String registrationNumber, String color, String model, Double kmTraveled, Date constructionsYear, byte[] image, String notes, Set<Intervention> interventions) {
       this.customer = customer;
       this.vehicletype = vehicletype;
       this.registrationNumber = registrationNumber;
       this.color = color;
       this.model = model;
       this.kmTraveled = kmTraveled;
       this.constructionsYear = constructionsYear;
       this.image = image;
       this.notes = notes;
       this.interventions = interventions;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="idVehicle", unique=true, nullable=false)
    public Integer getIdVehicle() {
        return this.idVehicle;
    }
    
    public void setIdVehicle(Integer idVehicle) {
        this.idVehicle = idVehicle;
    }
@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="customerId")
    public Customer getCustomer() {
        return this.customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="typeId")
    public Vehicletype getVehicletype() {
        return this.vehicletype;
    }
    
    public void setVehicletype(Vehicletype vehicletype) {
        this.vehicletype = vehicletype;
    }
    
    @Column(name="registrationNumber", unique=true, length=50)
    public String getRegistrationNumber() {
        return this.registrationNumber;
    }
    
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    
    @Column(name="color", length=50)
    public String getColor() {
        return this.color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    @Column(name="model", length=50)
    public String getModel() {
        return this.model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    @Column(name="kmTraveled", precision=22, scale=0)
    public Double getKmTraveled() {
        return this.kmTraveled;
    }
    
    public void setKmTraveled(Double kmTraveled) {
        this.kmTraveled = kmTraveled;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="constructionsYear", length=10)
    public Date getConstructionsYear() {
        return this.constructionsYear;
    }
    
    public void setConstructionsYear(Date constructionsYear) {
        this.constructionsYear = constructionsYear;
    }
    
    @Column(name="image")
    public byte[] getImage() {
        return this.image;
    }
    
    public void setImage(byte[] image) {
        this.image = image;
    }
    
    @Column(name="notes", length=50)
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="vehicle")
    public Set<Intervention> getInterventions() {
        return this.interventions;
    }
    
    public void setInterventions(Set<Intervention> interventions) {
        this.interventions = interventions;
    }




}


