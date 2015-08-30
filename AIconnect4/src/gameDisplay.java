import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import neat.Gene;
import neat.Genome;
import neat.Neuron;
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
    
    public static gameDisplay main(Genome g, int generation){
    	gameDisplay z = new gameDisplay();
    	z.go(g,generation);
    	return z;
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
   
    private void go(Genome g, int generation)
    {
   // 	if(frame == null){
        frame = new JFrame("KI Nodes");
        frame.setSize(640, 480);
    //	}
      //  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel(g,generation);

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
        public Genome g;
        public int generation;
        public boolean update;
        
        public DrawPanel(KI k){
        	super();
        	this.ki = k;
        }
        
        public DrawPanel(Genome g, int generation){
        	super();
        	this.ki = null;
        	this.g = g;
        	this.generation = generation;
        	
        	
        }
        
        public void refresh(){
        	 
        }
 

        public void paintComponent(Graphics g)
        {
     //   	KI ki = this.k;
        	while(this.update){

        	}
        	if(!this.update){
        	g.clearRect(0, 0, getWidth(), getHeight() );
        	int width = 640, height = 480;
        	try {
    			Font font = new Font("TimesRoman", Font.BOLD, 20);
    		      g.setFont(font);

    		      if(ki != null&& this.g == null){
    		      //Erstellen der Output nodes
    		      for(int i=0;i<ki.out;i++){
    		      g.setColor(Color.getHSBColor((float)0,(float)0,(float)ki.Nodes.get(i).getValue()));
    		      g.fillRect(530, 30+i*30, 25, 25);
    		      }
    		      //Erstellen der Output nodes
    		      int correctionvalue = 0;
    		      for(int i=0;i<ki.in;i++){
    		    	  float val = (float)ki.Nodes.get(i+ki.out).getValue();
    		      g.setColor(Color.getHSBColor(val*560,val*500,val*60));
    		      g.fillRect(20+30*((i+correctionvalue)%7), 30+((i+(correctionvalue))/7)*30, 25, 25);
    		      }   
    		     // int cor = 75;
    		      for(int i=0;i<ki.hid;i++){
    		          g.setColor(Color.getHSBColor((float)0,(float)0,(float)ki.Nodes.get(i+ki.in+ki.out).getValue()));
    		          g.fillRect(300+(i/4)*30, 30+(i%4)*30, 25, 25);
    		          } 
    		      if(ki.hidlayer >1){
    		      for(int i=0;i<ki.hid;i++){
    		          g.setColor(Color.getHSBColor((float)0,(float)0,(float)ki.Nodes.get(i+ki.hid+ki.in+ki.out).getValue()));
    		          g.fillRect(400+i/4*30, 30+(i%4)*30, 25, 25);
    		          } 
    		      }
    		      
    		      
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
    		      }
    		      
    		      if(this.g!= null){
    		    	  
    		   //       this.g.generateNetwork();
    		    //List Neurons
    		          ArrayList<Double> Nodes = new ArrayList<Double>();
    		          int z = 0;
    		          int zähler = 0;
    		          for(int i = 0;i<this.g.Network.Neurons.size();i++){
    		        	  if(i<Genome.Inputs/6){
    		        		  for(z = 0;z<6;z++){
    		        			  Nodes.add(20.0+25*i);
    		    		          Nodes.add(100.0+25*z);
    		    		          Nodes.add(this.g.Network.Neurons.get(zähler++).value);
    		        	  }
    		        	  }
    		          }
    		          
    		          for(int i =0;i<this.g.Network.Neurons.size();i++){
    		        	  Neuron n = this.g.Network.Neurons.get(i);
    		        	  if(i<Genome.Inputs){

    		        	  }
    		        	  else{
    		        		  if(i==Genome.Inputs){
    		        			//Bias Cell
    		        			  Nodes.add(80.0);
    		        			  Nodes.add(450.0);
    		        			  Nodes.add(1.0);
    		        		  }
    		        		  if(i<Genome.Inputs+Genome.Outputs){
    		        			  Nodes.add(600.0);
    		        			  Nodes.add(30.0+25*(i-Genome.Inputs));
    		        			  Nodes.add(n.value);
    		        		  }
    		        		  else{
    		        			  Nodes.add(440.0);
    		        			  Nodes.add(40.0);
    		        			  Nodes.add(n.value);
    		        		  }
    		        	  
    		        	  }
    		          }

    		          

    		         

    		          
    		          int x1=0;
    		          int x2=0;
    		          int y1=0;
    		          int y2=0;
    		          for(int j =1;j<100;j++){
    		          for(int i=0;i<this.g.Genes.size();i++){
    		        	 Gene that = this.g.Genes.get(i);
    		        	  if(that != null&&that.enabled){

    		        		  //Init
    		        		  x1 = Nodes.get(that.into*3).intValue();
    		        		  x2 = Nodes.get(that.out*3).intValue();
    		        		  y1 = Nodes.get(that.into*3+1).intValue();
    		        		  y2 = Nodes.get(that.out*3+1).intValue();
    		        		 if(that.into > Genome.Inputs+Genome.Outputs){
    		        			 x1 = (int)Math.round(0.75*x1+0.25*x2);
    		        			 if(x1 >= x2){
    		        				 x1 -= 60;
    		        			 }
    		        			 if(x1 <220){
    		        				 x1 = 220;
    		        			 }
    		        			 if(x1>420){
    		        				 x1 = 420;
    		        			 }
    		        			 y1 = (int)Math.round(0.75*y1+0.25*y2);
    		        			 
    		        		 }
    		        		 
    		        		 if(that.out > Genome.Inputs+Genome.Outputs){
    		        			 x2 = (int)Math.round(0.75*x2+0.25*x1);
    		        			 if(x1 >= x2){
    		        				 x2 += 60;
    		        			 }
    		        			 if(x2 <220){
    		        				 x2 = 220;
    		        			 }
    		        			 if(x2>420){
    		        				 x2 = 420;
    		        			 }
    		        			 y2 = (int)Math.round(0.75*y2+0.25*y1);
    		        		 
    		        			 
    		        		 }
    		       		  Nodes.set(that.into*3,(double)x1);
    		       		  Nodes.set(that.out*3,(double)x2);
    		       		  Nodes.set(that.into*3+1,(double)y1);
    		       		  Nodes.set(that.out*3+1,(double)y2);
    		        		 
    		        		 }
    		        	  
    		        	  }
    		          }
    		          //Zeichnen der Nodes
    		          for(int i=0;i<Nodes.size();i=i+3){
    		        	 
    		        	  int x = Nodes.get(i).intValue();
    		        	  int y = Nodes.get(i+1).intValue();
    		        	  float value = Nodes.get(i+2).floatValue();
    		         	  g.setColor(Color.getHSBColor(value*560,value*500,value*60));
    		              g.fillRect(x-10, y-10, 20, 20);
    		        	  
    		        	  
    		          }
    		          for(int i=0;i<this.g.Genes.size();i++){
    		        	  Gene that = this.g.Genes.get(i);
    		        	  if(that.enabled){
    		        		  if(that.weigth < 0){
    		        		  g.setColor(Color.red);
    		        		  }
    		        		  if(that.weigth > 0){
    		            		  g.setColor(Color.green);
    		            		  }
    		        		  if(that.weigth == 0){
    		            		  g.setColor(Color.gray);
    		            		  }
    		    		  x1 = Nodes.get(that.into*3).intValue();
    		    		  x2 = Nodes.get(that.out*3).intValue();
    		    		  y1 = Nodes.get(that.into*3+1).intValue();
    		    		  y2 = Nodes.get(that.out*3+1).intValue();
    		        	  g.drawLine(x1, y1, x2, y2);
    		        	  }
    		          }
    		        	  
    		          
    		          if(this.g.fitness > 0)
    		          {
    		        	  g.setColor(Color.green);
    		          }
    		          if(this.g.fitness == 0)
    		          {
    		        	 g.setColor(Color.gray);
    		          }
    		          if(this.g.fitness < 0)
    		          {
    		        	  g.setColor(Color.red);
    		          }
    		          String message = "MaxNeurons: "+this.g.maxneuron+" Innovations: "+this.g.Innovation;
    		          FontMetrics fontMetrics = g.getFontMetrics();
    		          int stringWidth = fontMetrics.stringWidth(message);
    		          int stringHeight = fontMetrics.getAscent();
    		          g.drawString(message, (width - stringWidth-40) / 2, (height / 2)+150 + stringHeight / 4);
    		          message =  " Generation: "+this.generation+" Fitness: "+this.g.fitness;
    		          g.drawString(message, (width - stringWidth-40) / 2, (height / 2)+150 + stringHeight *2);
    		    	  
    		    	  
    		      }
    			

    		  
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			//e.printStackTrace();
    		}
        }
        }
       
    }

  
    
}