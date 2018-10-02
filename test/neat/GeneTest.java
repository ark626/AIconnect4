package neat;

import org.junit.Test;

public class GeneTest {
    
    @Test
   public void testThatGeneCopyWorks(){
        Gene gene = new Gene();
        
        gene.setWeigth(0.0);
        gene.setExcess(1);
        gene.setOut(5);
        
        Gene copy = gene.copyGene();
        
        assert(copy.getWeigth() == 0.0);
        assert(copy.isExcess() == 1);
        assert(copy.getOut()==5);
    }
    
    

}
