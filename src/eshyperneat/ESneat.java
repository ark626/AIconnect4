package eshyperneat;

import java.math.BigDecimal;
import java.util.ArrayList;

import neat.Gene;
import neat.Genome;
import neat.Neuron;
import neat.Pool;



public class ESneat {

    // Anzahl der InputNeuronen
    private int Input;

    // Anzahl der OutputNeuronen
    private int Output;

    // Anzahl der Schichten des EsNeatNetzes
    private int Hidden;
    private int x;
    private int y;
    private Neuron Neurons[];
    private ArrayList<Gene> Links;
    private Pool pool;
    private ArrayList<Integer> maxNodesPerLayer;

    public ESneat(int Input, int Output, int Hidden, int xsize, int ysize, Pool pool) {
        this.Input = Input;
        this.Output = Output;
        this.Hidden = Hidden;
        this.x = xsize;
        this.y = ysize;
        this.pool = pool;

        Output = 7;
        Input = 102+8;
        Hidden = 17;

        Neurons = new Neuron[Input + Output + Hidden];
        Links = new ArrayList<Gene>();
        for (int i = 0; i < Input + Output + Hidden; i++) {

            Neurons[i] = new Neuron();

        }

        for (int j = Input; j < Input + Hidden; j++) {

            for (int i = 0; i < Input; i++) {
                Gene g = new Gene();
                g.setOut(i);
                g.setInto(j);
                g.layer = 1;
                Neurons[j].addIncoming(g);
                Neurons[i].addOutgoing(g);
                Links.add(g);
            }
        }



        for (int j = Input + Hidden; j < Input + Hidden + Output; j++) {
            for (int i = Input; i < Input + Hidden; i++) {

                Gene g = new Gene();
                g.setOut(i);
                g.setInto(j);
                g.layer = 2;
                Neurons[j].addIncoming(g);
                Neurons[i].addOutgoing(g);
                Links.add(g);
            }

        }



    }

    public double[] step(double[] Inputs) {

        for (int inp = 0; inp < Inputs.length; inp++) {
            Neurons[inp].setValue(Inputs[inp]);
        }

        for (int n = Inputs.length; n < this.Neurons.length; n++) {
            Neuron neuron = this.Neurons[n];
            
//            if(neuron.getIncoming().size()>1){
//                neuron.setValue(neuron.getValue()/neuron.getIncoming().size());
//            }
            for (Gene g : neuron.getIncoming()) {
                
                ;
                neuron.setValue(UtilityforMath.round((g.getWeigth() * Neurons[g.getOut()].getValue()) + neuron.getValue(), 7));
            }
            if(neuron.getIncoming().size()>1){
                //double factor = 1e5;
                neuron.setValue((this.activition(10, (neuron.getValue( )))));//*factor)/factor)));
                }
        }

        double[] outputValues = new double[Output];
        for (int i = 0; i < this.Output; i++) {
            
            outputValues[i] = (this.activition(11, this.Neurons[Neurons.length - 1 - i].getValue()));
        }

        return outputValues;
    }

    public void generateweigths(Genome CPPN) {
        // Genome CPPN =
        // this.pool.Species.get(this.pool.currentSpecies-1).Genomes.get(this.pool.currentGenome-1);
        CPPN.generateNetwork();
        // int i = 0;
        for (Neuron n : Neurons) {
            n.setValue(0.0);
        }
        boolean Empty = true;

        for (Gene g : Links) {

            double[] Inputs = new double[5];
            Inputs[0] = (g.getInto() % x);
            Inputs[1] = (g.getInto() % x) * y;
            Inputs[2] = (g.getOut() % x);
            Inputs[3] = (g.getOut() % x) * y;
            Inputs[4] = g.layer;



            g.setWeigth(CPPN.step(Inputs)[0]);//.step(Inputs)[0]))));

            if (g.getWeigth() != 0.0) {
                Empty = false;
            }
            //
        }

        // System.out.println("Nodes"+CPPN.Network.Neurons.size()+" Genes "+CPPN.Genes.size());
        if (Empty) {
            CPPN.setFitness(-50);
        }
    }



    public double activition(int n, double value) {
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
        }
        return 0;
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

    public int getInput() {
        return Input;
    }

    public void setInput(int input) {
        Input = input;
    }

    public int getOutput() {
        return Output;
    }

    public void setOutput(int output) {
        Output = output;
    }

    public int getHidden() {
        return Hidden;
    }

    public void setHidden(int hidden) {
        Hidden = hidden;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Neuron[] getNeurons() {
        return Neurons;
    }

    public void setNeurons(Neuron[] neurons) {
        Neurons = neurons;
    }

    public ArrayList<Gene> getLinks() {
        return Links;
    }

    public void setLinks(ArrayList<Gene> links) {
        Links = links;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public ArrayList<Integer> getMaxNodesPerLayer() {
        return maxNodesPerLayer;
    }

    public void setMaxNodesPerLayer(ArrayList<Integer> maxNodesPerLayer) {
        this.maxNodesPerLayer = maxNodesPerLayer;
    }



}
