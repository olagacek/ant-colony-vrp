package main.java.pl.edu.agh.bo.cvrp;

import java.util.Set;

public class Graph {
    private Set<Edge> edges;
    private Set<Node> nodes;

    public Graph(Set<Edge> edges, Set<Node> nodes) {
        this.edges = edges;
        this.nodes = nodes;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public Set<Node> getNodes() {
        return nodes;
    }
}
