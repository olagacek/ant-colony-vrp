package pl.edu.agh.bo.cvrp;

import java.util.Set;

public class CapacitatedVehicleRoutingProblem {
    private Node startingNode;
    private Node finishNode;
    private Graph nodes;
    private Set<Vehicle> vehicles;

    public CapacitatedVehicleRoutingProblem(Node startingNode, Node finishNode, Graph nodes, Set<Vehicle> vehicles) {
        this.startingNode = startingNode;
        this.finishNode = finishNode;
        this.nodes = nodes;
        this.vehicles = vehicles;
    }

    public Node getStartingNode() {
        return startingNode;
    }

    public Node getFinishNode() {
        return finishNode;
    }

    public Graph getNodes() {
        return nodes;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }
}

