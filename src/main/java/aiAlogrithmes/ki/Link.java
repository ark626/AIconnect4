package aiAlogrithmes.ki;

public class Link implements java.io.Serializable,Comparable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double value;
	private int from;
	private int to;
	
	public Link(int from, int to, double value){
		this.from = from;
		this.to = to;
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}
	
	public int checkType(){
		//Unterteilen der Links in 3 Typen für die 3 Layer
		//1. => Input zu Hidden 2 => Hidden zu Output 3 => Input zu Output
		//TODO: Proper chgeck
		return 0;
	}
	
	public int compareTo(Object o) {
	
			int comp = ( ((Link)o).getTo()-this.getTo() );

			return comp;
	
	}
	
	
	

}
