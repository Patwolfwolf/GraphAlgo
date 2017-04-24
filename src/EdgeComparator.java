import java.util.Comparator;

public class EdgeComparator  implements Comparator<Edge>{

	@Override
	public int compare(Edge o1, Edge o2) {
		if (o1.getWeight() < o2.getWeight()){
			return -1;
		}
		else if (o1.getWeight() > o2.getWeight()){
			return 1;
		}
		else{
			return o1.getTarget().label.compareTo(o2.getTarget().label);
		}
	}

}
