//import static org.junit.Assert.fail;
//import java.io.IOException;
//import org.junit.Test;
//
//import eshyperneat.EsHyperNeat;
//import hyperneat.HyperNeat;
//import neat.Gene;
//import neat.Genome;
//import neat.Pool;
//
//public class test {
//
//    @Test
//    public void Estest() {
//        Pool pool = new Pool(5, 1);
//        EsHyperNeat eh = new EsHyperNeat(16, 1, 2, pool);
//        Genome g = null;
//        boolean notdone = true;
//        while (notdone) {
//            while (pool.alreadyMeasured()) {
//                pool.nextGenome();
//            }
//            g = pool.Species.get(pool.currentSpecies - 1).Genomes.get(pool.currentGenome - 1);
//            g.generateNetwork();
//            eh.generateweigths(g);
//            String s = "From 0 to ";
//            s += eh.Neurons.get(0).Neurons[0].getOutgoing().get(0).getInto();
//            s += eh.Neurons.get(1).Neurons[eh.Neurons.get(0).Neurons[0].getOutgoing().get(0).getInto()];
//
//            System.out.println(s);
//            or(eh);
//            if (g.getFitness() >= 5) {
//                notdone = false;
//                for (Gene ge : g.Genes) {
//                    System.out.println("Gen: " + ge.getInto() + " " + ge.getOut() + " " + ge.getWeigth());
//                    System.out.println("Solution found after: " + pool.generation);
//                }
//            }
//        }
//
//    }
//
//    @Test
//    public void Teststarter() {
//        boolean notdone = true;
//        Pool p = new Pool(4, 1);
//        HyperNeat h = new HyperNeat(2, 1, 0, 2, 1, p);// (int Input,int Output, int Hidden, int
//                                                      // xsize,int ysize,Pool pool){
//
//
//        Genome g = null;
//        while (notdone) {
//            while (p.alreadyMeasured()) {
//                p.nextGenome();
//            }
//            // System.out.println("Test");
//            g = p.Species.get(p.currentSpecies - 1).Genomes.get(p.currentGenome - 1);
//            g.generateNetwork();
//            h.generateweigths(g);
//            or(h);
//
//            // System.out.println(p.generation+" "+g.fitness);
//
//            if (g.getFitness() >= 5) {
//                notdone = false;
//                for (Gene ge : g.Genes) {
//                    System.out.println("Gen: " + ge.getInto() + " " + ge.getOut() + " " + ge.getWeigth());
//                    System.out.println("Solution found after: " + p.generation);
//                }
//            }
//        }
//        p.save("./","TEST.KI", 0);
//        try {
//            Pool p2 = Pool.load("./TEST.KI");
//            HyperNeat h2 = new HyperNeat(2, 1, 0, 2, 1, p);
//            h2.generateweigths(g);
//            if (!h.equals(h2)) {
//                fail("Dont equal");
//            }
//        } catch (ClassNotFoundException e) {
//            // TODO Auto-generated catch block
//            fail(e.getMessage());
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void TeststarterNeat() {
//        boolean notdone = true;
//        Pool p = new Pool(2, 1);
//        // HyperNeat h = new HyperNeat(2,1,0,2,1,p);//(int Input,int Output, int Hidden, int
//        // xsize,int ysize,Pool pool){
//
//        Genome g = null;
//        while (notdone) {
//            while (p.alreadyMeasured()) {
//                p.nextGenome();
//            }
//            // System.out.println("Test");
//            g = p.Species.get(p.currentSpecies - 1).Genomes.get(p.currentGenome - 1);
//            g.generateNetwork();
//            or(g);
//
//            // System.out.println(p.generation+" "+g.fitness);
//
//            if (g.getFitness() >= 5) {
//                notdone = false;
//                for (Gene ge : g.Genes) {
//                    System.out.println("Gen: " + ge.getInto() + " " + " " + " " + " " + ge.getOut() + " " + ge.getWeigth());
//                    System.out.println("Solution found after: " + p.generation);
//                }
//            }
//        }
//    }
//
//
//    public static double[] teststep(HyperNeat h, double[] input) {
//        return h.step(input);
//    }
//
//    public static double[] teststep(Genome h, double[] input) {
//        return h.step(input);
//    }
//
//    public static void or(Genome g) {
//        String s = "";
//        int met = 12;
//        double[] inputs = new double[2];
//        int fitness = 1;
//        inputs[0] = 0;
//        inputs[1] = 0;
//        if (g.step(inputs, met)[0] == 0.0) {
//            fitness += 1;
//            s += "1";
//        }
//        inputs[0] = 1;
//        inputs[1] = 0;
//        if (g.step(inputs, met)[0] == 1.0) {
//            fitness += 1;
//            s += "2";
//        }
//        inputs[0] = 0;
//        inputs[1] = 1;
//        if (g.step(inputs, met)[0] == 1.0) {
//            fitness += 1;
//            s += "3";
//        }
//        inputs[0] = 1;
//        inputs[1] = 1;
//        if (g.step(inputs, met)[0] == 1.0) {
//            fitness += 1;
//            s += "4";
//        }
//        // System.out.println(s);
//        // System.out.println(g.step(inputs,9)[0]);
//        g.setFitness(fitness);
//    }
//
//    public static void or(HyperNeat g) {
//        String s = "";
//        double[] inputs = new double[2];
//        int fitness = 1;
//        inputs[0] = 0;
//        inputs[1] = 0;
//        if (g.step(inputs)[0] == 0.0) {
//            fitness += 3;
//            s += "1";
//        }
//        inputs[0] = 1;
//        inputs[1] = 0;
//        if (g.step(inputs)[0] == 1.0) {
//            fitness += 1;
//            s += "2";
//        }
//        inputs[0] = 0;
//        inputs[1] = 1;
//        if (g.step(inputs)[0] == 1.0) {
//            fitness += 1;
//            s += "3";
//        }
//        inputs[0] = 1;
//        inputs[1] = 1;
//        if (g.step(inputs)[0] == 1.0) {
//            fitness += 1;
//            s += "4";
//        }
//        System.out.println(s);
//        // System.out.println(g.step(inputs,9)[0]);
//        g.getPool().Species.get(g.getPool().currentSpecies - 1).Genomes.get(g.getPool().currentGenome - 1).setFitness(fitness);
//    }
//
//    public static void or(EsHyperNeat g) {
//        String s = "";
//        double[] inputs = new double[2];
//        int fitness = 1;
//        inputs[0] = 0;
//        inputs[1] = 0;
//        if (g.step(inputs)[0] == 0.0) {
//            fitness += 3;
//            s += "1";
//        }
//        inputs[0] = 1;
//        inputs[1] = 0;
//        if (g.step(inputs)[0] == 1.0) {
//            fitness += 1;
//            s += "2";
//        }
//        inputs[0] = 0;
//        inputs[1] = 1;
//        if (g.step(inputs)[0] == 1.0) {
//            fitness += 1;
//            s += "3";
//        }
//        inputs[0] = 1;
//        inputs[1] = 1;
//        if (g.step(inputs)[0] == 1.0) {
//            fitness += 1;
//            s += "4";
//        }
//        System.out.println(s);
//        // System.out.println(g.step(inputs,9)[0]);
//        g.pool.Species.get(g.pool.currentSpecies - 1).Genomes.get(g.pool.currentGenome - 1).setFitness(fitness);
//    }
//}
