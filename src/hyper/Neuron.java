package hyper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public  class Neuron {
    private ArrayList<Gene> incoming;
    private ArrayList<Gene> outgoing;
    private BigDecimal value;
    private boolean active;
    private int activition;
    
    public Neuron(){
        this.incoming = new ArrayList<Gene>();
        this.outgoing = new ArrayList<Gene>();
        this.value = BigDecimal.ZERO;
        this.active = true;
        this.activition = 0;
    }
    
    
    
    public void addIncoming(Gene g){
        this.incoming.add(g);
    }
    
    public void addOutgoing(Gene g){
        this.outgoing.add(g);
    }

    public ArrayList<Gene> getIncoming() {
        return incoming;
    }

    public void setIncoming(ArrayList<Gene> incoming) {
        this.incoming = incoming;
    }

    public ArrayList<Gene> getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(ArrayList<Gene> outgoing) {
        this.outgoing = outgoing;
    }

    public BigDecimal getValue() {
        return value.setScale(16, RoundingMode.HALF_EVEN);
    }

    public void setValue(BigDecimal value) {
        this.value =  value.setScale(16, RoundingMode.HALF_EVEN);
    }
    
    public void setValue(double value) {
        this.value =  BigDecimal.valueOf(value).setScale(16, RoundingMode.HALF_EVEN);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getActivition() {
        return activition;
    }

    public void setActivition(int activition) {
        this.activition = activition;
    }

}
