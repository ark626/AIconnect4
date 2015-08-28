import java.util.Random;


public  class minmax {
	public static int step(int[][] grid,boolean player,int rec,boolean xes){
		int step = 0;
		int stepvalue[][] = new int[2][7];
		for(int[] a : stepvalue){
			for(int i = 0;i<7;i++){
				a[i] = Integer.MIN_VALUE;
			}
		}
		String tst="Step Calculation: ";
		int result = Integer.MIN_VALUE;
		int comp = 0;
		for(int i = 0;i<7;i++){
			//System.out.println(rec);
			if(isDoable(i,grid)){
				if(player){
					int[][] pgrid = new int[6][7];
					for(int x=0;x<6;x++){
						for(int j=0;j<7;j++){
							pgrid[x][j] = grid[x][j];
						}
					}
					comp = playerstep(pgrid,i,rec,0,0,xes);
//					tst += comp +" ";
				}
				
				else{
					int[][] pgrid = new int[6][7];
					for(int x=0;x<6;x++){
						for(int j=0;j<7;j++){
							pgrid[x][j] = grid[x][j];
						}
					}
					comp = enemystep(pgrid,i,rec,0,4,xes);
				}
				//System.out.println("Compvalue for step "+i+" is "+comp);
				stepvalue[0][i] = i;
				stepvalue[1][i] = comp;
				tst += stepvalue[0][i]+" "+stepvalue[1][i]+" ";
				if(comp > result){
					result = comp;
					step = i;
				}
			}


		}
	//	comp = Integer.MIN_VALUE;
//		for(int i = 0;i<8;i++){
//			if(stepvalue[0][i]>0){
//			if(!isDoable(stepvalue[0][i],grid)){
//				stepvalue[0][i] = Integer.MIN_VALUE;
//				stepvalue[1][i] = Integer.MIN_VALUE;
//			}
//			if(stepvalue[1][i] < comp && isDoable(stepvalue[0][i],grid)){
//				stepvalue[0][i] = Integer.MIN_VALUE;
//				stepvalue[1][i] = Integer.MIN_VALUE;
//			}
//			if(stepvalue[1][i] > comp && isDoable(stepvalue[0][i],grid)){
//				comp = stepvalue[1][i];
//				for(int j=i-1;j>=0;j--){
//					stepvalue[0][j] = Integer.MIN_VALUE;
//					stepvalue[1][j] = Integer.MIN_VALUE;
//				}
//			}
//			}
//		}
		int l = 0;
		
		//System.out.println("comp: "+ comp);
		for(int a:stepvalue[1]){
			if(a == result){
				l++;
			}
		}
		int[] wanted = new int[l];
		l = 0;
		for(int i=0;i<7;i++){
			if(stepvalue[1][i]== result){
				if(isDoable(stepvalue[0][i],grid)){
				wanted[l] = stepvalue[0][i];
				}
				tst += " :"+ wanted[l];
				l++;
			}
		}
		System.out.println(tst);
		Random rand = new Random();
		step = wanted[rand.nextInt(wanted.length)];
		System.out.println("Step: "+step);
		return step;
		
//		if(step == 0){
//			while(!isDoable(step,grid)){
//				System.out.println("No way!");
//				step += 1;
//			}
//		}
//		//
//		System.out.println(tst);
//		//System.out.println("DO Step: "+step);
//		return step;
		//}
		//else{
		//	Random rand = new Random();
		//	return rand.nextInt(8);
		//}
		
	}
	
	public static int helper(int[][] grid,boolean player,int rec, int lastx, int lasty,boolean xes){
		int result =0;
		
		for(int i = 0;i<7;i++){
			//System.out.println(rec);
			if(isDoable(i,grid)){
				
				if(player){
					int[][] pgrid = new int[6][7];
					for(int x=0;x<6;x++){
						for(int j=0;j<7;j++){
							pgrid[x][j] = grid[x][j];
						}
					}
					int ps = playerstep(pgrid,i,rec,lastx,lasty,xes);
					result += ps;
				}
				
				else{
					int[][] pgrid = new int[6][7];
					for(int x=0;x<6;x++){
						for(int j=0;j<7;j++){
							pgrid[x][j] = grid[x][j];
						}
					}
					int ps = enemystep(pgrid,i,rec,lastx,lasty,xes);
					result += ps;
				}
			}
//			else{
//				if(player){
//					result -= 1 + 1*Math.pow(8, rec);
//				}
//				else{
//				//	result += 1 + 1*Math.pow(8, rec);
//				}
//			}
		}
			return result;
	}
			
	
	public static int enemystep(int[][] grid, int x,int rec,int lastx, int lasty,boolean xes){
		
		//evaluate last step
//		if(check(1,lastx,lasty,grid)){
//			return 1*rec;
//		}
		int y = 0;
		for(int i = 4;i>=0;i--){
			if(grid[i][x] ==0){
				if(xes){
				grid[i][x] = -1;
				}
				else{
					grid[i][x] = 1;
				}
				 y = i;
				 lasty = y;
				 lastx = x;
				break;
			}
		}
		if(xes){
		if(check(-1,lastx,lasty,grid)){
		//	System.out.print("Value; "+ (-1+((int) ((-1)*(Math.pow( 8,rec))))));
		return -1*((int) ((Math.pow( 64,rec))));
	}
		}
		else{
			if(check(1,lastx,lasty,grid)){
				//	System.out.print("Value; "+ (-1+((int) ((-1)*(Math.pow( 8,rec))))));
				return -1*((int) ((Math.pow( 64,rec))));
			}
		}
		rec -=1;
		if(rec <= 0){
			return 0;
		}
		//printgrid(grid);
			//Nobody won =>
			return helper(grid,true,rec,lastx,lasty,xes);

		
	}
	
	
	public static int playerstep(int[][] grid,int x,int rec,int lastx,int lasty,boolean xes){
	//simulate step
		//evaluate last step
//		if(check(2,lastx,lasty,grid)){
//			return -1*rec;
//		}
		int y = 0;
		for(int i = grid.length-1;i>=0;i--){
			if(grid[i][x] ==0){
				if(xes){
				grid[i][x] = 1;
				}
				else{
					grid[i][x]=-1;
				}
				y = i;
				 lasty = y;
				 lastx = x;
				break;
			}
		}
		if(xes){
		if(check(1,lastx,lasty,grid)){
		return (int) ((Math.pow( 64,rec)));
	}
		}
		else{
			if(check(2,lastx,lasty,grid)){
				return (int) ((Math.pow( 64,rec)));
			}
		}
			//Nobody won =>
			return helper(grid,false,rec,lastx,lasty,xes);	
	}
	
	
	
	public static boolean isDoable(int z,int[][] grid){
		
		for(int i = 0;i<grid.length;i++){
			if(grid[i][z] == 0){
				return true;
			}
		}
		return false;
	}
	
	public static boolean check(int player,int laststonex,int laststoney, int[][] grid){
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
	
	
	

}
