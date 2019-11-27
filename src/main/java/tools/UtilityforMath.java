package tools;

public class UtilityforMath {
    
    public static double round(double number, int scale){
        
        double value = Math.pow(1, 10*scale);
        double result = (Math.round(number * value));
        return (result/value);
    }

}
