package neat;


import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.Test;
import testUtils.testUtils;

public class SpeciesTest {
    
    @Test
   public void testThatSpeciesCalculatesAverageFitness(){
        
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
        
        long result = species.calculateAverageFitness();
        assertThat(result, is(13L));
    
    }
    
    @Test
   public void testThatSpeciesCrossover(){
        
        Pool pool = testUtils.getSortPool();
        pool.rankGlobally();
        Collections.sort(pool.Species);
        Collections.sort(pool.Species.get(0).Genomes,Genome.Comparators.DESCENDING);
        Collections.sort(pool.Species.get(1).Genomes,Genome.Comparators.DESCENDING);
        Collections.sort(pool.Species.get(2).Genomes,Genome.Comparators.DESCENDING);
        Collections.sort(pool.Species.get(3).Genomes,Genome.Comparators.DESCENDING);
        
        assertThat(pool.Species.size(), is(5));
        assertThat(pool.Species.get(0).Genomes.size(), is(5));
        assertThat(pool.Species.get(0).calculateAverageFitness(), is(18L));
        assertThat(pool.Species.get(0).Genomes.get(0).getFitness(), is(1L));
        assertThat(pool.Species.get(0).Genomes.get(1).getFitness(), is(1L));
        assertThat(pool.Species.get(0).Genomes.get(2).getFitness(), is(1L));
        assertThat(pool.Species.get(0).Genomes.get(3).getFitness(), is(1L));
        assertThat(pool.Species.get(0).Genomes.get(4).getFitness(), is(1L));
        
        assertThat(pool.Species.get(1).Genomes.size(), is(5));
        assertThat(pool.Species.get(1).calculateAverageFitness(), is(15L));
        assertThat(pool.Species.get(1).Genomes.get(0).getFitness(), is(4L));
        assertThat(pool.Species.get(1).Genomes.get(1).getFitness(), is(3L));
        assertThat(pool.Species.get(1).Genomes.get(2).getFitness(), is(2L));
        assertThat(pool.Species.get(1).Genomes.get(3).getFitness(), is(1L));
        assertThat(pool.Species.get(1).Genomes.get(4).getFitness(), is(1L));
        
        assertThat(pool.Species.get(2).Genomes.size(), is(5));
        assertThat(pool.Species.get(2).calculateAverageFitness(), is(12L));
        assertThat(pool.Species.get(2).Genomes.get(0).getFitness(), is(8L));
        assertThat(pool.Species.get(2).Genomes.get(1).getFitness(), is(6L));
        assertThat(pool.Species.get(2).Genomes.get(2).getFitness(), is(4L));
        assertThat(pool.Species.get(2).Genomes.get(3).getFitness(), is(2L));
        assertThat(pool.Species.get(2).Genomes.get(4).getFitness(), is(1L));
        
        assertThat(pool.Species.get(3).Genomes.size(), is(5));
        assertThat(pool.Species.get(3).calculateAverageFitness(), is(10L));
        assertThat(pool.Species.get(3).Genomes.get(0).getFitness(), is(12L));
        assertThat(pool.Species.get(3).Genomes.get(1).getFitness(), is(9L));
        assertThat(pool.Species.get(3).Genomes.get(2).getFitness(), is(6L));
        assertThat(pool.Species.get(3).Genomes.get(3).getFitness(), is(3L));
        assertThat(pool.Species.get(3).Genomes.get(4).getFitness(), is(1L));
    
    }


}
