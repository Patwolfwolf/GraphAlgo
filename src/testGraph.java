import java.util.ArrayList;
import java.util.PriorityQueue;

public class testGraph {

	public static void main(String[] args) {
		PriorityQueue<Integer> pq = new PriorityQueue();
		pq.add(1);
		pq.add(4);
		pq.add(10);
		pq.add(7);
		pq.add(77);
		pq.add(3);
		System.out.println(pq.toString());
		int size = pq.size();
		ArrayList<Integer> a = new ArrayList();
		Integer d = new Integer(10);
		for (int i = 0; i < size; i++){
			Integer j = pq.poll();
			System.out.println(j);
			if (j.equals(d)){
				pq.add(11);
				pq.addAll(a);
				break;
			}
			else {
				a.add(j);
				System.out.println(a.toString());
			}
		}
		System.out.println(pq.toString());
		
	}

}
