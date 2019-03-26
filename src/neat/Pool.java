package neat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.function.Consumer;
import database.Image;
import database.Situation;

public class Pool implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 8913652205956204258L;
    public ArrayList<Species> Species;
    // public transient Genome currentBest;

    public int generation;
    public long Innovation;
    public int currentSpecies;
    public int currentGenome;
    public int currentFrame;
    public transient boolean reevaluationProgress = false;
    public long maxFitness = Integer.MIN_VALUE;
    public static final double Population = 300;
    public static final double DeltaDisjoint = 2.0;
    public static final double DeltaWeights = 0.4;
    public static final double DeltaThreshold = 1;// 5
    public static final double DeltaActivation = 2;// Merged with DeltaDisjoint
    public static final double StaleSpecies = 15; // 15
    private static final double SPECIESPERCENTAGE = 0.3;// normally 0.5
    private transient static final double ELITISM = 0.05;// normally 0.5
    private static final int MINSPECIES = 5;
    private static final int MAXSPECIES = 15;
    private static final int SuperMutantsMax = 20;
    public transient String fileName;
    public int superMutants = 0;
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
        this.maxFitness = Integer.MIN_VALUE;
        for (int i = 0; i < Population; i++) {
            Genome basic = Genome.basicGenome(this.Inputs, this.Outputs, this);
            this.Innovation = basic.mutate(this.Innovation);
            this.addToSpecies(basic);
        }

    }



    public long preEvaluateSituationWithGenome(Genome genome, Situation situation) {

        double[] result = genome.step(
                situation.getInputSituation()
                        .getImage());

        Image resultImage = new Image();
        resultImage.setImage(result);

        if (resultImage.equalsButton(
                situation.getBestTransition()
                        .getButtonPress())) {

            return situation.getBestFitness();
        }

        return 0;
    }

    public void getNextGenome() {
        boolean preEvaluation = false;
        int count = 0;

        while (!preEvaluation) {

            while (this.alreadyMeasured()) {
                if (count > Pool.Population - 10) {
                    this.reevaluationProgress = true;
                }
                count++;
                this.nextGenome();
            }
            Genome current = this.Species.get(this.currentSpecies - 1).Genomes.get(this.currentGenome - 1);
            current.generateNetwork();

            // boolean result = preEvaluateGenome(current);
            // if (result == true) {
            // System.out.println("Preevaluation of Genome successfull " + current.getFitness());
            // current.setFitness(0L, false, false);
            // preEvaluation = true;
            // this.reevaluationProgress = false;
            // count = 0;
            // return;
            // }



        }
    }


    public void resetEveryEvaluation() {
        for (Species s : this.Species) {
            for (Genome g : s.Genomes) {
                g.setFitness(0L, false, false);
            }
        }
        this.maxFitness = 0L;
    }

    public long getTotalAverageFitness() {
        long total = 0;
        long amount = 0;

        for (Species s : this.Species) {
            for (Genome g : s.Genomes) {
                total += g.getFitness();
                amount += 1;
            }
        }

        return total / amount;
    }

    public void logStuffAway() {

        String logString = "";
        logString += this.generation + ";";
        logString += this.Innovation + ";";
        logString += this.Species.size() + ";";

        logString += this.getbest()
                .getGeneration() + ";";
        logString += this.getbest()
                .getGlobalRank() + ";";
        logString += this.getbest()
                .getFitness() + ";";
        logString += this.getbestSpecies()
                .getAverageFitness() + ";";;
        logString += this.getbest()
                .getGenes()
                .size() + ";";
        logString += this.Species.indexOf(this.getbestSpecies()) + ";";;
        logString += this.getbestSpecies().Genomes.indexOf((this.getbest())) + ";";;

        logString += this.Species.get(0).Genomes.get(0)
                .getGeneration() + ";";
        logString += this.Species.get(0).Genomes.get(0)
                .getGlobalRank() + ";";
        logString += this.Species.get(0).Genomes.get(0)
                .getFitness() + ";";
        logString += this.Species.get(0)
                .getAverageFitness() + ";";;
        logString += this.Species.get(0).Genomes.get(0)
                .getGenes()
                .size() + ";";

        logString += this.getTotalAverageFitness() + ";";
        // logString += this.situations.size() + ";";

        logString += "\r\n";
        log(logString, "LearnLog");
        //
        // for (Situation situation : this.situations) {
        // String[] picture = situation.getStringedVersionOfInputImage();
        // for (String line : picture) {
        // log(line + "\r\n", "SituationLog");
        // }
        // log("\r\n", "SituationLog");
        // }
        // log("\r\n", "SituationLog");
        // log("-------------------------- \r\n", "SituationLog");
        // log("\r\n", "SituationLog");
    }

    public Pool copy() {
        Pool p = new Pool(this.Inputs, this.Outputs);

        this.save("./", "copyPool.pl", 0);
        try {
            p = Pool.load("./copyPool.pl");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return p;
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

    public Species getbestSpecies() {
        Species temp = this.Species.get(0);
        long maxfit = Integer.MIN_VALUE;// temp.fitness;
        for (Species s : this.Species) {
            for (Genome g : s.Genomes) {
                if (g.getFitness() != 0) {
                    if ((g.getFitness() >= maxfit)) {
                        temp = s;
                        maxfit = g.getFitness();
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
        long oldBest = this.getbest()
                .getFitness();
        ArrayList<Species> survivors = new ArrayList<Species>();
        // Collections.sort(this.Species);
        for (Species s : this.Species) {
            Collections.sort(s.Genomes, Genome.Comparators.DESCENDING);

            if (s.Genomes.get(0)
                    .getFitness() > s.getTopFitness()) {
                s.setTopFitness(
                        s.getGenomes()
                                .get(0)
                                .getFitness());
                s.setStaleness(0);
            } else {
                s.setStaleness(s.getStaleness() + 1);
            }
            if (s.getStaleness() < StaleSpecies || this.maxFitness <= s.getTopFitnessCalculated()) {// this.getTopfitness()
                                                                                                    // )
                                                                                                    // {//
                                                                                                    // ||(survivors.size()<MINSPECIES&&s.calculateAverageFitness()>this.totalAverageFitness()/this.Species.size()))
                // {
                survivors.add(s);
            }
        }

        this.Species = survivors;
        long newBest = this.getbest()
                .getFitness();
        issueLogger(oldBest, newBest, "removeStaleSpecies");
        this.rankGlobally();
    }

    private void issueLogger(long old, long nEw, String where) {
        if (old > nEw) {
            System.out.println(where);
        }
    }

    private boolean isBestInThisSpecies(Species s) {
        return s.Genomes.contains(this.getbest());
    }

    public void removeWeakSpecies() {
        long old = this.getbest()
                .getFitness();
        int sum = this.totalAverageFitness();
        ArrayList<Species> survivors = new ArrayList<Species>();
        for (Species s : this.Species) {
            s.calculateAverageFitness();
            int breed = (int) Math.floor(((double) s.getAverageFitness() / (double) sum * Population));

            if (breed >= 1 || this.maxFitness <= s.getTopFitnessCalculated()) { // ||(survivors.size()
                                                                                // < MINSPECIES &&
                // s.calculateAverageFitness() >
                // (this.totalAverageFitness() / this.Species.size()))) {
                survivors.add(s);
            }
        }
        // if (survivors.size() > MAXSPECIES) {
        //
        // Collections.sort(survivors, neat.Species.Comparators.ASCENDING);
        // for (int i = 0; i < survivors.size(); i++) {
        // if (survivors.get(i)
        // .calculateAverageFitness() < this.totalAverageFitness() / survivors.size()) {
        // survivors.remove(i);
        // }
        // }
        // }
        this.Species = survivors;
        long newBest = this.getbest()
                .getFitness();
        issueLogger(old, newBest, "removeWeakSpecies");
    }

    public void removeEmptySpecies() {
        for (Species s : this.Species) {
            if (s.Genomes.size() == 0) {
                this.Species.remove(s);
            }
        }
    }

    // public int disjoint(ArrayList<Gene> g1, ArrayList<Gene> g2) {
    // ArrayList<Boolean> i1 = new ArrayList<Boolean>();
    // int maxinovation = 0;
    // for (Gene g : g1) {
    // if (g.getInnovation() > maxinovation) {
    // maxinovation = g.getInnovation();
    // }
    // }
    // for (Gene g : g2) {
    // if (g.getInnovation() > maxinovation) {
    // maxinovation = g.getInnovation();
    // }
    // }
    // for (int i = 0; i < maxinovation + 1; i++) {
    // i1.add(false);
    // }
    // for (Gene g : g1) {
    // i1.set(g.getInnovation(), true);
    // }
    // ArrayList<Boolean> i2 = new ArrayList<Boolean>();
    // for (int i = 0; i < maxinovation + 1; i++) {
    // i2.add(false);
    // }
    // for (Gene g : g2) {
    // i2.set(g.getInnovation(), true);
    // }
    //
    // int disjointGenes = 0;
    // for (Gene g : g1) {
    // if (!i2.get(g.getInnovation())) {
    // disjointGenes += 1;
    // }
    // }
    // for (Gene g : g2) {
    // if (!i1.get(g.getInnovation())) {
    // disjointGenes += 1;
    // }
    // }
    //
    // int n = Math.max(g1.size(), g2.size());
    // if (n > 0) {
    //
    // return disjointGenes / n;
    // } else
    // return 0;
    // }

    public int disjoint(ArrayList<Gene> g1, ArrayList<Gene> g2) {


        int disjointGenes = 0;
        for (Gene g : g1) {
            if (this.findGeneWithSameInnovationInList(g2, g) == null) {
                disjointGenes += 1;
            }
        }
        for (Gene g : g2) {
            if (this.findGeneWithSameInnovationInList(g1, g) == null) {
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
            Gene gene2 = findGeneWithSameInnovationInList(i2, g);
            if (gene2 != null) {
                sum += Math.abs(g.getWeigth() - (gene2.getWeigth()));
                zufall = zufall + 1;
            }
        }

        if (zufall > 0) {
            return sum / zufall;
        } else
            return 0;
    }

    public Gene findGeneWithSameInnovationInList(ArrayList<Gene> g1, Gene g) {

        for (Gene ge : g1) {
            if (ge.getInnovation() == g.getInnovation()) {
                return ge;
            }
        }
        return null;
    }

    public int activationFunctionDelta(ArrayList<Gene> g1, ArrayList<Gene> g2) {
        ArrayList<Gene> i2 = new ArrayList<Gene>();
        for (Gene g : g2) {
            i2.add(g);
        }

        int sum = 0;
        for (Gene g : g1) {
            // if (i2.size() > g.getInnovation()) {

            // if (i2.get(g.getInnovation()) != null) {
            // Gene gene2 = i2.get(g.getInnovation());

            Gene gene2 = findGeneWithSameInnovationInList(i2, g);
            if (gene2 != null) {
                if (g.getActivition() != gene2.getActivition()) {
                    sum += 1;
                }
            }
        }
        // }
        return sum;

    }

    public boolean sameSpecies(Genome g1, Genome g2) {
        double dd = DeltaDisjoint * disjoint(g1.Genes, g2.Genes);
        double dw = DeltaWeights * weigths(g1.Genes, g2.Genes);
        double ad = DeltaActivation * activationFunctionDelta(g1.Genes, g2.Genes);
        // System.out.println("Delta for new Generations Species "+dd + dw + ad+" =>" + (dd + dw +
        // ad < DeltaThreshold));
        return (dd + dw + ad < DeltaThreshold);
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
            // System.out.println("New Species Generated"+this.Species.size()+" "+
            // this.Species.indexOf(childSpecies));
        }


    }

    public void rankGlobally() {
        // Genome oldBest = this.currentBest;
        ArrayList<Genome> global = new ArrayList<Genome>();
        for (Species s : this.Species) {
            for (Genome g : s.Genomes) {
                global.add(g);
            }
        }
        // if(oldBest!= null&&!global.contains(oldBest)) {
        // System.out.println("OLD Is MISSING");
        // }
        // return (a.fitness < b.fitness)
        Collections.sort(global, Genome.Comparators.ASCENDING);
        int i = 1;
        for (Genome g : global) {
            g.setGlobalRank(i++);
        }
        // this.currentBest = global.get(global.size() - 1);
        //
        // if(oldBest!= null&&oldBest.getFitness()>currentBest.getFitness()) {
        // System.out.println("OLD FITNESS WAS BETTER
        // "+oldBest.getFitness()+"/"+currentBest.getFitness());
        // }
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
     * Sortiert die Genome in jeder Species Desc, dannach wird errechnet wieviele % überleben und
     * die Genome von hinten aus der Spezies herrausgelöscht Wenn cutToOne aktiv ist, werden die
     * Spezies auf das beste Element reduziert
     * 
     * @param cutToOne
     */
    public void cullSpecies(boolean cutToOne) {

        long old = this.getbest()
                .getFitness();
        for (Species s : this.Species) {

            Collections.sort(s.Genomes, Genome.Comparators.DESCENDING);

            int remain = (int) Math.ceil(s.Genomes.size() * SPECIESPERCENTAGE);
            if (cutToOne) {
                remain = (int) Math.ceil(s.Genomes.size() * ELITISM);// 1;
            }
            if (remain < 1)
                remain = 1;


            while (s.Genomes.size() > remain) {

                s.Genomes.remove(s.Genomes.size() - 1);

            }
        }
        long newBest = this.getbest()
                .getFitness();
        issueLogger(old, newBest, "cullSpecies");
    }

    public void newGeneration() {
        this.logStuffAway();

        long old = this.getbest()
                .getFitness();

        // sortInSituations();
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


            for (int i = 0; i < breed * 300; i++) {
                Genome ge = s.breedChild();
                this.Innovation = ge.mutate(this.Innovation);
                while (ge.Genes.size() == 0) {
                    ge = s.breedChild();
                    this.Innovation = ge.mutate(this.Innovation);
                }
                children.add(ge);

            }
        }


        // if (tries > 30) {
        // i = Integer.MAX_VALUE;
        // System.out.println("Warning, couldnt breed properly");
        // }


        this.rankGlobally();
        this.cullSpecies(true);
        this.rankGlobally();
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

        // If only 1. Species, try to create supermutants.
        // if (this.Species.size() == 1) {
        // this.superMutants++;
        // } else {
        // this.superMutants = 0;
        // }
        // if (this.superMutants >= SuperMutantsMax) {
        // System.out.println("Bottleneck Triggered! Creating Supermutants");
        //
        // for (Genome g : children) {
        // for (int i = 0; i < 100; i++) {
        // this.Innovation = g.mutate(this.Innovation);
        // }
        // }
        // }
        for (Genome g : children) {
            this.addToSpecies(g);
        }
        this.generation += 1;
        long newBest = this.getbest()
                .getFitness();
        issueLogger(old, newBest, "TOTAL");
        // TODO: Save
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
                // this.resetEveryEvaluation();

                if (!this.reevaluationProgress) {
                    new File("./MarioAI").mkdirs();
                    this.save("./MarioAI/", fileName, 0);
                }
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

    public void save(String s, String fileName, int i) {

        try {

            Pool.cleanUp(s);

            File f = new File(s + fileName);
            FileOutputStream fileOut = new FileOutputStream(f.getAbsolutePath());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);


            // END

            writePool(s, fileName, out, f, fileOut);


        }

        catch (IOException e) {
            e.printStackTrace();
            Pool.log(e.getMessage() + "; Pool line 523");
            System.out.println(e.getMessage());
            // this.save("/tmp/employee"+i+1+".ser",i+1);
        }

        // boolean didRealodWorked = true;;

        tryReload(t -> {
            try {
                loadBackup(t);
            } catch (Exception e) {
                Pool.log(e.getMessage() + "; Pool line 532");
                // didRealodWorked = false;
                System.out.println(e.getMessage());
                System.out.println("Reaload didnt work.");
                return;
            }
        }, s + fileName);

        new File("./MarioAISave").mkdirs();
        Pool.cleanUp("./MarioAISave");
        moveFiles(s, "./MarioAISave/");



    }

    protected void tryReload(Consumer<String> poolLoad, String filePath) {

        // Pool p = Pool.loadBackup(s + fileName);
        poolLoad.accept(filePath);
        // p.rankGlobally();
        // TODO:


    }



    private void logStuffAndCloseRest(File f, FileOutputStream fileOut, ObjectOutputStream out) throws IOException {
        out.close();
        fileOut.close();
        String saveStatement = "Serialized data is saved in " + f.getAbsolutePath() + " with Generation " + this.generation + " Fitness "
                + this.getbest()
                        .getFitness()
                + " First: " + this.Species.get(0)
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



    protected void writePool(String s, String fileName, ObjectOutputStream out, File f, FileOutputStream fileOut)
            throws IOException, FileNotFoundException {
        // writing KI's
        out.writeInt(this.Inputs);
        out.writeInt(this.Outputs);
        out.writeInt(this.generation);
        out.writeLong(this.Innovation);
        out.writeLong(this.maxFitness);
        out.writeInt(this.Species.size());
        int speciesIndex = -1;

        for (Species sp : this.Species) {
            out.writeInt(sp.Genomes.size());
            out.writeLong(sp.getTopFitness());
            out.writeLong(sp.getAverageFitness());
            speciesIndex++;
            int j = 0;
            for (Genome g : sp.Genomes) {
                File fi = new File(s + fileName + "Species" + speciesIndex + "Genome" + j++);
                FileOutputStream fileOutTemp = new FileOutputStream(fi.getAbsolutePath());
                ObjectOutputStream outPut = new ObjectOutputStream(fileOutTemp);
                outPut.writeObject(g);
                outPut.close();
            }


        }
        logStuffAndCloseRest(f, fileOut, out);
    }

    public static void log(String s) {

        try (FileWriter out = new FileWriter("ErrorLog.txt", true)) {

            out.write(s);
            out.flush();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void log(String s, String name) {

        try (FileWriter out = new FileWriter(name + ".txt", true)) {

            out.write(s);
            out.flush();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Pool load(String s) throws ClassNotFoundException, IOException {

        return Pool.loadBackup("./MarioAISave/" + s);

    }

    public static Pool loadBackup(String s) throws ClassNotFoundException, IOException {
        try {
            File f = new File(s);
            FileInputStream fileIn = new FileInputStream(f.getAbsolutePath());
            ObjectInputStream in = new ObjectInputStream(fileIn);

            int inn = in.readInt();
            int out = in.readInt();
            // int currentBestFitness = Integer.MIN_VALUE;
            Pool p = new Pool(1, inn, out);

            return readPool(s, fileIn, in, inn, out, p);
        } catch (FileNotFoundException i) {
            System.out.println(
                    i.getMessage() + i.toString() + i.getStackTrace()
                            .toString() + " Couldnt load file");
            Pool.log(i.getMessage() + "; Pool line 612");
            throw i;
            // return new Pool();

        } catch (IOException i) {
            i.printStackTrace();
            Pool.log(i.getMessage() + "; Pool line 618");
            throw i;
            // return new Pool();

        }


    }



    protected static Pool readPool(String s, FileInputStream fileIn, ObjectInputStream in, int inn, int out, Pool p)
            throws IOException, FileNotFoundException, ClassNotFoundException {
        p.Inputs = inn;
        p.Outputs = out;
        p.generation = in.readInt();
        p.Innovation = in.readLong();
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
                File fi = new File(s + "Species" + i + "Genome" + j);
                FileInputStream fileInTemp = new FileInputStream(fi.getAbsolutePath());
                ObjectInputStream inPut = new ObjectInputStream(fileInTemp);
                Genome g = (Genome) inPut.readObject();
                g.setParent(p);

                spe.Genomes.add(g);
                fileInTemp.close();
            }
            p.Species.add(spe);
        }

        in.close();
        fileIn.close();
        return p;
    }


    public static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;

        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            if (is != null)
                is.close();
            if (os != null)
                os.close();
        }
    }

    public static void cleanUp(String path) {
        File file = new File(path);
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }
    }

    public static boolean moveFiles(String source, String dest) {
        File sourceFile = new File(source);
        // copy file conventional way using Stream
        long start = System.nanoTime();
        String[] myFiles;
        if (sourceFile.isDirectory()) {
            myFiles = sourceFile.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(sourceFile, myFiles[i]);
                try {
                    File destFile = new File(dest + myFile.getName());
                    Pool.copyFileUsingStream(myFile, destFile);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    return false;
                }
            }
        }

        System.out.println("Time taken by Stream Copy = " + (System.nanoTime() - start));
        return true;
    }

    public boolean checkIfNextGenomeWouldGenerateNextStation() {
        int storeCurrentGenome = this.currentGenome;
        int storeCurrentSpecies = this.currentSpecies;

        storeCurrentGenome += 1;
        if (storeCurrentGenome > this.Species.get(storeCurrentSpecies - 1).Genomes.size()) {
            storeCurrentGenome = 1;
            storeCurrentSpecies += 1;
            if (storeCurrentSpecies > this.Species.size()) {

                // System.out.println(this.toString());
                return true;
            }
        }
        return false;
    }

}


