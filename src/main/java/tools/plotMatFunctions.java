package tools;

import java.awt.Frame;

public class plotMatFunctions {

    public static void main(String[] args) {
        double[] d = new double[2000+3];
        
        for (EnumMath current : EnumMath.values()) {
            int z = 0;
            double x = -10;
            for (double i = -10; i < 10; i=i+0.02) {

                d[z++]=x;
                x=x+0.02;
                d[z++]=MathLib.newAcitvation(current, i, 2, 0);

            }
            Frame f = new SimpleGraph(d,current.name());
            f.show();
        }


    }

}
