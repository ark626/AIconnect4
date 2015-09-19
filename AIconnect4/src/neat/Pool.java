package neat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ki.KI;
import ki.KICluster;
import ki.Link;
import ki.Node;

public class Pool implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8913652205956204258L;
	public ArrayList<Species> Species;
	public int generation;
	public int Innovation;
	public int currentSpecies;
	public int currentGenome;
	public int currentFrame;
	public int maxFitness;
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
	public int Inputs = 42;
	public int Outputs = 3;
	public static final int MaxNodes = 1000000;
	
	public Pool(int in, int out) {
		super();
		this.Inputs = in;
		this.Outputs = out;
		Species = new ArrayList<Species>();
		this.generation = 0;
		Innovation = Outputs;
		this.currentSpecies = 1;
		this.currentGenome = 1;
		this.currentFrame = 0;
		this.maxFitness = 0;
		for(int i=0;i<Population;i++){
			Genome basic = Genome.basicGenome(this.Inputs,this.Outputs);
			this.Innovation = basic.mutate(this.Innovation);
			this.addToSpecies(basic);
		}
		
	}
	
	public Genome getbest(){
		Genome temp = this.Species.get(0).Genomes.get(0);
		int maxfit = temp.fitness;
		for(Species s:this.Species){
			for(Genome g:s.Genomes){
				if(g.fitness!=0){
				if((g.fitness >= maxfit)){
					temp = g;
					maxfit = temp.fitness;
					this.currentGenome = s.Genomes.indexOf(temp)+1;
					this.currentSpecies = this.Species.indexOf(s)+1;
				}
				}
			}
		}
		
		
		return temp;
	}
	
	public Pool(int i,int in, int out){
		super();
		this.Inputs = in;
		this.Outputs =out;
		Species = new ArrayList<Species>();
		this.generation = 0;
		Innovation = Outputs;
		this.currentSpecies = 1;
		this.currentGenome = 1;
		this.currentFrame = 0;
		this.maxFitness = 0;
		
	}
	
	public void removeStaleSpecies(){
		ArrayList<Species> survivors = new ArrayList<Species>();
		for(Species s:this.Species){
			Collections.sort(s.Genomes);
			
			if(s.Genomes.get(0).fitness >= s.topFitness){
				s.topFitness = s.Genomes.get(0).fitness;
				s.staleness = 0;
			}
			else{
				s.staleness += 1;
			}
			if(s.staleness < StaleSpecies|| s.topFitness >= this.maxFitness){
				survivors.add(s);
			}
		}
		this.Species = survivors;
	}
	
	public void removeWeakSpecies(){
		int sum = this.totalAverageFitness();
		ArrayList<Species> survivors = new ArrayList<Species>();
		for(Species s:this.Species){
		s.calculateAverageFitness();
			int breed = (int)Math.ceil(((double)s.averageFitness/(double)sum*Population)) ;

			if(breed >= 1){
				survivors.add(s);
			}
		}
		this.Species = survivors;
	}
	
	public void removeEmptySpecies(){
		for(Species s:this.Species){
			if(s.Genomes.size() == 0){
				this.Species.remove(s);
			}
		}
	}
	
	public int disjoint(ArrayList<Gene> g1, ArrayList<Gene> g2){
		ArrayList<Boolean> i1= new ArrayList<Boolean>();
		int maxinovation = 0;
		for(Gene g:g1){
			if(g.innovation > maxinovation){
				maxinovation = g.innovation;
			}
		}
		for(Gene g:g2){
			if(g.innovation > maxinovation){
				maxinovation = g.innovation;
			}
		}
		for(int i=0;i<maxinovation+1;i++){
			i1.add(false);
		}
		for(Gene g:g1){
			i1.set(g.innovation,true);
		}
		ArrayList<Boolean> i2= new ArrayList<Boolean>();
		for(int i=0;i<maxinovation+1;i++){
			i2.add(false);
		}
		for(Gene g:g2){
			i2.set(g.innovation,true);
		}
		
		int disjointGenes = 0;
		for(Gene g:g1){
			if(!i2.get(g.innovation)){
				disjointGenes += 1;
			}
		}
		for(Gene g:g2){
			if(!i1.get(g.innovation)){
				disjointGenes += 1;
			}
		}
		
		int n = Math.max(g1.size(), g2.size());
		if(n > 0){
			
		return disjointGenes /n;
		}
		else return 0;
	}
	
	public int weigths(ArrayList<Gene> g1, ArrayList<Gene> g2){
		ArrayList<Gene> i2= new ArrayList<Gene>();
		for(Gene g:g2){
			i2.add(g);
		}
		
		int sum = 0;
		int zufall = 0;
		for(Gene g:g1){
			if(i2.size() > g.innovation){
			if(i2.get(g.innovation)!=null){
				Gene gene2 = i2.get(g.innovation);
				sum += Math.abs(g.weigth-gene2.weigth);
				zufall = zufall +1;
			}}
		}
		if(zufall>0){
		return sum/zufall;
		}
		else return 0;
	}
	
	public boolean sameSpecies(Genome g1, Genome g2){
		double dd = DeltaDisjoint*disjoint(g1.Genes,g2.Genes);
		double dw = DeltaWeights*weigths(g1.Genes,g2.Genes);
		return (dd+dw<DeltaThreshold);
	}
	
	public void addToSpecies(Genome child){
		boolean found = false;
		for(Species s : this.Species){
			if(!found && sameSpecies(child,s.Genomes.get(0))){
				s.Genomes.add(child);
				found = true;
			}
		}
		if(!found){
			Species childSpecies = new Species(this.Inputs,this.Outputs);
			childSpecies.Genomes.add(child);
			this.Species.add(childSpecies);
		}
		
		
	}
	
	public void rankGlobally(){
		ArrayList<Genome> global = new ArrayList<Genome>();
		for(Species s:this.Species){
			for(Genome g:s.Genomes){
				global.add(g);
			}
		}
		Collections.sort(global);
		int i =1;
		for(Genome g:global){
			g.globalRank = i++;
		}
	}
	
	public int totalAverageFitness(){
		int total =0;
		for(Species s:this.Species){
			//s.calculateAverageFitness();
			total += s.averageFitness;
			
		}

		return total;
	}
	
	public void cullSpecies(boolean cutToOne){
		
		
		for(Species s:this.Species){
			Collections.sort(s.Genomes);
			
			int remain = (int)Math.ceil(s.Genomes.size()/2);
			if(cutToOne){
				remain = 1;
			}
//			ArrayList<Genome> remove = new ArrayList<Genome>();
//			for(Genome g:s.Genomes){
//				if(g.fitness == -9999){
//					remove.add(g);
//				}
//			}
//			for(Genome g: remove){
//				s.Genomes.remove(g);
//			}
			while(s.Genomes.size()-2 > remain){
				s.Genomes.remove(s.Genomes.size()-1);
				
			}
		}
	}
	
	public void newGeneration(){
		this.cullSpecies(false);
		this.rankGlobally();
		this.removeStaleSpecies();
		this.rankGlobally();
		
		for(Species s:this.Species){
			s.calculateAverageFitness();
		}
		
		this.removeWeakSpecies();
		
		
		int sum = totalAverageFitness();
		ArrayList<Genome> children = new ArrayList<Genome>();
		for(Species s:this.Species){
			int breed = (int)Math.ceil(Math.floor((double)s.averageFitness/(double)sum*Population)-1);
			for(int i = 0;i<breed;i++){
				Genome ge = s.breedChild();
				this.Innovation = ge.mutate(this.Innovation);
				while(ge.Genes.size() ==0){
					ge = s.breedChild();
				this.Innovation = ge.mutate(this.Innovation);
				}
				
				children.add(ge);
			}
		}
		this.cullSpecies(true);
		Random r = new Random();
//		this.removeEmptySpecies();
//		if(this.Species.size()<=0){
//			for(int i=0;i<Population;i++){
//				Genome basic = Genome.basicGenome();
//				this.Innovation = basic.mutate(this.Innovation);
//				this.addToSpecies(basic);
//			};
//		}
		while(children.size()+this.Species.size()<Population){
			
			Species s = this.Species.get(r.nextInt(this.Species.size()));
			Genome ge = s.breedChild();
			this.Innovation = ge.mutate(this.Innovation);
			
			while(ge.Genes.size()==0){
				 ge = s.breedChild();
			this.Innovation = ge.mutate(this.Innovation);
			}
			
			if(ge.Genes.size()>0){
			children.add(ge);
			}
		}
		for(Genome g:children){
			this.addToSpecies(g);
		}
		this.generation += 1;
		
		//TODO: Save
	}
	
	public void nextGenome(){
		this.currentGenome += 1;
		if(this.currentGenome >= this.Species.get(this.currentSpecies-1).Genomes.size()){
		currentGenome = 1;
		currentSpecies += 1;
		if(currentSpecies >= this.Species.size()){
			this.newGeneration();
			//System.out.println(this.toString());
			this.currentSpecies = 1;
			this.currentGenome = 1;
		}
		}
	}
	
	@Override
	public String toString(){
		String test="";
		for(Species s:this.Species){
			for(Genome g:s.Genomes){
			if(s.Genomes.size()<1){
				test += " Error in "+ Species.indexOf(s);
			}
			}
		}
		return test;
			
	}
	
	public boolean alreadyMeasured(){
		
		return (this.Species.get(this.currentSpecies-1).Genomes.get(this.currentGenome-1).fitness != 0);
	}
	
	//Serializable
	
	public void save(String s,int i){
		 try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream(s);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         //writing KI's
	         out.writeInt(this.Inputs);
	         out.writeInt(this.Outputs);
	         out.writeInt(this.generation);
	         out.writeInt(this.Innovation);
	         out.writeInt(this.maxFitness);
	         out.writeInt(this.Species.size());
	         for(Species sp:this.Species){
	         out.writeInt(sp.Genomes.size());
	         out.writeInt(sp.topFitness);
	         out.writeInt(sp.averageFitness);
	         for(Genome g :sp.Genomes){
	        	 out.writeObject(g);
	         }


	         }
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in "+s);
	      }catch(IOException e)
	      {
	          e.printStackTrace();
	        //  this.save("/tmp/employee"+i+1+".ser",i+1);
	      }
	   }
	
	public static Pool load(String s) throws ClassNotFoundException{
	      try
	      {
	         FileInputStream fileIn = new FileInputStream(s);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         int inn = in.readInt();
	         int out = in.readInt();
	         Pool p = new Pool(1,inn,out);
	         p.Inputs = inn;
	         p.Outputs = out;
	         p.generation = in.readInt();
	         p.Innovation = in.readInt();
	         p.maxFitness = in.readInt();
	         p.currentGenome = 1;
	         p.currentSpecies = 1;
	         int Speciessize = in.readInt();
	         for(int i=0;i<Speciessize;i++){
	        	 int Genomesize = in.readInt();
	        	 Species spe = new Species(p.Inputs,p.Outputs);
	        	 spe.topFitness = in.readInt();
	        	 spe.averageFitness = in.readInt();
	        	 for(int j=0;j<Genomesize;j++){
	        		 Genome g = (Genome)in.readObject();

	        		spe.Genomes.add(g);
	        	 }
	        	 p.Species.add(spe);
	         }
	         
	         in.close();
	         fileIn.close();
	         return p;
	      }catch(IOException i)
	      {
	    	  i.getMessage();
	    	  return null;
	    	  //return new Pool();

	      }

	      }
		
	}


