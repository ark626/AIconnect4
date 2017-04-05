import java.sql.Timestamp;

import hyperneat.HyperNeat;
import neat.Pool;

public class mathtest {

    public static void main(String args[]) throws ClassNotFoundException {
        // for(int t = 0;t<100;t++){
        // byte[] input = new byte[102];
        // double[] inp = new double[102];
        // for(int i=0;i<input.length;i++){
        // input[i] = (byte)(Math.random()*Math.random()*100);
        // }
        // for(int i=0;i<input.length;i++){
        // inp[i] = (double)(input[i])*Math.random()*Math.random()*Math.random();
        // //System.out.print(inp[i]+" ");
        // }
        //
        // for(int i=0;i<input.length;i++){
        // for(int j = 0;j<100;j++){
        // if(Math.round(Math.random()) >0.5){
        // inp[i] = -inp[i];
        // }
        // }
        // System.out.print(inp[i]+" ");
        // }
        //
        // double [][] results = new double[100][7];
        //
        // for(int i = 0;i<results.length;i++){
        // Pool p = Pool.load("./MARIOHYPERAI.ki");
        // HyperNeat h = new HyperNeat(101+1,7,0,8,13,p);
        // h.generateweigths(h.getPool().getbest());
        // results[i]=h.step(inp);
        // System.out.println("FITNESS: "+p.getbest().getFitness());
        // }
        //
        // double[] temp = new double[7];
        // temp = results[0];
        // for(double[] set:results){
        // for(int i = 0;i<7;i++){
        // if(set[i]!= temp[i]){
        // System.out.println(temp[i]+" vs "+set[i]);
        // }
        //
        // }
        // }
        //
        //
        // }
        //
        Timestamp t = new Timestamp(System.currentTimeMillis());
        System.out.println(t.toString());
        System.out.println(datetotimestamp(t.toString()));
    }

    public static String datetotimestamp(String s) {
        s = s.toString().replaceAll("-", "");
        // s = s.replaceAll(".", "");
        s = s.replaceAll(":", "");
        s = s.replaceAll(" ", "");
        s = s.substring(0, s.length() - 4);
        return s;
    }
}
