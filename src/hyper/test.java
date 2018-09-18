package hyper;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;



public class test {
	static int input = 10;
	static int output = 10;
	static int geninmax=100;
	static int genoutmax=100;
	static int gengenmax=100;
	
     @Test
     public void initializeGenome(){
     Genome g = null;
     for(int i=1;i<geninmax;i++){
     for(int j=1;j<genoutmax;j++){
     for(int k=0;k<gengenmax;k++){
     g = new Genome(i, j, k,new Pool(k, k, k));
     g.generateNetwork();
    
     if(g.getInputs()!=i)
     fail("Input nodes differ from input" + g.getInputs()+" "+i);
     if(g.getOutputs()!=j)
     fail("Outputs nodes differ from input" + g.getOutputs()+" "+j);
     if(g.getGeneration()!=k)
     fail("Generation differs from input" + g.getGeneration()+" "+k);
     if(g.getNetwork().Neurons.length!=i+j){
     fail("Networks neurons differs from Output+Input"+g.getNetwork().Neurons.length+" "+i+j);
     }
     }
     }
     }
    
     }
    
     @Test
     public void initializePool(){
     Pool test = new Pool(input,output);
     if(test.generation!=0){
     fail("Generation is grater than 0");
     }
     if(test.alreadyMeasured()){
     fail("Pool already measured");
     }
     }
    
     @Test
     public void Mutateandgenerategenomes(){
    
     Pool test = new Pool(input,output);
     int countgenomes = 0;
     for(int i = 0;i<100;i++){
     int j = test.currentSpecies;
     Genome temp = test.Species.get(test.currentSpecies-1).Genomes.get(test.currentGenome-1);
     temp.generateNetwork();
     test.nextGenome();
     for(Gene g:temp.Genes){
     if(g.getOut() != g.getInto()&&g.getWeigth()!=0){
     countgenomes++;
     }
     }
     }
     System.out.println(countgenomes+" valid Genes generated");
     if(countgenomes <=1){
     fail("To less Genes generated");
     }
     }
     
     @Test
     public void OrTest(){
    
     Pool test = new Pool(2,1);

     int iterations = 0;
     
     while(iterations<100000){
         test.nextGenome(); 
         while(test.alreadyMeasured()){
             test.nextGenome(); 
             
            
         }
         iterations ++;
         double input[] = new double[2];
         double result[] = new double[1];

         test.currentGenome().generateNetwork();
         result = test.currentGenome().evaluateNetwork(input);
         
         if(result[0] <= 0.0){
             System.out.println("First");
             test.currentGenome().setFitness(test.currentGenome().getFitness()+10);
         }
         
         input[0] = 1;
         result = test.currentGenome().evaluateNetwork(input);
         
         if(result[0] > 0.0){
             System.out.println("Second");
             test.currentGenome().setFitness(test.currentGenome().getFitness()+10);
         }
         
         input[1] = 1;
         result = test.currentGenome().evaluateNetwork(input);
         if(result[0] > 0.0){
             System.out.println("Third");
             test.currentGenome().setFitness(test.currentGenome().getFitness()+10);
         }
         input[0] = 0;
         result = test.currentGenome().evaluateNetwork(input);
         if(result[0] > 0.0){
             System.out.println("Fourth");
             test.currentGenome().setFitness(test.currentGenome().getFitness()+10);
         }
         
         if(test.currentGenome().getFitness()>=40){
             break;
         }
         
         
     }
     
     System.out.println("Done in "+ iterations + " iterations");
     
     }
    
     @Test
     public void Or2Test(){
    
     Pool test = new Pool(2,2);

     int iterations = 0;
     
     while(iterations<100000){
         test.nextGenome(); 
         while(test.alreadyMeasured()){
             test.nextGenome(); 
             
            
         }
         iterations ++;
         double input[] = new double[2];
         double result[] = new double[1];

         test.currentGenome().generateNetwork();
         result = test.currentGenome().evaluateNetwork(input);
         
         if(result[0] <= 0.0&&result[1] <=0.0){
           //  System.out.println("First");
             test.currentGenome().setFitness(test.currentGenome().getFitness()+10);
         }
         
         input[0] = 1;
         result = test.currentGenome().evaluateNetwork(input);
         
         if(result[0] > 0.0&&result[1]<=0.0){
           //  System.out.println("Second");
             test.currentGenome().setFitness(test.currentGenome().getFitness()+10);
         }
         
         input[1] = 1;
         result = test.currentGenome().evaluateNetwork(input);
         if(result[0] > 0.0&&result[1] >0.0){
           //  System.out.println("Third");
             test.currentGenome().setFitness(test.currentGenome().getFitness()+10);
         }
         input[0] = 0;
         result = test.currentGenome().evaluateNetwork(input);
         if(result[0] > 0.0&&result[1]<=0.0){
            // System.out.println("Fourth");
             test.currentGenome().setFitness(test.currentGenome().getFitness()+10);
         }
         
         if(test.currentGenome().getFitness()>=40){
             break;
         }
         
         
     }
     
     System.out.println("Done in "+ iterations + " iterations");
     
     }
//     @Test
//     public void Neatrunnigntest(){
//     Pool pool = new Pool(4,1);
//     Random r = new Random();
//     for(int j = 0;j<999999;j++){
//     for(int i = 0;i<999999;i++){
//     pool.nextGenome();
//     pool.Species.get(pool.currentSpecies-1).Genomes.get(pool.currentGenome-1).setFitness(
//     r.nextInt(-300 - Integer.MIN_VALUE + 1) + Integer.MIN_VALUE);
//     System.out.println(pool.getbest().getFitness());
//     } }}
	}


