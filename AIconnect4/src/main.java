import hyperneat.HyperNeat;
import neat.Genome;
import neat.Pool;
import neat.Species;
import ki.KI;
import ki.KICluster;
import ki.Link;
import ki.Node;
import ki.visualizer;


public class main {
	public static void main(String[] args){
		System.out.println(-9999>-800);
	Pool p = new Pool(4,1);
	try {
		p = Pool.load("/KI/tmp/Hyper/generator.ki");
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	if(p == null){
		p = new Pool(4,1);
	}
	HyperNeat test = new HyperNeat(42,3,0,7,6,p);
	Player a = new Player(4,4);
	Player b = new Player(1,1);
	a.pool = p;
    Genome  current = p.Species.get(p.currentSpecies-1).Genomes.get(p.currentGenome-1);
	a.h = test;
	gameDisplay dis = null;//gameDisplay.main(current, 0);
	Game g = new Game(a,b);
	int Iteration = 0;
	long started = System.currentTimeMillis();
	long round = System.currentTimeMillis();
	long it = System.currentTimeMillis();
	long total = Runtime.getRuntime().totalMemory();
	long used  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	while(2>1){
		Iteration++;
		it = System.currentTimeMillis();
	for(int i = 0;i<Integer.parseInt(args[0]);i++){
		if(i%1000 == 0){
			 total = Runtime.getRuntime().totalMemory();
			 used  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			 
			System.out.println("Run: "+i+" Iteration: "+Iteration +" Iterating: "+args[0]+" Batching: "+args[1]+" Runtime: "+(System.currentTimeMillis()-round)/1000+" Iterationtime: "+(System.currentTimeMillis()-it)/1000+" Total: "+(System.currentTimeMillis()-started)/1000);
			System.out.println("Total: "+total+" used "+used);
			round = System.currentTimeMillis();
		}
		p.currentGenome = 1;
		p.currentSpecies = 1;
	while(p.alreadyMeasured()){
		p.nextGenome();
	}
	current = p.Species.get(p.currentSpecies-1).Genomes.get(p.currentGenome-1);
	current.generateNetwork();
	test.generateweigths(current);
	
	if(p.alreadyMeasured()){
	while(p.alreadyMeasured()){
		p.nextGenome();
		current = p.Species.get(p.currentSpecies-1).Genomes.get(p.currentGenome-1);
		current.generateNetwork();
		test.generateweigths(current);
	}
	}
	
	
	//dis.drawPanel.g = current;
	
	//dis.drawPanel.g = p.Species.get(p.currentSpecies).Genomes.get(p.currentGenome);
	for(int j = 0;j<Integer.parseInt(args[1]);j++){
		g.reset();
		g.run(i, dis, j);
	}
	}
	
	
	System.out.println("Started saving");

	
		String null1 = "";
		String null2 = "";
		double[] input = new double[7];
		p.save("/KI/tmp/Hyper/generator.ki", 0);
		for(Species s :p.Species){
			for(Genome ge:s.Genomes){
				//g.generateNetwork();
				try {
					test.step(input);
					int Spec = (p.Species.indexOf(s));
					int Genome = s.Genomes.indexOf(ge);
					if(Spec >999){
						null1 = "";
					}
					else{
						if(Spec > 99){
							null1 = "0";
						}
						else{
							if(Spec > 9){
								null1 = "00";
							}
							else{
								null1= "000";
							}
							
						}
					}
					if(Genome >999){
						null2 = "";
					}
					else{
						if(Genome > 99){
							null2 = "0";
						}
						else{
							if(Genome > 9){
								null2 = "00";
							}
							else{
								null2= "000";
							}
							
						}
					}
					neat.visualizer.visualize(ge, "/var/www/PICTURES/HyperGenerator/Generator Species "+null1+Spec+" Genome "+null2+Genome);
					test.generateweigths(ge);
					hyperneat.visualizer.visualize(test, "/var/www/PICTURES/Hyper/HN Species "+null1+Spec+" Genome "+null2+Genome,p.Species.indexOf(s),s.Genomes.indexOf(ge));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

Genome best = p.getbest();
System.out.println("Best fitness: "+best.fitness);
best.generateNetwork();
test.generateweigths(best);
g.reset();

g.path = "/var/www/PICTURES/bestGame/";
g.testplay = 1;
g.run(1, dis, 1);
g.testplay = 0;
	
	System.out.println("DONE");
	
	
	}
	
}}
	