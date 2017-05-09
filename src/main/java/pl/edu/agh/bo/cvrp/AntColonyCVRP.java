/**
 * Created by Mati on 2017-05-09.
 */
package main.java.pl.edu.agh.bo.cvrp;
import main.java.pl.edu.agh.bo.ants.*;

public class AntColonyCVRP extends AntColony{

    public AntColonyCVRP(AntGraph graph, int nAnts, int nIterations) {
        super(graph, nAnts, nIterations);
    }

    @Override
    protected Ant[] createAnts(AntGraph graph, int ants) {
        return new Ant[0];
    }

    @Override
    protected void globalUpdatingRule() {

    }
}
