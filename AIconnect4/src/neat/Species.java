package neat;

import java.util.ArrayList;
import java.util.Random;

public class Species {
	public int topFitness;
	public int staleness;
	public ArrayList<Genome> Genomes;
	public int averageFitness;
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
	public static final int MaxNodes = 1000000;
	
	public Species(){
		this.topFitness = 0;
		this.staleness = 0;
		this.Genomes = new ArrayList<Genome>();
		this.averageFitness = 0;
	}
	
	public void calculateAverageFitness(){
		int total = 0;
		
		for(Genome g:this.Genomes){
			
			total += g.globalRank;
			
		}
		this.averageFitness = total/this.Genomes.size();
		//return total/this.Genomes.size();
	}
	
	public Genome crossover(Genome g1,Genome g2){
		if(g1.fitness > g2.fitness){
			Genome temp = g1;
			g1 = g2;
			g2 = temp;
		}
		int i =0;
		double[] temp = new double[7];
		for(double z :g1.mutationrates){
			temp[i++] = z;
		}
		Genome child = new Genome();
		ArrayList<Gene> Innovation = new ArrayList<Gene>();
		for(Gene gene:g2.Genes){
			Innovation.add(gene);
		}
		
		for(Gene gene:g1.Genes){
			Gene gene1 = gene;
			Gene gene2 = null;
			for(Gene g:Innovation){
				if(g.innovation == gene1.innovation){
			    	gene2 = g;
				}
			}
			int rand = (int)Math.round(Math.random());
			if(gene2 != null &&rand  == 1&&gene2.enabled){
				child.Genes.add(gene2.copyGene());
			}
			else{
				child.Genes.add(gene1.copyGene());
			}
		}
		child.maxneuron = Math.max(g1.maxneuron, g2.maxneuron);
		return child;

	} 
	
	public Genome breedChild(){
		Genome child = new Genome();
		Random r = new Random();
		if(Math.random() <CrossoverChance){
			int rand = r.nextInt(this.Genomes.size());
			Genome g1 = this.Genomes.get(rand);
			rand = r.nextInt(this.Genomes.size());
			Genome g2 = this.Genomes.get(rand);
			child = this.crossover(g1,g2);
		}
		else{
			int rand = r.nextInt(this.Genomes.size());
			child = (this.Genomes.get(rand)).copyGenome();
			
		}
		child.mutate();
		return child;
	}
	
	

}
