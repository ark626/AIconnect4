package neat.test;

import static org.junit.Assert.*;

import org.junit.Test;

import neat.Gene;
import neat.Genome;
import neat.Pool;
//import neat.visualizer;

public class test {
	static int input = 10;
	static int output = 10;
	static int geninmax=100;
	static int genoutmax=100;
	static int gengenmax=100;
	@Test
	public void initializeGenome(){
		Genome g = null;
		for(int i=1;i<geninmax;i++){
			for(int j=1;j<genoutmax;j++){
				for(int k=0;k<gengenmax;k++){
				g = new Genome(i, j, k);	
				g.generateNetwork();

				if(g.Inputs!=i)
					fail("Input nodes differ from input" + g.Inputs+" "+i);
				if(g.Outputs!=j)
					fail("Outputs nodes differ from input" + g.Outputs+" "+j);
				if(g.Generation!=k)
					fail("Generation differs from input" + g.Generation+" "+k);
				if(g.Network.Neurons.size()!=i+j){
					fail("Networks neurons differs from Output+Input"+g.Network.Neurons.size()+" "+i+j);
				}
				}
			}
		}
		
	}
	
	@Test
	public void initializePool(){
		Pool test = new Pool(input,output);
		if(test.generation!=0){
			fail("Generation is grater than 0");
		}
		if(test.alreadyMeasured()){
			fail("Pool already measured");
		}
	}
	
	
	
	@Test
	public  void Mutateandgenerategenomes(){
		
		Pool test = new Pool(input,output);
		int countgenomes = 0;
	    for(int i = 0;i<100;i++){
	    	int j = test.currentSpecies;
	    	Genome temp = test.Species.get(test.currentSpecies-1).Genomes.get(test.currentGenome-1);
	    	temp.generateNetwork();
	    	test.nextGenome();
	    	for(Gene g:temp.Genes){
	    		if(g.out != g.into&&g.weigth!=0){
	    			countgenomes++;
	    		}
//	    	for(Gene g:temp.Genes){
//	    		//System.out.print("Von "+g.out +" Zu "+g.into);
//	    		//if(g.out!= null&&g.into != null)
//	    	}
//	    	test.alreadyMeasured();
	    
	    
	    
	    }
		}
	    System.out.println(countgenomes+" valid Genes generated");
	    if(countgenomes <=1){
	    	fail("To less Genes generated");
	    }
		}
	}


