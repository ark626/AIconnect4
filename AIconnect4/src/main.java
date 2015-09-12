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
	Pool p = new Pool(4,1);
	try {
		p = Pool.load("/tmp/Hyper/generator.ki");
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
	
	
	for(int i = 0;i<60000;i++){
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
	for(int j = 0;j<10;j++){
		g.reset();
		g.run(i, dis, j);
	}
	}
	
	
	System.out.println("Started saving");

	

		p.save("/tmp/Hyper/generator.ki", 0);
		for(Species s :p.Species){
			for(Genome ge:s.Genomes){
				//g.generateNetwork();
				try {
					neat.visualizer.visualize(ge, "Hyper/Generator Species "+p.Species.indexOf(s)+" Genome "+s.Genomes.indexOf(ge));
					test.generateweigths(ge);
					hyperneat.visualizer.visualize(test, "Hyper/HN Species "+p.Species.indexOf(s)+" Genome "+s.Genomes.indexOf(ge),p.Species.indexOf(s),s.Genomes.indexOf(ge));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	try {
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	System.out.println("DONE");
	
	
	
	
}}
	