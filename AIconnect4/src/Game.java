
public class Game {
	Player a;
	Player b;
	int[][] grid;
	long started;
	int set;
	StringBuilder GRID;
	int iteration;

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
	
	public void reset(){
		a.setChips(20);
		a.setTurns(0);
		b.setChips(20);
		b.setTurns(0);
	}
	
	public void gamedisplay(){
		System.out.println("Round: " +this.iteration+" Turn: "+ a.getTurns() );
		for(int[] line:grid){
			GRID = new StringBuilder();
			GRID.append("|");
			for(int z:line){
				if(z == 1){
				GRID.append("X|");
				}
				if(z == -1){
				GRID.append("O|");
				}
				if(z == 0){
					GRID.append(" |");
				}
			}
			System.out.println(GRID);
		}
	}

	
	
	public int run(int reac, gameDisplay dis, int times){
		//ini
		this.iteration = reac;
		for(int i = 0;i<grid.length;i++){
			for(int j = 0;j<grid[i].length;j++){
				this.grid[i][j] = 0;
			}
		}
		while(1 == 1){
	//		System.out.println("Round:"+reac);
			int y = 0;
			//Player 1 Turn
			if(a.getChips()>0){
			//AI THinking
			AIcheckgrid(a);
			set = a.turn(0,grid,true);
			int tries = 0;
			while(!isDoable(set)){
				tries += 1;
				set = a.turn(tries,grid,true);
				if(set == -5){
					evaluategame(a, false,times);
					return 2;
				}
			}
			if(dis != null){
			dis.drawPanel.updateUI();;
			}
			y = fall(set,1);
		gamedisplay();
			
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
			set = b.turn(0,grid,false);
			int tries = 0;
			while(!isDoable(set)){
				tries += 1;
				set = b.turn(tries,grid,false);
				if(set == -5){
					evaluategame(b, false,times);
					return 1;
				}
			}
			if(dis != null){
			dis.drawPanel.updateUI();;
			}
			y=fall(set,-1);
			gamedisplay();
			b.setChips(b.getChips()-1);
			b.setTurns(b.getTurns()+1);
			}
			if(check(-1,set,y)){
				gamedisplay();
				evaluategame(a, false,times);
				evaluategame(b, true,times);
				return 2;
			}
			
			if(a.getChips() == b.getChips() && a.getChips() == 0){
				evaluategame(a, false,times);
				evaluategame(b, false,times);
				return 0;
			}
			
		}
	}
	
	public void evaluategame(Player z, boolean hasWon, int times){
		int chips = z.getChips();
	//	int turns = z.getTurns();
		//int rowcount = 0;
		if(hasWon){
			//z.receivefeedback((chips+100+z.fitness)*times/2);
			if(z.ki != null){
			z.receivefeedback(100+z.ki.fitness+chips);
			}
			else{
				if(times == 0){
					z.receivefeedback(100+chips);

				}
				else{
					if(z.pool != null){
				z.receivefeedback((100+chips+z.pool.Species.get(z.pool.currentSpecies-1).Genomes.get(z.pool.currentGenome-1).fitness));
					}
				}
			}
		}
		else{
			if(z.ki != null){
			//z.receivefeedback((-chips+z.fitness)*times/2);
			z.receivefeedback(z.ki.fitness-chips);
			
		}
			else{
				if(times == 0){
					if(z.pool != null){
					z.receivefeedback(-chips);
					}
				}
				else{
					if(z.pool != null){
				z.receivefeedback((-chips+z.pool.Species.get(z.pool.currentSpecies-1).Genomes.get(z.pool.currentGenome-1).fitness));
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
		int y = 0;
		int x = laststonex -laststoney;
		while(x < 0){
			x +=1;
			y +=1;
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
	    x = laststonex +laststoney;
		while(x > 6){
			x -=1;
			y +=1;
		}
		for(int i =0;x-i>0 &&i+y<6;i++){
			if(grid[y+i][x-i]==player){
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
	
//	public boolean check(int z){
//		//int result = 0;
//		for(int i = 0;i<grid.length;i++){
//			for(int j = 0;j<grid[i].length;j++){
//				if(grid[i][j]==z){
//					if(checkhelper(0,0,z,i,j)==z){
//						return true;
//					}
//					if(checkhelper(0,1,z,i,j)==z){
//						return true;
//					}
//					if(checkhelper(0,2,z,i,j)==z){
//						return true;
//					}
//					if(checkhelper(0,3,z,i,j)==z){
//						return true;
//					}
//					
//				}
//			
//			}
//		}
//		return false;
//		
//		
//	}
//	
//	public int checkhelper(int times,int direction,int value, int x, int y){	
//		if(times < 3){
//			switch(direction){
//			case(0):
//				if(y > 0){
//					if(grid[x][y-1]==value){
//						return checkhelper(times+1,direction,value,x,y-1);
//					}
//					else{
//						return 0;
//					}
//				}
//				else{
//					return 0;
//				}
//			
//		case(1):
//			if(y < grid[x].length-1){
//				if(grid[x][y+1]==value){
//					return checkhelper(times+1,direction,value,x,y+1);
//				}
//				else{
//					return 0;
//				}
//			}
//			else{
//				return 0;
//			}
//
//		case(2):
//			if(x > 0){
//				if(grid[x-1][y]==value){
//					return checkhelper(times+1,direction,value,x-1,y);
//				}
//				else{
//					return 0;
//				}
//			}
//			else{
//				return 0;
//			}
//		
//		case(3):
//			if(x < grid.length-1){
//				if(grid[x+1][y]==value){
//					return checkhelper(times+1,direction,value,x+1,y);
//				}
//				else{
//					return 0;
//				}
//			}
//			else{
//				return 0;
//			}
//		
//}
//		}
//		else{
//			return value;
//		}
//		return value;
//}
	
}

