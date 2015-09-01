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

	public Running(Game g, int i,KICluster k,Pool p,gameDisplay dis){
		this.game = g;
		this.i = i;
		test = k;
		this.dis = dis;
		this.p = p;
	}
	
	public void run() {
		test.retest();
		try{
			int currentki=0;
			int maxruns = 10;	
		for(int j = 0;j<i;j++){
			if(Thread.interrupted()){
				throw new InterruptedException();
			}
			game.a.Playertype = 3;
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
			
//			if(game.a.Playertype == 3){
//				game.a.ki = null;
//				game.a.pool = p;
//				while(p.alreadyMeasured()){
//					p.nextGenome();
//				}
//				game.a.pool.Species.get(game.a.pool.currentSpecies-1).Genomes.get(game.a.pool.currentGenome-1).generateNetwork();
//			}
//	
//			if(game.b.Playertype == 3){
//				game.b.ki = null;
//				game.b.pool = p;
//				while(p.alreadyMeasured()){
//					p.nextGenome();
//				}
//				game.b.pool.Species.get(game.b.pool.currentSpecies-1).Genomes.get(game.b.pool.currentGenome-1).generateNetwork();
//				
//			}
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
				game.b.pool = p;
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
		game.reset();
		
		}
		}
		System.out.println("Started saving");
		for(Species s :p.Species){
			for(Genome g:s.Genomes){
				//g.generateNetwork();
				neat.visualizer.visualize(g, p.generation, "Neat/Neat Species "+p.Species.indexOf(s)+" Genome "+s.Genomes.indexOf(g));
			}
		}
		 test.rank();
			test.save("/tmp/Normal/GLaDoS.ki", 0);
			p.save("/tmp/Neat/Shodan.ki", 0);
			try {
				for(int i=0;i<test.ranking.size();i++){
				ki.visualizer.visualize(test.ranking.get(i),"Normal/GLaDoS"+i);
				}
				System.out.println("DONE");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch(InterruptedException e){
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
