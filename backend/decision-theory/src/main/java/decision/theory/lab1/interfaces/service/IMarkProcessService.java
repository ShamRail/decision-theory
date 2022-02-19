package decision.theory.lab1.interfaces.service;

import decision.theory.lab1.interfaces.model.IMarkProcessResult;
import decision.theory.lab1.interfaces.model.IMarkProcessSourceData;

import java.util.List;

public interface IMarkProcessService {

    IMarkProcessResult calculate(List<double[][]> probabilityData, List<double[][]> valuesData, int stepAmount);

    IMarkProcessSourceData generateSourceData(int statesCount, int strategiesCount,
                                              double minV, double maxV);
}
