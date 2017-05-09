package main.java.pl.edu.agh.bo.cvrp;


import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private int maxCapacity;
    private Node currentNode;
    private List<Node> currentPath;

    public Vehicle(int maxCapacity, Node startNode) {
        this.maxCapacity = maxCapacity;
        this.currentNode = startNode;
        currentPath = new ArrayList<Node>();
        currentPath.add(startNode);
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public List<Node> getCurrentPath() {
        return currentPath;
    }

    public void addNodeToPath(Node node){
        currentPath.add(node);
        currentNode = node;
    }
}
