package neat.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import hyperneat.HyperNeat;
import neat.Genome;
import neat.Pool;

public class Hyperneatsetptester {

	@Test
	public void test() {
		Pool p = new Pool(4,1);
		HyperNeat test = new HyperNeat(42,3,0,7,6,p);
		Random r = new Random();
		Genome g = null;
		for(int k = 0;k<10;k++){
			g = test.pool.Species.get(test.pool.currentSpecies-1).Genomes.get(test.pool.currentGenome-1);
			g.generateNetwork();
			test.generateweigths(g);
		while(test.pool.alreadyMeasured()){
			g = test.pool.Species.get(test.pool.currentSpecies-1).Genomes.get(test.pool.currentGenome-1);
			g.generateNetwork();
			test.generateweigths(g);
		}
		for(int z=1;z<1000;z++){
			double[] in = randInput(r,test.Input);
			double[] t1 = test.step(in);
			double[] t2 = test.stepold(in);
			for(int j=0;j<t1.length;j++){
			//	System.out.println(t1[j]+" | " +t2[j]);
				if(t1[j]!=t2[j]){
					fail("Failed: " + t1[j]+" " +t2[j]);
					//System.out.println(t1[j]+" | " +t2[j]);
				}
				if(t1[j]>=0.5){//||t2[j]<1){
					System.out.println(t1[j]+" | " +t2[j]);
				}
			}
		}
		g.fitness =r.nextInt(123);
		}
	//	fail("Not yet implemented");
	}
	
	public static double[] randInput(Random r,int Output){
		
		double[] in = new double[Output];
		for(int i = 0;i<Output;i++){
		in[i] = r.nextDouble();
		}
		return in;
	}

}
