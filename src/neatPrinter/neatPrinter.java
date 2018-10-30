package neatPrinter;

import java.io.File;
import java.io.IOException;
import hyperneat.HyperNeat;
import neat.Genome;
import neat.Pool;
import neat.Species;

public class neatPrinter {
    
    public static void main(String args[]) throws ClassNotFoundException, IOException{
        
        //neatGenerator( "C:/tmp/tmp","C:/Users/VDINGER/git/mario-ai/MARIOAI.ki");
        Pool p = Pool.load("C:/tmp/MARIOHYPERAI.ki");
        showFitnessEtc(p);
    }
    
    public static void hyperNeatGenerator(String filePathPictures, String filePathOrigin ) throws ClassNotFoundException, IOException  {
      
        Pool p = new Pool(4, 1);
        HyperNeat test = new HyperNeat(42, 3, 0, 7, 6, p);

        p = Pool.load(filePathOrigin);

        
        File file = deleteFilesAndCheckPath(filePathPictures);

        deleteRest(filePathPictures, file);


        printHyperNeat(filePathPictures, p, test);
    }

    private static void printHyperNeat(String filePathPictures, Pool p, HyperNeat test) {
        String null1 = "";
        String null2 = "";
        double[] input = new double[7];

        for (Species s : p.Species) {
            for (Genome ge : s.Genomes) {
                ge.generateNetwork();
                try {
                    test.step(input);
                    // te.step(input);
                    int Spec = (p.Species.indexOf(s));
                    int Genome = s.Genomes.indexOf(ge);
                    null1 = calculateNullString(Spec);
                    null2 = calculateNullString(Genome);
                    neat.visualizer.visualize(ge,generateImagePath(filePathPictures, null1, null2, Spec, Genome,"/HyperGenerator/Generator Species "));
                    test.generateweigths(ge);
                    hyperneat.visualizer.visualize(test, generateImagePath(filePathPictures, null1, null2, Spec, Genome,"Hyper/HN Species "), p.Species.indexOf(s), s.Genomes.indexOf(ge));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private static String generateImagePath(String filePathPictures, String null1, String null2, int Spec, int Genome,String name) {
        return filePathPictures+ name + null1 + Spec + " Genome " + null2 + Genome;
    }

    private static String calculateNullString(int Spec) {
        String null1;
        if (Spec > 999) {
            null1 = "";
        } else {
            if (Spec > 99) {
                null1 = "0";
            } else {
                if (Spec > 9) {
                    null1 = "00";
                } else {
                    null1 = "000";
                }

            }
        }
        return null1;
    }

    private static void deleteRest(String filePathPictures, File file) {
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }
        file = new File(filePathPictures+"/HyperGenerator");

        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }
    }
    
    public static void neatGenerator(String filePathPictures, String filePathOrigin ) throws ClassNotFoundException, IOException  {
        
        Pool p = new Pool(4, 1);

        p = Pool.load(filePathOrigin);

        
        deleteFilesAndCheckPath(filePathPictures);

        printNeat(filePathPictures, p);
    }

    private static void printNeat(String filePathPictures, Pool p) {
        String null1 = "";
        String null2 = "";

        for (Species s : p.Species) {
            for (Genome ge : s.Genomes) {
                ge.generateNetwork();
                try {
                    // te.step(input);
                    int Spec = (p.Species.indexOf(s));
                    int Genome = s.Genomes.indexOf(ge);
                    null1 = calculateNullString(Spec);
                    null2 = calculateNullString(Genome);
                    File file2 = new File(filePathPictures+ "/Species " + null1 + Spec );
                    file2.mkdir();
                    neat.visualizer.visualize(ge,generateImagePath(filePathPictures, null1, null2, Spec, Genome,"/Neat/Species "));

               } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private static File deleteFilesAndCheckPath(String filePathPictures) {
        File file = new File(filePathPictures);
        
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }
        return file;

    }
    
    public static void showFitnessEtc(Pool p){
        
        String averageFitness = "";
        int i = 0;
        for(Species s:p.Species){
            int j = 0;
            averageFitness+= i +" Average: "+ s.calculateAverageFitness()+" Top:"+ s.getTopFitness();
            System.out.println("Species: "+i++ +" Average: "+ s.calculateAverageFitness()+" Top:"+ s.getTopFitness());
            String genomes = "";
            for(Genome g:s.Genomes){
                genomes += j++ +": "+g.getFitness() + " Generation "+ g.getGeneration()+ " Ino: "+g.getInnovation()+"| ";
                
            }
            System.out.println(genomes);
        }
        
        System.out.println(averageFitness);
    }

}
