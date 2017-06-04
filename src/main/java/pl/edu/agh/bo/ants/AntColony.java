package pl.edu.agh.bo.ants;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;


import static pl.edu.agh.bo.ants.Ant.s_nLastBestPathIteration;
import static pl.edu.agh.bo.ants.Ant.s_bestPathVect;

/**
 * Created by Mati on 2017-05-09.
 */



public abstract class AntColony implements Observer
{
    protected PrintStream m_outs;

    protected AntGraph m_graph;
    protected Ant[]    m_ants;
    protected int      m_nAnts;
    protected int      m_nAntCounter;
    protected int      m_nIterCounter;
    protected int      m_nIterations;
    protected int      m_capacity;

    private int      m_nID;

    private static int s_nIDCounter = 0;

    public AntColony(AntGraph graph, int nAnts, int nIterations, int capacity)
    {
        m_graph = graph;
        m_nAnts = nAnts;
        m_nIterations = nIterations;
        s_nIDCounter++;
        m_nID = s_nIDCounter;
        m_capacity = capacity;
        m_nIterCounter = 0;
    }

    public synchronized void start()
    {
        // creates all ants
        m_ants  = createAnts(m_graph, m_nAnts);

        try
        {
            m_outs = new PrintStream(new FileOutputStream("" + m_nID + "_" + m_graph.nodes() + "x" + m_ants.length + "x" + m_nIterations + "_colony.txt"));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        // loop for all iterations
        while(m_nIterCounter < m_nIterations)
        {
            // run an iteration
            iteration();
            try
            {
                wait();
            }
            catch(InterruptedException ex)
            {
                ex.printStackTrace();
            }

            // synchronize the access to the graph
            synchronized(m_graph)
            {
                // apply global updating rule
                globalUpdatingRule();
            }
        }

        if(m_nIterCounter == m_nIterations)
        {
            m_outs.close();
        }
    }

    private void iteration()
    {
        m_nAntCounter = 0;
        m_nIterCounter++;
        m_outs.print(""+m_nIterCounter);
        for(int i = 0; i < m_ants.length; i++)
        {
            m_ants[i].start();
        }
    }

    public AntGraph getGraph()
    {
        return m_graph;
    }

    public int getAnts()
    {
        return m_ants.length;
    }

    public int getIterations()
    {
        return m_nIterations;
    }

    public int getIterationCounter()
    {
        return m_nIterCounter;
    }

    public int getID()
    {
        return m_nID;
    }

    public synchronized void update(Observable ant, Object obj)
    {
        m_outs.print(";" + ((Ant)ant).m_dPathValue);
        m_nAntCounter++;

        if(m_nAntCounter == m_ants.length)
        {
            m_outs.println("    iteration: "+Ant.s_nLastBestPathIteration+"     result: " + Ant.s_dBestPathValue);

                        System.out.println("---------------------------");
                        System.out.println(m_nIterCounter + " - Best Path: " + Ant.s_dBestPathValue);
                        System.out.println("---------------------------");
                        System.out.println("Path seq: ");
                        for(int i = 0; i<Ant.s_bestPathVect.size(); i++){
                            System.out.print(s_bestPathVect.get(i));
                        }



            notify();

        }
    }

    public double getBestPathValue()
    {
        return Ant.s_dBestPathValue;
    }

    public int[] getBestPath()
    {
        return Ant.getBestPath();
    }

    public Vector getBestPathVector()
    {
        return s_bestPathVect;
    }

    public int getLastBestPathIteration()
    {
        return s_nLastBestPathIteration;
    }

    public boolean done()
    {
        return m_nIterCounter == m_nIterations;
    }

    protected abstract Ant[] createAnts(AntGraph graph, int ants);

    protected abstract void globalUpdatingRule();
}
