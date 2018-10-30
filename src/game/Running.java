package game;
import hyperneat.HyperNeat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import neat.Genome;
import neat.Pool;
import neat.Species;
import ki.KICluster;


public class Running implements Runnable{
Game game;
int i;
KICluster test;
gameDisplay dis;
Pool p;
Pool p2;
int minfit;
long time;
int loadedtype1;
int loadedtype2;
HyperNeat hyper1;
HyperNeat hyper2;


	public Running(Game g, int i,KICluster k,Pool p,Pool p2,int a, int b,gameDisplay dis, int minfit,HyperNeat hyper1,HyperNeat hyper2){
		this.game = g;
		this.i = i;
		test = k;
		this.dis = dis;
		this.p = p;
		this.p2= p2;
		this.minfit = minfit;
		this.loadedtype2 = b;
		this.hyper1 = hyper1;
		this.hyper2 = hyper2;
	}
	public boolean dowhile(int currentround,int i){
		float tempfit =Float.MIN_VALUE;
		if(game.a.Playertype==0&&(game.a.ki.fitness > tempfit)){
			tempfit = game.a.ki.fitness;
		}
		if(game.b.Playertype ==0&&(game.b.ki.fitness > tempfit)){
			tempfit = game.b.ki.fitness;
		}
		if(game.a.Playertype == 3&&game.a.pool.Species.get(game.a.pool.currentSpecies-1).Genomes.get(game.a.pool.currentGenome-1).getFitness()>tempfit){
			tempfit = game.a.pool.Species.get(game.a.pool.currentSpecies-1).Genomes.get(game.a.pool.currentGenome-1).getFitness();
		}
		if(game.b.Playertype == 3&&game.b.pool.Species.get(game.b.pool.currentSpecies-1).Genomes.get(game.b.pool.currentGenome-1).getFitness()>tempfit){
			tempfit = game.b.pool.Species.get(game.b.pool.currentSpecies-1).Genomes.get(game.b.pool.currentGenome-1).getFitness();
		}

		if((((currentround<(i))||i ==0))&&(minfit==0||tempfit<= minfit)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void run() {
		test.retest();
		//Write initvalues to text
		try {
			String path = "C:/tmp/";
			String path2= "C:/tmp/";
			long time2 = System.currentTimeMillis();
			String content = "";
			File file = null;
			if(game.a.Playertype == 3){
			content = "Playera: "+"NEAT "+" Playerb Strength: "+game.b.Playerstrength+" Playerb Type: "+game.b.Playertype+" Started at: "+time2+ " ms"+" for "+this.i+" Rounds "+" minfit: "+this.minfit+ "\n";
			path += "Neat/Playera.txt";
			file = new File(path);
			//System.out.println("Done");
			}
			if(game.b.Playertype == 3){
				content = "Playerb: "+"NEAT "+" Playera Strength: "+game.a.Playerstrength+" Playera Type: "+game.a.Playertype+" Started at: "+time2+ " ms"+" for "+this.i+" Rounds "+" minfit: "+this.minfit+ "\n";
			path2 += "Neat/Playerb.txt";
			file = new File(path2);
			}
			if(game.b.Playertype == 4){
				content = "Playerb: "+"NEAT "+" Playera Strength: "+game.a.Playerstrength+" Playera Type: "+game.a.Playertype+" Started at: "+time2+ " ms"+" for "+this.i+" Rounds "+" minfit: "+this.minfit+ "\n";
			path2 += "Hyper/Playerb.txt";
			file = new File(path2);
			}
			if(game.a.Playertype == 4){
			content = "Playera: "+"NEAT "+" Playerb Strength: "+game.b.Playerstrength+" Playerb Type: "+game.b.Playertype+" Started at: "+time2+ " ms"+" for "+this.i+" Rounds "+" minfit: "+this.minfit+ "\n";
			path += "Hyper/Playera.txt";
			file = new File(path);
			//System.out.println("Done");
			}
			
			if(game.a.Playertype == 0){
				content = "Playera: "+"KI Cluster "+" Playerb Strength: "+game.b.Playerstrength+" Playerb Type: "+game.b.Playertype+" Started at: "+time2+ " ms"+" for "+this.i+" Rounds "+" minfit: "+this.minfit+ "\n";
			path += "Normal/Playera.txt";
			file = new File(path);
			}
			
			if(game.b.Playertype == 0){
				content = "Playerb: "+"KI Cluster "+" Playera Strength: "+game.a.Playerstrength+" Playera Type: "+game.a.Playertype+" Started at: "+time2+ " ms"+" for "+this.i+" Rounds "+" minfit: "+this.minfit+ "\n";
				path2 += "Normal/Playerb.txt";
				file = new File(path2);
				}
			
			// if file doesnt exists, then create it
			if (file != null&&!file.exists()&&(game.a.Playertype >2 ||game.b.Playertype > 2)) {
//				new File(path).mkdir();
//				new File(path2).mkdir();
			//	System.out.println("asdf"+file.getAbsolutePath()+"asd");
				file.createNewFile();
			}
			if(file != null){
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.newLine();
			bw.write(content);
			bw.newLine();
			bw.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		this.time = System.currentTimeMillis();
		try{
			int currentki=0;
			int maxruns = 10;	
			int j = 0;
		do{
			
			if(Thread.interrupted()){
				throw new InterruptedException();
			}
		//	game.a.Playertype = 3;
		//Wenn nur eine KI Spielt 
		if(game.a.Playertype ==0&&game.b.Playertype != 0){
			game.a.ki = test.ranking.get(test.next());
			game.a.ki.isTesting = true;
			if(dis != null){
			dis.drawPanel.ki = game.b.ki;
			}
		}
		if(game.b.Playertype ==0&&game.a.Playertype != 0){
			game.b.ki = test.ranking.get(test.next());
			game.b.ki.isTesting = true;
			if(dis != null){
			dis.drawPanel.ki = game.b.ki;
			}
			//Wenn 2 KIs spielen 
		}
		
			if(game.a.Playertype == 0 && game.b.Playertype ==0){
		    currentki=0;
			game.a.ki = test.ranking.get(test.next());
			game.a.ki.isTesting = true;
			maxruns = test.ranking.size();
			dis.drawPanel.ki = game.a.ki;
			
			}
			
			if(game.a.Playertype == 3){
				game.a.ki = null;
				game.a.pool = p;
				game.a.pool.currentSpecies = 1;
				game.a.pool.currentGenome = 1;
			//	System.out.println("MES"+game.a.pool.alreadyMeasured()+game.a.pool.Species.get(game.a.pool.currentSpecies-1).Genomes.get(game.a.pool.currentGenome-1).fitness);
				while(game.a.pool.alreadyMeasured()){
				//	System.out.println("MES");
					game.a.pool.nextGenome();
				}
				dis.drawPanel.ki = null;
				dis.drawPanel.update = true;
				game.a.pool.Species.get(game.a.pool.currentSpecies-1).Genomes.get(game.a.pool.currentGenome-1).generateNetwork();
				dis.drawPanel.g = game.a.pool.Species.get(game.a.pool.currentSpecies-1).Genomes.get(game.a.pool.currentGenome-1);
				dis.drawPanel.generation = game.a.pool.generation;
				dis.drawPanel.update = false;
				
			}
			if(game.b.Playertype == 3){
				game.b.ki = null;
				game.b.pool = p2;
				game.b.pool.currentSpecies = 1;
				game.b.pool.currentGenome = 1;
				while(game.b.pool.alreadyMeasured()){
					
					game.b.pool.nextGenome();
				}
				dis.drawPanel.ki = null;
				game.b.pool.Species.get(game.b.pool.currentSpecies-1).Genomes.get(game.b.pool.currentGenome-1).generateNetwork();
				dis.drawPanel.update = true;
				dis.drawPanel.g = game.b.pool.Species.get(game.b.pool.currentSpecies-1).Genomes.get(game.b.pool.currentGenome-1);
				dis.drawPanel.generation = game.b.pool.generation;
				dis.drawPanel.update = false;

				
				
			}
			if(game.a.Playertype == 4){
				game.a.ki = null;
				game.a.pool = hyper1.getPool();
				game.a.h = hyper1;
			//	dis = null;

				game.a.pool.currentGenome = 1;
				game.a.pool.currentSpecies = 1;
				while(game.a.pool.alreadyMeasured()){
					game.a.pool.nextGenome();
				}
				
				dis.drawPanel.update = true;
				dis.drawPanel.g = game.a.pool.Species.get(game.a.pool.currentSpecies-1).Genomes.get(game.a.pool.currentGenome-1);
				dis.drawPanel.generation = game.a.pool.generation;
				dis.drawPanel.update = false;
				
				Genome current = game.a.pool.Species.get(game.a.pool.currentSpecies-1).Genomes.get(game.a.pool.currentGenome-1);
				current.generateNetwork();
				game.a.h.generateweigths(current);
				
				if(p.alreadyMeasured()){
				while(game.a.pool.alreadyMeasured()){
					game.a.pool.nextGenome();
					current = game.a.pool.Species.get(game.a.pool.currentSpecies-1).Genomes.get(game.a.pool.currentGenome-1);
					current.generateNetwork();
					game.a.h.generateweigths(current);
				}
				}
			}
			
			if(game.b.Playertype == 4){
				game.b.ki = null;
				game.b.pool = hyper2.getPool();
				game.b.h = hyper2;
				//dis = null;
				game.b.pool.currentGenome = 1;
				game.b.pool.currentSpecies = 1;
				while(game.b.pool.alreadyMeasured()){
					game.b.pool.nextGenome();
				}
				Genome current = game.b.pool.Species.get(game.b.pool.currentSpecies-1).Genomes.get(game.b.pool.currentGenome-1);
				current.generateNetwork();
				game.b.h.generateweigths(current);
				
				dis.drawPanel.update = true;
				dis.drawPanel.g = game.b.pool.Species.get(game.b.pool.currentSpecies-1).Genomes.get(game.b.pool.currentGenome-1);
				dis.drawPanel.generation = game.b.pool.generation;
				dis.drawPanel.update = false;
				
				
				
				if(game.b.pool.alreadyMeasured()){
				while(game.b.pool.alreadyMeasured()){
					game.b.pool.nextGenome();
					current = game.b.pool.Species.get(game.b.pool.currentSpecies-1).Genomes.get(game.b.pool.currentGenome-1);
					current.generateNetwork();
					game.b.h.generateweigths(current);
				}
				}
			}
			
		
		
		for(int runs = 0;runs<maxruns;runs++){
			if(game.a.Playertype == 0 && game.b.Playertype == 0){
			currentki = test.tournamentnext(currentki);
			if(currentki >= 500){
				break;
			}
			game.b.ki = test.ranking.get(currentki++);
			}
			if(game.a.ki != null){
			for(int i =0;i<game.a.ki.out+game.a.ki.in+game.a.ki.hid;i++){
				game.a.ki.Nodes.get(i).setValue(0.0);
			}
		}
			if(game.b.ki != null){
			for(int i =0;i<game.b.ki.out+game.b.ki.in+game.b.ki.hid;i++){
				game.b.ki.Nodes.get(i).setValue(0.0);
			}
		}
			
	
		game.run(j,dis,runs);
		String data = "";
		if(this.hyper1 != null){
			data += hyper1.getPool().generation+" "+(hyper1.getPool().currentSpecies-1)+"/"+hyper1.getPool().Species.size()+" "+hyper1.getPool().currentGenome+"/"+hyper1.getPool().Species.get(hyper1.getPool().currentSpecies-1).Genomes.size()+" "+hyper1.getPool().Species.size()+hyper1.getPool().Species.get(0).Genomes.get(0).getFitness()+" "+hyper1.getPool().Species.get(hyper1.getPool().currentSpecies-1).Genomes.get(hyper1.getPool().currentGenome-1).getFitness()+" "+this.p.maxFitness;
		}
		if(this.hyper2 != null){
		//	data += hyper2.pool.getbest().fitness+" "+hyper2.pool.Species.get(hyper2.pool.currentSpecies-1).Genomes.get(hyper2.pool.currentGenome-1).fitness;
		}
		System.out.println(data);
		game.reset();
		
		}
		
		j++;
		
		//Write result to text
//		try {
//			String path = "C:/tmp/";
//			String path2= "C:/tmp/";
//			long time2 = System.currentTimeMillis()-time;
//			String content = "";
//			File file = null;
//			if(game.a.Playertype == 3){
//			content = "maxFitness: "+p.getbest().fitness+" after "+j+" Rounds"+" in "+time2+ " ms \n";
//			path += "Neat/Playera.txt";
//			file = new File(path);
//			//System.out.println("Done");
//			}
//			if(game.b.Playertype == 3){
//			content = "maxFitness: "+p2.getbest().fitness+" after "+j+" Rounds"+" in "+time2+ " ms \n";
//			path2 += "Neat/Playerb.txt";
//			file = new File(path2);
//			}
//			
//			if(game.a.Playertype == 4){
//			content = "maxFitness: "+game.a.pool.getbest().fitness+" after "+j+" Rounds"+" in "+time2+ " ms \n";
//			path += "Hyper/Playera.txt";
//			file = new File(path);
//			//System.out.println("Done");
//			}
//			
//			if(game.b.Playertype == 4){
//			content = "maxFitness: "+game.b.pool.getbest().fitness+" after "+j+" Rounds"+" in "+time2+ " ms \n";
//			path2 += "Hyper/Playerb.txt";
//			file = new File(path2);
//			}
//			
//			if(game.a.Playertype == 0){
//			content = "maxFitness: "+test.best().fitness+" after "+j+" Rounds"+" in "+time2+ " ms \n";
//			path += "Normal/Playera.txt";
//			file = new File(path);
//			}
//			
//			if(game.b.Playertype == 0){
//				content = "maxFitness: "+test.best().fitness+" after "+j+" Rounds"+" in "+time2+ " ms \n";
//				path2 += "Normal/Playerb.txt";
//				file = new File(path2);
//				}
//			
			// if file doesnt exists, then create it
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write(content);
//			bw.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
		}
		while(this.dowhile(j,this.i));
		
		//Output
		time = System.currentTimeMillis()-time;
	
		int Gen = 0;
		int Spec = 0;
		//System.out.println(game.a.Playertype+" "+game.b.Playertype+" "+game.a.Playerstrength+" "+game.b.Playerstrength);
		if(game.a.Playertype ==3){
		for(Species s:p.Species){
			if(s.Genomes.contains(p.getbest())){
				Gen = s.Genomes.indexOf(p.getbest());
				Spec = p.Species.indexOf(s);
			}
		}
	
		System.out.println("Best Genome in Player a is in Species :"+Spec+" Genome: "+Gen+" with Fitness of: "+p.getbest().getFitness());
		
		}
		if(game.b.Playertype ==3){
			Gen = 0;
			Spec = 0;
		for(Species s:p2.Species){
			if(s.Genomes.contains(p2.getbest())){
				Gen= s.Genomes.indexOf(p2.getbest());
				Spec = p2.Species.indexOf(s);
			}
		}

		System.out.println("Best Genome in Player b is in Species :"+Spec+" Genome: "+Gen+" with Fitness of: "+p2.getbest().getFitness());
		
		}
		
		System.out.println("Started saving");

		if(this.loadedtype1 == 1||this.loadedtype2 == 1){
		 test.rank();
			test.save("C:/KI/GLaDoS.ki", 0);
			try {
				for(int i=0;i<test.ranking.size();i++){
				ki.visualizer.visualize(test.ranking.get(i),"C:/tmp/Normal/GLaDoS"+i);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(this.loadedtype1 == 3){
			p.save("C:/tmp/Neat/","Shodan1.ki", 0);
		//	File f =  new File("C:/tmp/Neat/Shodan1.ki");
			//System.out.println(f.getAbsolutePath());
			for(Species s :p.Species){
				for(Genome g:s.Genomes){
					g.generateNetwork();
					neat.visualizer.visualize(g, "C:/tmp/Neat/Shodan1 Species "+p.Species.indexOf(s)+" Genome "+s.Genomes.indexOf(g));
				}
			}
			
		}
		if(this.loadedtype2 == 3){
			p2.save("C:/tmp/Neat/","Shodan2.ki", 0);
			for(Species s :p2.Species){
				for(Genome g:s.Genomes){
					g.generateNetwork();
					neat.visualizer.visualize(g, "C:/tmp/Neat/Shodan2 Species "+p2.Species.indexOf(s)+" Genome "+s.Genomes.indexOf(g));
				}
			}
			
		}
		
		if(this.loadedtype1 == 4){
			game.a.pool.save("C:/tmp/Hyper/","generator.ki",0);
			for(Species s :game.a.pool.Species){
				for(Genome g:s.Genomes){
					g.generateNetwork();
					int Speci = game.a.pool.Species.indexOf(s);
					int Geno = s.Genomes.indexOf(g);
					
					neat.visualizer.visualize(g, "C:/tmp/Hyper/generator/generator Species "+Speci+" Genome "+Geno);
					game.a.h.generateweigths(g);
					hyperneat.visualizer.visualize(game.a.h, "C:/tmp/Hyper/Hyper/Hyper Species "+Speci+" Genome "+Geno, Speci, Geno);
				}
			}
		}
		if(this.loadedtype2 == 4){
			game.b.pool.save("C:/tmp/Hyper/","generator.ki",0);
			for(Species s :game.b.pool.Species){
				for(Genome g:s.Genomes){
					g.generateNetwork();
					int Speci = game.b.pool.Species.indexOf(s);
					int Geno = s.Genomes.indexOf(g);
					neat.visualizer.visualize(g, "C:/tmp/Hyper/generator/generator Species "+Speci+" Genome "+Geno);
					
					game.b.h.generateweigths(g);
					hyperneat.visualizer.visualize(game.b.h, "C:/tmp/Hyper/Hyper/Hyper Species "+Speci+" Genome "+Geno, Speci, Geno);
				}
			}
		}
		System.out.println("DONE");
		}
		
		catch(InterruptedException e){
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
