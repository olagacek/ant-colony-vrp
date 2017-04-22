package pl.edu.agh.bo.cvrp;

public class Edge {
    private int weight;
    private Node nodeA;
    private Node nodeB;

    public Edge(int weight, Node nodeA, Node nodeB) {
        this.weight = weight;
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    public int getWeight() {
        return weight;
    }

    public Node getNodeA() {
        return nodeA;
    }

    public Node getNodeB() {
        return nodeB;
    }
}
