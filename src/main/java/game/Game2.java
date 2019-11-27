package game;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import aiAlogrithmes.ki.KI;
import aiAlogrithmes.ki.Link;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

class Game2 extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int DELAY = 150;
    private Timer timer;
	KI ki;
    public Game2(KI k) {
		super();
		ki = k;
        initTimer();
    }

    private void initTimer() {

        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public Timer getTimer() {
        
        return timer;
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
    		try {
    			Font font = new Font("TimesRoman", Font.BOLD, 20);
    		      g2d.setFont(font);
    		      
    		      
    		      //Erstellen der Input nodes
    		      for(int i=0;i<8;i++){
    		      
    		     // if(ki.Nodes.get(i).getValue() >0){
    		    	  g2d.setPaint(Color.getHSBColor((float)0,(float)0,(float)ki.Nodes.get(i).getValue()));
    		      //}
    		      g2d.drawRect(530, 30+i*30, 25, 25);
    		    //  g2d.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);
    		      }
    		      //Erstellen der Output nodes
    		      for(int i=8;i<48;i++){
    		    	 g2d.setPaint(Color.getHSBColor((float)0,(float)0,(float)ki.Nodes.get(i).getValue()));
    		      g2d.drawRect(20+30*(i%8), 30+(i/8)*30, 25, 25);
    		    //  g2d.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);
    		      }   
    		      int width = 640, height = 480;
    		      //TODO:Erstelen von Hidden Nodes
    		      int x1=0;
    		      int x2=0;
    		      int y1=0;
    		      int y2=0;
    		      for(int i=0;i<ki.Links.size();i++){
    		    	  Link that = ki.Links.get(i);
    		    	  if(that != null){
    		    		  if(that.getValue() < 0){
    		    		  g2d.setPaint(Color.red);
    		    		  }
    		    		  if(that.getValue() > 0){
    		        		  g2d.setPaint(Color.green);
    		        		  }
    		    		  if(that.getValue() == 0){
    		        		  g2d.setPaint(Color.gray);
    		        		  }
    		    		  if(that.getFrom()<8){
    		    		  x1=530+12;
    		    		  y1 = 30+that.getFrom()*30+12;
    		    		  }
    		    		  if(that.getTo()<8){
    		    		  x2=530+12;
    		    		  y2 = 30+that.getTo()*30+12;
    		    		  }
    		    		  if(that.getFrom()>=8){
    		    			  x1 = 20+30*(that.getFrom()%8)+12;
    		    			  y1 = 30+(that.getFrom()/8)*30+12;
    		    		  } 
    		    		  if(ki.Nodes.indexOf(that.getTo())>=8){
    		    			  x2 = 20+30*(that.getTo()%8+12);
    		    			  y2 = 30+(ki.Nodes.indexOf(that.getTo())/8)*30+12;
    		    		  }
    		    	  }
    		    	  g2d.drawLine(x1, y1, x2, y2);
    		      }
    		      String message = "Tested?: "+ki.isTesting+" Fitness: "+ki.fitness+"Generation: "+ki.generation;
    		      FontMetrics fontMetrics = g2d.getFontMetrics();
    		      int stringWidth = fontMetrics.stringWidth(message);
    		      int stringHeight = fontMetrics.getAscent();
    		      g2d.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);
    		     
    			

    		  
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

    		g2d.draw(new Ellipse2D.Double(0, 100, 30, 30));
    	}
    	
    
    

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }




    public static void initUI(KI k) {
    	JFrame frame = new JFrame();
    	frame.setSize(640, 480);
    	frame.setVisible(true);
        final Game2 surface = new Game2(k);
        frame.add(surface);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer = surface.getTimer();
                timer.stop();
            }
        });

        frame.setTitle("KI Nodes");
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            
            public void run() {

            }
        });
    }
}


