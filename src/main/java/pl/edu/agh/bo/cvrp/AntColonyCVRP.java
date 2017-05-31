/**
 * Created by Mati on 2017-05-09.
 */
package pl.edu.agh.bo.cvrp;

import pl.edu.agh.bo.ants.Ant;
import pl.edu.agh.bo.ants.AntColony;
import pl.edu.agh.bo.ants.AntGraph;

import java.util.Random;

public class AntColonyCVRP extends AntColony{

    protected static final double A = 0.01;

    public AntColonyCVRP(AntGraph graph, int nAnts, int nIterations, int capacity) {
        super(graph, nAnts, nIterations, capacity);
    }

    @Override
    protected Ant[] createAnts(AntGraph graph, int ants){
    Random ran = new Random(System.currentTimeMillis());
    AntCVRP.reset();
    AntCVRP.setAntColony(this);
    AntCVRP ant[] = new AntCVRP[m_nAnts];
        for(int i = 0; i < m_nAnts; i++)
    {
        ant[i] = new AntCVRP(0, this, m_capacity);
    }

        return ant;
    }

    @Override
    protected void globalUpdatingRule() {
        double dEvaporation = 0;

        for(int r = 0; r < m_graph.nodes(); r++)
        {
            for(int s = 0; s < m_graph.nodes(); s++)
            {
                if(r != s)
                {
                    dEvaporation = A * m_graph.tau(r,s);
                    m_graph.updateTau(r, s, m_graph.tau(r,s)-dEvaporation);
                }
            }
        }

    }
}
