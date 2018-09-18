package neatPrinter;

import java.io.File;
import hyperneat.HyperNeat;
import neat.Genome;
import neat.Pool;
import neat.Species;

public class neatPrinter {
    
    public static void main(String args[]) throws ClassNotFoundException{
        
        neatGenerator( "C:/tmp/tmp","C:/Users/VDINGER/git/mario-ai/MARIOAI.ki");
    }
    
    public static void hyperNeatGenerator(String filePathPictures, String filePathOrigin ) throws ClassNotFoundException  {
      
        Pool p = new Pool(4, 1);
        HyperNeat test = new HyperNeat(42, 3, 0, 7, 6, p);

        p = Pool.load(filePathOrigin);

        
        File file = new File(filePathPictures);
        
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }

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
                    if (Genome > 999) {
                        null2 = "";
                    } else {
                        if (Genome > 99) {
                            null2 = "0";
                        } else {
                            if (Genome > 9) {
                                null2 = "00";
                            } else {
                                null2 = "000";
                            }

                        }
                    }
                    neat.visualizer.visualize(ge,filePathPictures+ "/HyperGenerator/Generator Species " + null1 + Spec + " Genome " + null2 + Genome);
                    test.generateweigths(ge);
                    hyperneat.visualizer.visualize(test, filePathPictures+"Hyper/HN Species " + null1 + Spec + " Genome " + null2 + Genome, p.Species.indexOf(s), s.Genomes.indexOf(ge));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void neatGenerator(String filePathPictures, String filePathOrigin ) throws ClassNotFoundException  {
        
        Pool p = new Pool(4, 1);

        p = Pool.load(filePathOrigin);

        
        File file = new File(filePathPictures);
        
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }

        String null1 = "";
        String null2 = "";

        for (Species s : p.Species) {
            for (Genome ge : s.Genomes) {
                ge.generateNetwork();
                try {
                    // te.step(input);
                    int Spec = (p.Species.indexOf(s));
                    int Genome = s.Genomes.indexOf(ge);
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
                    if (Genome > 999) {
                        null2 = "";
                    } else {
                        if (Genome > 99) {
                            null2 = "0";
                        } else {
                            if (Genome > 9) {
                                null2 = "00";
                            } else {
                                null2 = "000";
                            }

                        }
                    }
                    neat.visualizer.visualize(ge,filePathPictures+ "/Species " + null1 + Spec + " Genome " + null2 + Genome);

               } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


}
