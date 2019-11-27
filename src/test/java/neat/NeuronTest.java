package neat;

import org.junit.Test;

import aiAlgorithmes.neat.Gene;
import aiAlgorithmes.neat.Neuron;

public class NeuronTest {
    
    @Test
   public void testThatNeuronCanAddGene(){
        Neuron neuron = new Neuron();
        Gene gene1 = new Gene();
        Gene gene2 = new Gene();
        
        neuron.addIncoming(gene1);
        neuron.addOutgoing(gene2);      
        
        assert(neuron.getIncoming().size() == 1);
        assert(neuron.getIncoming().get(0).equals(gene1));
        assert(neuron.getOutgoing().size() == 1);
        assert(neuron.getOutgoing().get(0).equals(gene2));
    }

}
