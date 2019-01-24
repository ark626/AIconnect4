package hyper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Genome implements Serializable, Comparable<Genome> {
    /**
     * 
     */
    private static final long serialVersionUID = -4198647103860678421L;
    public ArrayList<Gene> Genes;
    private int fitness;
    private int adjustedfitness;
    private transient Network Network;
    // public transient Pool pool;
    private int maxneuron;
    private int globalRank;
    private double[] mutationrates;
    private int Innovation = 0;
    private int Generation;
    private transient Pool parent;
    // genome.mutationRates["connections"] = MutateConnectionsChance
    // genome.mutationRates["link"] = LinkMutationChance
    // genome.mutationRates["bias"] = BiasMutationChance
    // genome.mutationRates["node"] = NodeMutationChance
    // genome.mutationRates["enable"] = EnableMutationChance
    // genome.mutationRates["disable"] = DisableMutationChance
    // genome.mutationRates["step"] = StepSize
    // public static final double Population = 300;
    // public static final double DeltaDisjoint = 2.0;
    // public static final double DeltaWeights = 0.4;
    // public static final double DeltaThreshold = 1.0;
    // public static final double StaleSpecies = 15;
    private static final double MutateConnectionsChance = 0.25;
    private static final double PerturbChance = 0.90;
    private static final double LinkMutationChance = 2.0;
    private static final double NodeMutationChance = 0.50;
    private static final double BiasMutationChance = 0.40;
    private static final double ActivitionMutationChance = 0.5;
    private static final double StepSize = 0.1;
    private static final double DisableMutationChance = 0.4;
    private static final double EnableMutationChance =  0.2;
    private int Inputs = 42;
    private int Outputs = 3;
    private static final int MaxNodes = 10000;


    public Genome(int in, int out, int gen, Pool p) {
        super();
        this.Generation = gen;
        // this.pool = p;
        this.Inputs = in;
        this.Outputs = out;
        Genes = new ArrayList<Gene>();
        this.fitness = 0;
        this.adjustedfitness = 0;
        Network = new Network();
        this.maxneuron = 0;
        this.globalRank = 0;
        this.mutationrates = new double[8];
        this.mutationrates[0] = Genome.MutateConnectionsChance;
        this.mutationrates[1] = Genome.LinkMutationChance;
        this.mutationrates[2] = Genome.BiasMutationChance;
        this.mutationrates[3] = Genome.NodeMutationChance;
        this.mutationrates[4] = Genome.EnableMutationChance;
        this.mutationrates[5] = Genome.DisableMutationChance;
        this.mutationrates[6] = Genome.StepSize;
        this.mutationrates[7] = Genome.ActivitionMutationChance;
        this.parent = p;
    }



    public static Genome basicGenome(int in, int out, Pool p) {
        Genome g = new Genome(in, out, 1, p);

        g.maxneuron = in + out;
        g.Innovation = 1;
        g.Generation = 1;
        return g;
    }

    public void setFitness(int fitness) {
        if (fitness > this.parent.maxFitness) {
            // this.parent.Species.get(parent.currentSpecies-1).staleness = 0;
            this.parent.maxFitness = fitness;
        }
        this.fitness = fitness;
        if (fitness == 0) {
            this.fitness = 1;
        }
    }

    public Genome copyGenome() {
        Genome temp = new Genome(this.Inputs, this.Outputs, this.Generation, this.parent);
        temp.Genes = new ArrayList<Gene>();
        for (Gene g : this.Genes) {
            temp.Genes.add(g.copyGene());
        }
        temp.Generation = this.Generation;
        temp.maxneuron = this.maxneuron;
        temp.mutationrates = new double[8];
        for (int i = 0; i < this.mutationrates.length; i++) {
            temp.mutationrates[i] = this.mutationrates[i];
        }
        return temp;
    }

    public void generateNetwork() {
        
        Network net = new Network();
        net.Neurons = new Neuron[MaxNodes+Outputs+Inputs];
        // Harte Knoten einfügen
        for (int i = 0; i < Inputs; i++) {

            net.Neurons[i] = new Neuron();
        }

        for (int i = 0; i < Outputs; i++) {
            net.Neurons[i+MaxNodes]= new Neuron();
        }

        if (this.Genes != null && this.Genes.size() > 1) {
            Collections.sort(this.Genes);
        }

        for(Gene gene :this.Genes){

            if (gene.isEnabled()){ 
                if(net.Neurons[gene.getOut()] == null){
                    net.Neurons[gene.getOut()] = new Neuron();
                }
                Neuron neuron = net.Neurons[gene.getOut()];
                neuron.addIncoming(gene);
                        
                if( net.Neurons[gene.getInto()] == null) {
                    net.Neurons[gene.getInto()] = new Neuron();
                }
            }
        }
        
        this.Network = net;
    
    }
    
    public double[] evaluateNetwork(double[] inputs){
        
    
   // table.insert(inputs, 1)
        
        for(int i=0;i<inputs.length;i++){
            this.Network.Neurons[i].setValue(inputs[i]);
        }
        
        for(Neuron neuron:Network.Neurons){
            if(neuron != null){
                double sum = 0;
                for(Gene gene:neuron.getIncoming()){
                    Gene incoming = gene;
                    Neuron other = Network.Neurons[incoming.getInto()];
                    sum += incoming.getWeigth()*(other.getValue());
                }
                if(neuron.getIncoming().size()>0){
                    neuron.setValue((2 / (1 + Math.exp(-4.9 * sum)) - 1) );
                }
            }
        }
        
        double[] outputs = new double[Outputs];
        for(int i = 0;i<outputs.length;i++){
//            if(Network.Neurons[MaxNodes+i].getValue()>0){
//                outputs[i] = 1.0;
//            }
//            else{
//                outputs[i] = 0.0;
//            }
            outputs[i] = Network.Neurons[MaxNodes+i].getValue();
        }
        return outputs;
    }

    /**
     * Gets the id of an Random Neuron
     * 
     * @param nonInput
     * @return
     */
    public int randomNeuron(boolean nonInput) {
       
        boolean[] neurons = new boolean[Inputs+Outputs+MaxNodes];
        if(!nonInput){
            for(int i = 0;i<Inputs;i++){
                neurons[i] = true;
            }
        }
        for(int i = 0;i<Outputs;i++){
            neurons[i+MaxNodes]= true;
        }
        
        for(int i = 0;i<this.Genes.size();i++){
            if(!nonInput || Genes.get(i).getInto() > Inputs){
            neurons[Genes.get(i).getInto()] = true;
            }
            if(!nonInput||Genes.get(i).getOut()>Inputs){
                neurons[Genes.get(i).getOut()] = true;
            }
        }
        
        Random r = new Random();
        int rand = r.nextInt(neurons.length);
        
        while(!neurons[rand]){
            rand = r.nextInt(neurons.length);
        }
        return rand;
        
    
        
    }

    public boolean containsLink(Gene g) {
        for (Gene gene : this.Genes) {
            if (gene.getInto() == g.getInto() && gene.getOut() == g.getOut()) {
                return true;
            }
        }
        return false;
    }

    public void pointMutate() {
        double step = this.mutationrates[6];
        for (Gene gene : this.Genes) {
            if (Math.random() < PerturbChance) {
                gene.setWeigth(gene.getWeigth() + Math.random() * step * 2 - step);
            } else {
                gene.setWeigth(Math.random() * 4 - 2);
            }
        }
    }

    public int linkMutate(boolean forceBias, int inovation) {

        int neuron1 = this.randomNeuron(false);
        int neuron2 = this.randomNeuron(true);

        Gene newLink = new Gene();

        if ((neuron1 < Inputs || neuron1 == Inputs + Outputs) && (neuron2 < Inputs || neuron2 == Inputs + Outputs)) {
            return 0;
        }
        if (neuron2 < Inputs || neuron2 == Inputs + Outputs) {
            int temp = neuron1;
            neuron1 = neuron2;
            neuron2 = temp;
        }
        newLink.setInto(neuron1);
        newLink.setOut(neuron2);
        if (forceBias) {
            newLink.setInto(Inputs + Outputs);
        }

        if (this.containsLink(newLink)) {
            return 0;
        }
        newLink.setInnovation(inovation++);
        newLink.setWeigth(Math.random() * 4 - 2);
        // System.out.println("From: "+newLink.out+" TO: "+newLink.into);
        this.Genes.add(newLink);
        return inovation;
    }

    public int activitionMutate(int inovation) {
        if (this.Genes.size() > 0) {
            Random rand = new Random();
            int i = rand.nextInt(this.Genes.size());
            this.Genes.get(i).setActivition(rand.nextInt(10));
            return inovation + 1;
        }
        return inovation;
    }

    public int nodeMutate(int inovation) {
        if (this.Genes.size() == 0) {
            return 0;
        }
        this.maxneuron += 1;
        Random r = new Random();
        int rand = r.nextInt(this.Genes.size());
        Gene gene = this.Genes.get(rand);

        if (!gene.isEnabled()) {
            return 0;
        }
        gene.setEnabled(false);

        Gene gene1 = gene.copyGene();
        gene1.setOut(this.maxneuron);
        gene1.setWeigth(1.0);
        gene1.setInnovation(inovation++);

        gene1.setEnabled(true);
        this.Genes.add(gene1);

        Gene gene2 = gene.copyGene();
        gene2.setInto(this.maxneuron);
        gene2.setInnovation(inovation++);
        gene2.setEnabled(true);
        this.Genes.add(gene2);
        return inovation;
    }

    public int enableDisableMutate(boolean enable) {
        ArrayList<Gene> Auswahl = new ArrayList<Gene>();
        for (Gene gene : this.Genes) {
            if (gene.isEnabled() == !enable) {
                Auswahl.add(gene);
            }
        }

        if (Auswahl.size() <= 0) {
            return 0;
        }
        Random r = new Random();
        int rand = r.nextInt(Auswahl.size());
        Gene gene = Auswahl.get(rand);
        gene.setEnabled(!enable);
        return 0;
    }



    public int mutate(int inovation) {

        // Random r = new Random();
        for (int i = 0; i < this.mutationrates.length; i++) {
            if (Math.round(Math.random()) == 1) {
                this.mutationrates[i] = this.mutationrates[i] * 0.95;
            } else {
                this.mutationrates[i] = this.mutationrates[i] * 1.05263;
            }
        }

        if (Math.random() < this.mutationrates[0]) {
            this.pointMutate();
        }

        double p = this.mutationrates[1];
        while (p > 0) {
            if (Math.random() < p) {
                inovation = this.linkMutate(false, inovation);
            }
            p -= 1;
        }

        p = this.mutationrates[2];
        while (p > 0) {
            if (Math.random() < p) {
                inovation = this.linkMutate(true, inovation);
            }
            p -= 1;
        }
        p = this.mutationrates[3];
        while (p > 0) {
            if (Math.random() < p) {
                inovation = this.nodeMutate(inovation);
            }
            p -= 1;
        }

        p = this.mutationrates[4];
        while (p > 0) {
            if (Math.random() < p) {
                this.enableDisableMutate(true);
            }
            p -= 1;
        }

        p = this.mutationrates[5];
        while (p > 0) {
            if (Math.random() < p) {
                this.enableDisableMutate(false);
            }
            p -= 1;
        }

        p = this.mutationrates[7];
        while (p > 0) {
            if (Math.random() < p) {
                inovation = this.activitionMutate(inovation);
            }
            p -= 1;
        }
        this.Innovation = inovation;
        return inovation;
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
        return value;
    }



    public int compareTo(Genome arg0) {

        
        return Comparators.DESCENDING.compare(this, arg0);//(int) (((Genome) arg0).fitness - this.fitness);
    }
    
    public static class Comparators {

        public static Comparator<Genome> ASCENDING = new Comparator<Genome>() {

            public int compare(Genome o1, Genome o2) {
                return o2.fitness-(o1.fitness);
            }
        };
        public static Comparator<Genome> DESCENDING = new Comparator<Genome>() {

            public int compare(Genome o1, Genome o2) {
                return o1.fitness-(o2.fitness);
            }
        };

       
    
}



    public ArrayList<Gene> getGenes() {
        return Genes;
    }



    public void setGenes(ArrayList<Gene> genes) {
        Genes = genes;
    }



    public int getAdjustedfitness() {
        return adjustedfitness;
    }



    public void setAdjustedfitness(int adjustedfitness) {
        this.adjustedfitness = adjustedfitness;
    }



    public Network getNetwork() {
        return Network;
    }



    public void setNetwork(Network network) {
        Network = network;
    }



    public int getMaxneuron() {
        return maxneuron;
    }



    public void setMaxneuron(int maxneuron) {
        this.maxneuron = maxneuron;
    }



    public int getGlobalRank() {
        return globalRank;
    }



    public void setGlobalRank(int globalRank) {
        this.globalRank = globalRank;
    }



    public double[] getMutationrates() {
        return mutationrates;
    }



    public void setMutationrates(double[] mutationrates) {
        this.mutationrates = mutationrates;
    }



    public int getInnovation() {
        return Innovation;
    }



    public void setInnovation(int innovation) {
        Innovation = innovation;
    }



    public int getGeneration() {
        return Generation;
    }



    public void setGeneration(int generation) {
        Generation = generation;
    }



    public Pool getParent() {
        return parent;
    }



    public void setParent(Pool parent) {
        this.parent = parent;
    }



    public int getInputs() {
        return Inputs;
    }



    public void setInputs(int inputs) {
        Inputs = inputs;
    }



    public int getOutputs() {
        return Outputs;
    }



    public void setOutputs(int outputs) {
        Outputs = outputs;
    }



    public int getFitness() {
        return fitness;
    }


}
