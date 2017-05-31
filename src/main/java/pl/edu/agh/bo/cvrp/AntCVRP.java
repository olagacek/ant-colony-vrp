package pl.edu.agh.bo.cvrp;

import pl.edu.agh.bo.ants.Ant;
import pl.edu.agh.bo.ants.AntGraph;

import java.util.*;

/**
 * Created by Mati on 2017-05-09.
 */
public class AntCVRP extends Ant {

    private static final double A    = 2;
    private static final double B    = 2;
    private static final double Q0   = 0.5;
    private static final double R    = 0.1;
    private AntGraph graph;

    private static final Random s_randGen = new Random(System.currentTimeMillis());

    protected Hashtable m_nodesToVisitTbl;

    public AntCVRP(int nStartNode, Observer observer, int cap) {
        super(nStartNode, observer, cap);
    }

    public void init(){
        super.init();
        graph = s_antColony.getGraph();
        m_nodesToVisitTbl = new Hashtable(graph.nodes());
        for(int i = 0; i < graph.nodes(); i++)
            m_nodesToVisitTbl.put(new Integer(i), new Integer(i));
        m_nodesToVisitTbl.remove(new Integer(m_nStartNode));
    }


    @Override
    public int stateTransitionRule(int r) {
        graph = s_antColony.getGraph();
        if(s_randGen.nextDouble() <= Q0){return exploitation();}
        return exploration();
    }

    private int exploration(){
        int nMaxNode = 0;
        double dSum = 0;
        int nNode = 0;

        Enumeration en = m_nodesToVisitTbl.elements();
        while(en.hasMoreElements())
        {
            nNode = ((Integer)en.nextElement()).intValue();
            if(graph.tau(m_nCurNode, nNode) == 0)
                continue;

            dSum += hValue(nNode);
        }

//        if(dSum == 0)
//            throw new RuntimeException("SUM = 0");

        double dAverage = dSum / (double)m_nodesToVisitTbl.size();

        en = m_nodesToVisitTbl.elements();
        while(en.hasMoreElements())
        {
            nNode = ((Integer)en.nextElement()).intValue();

            double p = hValue(nNode) / dSum;

            if(hValue(nNode) > dAverage && graph.demand(nNode) <= m_curCap)
            {
                nMaxNode = nNode;
                break;
            }
        }
        if(nMaxNode!=0){
            m_nodesToVisitTbl.remove(new Integer(nMaxNode));
            m_curCap -= graph.demand(nMaxNode);
        }
        else{
            m_curCap = m_maxCap;
        }
        System.out.println("Ant: "+m_nAntID+" makes move: "+m_nCurNode+" -> "+nMaxNode);
        return nMaxNode;
    }

    private int exploitation(){
        int nMaxNode = 0;
        double dMaxVal = -1;
        double dVal;
        int nNode;

        Enumeration en = m_nodesToVisitTbl.elements();
        while(en.hasMoreElements())
        {
            nNode = ((Integer)en.nextElement()).intValue();

            if(graph.tau(m_nCurNode, nNode) == 0)
                continue;

            dVal = hValue(nNode);

            if(dVal > dMaxVal && graph.demand(nNode) <= m_curCap)
            {
                dMaxVal  = dVal;
                nMaxNode = nNode;
            }
        }
        if(nMaxNode!=0){
            m_nodesToVisitTbl.remove(new Integer(nMaxNode));
            m_curCap -= graph.demand(nMaxNode);
        }
        else{
            m_curCap = m_maxCap;
        }
        System.out.println("Ant: "+m_nAntID+" makes move: "+m_nCurNode+" -> "+nMaxNode);
        return nMaxNode;
    }

    private double hValue(int nNode){
        return Math.pow(graph.tau(m_nCurNode, nNode),A) * Math.pow(graph.etha(m_nCurNode, nNode), B);
    }

    @Override
    public void localUpdatingRule(Vector path) {
        final AntGraph graph = s_antColony.getGraph();
        for(int i = 1; i< path.size(); i++){
            int currVertex = (int)path.get(i-1);
            int nextVertex = (int)path.get(i);
            double val = (R*graph.demand(nextVertex))/(m_dPathValue*graph.delta(currVertex, nextVertex));
            graph.updateTau(currVertex, nextVertex, val);
        }
    }

    @Override
    public boolean better(double dPathValue1, double dPathValue2)
    {
        return dPathValue1 < dPathValue2;
    }

    @Override
    public boolean end()
    {
        return m_nodesToVisitTbl.isEmpty() && m_nCurNode == m_nStartNode;
    }
}
