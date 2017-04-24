import java.util.HashMap;

public class DisjointSets<E>{

	private HashMap<E, DSNode> nodes;


	public DisjointSets(Iterable<E> items){
		nodes = new HashMap<E, DSNode>();
		for (E item : items){
			makeSet(item);
		}
	}


	public boolean makeSet(E item){
		if (!nodes.containsKey(item)){
			DSNode node = new DSNode(item);
			nodes.put(item, node);
			return true;
		}
		return false;
	}


	public DSNode findRep(DSNode a){
		if (a.parent == a){
			return a;
		}
		else{
			a.parent = findRep(a.parent);
			return a.parent;
		}
	}


	public boolean sameSet(E a, E b){
		return findRep(nodes.get(a)) == findRep(nodes.get(b));
	}


	public void union(E a, E b){
		if (this.sameSet(a, b)){
			return;
		}
		DSNode repA = this.findRep(nodes.get(a));
		DSNode repB = this.findRep(nodes.get(b));
		if (repA.rank > repB.rank){
			repB.parent = repA;
		}
		else if (repA.rank == repB.rank){
			repB.parent = repA;
			repA.rank++;
		}
		else{
			repA.parent = repB.parent;
		}
	}


	class DSNode{
		E item;
		int rank;
		DSNode parent;

		public DSNode(E item) {
			this.item = item;
			this.parent = this;
			this.rank = 0;
		}

	}

}
