
public class Edge {
	private Vertex source;
	private Vertex target;
	private double weight;
	
	public Edge(Vertex source, Vertex target, double weight) {
		super();
		this.source = source;
		this.target = target;
		this.weight = weight;
	}
	
	public Vertex getSource() {
		return source;
	}
	
	public Vertex getTarget() {
		return target;
	}
	
	public double getWeight() {
		return weight;
	}
	
}
