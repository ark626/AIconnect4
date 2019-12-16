package neat;


import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.Test;

import aiAlgorithmes.neat.Genome;
import aiAlgorithmes.neat.Pool;
import aiAlgorithmes.neat.Species;
import testUtils.testUtils;

public class SpeciesTest {
    
    @Test
   public void testThatSpeciesCalculatesAverageFitness(){
        
        Pool pool = new Pool(5, 5,300);
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
        
        long result = species.calculateAverageFitness();
        assertThat(result, is(13L));
    
    }
    
   

}
