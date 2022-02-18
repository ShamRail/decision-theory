package decision.theory.lab1.interfaces.model;

import java.util.List;
import java.util.Map;

public interface IMarkProcessResult {

    List<double[][]> getStateAdjacencyMatrix();

    Map<Integer, Map<Integer, Double>> getValueResultPerStep();

    Map<Integer, Map<Integer, Integer>> getStrategyResultStep();

}
