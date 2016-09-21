package neat;

import java.util.ArrayList;

public class Neuron {
	public ArrayList<Gene> incoming;
	public ArrayList<Gene> outgoing;
	public double value;
	public boolean active;
	public int activition;
	
	public Neuron(){
		this.incoming = new ArrayList<Gene>();
		this.outgoing = new ArrayList<Gene>();
		this.value = 0.0;
		this.active = true;
		this.activition = 0;
	}

}
