package neat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import tools.EnumMath;

public class Genome implements Serializable, Comparable<Genome> {
    private static final int FUNCTIONSOFNODES = 15;
    /**
     * 
     */
    private static final long serialVersionUID = -4198647103860678421L;
    public transient ArrayList<Gene> Genes;
    private long fitness;
    private int adjustedfitness;
    private transient Network Network;
    // public transient Pool pool;
    private int maxneuron;
    private int globalRank;
    private double[] mutationrates;
    private int Innovation = 0;
    private int Generation;
    private transient Pool parent;
    private static transient double NodeShift = 0;
    private static transient double NodeSlope = 1;
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
    private static final double ActivitionMutationChance = 0.0125; //0.5
    private static final double StepSize = 0.1;
    private static final double DisableMutationChance = 0.4;
    private static final double EnableMutationChance = 0.2;
    private int Inputs = 42;
    private int Outputs = 3;
    // private static final int MaxNodes = 10000;



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
        this.globalRank = -1;
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

    /**
     * Just for TEsting purposes
     * 
     * @param in
     * @param out
     * @param gen
     */
    public Genome(int in, int out, int gen) {
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
    }



    public static Genome basicGenome(int in, int out, Pool p) {
        Genome g = new Genome(in, out, 1, p);

        g.maxneuron = in + out;
        g.Innovation = 1;
        g.Generation = 1;
        return g;
    }

    public void setFitness(long fitness) {
        if (parent != null && fitness > this.parent.maxFitness) {
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
        Genome g = this;
        Network net = new Network();
        // Harte Knoten einfügen
        for (int i = 0; i < Inputs; i++) {

            net.Neurons.add(new Neuron());
        }

        for (int i = 0; i < Outputs; i++) {
            net.Neurons.add(new Neuron());
        }

        if (g.Genes != null && g.Genes.size() > 1) {
            Collections.sort(g.Genes);
        }
        boolean Check = true;
        
        for (Gene ge : g.Genes) {
            if (ge.isEnabled()) {
                boolean checker = false;
                while (net.Neurons.size() <= ge.getOut()) {
                    checker = true;
                    net.Neurons.add(new Neuron());
                    net.Neurons.get(net.Neurons.size() - 1)
                            .setActive(false);
//                    if (ge.getActivition() != 0) {
//                        net.Neurons.get(net.Neurons.size() - 1)
//                                .setActivition(ge.getActivition());
//                    }
                }
                net.Neurons.get(net.Neurons.size() - 1)
                        .setActive(true);
                net.Neurons.get(ge.getOut())
                        .addIncoming(ge);
                net.Neurons.get(ge.getOut()).setActivition(ge.getActivition());
                checker = false;
                while (net.Neurons.size() <= ge.getInto()) {
                    checker = true;
                    net.Neurons.add(new Neuron());
                    net.Neurons.get(net.Neurons.size() - 1)
                            .setActive(false);
                }
                net.Neurons.get(net.Neurons.size() - 1)
                        .setActive(true);
                // if(ge.out == Inputs+Outputs){
                // net.Neurons.get(Inputs+Outputs).value = 1.0;
                // }
                if (ge.getWeigth() != 0.0) {
                    Check = false;
                }
            }
        }
        if (Check) {
            // this.fitness = -9999;

        }
        g.Network = net;

    }


    /**
     * Gets the id of an Random Neuron
     * 
     * @param nonInput
     * @return
     */
    public int randomNeuron(boolean nonInput) {
        ArrayList<Integer> Neurons = new ArrayList<Integer>();

        if (nonInput) {

            for (int i = 0; i < Inputs; i++) {
                Neurons.add(i);
            }
        }

        for (int i = Inputs; i < Inputs + Outputs + 1; i++) {
            Neurons.add(i);
        }

        for (int i = 0; i < this.Genes.size(); i++) {
            if (!nonInput || this.Genes.get(i)
                    .getInto() > Inputs
                    && this.Genes.get(i)
                            .getOut() != Inputs + Outputs) {
                Neurons.add(
                        this.Genes.get(i)
                                .getInto());


            }
            if (!nonInput || this.Genes.get(i)
                    .getOut() >= Inputs
                    && this.Genes.get(i)
                            .getOut() != Inputs + Outputs) {
                Neurons.add(
                        this.Genes.get(i)
                                .getOut());
            }
        }

        int count = Neurons.size();
        Random r = new Random();
        int rand = r.nextInt(count);
        // System.out.println("Randnode: "+rand);
        return Neurons.get(rand);
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
        Random rand = new Random();
        newLink.setActivition(rand.nextInt(EnumMath.values().length));
        newLink.setWeigth(Math.random() * 4 - 2);
        // System.out.println("From: "+newLink.out+" TO: "+newLink.into);
        this.Genes.add(newLink);
        return inovation;
    }

    public int activitionMutate(int inovation) {
        if (this.Genes.size() > 0) {
            Random rand = new Random();
            int i = rand.nextInt(this.Genes.size());
            this.Genes.get(i)
                    .setActivition(rand.nextInt(EnumMath.values().length));
           //this.Genes.get(i).getOut()
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

    public double[] step(double[] Inputs) {
        return this.step(Inputs, 13);

    }


    public double[] step(double[] Inputs, int act) {
        // Inputs im Netzwerk setzen
        int z = 0;
        for (double i : Inputs) {
            this.Network.Neurons.get(z)
                    .setValue(i);
            z++;
        }
        // Biased Cell
        if (this.Network.Neurons.size() > Outputs + this.Inputs) {
            this.Network.Neurons.get(this.Inputs + Outputs)
                    .setValue(1.0);
        }

        for (Neuron n : this.Network.Neurons) {
            double sum = 0;

            for (Gene g : n.getIncoming()) {
                sum += g.getWeigth() * this.Network.Neurons.get(g.getInto())
                        .getValue();

            }

            if (n.getIncoming()
                    .size() > 1) {
                n.setValue((tools.MathLib.newAcitvation(EnumMath.values()[n.getActivition()], sum, NodeSlope, NodeShift)));
                // n.setValue((tools.MathLib.activition(n.getActivition(), (sum))));
            }

            // }

        }

        double[] Output = new double[this.Outputs];
        for (int i = 0; i < this.Outputs; i++) {
            Output[i] = this.Network.Neurons.get(this.Inputs + i)
                    .getValue();
        }
        return Output;



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



    public long getFitness() {
        return fitness;
    }



    public int compareTo(Genome arg0) {


        return Comparators.DESCENDING.compare(this, arg0);// (int) (((Genome) arg0).fitness -
                                                          // this.fitness);
    }

    public static class Comparators {

        public static Comparator<Genome> DESCENDING = new Comparator<Genome>() {

            public int compare(Genome o1, Genome o2) {
                return (int) (o2.fitness - (o1.fitness));
            }
        };
        public static Comparator<Genome> ASCENDING = new Comparator<Genome>() {

            public int compare(Genome o1, Genome o2) {
                return (int) (o1.fitness - (o2.fitness));
            }
        };



    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        // default serialization
        oos.defaultWriteObject();
        oos.writeInt(this.Genes.size());
        for (Gene gene : Genes) {
            oos.writeInt(gene.getInto());
            oos.writeInt(gene.getOut());
            oos.writeInt(gene.layer);
            oos.writeDouble(gene.getWeigth());
            oos.writeBoolean(gene.isEnabled());
            oos.writeInt(gene.getInnovation());
            oos.writeInt(gene.getActivition());
            oos.writeInt(gene.isExcess());

        }
        // write the object

    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        // default deserialization
        ois.defaultReadObject();
        int sizeOfGenes = ois.readInt();
        this.Genes = new ArrayList<Gene>();
        for (int i = 0; i < sizeOfGenes; i++) {
            Gene gene = new Gene();
            gene.setInto(ois.readInt());
            gene.setOut(ois.readInt());
            gene.layer = ois.readInt();
            gene.setWeigth(ois.readDouble());
            gene.setEnabled(ois.readBoolean());
            gene.setInnovation(ois.readInt());
            gene.setActivition(ois.readInt());
            gene.setExcess(ois.readInt());
            this.Genes.add(gene);

            // ... more code
        }

    }



}
