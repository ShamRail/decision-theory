package decision.theory.lab1.model;

import decision.theory.lab1.interfaces.model.IMarkProcessResult;

import java.util.List;
import java.util.Map;

public class MarkProcessResult implements IMarkProcessResult {

    private Map<Integer, Map<Integer, Double>> valueResult;

    private Map<Integer, Map<Integer, Integer>> strategyResult;

    private List<double[][]> probabilities;

    public MarkProcessResult(List<double[][]> probabilities,
                             Map<Integer, Map<Integer, Double>> valueResult,
                             Map<Integer, Map<Integer, Integer>> strategyResult) {
        this.valueResult = valueResult;
        this.strategyResult = strategyResult;
        this.probabilities = probabilities;
    }

    @Override
    public List<double[][]> getStateAdjacencyMatrix() {
        return probabilities;
    }

    @Override
    public Map<Integer, Map<Integer, Double>> getValueResultPerStep() {
        return valueResult;
    }

    @Override
    public Map<Integer, Map<Integer, Integer>> getStrategyResultStep() {
        return strategyResult;
    }
}
