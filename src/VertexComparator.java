import java.util.Comparator;

public class VertexComparator implements Comparator<Vertex> {

	@Override
	public int compare(Vertex arg0, Vertex arg1) {
		if (arg0.distance < arg1.distance){
			return -1;
		}
		else if (arg0.distance > arg1.distance){
			return 1;
		}
		else if (arg0.distance == arg0.distance){
			return 0;
		}

		else{
			return arg0.label.compareTo(arg1.label);
		}
	}

}
