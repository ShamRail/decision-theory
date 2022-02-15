package decision.theory.lab1.interfaces.model;

import java.util.Map;

public interface IMarkProcess {

    Map<Integer, IMarkProcessState> getStateDescriptor();

    Map<Integer, IMarkProcessStrategy> getStrategyDescriptor();

    IMarkProcessResult calculate();

}
