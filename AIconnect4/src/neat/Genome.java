package neat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genome implements Serializable,Comparable<Genome>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4198647103860678421L;
	public ArrayList<Gene> Genes;
	public int fitness;
	public int adjustedfitness;
	public transient Network Network;
	//public transient Pool pool;
	public int maxneuron;
	public int globalRank;
	public double[] mutationrates;
	public int Innovation =0;
	public int Generation ;
	public transient Pool parent;
//	genome.mutationRates["connections"] = MutateConnectionsChance
//	genome.mutationRates["link"] = LinkMutationChance
//	genome.mutationRates["bias"] = BiasMutationChance
//	genome.mutationRates["node"] = NodeMutationChance
//	genome.mutationRates["enable"] = EnableMutationChance
//	genome.mutationRates["disable"] = DisableMutationChance
//	genome.mutationRates["step"] = StepSize
//	public static final double Population = 300;
//	public static final double DeltaDisjoint = 2.0;
//	public static final double DeltaWeights = 0.4;
//	public static final double DeltaThreshold = 1.0;
//	public static final double StaleSpecies = 15;
	public static final double MutateConnectionsChance = 0.25;
	public static final double PerturbChance = 0.90;
	public static final double CrossoverChance = 0.75;
	public static final double LinkMutationChance = 2.0;
	public static final double NodeMutationChance = 0.50;
	public static final double BiasMutationChance = 0.40;
	public static final double ActivitionMutationChance = 0.5;
	public static final double StepSize = 0.2;
	public static final double DisableMutationChance = 0.4;
	public static final double EnableMutationChance = 0.2;//0.4
	public int Inputs = 42;
	public int Outputs = 3;
	public static final int MaxNodes = 10000;
	
	
	public Genome(int in,int out,int gen,Pool p) {
		super();
		this.Generation = gen;
		//this.pool = p;
		this.Inputs = in;
		this.Outputs = out;
		Genes = new ArrayList<Gene>();
		this.fitness = 0;
		this.adjustedfitness = -9999;
		Network = new Network();
		this.maxneuron = 0;
		this.globalRank = 0;
		this.mutationrates = new double[8];
		this.mutationrates[0] = Genome.MutateConnectionsChance;
		this.mutationrates[1] = Genome.LinkMutationChance;
		this.mutationrates[2] = Genome.BiasMutationChance;
		this.mutationrates[3] = Genome.NodeMutationChance;
		this.mutationrates[4] = Genome.EnableMutationChance;
		this.mutationrates[5] = Genome.DisableMutationChance;
		this.mutationrates[6] = Genome.StepSize;
		this.mutationrates[7] = Genome.ActivitionMutationChance;
		this.parent = p;
	}



	public static Genome basicGenome(int in, int out,Pool p){
		Genome g = new Genome(in,out,1,p);
	
		g.maxneuron = in+out;
		g.Innovation = 1;
		g.Generation = 1;
		return g;
	}
	
	public void setFitness(int fitness){
		if(fitness>this.parent.maxFitness){
			//this.parent.Species.get(parent.currentSpecies-1).staleness = 0;
			this.parent.maxFitness = fitness; 
		}
		this.fitness = fitness;
		if(fitness == 0){
			 this.fitness = 1;
			}
	}
	public Genome copyGenome(){
		Genome temp = new Genome(this.Inputs,this.Outputs,this.Generation,this.parent);
		temp.Genes = new ArrayList<Gene>();
		for(Gene g :this.Genes){
			temp.Genes.add(g.copyGene());
		}
		temp.Generation = this.Generation;
		temp.maxneuron = this.maxneuron;
		temp.mutationrates = new double[8];
		for(int i=0;i<this.mutationrates.length;i++){
		temp.mutationrates [i] = this.mutationrates[i];
		}
		return temp;
	}
	
	public void generateNetwork(){
		Genome g = this;
		Network net = new Network();
	//Harte Knoten einfügen
		for(int i =0;i<Inputs;i++){
		
			net.Neurons.add(new Neuron());
		}

		for(int i =0;i<Outputs;i++){
			net.Neurons.add(new Neuron());
		}
		
		if(g.Genes != null && g.Genes.size() > 1){
		Collections.sort(g.Genes);
		}
		boolean Check = true;
		for(Gene ge:g.Genes){
			if(ge.enabled){
				boolean checker = false;
				while(net.Neurons.size()<=ge.out){
					checker = true;
					net.Neurons.add(new Neuron()) ;
					net.Neurons.get(net.Neurons.size()-1).active = false;
					if(ge.activition != 0){
					net.Neurons.get(net.Neurons.size()-1).activition = ge.activition;
					}
				}
				net.Neurons.get(net.Neurons.size()-1).active = true;
				net.Neurons.get(ge.out).incoming.add(ge);
				checker = false;
				while(net.Neurons.size()<=ge.into){
					checker = true;
					net.Neurons.add(new Neuron()) ;
					net.Neurons.get(net.Neurons.size()-1).active = false;
				}
				net.Neurons.get(net.Neurons.size()-1).active = true;
//				if(ge.out == Inputs+Outputs){
//					net.Neurons.get(Inputs+Outputs).value = 1.0;
//				}
				if(ge.weigth != 0.0){
					Check = false;
				}
			}
		}
		if(Check){
			this.fitness = -999999;

		}
		g.Network = net;
	
	}
	
	
	
	public int randomNeuron(boolean nonInput){
		ArrayList<Integer> Neurons = new ArrayList<Integer>();

		if(nonInput){
			
			for(int i=0;i<Inputs;i++){
				Neurons.add(i);
			}
		}
		
		for(int i=Inputs;i<Inputs+Outputs+1;i++){
			Neurons.add(i);
		}
		
		for(int i=0;i<this.Genes.size();i++){
			if(!nonInput||this.Genes.get(i).into > Inputs&&this.Genes.get(i).out != Inputs+Outputs){
				Neurons.add(this.Genes.get(i).into);
				
				
			}
			if(!nonInput||this.Genes.get(i).out >= Inputs&&this.Genes.get(i).out != Inputs+Outputs){
				Neurons.add(this.Genes.get(i).out);
			}
		}
		
		int count = Neurons.size();
		Random r = new Random();
		int rand = r.nextInt(count);
	//	System.out.println("Randnode: "+rand);
		return Neurons.get(rand);
	}
	
	public boolean containsLink(Gene g){
		for(Gene gene :this.Genes){
			if(gene.into==g.into&&gene.out==g.out){
				return true;
			}
		}
		return false;
	}
	
	public void pointMutate(){
		double step = this.mutationrates[6];
		for(Gene gene : this.Genes){
			if(Math.random() < PerturbChance){
				gene.weigth = gene.weigth + Math.random() * step*2-step;
			}
			else{
				gene.weigth = Math.random()*4-2;
			}
		}
	}
	
	public int linkMutate(boolean forceBias,int inovation){
		
		int neuron1 = this.randomNeuron(false);
		int neuron2 = this.randomNeuron(true);
		
		Gene newLink = new Gene();
		
		if((neuron1<Inputs||neuron1==Inputs+Outputs) && (neuron2 < Inputs||neuron2==Inputs+Outputs )){
			return 0;
		}
		if(neuron2 < Inputs||neuron2==Inputs+Outputs  ){
			int temp = neuron1;
			neuron1 = neuron2;
			neuron2 = temp;
		}
		newLink.into = neuron1;
		newLink.out = neuron2;
		if(forceBias){
			newLink.into = Inputs+Outputs;
		}
		
		if(this.containsLink(newLink)){
			return 0;
		}
		newLink.innovation = inovation++;
		newLink.weigth = Math.random()*4-2;
//		System.out.println("From: "+newLink.out+" TO: "+newLink.into);
		this.Genes.add(newLink);
		return inovation;
	}
	
	public int activitionMutate(int inovation){
		if(this.Genes.size() >0){
		Random rand = new Random();
		int i = rand.nextInt(this.Genes.size());
		this.Genes.get(i).activition = rand.nextInt(10);
		return inovation +1;
		}
		return inovation;
	}
	
	public int nodeMutate(int inovation){
		if(this.Genes.size() == 0){
			return 0;
		}
		this.maxneuron +=1;
		Random r = new Random();
		int rand = r.nextInt(this.Genes.size());
		Gene gene = this.Genes.get(rand);
		
		if(!gene.enabled){
			return 0;
		}
		gene.enabled = false;
		
		Gene gene1 = gene.copyGene();
		gene1.out = this.maxneuron;
		gene1.weigth = 1.0;
		gene1.innovation = inovation++;
		
		gene1.enabled = true;
		this.Genes.add(gene1);
		
		Gene gene2 = gene.copyGene();
		gene2.into = this.maxneuron;
		gene2.innovation = inovation++;
		gene2.enabled = true;
		this.Genes.add(gene2);
		return inovation;
	}
	
	public int enableDisableMutate(boolean enable){
		ArrayList<Gene> Auswahl = new ArrayList<Gene>();
		for(Gene gene:this.Genes){
			if(gene.enabled == !enable){
				Auswahl.add(gene);
			}
		}
		
		if(Auswahl.size() <= 0){
			return 0;
		}
		Random r = new Random();
		int rand = r.nextInt(Auswahl.size());
		Gene gene = Auswahl.get(rand);
		gene.enabled = !gene.enabled;
		return 0;
	}
	

	
	public int mutate(int inovation){

	//	Random r = new Random();
		for(int i =0;i<this.mutationrates.length;i++){
			if(Math.round(Math.random())==1){
				this.mutationrates[i] = this.mutationrates[i]* 0.95;
			}
			else{
				this.mutationrates[i] =this.mutationrates[i]* 1.05263;
			}
		}
		
		if(Math.random() < this.mutationrates[0]){
			 this.pointMutate();
		}
		
		double p = this.mutationrates[1];
		while(p>0){
			if(Math.random() < p){
				inovation = this.linkMutate(false,inovation);
			}
			p-=1;
		}
		
		p = this.mutationrates[2];
		while(p>0){
			if(Math.random() < p){
				inovation=this.linkMutate(true,inovation);
			}
			p-=1;
		}
		p = this.mutationrates[3];
		while(p>0){
			if(Math.random() < p){
				inovation=this.nodeMutate(inovation);
			}
			p-=1;
		}
		
		p = this.mutationrates[4];
		while(p>0){
			if(Math.random() < p){
				this.enableDisableMutate(true);
			}
			p-=1;
		}
		
		p = this.mutationrates[5];
		while(p>0){
			if(Math.random() < p){
				this.enableDisableMutate(false);
			}
			p-=1;
		}
		
		p = this.mutationrates[7];
		while(p>0){
			if(Math.random() < p){
				inovation = this.activitionMutate(inovation);
			}
			p-=1;
		}
		this.Innovation = inovation;
		return inovation;
	}
	
public double[] step(double[] Inputs){	
return this.step(Inputs, 10);
	
}


public double[] step(double[] Inputs, int act){	
//Inputs im Netzwerk setzen
int z=0;
for(double i:Inputs){
	this.Network.Neurons.get(z).value = i;
	z++;
}
//Biased Cell
if(this.Network.Neurons.size() > Outputs+this.Inputs){
	this.Network.Neurons.get(this.Inputs+Outputs).value = 1.0;
}

for(Neuron n:this.Network.Neurons){
	double sum =0;
	//int act[] =new int[n.incoming.size()];
	int ct = 0;
	for(Gene g:n.incoming){
	//	act[ct++] = g.activition;
		sum += g.weigth * this.Network.Neurons.get(g.into).value;
		
	}
//	if(n.incoming.size() >1){
//		int currentbest = 0;
//		int currenccount= 0;
//		int temp = 0;
//		for(int actid=0;actid<10;actid++){
//			temp = 0;
//			for(int a:act){
//				if(actid == a){
//					temp++;
//				}
//			}
//			if(temp > currenccount){
//				currenccount = temp;
//				currentbest = actid;
//			}
//			}
		n.value = sum;//activition(act,sum);//currentbest
//	}

}

double[] Output = new double[this.Outputs];
for(int i=0;i<this.Outputs;i++){
	Output[i] = activition(act,this.Network.Neurons.get(this.Inputs+i).value);
	//Output[i] = this.Network.Neurons.get(this.Inputs+i).value;
}
return Output;



}

public double activition(int n,double value){
	switch(n){
	case 0: return Math.sin(value);
	case 1: return Math.cos(value);
	case 2: double x = Math.tan(value);
	if(x<10000.0&&x>-10000.0) return x;
	if(x>10000.0) return 10000.0;
	if(x<-10000.0)return -10000.0;
	return 0.0;
	case 3:return Math.tanh(value);
	case 4:return (1.0-Math.exp(-value))/(1.0+Math.exp(-value));
	case 5:return Math.exp(-1.0*(value*value));
	case 6:return 1.0-2.0*(value-Math.floor(value));
	case 7:if((int)Math.floor(value)%2==0)return 1.0;
	return -1.0;
	case 8:if((int)Math.floor(value)%2==0)return 1.0-2.0*(value-Math.floor(value));
	return -1-2*(value-Math.floor(value));
	case 9: return -value;
	case 10: return (2/(1+Math.exp(-4.9*value))-1);
	case 11:return value;
	case 12:if((int)Math.floor(value)%2==0){
		return 1.0;
	}
	else{
	return 0.0;
	}
	//Sigmoid
//	case 1: return value; //Linear
//	case 2: return Math.round(value); //Binary
//	case 3: return Math.exp(-(value*value )); //Gaussian
//	case 4: return 2.0 / (1.0 + Math.exp(-(value * 2))) - 1.0; //Sigmoid Bipolar
//	case 5: if (value <= 0)return 0;
//	else if (value >= 1)return 1;
//	else return value;
//	case 6:if (value <= 0)value = 0;
//	else if (value >= 1)value = 1;
//	return (value * 2) - 1;
//	case 7: return Math.cos(value);
//	case 8: return Math.sin(value);
	
	}
	return value;
}


	
	public int compareTo(Genome arg0) {
		
		return (int) (((Genome)arg0).fitness -this.fitness);
	}

}
