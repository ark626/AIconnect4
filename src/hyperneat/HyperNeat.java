package hyperneat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import neat.Gene;
import neat.Genome;
import neat.Neuron;
import neat.Pool;

public  class  HyperNeat {
	private int Input;
	private int Output;
	private int Hidden;
	private int x;
	private int y;
	private Neuron Neurons[];
	private ArrayList<Gene> Links;
	private Pool pool;
	
	public  HyperNeat(int Input,int Output, int Hidden, int xsize,int ysize,Pool pool){
		this.Input = Input;
		this.Output= Output;
		this.Hidden= Hidden;
		this.x = xsize;
		this.y = ysize;
		this.pool = pool;
		this.Neurons = new Neuron[Input*2+Output];
		this.Links= new ArrayList<Gene>(Input*Input+Output*Input);
		for(int i = 0;i<Input*2+Output;i++){
			this.Neurons[i] = new Neuron();
		}
		

		
		//Generate empty links from Input to hidden (Input^2)
		int no = 0;
		int ni = Input;
		for(int i = 0;i<Input*Input;i++){
			this.Links.add(new Gene());
			this.Links.get(i).setOut(no);
			this.Links.get(i).setInto(ni);
			this.Neurons[ni++].addIncoming(this.Links.get(i));
			this.Neurons[no].addOutgoing(this.Links.get(i));
			if(ni>=Input+Input){
				ni = Input;
				no+= 1;
			}
		}
		 no = Input;
		 ni = Input+Input;
		for(int i = 0;i<Input*Output;i++){
			this.Links.add(new Gene());
			this.Links.get(i+Input*Input).setOut(no); 
			this.Links.get(i+Input*Input).setInto(ni); 
			this.Neurons[ni++].addIncoming(this.Links.get(i+Input*Input));
			this.Neurons[no].addOutgoing(this.Links.get(i+Input*Input));
			if(ni>=(Input+Input+Output)){
				ni = Input+Input;
				no+= 1;
			}
		}

//		for(int i = 0;i<Input*Input;i++){
//			this.Links.add(new Gene());
//			//From is i
//			this.Links.get(i).out = no; 
//			//To is j+Inputsize-1
//			this.Links.get(i).into = ni;
//			this.Neurons[ni].incoming.add(this.Links.get(i));
//			no++;
//			if(no >= this.Input){
//				no = 0;
//				ni++;
//			}
//			if(ni >= Input*2){
//				ni = 0;
//			}
//			
//		}
//		 ni = Input+Input;
//		 no = ;
//		for(int i = 0;i<Input*Input;i++){
//			this.Links.add(new Gene());
//			this.Links.get(i).out = no;
//			this.Links.get(i).into = ni++;
//			this.Neurons[ni].incoming.add(this.Links.get(i));
//			this.Neurons[no].outgoing.add(this.Links.get(i));
//			if(ni>=Input+Input){
//				ni = Input;
//				no+= 1;
//			}
//			
//		}
		
//		no = Input;
//		ni = Input*2;
//		
//		//Generate empty Hidden to Output Links
//		int layers = (int) Math.pow(Input,Hidden+2);
//		for(int i = layers;i<(layers)+Input*Output;i++){
//		    
//			this.Links.add(new Gene());
//			//From is i
//			this.Links.get(i).out = no; 
//			//To is j+Inputsize-1
//			this.Links.get(i).into = ni;
//			this.Neurons[ni].incoming.add(this.Links.get(i));
//			no++;
//			if(no >= this.Input*2){
//				no = Input;
//				ni++;
//			}
//			if(ni >= Input*2+Input*3){
//				ni = Input*2;
//			}
//			}
	
//		System.out.println("Neurons" + Neurons.length+" Links "+this.Links.size());
//		String s = "";
//		for(int i=0;i<3;i++){
//			s+= this.neuronPath(Neurons[i], i)+"\\n";
//		}
//		System.out.println(s);
//		Collections.sort(Links);
		

	}
	
	public String neuronPath(Neuron n,int i){
		String s = "";
while(n.getOutgoing().size() != 0){
	   s+= "From"+ i +" to "+n.getOutgoing().get(0).getInto();
	   i = n.getOutgoing().get(0).getInto();
	   n = Neurons[n.getOutgoing().get(0).getInto()];
	  
}
	   return s;
		
	}
	
	public long topFit(){
		return this.pool.getTopfitness();
	}
	
	public void generateweigths(Genome CPPN){
		//Genome CPPN = this.pool.Species.get(this.pool.currentSpecies-1).Genomes.get(this.pool.currentGenome-1);
		CPPN.generateNetwork();
	//	int i = 0;
		for(Neuron n:Neurons){
			n.setValue(0.0); 
		}
		boolean Empty = true;
		int i = 0;
		for(Gene g:Links){
			i++;
			double[] Inputs = new double[4];
					Inputs[0] = (g.getInto()%x);
					Inputs[1] = (g.getInto()%x)*y;
					Inputs[2] = (g.getOut()%x);
					Inputs[3] = (g.getOut()%x)*y;
				
//System.out.println(Inputs[0]+" " +Inputs[1]+" " +Inputs[2]+" " +Inputs[3] );
				if(i>=Input*Input){
					double factor = 1e5; // = 1 * 10^5 = 100000.
					g.setWeigth((this.activition(11,CPPN.step(Inputs,0)[0])));
				//	g.setWeigth((((g.getWeigth() * factor)) / factor));
				}
				else{
					double factor = 1e5; // = 1 * 10^5 = 100000.		
					g.setWeigth((this.activition(11,CPPN.step(Inputs,0)[0])));//g.setWeigth((CPPN.step(Inputs,0)[0]));
				//	g.setWeigth((g.getWeigth() * factor)/ factor);
				}
				if(g.getWeigth()!=0){
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
			CPPN.setFitness(-9999);
//			this.pool.nextGenome();
//			this.pool.Species.get(pool.currentSpecies-1).Genomes.get(pool.currentGenome-1).generateNetwork();
//			this.generateweigths(this.pool.Species.get(pool.currentSpecies-1).Genomes.get(pool.currentGenome-1));
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
		for(Neuron n:Neurons){
			n.setValue(0.0); 
		}
		for(int i =0;i<Inputs.length;i++){
			this.Neurons[i].setValue(Inputs[i]);
//			test+=i+" ";
		}

		//Just 1 layer
		//From Input to first Layer
		int sum = 0;
		for(int i = 0;i<Input;i++){
			Neuron n = this.Neurons[i];
			if(n.getValue() != 0.0){
				for(Gene g:n.getOutgoing()){
					if(g.getWeigth()!=0){
						Neurons[g.getInto()].setValue(Neurons[g.getInto()].getValue()+g.getWeigth()*Neurons[g.getOut()].getValue());//this.activition(11,n.value*g.weigth+Neurons[g.into].value);
					//	Neurons[g.into].value += n.value*g.weigth;
					}
				}
				
			}
		}
//		//Fixing 1 Layer Output
//		for(int i = Input;i<Input*2;i++){
//			double d = this.activition(10,Neurons[i].getValue());
//			Neurons[i].setValue(d);
//		}
		
		//From 1 Layer to Output
		for(int i = Input;i<Input*2;i++){
			Neuron n = this.Neurons[i];
			if(n.getValue() != 0.0){
				for(Gene g:n.getOutgoing()){
					if(g.getWeigth()!=0){
						Neurons[g.getInto()].setValue(Neurons[g.getInto()].getValue()+g.getWeigth()*Neurons[g.getOut()].getValue());
						//Neurons[g.into].value += n.value*g.weigth;
					//	Neurons[g.into].value += this.activition(11,n.value*g.weigth+Neurons[g.into].value);
					}
				}
			}
		}
		//Fixing Output
		for(int i = Input*2;i<Neurons.length;i++){
			double d = this.activition(0,Neurons[i].getValue());
			Neurons[i].setValue(d);
		}
		
		double[] Output = new double[this.Output];
		int j = 0;
//		String s = "Outputs: ";
		for(int i=Input*2;i<this.Neurons.length;i++){
Output[j++] = this.activition(12,Neurons[i].getValue());

//s+= " "+Output[j]+"";
//			j++;
			
		}
//System.out.println(s);
		return Output;
	}
	
	private double getValues(int n,int act){
		double d = 0;
		double f = 1e5;
//		int i = 0;
		for(Gene g:Neurons[n].getIncoming()){
//			if(i<=50){
//			 d= ((d+g.getWeigth()*Neurons[g.getOut()].getValue()));
//			 i++;
//			}
//			else{
			if(g.getWeigth()!=0||Neurons[g.getOut()].getValue()!=0.0){
			d+=(g.getWeigth()*Neurons[g.getOut()].getValue()*f)/f;
			}
//			i=0;
//			}
		}
		
		return this.activition(act, d);
	}
	
public double[] stepold(double[] Inputs){
		
		//Inputs im Netzwerk setzen
		int z=0;
		for(double i:Inputs){
			this.Neurons[z].setValue(i);
			z++;
		}
//		if(this.Neurons.length > Output+this.Input){
//			//this.Neurons.get(this.Input+Output).value = 1.0;
//		}
		
		for(int i = this.Input;i<this.Neurons.length;i++){
//			Neuron n = this.Neurons[i];
//			double sum =0;
//			for(Gene g:n.getIncoming()){
//				if(Neurons[g.getOut()].getValue() != 0.0&&g.getWeigth() !=0.0){
				
				Neurons[i].setValue(this.getValues(i, 12));//this.activition(12,g.weigth * this.Neurons[g.out].value+n.value);
				//System.out.println("LOL: "+sum);
//				}
//				
//			}

//			if(n.incoming.size() >1){
//		//		n.value = (2/(1+Math.exp(-4.9*sum))-1);
//			}
		}
		
		double[] Output = new double[this.Output];
		int j = 0;
//		String s = "Outputs: ";
		for(int i=this.Neurons.length-this.Output;i<this.Neurons.length;i++){
			Output[j++] = this.Neurons[i].getValue();//this.activition(12,this.Neurons[i].getValue());
			
//			s+= " "+Output[j];
			
		}
	//	System.out.println(s);
		return Output;

		
		
	}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + Hidden;
	result = prime * result + Input;
	result = prime * result + ((Links == null) ? 0 : Links.hashCode());
	result = prime * result + Arrays.hashCode(Neurons);
	result = prime * result + Output;
	result = prime * result + ((pool == null) ? 0 : pool.hashCode());
	result = prime * result + x;
	result = prime * result + y;
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	HyperNeat other = (HyperNeat) obj;
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
	if (x != other.x)
		return false;
	if (y != other.y)
		return false;
	return true;
}

public int getInput() {
	return Input;
}

public void setInput(int input) {
	Input = input;
}

public int getOutput() {
	return Output;
}

public void setOutput(int output) {
	Output = output;
}

public int getHidden() {
	return Hidden;
}

public void setHidden(int hidden) {
	Hidden = hidden;
}

public int getX() {
	return x;
}

public void setX(int x) {
	this.x = x;
}

public int getY() {
	return y;
}

public void setY(int y) {
	this.y = y;
}

public Neuron[] getNeurons() {
	return Neurons;
}

public void setNeurons(Neuron[] neurons) {
	Neurons = neurons;
}

public ArrayList<Gene> getLinks() {
	return Links;
}

public void setLinks(ArrayList<Gene> links) {
	Links = links;
}

public Pool getPool() {
	return pool;
}

public void setPool(Pool pool) {
	this.pool = pool;
}



	
}
