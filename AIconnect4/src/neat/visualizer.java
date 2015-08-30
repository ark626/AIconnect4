package neat;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class visualizer {
  static public void visualize(Genome g,int generation,String s) throws Exception {
    try {
      int width = 640, height = 480;
      g.generateNetwork();

      // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
      // into integer pixels
      //Initialization
      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D ig2 = bi.createGraphics();
      Font font = new Font("TimesRoman", Font.BOLD, 20);
      ig2.setFont(font);
      
      
//List Neurons
      ArrayList<Double> Nodes = new ArrayList<Double>();
      int z = 0;
      for(int i = 0;i<g.Network.Neurons.size();i++){
    	  if(i<Genome.Inputs/7){
    	  for(z = 0;z<7;z++){
		  Nodes.add(20.0+25*z);
		  Nodes.add(100.0+25*((i*7+z)%6));

		  Nodes.add(g.Network.Neurons.get(i*7+z).value);
    	  }
    	  }
      }
      
      for(int i =0;i<g.Network.Neurons.size();i++){
    	  Neuron n = g.Network.Neurons.get(i);
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
      for(int i=0;i<g.Genes.size();i++){
    	 Gene that = g.Genes.get(i);
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
    //  System.out.println(" Test "+Nodes.size()+" "+Nodes.size()/3);
      for(int i=0;i<Nodes.size();i=i+3){
    	 
    	  int x = Nodes.get(i).intValue();
    	  int y = Nodes.get(i+1).intValue();
    	  float value = Nodes.get(i+2).floatValue();
     	  ig2.setColor(Color.getHSBColor(value*560,value*500,value*60));
          ig2.fillRect(x-10, y-10, 20, 20);
    	  
    	  
      }
      for(int i=0;i<g.Genes.size();i++){
    	  Gene that = g.Genes.get(i);
    	  if(that.enabled){
    		  if(that.weigth < 0){
    		  ig2.setPaint(Color.red);
    		  }
    		  if(that.weigth > 0){
        		  ig2.setPaint(Color.green);
        		  }
    		  if(that.weigth == 0){
        		  ig2.setPaint(Color.gray);
        		  }
		  x1 = Nodes.get(that.into*3).intValue();
		  x2 = Nodes.get(that.out*3).intValue();
		  y1 = Nodes.get(that.into*3+1).intValue();
		  y2 = Nodes.get(that.out*3+1).intValue();
    	  ig2.drawLine(x1, y1, x2, y2);
    	  }
      }
    	  
      
      if(g.fitness > 0)
      {
    	  ig2.setPaint(Color.green);
      }
      if(g.fitness == 0)
      {
    	  ig2.setPaint(Color.gray);
      }
      if(g.fitness < 0)
      {
    	  ig2.setPaint(Color.red);
      }
      String message = "MaxNeurons: "+g.maxneuron+" Innovations: "+g.Innovation;
      FontMetrics fontMetrics = ig2.getFontMetrics();
      int stringWidth = fontMetrics.stringWidth(message);
      int stringHeight = fontMetrics.getAscent();
      ig2.drawString(message, (width - stringWidth-40) / 2, (height / 2)+150 + stringHeight / 4);
      message =  " Generation: "+generation+" Fitness: "+g.fitness;
      ig2.drawString(message, (width - stringWidth-40) / 2, (height / 2)+150 + stringHeight *2);
      
      ImageIO.write(bi, "PNG", new File("C:/tmp/"+s+".PNG"));
//      ImageIO.write(bi, "JPEG", new File("c:\\yourImageName.JPG"));
//      ImageIO.write(bi, "gif", new File("c:\\yourImageName.GIF"));
//      ImageIO.write(bi, "BMP", new File("c:\\yourImageName.BMP"));
//      
    } catch (IOException ie) {
      ie.printStackTrace();
    }

  }
}

