package eshyperneat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import neat.Gene;
import neat.Genome;
import neat.Neuron;
import neat.Pool;

public class EsHyperNeat {
	public int Input;
	public int Output;
	public int Hidden;
	public int sublayerfactor;
	public int layers;
	public int[] layersizes;
	public ArrayList<Layer> Neurons;
	public Links Links[];
	public Pool pool;
	
	
//	public int calcLayer(int layer){
//		int size = Input;
//		for(int i =layer;i>0;layer--){
//			size = (int)Math.floor(Math.sqrt(size));
//		}
//		return size;
//	}
	
	public EsHyperNeat(int Input,int Output,int layers,Pool pool){
		this.Input = Input;
		this.Output= Output;
		this.layersizes = new int[layers+1];
		this.layers = layers;
		this.pool = pool;
		this.Neurons = new ArrayList<Layer>();
		this.Links = new Links[layers+1];
		
		
		//Create neuron layers;
		int temp = Input;
		Neurons.add(new Layer(temp+1,temp+1));
		
	for(int i = 0;i<=layers;i++){
		temp = (int)Math.ceil(Math.sqrt(temp));
		layersizes[i] = temp;
		Neurons.add(new Layer(temp+1,temp+1));
	}
		
//First layer to First hidden layer
	int nodecalc = this.layersizes[0]/layersizes[1];
	Links[0] = new Links();
	for(int i = 0;i<layersizes[0];i++){
		
		for(int j = 0;j<nodecalc;j++){
		Gene g = new Gene();
		g.setOut(i);
		g.setInto(j);
		Links[0].Links.add(g);
		Neurons.get(0).Neurons[i].getOutgoing().add(g);
		Neurons.get(1).Neurons[j+i].getIncoming().add(g);
		}
	}
//Link Creation from Layer to Layer
	nodecalc = this.layersizes[1]/layersizes[2];
	Links[1] = new Links();
	for(int i = 0;i<layersizes[1];i++){
		for(int j = 0;j<nodecalc;j++){
		Gene g = new Gene();
		g.setOut(i);
		g.setInto(j);
		Links[0].Links.add(g);
		Neurons.get(1).Neurons[i].getOutgoing().add(g);
		Neurons.get(2).Neurons[j].getIncoming().add(g);
		}
	}
	Links[2] = new Links();
	nodecalc = this.layersizes[2]/Output;
	for(int i = 0;i<Output;i++){
		for(int j = 0;j<nodecalc;j++){
		Gene g = new Gene();
		g.setOut(i);
		g.setInto(j);
		Links[0].Links.add(g);
		Neurons.get(1).Neurons[i].getOutgoing().add(g);
		Neurons.get(2).Neurons[j].getIncoming().add(g);
		}
	}
	
	
//		Collections.sort(Links);
		

	}
	

	
	public long topFit(){
		return this.pool.getTopfitness();
	}
	
	public void generateweigths(Genome CPPN){
		//Genome CPPN = this.pool.Species.get(this.pool.currentSpecies-1).Genomes.get(this.pool.currentGenome-1);
		CPPN.generateNetwork();
	//	int i = 0;
//		for(int i = 0;i<Neurons.size();i++){
//		for(Neuron n:Neurons.get(i).Neurons){
//			n.value = 0.0;
//		}
//		}
		
		for(int z = 0;z<Links.length;z++){
		boolean Empty = true;
		int i = 0;
		for(Gene g:Links[z].Links){
			i++;
			double[] Inputs = new double[5];
					Inputs[0] = g.getInto()%layersizes[z];
					Inputs[1] = g.getInto()/layersizes[z];
					Inputs[2] = g.getOut() %layersizes[z];
					Inputs[3] = g.getOut() /layersizes[z];
					Inputs[4] = z;
					
//System.out.println(Inputs[0]+" " +Inputs[1]+" " +Inputs[2]+" " +Inputs[3] );
				if(i>=Input*Input){
					g.setWeigth((CPPN.step(Inputs,4)[0]));
				}
				else{
				g.setWeigth((CPPN.step(Inputs,0)[0]));
				}
				if(g.getWeigth() != 0.0 ){
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
	//	System.out.println("Nodes"+CPPN.Network.Neurons.size()+" Genes "+CPPN.Genes.size());
		if(Empty){
			CPPN.setFitness(-999999,false,false);
//			this.pool.nextGenome();
//			this.pool.Species.get(pool.currentSpecies-1).Genomes.get(pool.currentGenome-1).generateNetwork();
//			this.generateweigths(this.pool.Species.get(pool.currentSpecies-1).Genomes.get(pool.currentGenome-1));
			}
		}
//		Collections.sort(Links);
//		String s="T: ";
//		Neuron n2 = Neurons[0];
//		Neuron n = Neurons[0];
//		for(int j =0;j<Neurons.length;j++){
//		 n = Neurons[j];
//		 s+= this.neuronPath(n,j);
//		System.out.println(s);	
//		s= "";
//		}
		
	
		
		
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
		case 13:return 2.0 / (1.0 + Math.exp(-(value * 2))) - 1.0;
		}
		return 0;
		//Sigmoid
//		case 1: return value; //Linear
//		case 2: return Math.round(value); //Binary
//		case 3: return Math.exp(-(value*value )); //Gaussian
//		case 4: return 2.0 / (1.0 + Math.exp(-(value * 2))) - 1.0; //Sigmoid Bipolar
//		case 5: if (value <= 0)return 0;
//		else if (value >= 1)return 1;
//		else return value;
//		case 6:if (value <= 0)value = 0;
//		else if (value >= 1)value = 1;
//		return (value * 2) - 1;
//		case 7: return Math.cos(value);
//		case 8: return Math.sin(value);
		
		}
	
	public double[] step(double[] Inputs){
//		String test = "Input: ";
		//Inputs im Netzwerk setzen
		for(int i=0;i<layers;i++){
		for(int z=0;z<layersizes[i];z++){
			Neuron n = Neurons.get(i).Neurons[z];
			n.setValue(0.0);
		}
		}
		
	//	for(int j=0;j<layers;j++){
		for(int i =0;i<Inputs.length;i++){
			this.Neurons.get(0).Neurons[i].setValue(Inputs[i]);
//			test+=i+" ";
		}
	//	}

		//Just 1 layer
		//From Input to first Layer
		for(int j=0;j<layers;j++){
		for(int i = 0;i<layersizes[j];i++){
			Neuron n = this.Neurons.get(j).Neurons[i];
			if(n.getValue() != 0.0){
				for(Gene g:n.getOutgoing()){
					if(g.getWeigth() != 0.0){
						Neurons.get(j+1).Neurons[g.getInto()].setValue(this.activition(11,n.getValue()*g.getWeigth()+Neurons.get(j+1).Neurons[g.getInto()].getValue()));
					//	Neurons[g.into].value += n.value*g.weigth;
					}
				}
			}
		}
		}
		
		double[] Output = new double[this.Output];
		int j = 0;
		String s = "Outputs: ";
		for(int i=Input*2;i<this.Neurons.get(this.Neurons.size()-1).Neurons.length;i++){
Output[j] = Math.round(this.activition(10, Neurons.get(Neurons.size()-1).Neurons[i].getValue()));

s+= " "+Output[j]+"";
			j++;
			
		}
//System.out.println(s);
		return Output;
	}
	
	public double getValues(int n,int z,int act){
		double d = 0;
		int i = 0;
		for(Gene g:Neurons.get(z).Neurons[n].getIncoming()){
			if(i<=50){
			 d= ((d+g.getWeigth()*Neurons.get(z).Neurons[g.getOut()].getValue()));
			 i++;
			}
			else{
			d=this.activition(act, d);
			i=0;
			}
		}
		return d;
	}
	
//public double[] stepold(double[] Inputs){
//		
//		//Inputs im Netzwerk setzen
//		int z=0;
//		for(double i:Inputs){
//			this.Neurons[z].value =i;
//			z++;
//		}
////		if(this.Neurons.length > Output+this.Input){
////			//this.Neurons.get(this.Input+Output).value = 1.0;
////		}
//		
//		for(int i = this.Input;i<this.Neurons.length;i++){
//		//	Neuron n = this.Neurons[i];
//		//	double sum =0;
//		//	for(Gene g:n.incoming){
//			//	if(Neurons[g.out].value != 0.0&&g.weigth !=0.0){
//				
//				Neurons[i].value = this.getValues(i, 12);//this.activition(12,g.weigth * this.Neurons[g.out].value+n.value);
//				//System.out.println("LOL: "+sum);
////				}
////				
////			}
//
////			if(n.incoming.size() >1){
////		//		n.value = (2/(1+Math.exp(-4.9*sum))-1);
////			}
//		}
//		
//		double[] Output = new double[this.Output];
//		int j = 0;
//		String s = "Outputs: ";
//		for(int i=this.Neurons.length-this.Output;i<this.Neurons.length;i++){
//			Output[j] = this.Neurons[i].value;
//			
//			s+= " "+Output[j];
//			j++;
//		}
//	//	System.out.println(s);
//		return Output;
//
//		
//		
//	}
//
//@Override
//public int hashCode() {
//	final int prime = 31;
//	int result = 1;
//	result = prime * result + Hidden;
//	result = prime * result + Input;
//	result = prime * result + ((Links == null) ? 0 : Links.hashCode());
//	result = prime * result + Arrays.hashCode(Neurons);
//	result = prime * result + Output;
//	result = prime * result + ((pool == null) ? 0 : pool.hashCode());
//	result = prime * result + x;
//	result = prime * result + y;
//	return result;
//}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	EsHyperNeat other = (EsHyperNeat) obj;
	if (Hidden != other.Hidden)
		return false;
	if (Input != other.Input)
		return false;
	if (Links == null) {
		if (other.Links != null)
			return false;
	} else if (!Links.equals(other.Links))
		return false;
//	if (!Arrays.equals(Neurons, other.Neurons))
//		return false;
	if (Output != other.Output)
		return false;
//	if (pool == null) {
//		if (other.pool != null)
//			return false;
//	} else if (!pool.equals(other.pool))
//		return false;

	return true;
}



	
}
