package hyper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Species implements Comparable<Species> {
    private long topFitness;
    private int staleness;
    public ArrayList<Genome> Genomes;
    private long averageFitness;
    private static final double CrossoverChance = 0.70;
    private static final int EXCESSMULTIPLIKANT = 1;
    private int Inputs = 42;
    private int Outputs = 3;
    // public static final int MaxNodes = 1000000;

    public transient Pool p;

    public Species(int in, int out, Pool p) {
        this.Inputs = in;
        this.Outputs = out;
        this.topFitness = 0;
        this.staleness = 0;
        this.p = p;
        this.Genomes = new ArrayList<Genome>();
        this.averageFitness = 0;
    }

    public long calculateAverageFitness() {
        int total = 0;

        for (Genome g : this.Genomes) {

            total += g.getGlobalRank();

        }
        this.averageFitness = total / this.Genomes.size();
        // return total/this.Genomes.size();
        return this.averageFitness;
    }

    public Genome crossover(Genome g1, Genome g2) {
        if (g1.getFitness() > g2.getFitness()) {
            Genome temp = g1;
            g1 = g2;
            g2 = temp;
        }
        int i = 0;
        int gen = 0;
        double[] temp = new double[8];
        for (double z : g1.getMutationrates()) {
            temp[i++] = z;
        }
        if (g1.getGeneration() > g2.getGeneration()) {
            gen = g1.getGeneration() + 1;
        } else {
            gen = g2.getGeneration() + 1;
        }

        Genome child = new Genome(this.Inputs, this.Outputs, gen, p);
        ArrayList<Gene> Innovation = new ArrayList<Gene>();
        for (Gene gene : g2.Genes) {
            Innovation.add(gene);
        }
        
        for(Gene gene: g1.Genes){
            int times=0;
            for(Gene gene2:g2.Genes){
                if(gene.getInto() == gene2.getInto()&&gene.getOut()==gene2.getOut()&&gene.getWeigth()==gene2.getWeigth()){
                    times += 1;
                }
            }
            if(times >0||gene.isExcess()>0){
                
                if(gene.isExcess()<2){
                gene.setExcess(1);
                }
                
//                for(int j = 0;j<EXCESSMULTIPLIKANT;j++){
//                    
//                    Gene g = gene.copyGene();
//                    g.setExcess(2);
//                    Innovation.add(g);
//                }
            }
        }

        for (Gene gene : g1.Genes) {
            Gene gene1 = gene;
            Gene gene2 = null;
            for (Gene g : Innovation) {
                if (g.getInnovation() == gene1.getInnovation()) {
                    gene2 = g;
                }
            }
            int rand = (int) Math.round(Math.random());
            Gene geneToAdd = null;
            if (gene2 != null && rand == 1 && gene2.isEnabled()) {
                 geneToAdd =  gene2.copyGene();
                if(geneToAdd.isExcess()>0){
                    geneToAdd.setExcess(2);
                }
                
            } else {
                geneToAdd =  gene1.copyGene();
               if(geneToAdd.isExcess()>0){
                   geneToAdd.setExcess(2);
               }
            }
            child.Genes.add(geneToAdd);
        }
        child.setMaxneuron(Math.max(g1.getMaxneuron(), g2.getMaxneuron()));
        return child;

    }

    public Genome breedChild() {
        Genome child = null;
        Random r = new Random();
        if (Math.random() < CrossoverChance) {
            int rand = r.nextInt(this.Genomes.size());
            Genome g1 = this.Genomes.get(rand);
            //child = new Genome(this.Inputs, this.Outputs, 1, p);
            rand = r.nextInt(this.Genomes.size());
            Genome g2 = this.Genomes.get(rand);
            child = this.crossover(g1, g2);
        } else {
            int rand = r.nextInt(this.Genomes.size());
            child = (this.Genomes.get(rand)).copyGenome();

        }

        return child;
    }

    public long getTopFitness() {
        return topFitness;
    }

    public void setTopFitness(long topFitness) {
        this.topFitness = topFitness;
    }

    public int getStaleness() {
        return staleness;
    }

    public void setStaleness(int staleness) {
        this.staleness = staleness;
    }

    public ArrayList<Genome> getGenomes() {
        return Genomes;
    }

    public void setGenomes(ArrayList<Genome> genomes) {
        Genomes = genomes;
    }

    public long getAverageFitness() {
        return averageFitness;
    }

    public void setAverageFitness(long averageFitness) {
        this.averageFitness = averageFitness;
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

    public Pool getP() {
        return p;
    }

    public void setP(Pool p) {
        this.p = p;
    }

    public int compareTo(Species arg0) {


        return Comparators.DESCENDING.compare(this, arg0);// (int) (((Genome) arg0).fitness -
                                                         // this.fitness);
    }

    public static class Comparators {

        public static Comparator<Species> ASCENDING = new Comparator<Species>() {

            public int compare(Species o1, Species o2) {
                return (int)(o1.getAverageFitness()- (o2.getAverageFitness()));
            }
        };
        public static Comparator<Species> DESCENDING = new Comparator<Species>() {

            public int compare(Species o1, Species o2) {
                return (int)(o2.getAverageFitness() - (o1.getAverageFitness()));
            }
        };



    }


}
