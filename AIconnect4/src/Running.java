import ki.KICluster;


public class Running implements Runnable{
Game game;
int i;
KICluster test;
gameDisplay dis;
	public Running(Game g, int i,KICluster k,gameDisplay dis){
		this.game = g;
		this.i = i;
		test = k;
		this.dis = dis;
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
			//game.b.ki = test.ranking.get(currentki);
			maxruns = test.ranking.size();
			dis.drawPanel.ki = game.a.ki;
			
			}
			
		
//		System.out.println(game.b.Playertype);
		
		for(int runs = 0;runs<maxruns;runs++){
			if(game.a.Playertype == 0 && game.b.Playertype == 0){
			currentki = test.tournamentnext(currentki);
			if(currentki >= 500){
				break;
			}
			game.b.ki = test.ranking.get(currentki++);
			//dis.drawPanel.ki = game.b.ki;
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
		System.out.println(game.b.ki.Nodes.size());
		game.run(j,dis,runs);
		game.reset();
		
		}
		}
		 test.rank();
			test.save("/tmp/GLaDoS.ki", 0);

			try {
				for(int i=0;i<test.ranking.size();i++){
				ki.visualizer.visualize(test.ranking.get(i),"GLaDoS"+i);
				}
				System.out.println("DONE");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch(InterruptedException e){
			
		}
		
	}
	

}
