package ki;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class KI implements java.io.Serializable, Comparable {
	static final long serialVersionUID = 1;
	public ArrayList<Node> Nodes;	
	public ArrayList<Link> Links;
	public KI father;
	public KI mother;
	public int fitness;
	public boolean isTesting;
	public int generation;
	public int gen;
	public int in;
	public int out;
	public int hid;
	public int hidlayer;
	
	public KI(int in, int out, int hid,int hidlayer){
		isTesting = false;
		this.hid = hid;
		this.hidlayer = hidlayer;
		this.in = in;
		this.out = out;
		this.Nodes = new ArrayList<Node>();
		Links = new ArrayList<Link>();
		father = null;
		mother = null;
		fitness = -99999;
		for(int i = 0;i<(in+out+hid*hidlayer);i++){
		this.Nodes.add(new Node(0));
		
		}
		
		generation =0;
		////System.out.println("Anzahl Nodes: "+this.Nodes.size());
		this.mutate(4);
	}
	
	public KI copy(){
		KI ki = new KI(this.in,this.out,this.hid,this.hidlayer);
		ki.fitness = this.fitness;
		ki.isTesting = this.isTesting;
		ki.generation = this.generation;
		for(Node n : Nodes){
			Node no = new Node(n.getValue());
			ki.Nodes.add(no);
		}
		for(Link l:this.Links){
			Link li = new Link(l.getFrom(),l.getTo(),l.getValue());
			ki.Links.add(li);
		}
		return ki;
	}
	
	public KI(int j,int in, int out, int hid,int hidlayer){
	//	isTesting = false;
		this.in = in;
		this.out = out;
		this.hid = hid;
		this.hidlayer = hidlayer;
		this.Nodes = new ArrayList<Node>();
		Links = new ArrayList<Link>();
		father = null;
		mother = null;
		//fitness = -1;

		//System.out.println("Anzahl Nodes: "+this.Nodes.size());
//		this.mutate();
	}
	
	public int getGeneration(int i){
		if(this.father != null && this.mother != null){
		if(this.father.getGeneration(i+1)>this.mother.getGeneration(i+1)){
			return this.father.getGeneration(i+1);
		}
		else{
			return this.mother.getGeneration(i+1);
		}
		}
		return i;
	}
	//"/tmp/employee.ser"
	public void save(String s,int i){
		 try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream(s);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      }catch(IOException e)
	      {
	          e.printStackTrace();
	          this.save("/tmp/employee"+i+1+".ser",i+1);
	      }
	   }
	
	//public void load()
	
	@SuppressWarnings("unchecked")
	public void mutate(int prob){
		Random random = new Random();
		int max = prob;
		int min = 0;
		int choice = 0;
		choice = random.nextInt(max - min + 1) + min;
//		//Switch Linking (Input=> Output to Input=>Hidden=>Output)
//		if(choice ==4 || choice ==5){
//			if(!this.Links.isEmpty()){
//				Link change = this.Links.get(this.getrandomLinkid());
//				
//				//If  NO Hidden
//				if(change.getFrom() <in+out &&change.getTo() <in){
//				Link ne = new Link(in+out+random.nextInt(hid),change.getTo(),(random.nextInt(3)-1)*random.nextDouble());
//				this.Links.add(ne);
//				change.setTo(ne.getFrom());
//			}
//				else{
//					choice = random.nextInt(3);
//					if(choice == 1){
//					//Hiddencomb to single link
//					//FROM Hidden TO Output
//					Link l = this.Links.get(this.getrandomLinkid());
//					int tries = 0;
//					if(change.getFrom() > in+out-1){
//						while(l.getTo()<=in+out-1||l.getFrom() != change.getTo()){
//								l = this.Links.get(this.getrandomLinkid());
//								if(tries >=500){
//								break;
//								}
//									tries++;	
//							};
//							if(tries <500){
//						change.setFrom(l.getFrom());
//						this.Links.remove(l);
//					//	this.Links.trimToSize();
//							}
//					
//					}
//					if(change.getTo() > in+out-1){
//						tries = 0;
//						while(l.getFrom()<=out-1||l.getTo() != change.getFrom()){
//								l = this.Links.get(this.getrandomLinkid());
//								tries++;
//								if(tries >= 500){
//									break;
//								}
//							};
//							if(tries < 500){
//						change.setFrom(l.getFrom());
//						this.Links.remove(l);
//				//		this.Links.trimToSize();
//							}
//					}
//				}
//			}
//			}
//		}
		//ChangeLink
			if(choice == 0 || choice == 6){
				if(!this.Links.isEmpty()){
				Link changing = this.Links.get(this.getrandomLinkid());
				choice = random.nextInt(2+ 1);
				//Gewichtung
				if(choice == 0){
					Random randomx = new Random();
					 double r = 0; 
					 int i= randomx.nextInt(3);
					 i-=1;
					 r = i*randomx.nextDouble();
					changing.setValue(r);
				}
				//Ändern des from
				if(choice == 1){
					//Wenn To ein Output ist, muss from ein Hid l2 sein
					if(changing.getTo() < out){
						choice = random.nextInt(hid);
						choice += in+out+hid;//*(hidlayer-1);
					}
					//Wenn To ein Hiddenl2 ist muss From ein Hiddenl1 sein
					if(changing.getTo() >=in+out+hid && changing.getTo() < out+in+hid*2){
						choice = random.nextInt(hid);
						choice += in+out;
					}
					//Wenn To Ein Hiddenl1 ist muss from ein Input sein
					if(changing.getTo() >=out+in && changing.getTo() < in+out+hid){
						choice = random.nextInt(in);
						choice += out;
					}
					changing.setFrom(choice);
//					choice = random.nextInt(in+out+hid-out);
//					choice += out;
//					changing.setFrom(choice);
//					// Wenn From von Input zu Hidden wechselt muss to von Hidden zu Input wechseln!
//					if(choice > out+in-1 && changing.getTo() >out-1){
//						choice = random.nextInt(out);
//						changing.setTo(choice);
//					}
			}
				//Ändern des to
				if(choice == 2){
//					if(changing.getFrom()<this.Nodes.size()){
//					choice = random.nextInt(out);
//					changing.setTo(choice);
//					}
//					else{
//						choice = random.nextInt(out+hid);//25
//						if(choice >= out){
//							choice = choice-out+out+in-1;
//							changing.setTo(choice);
//						}
//					}
					//Wenn From ein Hiddenl1 ist muss to ein Hiddenl2 sein
					if(changing.getFrom() >=in+out && changing.getFrom() < out+in+hid){
						choice = random.nextInt(hid);
						choice += in+out+hid;
					}
					//Wenn From Ein hiddenl2 ist muss To ein output sein
					if(changing.getFrom() >=out+in+hid){
						choice = random.nextInt(out);
						//choice += out;
					}
					//Wenn From Ein Input ist muss to ein Hiddenl1 sein
					if(changing.getFrom() >=out&&changing.getFrom()<in+out){
						choice = random.nextInt(hid);
						choice += out+in;
					}
					changing.setTo(choice);
				}
				}
			}
		//DeleteLink
			if(choice == 1){
				if(!this.Links.isEmpty()){
				this.Links.remove(this.getrandomLinkid());
			//	this.Links.trimToSize();
				}
			}
		//AddLink
			if(choice == 2 || choice == 7){
				int from =0;
				int to =0;
				//From darf kein Output sein
		//		while(from < out ){//|| from > 47 ){
					 from =random.nextInt(in+hid*hidlayer);
					 from += out;
					 

				//Wenn from Input ist muss to ein Hiddenl1
				if(from < out+in && from >=out){
					to =random.nextInt(hid);
					to += in+out;
				}
				//Wenn from Hiddenl1  ist ,muss to ein Hiddenl2
				if(from < out+in+hid && from >=out+in){
					to =random.nextInt(hid);
					to += in+out+hid;
				}
				//Wenn from ein Hiddenl2 ist muss to ein Output sein
				if(from >=out+in+hid){
					to =random.nextInt(out);
					//to += in+out+hid;
				}
//				//To darf nur Hidden oder Output sein
//				//From is HIDDEN
//				if(from >in+out-1){
//				while(to >out-1 ){//&&to <48 ){
//					
//					to = random.nextInt(out);
//				}
//				}
//				//From is Input
//				if(from <out+in && from >out-1){
//					while(to >out-1 && to<out+in){//&&to <48 ){
//						to =random.nextInt(this.Nodes.size());
//					}
//				}
				Random randomx = new Random();
				 double r = randomx.nextInt(3);
				 r= (randomx.nextInt(3)-1)*randomx.nextDouble();
				this.Links.add(new Link(from,to,r));
				
			}
			Collections.sort(this.Links);
			
		
	}
	
	
	public int step(){

		//Add nulify for Hiddensl1
		for(int i=this.out+this.in;i<in+out+hid;i++){
			this.Nodes.get(i).setValue(0.0);
		}
		
		//From Input to Hiddenl1
		int[] ith = new int[this.hid];
		for(Link l :this.Links){
			
			if(l.getTo() < hid+in+out && l.getTo() >= in+out){
			Node to = this.Nodes.get(l.getTo());
			Node from = this.Nodes.get(l.getFrom());
			to.setValue(from.getValue()*l.getValue()+to.getValue());
			ith[l.getTo()-this.out-this.in]++;
			}
			else{
				if(l.getTo()<in+out){
				break;
				}
			}
		}
		for(int i =0;i<ith.length;i++){
	if(ith[i]>0){
				
				double j = this.Nodes.get(i).getValue()/ith[i];
				//Binär
//				if(j > 0){
//					this.Nodes.get(i).setValue(1);	
//				}
//				else{
//					this.Nodes.get(i).setValue(0);
//				}
				//Linear
//				this.Nodes.get(i).setValue(j);
//				
				//Treshhold
//				this.Nodes.get(i).setValue(Math.round(j));

				//Sigmoid
				double a = 5.0;
				if(j <=-1){
					j = -1;
				}
				else{
					if(j >=1){
						j = 1;
					}
					else{
				j = 1.0/(1.0+Math.exp(-j*a));
				}
				}
				this.Nodes.get(i).setValue(j);
				
			}
		}
		
		//Add nulify for Hiddensl2
		for(int i=out+in+hid;i<in+out+hid*2;i++){
			this.Nodes.get(i).setValue(0.0);
		}
		
		//From Hiddenl1 to Hiddenl2
		int[] hth = new int[this.hid];
		for(Link l :this.Links){
			if(l.getTo() >= hid+in+out && l.getTo() < in+out+hid*2){
			Node to = this.Nodes.get(l.getTo());
			Node from = this.Nodes.get(l.getFrom());
			to.setValue(from.getValue()*l.getValue()+to.getValue());
			hth[l.getTo()-out-in-hid]++;
			}
			else{
				if(l.getTo()<in+out+hid){
				break;
				}
			}
		}
		//Nullify endnodes
		for(int i =0;i<out;i++){
			this.Nodes.get(i).setValue(0.0);
		}
		for(int i =0;i<hth.length;i++){
	if(hth[i]>0){
				
				double j = this.Nodes.get(i).getValue()/hth[i];
				//Binär
//				if(j > 0){
//					this.Nodes.get(i).setValue(1);	
//				}
//				else{
//					this.Nodes.get(i).setValue(0);
//				}
				//Linear
//				this.Nodes.get(i).setValue(j);
//				
				//Treshhold
//				this.Nodes.get(i).setValue(Math.round(j));

				//Sigmoid
				double a = 5.0;
				if(j <=-1){
					j = -1;
				}
				else{
					if(j >=1){
						j = 1;
					}
					else{
				j = 1.0/(1.0+Math.exp(-j*a));
				}
				}
				this.Nodes.get(i).setValue(j);
				
			}
		}
		
		//From Hidden to Output
		int[] hto = new int[this.out];
		for(Link l :this.Links){
			if(l.getTo() < out){
			Node to = this.Nodes.get(l.getTo());
			Node from = this.Nodes.get(l.getFrom());
			to.setValue(from.getValue()*l.getValue()+to.getValue());
			hto[l.getTo()]++;
			}

		}
		
		for(int i =0;i<hto.length;i++){
			if(hto[i]>0){
				
				double j = this.Nodes.get(i).getValue()/hto[i];
				//Binär
//				if(j > 0){
//					this.Nodes.get(i).setValue(1);	
//				}
//				else{
//					this.Nodes.get(i).setValue(0);
//				}
				//Linear
//				this.Nodes.get(i).setValue(j);
//				
				//Treshhold
//				this.Nodes.get(i).setValue(Math.round(j));
				
				//Sigmoid
				double a = 5.0;
				if(j <=-1){
					j = -1;
				}
				else{
					if(j >=1){
						j = 1;
					}
					else{
				j = 1.0/(1.0+Math.exp(-j*a));
				}
				}
				this.Nodes.get(i).setValue(j);
			}
		}
		
//	for(Link l :this.Links){
//		Node to = this.Nodes.get(l.getTo());
//		Node from = this.Nodes.get(l.getFrom());
//		to.setValue(from.getValue()*l.getValue()+to.getValue());
//	}
//	String s = "Values Nodes: ";
//	for(Node n:this.Nodes){
//		s+= " "+n.getValue();
//	}
//	System.out.println(s);
		double max = 0.0;
		int curmax= 0;
	//	String nodes = "";
		for(int i =out-1;i>=0;i--){
				max = Math.round(this.Nodes.get(i).getValue());
				if(((int)((double)curmax + max*Math.pow((double)2, (double)i)))>=0.0 ){
					curmax = (int)((double)curmax + max*Math.pow((double)2, (double)i));
				}
	
			//	System.out.println(curmax);
			
		//	nodes += " "+this.Nodes.get(i).getValue();
		}
		//System.out.println("Step Calculation: "+nodes+" Step: "+ Math.round(curmax));
		
		return Math.round(curmax);
	}
	
	public KI offspring(KI father){
		KI child = new KI(this.in,this.out,this.hid,this.hidlayer);
		int linksize = 0;
		//Vererbung
		if(this.Links.size() > father.Links.size()){
			linksize = this.Links.size();
		}
		else{
			linksize = father.Links.size();
		}
		
		for(int i = 0;i<linksize;i++){
			int random = (int)Math.round(Math.random());
			if(random == 0){
				if(father.Links.size() >i){
				if(father.Links.get(i) != null){
					int from = father.Links.get(i).getFrom();
					int to   = father.Links.get(i).getTo();
					if(from >-1 && to > -1){
				//		if(from <  in+out && to < in+out){
							child.Links.add(new Link(from,to,father.Links.get(i).getValue()));
							
					//	}
						//TODO:Add method for Hidden links
					}
				}
				}
				
			}
			else{
				if(this.Links == null){
					this.Links = new ArrayList<Link>();
				}
				if(this.Links.size() > i){
				if(this.Links.get(i) != null){
					int from = this.Links.get(i).getFrom();
					int to   = this.Links.get(i).getTo();
					if(from >-1 && to > -1){
					//	if(from <  in+out && to < in+out){
							child.Links.add(new Link(from,to,this.Links.get(i).getValue()));
							
					//	}
						//TODO:Add method for Hidden links
					}
				}
				}
				
			}
			child.mutate(30);
		}
		
		//Mutation
		child.mutate(5);
		//child.mutate();
		//Deletation of doublelinks
		child.eliminatedoublelinks();
		return child;
	}
	
	public int getrandomLinkid(){
		int max = this.Links.size()-1;
		int min = 0;
		if(this.Links.isEmpty()){
			return -1;
		}
		Random random = new Random();
		return  random.nextInt(max - min + 1) + min;
	}
	
	public int getrandomNodeid(){
		int max = this.Nodes.size();
		//int min = 0;
		Random random = new Random();

		return  random.nextInt(max);
	}
	
	private void eliminatedoublelinks(){
		int error =0;
		for(int i=0;i<this.Links.size();i++){
			Link z = this.Links.get(i);
			for(int j=0;j<this.Links.size();j++){
				Link l = this.Links.get(j);
				if(z != l){
				if(z.getFrom() == l.getFrom()){
					if(z.getTo() == l.getTo()){
					//	if(this.Nodes.indexOf(z.getValue()) == this.Nodes.indexOf(l.getValue())){
							z.setValue(l.getValue()*z.getValue());
							this.Links.remove(l);
					//		this.Links.trimToSize();
							error = 1;
							break;
					//	}
					}
				}
				}
			}
				if(error > 0){
					this.eliminatedoublelinks();
					break;
				}
				}
					
				}
			
		
	
	
	public KI(FileInputStream fileIn){
	  KI e = null;
      try
      {
        // FileInputStream fileIn = new FileInputStream("/tmp/employee.ser");
         ObjectInputStream in = new ObjectInputStream(fileIn);
         try {
			e = (KI) in.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         in.close();
         fileIn.close();
      }catch(IOException i)
      {
         i.printStackTrace();
	
      }
	}
	

	public int compareTo(KI comparestu){
		if(((KI)comparestu).gen == this.gen){
		int compare =((KI)comparestu).fitness+((KI)comparestu).generation ;
		return  compare-this.fitness-this.generation;
		}
		if(((KI)comparestu).gen > this.gen){
			int compare =((KI)comparestu).fitness+((KI)comparestu).generation ;
			return  compare-this.fitness-this.generation;
		}
		else{
			int compare =((KI)comparestu).fitness+((KI)comparestu).generation ;
			return  compare-this.fitness-this.generation;
		}
	}

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
	//	int compare =((KI)o).fitness+((KI)o).generation ; 
		if(((KI)o).gen == this.gen){
				int comp = ( ((KI)o).fitness-this.fitness )*10000;
					comp += ((KI)o).Links.size() -this.Links.size();
					if(comp == 0){
						comp += ((KI)o).generation-this.generation;
					}
				return comp;
		}
		if(((KI)o).gen > this.gen){
			int comp = ( ((KI)o).fitness-this.fitness )*10000;
				comp += ((KI)o).generation+9999-this.generation;
			return comp;
	}
		else{
			int comp = ( ((KI)o).fitness-this.fitness )*10000;
				comp += ((KI)o).generation-this.generation-9999;
			return comp;
	}
	}
	

}
