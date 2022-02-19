package decision.theory.lab1.model;

import decision.theory.lab1.interfaces.model.IMarkProcessSourceData;

import java.util.List;

public record MarkProcessSourceData(
        List<double[][]> probabilities,
        List<double[][]> values) implements IMarkProcessSourceData {

    @Override
    public List<double[][]> getProbabilities() {
        return probabilities;
    }

    @Override
    public List<double[][]> getValues() {
        return values;
    }

}
