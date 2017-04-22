package pl.edu.agh.bo.cvrp;

public class Node {
    private int id;
    private int demand;

    public Node(int id, int demand) {
        this.id = id;
        this.demand = demand;
    }

    public int getId() {
        return id;
    }

    public int getDemand() {
        return demand;
    }
}
