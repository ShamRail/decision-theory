package decision.theory.lab2.interfaces;

import decision.theory.lab2.model.LvmNodeResult;
import decision.theory.lab2.model.LvmTreeEdge;

import java.util.List;
import java.util.Map;

public interface ILvmService {
    List<LvmNodeResult> restoreFunctionsAndCalculate(List<LvmTreeEdge> treeEdges, Map<String, Double> probabilities);
}
