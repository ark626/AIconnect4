package neat;

import java.util.ArrayList;
import database.Situation;

public interface DataBaseAddOn {
    
    public void setFitnessOfNewSituation(long fitness);
    
    public boolean preEvaluateGenome(Genome genome) ;
    
    

}
