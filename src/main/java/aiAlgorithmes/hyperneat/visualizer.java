package aiAlgorithmes.hyperneat;

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

import aiAlgorithmes.neat.Gene;
import aiAlgorithmes.neat.Neuron;




	public class visualizer {
	  static public void visualize(HyperNeat g,String s,int Spec, int Geno) throws Exception {
	    try {
	      int width = 640, height = 480;


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
	      double cor = 0;
	      for(int i = 0;i<g.getNeurons().length;i++){
	    	  if(i<(g.getInput()*2)/6){
	    		  if(i>=g.getInput()){
	    			  cor = 50.0;
	    		  }
	    		  for(z = 0;z<6;z++){
	    			  Nodes.add(20.0+35*i+cor);
			          Nodes.add(100.0+35*z);
			          Nodes.add(g.getNeurons()[zähler++].getValue());
	    	  }
	    	  }
	      }
	   //   System.out.println(g.Network.getNeurons().size());
	      for(int i =0;i<g.getNeurons().length;i++){
	    	  Neuron n = g.getNeurons()[i];
	    	  if(i<g.getInput()*2){


	    	  }
	    	  else{
	    		  if(i<=g.getInput()*2+g.getOutput()){
	    			  Nodes.add(600.0);
	    			  Nodes.add(30.0+25*(i-g.getInput()*2));
	    			  Nodes.add(n.getValue());
	    		  }
	    		  else{
//	        		  if(i==g.getInput()+g.getOutput()){
//	          			//Bias Cell
//	          			  Nodes.add(80.0);
//	          			  Nodes.add(450.0);
//	          			  Nodes.add(n.value);
//	          		  }
//	        		  else{
	    			  Nodes.add(440.0);
	    			  Nodes.add(40.0);
	    			  Nodes.add(n.getValue());
//	        		  }
	    		  }
	    	  
	    	  }
	      }


	      

	      

	      
	      int x1=0;
	      int x2=0;
	      int y1=0;
	      int y2=0;
	      for(int j =1;j<4;j++){
	      for(int i=0;i<g.getLinks().size();i++){
	    	 Gene that = g.getLinks().get(i);
	    	  if(that != null&&that.isEnabled()){

	    		  //Init
	    	//	  System.out.println(that.into+" "+g.Links.size()+" "+g.getNeurons().length);
	    		  x1 = Nodes.get(that.getInto()*3).intValue();
	    		  x2 = Nodes.get(that.getOut()*3).intValue();
	    		  y1 = Nodes.get(that.getInto()*3+1).intValue();
	    		  y2 = Nodes.get(that.getOut()*3+1).intValue();
	    		 if(that.getInto() > g.getInput()*2+g.getOutput()){
	    			 x1 = (int)Math.round(0.75*x1+0.25*x2);
	    			 if(x1 >= x2){
	    				 x1 -= 60;
	    			 }
	    			 if(x1 <220){
	    				 x1 = 220;
	    			 }
	    			 if(x1>550){
	    				 x1 = 550;
	    			 }
	    			 y1 = (int)Math.round(0.75*y1+0.25*y2);
	    			 
	    		 }
	    		 
	    		 if(that.getOut() > g.getInput()*2+g.getOutput()){
	    			 x2 = (int)Math.round(0.75*x2+0.25*x1);
	    			 if(x1 >= x2){
	    				 x2 += 60;
	    			 }
	    			 if(x2 <220){
	    				 x2 = 220;
	    			 }
	    			 if(x2>550){
	    				 x2 = 550;
	    			 }
	    			 y2 = (int)Math.round(0.75*y2+0.25*y1);
	    		 
	    			 
	    		 }
	   		  Nodes.set(that.getInto()*3,(double)x1);
	   		  Nodes.set(that.getOut()*3,(double)x2);
	   		  Nodes.set(that.getInto()*3+1,(double)y1);
	   		  Nodes.set(that.getOut()*3+1,(double)y2);
	    		 
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
	      for(int i=0;i<g.getLinks().size();i++){
	    	  Gene that = g.getLinks().get(i);
	    	  Composite temp = ig2.getComposite();
	    	  if(that.isEnabled()){
	    		  if(that.isEnabled()&&that.getWeigth() != 0.0){
	    			  
	    		  ig2.setPaint(Color.red);
	    		  }
	    		  if(that.getWeigth() > 0){
	        		  ig2.setPaint(Color.green);
	        		  }
	    		  if(that.getWeigth() == 0){
	        		  ig2.setPaint(Color.gray);
	        		  }
			  x1 = Nodes.get(that.getInto()*3).intValue();
			  x2 = Nodes.get(that.getOut()*3).intValue();
			  y1 = Nodes.get(that.getInto()*3+1).intValue();
			  y2 = Nodes.get(that.getOut()*3+1).intValue();
			  if(that.getWeigth() < 0&& that.getWeigth() >= -1){
				  ig2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, -(float)that.getWeigth()));
			  }
			  if(that.getWeigth() > 0&& that.getWeigth() <= 1){
				  ig2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)that.getWeigth())); 
			  }
			  if(that.getWeigth() != 0.0){
	    	
				  if(that.getInto() <g.getInput()){//42
	    	  ig2.drawRect((x1-10)+(that.getOut())%7*3, (y1-10)+(that.getOut()/7)%6*3, 1, 1);
				  }
				  else{
					  ig2.drawLine(x1, y1, x2, y2);
				  }
			  }
	    	  ig2.setComposite(temp);
	    	  }
	      }
	    	  
	      
	      if(g.getPool().Species.get(Spec).Genomes.get(Geno).getFitness() > 0)
	      {
	    	  ig2.setPaint(Color.green);
	      }
	      if(g.getPool().Species.get(Spec).Genomes.get(Geno).getFitness() == 0)
	      {
	    	  ig2.setPaint(Color.gray);
	      }
	      if(g.getPool().Species.get(Spec).Genomes.get(Geno).getFitness() < 0)
	      {
	    	  ig2.setPaint(Color.red);
	      }
	      String message = "MaxNeurons: "+g.getPool().Species.get(Spec).Genomes.get(Geno).getMaxneuron()+" Innovations: "+g.getPool().Species.get(Spec).Genomes.get(Geno).getInnovation();
	      FontMetrics fontMetrics = ig2.getFontMetrics();
	      int stringWidth = fontMetrics.stringWidth(message);
	      int stringHeight = fontMetrics.getAscent();
	      ig2.drawString(message, (width - stringWidth-40) / 2, (height / 2)+150 + stringHeight / 4);
	      message =  " Generation: "+g.getPool().Species.get(Spec).Genomes.get(Geno).getGeneration()+" Fitness: "+g.getPool().Species.get(Spec).Genomes.get(Geno).getFitness();
	      ig2.drawString(message, (width - stringWidth-40) / 2, (height / 2)+150 + stringHeight *2);
	      String se = s.substring(24, s.length());
	      message = se;
	      ig2.drawString(message, (width - stringWidth-40) / 2, (height / 2)+150 - stringHeight );
	      ig2.drawString("Inputs",20,20);
	      ig2.drawString("Hidden", 25*10-5, 20);
	      ig2.drawRect(25*10, 100-15, 25*10-5, 25*9);
	      ig2.drawRect(20-15, 100-15, 25*10-10, 25*9);
//	      Image b  = bi.getScaledInstance(1280, 960, BufferedImage.SCALE_FAST);
//	      bi = new BufferedImage(1280,960,BufferedImage.TYPE_INT_ARGB);
//	      bi.getGraphics().drawImage(b, 0, 0, null);
	      ImageIO.write(bi, "PNG", new File(s+".PNG"));
//	      ImageIO.write(bi, "JPEG", new File("c:\\yourImageName.JPG"));
//	      ImageIO.write(bi, "gif", new File("c:\\yourImageName.GIF"));
//	      ImageIO.write(bi, "BMP", new File("c:\\yourImageName.BMP"));
//	      
	    } catch (IOException ie) {
	      ie.printStackTrace();
	    }

	  }
	}



