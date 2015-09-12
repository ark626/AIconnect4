package neat;

import java.io.Serializable;

public class Gene implements Serializable,Comparable<Gene>{
	public int into;
	public int out;
	public double weigth;
	public boolean enabled;
	public int innovation;
	public int activition;
	
	public Gene() {
		super();
		this.into = 0;
		this.out = 0;
		this.weigth = 0.0;
		this.enabled = true;
		this.innovation = 0;
		this.activition = 0;
	}
	
	public Gene copyGene(){
		Gene temp = new Gene();
		temp.into = this.into;
		temp.out = this.out;
		temp.innovation = this.innovation;
		temp.enabled = this.enabled;
		return temp;
	}

	public int compareTo(Gene arg0) {
		
		return ((Gene)arg0).out -this.out;
	}

}
