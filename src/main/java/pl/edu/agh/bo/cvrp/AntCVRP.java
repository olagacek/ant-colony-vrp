package main.java.pl.edu.agh.bo.cvrp;

import main.java.pl.edu.agh.bo.ants.Ant;

import java.util.Observer;

/**
 * Created by Mati on 2017-05-09.
 */
public class AntCVRP extends Ant {

    public AntCVRP(int nStartNode, Observer observer) {
        super(nStartNode, observer);
    }

    @Override
    protected boolean better(double dPathValue, double dBestPathValue) {
        return false;
    }

    @Override
    public int stateTransitionRule(int r) {
        return 0;
    }

    @Override
    public void localUpdatingRule(int r, int s) {

    }

    @Override
    public boolean end() {
        return false;
    }
}
