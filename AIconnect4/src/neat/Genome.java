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
	public int fitness ;
	public int adjustedfitness;
	public transient Network Network;
	public int maxneuron ;
	public int globalRank;
	public double[] mutationrates;
	public int Innovation =0;
//	genome.mutationRates["connections"] = MutateConnectionsChance
//	genome.mutationRates["link"] = LinkMutationChance
//	genome.mutationRates["bias"] = BiasMutationChance
//	genome.mutationRates["node"] = NodeMutationChance
//	genome.mutationRates["enable"] = EnableMutationChance
//	genome.mutationRates["disable"] = DisableMutationChance
//	genome.mutationRates["step"] = StepSize
	public static final double Population = 300;
	public static final double DeltaDisjoint = 2.0;
	public static final double DeltaWeights = 0.4;
	public static final double DeltaThreshold = 1.0;
	public static final double StaleSpecies = 15;
	public static final double MutateConnectionsChance = 0.25;
	public static final double PerturbChance = 0.90;
	public static final double CrossoverChance = 0.75;
	public static final double LinkMutationChance = 2.0;
	public static final double NodeMutationChance = 0.50;
	public static final double BiasMutationChance = 0.40;
	public static final double StepSize = 0.1;
	public static final double DisableMutationChance = 0.4;
	public static final double EnableMutationChance = 0.2;
	public static final int Inputs = 42;
	public static final int Outputs = 3;
	public static final int MaxNodes = 10000;
	
	
	public Genome() {
		super();
		Genes = new ArrayList<Gene>();
		this.fitness = 0;
		this.adjustedfitness = 0;
		Network = new Network();
		this.maxneuron = 0;
		this.globalRank = 0;
		this.mutationrates = new double[7];
		this.mutationrates[0] = Genome.MutateConnectionsChance;
		this.mutationrates[1] = Genome.LinkMutationChance;
		this.mutationrates[2] = Genome.BiasMutationChance;
		this.mutationrates[3] = Genome.NodeMutationChance;
		this.mutationrates[4] = Genome.EnableMutationChance;
		this.mutationrates[5] = Genome.DisableMutationChance;
		this.mutationrates[6] = Genome.StepSize;
	}



	public static Genome basicGenome(){
		Genome g = new Genome();
		g.maxneuron = Inputs+Outputs;
		g.Innovation = Outputs;
		g.mutate();
		return g;
	}
	
	public Genome copyGenome(){
		Genome temp = new Genome();
		temp.Genes = new ArrayList<Gene>();
		for(Gene g :this.Genes){
			temp.Genes.add(g.copyGene());
		}
		temp.maxneuron = this.maxneuron;
		temp.mutationrates = new double[7];
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
		for(Gene ge:g.Genes){
			if(ge.enabled){
				boolean checker = false;
				while(net.Neurons.size()<=ge.out||net.Neurons.get(ge.out)== null){
					checker = true;
					net.Neurons.add(new Neuron()) ;
					net.Neurons.get(net.Neurons.size()-1).active = false;
				}
				net.Neurons.get(net.Neurons.size()-1).active = true;
				net.Neurons.get(ge.out).incoming.add(ge);
				checker = false;
				while(net.Neurons.size()<=ge.into||net.Neurons.get(ge.into)== null){
					checker = true;
					net.Neurons.add(new Neuron()) ;
					net.Neurons.get(net.Neurons.size()-1).active = false;
				}
				net.Neurons.get(net.Neurons.size()-1).active = true;
			}
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
		
		for(int i=Inputs;i<Inputs+Outputs;i++){
			Neurons.add(i);
		}
		
		for(int i=0;i<this.Genes.size();i++){
			if(!nonInput||this.Genes.get(i).into > Inputs){
				Neurons.add(this.Genes.get(i).into);
			}
			if(!nonInput||this.Genes.get(i).out > Inputs){
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
	
	public void linkMutate(boolean forceBias){
		
		int neuron1 = this.randomNeuron(false);
		int neuron2 = this.randomNeuron(true);
		
		Gene newLink = new Gene();
		
		if(neuron1<=Inputs && neuron2 <= Inputs){
			return;
		}
		if(neuron2 <= Inputs ){
			int temp = neuron1;
			neuron1 = neuron2;
			neuron2 = temp;
		}
		newLink.into = neuron1;
		newLink.out = neuron2;
		if(forceBias){
			newLink.into = Inputs;
		}
		
		if(this.containsLink(newLink)){
			return;
		}
		newLink.innovation = ++this.Innovation;
		newLink.weigth = Math.random()*4-2;
//		System.out.println("From: "+newLink.out+" TO: "+newLink.into);
		this.Genes.add(newLink);
	}
	
	public void nodeMutate(){
		if(this.Genes.size() == 0){
			return;
		}
		this.maxneuron +=1;
		Random r = new Random();
		int rand = r.nextInt(this.Genes.size());
		Gene gene = this.Genes.get(rand);
		
		if(!gene.enabled){
			return;
		}
		gene.enabled = false;
		
		Gene gene1 = gene.copyGene();
		gene1.out = this.maxneuron;
		gene1.weigth = 1.0;
		gene1.innovation = ++this.Innovation;
		gene1.enabled = true;
		this.Genes.add(gene1);
		
		Gene gene2 = gene.copyGene();
		gene2.into = this.maxneuron;
		gene2.innovation = ++this.Innovation;
		gene2.enabled = true;
		this.Genes.add(gene2);
		
	}
	
	public void enableDisableMutate(boolean enable){
		ArrayList<Gene> Auswahl = new ArrayList<Gene>();
		for(Gene gene:this.Genes){
			if(gene.enabled == !enable){
				Auswahl.add(gene);
			}
		}
		
		if(Auswahl.size() <= 0){
			return;
		}
		Random r = new Random();
		int rand = r.nextInt(Auswahl.size());
		Gene gene = Auswahl.get(rand);
		gene.enabled = !gene.enabled;
	}
	
	public void mutate(){
		Random r = new Random();
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
				this.linkMutate(false);
			}
			p-=1;
		}
		
		p = this.mutationrates[2];
		while(p>0){
			if(Math.random() < p){
				this.linkMutate(true);
			}
			p-=1;
		}
		p = this.mutationrates[3];
		while(p>0){
			if(Math.random() < p){
				this.nodeMutate();
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
	}
	
public double[] step(double[] Inputs){
	
	//Inputs im Netzwerk setzen
	int z=0;
	for(double i:Inputs){
		this.Network.Neurons.get(z).value = i;
		z++;
	}
	
	for(Neuron n:this.Network.Neurons){
		double sum =0;
		for(Gene g:n.incoming){
			sum += g.weigth * this.Network.Neurons.get(g.into).value;
		}
		if(n.incoming.size() >1){
			n.value = (2/(1+Math.exp(-4.9*sum))-1);
		}
	}
	
	double[] Output = new double[Genome.Outputs];
	for(int i=0;i<Genome.Outputs;i++){
		Output[i] = this.Network.Neurons.get(Genome.Inputs+i).value;
	}
	return Output;

	
	
}


	
	public int compareTo(Genome arg0) {
		
		return ((Genome)arg0).fitness -this.fitness;
	}

}
