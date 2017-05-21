/**
 * Created by Mati on 2017-05-09.
 */
package main.java.pl.edu.agh.bo.cvrp;

import main.java.pl.edu.agh.bo.ants.Ant;
import main.java.pl.edu.agh.bo.ants.AntColony;
import main.java.pl.edu.agh.bo.ants.AntGraph;

import java.util.Random;

public class AntColonyCVRP extends AntColony{

    protected static final double A = 0.1;

    public AntColonyCVRP(AntGraph graph, int nAnts, int nIterations) {
        super(graph, nAnts, nIterations);
    }

    @Override
    protected Ant[] createAnts(AntGraph graph, int ants){
    Random ran = new Random(System.currentTimeMillis());
    AntCVRP.reset();
    AntCVRP.setAntColony(this);
    AntCVRP ant[] = new AntCVRP[m_nAnts];
        for(int i = 0; i < m_nAnts; i++)
    {
        ant[i] = new AntCVRP(0, this, 10);
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
                    dEvaporation = ((double)1 - A) * m_graph.tau(r,s);
                    m_graph.updateTau(r, s, m_graph.tau(r,s)-dEvaporation);
                }
            }
        }

    }
}
