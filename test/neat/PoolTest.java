package neat;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.Test;
import testUtils.testUtils;

public class PoolTest {

    
    @Test
    public void testThatSpeciesCrossover(){
        
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

    private Pool createTestPool() {
        return testUtils.getSortPool();
    }

  
}
