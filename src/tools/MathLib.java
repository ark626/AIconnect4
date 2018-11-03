package tools;

import java.math.BigDecimal;

public class MathLib {
    
    public static double activition(int n, double value) {
        
        return Math.scalb(activitionReduced(n, value),Double.MAX_EXPONENT/2);
//        double factor =  100000; //1 * 10^5 = 100000
//        return (activitionUnrounded(n, value)*factor)/factor;
        //return BigDecimal.valueOf(activitionUnrounded(n, value)).setScale(16, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }
    
    public static double activitionReduced(int n, double value) {
        switch (n) {
            case 0:
                return Math.sin(value);
            case 1:
                return Math.cos(value);         
            case 3:
                return Math.tanh(value);
            case 4:
                return (1.0 - Math.exp(-value)) / (1.0 + Math.exp(-value));
            case 5:
                return -value;
            case 6:
                return (2 / (1 + Math.exp(-4.9 * value)) - 1);
            case 7:
                return value;
            case 8:
                if ((int) Math.floor(value) % 2 == 0) {
                    return 1.0;
                } else {
                    return 0.0;
                }
            case 9:
                return 2.0 / (1.0 + Math.exp(-(value * 2))) - 1.0;
            case 10:
                return 1 / (1 + Math.exp(-value));
            case 11:
                return value*value;
            case 12:
                return Math.expm1(value);
            case 13:
                return Math.log(value);
            case 14:
                return Math.signum(value);
                
        }
        return value;
        // Sigmoid
        // case 1: return value; //Linear
        // case 2: return Math.round(value); //Binary
        // case 3: return Math.exp(-(value*value )); //Gaussian
        // case 4: return 2.0 / (1.0 + Math.exp(-(value * 2))) - 1.0; //Sigmoid Bipolar
        // case 5: if (value <= 0)return 0;
        // else if (value >= 1)return 1;
        // else return value;
        // case 6:if (value <= 0)value = 0;
        // else if (value >= 1)value = 1;
        // return (value * 2) - 1;
        // case 7: return Math.cos(value);
        // case 8: return Math.sin(value);

    }
    
    public static double activitionUnrounded(int n, double value) {
        switch (n) {
            case 0:
                return Math.sin(value);
            case 1:
                return Math.cos(value);
            case 2:
                double x = Math.tan(value);
                if (x < 10000.0 && x > -10000.0)
                    return x;
                if (x > 10000.0)
                    return 10000.0;
                if (x < -10000.0)
                    return -10000.0;
                return 0.0;
            case 3:
                return Math.tanh(value);
            case 4:
                return (1.0 - Math.exp(-value)) / (1.0 + Math.exp(-value));
            case 5:
                return Math.exp(-1.0 * (value * value));
            case 6:
                return 1.0 - 2.0 * (value - Math.floor(value));
            case 7:
                if ((int) Math.floor(value) % 2 == 0)
                    return 1.0;
                return -1.0;
            case 8:
                if ((int) Math.floor(value) % 2 == 0)
                    return 1.0 - 2.0 * (value - Math.floor(value));
                return -1 - 2 * (value - Math.floor(value));
            case 9:
                return -value;
            case 10:
                return (2 / (1 + Math.exp(-4.9 * value)) - 1);
            case 11:
                return value;
            case 12:
                if ((int) Math.floor(value) % 2 == 0) {
                    return 1.0;
                } else {
                    return 0.0;
                }
            case 13:
                return 2.0 / (1.0 + Math.exp(-(value * 2))) - 1.0;
            case 14:
                return 1 / (1 + Math.exp(-value));
        }
        return value;
        // Sigmoid
        // case 1: return value; //Linear
        // case 2: return Math.round(value); //Binary
        // case 3: return Math.exp(-(value*value )); //Gaussian
        // case 4: return 2.0 / (1.0 + Math.exp(-(value * 2))) - 1.0; //Sigmoid Bipolar
        // case 5: if (value <= 0)return 0;
        // else if (value >= 1)return 1;
        // else return value;
        // case 6:if (value <= 0)value = 0;
        // else if (value >= 1)value = 1;
        // return (value * 2) - 1;
        // case 7: return Math.cos(value);
        // case 8: return Math.sin(value);

    }

}
