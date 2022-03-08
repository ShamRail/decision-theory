package decision.theory.lab2.interfaces;

import decision.theory.lab2.model.LvmLogicalNode;
import decision.theory.lab2.model.LvmProbabilityFunction;

import java.util.Map;

public interface ILvmProbabilityFunctionSupplier {

    LvmProbabilityFunction buildFunction(LvmLogicalNode logicalNode);

    double calculate(LvmProbabilityFunction function, Map<String, Double> args);

}
