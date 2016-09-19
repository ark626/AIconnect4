package neat;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class visualizer {
  static public void visualize(Genome g,String s) throws Exception {
    try {
      int width = 640, height = 480;
      if(g.Network == null||g.Network.Neurons.size() ==0){
      g.generateNetwork();
      }

      // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
      // into integer pixels
      //Initialization
      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D ig2 = bi.createGraphics();
      Font font = new Font("TimesRoman", Font.BOLD, 20);
      ig2.setFont(font);
      
      
      ArrayList<Double> Nodes = new ArrayList<Double>();
      int z = 0;
      int zähler = 0;
      int ct = 0;
      for(int i = 0;i<g.Network.Neurons.size();i++){
    	  if(i<g.Inputs/6||g.Inputs == 4&&i<g.Inputs){
    		  
    		  for(z = 0;z<6;z++){
    			  if(ct == 4&&g.Inputs == 4){
		        	  break;
		          }
    			  Nodes.add(20.0+25*i);
		          Nodes.add(100.0+25*z);
		          Nodes.add(g.Network.Neurons.get(zähler++).value);
		          ct++;
		          
    	  }
    	  }
      }
   //   System.out.println(g.Network.Neurons.size());
      for(int i =0;i<g.Network.Neurons.size();i++){
    	  Neuron n = g.Network.Neurons.get(i);
    	  if(i<g.Inputs){
//    		  if(g.Inputs <6){
//    			  Nodes.add(20.0+25*i);
//		          Nodes.add(100.0+25*1);
//		          Nodes.add(g.Network.Neurons.get(i).value);
//    			  
//    		  }

    	  }
    	  else{
    		  if(i<g.Inputs+g.Outputs){
    			  Nodes.add(600.0);
    			  Nodes.add(30.0+25*(i-g.Inputs));
    			  Nodes.add(n.value);
    		  }
    		  else{
        		  if(i==g.Inputs+g.Outputs){
          			//Bias Cell
          			  Nodes.add(80.0);
          			  Nodes.add(450.0);
          			  Nodes.add(n.value);
          		  }
        		  else{
    			  Nodes.add(440.0);
    			  Nodes.add(40.0);
    			  Nodes.add(n.value);
        		  }
    		  }
    	  
    	  }
      }


      

    
      

      
      int x1=0;
      int x2=0;
      int y1=0;
      int y2=0;
      for(int j =1;j<4;j++){
      for(int i=0;i<g.Genes.size();i++){
    	 Gene that = g.Genes.get(i);
    	  if(that != null&&that.enabled){

    		  //Init
    //		  System.out.println(that.into+" "+g.Genes.size()+" "+g.Network.Neurons.size());
    		  x1 = Nodes.get(that.into*3).intValue();
    		  x2 = Nodes.get(that.out*3).intValue();
    		  y1 = Nodes.get(that.into*3+1).intValue();
    		  y2 = Nodes.get(that.out*3+1).intValue();
    		 if(that.into > g.Inputs+g.Outputs){
    			 x1 = (int)Math.round(0.75*x1+0.25*x2);
    			 if(x1 >= x2){
    				 x1 -= 60;
    			 }
    			 if(x1 <280){
    				 x1 = 280;
    			 }
    			 if(x1>550){
    				 x1 = 550;
    			 }
    			 y1 = (int)Math.round(0.75*y1+0.25*y2);
    			 
    		 }
    		 
    		 if(that.out > g.Inputs+g.Outputs){
    			 x2 = (int)Math.round(0.75*x2+0.25*x1);
    			 if(x1 >= x2){
    				 x2 += 60;
    			 }
    			 if(x2 <280){
    				 x2 = 280;
    			 }
    			 if(x2>550){
    				 x2 = 550;
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
     	  ig2.setColor(Color.getHSBColor(1, 2, value));
     	  if(x==440&&y==40){
     		  //Ignore not used nodes;
         	  }
         	  else{
          ig2.fillRect(x-10, y-10, 20, 20);
         	  }
    	  
    	  
      }
      for(int i=0;i<g.Genes.size();i++){
    	  Gene that = g.Genes.get(i);
    	  if(that.enabled){
    		  if(that.enabled&&that.weigth != 0.0){
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
		  Composite temp = ig2.getComposite();
		  
		  if(that.weigth < 0 && that.weigth >= -1){
			  ig2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, -(float)that.weigth));
		  }
		  if(that.weigth > 0&& that.weigth <= 1){
			  ig2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)that.weigth)); 
		  }
		  if(that.weigth != 0.0){
    	  ig2.drawLine(x1, y1, x2, y2);
		  }
    	  ig2.setComposite(temp);
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
      message =  " Generation: "+g.Generation+" Fitness: "+g.fitness;
      ig2.drawString(message, (width - stringWidth-40) / 2, (height / 2)+150 + stringHeight *2);
      String se = s.substring(33, s.length());
      message = se;
      ig2.drawString(message, (width - stringWidth-40) / 2, (height / 2)+150 - stringHeight );
      ig2.drawString("Inputs",20,20);
      ig2.drawString("Hidden", 210, 20);
      ig2.drawRect(270, 20, 560-270, 25*13);
      ig2.drawRect(20-15, 100-15, 25*10-10, 25*9);
//      Image b  = bi.getScaledInstance(1280, 960, BufferedImage.SCALE_FAST);
//      bi = new BufferedImage(1280,960,BufferedImage.TYPE_INT_ARGB);
//      bi.getGraphics().drawImage(b, 0, 0, null);
      File f = new File(s+".PNG");
      String pa = f.getAbsolutePath();
      ImageIO.write(bi, "PNG", new File(pa));
//      ImageIO.write(bi, "JPEG", new File("c:\\yourImageName.JPG"));
//      ImageIO.write(bi, "gif", new File("c:\\yourImageName.GIF"));
//      ImageIO.write(bi, "BMP", new File("c:\\yourImageName.BMP"));
//      
    } catch (IOException ie) {
      ie.printStackTrace();
    }

  }
}

