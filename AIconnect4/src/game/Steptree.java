package game;

public class Steptree {
	Steptree parent;
	int x;
	int y;
	int player;
	int prob;
	Steptree[] child;
	
	Steptree(int x,int y){
		parent = null;
		this.x = x;
		this.y = y;
		prob = 0;
		child = new Steptree[8];
		this.createchildrenhelp();
		this.createchildren();
		this.evaluatechilds();
	}
//Sollte 5 iterationen 8 Kinder erstellen	
	public void createchildren(){
		Steptree that = this;
		if(that != null){
			if(!that.check(1) && !that.check(2)){
			for(int i = 0;i<5;i++){
				for(int z=0;z<8;z++){
				 that.child[i].createchildren();
				 that.child[i].createchildrenhelp();
				}
			}
			
			}
		}
	}
	
	public int step(int[][] grid){
		if(this.getGrid().equals(grid)){
			return this.getbest();
		}
		else{
			for(int i = 0;i<8;i++){
				if(this.child[i]!= null){
					this.child[i].step(grid);
				}
				else{return 0;}
			}
		}
		return 0;
	}
	
	public int getbest(){
		int i = 0;
		int res = 0;
		for(int z=0;z<8;z++){
			if(this.prob > i){
				i = this.prob;
				res = z;
			}
		}
		return res;
	}
	
	public int evaluatechilds(){
		if(this.ischildrenempty()){
			if(this.check(1)){
				this.prob =1;
				return 1;
			}
			else{
				if(this.check(2)){
					this.prob = -1;
					return -1;
				}
				else{
					this.prob = 0;
					return 0;
				}
			}
		}
		else{
			int result =0 ;
			for(int i = 0;i<8;i++){
				if(this.child[i]!=null){
				result += this.child[i].evaluatechilds();
				}
				else{return 0;
				
				}
				}
			this.prob = result;
			return result;
		}
		
	}
	
	public boolean ischildrenempty(){
		for(int i = 0;i<8;i++){
			if(this.child[i] != null){
				return false;
			}
		}
		return true;
	}
	
// Erstellt 8 Kinder
	public void createchildrenhelp(){
		if(this.player % 2 == 0){
			for(int i =0;i<8;i++){
				if(this.isDoable(i)){
					this.child[i]= new Steptree(i,this.fall(i, 1));
					this.child[i].parent = this;
					this.child[i].player = player +1;
				}
			}
		}
		if(this.player % 2 == 1){
			for(int i =0;i<8;i++){
				if(this.isDoable(i)){
					this.child[i]= new Steptree(i,this.fall(i, 2));
					this.child[i].parent = this;
					this.child[i].player = player +1;
				}
			}
		}
	}
		


	
	public int [][] getGrid(){
		int[][] grid = new int[5][8];
		Steptree that = this.parent;
		while(that != null){
			grid[this.y][this.x] =this.player;
			that = that.parent;
		}
		return grid;
		
	}
	
	public boolean isDoable(int z){
		int[][] grid = this.getGrid();
		for(int i = 0;i<grid[z].length;i++){
			if(grid[z][i] == 0){
				return true;
			}
		}
		return false;
	}
	
	public int fall(int z,int value){
		int[][] grid = this.getGrid();
		for(int i = grid.length-1;i>=0;i--){
			if(grid[i][z] ==0){
				return i;
			}
			
		}
		return -1;
	}
	
	
	//CLONED METHOD
	
	public boolean check(int z){
		//int result = 0;
		int[][] grid = this.getGrid();
		for(int i = 0;i<grid.length;i++){
			for(int j = 0;j<grid[i].length;j++){
				if(grid[i][j]==z){
					if(checkhelper(0,0,z,i,j,grid)==z){
						return true;
					}
					if(checkhelper(0,1,z,i,j,grid)==z){
						return true;
					}
					if(checkhelper(0,2,z,i,j,grid)==z){
						return true;
					}
					if(checkhelper(0,3,z,i,j,grid)==z){
						return true;
					}
					
				}
			
			}
		}
		return false;
		
		
	}
	
	public int checkhelper(int times,int direction,int value, int x, int y,int[][] grid){	
		if(times < 4){
			switch(direction){
			case(0):
				if(y > 0){
					if(grid[x][y-1]==value){
						return checkhelper(times+1,direction,value,x,y-1,grid);
					}
					else{
						return 0;
					}
				}
				else{
					return 0;
				}
			
		case(1):
			if(y < grid[x].length-1){
				if(grid[x][y+1]==value){
					return checkhelper(times+1,direction,value,x,y+1,grid);
				}
				else{
					return 0;
				}
			}
			else{
				return 0;
			}

		case(2):
			if(x > 0){
				if(grid[x-1][y]==value){
					return checkhelper(times+1,direction,value,x-1,y,grid);
				}
				else{
					return 0;
				}
			}
			else{
				return 0;
			}
		
		case(3):
			if(x < grid.length-1){
				if(grid[x+1][y]==value){
					return checkhelper(times+1,direction,value,x+1,y,grid);
				}
				else{
					return 0;
				}
			}
			else{
				return 0;
			}
		
}
		}
		else{
			return value;
		}
		return value;
}
}