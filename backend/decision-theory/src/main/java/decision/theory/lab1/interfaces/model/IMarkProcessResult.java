package decision.theory.lab1.interfaces.model;

import java.util.Map;

public interface IMarkProcessResult {

    double[][] getStateAdjacencyMatrix();

    Map<Integer, Map<Integer, Double>> getValueResultPerStep();

    Map<Integer, Map<Integer, Integer>> getStrategyResultStep();

}
