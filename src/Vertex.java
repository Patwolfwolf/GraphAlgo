
public class Vertex {
	public String label;
	public Double distance;
	public Vertex parent;
	public boolean finish;


	public Vertex(String label) {
		super();
		this.label = label;
		this.distance = Double.MAX_VALUE;
		this.parent = null;
		this.finish = false;

	}	


	public int hashCode() {
		return label.hashCode();
	}


	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vertex)){
			return false;
		}
		Vertex object = (Vertex) obj;
		if (this.label == null) {
			if (object.label != null)
				return false;
		} else if (!label.equals(object.label)){
			return false;
		}
		return true;
	}


}
