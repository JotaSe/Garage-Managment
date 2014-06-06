 package com.jotase.garage.util;

import javax.swing.UIManager;
import org.pushingpixels.substance.api.SubstanceConstants;
 import org.pushingpixels.substance.api.SubstanceLookAndFeel;
//import org.jvnet.substance.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.*;
//import org.jvnet.substance.skin.*;
//import org.jvnet.substance.watermark.SubstanceWatermark;
import org.pushingpixels.substance.api.watermark.SubstanceImageWatermark;
import org.pushingpixels.substance.api.watermark.SubstanceWatermark;
 

 public class DefaultSkin  extends  SubstanceBusinessBlueSteelLookAndFeel
 {
   SubstanceImageWatermark imageWatermark;


   public DefaultSkin()
   {

       // SubstanceLookAndFeel.setSkin(this);
       
     
   }

   public String getDisplayName()
   {
     return "Garage Management";
   }

   public SubstanceImageWatermark getImageWatermark()
   {
     return this.imageWatermark;
   }

   public void setWatermark(String s)
   {
     this.imageWatermark = new SubstanceImageWatermark(s);
     this.imageWatermark.setOpacity(1F);
     this.imageWatermark.setKind(SubstanceConstants.ImageWatermarkKind.APP_ANCHOR);
     //this.watermark = (SubstanceWatermark)this.imageWatermark;
   }

   public void setTransparency(float f)
   {
     this.imageWatermark.setOpacity(f);
   }

   public void setPosGB(int i)
   {
     switch (i) { case 1:
       this.imageWatermark.setKind(SubstanceConstants.ImageWatermarkKind.APP_ANCHOR); break;
     case 2:
       this.imageWatermark.setKind(SubstanceConstants.ImageWatermarkKind.APP_CENTER); break;
     case 3:
       this.imageWatermark.setKind(SubstanceConstants.ImageWatermarkKind.APP_TILE); break;
     case 4:
       this.imageWatermark.setKind(SubstanceConstants.ImageWatermarkKind.SCREEN_CENTER_SCALE); break;
     case 5:
       this.imageWatermark.setKind(SubstanceConstants.ImageWatermarkKind.SCREEN_TILE);
     }
   }
        public static void setUIFont (javax.swing.plaf.FontUIResource f)
        {
         java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
         Object key = keys.nextElement();
         Object value = UIManager.get (key);
         if (value != null && value instanceof javax.swing.plaf.FontUIResource)
         UIManager.put (key, f);
      }
    }    
   
   
 }

