package tools;
import java.awt.*;
import javax.swing.*;

public class SimpleGraph extends JFrame {
    private final int WIDTH = 1000;
    private final int HEIGHT = 600;
    private Container drawable;
    private JPanel canvas;
    public SimpleGraph(double[] someData,String name) {
        super(name);
        drawable = getContentPane();
        canvas = new GraphCanvas(someData);
        drawable.add(canvas);
        setSize(WIDTH, HEIGHT);
    }
    public class GraphCanvas extends JPanel {
        private double[] data;
        private int points;
        private double[] XData;
        private double[] YData;
        public GraphCanvas(double[] someData) {
            super();
            data = someData;
            points = data.length / 2;
            XData = new double[points];
            YData = new double[points];
            for(int i = 0; i < points; i++) {
                XData[i] = data[i * 2];
                YData[i] = data[i * 2 + 1];
            }
        }
        public void paint(Graphics g) {
            int zoom = 35;
            int move = 15;
            Graphics2D g2 = (Graphics2D) g;
            
            int scalaLength = 10;
            for(int i = -10;i<10;i++) {
                int x0 = x(zoom, move, i);
                int x1 = x(zoom, move, i+1);
                int y0 = y(zoom, move, i);
                int y1 = y(zoom, move, i+1);
                
                
                
                g2.setColor(Color.GRAY);
                g2.drawLine(x0, -y(zoom, move, 0), x0, -y(zoom, move, scalaLength));
                g2.drawLine(x(zoom, move, 0), -y0, x(zoom, move, scalaLength), -y0);
                g2.drawLine(x0, -y(zoom, move, 0), x0, -y(zoom, move, -scalaLength));
                g2.drawLine(x(zoom, move, 0), -y0, x(zoom, move, -scalaLength), -y0);
                
                
                g2.setColor(Color.BLACK);
                g2.drawLine(x0, -y(zoom, move, 0), x1, -y(zoom, move, 0));
                g2.drawLine(x(zoom, move, 0), -y0, x(zoom, move, 0), -y1);
                

                

                
            }
            
            
            for(int i = 0; i < points - 1; i++) {
                int x0 = x(zoom, move, XData[i]);
                int x1 = x(zoom, move, XData[i+1]);
                int y0 = y(zoom, move, YData[i]);
                int y1 = y(zoom, move, YData[i+1]);
                g2.setColor(Color.RED);
                g2.drawLine(x0, -y0, x1, -y1);
                if (i == 0)
                  g2.drawString(("" + x0 + ", " + -y0), x0 - 20, -y0 + 10);
                if (i == points - 2)
                  g2.drawString(("" + x1 + ", " + -y1), x1, -y1);
            }

        }
//        private int y(int zoom, int move, int i) {
//            return (int) (i +0.5-move)*zoom;
//        }
//        private int x(int zoom, int move, int i) {
//            return (int) (i + 0.5+move)*zoom;
//        }
        
        private int y(int zoom, int move, double i) {
            return (int) ((i +0.5-move)*zoom);
        }
        private int x(int zoom, int move, double i) {
            return (int) ((i + 0.5+move)*zoom);
        }
    }
    public static void main(String[] args) {
        double[] d = { 30.0, 150.0,
                       33.0, 145.0,
                       36.0, 162.0,
                       39.0, 128.0,
                       48.0, 114.0,
                       70.0, 240.0,
                       81.0, 400.0,
                      130.0, 450.0,
                      230.0,  85.0,
                      255.0,  30.0 };
        Frame f = new SimpleGraph(d,"");
        f.show();
    }
}