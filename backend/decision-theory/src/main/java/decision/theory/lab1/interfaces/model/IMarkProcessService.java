package decision.theory.lab1.interfaces.model;

import java.util.List;

public interface IMarkProcessService {

    IMarkProcessResult calculate(List<double[][]> probabilityData, List<double[][]> valuesData, int stepAmount);

}
