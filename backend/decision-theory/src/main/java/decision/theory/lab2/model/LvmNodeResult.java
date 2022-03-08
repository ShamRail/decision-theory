package decision.theory.lab2.model;

public record LvmNodeResult(
        String nodeName,
        String logicalFunction, String notAndLogicalFunction,
        String probabilityFunction, double resultProbability) {
}
