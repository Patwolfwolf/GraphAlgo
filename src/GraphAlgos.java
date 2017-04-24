import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class GraphAlgos {

	private static DecimalFormat f = new DecimalFormat("###.###");

	public static void dfs(Graph graph, String source){
		for (Vertex v : graph.getVertices()){
			v.finish = false;
			v.distance = Double.MAX_VALUE;
			v.parent = null;
		}
		Vertex curr = graph.getVertex(source);
		curr.finish = true;
		curr.distance = 0.0;
		dfsRe(graph, curr);

	}

	private static void dfsRe(Graph graph, Vertex curr){
		System.out.print(curr.label + ":" + curr.distance + " ");
		for (Edge edges : graph.getAdjacent(curr)){
			Vertex tag = edges.getTarget();
			if ( !tag.finish ){
				tag.finish = true;
				tag.distance = curr.distance + 1;
				dfsRe(graph, tag);
			}
		}
	}


	public static void dfsNonRec(Graph graph, String source){
		for (Vertex v : graph.getVertices()){
			v.finish = false;
			v.distance = Double.MAX_VALUE;
			v.parent = null;
		}
		Stack<Vertex> stack = new Stack<Vertex>();
		Vertex curr = graph.getVertex(source);
		stack.add(curr);
		curr.distance = 0.0;
		curr.finish = true;

		while (!stack.isEmpty()){
			curr = stack.pop();
			System.out.print(curr.label + ":" + curr.distance + " ");
			curr.finish = true;
			for (Edge edges : graph.getAdjacent(curr)){
				Vertex nebor = edges.getTarget();
				if (!nebor.finish){
					nebor.finish = true;
					nebor.distance = curr.distance + 1;
					stack.add(nebor);
				}
			}
		}
	}


	public static void bfs(Graph graph, String source){
		for (Vertex v : graph.getVertices()){
			v.finish = false;
			v.distance = Double.MAX_VALUE;
			v.parent = null;
		}
		Queue<Vertex> queue = new LinkedList<Vertex>();
		Vertex curr = graph.getVertex(source);
		queue.add(curr);
		curr.distance = 0.0;
		curr.finish = true;
		while (!queue.isEmpty()){
			curr = queue.poll();
			System.out.print(curr.label + ":" + curr.distance + " ");
			curr.finish = true;
			for (Edge edges : graph.getAdjacent(curr)){
				Vertex nebor = edges.getTarget();
				if (!nebor.finish){
					nebor.finish = true;
					nebor.distance = curr.distance + 1;
					queue.add(nebor);
				}
			}
		}
		System.out.println();
	}

	public static void dijkstra(Graph graph, String source){
		for (Vertex v : graph.getVertices()){
			v.distance = Double.MAX_VALUE;
			v.parent = null;
			v.finish = false;
		}
		Vertex src = graph.getVertex(source);
		src.distance = 0.0;
		VertexComparator comp = new VertexComparator();
		PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>(graph.getVertices().size(), comp);
		for (Vertex v : graph.getVertices()){
			pq.add(v);
		}
		while (!pq.isEmpty()){
			Vertex curr = pq.poll();
			curr.finish = true;
			for (Edge edges : graph.getAdjacent(curr)){
				Vertex nebor = edges.getTarget();
				if ((!nebor.finish) && nebor.distance > (curr.distance + edges.getWeight())){
					pq.remove(nebor);
					nebor.distance = curr.distance + edges.getWeight();
					nebor.parent = curr;
					pq.add(nebor);
				}
			} 
			printPath(src, curr);
			printPathRec(src, curr);
		}
		System.out.println();
	}


	private static void printPath(Vertex source, Vertex vertex){
		if (vertex.parent != null){
			System.out.println();
			String print = vertex.label;
			double distance = 0.0;
			while (vertex.parent != null){
				print = print + "--" + f.format(vertex.distance - vertex.parent.distance) + "--> " + vertex.parent.label;
				distance = distance + vertex.distance;
				vertex = vertex.parent;
			}
			System.out.println( print + " ( total length " + f.format(distance) + " )");
		}
	}


	private static void printPathRec(Vertex source, Vertex vertex){
		if (source == vertex.parent){
			System.out.print(source.label + "--" + f.format(vertex.distance - vertex.parent.distance) + "--> " +  vertex.label);
		}
		else if (vertex.parent != null) {
			printPathRec(source, vertex.parent);
			System.out.print( "--" + f.format(vertex.distance) + "--> " +  vertex.label );
		}
	}


	public static Graph prim(Graph graph, String source){
		for (Vertex v : graph.getVertices()){
			v.distance = Double.MAX_VALUE;
			v.parent = null;
			v.finish = false;
		}
		Vertex src = graph.getVertex(source);
		src.distance = 0.0;
		VertexComparator comp = new VertexComparator();
		PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>(graph.getVertices().size(), comp);
		for (Vertex v : graph.getVertices()){
			pq.add(v);
		}
		Graph mst = new Graph();
		while (!pq.isEmpty()){
			Vertex curr = pq.poll();
			curr.finish = true;
			if (curr.parent != null){
				mst.addEdge(curr.label, curr.parent.label, curr.distance);
			}
			for (Edge edges : graph.getAdjacent(curr)){
				Vertex nebor = edges.getTarget();
				if ((!nebor.finish) && (nebor.distance > edges.getWeight())){
					pq.remove(nebor);
					nebor.distance = edges.getWeight();
					nebor.parent = curr;
					pq.add(nebor);
				}
			}
		}
		return mst;
	}


	public static void printMST(Graph mst){
		double total = 0;
		for (Vertex v : mst.getVertices()){
			for (Edge e : mst.getAdjacent(v)){
				System.out.println(e.getSource().label + "--" + e.getWeight() + "--" + e.getTarget().label);
				total = total + e.getWeight();
			}
		}
		System.out.println("Total Weight: " + total);
	}


	public static Graph kruskal(Graph graph){
		EdgeComparator comp = new EdgeComparator();
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>(graph.edges().size(), comp);
		DisjointSets<Vertex> djs = new DisjointSets<Vertex>(graph.getVertices());
		for (Vertex v : graph.getVertices()){
			djs.makeSet(v);
		}
		for (Edge e : graph.edges()){
			pq.add(e);
		}
		Graph mst = new Graph();
		while(!pq.isEmpty()){
			Edge e = pq.poll();
			if (!djs.sameSet(e.getSource(), e.getTarget())){
				mst.addEdge(e.getSource().label, e.getTarget().label, e.getWeight());
				djs.union(e.getSource(), e.getTarget());
			}
		}
		return mst;
	}


	public static int[][] initPredecessor(double[][] D){
		int[][] P = new int[D.length][D.length];
		for (int i = 0; i < D.length; i++){
			for (int j = 0; j < D.length; j++){
				if (D[i][j] < Double.MAX_VALUE){
					P[i][j] = i;
				}
				else{
					P[i][j] = -1;
				}
			}
		}
		return P;
	}


	private static void floydWarshall(double[][] D, int[][] P){
		for (int k = 0; k < D.length; k++){
			for (int i = 0; i < D.length; i++){
				for (int j = 0; j < D.length; j++){
					if (D[i][k] + D[k][j] < D[i][j]){
						D[i][j] = D[i][k] + D[k][j];
						P[i][j] = P[k][j];
					}
				}
			}
		}
	}


	public static void floydWarshall (Graph G){
		double[][] D = G.getMatrix();
		int[][] P = initPredecessor(D);
		floydWarshall(D, P);
		printAllPaths(D, P, G.getLabels());
	}


	public static void printAllPaths(double[][] D, int[][] P, String[] labels){
		for (int i = 0; i < D.length; i++){
			for (int j = 0; j < D.length; j++){
				if (i != j){
					printPath(i, j, D, P, labels);
				}
			}
		}
	}


	public static void printPath(int i, int j, double[][] D, int[][] P, String[] labels){
		System.out.print(labels[i]);
		int k = j;
		while (labels[i] != labels[P[i][j]]){
			System.out.print("--" + f.format(D[i][j] - D[i][P[i][j]]) + "-->" + labels[P[i][j]]);
			j = P[i][j];
		}
		System.out.print("--" + f.format(D[i][j] - D[i][P[i][j]]) + "-->" + labels[k]);
		System.out.println();
	}


	public static void main(String[] args) {
		if (args[0].equals("-bfs")){
			try{
				Graph graph = new Graph(args[1]);
				bfs(graph, args[2]);
			}
			catch(FileNotFoundException e){
				e.printStackTrace();
			}
		}
		else if (args[0].equals("-dfs")){
			try{
				Graph graph = new Graph(args[1]);
				dfsNonRec(graph, args[2]);
			}
			catch(FileNotFoundException e){
				e.printStackTrace();
			}
		}
		else if (args[0].equals("-dfsRe")){
			try{
				Graph graph = new Graph(args[1]);
				dfs(graph, args[2]);
			}
			catch(FileNotFoundException e){
				e.printStackTrace();
			}
		}
		else if (args[0].equals("-dijkstra")){
			try{
				Graph graph = new Graph(args[1]);
				dijkstra(graph, args[2]);
			}
			catch(FileNotFoundException e){
				e.printStackTrace();
			}
		}
		else if (args[0].equals("-prim")){
			try{
				Graph graph = new Graph(args[1]);
				Graph mst = prim(graph, args[2]);
				printMST(mst);
			}
			catch(FileNotFoundException e){
				e.printStackTrace();
			}
		}
		else if (args[0].equals("-kruskal")){
			try{
				Graph graph = new Graph(args[1]);
				Graph mst = kruskal(graph);
				printMST(mst);
			}
			catch(FileNotFoundException e){
				e.printStackTrace();
			}
		}
		else if (args[0].equals("-floydwarshall")){
			try{
				Graph graph = new Graph(args[1]);
				floydWarshall(graph);
			}
			catch(FileNotFoundException e){
				e.printStackTrace();
			}
		}
	}

}
