package neat;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import org.junit.Test;
import testUtils.testUtils;

public class PoolTest {

    @Test
    public void testThatPoolCalculatesAverageFitness() {

        ArrayList<Species> species = new ArrayList<>();

        for (int i = 0; i < 5; i++) {


            ArrayList<Genome> genomes = new ArrayList<>();

            for (int z = 0; z < 5; z++) {
                genomes.add(testUtils.getGenome(z * i));

            }

            species.add(testUtils.getSpecies(genomes));
        }

        Pool pool = testUtils.getPool(species);

        pool.rankGlobally();;
        long result = pool.totalAverageFitness();
        assertThat(result, is(63L));
        
        result = pool.getTopfitness();
        assertThat(result, is(16L));

    }

  
}
