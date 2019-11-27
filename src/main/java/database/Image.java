package database;

import java.io.Serializable;
import java.util.Arrays;

public class Image  implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private double[] image;

    public double[] getImage() {
        return image;
    }

    public void setImage(double[] image) {
        this.image = image;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Image other = (Image) obj;
        
        for(int i = 0;i<other.image.length;i++) {
        if(other.image[i] != this.image[i]) {
            return false;
        }
        }
        return true;
    }
    
    public boolean equalsButton(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Image other = (Image) obj;
        
        for(int i = 0;i<other.image.length;i++) {
        if((other.image[i] > 0&& this.image[i]<=0) || (other.image[i] > 0&& this.image[i]<=0)) {
            return false;
        }
        }
        return true;
    }



}
