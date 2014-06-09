package com.jotase.garage.POJO;
// Generated 08-jun-2014 17:19:09 by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * InterventionHasProductsId generated by hbm2java
 */
@Embeddable
public class InterventionHasProductsId  implements java.io.Serializable {


     private int interventionId;
     private int productId;

    public InterventionHasProductsId() {
    }

    public InterventionHasProductsId(int interventionId, int productId) {
       this.interventionId = interventionId;
       this.productId = productId;
    }
   

    @Column(name="interventionId", unique=true, nullable=false)
    public int getInterventionId() {
        return this.interventionId;
    }
    
    public void setInterventionId(int interventionId) {
        this.interventionId = interventionId;
    }

    @Column(name="productId", unique=true, nullable=false)
    public int getProductId() {
        return this.productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof InterventionHasProductsId) ) return false;
		 InterventionHasProductsId castOther = ( InterventionHasProductsId ) other; 
         
		 return (this.getInterventionId()==castOther.getInterventionId())
 && (this.getProductId()==castOther.getProductId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getInterventionId();
         result = 37 * result + this.getProductId();
         return result;
   }   


}


