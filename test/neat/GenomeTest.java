package neat;

import org.junit.Test;
import testUtils.testUtils;

public class GenomeTest {

    

    @Test
   public void testThatGenomeWillGoStep(){
        Genome genome = testUtils.getTestGenome();
        genome.generateNetwork();
        double[] result = genome.step(new double[5]);
        
        assert(result.length ==5);
    }

}
