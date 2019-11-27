package tensorflowPlayGround;

import java.io.Serializable;
import tools.UtilityforMath;

/**
 * 
 * @author vdinger
 *
 */
public class Gene implements Serializable, Comparable<Gene> {
    /**
     * 
     */
    private static final long serialVersionUID = -27186992329707267L;
    private int into;
    private int out;
    public int layer;
    private double weigth;
    private boolean enabled;
    private long innovation;
    private int activition;
    private int excess = 0;

    // private void writeObject(ObjectOutputStream oos) throws IOException {
    // // default serialization
    // oos.defaultWriteObject();
    // // write the object
    //// oos.writeInt(into);
    //// oos.writeInt(out);
    //// oos.writeInt(layer);
    //// oos.writeDouble(weigth);
    //// oos.writeBoolean(enabled);
    //// oos.writeInt(innovation);
    //// oos.writeInt(activition);
    //// oos.writeInt(excess);
    // }
    //
    // private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    // // default deserialization
    // ois.defaultReadObject();
    //// Gene gene = new Gene();
    //// gene.setInto(ois.readInt());
    //// gene.setOut(ois.readInt());
    //// gene.layer = ois.readInt();
    //// gene.setWeigth(ois.readDouble());
    //// gene.setEnabled(ois.readBoolean());
    //// gene.setInnovation(ois.readInt());
    //// gene.setActivition(ois.readInt());
    //// gene.setExcess(ois.readInt());
    // // ... more code
    //
    // }

    public Gene() {
        super();
        this.into = 0;
        this.out = 0;
        this.weigth = 0.0;
        this.enabled = true;
        this.innovation = 0;
        this.activition = 0;
    }

    public Gene copyGene() {
        Gene temp = new Gene();
        temp.into = this.into;
        temp.out = this.out;
        temp.innovation = this.innovation;
        temp.enabled = this.enabled;
        temp.excess = this.excess;
        temp.weigth = this.weigth;
        temp.activition = this.activition;
        return temp;
    }

    public int compareTo(Gene arg0) {

        return ((Gene) arg0).out - this.out;
    }

    // @Override
    // public int hashCode() {
    // final int prime = 31;
    // int result = 1;
    // result = prime * result + activition;
    // result = prime * result + (enabled ? 1231 : 1237);
    // result = prime * result + innovation;
    // result = prime * result + into;
    // result = prime * result + out;
    // result = pri,m
    //// long temp;
    //// temp = Double.doubleToLongBits(weigth);
    //// result = prime * result + (int) (temp ^ (temp >>> 32));
    // return result;
    // }



    // @Override
    // public boolean equals(Object obj) {
    // if (this == obj)
    // return true;
    // if (obj == null)
    // return false;
    // if (getClass() != obj.getClass())
    // return false;
    // Gene other = (Gene) obj;
    // if (activition != other.activition)
    // return false;
    // if (enabled != other.enabled)
    // return false;
    // if (innovation != other.innovation)
    // return false;
    // if (into != other.into)
    // return false;
    // if (out != other.out)
    // return false;
    // if (Double.doubleToLongBits(weigth) != Double.doubleToLongBits(other.weigth))
    // return false;
    // return true;
    // }

    public int getInto() {
        return into;
    }

    public void setInto(int into) {
        this.into = into;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }



    public double getWeigth() {
        return weigth;
    }

    public void setWeigth(double weigth) {
        this.weigth = UtilityforMath.round(weigth, 10);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getInnovation() {
        return innovation;
    }

    public void setInnovation(long innovation) {
        this.innovation = innovation;
    }

    public int getActivition() {
        return activition;
    }

    public void setActivition(int activition) {
        this.activition = activition;
    }

    public int isExcess() {
        return excess;
    }

    public void setExcess(int excess) {
        this.excess = excess;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + activition;
        result = prime * result + into;
        result = prime * result + layer;
        result = prime * result + out;
        long temp;
        temp = Double.doubleToLongBits(weigth);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Gene other = (Gene) obj;
        if (activition != other.activition)
            return false;
        if (into != other.into)
            return false;
        if (layer != other.layer)
            return false;
        if (out != other.out)
            return false;
        if (Double.doubleToLongBits(weigth) != Double.doubleToLongBits(other.weigth))
            return false;
        return true;
    }



}