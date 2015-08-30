package ki;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class KICluster implements java.io.Serializable{
	//LRM Sortiert Vater Mutter Kind
//	public ArrayList<KI> stammbaum;
	public ArrayList<KI> ranking;
	public KI current;
	public int in;
	public int out;
	public int hid;
	public int hidlayer;
	
	public void save(String s,int i){
		 try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream(s);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         //writing KI's
	         out.writeInt(this.ranking.size());
	         out.writeInt(this.in);
	         out.writeInt(this.out);
	         out.writeInt(this.hid);
	         out.writeInt(this.hidlayer);
	         for(KI k:this.ranking){
	        // out.writeObject(k);
	        	 out.writeInt(k.fitness);
	        	 out.writeInt(k.generation);
	        	 out.writeBoolean(k.isTesting);
	        	 out.writeInt(k.Links.size());
	        	 out.writeInt(k.Nodes.size());
	        	 for(Node n:k.Nodes){
	        		 out.writeObject(n);
	        	 }
	        	 for(Link l:k.Links){
	        		 out.writeObject(l);
	        		 
	        	 }

	         }
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in /tmp/GLaDoS.ki");
	      }catch(IOException e)
	      {
	          e.printStackTrace();
	          this.save("/tmp/employee"+i+1+".ser",i+1);
	      }
	   }
	
	public void retest(){
		for(KI k: this.ranking){
			if(k.isTesting == true && k.fitness == -99999){
				
			}
			else{
			k.isTesting = false;
			k.fitness = 0;
			}
		}
	}

	public void reevaluate(){
		for(KI k: this.ranking){
			if((k.isTesting == true && k.fitness == -99999) ||k.Links.size() <=0){
				k.fitness = -99999;
			}
			else{
				k.fitness = 0;
			k.isTesting = false;
			}
		}
		
	}
	

	
	public static KICluster load(String s){
		  KICluster e = new KICluster(1);
	      try
	      {
	         FileInputStream fileIn = new FileInputStream(s);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         int i = in.readInt();
	         int input = in.readInt();
	         int output= in.readInt();
	         int hidden= in.readInt();
	         int hiddenl= in.readInt();
	         e.in = input;
	         e.out = output;
	         e.hid = hidden;
	         e.hidlayer = hiddenl;
	         for(int j = 0;j<i;j++){
	        	 KI k = new KI(1,input,output,hidden,hiddenl);
	        	 k.fitness=in.readInt();
	        	 k.generation=in.readInt();
	        	 k.isTesting=in.readBoolean();
	        	 k.in = input;
	        	 k.out = output;
	        	 k.hid = hidden;
	        	 k.hidlayer = hiddenl;
	        	 int z = in.readInt();
	        	 int x = in.readInt();
	        	 for(int d=0;d<x;d++){
	        		 k.Nodes.add((Node)in.readObject());
	        	 }
	        	 for(int a=0;a<z;a++){
	        		 k.Links.add((Link)in.readObject());
	        	 }
	        	e.ranking.add(k); 
	         }
	         in.close();
	         fileIn.close();
	         return e;
	      }catch(IOException i)
	      {
	    	  return new KICluster(42,3,8,1);

	      }catch(ClassNotFoundException c)
	      {
	    	  return new KICluster(42,3,8,1);
	      }
		
	}
	
	
	public KICluster(int in, int out, int hid, int hidlayer){
	//	stammbaum = new ArrayList<KI>();
		ranking = new ArrayList<KI>();
		this.in = in;
		this.out = out;
		this.hid = hid;
		this.hidlayer = hidlayer;
		for(int i = 0;i<50;i++){
		ranking.add(new KI(in, out, hid,hidlayer));
		}
	}
	public KICluster(int i){
		ranking = new ArrayList<KI>();
	}
	
	public KI best(){
		int i = Integer.MIN_VALUE;
		KI ki = null;
		for(KI k :this.ranking){
			if(i < k.fitness){
			i = k.fitness;
			ki = k;
			}
		}
		return ki;
	}
	@SuppressWarnings("unchecked")
	public void rank(){
		Collections.sort(ranking);
	}
	
	public void addKI(KI add){
		ranking.add(add);
		this.rank();
	}
	
	public int tournamentnext(int i){
		if(i >= this.ranking.size()){
			return 500;
		}
		if(ranking.get(i).Links.size() > 0){
			return i;
		}
		else{
			return tournamentnext(++i);
		}
	}
	
	public int next(){
		for(KI i:this.ranking){
			if(!i.isTesting){
				if(i.Links.size() >0){
				return this.ranking.indexOf(i);
				}
				else{
					i.isTesting = true;
					i.fitness = -99999;
					//this.rank();
				}
			}
		}
		this.rank();
		//Selection
		this.selection();
		
		//Breeding
		int max = (this.ranking.size()-1)/2;
		int min = 0;
		Random random = new Random();
		int old = this.ranking.size();
		//return  
		for(int i = 50-this.ranking.size();i>0;i--){
			int v = random.nextInt(max - min + 1) + min;
			int m = random.nextInt(max - min + 1) + min;
			while(m == v){
				m = random.nextInt(max - min + 1) + min;
			}
			KI temp = this.ranking.get(m).offspring(this.ranking.get(v));
			temp.father = this.ranking.get(v);
			temp.mother = this.ranking.get(m);
		this.ranking.add(temp);
		if (temp.father.generation > temp.mother.generation){
			temp.generation = temp.father.generation+1;
		}
		else{
			temp.generation = temp.mother.generation+1;
		}
		if (temp.father.gen > temp.mother.gen){
			temp.gen = temp.father.gen;
			temp.generation = temp.father.generation+1;
		}
		else{
			temp.gen = temp.mother.gen;
			temp.generation = temp.mother.generation+1;
		}
		if(temp.generation > 9999){
			temp.generation = 0;
			temp.gen = temp.gen +1;
		}
		//this.ranking.add(temp);
		}
		
		int size = this.ranking.size();
		System.out.println("Size increased from: " + old +" to: "+size);
		this.reevaluate();
		return this.next();
		}
	
	private void selection(){
		int size =this.ranking.size();
		int old = size;
		size = size /3;
		
		
		for(int i = this.ranking.size()-1;i>size;i--){
			this.ranking.remove(i);
		//	this.ranking.trimToSize();
		}
		System.out.println("Size trimmed from: " + old +" to: "+size);
	}
	
	

}
