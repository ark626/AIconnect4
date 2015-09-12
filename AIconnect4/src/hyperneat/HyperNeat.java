package hyperneat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import neat.Gene;
import neat.Genome;
import neat.Neuron;
import neat.Pool;

public class HyperNeat {
	public int Input;
	public int Output;
	public int Hidden;
	public int x;
	public int y;
	public Neuron Neurons[];
	public ArrayList<Gene> Links;
	public Pool pool;
	
	public HyperNeat(int Input,int Output, int Hidden, int xsize,int ysize,Pool pool){
		this.Input = Input;
		this.Output= Output;
		this.Hidden= Hidden;
		this.x = xsize;
		this.y = ysize;
		this.pool = pool;
		this.Neurons = new Neuron[Input*2+Output];
		this.Links= new ArrayList<Gene>();
		for(int i = 0;i<Input*2+Output;i++){
			this.Neurons[i] = new Neuron();
		}
		
		//Generate empty links from Input to hidden (Input^2)
		int no = 0;
		int ni = Input;
		for(int i = 0;i<Input*Input;i++){
			this.Links.add(new Gene());
			//From is i
			this.Links.get(i).into = no; 
			//To is j+Inputsize-1
			this.Links.get(i).out = ni;
			this.Neurons[ni].incoming.add(this.Links.get(i));
			no++;
			if(no >= this.Input){
				no = 0;
				ni++;
			}
			if(ni >= Input*2){
				ni = 0;
			}
			
		}
		
		no = Input;
		ni = Input*2;
		
		//Generate empty Hidden to Output Links
		for(int i = Input*Input;i<(Input*Input)+Input*3;i++){
		    
			this.Links.add(new Gene());
			//From is i
			this.Links.get(i).into = no; 
			//To is j+Inputsize-1
			this.Links.get(i).out = ni;
			this.Neurons[ni].incoming.add(this.Links.get(i));
			no++;
			if(no >= this.Input*2){
				no = Input;
				ni++;
			}
			if(ni >= Input*2+Input*3){
				ni = Input*2;
			}
			}
	//	Collections.sort(Links);
			
		}
	
	public void generateweigths(Genome CPPN){
		//Genome CPPN = this.pool.Species.get(this.pool.currentSpecies-1).Genomes.get(this.pool.currentGenome-1);
		CPPN.generateNetwork();
		int i = 0;
		boolean Empty = true;
		for(Gene g:Links){
			double[] Inputs = new double[4];
					Inputs[0] = g.into%7+1;
					Inputs[1] = (g.into/7)%6+1;
					Inputs[2] = g.out%7+1;
					Inputs[3] = (g.out/7)%6+1;
				

				g.weigth = CPPN.step(Inputs)[0];
				if(g.weigth != 0.0 ){
					Empty = false;
				}
//				if(g.weigth != 0.0){
//				try {
//					String path = "/tmp/";
//					String path2= "/tmp/";
//					//long time2 = System.currentTimeMillis()-time;
//					String content = "";
//					File file = null;
//					
//					content = "Gewicht: "+this.Links.get(i++).weigth+" Von: "+g.out+"/"+g.into+" "+g.out%7+"/"+(g.out/7)%6+" "+g.into%7+"/"+(g.into/7)%6+" ";
//					path += "HyperNeat.txt";
//					file = new File(path);
//					//System.out.println("Done");
//					
//					
//					// if file doesnt exists, then create it
//					if (!file.exists()) {
//						file.createNewFile();
//					}
//					FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
//					BufferedWriter bw = new BufferedWriter(fw);
//					bw.write(content+"\n");
//					bw.close();
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
		}
		if(Empty){
			CPPN.fitness = -9999;
			}
		Collections.sort(Links);
		
		
		
	}
		
	
	public double[] step(double[] Inputs){
		
		//Inputs im Netzwerk setzen
		int z=0;
		for(double i:Inputs){
			this.Neurons[z].value = i;
			z++;
		}
//		if(this.Neurons.length > Output+this.Input){
//			//this.Neurons.get(this.Input+Output).value = 1.0;
//		}
		
		for(int i = this.Input;i<this.Neurons.length;i++){
			Neuron n = this.Neurons[i];
			double sum =0;
			for(Gene g:n.incoming){
				if(Neurons[g.into].value != 0.0&&g.weigth !=0.0){
				sum += g.weigth * this.Neurons[g.into].value;
			//	System.out.println("LOL: "+sum);
				}
				
			}

			if(n.incoming.size() >1){
				n.value = (2/(1+Math.exp(-4.9*sum))-1);
			}
		}
		
		double[] Output = new double[this.Output];
		int j = 0;
	//	String s = "Outputs: ";
		for(int i=this.Neurons.length-3;i<this.Neurons.length;i++){
			Output[j] = this.Neurons[i].value;
			
			//s+= " "+Output[j];
			j++;
		}
	//	System.out.println(s);
		return Output;

		
		
	}
	
}
