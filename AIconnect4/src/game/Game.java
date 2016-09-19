
public class Game {
	Player a;
	Player b;
	int[][] grid;
	long started;
	int set;
	StringBuilder GRID;
	int iteration;
	int testplay;
	int showgui;
	String path = "";

	public Game(Player a, Player b){
		grid = new int[6][7];
		this.a = a;
		this.b = b;
		set = 0;
		
	}
	
	public boolean isDoable(int z){
		for(int i = 0;i<grid.length;i++){
			if(grid[i][z] == 0){
				return true;
			}
		}
		return false;
	}
	//Gibt y wert zurück
	public int fall(int z,int value){
		for(int i = grid.length-1;i>=0;i--){
			if(grid[i][z] ==0){
				grid[i][z] = value;
				return i;
				
			}
		}
		return -1;
	}
	public int fallsim(int z){
		for(int i = grid.length-1;i>=0;i--){
			if(grid[i][z] ==0){
				//grid[i][z] = value;
				return i;
				
			}
		}
		return -1;
	}
	
	public void reset(){
		a.setChips(20);
		a.setTurns(0);
		b.setChips(20);
		b.setTurns(0);
	}
	
	public void gamedisplay(){
//		System.out.println("Round: " +this.iteration+" Turn: "+ a.getTurns() );
//		for(int[] line:grid){
//			GRID = new StringBuilder();
//			GRID.append("|");
//			for(int z:line){
//				if(z == 1){
//				GRID.append("X|");
//				}
//				if(z == -1){
//				GRID.append("O|");
//				}
//				if(z == 0){
//					GRID.append(" |");
//				}
//			}
//			System.out.println(GRID);
//		}
	}
	
	public int draw(Player z,int i,boolean xes) throws Exception{
		z.step(grid, xes,set,this.fallsim(set));
		String s = " Player b ";
		String b = "0";
		if(i>9){
			b = "";
		}
		if(xes){
			s = " Player a ";
		}
		switch(z.Playertype){
		case(0):ki.visualizer.visualize(z.ki, this.path+s+" Turn: "+b+i++);
		case(3):neat.visualizer.visualize(z.pool.Species.get(z.pool.currentSpecies-1).Genomes.get(z.pool.currentGenome-1), this.path+s+" Turn: "+b+i++);
		case(4):if(z.h != null){
			hyperneat.visualizer.visualize(z.h, this.path+s+" Species "+z.pool.currentSpecies+" Genome "+z.pool.currentGenome+" Turn: "+b+i++, z.pool.currentSpecies-1, z.pool.currentGenome-1);
		}}
		return i;
	}

	
	
	public int run(int reac, gameDisplay dis, int times){
		//ini
		this.iteration = reac;
		for(int i = 0;i<grid.length;i++){
			for(int j = 0;j<grid[i].length;j++){
				this.grid[i][j] = 0;
			}
		}
		int graphics = 0;
		int y = 0;
		int x = 0;
		while(2>1){
	//		System.out.println("Round:"+reac);
			
			//Player 1 Turn
			if(a.getChips()>0){
			//AI THinking
			AIcheckgrid(a);
			set = a.turn(0,grid,true,x,y);
			int tries = 0;
			while(!isDoable(set)){
				tries += 1;
				set = a.turn(tries,grid,true,x,y);
				if(set == -5){
					evaluategame(a, false,times);
					return 2;
				}
			}
			if(dis != null){
			dis.drawPanel.updateUI();;
			}
			y = fall(set,1);
			x = set;
			if(this.showgui  ==1){
		gamedisplay();
			}
			if(this.testplay == 1){
				try {
					if(a.pool != null){
						graphics =this.draw(a, graphics,true);
					}
					if(b.pool != null){
						graphics =this.draw(b, graphics,false);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			a.setChips(a.getChips()-1);
			a.setTurns(a.getTurns()+1);
			}
			if(check(1,set,y)){
				evaluategame(a, true,times);
				evaluategame(b, false,times);
				return 1;
			}
			
			//Player 2 Turn
			if(b.getChips()>0){
			//AI THinking
			AIcheckgrid(b);
			set = b.turn(0,grid,false,x,y);
			int tries = 0;
			while(!isDoable(set)){
				tries += 1;
				set = b.turn(tries,grid,false,x,y);
				if(set == -5){
					evaluategame(b, false,times);
					return 1;
				}
			}
			if(dis != null){
			dis.drawPanel.updateUI();;
			}
			y=fall(set,-1);
			x = set;
			if(this.showgui  ==1){
		gamedisplay();
			}
			if(this.testplay == 1){
				try {
					if(a.pool != null){
					graphics = this.draw(a, graphics ,true);
					}
					if(b.pool != null){
					graphics = 	this.draw(b, graphics,false);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			b.setChips(b.getChips()-1);
			b.setTurns(b.getTurns()+1);
			}
			if(check(-1,set,y)){
				if(this.showgui  ==1){
					gamedisplay();
						}
				if(this.testplay == 1){
					try {
						if(a.pool != null){
							graphics =this.draw(a, graphics,true);
						}
						if(b.pool != null){
							graphics =this.draw(b, graphics,false);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				evaluategame(a, false,times);
				evaluategame(b, true,times);
				return 2;
			}
			
			if(a.getChips() == b.getChips() && a.getChips() == 0){
				if(this.showgui  ==1){
					gamedisplay();
						}
				if(this.testplay == 1){
					try {
						if(a.pool != null){
							graphics =this.draw(a, graphics,true);
						}
						if(b.pool != null){
							graphics =this.draw(b, graphics,false);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				evaluategame(a, false,times);
				evaluategame(b, false,times);
				return 0;
			}
			
		}
	}
	
	public void evaluategame(Player z, boolean hasWon, int times){
		if(this.testplay != 1){
		int chips = z.getChips();
	//	int turns = z.getTurns();
		//int rowcount = 0;
		if(hasWon){
			//z.receivefeedback((chips+100+z.fitness)*times/2);
			if(z.ki != null){
			z.receivefeedback(10000+z.ki.fitness+chips);
			}
			else{
			if(z.pool != null){
				//z.receivefeedback((10000+z.pool.Species.get(z.pool.currentSpecies-1).Genomes.get(z.pool.currentGenome-1).fitness));//+chips
				z.receivefeedback((1000+chips+z.pool.Species.get(z.pool.currentSpecies-1).Genomes.get(z.pool.currentGenome-1).fitness));//+chips
					}
				
			}
		}
		else{
			if(z.ki != null){
			//z.receivefeedback((-chips+z.fitness)*times/2);
			z.receivefeedback(z.ki.fitness-chips);
			
		}
			else{
					if(z.pool != null){
				z.receivefeedback((-chips-1000+z.pool.Species.get(z.pool.currentSpecies-1).Genomes.get(z.pool.currentGenome-1).fitness));
					}
				
			}
			}
		 
		}
	}
	
	public void turn(){
		started = System.currentTimeMillis();
		
	}
	
	public void AIcheckgrid(Player z){
		if(z.Playertype ==0){
			int c =z.ki.out;
		
			for(int j = 0;j<6;j++){
				for(int i = 0;i<7;i++){
		z.ki.Nodes.get(c).setValue((double)grid[j][(i)]);
	//	System.out.println("Set Node"+z.ki.Nodes.get(c)+" to "+z.ki.Nodes.get(c).getValue()+" wanted "+grid[j][i]);
		c++;
			}
		}
		}
		
	}
	
	public  boolean check(int player,int laststonex,int laststoney){

		//Horizonzal check
		int times = 0;
		for(int i =0;i<6;i++){
			if(grid[i][laststonex]==player){
				times +=1;
				if(times >= 4){
					return true;
				}
			}
			else{
				times = 0;
			}
		}
		
		//Vertical Check
		times = 0;
		for(int i =0;i<7;i++){
			if(grid[laststoney][i]==player){
				times +=1;
				if(times >= 4){
					return true;
				}
			}
			else{
				times = 0;
			}
		}
		
		//Diagonal Check l to r
		times = 0;
		int y = laststoney;
		int x = laststonex;
		
		while(x>0&&y>0){
	    	x -=1;
	    	y -=1;
	    }

		for(int i =0;i+x<7 &&i+y<6;i++){
			if(grid[y+i][x+i]==player){
				times +=1;
				if(times >= 4){
					return true;
				}
			}
			else{
				times = 0;
			}
		}

		//Diagonal Check l to r
		times = 0;
		y = 0;
		
	    
			x = laststonex;
			y = laststoney;
		
		
	    while(x>0&&y<5){
	    	x -=1;
	    	y +=1;
	    }
		
		for(int i =0;x+i<7 &&i-y>0;i++){
			if(grid[y-i][x+i]==player){
				times +=1;
				if(times >= 4){
					return true;
				}
			}
			else{
				times = 0;
			}
		}
		
		return false;
	
	
	}
	
}

