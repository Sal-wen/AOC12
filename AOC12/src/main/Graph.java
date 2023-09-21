package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Vertex, List<Vertex>> adjVertices;
     Graph(){
    	adjVertices=new HashMap<Vertex, List<Vertex>>();
    }
    
    void addVertex(String label) {
        getAdjVertices().putIfAbsent(new Vertex(label), new ArrayList<>());
    }

    void removeVertex(String label) {
        Vertex v = new Vertex(label);
        getAdjVertices().values().stream().forEach(e -> e.remove(v));
        getAdjVertices().remove(new Vertex(label));
    }
    void addEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        boolean test=getAdjVertices().get(v1).add(v2);
        boolean test2=getAdjVertices().get(v2).add(v1);
        System.out.println(test+" "+test2);
    }
    // standard constructor, getters, setters

	public Map<Vertex, List<Vertex>> getAdjVertices() {
		return adjVertices;
	}
}
