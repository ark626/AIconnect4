import java.util.Random;
import java.util.Scanner;

import ki.KI;


public class Player {
	private int points;
	private int chips;
	private int turns;
	public int fitness;
	public KI ki;
	public Steptree ultimate;
	public int Playertype;
	public int Playerstrength;
	public boolean xes;
	
	public Player(){
		points = 0;
		chips = 20;
		turns = 0;
	    fitness = 0;
	    Playertype = 0;
	}
	
	public Player(int a,int b){
		points = 0;
		chips = 20;
		turns = 0;
	    fitness = 0;
	    Playertype = a;
	    this.Playerstrength = b;
	}

	public int turn(int errors, int[][] grid,boolean xes){
		if(errors < 10){
		//TODO:Set evaluation of network with timer to do a step.
			return this.step(grid,xes);
		}
		else{
		//	System.out.println("Error: "+ this.Playertype);
			//Random random = new Random();
			return -5;//random.nextInt(7 - 0 + 1) + 0;
		}
	}
	
	public boolean checkgrid(int[][] grid){
		for(int i=0;i<5;i++){
			for(int j=0;j<8;j++){
				if(grid[i][j] !=0){
					return false;
				}
			}
		}
		return true;
	}
	
	public int step(int[][] grid,boolean xes){
		if(Playertype == 0){
		return this.ki.step();
		}
		if(Playertype == 1){
//			if(checkgrid(grid)){
//			return 4;	
//			}
			int[][] pgrid = new int[6][7];
			for(int i=0;i<6;i++){
				for(int j=0;j<7;j++){
					pgrid[i][j] = grid[i][j];
				}
			}
			return minmax.step(pgrid, true,this.Playerstrength,xes);
		}
		else{
			Scanner in = new Scanner(System.in);
			return in.nextInt();
		}
	}
	

	
	public void receivefeedback(int fitness){
		if(Playertype ==0){
		this.ki.fitness = fitness;
		}
		}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getChips() {
		return chips;
	}

	public void setChips(int chips) {
		this.chips = chips;
	}

	public int getTurns() {
		return turns;
	}

	public void setTurns(int turns) {
		this.turns = turns;
	}

}
