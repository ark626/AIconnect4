
public class mathtest {
	
	public static void main(String args[]){
		double out =0;
		for(int i=-100;i<100;i++){
			double a = -0.01*i;
		a = 2.0 / (1.0 + Math.exp(-(a * 2))) - 1.0;
		System.out.println(a);
		}
	}
		

}
