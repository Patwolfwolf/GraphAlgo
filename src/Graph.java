import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Graph {
	private HashMap<Vertex, List<Edge>> edgeMap;

	public Graph(String filename) throws FileNotFoundException{
		edgeMap = new HashMap<Vertex, List<Edge>>();
		FileInputStream read = new FileInputStream(filename);
		Scanner input = new Scanner(read);
		while(input.hasNext()){
			String source = input.next();
			String target = input.next();
			Double weight = input.nextDouble();
			addEdge(source, target, weight);
		}
		input.close();
	}

	public Graph() {
		edgeMap = new HashMap<Vertex, List<Edge>>();
	}

	public void addEdge(String src, String tgt, double w){
		Vertex srcVtx = this.getVertex(src);
		Vertex tgtVtx = this.getVertex(tgt);
		if (srcVtx == null){
			srcVtx = new Vertex(src);
			List<Edge> edges = new ArrayList<Edge>();
			edgeMap.put(srcVtx, edges);
		}
		if (tgtVtx == null){
			tgtVtx = new Vertex(tgt);
			List<Edge> edgest = new ArrayList<Edge>();
			edgeMap.put(tgtVtx, edgest);
		}
		Edge edge = new Edge(srcVtx, tgtVtx, w);
		edgeMap.get(srcVtx).add(edge);
	}

	public Vertex getVertex(String label){
		for (Vertex curr : this.getVertices()){
			if (curr.label.equals(label)){
				return curr;
			}
		}
		return null;
	}

	public List<Edge> getAdjacent(Vertex src){
		if (edgeMap.containsKey(src)){
			return Collections.unmodifiableList(edgeMap.get(src));
		}
		else{
			return null;
		}
	}

	public Set<Vertex> getVertices(){
		return Collections.unmodifiableSet(edgeMap.keySet());
	}

	public String[] getLabels(){
		Set<Vertex> allVtx = this.getVertices();
		String[] allLabels = new String[allVtx.size()];
		int i = 0;
		for (Vertex vtx : allVtx){
			allLabels[i] = vtx.label;
			i++;
		}
		return allLabels;
	}

	public double[][] getMatrix(){
		HashMap<String, Integer> labels = new HashMap<String, Integer>();
		String[] labelByInt = this.getLabels();
		int i = 0;
		for(String vtxLabel : labelByInt){
			labels.put(vtxLabel, i);
			i++;
		}
		int size = labelByInt.length;
		double[][] weightMatrix = new double[size][size];
		for (int r = 0; r < size; r++){
			for (int c = 0; c < size; c++){
				if(r == c){
					weightMatrix[r][c] = 0;
				}
				else{
					weightMatrix[r][c] = Double.MAX_VALUE;
				}
			}
		}
		for (Edge e : this.edges()){
			int r = labels.get(e.getSource().label);
			int c = labels.get(e.getTarget().label);
			weightMatrix[r][c] = e.getWeight();
		}
		return weightMatrix;
	}


	public List<Edge> edges(){
		List<Edge> edges = new ArrayList<Edge>(); 
		for (List<Edge> list : edgeMap.values()){
			edges.addAll(list);
		}
		return edges;
	}



}
