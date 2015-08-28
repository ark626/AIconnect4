import ki.KI;
import ki.KICluster;
import ki.Link;
import ki.Node;
import ki.visualizer;


public class main {
	public static void main(String[] args){
		String start = "Started with: ";
		for(String s :args){
			start += " "+s;
		}
		System.out.println(start);
		KICluster test = new KICluster(40,4,4,2);

		Player a = new Player(1,1);
		Player b= new Player();
		a.ki = null;
		//a.ki.isTesting = true;
		//a.initialize();
		b.ki = test.ranking.get(test.next());
		b.ki.isTesting = true;
	//	Game g = new Game(a,b);
		try{
		test = KICluster.load("/tmp/GLaDoS.ki");
		}
		catch (Exception e){
			
		}
		for(int i = 9000;i>0;i--){
			test.ranking.get(0).mutate(10);
		}
		try {
			for(int i=0;i<test.ranking.size();i++){
			ki.visualizer.visualize(test.ranking.get(i),"GLaDoS"+i);
			}
			System.out.println("DONE");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for(int i = 0;i<999999;i++){
//		b.ki.mutate();
//		}
		try {
			
			ki.visualizer.visualize(b.ki,"Test");
			
			System.out.println("DONE");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(KI k : test.ranking){
			k.gen = 0;
		}
		test.save("/tmp/GLaDoS.ki", 0);
		
	}
}
//		int round = 0;
//		for(int j = 0;j<300000;j++){
//		for(int i = 0;i<5000;i++){
//		//gameDisplay.main(b.ki);
//		g.run(round++);
//		g.reset();
//		int id = test.next();
//	//	System.out.println("ID: "+id);
//		b.ki = test.ranking.get(id);
//		b.ki.isTesting = true;
//		
//		}
//		g.run(round++);
//	g.reset();
////		for(int i=0;i<999;i++){
////		b.ki.mutate();
////		}
//		//b.ki.Links.add(new Link(b.ki.Nodes.get(2),b.ki.Nodes.get(40),1.0));
//	    test.rank();
//		test.save("/tmp/GLaDoS.ki", 0);
//
//		try {
//			for(int i=0;i<test.ranking.size();i++){
//			ki.visualizer.visualize(test.ranking.get(i),"GLaDoS"+i);
//			}
//			System.out.println("DONE");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		}
//	}
//
//}
