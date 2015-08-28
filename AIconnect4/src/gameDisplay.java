import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ki.KI;
import ki.Link;

final public class gameDisplay
{

    JFrame frame;
    DrawPanel drawPanel;
    
    public gameDisplay re(gameDisplay gold){
    	gameDisplay g = new gameDisplay();
    	g.frame = gold.frame;
    	g.drawPanel = new DrawPanel(gold.drawPanel.ki);
    	//g.go(g.drawPanel.ki);
//    	frame.getContentPane().remove(gold.drawPanel);
//        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

    	return g;
    }
    
    public void refresh(){
//    	JPanel contentPane = (JPanel) frame.getContentPane();
//    	contentPane.removeAll();
//    	contentPane.add(this.drawPanel);
//    	contentPane.revalidate(); 
//    	contentPane.repaint();
    	
    }
    public static gameDisplay main(KI ki)
    {
    	gameDisplay g = new gameDisplay();
    	g.go(ki);
    	return g;
    }
    public void update(){
    	//this.frame.
    	this.drawPanel.getGraphics().dispose();
    	this.drawPanel.update(this.drawPanel.getGraphics());
    	//this.drawPanel.updateUI();
    }

    private void go(KI ki)
    {
   // 	if(frame == null){
        frame = new JFrame("KI Nodes");
        frame.setSize(640, 480);
    //	}
      //  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel(ki);

        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setResizable(false);
      //  frame.setSize(300, 300);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
     //   moveIt();
    }
   

    
    public class DrawPanel extends JPanel
    {
        private static final long serialVersionUID = 1L;
        public KI ki;
        
        public DrawPanel(KI k){
        	super();
        	this.ki = k;
        }
        
        public void refresh(){
        	 
        }
 

        public void paintComponent(Graphics g)
        {
     //   	KI ki = this.k;
        	g.clearRect(0, 0, getWidth(), getHeight() );
        	try {
    			Font font = new Font("TimesRoman", Font.BOLD, 20);
    		      g.setFont(font);
    		      
    		      
    		      //Erstellen der Output nodes
    		      for(int i=0;i<ki.out;i++){
    		      
    		     // if(ki.Nodes.get(i).getValue() >0){
    		      g.setColor(Color.getHSBColor((float)0,(float)0,(float)ki.Nodes.get(i).getValue()));
    		      //}
    		      g.fillRect(530, 30+i*30, 25, 25);
    		      
    		    //  g2d.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);
    		      }
    		      //Erstellen der Output nodes
    		      int correctionvalue = 0;//(ki.out%8);
//    		      if (correctionvalue > -1*(ki.out%8-1)){
//    		    	  correctionvalue = -1*correctionvalue;
//    		      }
    		      for(int i=0;i<ki.in;i++){
    		    	  float val = (float)ki.Nodes.get(i+ki.out).getValue();
    		      g.setColor(Color.getHSBColor(val*560,val*500,val*60));
    		      g.fillRect(20+30*((i+correctionvalue)%7), 30+((i+(correctionvalue))/7)*30, 25, 25);
    		    //  g2d.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);
    		      }   
    		      int cor = 75;
    		      for(int i=0;i<ki.hid;i++){
    		          g.setColor(Color.getHSBColor((float)0,(float)0,(float)ki.Nodes.get(i+ki.in+ki.out).getValue()));
    		          g.fillRect(300+(i/4)*30, 30+(i%4)*30, 25, 25);
    		        //  ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);
    		          } 
    		      if(ki.hidlayer >1){
    		      for(int i=0;i<ki.hid;i++){
    		          g.setColor(Color.getHSBColor((float)0,(float)0,(float)ki.Nodes.get(i+ki.hid+ki.in+ki.out).getValue()));
    		          g.fillRect(400+i/4*30, 30+(i%4)*30, 25, 25);
    		        //  ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);
    		          } 
    		      }
    		      
    		      int width = 640, height = 480;
    		      
    		      int x1=0;
    		      int x2=0;
    		      int y1=0;
    		      int y2=0;
    		      for(int i=0;i<ki.Links.size();i++){
    		    	  Link that = ki.Links.get(i);
    		    	  if(that != null){
    		    		  if(that.getValue() < 0){
    		    		  g.setColor(Color.red);
    		    		  }
    		    		  if(that.getValue() > 0){
    		        		  g.setColor(Color.green);
    		        		  }
    		    		  if(that.getValue() == 0){
    		        		  g.setColor(Color.gray);
    		        		  }
    		    		  if(that.getFrom()<ki.out){
    		    		  x1=530+12;
    		    		  y1 = 30+that.getFrom()*30+12;
    		    		  }
    		    		  if(that.getTo()<ki.out){
    		    		  x2=530+12;
    		    		  y2 = 30+that.getTo()*30+12;
    		    		  }
    		    		  
    		    		  if(that.getFrom()>=ki.out){
    		    			  x1 = 20+30*((that.getFrom()-ki.out)%7)+12;
    		    			  y1 = 30+((that.getFrom()-ki.out)/7)*30+12;
    		    		  } 
    		    		  if(that.getTo()>=ki.out){
    		    			  x2 = 20+30*((that.getTo()-ki.out)%7+12);
    		    			  y2 = 30+(((that.getTo())-ki.out)/7)*30+12;
    		    		  }
    		    		  
    		    		  if(that.getFrom()>= ki.out+ki.in){
    		    			  x1 = 300+(that.getFrom()-ki.out-ki.in)/4*30+12;
    		    			  y1 = 30+((that.getFrom()-ki.out-ki.in)%4)*30+12;
    		    		  }
    		    		  if(that.getTo()>= ki.out+ki.in){
    		    			  x2 = 300+(that.getTo()-ki.out-ki.in)/4*30+12;
    		    			  y2 = 30+((that.getTo()-ki.out-ki.in)%4)*30+12;
    		    			//  (380+((i-48)/4)*30, 30+((i-48)%4)*30,
    		    		  }
    		    		  if(that.getFrom()>= ki.out+ki.in+ki.hid){
    		    			  x1 = 400+(that.getFrom()-ki.out-ki.in-ki.hid)/4*30+12;
    		    			  y1 = 30+((that.getFrom()-ki.out-ki.in-ki.hid)%4)*30+12;
    		    		  }
    		    		  if(that.getTo()>= ki.out+ki.in+ki.hid){
    		    			  x2 = 400+(that.getTo()-ki.out-ki.in-ki.hid)/4*30+12;
    		    			  y2 = 30+((that.getTo()-ki.out-ki.in-ki.hid)%4)*30+12;
    		    			//  (380+((i-48)/4)*30, 30+((i-48)%4)*30,
    		    		  }
    		    	  }
    		    	  g.drawLine(x1, y1, x2, y2);
    		      }
    		      if(ki.fitness > 0)
    		      {
    		    	  g.setColor(Color.green);
    		      }
    		      if(ki.fitness == 0)
    		      {
    		    	  g.setColor(Color.gray);
    		      }
    		      if(ki.fitness < 0)
    		      {
    		    	  g.setColor(Color.red);
    		      }
    		      String message = "Tested?: "+ki.isTesting+" Genome: "+ki.gen;
    		      FontMetrics fontMetrics = g.getFontMetrics();
    		      int stringWidth = fontMetrics.stringWidth(message);
    		      int stringHeight = fontMetrics.getAscent();
    		      g.drawString(message, (width - stringWidth-40) / 2, (height / 2)+150 + stringHeight / 4);
    		      message =  " Generation: "+ki.generation+" Fitness: "+ki.fitness;
    		      g.drawString(message, (width - stringWidth-40) / 2, (height / 2)+150 + stringHeight * 2);
    			

    		  
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
    }

  
    
}