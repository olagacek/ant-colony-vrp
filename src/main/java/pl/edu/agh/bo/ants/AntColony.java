package pl.edu.agh.bo.ants;

import pl.edu.agh.bo.cvrp.CapacitatedVehicleRoutingProblem;
import pl.edu.agh.bo.cvrp.Vehicle;

import java.util.Set;

public class AntColony {
    private int numberOfAnts;
    private int numberOfSteps;
    private double initialPheromoneAmount;
    private double pheromoneEvaporation;
    private double explorationProbability;

    public AntColony(int numberOfAnts, int numberOfSteps, double initialPheromoneAmount, double pheromoneEvaporation, double explorationProbability) {
        this.numberOfAnts = numberOfAnts;
        this.numberOfSteps = numberOfSteps;
        this.initialPheromoneAmount = initialPheromoneAmount;
        this.pheromoneEvaporation = pheromoneEvaporation;
        this.explorationProbability = explorationProbability;
    }

    public Set<Vehicle> solveCVRPProblem(CapacitatedVehicleRoutingProblem cvrp){
        return null;
    }
}
