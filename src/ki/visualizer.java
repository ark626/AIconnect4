package ki;
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

import javax.imageio.ImageIO;


public class visualizer {
  static public void visualize(KI ki,String s) throws Exception {
    try {
      int width = 640, height = 480;

      // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
      // into integer pixels
      //Initialization
      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D ig2 = bi.createGraphics();
      Font font = new Font("TimesRoman", Font.BOLD, 20);
      ig2.setFont(font);
      
      
      //Erstellen der Output nodes
      for(int i=0;i<ki.out;i++){
      
     // if(ki.Nodes.get(i).getValue() >0){
      ig2.setColor(Color.getHSBColor((float)0,(float)0,(float)ki.Nodes.get(i).getValue()));
      //}
      ig2.fillRect(530, 30+i*30, 25, 25);
      
    //  g2d.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);
      }
      //Erstellen der Output nodes
      int correctionvalue = 0;//(ki.out%8);
//      if (correctionvalue > -1*(ki.out%8-1)){
//    	  correctionvalue = -1*correctionvalue;
//      }
      for(int i=0;i<ki.in;i++){
    	  float val = (float)ki.Nodes.get(i+ki.out).getValue();
      ig2.setColor(Color.getHSBColor(val*560,val*500,val*60));
      ig2.fillRect(20+30*((i+correctionvalue)%7), 30+((i+(correctionvalue))/7)*30, 25, 25);
    //  g2d.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);
      }   
      int cor = 75;
      for(int i=0;i<ki.hid;i++){
          ig2.setColor(Color.getHSBColor((float)0,(float)0,(float)ki.Nodes.get(i+ki.in+ki.out).getValue()));
          ig2.fillRect(300+(i/4)*30, 30+(i%4)*30, 25, 25);
        //  ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);
          } 
      if(ki.hidlayer >1){
      for(int i=0;i<ki.hid;i++){
          ig2.setColor(Color.getHSBColor((float)0,(float)0,(float)ki.Nodes.get(i+ki.hid+ki.in+ki.out).getValue()));
          ig2.fillRect(400+i/4*30, 30+(i%4)*30, 25, 25);
        //  ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);
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
    		  ig2.setPaint(Color.red);
    		  }
    		  if(that.getValue() > 0){
        		  ig2.setPaint(Color.green);
        		  }
    		  if(that.getValue() == 0){
        		  ig2.setPaint(Color.gray);
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
    	  ig2.drawLine(x1, y1, x2, y2);
      }
      if(ki.fitness > 0)
      {
    	  ig2.setPaint(Color.green);
      }
      if(ki.fitness == 0)
      {
    	  ig2.setPaint(Color.gray);
      }
      if(ki.fitness < 0)
      {
    	  ig2.setPaint(Color.red);
      }
      String message = "Tested?: "+ki.isTesting+" Genome: "+ki.gen;
      FontMetrics fontMetrics = ig2.getFontMetrics();
      int stringWidth = fontMetrics.stringWidth(message);
      int stringHeight = fontMetrics.getAscent();
      ig2.drawString(message, (width - stringWidth-40) / 2, (height / 2)+150 + stringHeight / 4);
      message =  " Generation: "+ki.generation+" Fitness: "+ki.fitness;
      ig2.drawString(message, (width - stringWidth-40) / 2, (height / 2)+150 + stringHeight *2);
      
     // ImageIO.write(bi, "PNG", new File("/tmp/"+s+".PNG"));
      ImageIO.write(bi, "PNG", new File("C:\\tmp\\"+s+".PNG"));
//      ImageIO.write(bi, "JPEG", new File("c:\\yourImageName.JPG"));
//      ImageIO.write(bi, "gif", new File("c:\\yourImageName.GIF"));
//      ImageIO.write(bi, "BMP", new File("c:\\yourImageName.BMP"));
//      
    } catch (IOException ie) {
      ie.printStackTrace();
    }

  }
}

