package aiAlgorithmes.hyperneat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import aiAlgorithmes.neat.Gene;
import aiAlgorithmes.neat.Neuron;
import aiAlogrithmes.ki.KI;

final public class gameDisplay {

    public JFrame frame;
    public DrawPanel drawPanel;

    public gameDisplay re(gameDisplay gold) {
        gameDisplay g = new gameDisplay();
        g.frame = gold.frame;
        g.drawPanel = new DrawPanel(gold.drawPanel.ki);
        // g.go(g.drawPanel.ki);
        // frame.getContentPane().remove(gold.drawPanel);
        // frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        return g;
    }

    public void refresh() {
        // JPanel contentPane = (JPanel) frame.getContentPane();
        // contentPane.removeAll();
        // contentPane.add(this.drawPanel);
        // contentPane.revalidate();
        // contentPane.repaint();

    }

    public static gameDisplay main(KI ki) {
        gameDisplay g = new gameDisplay();
        g.go(ki);
        return g;
    }

    public static gameDisplay main(HyperNeat g, int generation) {
        gameDisplay z = new gameDisplay();
        z.go(g, generation);
        return z;
    }

    public void update() {
        // this.frame.
        this.drawPanel.getGraphics().dispose();
        this.drawPanel.update(this.drawPanel.getGraphics());
        // this.drawPanel.updateUI();
    }

    private void go(KI ki) {
        // if(frame == null){
        frame = new JFrame("KI Nodes");
        frame.setSize(640, 480);
        // }
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel(ki);

        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setResizable(false);
        // frame.setSize(300, 300);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        // moveIt();
    }

    private void go(HyperNeat g, int generation) {
        // if(frame == null){
        frame = new JFrame("KI Nodes");
        frame.setSize(640, 480);
        // }
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel(g, generation);

        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setResizable(false);
        // frame.setSize(300, 300);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        // moveIt();
    }

    public class DrawPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        public KI ki;
        public HyperNeat g;
        public int generation;
        public boolean update;

        public DrawPanel(KI k) {
            super();
            this.ki = k;
        }

        public DrawPanel(HyperNeat g, int generation) {
            super();
            this.ki = null;
            this.g = g;
            this.generation = generation;


        }

        public void refresh() {

        }


        public void paintComponent(Graphics g) {
            // KI ki = this.k;

            if (!this.update) {
                g.clearRect(0, 0, getWidth(), getHeight());
                int width = 640, height = 480;
                try {
                    Font font = new Font("TimesRoman", Font.BOLD, 20);
                    g.setFont(font);


                    ArrayList<Double> Nodes = new ArrayList<Double>();
                    int z = 0;
                    int zähler = 0;
                    double cor = 0;
                    for (int i = 0; i < this.g.getNeurons().length; i++) {
                        if (i < (this.g.getInput() * 2) / 6) {
                            if (i >= this.g.getInput()) {
                                cor = 50.0;
                            }
                            for (z = 0; z < 6; z++) {
                                Nodes.add(20.0 + 35 * i + cor);
                                Nodes.add(100.0 + 35 * z);
                                Nodes.add(this.g.getNeurons()[zähler++].getValue());
                            }
                        }
                    }
                    // System.out.println(g.Network.Neurons.size());
                    for (int i = 0; i < this.g.getNeurons().length; i++) {
                        Neuron n = this.g.getNeurons()[i];
                        if (i < this.g.getInput() * 2) {


                        } else {
                            if (i <= this.g.getInput() * 2 + this.g.getOutput()) {
                                Nodes.add(600.0);
                                Nodes.add(30.0 + 25 * (i - this.g.getInput() * 2));
                                Nodes.add(n.getValue());
                            } else {
                                // if(i==g.Input+g.Output){
                                // //Bias Cell
                                // Nodes.add(80.0);
                                // Nodes.add(450.0);
                                // Nodes.add(n.value);
                                // }
                                // else{
                                Nodes.add(440.0);
                                Nodes.add(40.0);
                                Nodes.add(n.getValue());
                                // }
                            }

                        }
                    }



                    int x1 = 0;
                    int x2 = 0;
                    int y1 = 0;
                    int y2 = 0;
                    for (int j = 1; j < 4; j++) {
                        for (int i = 0; i < this.g.getLinks().size(); i++) {
                            Gene that = this.g.getLinks().get(i);
                            if (that != null && that.isEnabled()) {

                                // Init
                                // System.out.println(that.into+" "+g.Links.size()+"
                                // "+g.Neurons.length);
                                x1 = Nodes.get(that.getInto() * 3).intValue();
                                x2 = Nodes.get(that.getOut() * 3).intValue();
                                y1 = Nodes.get(that.getInto() * 3 + 1).intValue();
                                y2 = Nodes.get(that.getOut() * 3 + 1).intValue();
                                if (that.getInto() > this.g.getInput() * 2 + this.g.getOutput()) {
                                    x1 = (int) Math.round(0.75 * x1 + 0.25 * x2);
                                    if (x1 >= x2) {
                                        x1 -= 60;
                                    }
                                    if (x1 < 220) {
                                        x1 = 220;
                                    }
                                    if (x1 > 550) {
                                        x1 = 550;
                                    }
                                    y1 = (int) Math.round(0.75 * y1 + 0.25 * y2);

                                }

                                if (that.getOut() > this.g.getInput() * 2 + this.g.getOutput()) {
                                    x2 = (int) Math.round(0.75 * x2 + 0.25 * x1);
                                    if (x1 >= x2) {
                                        x2 += 60;
                                    }
                                    if (x2 < 220) {
                                        x2 = 220;
                                    }
                                    if (x2 > 550) {
                                        x2 = 550;
                                    }
                                    y2 = (int) Math.round(0.75 * y2 + 0.25 * y1);


                                }
                                Nodes.set(that.getInto() * 3, (double) x1);
                                Nodes.set(that.getOut() * 3, (double) x2);
                                Nodes.set(that.getInto() * 3 + 1, (double) y1);
                                Nodes.set(that.getOut() * 3 + 1, (double) y2);

                            }

                        }
                    }
                    // Zeichnen der Nodes
                    // System.out.println(" Test "+Nodes.size()+" "+Nodes.size()/3);
                    for (int i = 0; i < Nodes.size(); i = i + 3) {

                        int x = Nodes.get(i).intValue();
                        int y = Nodes.get(i + 1).intValue();
                        float value = Nodes.get(i + 2).floatValue();
                        g.setColor(Color.getHSBColor(1, 2, value));
                        if (x == 440 && y == 40) {
                            // Ignore not used nodes;
                        } else {
                            g.fillRect(x - 10, y - 10, 20, 20);
                        }


                    }
                    for (int i = 0; i < this.g.getLinks().size(); i++) {
                        Gene that = this.g.getLinks().get(i);
                        // Composite temp = g.getComposite();
                        if (that.isEnabled()) {
                            if (that.isEnabled() && that.getWeigth() != 0.0) {

                                g.setColor(Color.red);
                            }
                            if (that.getWeigth() > 0) {
                                g.setColor(Color.green);
                            }
                            if (that.getWeigth() == 0) {
                                g.setColor(Color.gray);
                            }
                            x1 = Nodes.get(that.getInto() * 3).intValue();
                            x2 = Nodes.get(that.getOut() * 3).intValue();
                            y1 = Nodes.get(that.getInto() * 3 + 1).intValue();
                            y2 = Nodes.get(that.getOut() * 3 + 1).intValue();
                            if (that.getWeigth() < 0 && that.getWeigth() >= -1) {
                                // g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                // -(float)that.weigth));
                            }
                            if (that.getWeigth() > 0 && that.getWeigth() <= 1) {
                                // ig2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                // (float)that.weigth));
                            }
                            if (that.getWeigth() != 0.0) {

                                if (that.getInto() < this.g.getInput()) {// 42
                                    g.drawRect((x1 - 10) + (that.getOut()) % 7 * 3, (y1 - 10) + (that.getOut() / 7) % 6 * 3, 1, 1);
                                } else {
                                    g.drawLine(x1, y1, x2, y2);
                                }
                            }
                            // ig2.setComposite(temp);
                        }
                    }


                    if (this.g.getPool().Species.get(this.g.getPool().currentSpecies - 1).Genomes.get(this.g.getPool().currentGenome - 1).getFitness() > 0) {
                        g.setColor(Color.green);
                    }
                    if (this.g.getPool().Species.get(this.g.getPool().currentSpecies - 1).Genomes.get(this.g.getPool().currentGenome - 1).getFitness() == 0) {
                        g.setColor(Color.gray);
                    }
                    if (this.g.getPool().Species.get(this.g.getPool().currentSpecies - 1).Genomes.get(this.g.getPool().currentGenome - 1).getFitness() < 0) {
                        g.setColor(Color.red);
                    }
                    String message = "MaxNeurons: " + this.g.getPool().Species.get(this.g.getPool().currentSpecies - 1).Genomes.get(this.g.getPool().currentGenome - 1).getMaxneuron()
                            + " Innovations: " + this.g.getPool().Species.get(this.g.getPool().currentSpecies - 1).Genomes.get(this.g.getPool().currentGenome - 1).getInnovation();
                    FontMetrics fontMetrics = g.getFontMetrics();
                    int stringWidth = fontMetrics.stringWidth(message);
                    int stringHeight = fontMetrics.getAscent();
                    g.drawString(message, (width - stringWidth - 40) / 2, (height / 2) + 150 + stringHeight / 4);
                    message = " Generation: " + this.g.getPool().Species.get(this.g.getPool().currentSpecies - 1).Genomes.get(this.g.getPool().currentGenome - 1).getGeneration() + " Fitness: "
                            + this.g.getPool().Species.get(this.g.getPool().currentSpecies - 1).Genomes.get(this.g.getPool().currentGenome - 1).getFitness();
                    g.drawString(message, (width - stringWidth - 40) / 2, (height / 2) + 150 + stringHeight * 2);
                    // String se = s.substring(24, s.length());
                    // message = se;
                    // ig2.drawString(message, (width - stringWidth-40) / 2, (height / 2)+150 -
                    // stringHeight );
                    g.drawString("Inputs", 20, 20);
                    g.drawString("Hidden", 25 * 10 - 5, 20);
                    g.drawRect(25 * 10, 100 - 15, 25 * 10 - 5, 25 * 9);
                    g.drawRect(20 - 15, 100 - 15, 25 * 10 - 10, 25 * 9);


                }



                catch (Exception e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace(

                }

            }

        }
    }
}
