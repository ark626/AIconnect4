package neat;

import static org.junit.Assert.fail;
import org.junit.Test;
import hyperneat.HyperNeat;



public class test {
    static int input = 10;
    static int output = 10;
    static int geninmax = 100;
    static int genoutmax = 100;
    static int gengenmax = 100;

    @Test
    public void initializeGenome() {
        Genome g = null;
        for (int i = 1; i < geninmax; i++) {
            for (int j = 1; j < genoutmax; j++) {
                for (int k = 0; k < gengenmax; k++) {
                    g = new Genome(i, j, k, new Pool(k, k, k));
                    g.generateNetwork();

                    if (g.getInputs() != i)
                        fail("Input nodes differ from input" + g.getInputs() + " " + i);
                    if (g.getOutputs() != j)
                        fail("Outputs nodes differ from input" + g.getOutputs() + " " + j);
                    if (g.getGeneration() != k)
                        fail("Generation differs from input" + g.getGeneration() + " " + k);
                    if (g.getNetwork().Neurons.size() != i + j) {
                        fail("Networks neurons differs from Output+Input" + g.getNetwork().Neurons.size() + " " + i + j);
                    }
                }
            }
        }

    }

    @Test
    public void initializePool() {
        Pool test = new Pool(input, output);
        if (test.generation != 0) {
            fail("Generation is grater than 0");
        }
        if (test.alreadyMeasured()) {
            fail("Pool already measured");
        }
    }

    @Test
    public void Mutateandgenerategenomes() {

        Pool test = new Pool(input, output);
        int countgenomes = 0;
        for (int i = 0; i < 100; i++) {
            int j = test.currentSpecies;
            Genome temp = test.Species.get(test.currentSpecies - 1).Genomes.get(test.currentGenome - 1);
            temp.generateNetwork();
            test.nextGenome();
            for (Gene g : temp.Genes) {
                if (g.getOut() != g.getInto() && g.getWeigth() != 0) {
                    countgenomes++;
                }
            }
        }
        System.out.println(countgenomes + " valid Genes generated");
        if (countgenomes <= 1) {
            fail("To less Genes generated");
        }
    }

    @Test
    public void OrTest() {

        Pool test = new Pool(2, 1);

        int iterations = 0;

        while (iterations < 100000) {
            test.nextGenome();
            while (test.alreadyMeasured()) {
                test.nextGenome();


            }
            iterations++;
            double input[] = new double[2];
            double result[] = new double[1];

            test.currentGenome()
                    .generateNetwork();
            result = test.currentGenome()
                    .step(input);

            if (result[0] <= 0.0) {
                System.out.println("First");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            input[0] = 1;
            result = test.currentGenome()
                    .step(input);

            if (result[0] > 0.0) {
                System.out.println("Second");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            input[1] = 1;
            result = test.currentGenome()
                    .step(input);
            if (result[0] > 0.0) {
                System.out.println("Third");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }
            input[0] = 0;
            result = test.currentGenome()
                    .step(input);
            if (result[0] > 0.0) {
                System.out.println("Fourth");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            if (test.currentGenome()
                    .getFitness() >= 40) {
                break;
            }


        }

        System.out.println("Done in " + iterations + " iterations");

    }

    @Test
    public void Or2Test() {

        Pool test = new Pool(2, 2);

        int iterations = 0;

        while (iterations < 100000) {
            test.nextGenome();
            while (test.alreadyMeasured()) {
                test.nextGenome();


            }
            iterations++;
            double input[] = new double[2];
            double result[] = new double[1];

            test.currentGenome()
                    .generateNetwork();
            result = test.currentGenome()
                    .step(input);

            if (result[0] <= 0.0 && result[1] <= 0.0) {
                // System.out.println("First");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            input[0] = 1;
            result = test.currentGenome()
                    .step(input);

            if (result[0] > 0.0 && result[1] <= 0.0) {
                // System.out.println("Second");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            input[1] = 1;
            result = test.currentGenome()
                    .step(input);
            if (result[0] > 0.0 && result[1] > 0.0) {
                // System.out.println("Third");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }
            input[0] = 0;
            result = test.currentGenome()
                    .step(input);
            if (result[0] > 0.0 && result[1] <= 0.0) {
                // System.out.println("Fourth");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            if (test.currentGenome()
                    .getFitness() >= 40) {
                break;
            }


        }

        System.out.println("Done in " + iterations + " iterations");

    }

    @Test
    public void Benchmark() {

        long start = System.currentTimeMillis();
        int iterations = 0;
        for (int act = 0; act < 20; act++) {
            for (int gen = 0; gen < 20; gen++) {
                
                if(HyperNeat.wrongConfig(act, gen)) {
                    break;
                }
                iterations = 0;
                start = System.currentTimeMillis();
                for (int i = 0; i < 100; i++) {
                    int z = checkOrTest(act, gen);
                    if (z == Integer.MAX_VALUE || (System.currentTimeMillis() - start) / 1000 > 120) {
                        iterations = Integer.MAX_VALUE;
                        i = 9999999;
                        break;
                    }
                    iterations += z;// checkOrTest();
                }
                System.out.println(
                        "Done NormalTest in " + iterations / 100 + " iterations by Activation " + act + " and Generation " + gen + " took "
                                + ((System.currentTimeMillis() - start) / 1000));
                Pool.log(
                        "Done NormalTest in " + iterations / 100 + " iterations by Activation " + act + " and Generation " + gen + " took "
                                + ((System.currentTimeMillis() - start) / 1000));
                
                if (iterations == Integer.MAX_VALUE) {
                    System.out.println(
                            "Not Done SpecialTest in " + iterations / 100 + " iterations by Activation " + act + " and Generation " + gen + " took "
                                    + ((System.currentTimeMillis() - start) / 1000));
                    Pool.log(
                            "Not Done SpecialTest in " + iterations / 100 + " iterations by Activation " + act + " and Generation " + gen + " took "
                                    + ((System.currentTimeMillis() - start) / 1000));
                } else {
                    iterations = 0;
                    start = System.currentTimeMillis();
                    for (int i = 0; i < 100; i++) {
                        int z = checkSpecialTest(act, gen);
                        if (z == Integer.MAX_VALUE || (System.currentTimeMillis() - start) / 1000 > 120) {
                            iterations = Integer.MAX_VALUE;
                            i = 9999999;
                            break;
                        }
                        iterations += z;// checkOrTest();

                    }
                    System.out.println(
                            "Done SpecialTest in " + iterations / 100 + " iterations by Activation " + act + " and Generation " + gen + " took "
                                    + ((System.currentTimeMillis() - start) / 1000));
                    Pool.log(
                            "Done SpecialTest in " + iterations / 100 + " iterations by Activation " + act + " and Generation " + gen + " took "
                                    + ((System.currentTimeMillis() - start) / 1000));
                }
            }
        }



    }

    private int checkOrTest(int act, int gen) {
        long started = System.currentTimeMillis();
        neat.Pool test = new neat.Pool(5, 1);

        // Input outpit hidden xsize y size
        HyperNeat hyperNeat = new HyperNeat(2, 1, 1, 1, 1, test);
        hyperNeat.ACTIVATIONFUNCTION = act;
        hyperNeat.GENERATIONFUNCTION = gen;

        int iterations = 0;

        while (iterations < 100000) {
            test.nextGenome();
            while (test.alreadyMeasured()) {
                test.nextGenome();


            }
            iterations++;
            double input[] = new double[2];
            double result[] = new double[1];

            test.currentGenome()
                    .generateNetwork();
            hyperNeat.generateweigths(test.currentGenome());
            result = hyperNeat.step(input);

            if (result[0] <= 0.0) {
                // System.out.println("First");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            input[0] = 1;
            result = hyperNeat.step(input);

            if (result[0] > 0.0) {
                // System.out.println("Second");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            input[1] = 1;
            result = hyperNeat.step(input);
            if (result[0] > 0.0) {
                // System.out.println("Third");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }
            input[0] = 0;
            result = hyperNeat.step(input);
            if (result[0] > 0.0) {
                // System.out.println("Fourth");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            if (test.currentGenome()
                    .getFitness() >= 40) {
                // System.out.println("DONE: "+iterations);
                break;
            }
            if (isLongerThanXSeconds(started)) {
                iterations = Integer.MAX_VALUE;
                return iterations;
            }


        }
        return iterations;
    }


    private int checkSpecialTest(int act, int gen) {
        long started = System.currentTimeMillis();
        neat.Pool test = new neat.Pool(5, 1);
        // Input outpit hidden xsize y size
        HyperNeat hyperNeat = new HyperNeat(9, 1, 1, 3, 3, test);
        int iterations = 0;
        hyperNeat.ACTIVATIONFUNCTION = act;
        hyperNeat.GENERATIONFUNCTION = gen;

        while (iterations < 100000) {
            test.nextGenome();
            while (test.alreadyMeasured()) {
                test.nextGenome();


            }
            iterations++;
            double input[] = new double[9];
            double result[] = new double[1];

            test.currentGenome()
                    .generateNetwork();
            hyperNeat.generateweigths(test.currentGenome());
            result = hyperNeat.step(input);

            if (result[0] <= 0.0) {
                // System.out.println("First");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            input[0] = 1;
            input[1] = 1;
            input[2] = 1;

            result = hyperNeat.step(input);

            if (result[0] > 0.0) {
                // System.out.println("Second");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            input[0] = 14;
            input[1] = 0;
            input[2] = 0;
            input[4] = 11;
            input[8] = 71;

            result = hyperNeat.step(input);

            if (result[0] > 0.0) {
                // System.out.println("Second");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            input[0] = 0;
            input[1] = 0;
            input[2] = 0;
            input[3] = 11;
            input[4] = 11;
            input[5] = 12;
            input[6] = 0;
            input[7] = 0;
            input[8] = 0;

            input[1] = 1;
            result = hyperNeat.step(input);
            if (result[0] > 0.0) {
                // System.out.println("Third");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            input[0] = 0;
            input[1] = 0;
            input[2] = 0;
            input[3] = 0;
            input[4] = 0;
            input[5] = 0;
            input[6] = 31;
            input[7] = 31;
            input[8] = 11;

            input[1] = 1;
            result = hyperNeat.step(input);
            if (result[0] > 0.0) {
                // System.out.println("Third");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            input[0] = 0;
            input[1] = 51;
            input[2] = 0;
            input[3] = 0;
            input[4] = 11;
            input[5] = 0;
            input[6] = 0;
            input[7] = 31;
            input[8] = 0;

            input[1] = 1;
            result = hyperNeat.step(input);
            if (result[0] > 0.0) {
                // System.out.println("Third");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            input[0] = 0;
            input[1] = 0;
            input[2] = 81;
            input[3] = 0;
            input[4] = 0;
            input[5] = 91;
            input[6] = 0;
            input[7] = 0;
            input[8] = 11;

            input[1] = 1;
            result = hyperNeat.step(input);
            if (result[0] > 0.0) {
                // System.out.println("Third");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            input[0] = 61;
            input[1] = 14;
            input[2] = 31;
            input[3] = 1;
            input[4] = 1;
            input[5] = 31;
            input[6] = 1;
            input[7] = 11;
            input[8] = 1;

            input[1] = 1;
            result = hyperNeat.step(input);
            if (result[0] <= 0.0) {
                // System.out.println("Third");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            input[0] = 51;
            input[1] = 31;
            input[2] = 0;
            input[3] = 1;
            input[4] = 21;
            input[5] = 51;
            input[6] = 71;
            input[7] = 1;
            input[8] = 1;

            input[1] = 1;
            result = hyperNeat.step(input);
            if (result[0] <= 0.0) {
                // System.out.println("Third");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }

            input[0] = 1;
            input[1] = 0;
            input[2] = 1;
            input[3] = 1;
            input[4] = 0;
            input[5] = 1;
            input[6] = 0;
            input[7] = 1;
            input[8] = 1;

            result = hyperNeat.step(input);
            if (result[0] <= 0.0) {
                // System.out.println("Third");
                test.currentGenome()
                        .setFitness(
                                test.currentGenome()
                                        .getFitness() + 10);
            }


            if (test.currentGenome()
                    .getFitness() >= 90) {
                // System.out.println("DONE: "+iterations);
                break;
            }

            if (isLongerThanXSeconds(started)) {
                iterations = Integer.MAX_VALUE;
                return iterations;
            }


        }

        return iterations;
    }
    // @Test
    // public void Neatrunnigntest(){
    // Pool pool = new Pool(4,1);
    // Random r = new Random();
    // for(int j = 0;j<999999;j++){
    // for(int i = 0;i<999999;i++){
    // pool.nextGenome();
    // pool.Species.get(pool.currentSpecies-1).Genomes.get(pool.currentGenome-1).setFitness(
    // r.nextInt(-300 - Integer.MIN_VALUE + 1) + Integer.MIN_VALUE);
    // System.out.println(pool.getbest().getFitness());
    // } }}

    private boolean isLongerThanXSeconds(long started) {
        return System.currentTimeMillis() - started > 10000;
    }
}


