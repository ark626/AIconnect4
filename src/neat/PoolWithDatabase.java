package neat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import database.Situation;

public class PoolWithDatabase extends Pool implements DataBaseAddOn {

    public transient ArrayList<Situation> situations = new ArrayList<>();
    public transient ArrayList<Situation> tempSituations = new ArrayList<>();
    private static final boolean DOPREEVALUATION = false;


    public PoolWithDatabase(int i, int in, int out) {
        super(i, in, out);
        // TODO Auto-generated constructor stub
    }
    
    public PoolWithDatabase(int in, int out) {
        super(in, out);
    }

    public void setFitnessOfNewSituation(long fitness) {
        if (this.tempSituations.size() - 1 > 0) {
            Situation situation = this.tempSituations.get(this.tempSituations.size() - 1);
            if (situation != null) {
                situation.getBestTransition()
                        .setFitnessDelta(fitness);
            }
            return;
        }

    }

    private void sortInSituations() {
        System.out.println("Sorting in " + tempSituations.size() + " new Situations");
        for (int i = 0; i < this.tempSituations.size(); i++) {
            if (!sortInSituation(this.tempSituations.get(i))) {
                this.situations.add(tempSituations.get(i));
            }
        }
        this.tempSituations = new ArrayList<>();
        System.out.println("Total Situations " + situations.size() + "");
    }

    private boolean sortInSituation(Situation situationToSortIn) {
        for (Situation situation : this.situations) {
            if (situation.mergeSituation(situationToSortIn)) {
                return true;
            }
        }
        return false;
    }

    private long calculateSituationsTotalFitness() {
        long sum = 0L;

        for (Situation situation : this.situations) {
            sum += situation.getBestFitness();
        }

        return sum;
    }


    public boolean preEvaluateGenome(Genome genome) {

        System.out.println("Preevaluation started");
        if (this.situations.size() == 0) {
            return true;
        }

        long sum = 0;

        for (Situation situation : this.situations) {
            sum += this.preEvaluateSituationWithGenome(genome, situation);
        }

        genome.setFitness(-this.calculateSituationsTotalFitness() + sum, true, false);
        // System.out.println("Preevaluation Result "+sum+" "+
        // this.calculateSituationsTotalFitness());
        if (sum >= this.calculateSituationsTotalFitness()) {
            return true;
        }
        return false;


    }

    @Override
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

            boolean result = preEvaluateGenome(current);
            if (result == true) {
                System.out.println("Preevaluation of Genome successfull " + current.getFitness());
                current.setFitness(0L, false, false);
                preEvaluation = true;
                this.reevaluationProgress = false;
                count = 0;
                return;
            }



        }
    }

    @Override
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
        ArrayList<Genome> childrenTries = new ArrayList<Genome>();
        for (Species s : this.Species) {
            int breed = (int) Math.floor(((double) s.getAverageFitness() / (double) sum * Population));
            int tries = 0;
            int success = 0;

            while (tries > 1000 && breed > success) {
                tries++;
                for (int i = 0; i < breed * 300; i++) {
                    Genome ge = s.breedChild();
                    this.Innovation = ge.mutate(this.Innovation);
                    while (ge.Genes.size() == 0) {
                        ge = s.breedChild();
                        this.Innovation = ge.mutate(this.Innovation);
                    }
                    childrenTries.add(ge);
                    for (Genome genome : childrenTries) {
                        genome.generateNetwork();
                        if (DOPREEVALUATION) {
                            if (preEvaluateGenome(genome)) {
                                genome.setFitness(0L, false, false);
                                children.add(genome);
                                success++;
                                tries = 0;
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void save(String s, String fileName, int i) {

        try {

            Pool.cleanUp(s);

            File f = new File(s + fileName);
            FileOutputStream fileOut = new FileOutputStream(f.getAbsolutePath());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            // writing Situations DB
            out.writeInt(this.situations.size());
            for (Situation situation : this.situations) {
                out.writeObject(situation);
            }
            // END

            writePool(s, fileName, out,f, fileOut);
            
        }

        catch (IOException e) {
            e.printStackTrace();
            Pool.log(e.getMessage() + "; Pool line 523");
            System.out.println(e.getMessage());
            // this.save("/tmp/employee"+i+1+".ser",i+1);
        }

        boolean didRealodWorked = true;;
        try {
            PoolWithDatabase p = PoolWithDatabase.loadBackup(s + fileName);
            // p.rankGlobally();
            // TODO:
        } catch (Exception e) {
            Pool.log(e.getMessage() + "; Pool line 532");
            didRealodWorked = false;
            System.out.println(e.getMessage());
        }
        if (didRealodWorked) {
            new File("./MarioAISave").mkdirs();
            Pool.cleanUp("./MarioAISave");
            moveFiles(s, "./MarioAISave/");

        } else {
            System.out.println("Reaload didnt work.");
        }


    }


    public static PoolWithDatabase loadBackup(String s) throws ClassNotFoundException, IOException {
        try {
            File f = new File(s);
            FileInputStream fileIn = new FileInputStream(f.getAbsolutePath());
            ObjectInputStream in = new ObjectInputStream(fileIn);

            // writing Situations DB
            int situationsSize = in.readInt();
            ArrayList<Situation> situations = new ArrayList<>();
            for (int i = 0; i < situationsSize; i++) {

                Situation situation = (Situation) in.readObject();
                situations.add(situation);
            }

            // END
            int inn = in.readInt();
            int out = in.readInt();
            // int currentBestFitness = Integer.MIN_VALUE;
            PoolWithDatabase p = new PoolWithDatabase(inn, out);

            
            p = (PoolWithDatabase) Pool.readPool(s, fileIn, in, inn, out, p);
            p.situations = situations;
            return p;
            
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
    
    public static PoolWithDatabase load(String s) throws ClassNotFoundException, IOException {

        return PoolWithDatabase.loadBackup("./MarioAISave/" + s);

    }

 

}
