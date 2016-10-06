
public class mathtest {
	
	public static void main(String args[]){
		int x = 3;
		int y = 4;
		
		int i = 0;
		int o = 0;
		for(int h=0;h<10;h++){
			i = h;
		for(int k = 0;k<10;k++){
			o = k;
		int[] a = new int[4];
		a[0] = i%x;
		a[1] = i%y;
		a[2] = o%(x);
		a[3] = o%y;
		System.out.println(a[0]+":"+a[1]+" | "+a[2]+":"+a[3]);
		}
	}
	}
}
