package testUtils;

import java.util.ArrayList;
import neat.Genome;
import neat.Pool;
import neat.Species;

public class testUtils {

    public static Genome getTestGenome(){
        Pool pool = new Pool( 5, 5);
        Genome genome = new Genome(5, 5, 1, pool);
        return genome;
    }
    
    public static Pool getExamplePool(){
        
        Pool pool = new Pool(5, 5);
        Species species = new Species(5,5,pool);
        
        
        Genome genomeA = new Genome(5,5,1,pool);
        Genome genomeB = new Genome(5,5,1,pool); 
        Genome genomeC = new Genome(5,5,1,pool);
        
        genomeA.setGlobalRank(-10);//setFitness(-10);
        genomeB.setGlobalRank(1);
        genomeC.setGlobalRank(50);
        
        ArrayList<Genome> genomes = new ArrayList<Genome>();
        genomes.add(genomeB);
        genomes.add(genomeC);
        genomes.add(genomeA);
        
        species.setGenomes(genomes);
        return pool;
    }
    
    public static Pool getPool(ArrayList<Species> specieses){
        
        Pool pool = new Pool(5, 5, 5);
        pool.Species = specieses;
        
        for(Species species:specieses){
            species.setP(pool);
            for(Genome genome:species.Genomes){
                genome.setParent(pool);
                
            }
        }
        
        return pool;
    }
    
    public static Genome getGenome(long fitness){
        
        Genome genome = new Genome(5, 5, 1);
        genome.setFitness(fitness);
        return genome;
        
    }
    
    public static Species getSpecies(ArrayList<Genome> genomes){
        
        Species species = new Species(5, 5, null);
        species.setGenomes(genomes);
        return species;
        
    }
    

}
