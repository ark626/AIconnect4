package hyper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Pool implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 8913652205956204258L;
    public ArrayList<Species> Species;
    public int generation;
    public int Innovation;
    public int currentSpecies;
    public int currentGenome;
    public int currentFrame;
    public long maxFitness = Integer.MIN_VALUE;
    public static final double Population = 300;
    public static final double DeltaDisjoint = 2.0;
    public static final double DeltaWeights = 0.4;
    public static final double DeltaThreshold = 1;
    public static final double StaleSpecies = 15; // 15
    private static final double SPECIESPERCENTAGE = 0.5;// normally 0.5
    private static final int MINSPECIES = 5;
    private static final int MAXSPECIES = 15;
    public int Inputs = 42;
    public int Outputs = 3;
    // private static final int MaxNodes = 1000000;

    public Pool(int in, int out) {
        super();
        this.Inputs = in;
        this.Outputs = out;
        Species = new ArrayList<Species>();
        this.generation = 0;
        Innovation = Outputs;
        this.currentSpecies = 1;
        this.currentGenome = 1;
        this.currentFrame = 0;
        // this.maxFitness = Integer.MIN_VALUE;
        for (int i = 0; i < Population; i++) {
            Genome basic = Genome.basicGenome(this.Inputs, this.Outputs, this);
            this.Innovation = basic.mutate(this.Innovation);
            this.addToSpecies(basic);
        }

    }

    public Pool copy() {
        Pool p = new Pool(this.Inputs, this.Outputs);
        this.save("./copyPool.pl", 0);
        try {
            p = Pool.load("./copyPool.pl");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return p;
    }
    
    public Pool() {
        super();
    }

    public Genome getbest() {
        Genome temp = this.Species.get(0).Genomes.get(0);
        long maxfit = Integer.MIN_VALUE;// temp.fitness;
        for (Species s : this.Species) {
            for (Genome g : s.Genomes) {
                if (g.getFitness() != 0) {
                    if ((g.getFitness() >= maxfit)) {
                        temp = g;
                        maxfit = temp.getFitness();
                        // this.currentGenome = s.Genomes.indexOf(temp)+1;
                        // this.currentSpecies = this.Species.indexOf(s)+1;
                    }
                }
            }
        }


        return temp;
    }

    public long getTopfitness() {
        // Genome temp = this.Species.get(0).Genomes.get(0);//this.Species.get(0).Genomes.get(0);
        long maxfit = Integer.MIN_VALUE;// temp.fitness;
        for (Species s : this.Species) {
            for (Genome g : s.Genomes) {
                if (g.getFitness() != 0) {
                    if ((g.getFitness() >= maxfit)) {
                        // temp = g;
                        maxfit = g.getFitness();
                        // this.currentGenome = s.Genomes.indexOf(temp)+1;
                        // this.currentSpecies = this.Species.indexOf(s)+1;
                    }
                }
            }
        }


        return maxfit;
    }

    public Pool(int i, int in, int out) {
        super();
        this.Inputs = in;
        this.Outputs = out;
        Species = new ArrayList<Species>();
        this.generation = 0;
        Innovation = Outputs;
        this.currentSpecies = 1;
        this.currentGenome = 1;
        this.currentFrame = 0;
        // this.maxFitness = Integer.MIN_VALUE;

    }

    public void removeStaleSpecies() {
        ArrayList<Species> survivors = new ArrayList<Species>();
        // Collections.sort(this.Species);
        for (Species s : this.Species) {
            Collections.sort(s.Genomes, Genome.Comparators.DESCENDING);

            if (s.Genomes.get(0)
                    .getFitness() > s.getTopFitness()) {
                s.setTopFitness(s.getGenomes()
                        .get(0)
                        .getFitness());
                s.setStaleness(0);
            } else {
                s.setStaleness(s.getStaleness() + 1);
            }
            if (s.getStaleness() < StaleSpecies || s.getTopFitness() >= this.getTopfitness()) {// ||(survivors.size()<MINSPECIES&&s.calculateAverageFitness()>this.totalAverageFitness()/this.Species.size()))
                                                                                               // {
                survivors.add(s);
            }
        }

        this.Species = survivors;
        // this.rankGlobally();
    }

    public void removeWeakSpecies() {
        int sum = this.totalAverageFitness();
        ArrayList<Species> survivors = new ArrayList<Species>();
        for (Species s : this.Species) {
            s.calculateAverageFitness();
            int breed = (int) Math.floor(((double) s.getAverageFitness() / (double) sum * Population));

            if (breed >= 1 || survivors.size() < MINSPECIES && s.calculateAverageFitness() > this.totalAverageFitness() / this.Species.size()) {
                survivors.add(s);
            }
        }
//        if (survivors.size() > MAXSPECIES) {
//
//            Collections.sort(survivors, neat.Species.Comparators.ASCENDING);
//            for (int i = 0; i < survivors.size(); i++) {
//                if (survivors.get(i)
//                        .calculateAverageFitness() < this.totalAverageFitness() / survivors.size()) {
//                    survivors.remove(i);
//                }
//            }
//        }
        this.Species = survivors;
    }

    public void removeEmptySpecies() {
        for (Species s : this.Species) {
            if (s.Genomes.size() == 0) {
                this.Species.remove(s);
            }
        }
    }

    public int disjoint(ArrayList<Gene> g1, ArrayList<Gene> g2) {
        ArrayList<Boolean> i1 = new ArrayList<Boolean>();
        int maxinovation = 0;
        for (Gene g : g1) {
            if (g.getInnovation() > maxinovation) {
                maxinovation = g.getInnovation();
            }
        }
        for (Gene g : g2) {
            if (g.getInnovation() > maxinovation) {
                maxinovation = g.getInnovation();
            }
        }
        for (int i = 0; i < maxinovation + 1; i++) {
            i1.add(false);
        }
        for (Gene g : g1) {
            i1.set(g.getInnovation(), true);
        }
        ArrayList<Boolean> i2 = new ArrayList<Boolean>();
        for (int i = 0; i < maxinovation + 1; i++) {
            i2.add(false);
        }
        for (Gene g : g2) {
            i2.set(g.getInnovation(), true);
        }

        int disjointGenes = 0;
        for (Gene g : g1) {
            if (!i2.get(g.getInnovation())) {
                disjointGenes += 1;
            }
        }
        for (Gene g : g2) {
            if (!i1.get(g.getInnovation())) {
                disjointGenes += 1;
            }
        }

        int n = Math.max(g1.size(), g2.size());
        if (n > 0) {

            return disjointGenes / n;
        } else
            return 0;
    }

    public int weigths(ArrayList<Gene> g1, ArrayList<Gene> g2) {
        ArrayList<Gene> i2 = new ArrayList<Gene>();
        for (Gene g : g2) {
            i2.add(g);
        }

        int sum = 0;
        int zufall = 0;
        for (Gene g : g1) {
            if (i2.size() > g.getInnovation()) {
                if (i2.get(g.getInnovation()) != null) {
                    Gene gene2 = i2.get(g.getInnovation());
                    sum += Math.abs(g.getWeigth() - (gene2.getWeigth()));
                    zufall = zufall + 1;
                }
            }
        }
        if (zufall > 0) {
            return sum / zufall;
        } else
            return 0;
    }

    public boolean sameSpecies(Genome g1, Genome g2) {
        double dd = DeltaDisjoint * disjoint(g1.Genes, g2.Genes);
        double dw = DeltaWeights * weigths(g1.Genes, g2.Genes);
        return (dd + dw < DeltaThreshold);
    }

    public void addToSpecies(Genome child) {
        boolean found = false;
        for (Species s : this.Species) {
            if (!found && sameSpecies(child, s.Genomes.get(0))) {
                s.Genomes.add(child);
                found = true;
            }
        }
        if (!found) {
            Species childSpecies = new Species(this.Inputs, this.Outputs, this);
            childSpecies.Genomes.add(child);
            this.Species.add(childSpecies);
        }


    }

    public void rankGlobally() {
        ArrayList<Genome> global = new ArrayList<Genome>();
        for (Species s : this.Species) {
            for (Genome g : s.Genomes) {
                global.add(g);
            }
        }
        //return (a.fitness < b.fitness)
        Collections.sort(global, Genome.Comparators.ASCENDING);
        int i = 1;
        for (Genome g : global) {
            g.setGlobalRank(i++);
        }
    }

    public int totalAverageFitness() {
        int total = 0;
        for (Species s : this.Species) {
            s.calculateAverageFitness();
            total += s.getAverageFitness();

        }

        return total;
    }

    /**
     * Sortiert die Genome in jeder Species Desc, 
     * dannach wird errechnet wieviele % überleben
     * und die Genome von hinten aus der Spezies herrausgelöscht
     * Wenn cutToOne aktiv ist, werden die Spezies auf das beste 
     * Element reduziert
     * @param cutToOne
     */
    public void cullSpecies(boolean cutToOne) {


        for (Species s : this.Species) {

            Collections.sort(s.Genomes, Genome.Comparators.DESCENDING);

            int remain = (int) Math.ceil(s.Genomes.size() * SPECIESPERCENTAGE);
            if (remain < 1)
                remain = 1;
            if (cutToOne) {
                remain = 1;
            }

            while (s.Genomes.size() > remain) {
                s.Genomes.remove(s.Genomes.size() - 1);

            }
        }
    }

    public void newGeneration() {
        this.cullSpecies(false);
        this.rankGlobally();
        this.removeStaleSpecies();
        this.rankGlobally();

        for (Species s : this.Species) {
            s.calculateAverageFitness();
        }

        this.removeWeakSpecies();

        // Collections.sort(this.Species);
        int sum = totalAverageFitness();
        ArrayList<Genome> children = new ArrayList<Genome>();
        for (Species s : this.Species) {
            int breed = (int) Math.floor(((double) s.getAverageFitness() / (double) sum * Population));
            for (int i = 0; i < breed; i++) {
                Genome ge = s.breedChild();
                this.Innovation = ge.mutate(this.Innovation);
                // while(ge.Genes.size() ==0){
                // ge = s.breedChild();
                // this.Innovation = ge.mutate(this.Innovation);
                // }

                children.add(ge);
            }
        }
        // this.rankGlobally();
        this.cullSpecies(true);
        // this.rankGlobally();
        Random r = new Random();
        // this.removeEmptySpecies();
        // if(this.Species.size()<=0){
        // for(int i=0;i<Population;i++){
        // Genome basic = Genome.basicGenome();
        // this.Innovation = basic.mutate(this.Innovation);
        // this.addToSpecies(basic);
        // };
        // }
        while (children.size() + this.Species.size() < Population) {
            if (this.Species.size() == 0) {
                System.out.println("FUCK" + this.Species.size() + " " + this.currentSpecies);
            }
            Species s = this.Species.get(r.nextInt(this.Species.size()));
            Genome ge = s.breedChild();
            this.Innovation = ge.mutate(this.Innovation);

            while (ge.Genes.size() == 0) {
                ge = s.breedChild();
                this.Innovation = ge.mutate(this.Innovation);
            }

            if (ge.Genes.size() > 0) {
                children.add(ge);
            }
        }
        for (Genome g : children) {
            this.addToSpecies(g);
        }
        this.generation += 1;

        // TODO: Save
    }
    
    public boolean checkIfNextGenomeWouldGenerateNextStation() {
        int storeCurrentGenome = this.currentGenome;
        int storeCurrentSpecies = this.currentSpecies;
        
        this.currentGenome += 1;
        if (this.currentGenome > this.Species.get(this.currentSpecies - 1).Genomes.size()) {
            currentGenome = 1;
            currentSpecies += 1;
            if (currentSpecies > this.Species.size()) {
                
                // System.out.println(this.toString());
                this.currentSpecies = storeCurrentSpecies;
                this.currentGenome = storeCurrentGenome;
                return true;
            }
        }
       return false; 
    }

    public void nextGenome() {
        this.currentGenome += 1;
        if (this.currentGenome > this.Species.get(this.currentSpecies - 1).Genomes.size()) {
            currentGenome = 1;
            currentSpecies += 1;
            if (currentSpecies > this.Species.size()) {
                this.newGeneration();
                // System.out.println(this.toString());
                this.currentSpecies = 1;
                this.currentGenome = 1;
            }
        }
    }

    @Override
    public String toString() {
        String test = "";
        for (Species s : this.Species) {
            for (Genome g : s.Genomes) {
                if (s.Genomes.size() < 1) {
                    test += " Error in " + Species.indexOf(s);
                }
            }
        }
        return test;

    }

    public boolean alreadyMeasured() {

        return (this.Species.get(this.currentSpecies - 1).Genomes.get(this.currentGenome - 1)
                .getFitness() != 0L);
    }

    public Genome currentGenome() {
        return this.Species.get(this.currentSpecies - 1).Genomes.get(this.currentGenome - 1);
    }

    // Serializable

    public void save(String s, int i) {
        try {
            File f = new File(s);
            FileOutputStream fileOut = new FileOutputStream(f.getAbsolutePath());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            // writing KI's
            out.writeInt(this.Inputs);
            out.writeInt(this.Outputs);
            out.writeInt(this.generation);
            out.writeInt(this.Innovation);
            out.writeLong(this.maxFitness);
            out.writeInt(this.Species.size());
            for (Species sp : this.Species) {
                out.writeInt(sp.Genomes.size());
                out.writeLong(sp.getTopFitness());
                out.writeLong(sp.getAverageFitness());
                for (Genome g : sp.Genomes) {
                    out.writeObject(g);
                }


            }
            out.close();
            fileOut.close();
            String saveStatement =
                    "Serialized data is saved in " + f.getAbsolutePath() + " with Generation " + this.generation + " Fitness " + this.getbest()
                            .getFitness() + " First: "
                            + this.Species.get(0)
                                    .getGenomes()
                                    .get(0)
                                    .getFitness()
                            + "/" + this.Species.get(0)
                                    .getAverageFitness();
            if (this.Species.size() > 2) {
                saveStatement += " Second: " + this.Species.get(1)
                        .getGenomes()
                        .get(0)
                        .getFitness() + "/"
                        + this.Species.get(1)
                                .getAverageFitness();
            }
            System.out.println(saveStatement);
        }
        // catch(FileNotFoundException e){
        // File f = new File(s);
        // try {
        // String[] st = s.split("/");
        // String path = "";
        // for(int j = 0;j<st.length-1;j++){
        // path += st;
        // }
        // new File(path).mkdir();
        // f.createNewFile();
        // this.save(s, i);
        // }
        // catch (IOException e1) {
        // // TODO Auto-generated catch block
        // e1.printStackTrace();
        // }
        // }
        catch (IOException e) {
            e.printStackTrace();
            // this.save("/tmp/employee"+i+1+".ser",i+1);
        }

    }

    public static Pool load(String s) throws ClassNotFoundException {
        try {
            File f = new File(s);
            FileInputStream fileIn = new FileInputStream(f.getAbsolutePath());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            int inn = in.readInt();
            int out = in.readInt();
            Pool p = new Pool(1, inn, out);
            p.Inputs = inn;
            p.Outputs = out;
            p.generation = in.readInt();
            p.Innovation = in.readInt();
            p.maxFitness = in.readLong();
            p.currentGenome = 1;
            p.currentSpecies = 1;
            int Speciessize = in.readInt();
            for (int i = 0; i < Speciessize; i++) {
                int Genomesize = in.readInt();
                Species spe = new Species(p.Inputs, p.Outputs, p);
                spe.setTopFitness(in.readLong());
                spe.setAverageFitness(in.readLong());
                for (int j = 0; j < Genomesize; j++) {
                    Genome g = (Genome) in.readObject();
                    g.setParent(p);

                    spe.Genomes.add(g);
                }
                p.Species.add(spe);
            }

            in.close();
            fileIn.close();
            p.maxFitness = p.getTopfitness();
      
            // System.out.println("Loaded file from" +f.getAbsolutePath()+"with Generation:
            // "+p.generation+" Fitness
            // "+p.getbest().getFitness());//Thread.currentThread().getStackTrace().toString());
            return p;
        } catch (IOException i) {
            i.getMessage();
            System.out.println("Couldnt load file");
            return null;
            // return new Pool();

        }

    }

}


