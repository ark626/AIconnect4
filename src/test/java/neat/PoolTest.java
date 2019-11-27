package neat;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.Test;

import aiAlgorithmes.neat.Genome;
import aiAlgorithmes.neat.Pool;
import aiAlgorithmes.neat.Species;
import testUtils.testUtils;

public class PoolTest {

    
    @Test
    public void testThatSpeciesCollectionsSort(){
        
        Pool pool = createTestPool();
        

        pool.rankGlobally();
        assertThat(pool.Species.get(0).calculateAverageFitness(), is(18L));
        assertThat(pool.Species.get(1).calculateAverageFitness(), is(15L));
        assertThat(pool.Species.get(2).calculateAverageFitness(), is(12L));
        assertThat(pool.Species.get(3).calculateAverageFitness(), is(10L));
        assertThat(pool.Species.get(4).calculateAverageFitness(), is(9L));
        
        Collections.sort(pool.Species);
        
        assertThat(pool.Species.get(0).calculateAverageFitness(), is(9L));
        assertThat(pool.Species.get(1).calculateAverageFitness(), is(10L));
        assertThat(pool.Species.get(2).calculateAverageFitness(), is(12L));
        assertThat(pool.Species.get(3).calculateAverageFitness(), is(15L));
        assertThat(pool.Species.get(4).calculateAverageFitness(), is(18L));

    }
    
    @Test
    public void testThatPoolCalculatesAverageFitness() {

        Pool pool = createTestPool();

        pool.rankGlobally();;
        long result = pool.totalAverageFitness();
        assertThat(result, is(64L));
        
        result = pool.getTopfitness();
        assertThat(result, is(16L));

    }
    
    @Test
    public void testThatPoolranKingWorks(){
         
         Pool pool = testUtils.getSortPool();
         pool.rankGlobally();
         for (Species s : pool.Species) {
             s.calculateAverageFitness();
             
         }
         pool.rankGlobally();
         
         Collections.sort(pool.Species);
         Collections.sort(pool.Species.get(0).Genomes,Genome.Comparators.DESCENDING);
         Collections.sort(pool.Species.get(1).Genomes,Genome.Comparators.DESCENDING);
         Collections.sort(pool.Species.get(2).Genomes,Genome.Comparators.DESCENDING);
         Collections.sort(pool.Species.get(3).Genomes,Genome.Comparators.DESCENDING);
         
         assertThat(pool.Species.size(), is(5));
         assertThat(pool.Species.get(0).Genomes.size(), is(5));
         assertThat(pool.Species.get(0).calculateAverageFitness(), is(9L));
         assertThat(pool.Species.get(0).Genomes.get(0).getFitness(), is(16L));
         assertThat(pool.Species.get(0).Genomes.get(1).getFitness(), is(12L));
         assertThat(pool.Species.get(0).Genomes.get(2).getFitness(), is(8L));
         assertThat(pool.Species.get(0).Genomes.get(3).getFitness(), is(4L));
         assertThat(pool.Species.get(0).Genomes.get(4).getFitness(), is(1L));
         
         assertThat(pool.Species.get(1).Genomes.size(), is(5));
         assertThat(pool.Species.get(1).calculateAverageFitness(), is(10L));
         assertThat(pool.Species.get(1).Genomes.get(0).getFitness(), is(12L));
         assertThat(pool.Species.get(1).Genomes.get(1).getFitness(), is(9L));
         assertThat(pool.Species.get(1).Genomes.get(2).getFitness(), is(6L));
         assertThat(pool.Species.get(1).Genomes.get(3).getFitness(), is(3L));
         assertThat(pool.Species.get(1).Genomes.get(4).getFitness(), is(1L));
         
         assertThat(pool.Species.get(2).Genomes.size(), is(5));
         assertThat(pool.Species.get(2).calculateAverageFitness(), is(12L));
         assertThat(pool.Species.get(2).Genomes.get(0).getFitness(), is(8L));
         assertThat(pool.Species.get(2).Genomes.get(1).getFitness(), is(6L));
         assertThat(pool.Species.get(2).Genomes.get(2).getFitness(), is(4L));
         assertThat(pool.Species.get(2).Genomes.get(3).getFitness(), is(2L));
         assertThat(pool.Species.get(2).Genomes.get(4).getFitness(), is(1L));
         
         assertThat(pool.Species.get(3).Genomes.size(), is(5));
         assertThat(pool.Species.get(3).calculateAverageFitness(), is(15L));
         assertThat(pool.Species.get(3).Genomes.get(0).getFitness(), is(4L));
         assertThat(pool.Species.get(3).Genomes.get(1).getFitness(), is(3L));
         assertThat(pool.Species.get(3).Genomes.get(2).getFitness(), is(2L));
         assertThat(pool.Species.get(3).Genomes.get(3).getFitness(), is(1L));
         assertThat(pool.Species.get(3).Genomes.get(4).getFitness(), is(1L));
     
     }
    
    @Test
    public void testThatCullSpecies(){
         
         Pool pool = testUtils.getSortPool();
         pool.rankGlobally();
         pool.cullSpecies(false);
         Collections.sort(pool.Species);
         Collections.sort(pool.Species.get(0).Genomes,Genome.Comparators.DESCENDING);
         Collections.sort(pool.Species.get(1).Genomes,Genome.Comparators.DESCENDING);
         Collections.sort(pool.Species.get(2).Genomes,Genome.Comparators.DESCENDING);
         Collections.sort(pool.Species.get(3).Genomes,Genome.Comparators.DESCENDING);
         
         assertThat(pool.Species.size(), is(5));
         assertThat(pool.Species.get(0).Genomes.size(), is(3));
         assertThat(pool.Species.get(0).calculateAverageFitness(), is(17L));
         assertThat(pool.Species.get(0).Genomes.get(0).getFitness(), is(1L));
         assertThat(pool.Species.get(0).Genomes.get(1).getFitness(), is(1L));
         assertThat(pool.Species.get(0).Genomes.get(2).getFitness(), is(1L));
         
         assertThat(pool.Species.get(1).Genomes.size(), is(3));
         assertThat(pool.Species.get(1).calculateAverageFitness(), is(11L));
         assertThat(pool.Species.get(1).Genomes.get(0).getFitness(), is(4L));
         assertThat(pool.Species.get(1).Genomes.get(1).getFitness(), is(3L));
         assertThat(pool.Species.get(1).Genomes.get(2).getFitness(), is(2L));
         
         assertThat(pool.Species.get(2).Genomes.size(), is(3));
         assertThat(pool.Species.get(2).calculateAverageFitness(), is(7L));
         assertThat(pool.Species.get(2).Genomes.get(0).getFitness(), is(8L));
         assertThat(pool.Species.get(2).Genomes.get(1).getFitness(), is(6L));
         assertThat(pool.Species.get(2).Genomes.get(2).getFitness(), is(4L));
         
         assertThat(pool.Species.get(3).Genomes.size(), is(3));
         assertThat(pool.Species.get(3).calculateAverageFitness(), is(4L));
         assertThat(pool.Species.get(3).Genomes.get(0).getFitness(), is(12L));
         assertThat(pool.Species.get(3).Genomes.get(1).getFitness(), is(9L));
         assertThat(pool.Species.get(3).Genomes.get(2).getFitness(), is(6L));
     
     }
    
    @Test
    public void testThatCullSpeciesTrue(){
         
         Pool pool = testUtils.getSortPool();
         pool.rankGlobally();
         pool.cullSpecies(true);
         Collections.sort(pool.Species);
         Collections.sort(pool.Species.get(0).Genomes,Genome.Comparators.DESCENDING);
         Collections.sort(pool.Species.get(1).Genomes,Genome.Comparators.DESCENDING);
         Collections.sort(pool.Species.get(2).Genomes,Genome.Comparators.DESCENDING);
         Collections.sort(pool.Species.get(3).Genomes,Genome.Comparators.DESCENDING);
         
         assertThat(pool.Species.size(), is(5));
         assertThat(pool.Species.get(0).Genomes.size(), is(1));
         assertThat(pool.Species.get(0).calculateAverageFitness(), is(16L));
         assertThat(pool.Species.get(0).Genomes.get(0).getFitness(), is(1L));
         
         assertThat(pool.Species.get(1).Genomes.size(), is(1));
         assertThat(pool.Species.get(1).calculateAverageFitness(), is(9L));
         assertThat(pool.Species.get(1).Genomes.get(0).getFitness(), is(4L));
         
         assertThat(pool.Species.get(2).Genomes.size(), is(1));
         assertThat(pool.Species.get(2).calculateAverageFitness(), is(5L));
         assertThat(pool.Species.get(2).Genomes.get(0).getFitness(), is(8L));
         
         assertThat(pool.Species.get(3).Genomes.size(), is(1));
         assertThat(pool.Species.get(3).calculateAverageFitness(), is(2L));
         assertThat(pool.Species.get(3).Genomes.get(0).getFitness(), is(12L));
     
     }

    
    @Test
    public void testThatPoolCullingWorks(){
         
         Pool pool = testUtils.getSortPool();
         
         pool.cullSpecies(false);
         pool.rankGlobally();
         pool.removeStaleSpecies();
         pool.rankGlobally();

         for (Species s : pool.Species) {
             s.calculateAverageFitness();
         }

         pool.removeWeakSpecies();
         for (Species s : pool.Species) {
             s.calculateAverageFitness();
             
         }
         
         pool.cullSpecies(true);
         
         Collections.sort(pool.Species);
         Collections.sort(pool.Species.get(0).Genomes,Genome.Comparators.DESCENDING);
         Collections.sort(pool.Species.get(1).Genomes,Genome.Comparators.DESCENDING);
         Collections.sort(pool.Species.get(2).Genomes,Genome.Comparators.DESCENDING);
         Collections.sort(pool.Species.get(3).Genomes,Genome.Comparators.DESCENDING);
         
         assertThat(pool.Species.size(), is(5));
         assertThat(pool.Species.get(0).Genomes.size(), is(1));
         assertThat(pool.Species.get(0).calculateAverageFitness(), is(1L));
         assertThat(pool.Species.get(0).Genomes.get(0).getFitness(), is(16L));
         
         assertThat(pool.Species.get(1).Genomes.size(), is(1));
         assertThat(pool.Species.get(1).calculateAverageFitness(), is(2L));
         assertThat(pool.Species.get(1).Genomes.get(0).getFitness(), is(12L));
         
         assertThat(pool.Species.get(2).Genomes.size(), is(1));
         assertThat(pool.Species.get(2).calculateAverageFitness(), is(5L));
         assertThat(pool.Species.get(2).Genomes.get(0).getFitness(), is(8L));
         
         assertThat(pool.Species.get(3).Genomes.size(), is(1));
         assertThat(pool.Species.get(3).calculateAverageFitness(), is(9L));
         assertThat(pool.Species.get(3).Genomes.get(0).getFitness(), is(4L));
     
     }
    


    private Pool createTestPool() {
        return testUtils.getSortPool();
    }

  
}
