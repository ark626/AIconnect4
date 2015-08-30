package neat;

import java.util.ArrayList;

public class Neuron {
	public ArrayList<Gene> incoming;
	public double value;
	public boolean active;
	
	public Neuron(){
		this.incoming = new ArrayList<Gene>();
		this.value = 0.0;
		this.active = true;
	}

}
