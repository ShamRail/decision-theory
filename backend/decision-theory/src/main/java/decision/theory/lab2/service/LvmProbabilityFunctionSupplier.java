package decision.theory.lab2.service;

import com.bpodgursky.jbool_expressions.Expression;
import decision.theory.lab2.interfaces.IExpressionCalculator;
import decision.theory.lab2.interfaces.ILvmProbabilityFunctionSupplier;
import decision.theory.lab2.model.LvmLogicalNode;
import decision.theory.lab2.model.LvmProbabilityFunction;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LvmProbabilityFunctionSupplier implements ILvmProbabilityFunctionSupplier {

    private static final String NOT = "not";

    private static final String NOT_CONVERT_FORMAT = "(1 - %s)";

    private static final String NOT_DIVIDER = " * ";

    private final IExpressionCalculator<Double> expressionCalculator;

    public LvmProbabilityFunctionSupplier(IExpressionCalculator<Double> expressionCalculator) {
        this.expressionCalculator = expressionCalculator;
    }

    @Override
    public LvmProbabilityFunction buildFunction(LvmLogicalNode logicalNode) {
        return new LvmProbabilityFunction(truncateFirstLastBracket(
                buildFunctionPart(logicalNode.expression())
        ));
    }

    private String buildFunctionPart(Expression<String> expression) {
        if (expression.getChildren().isEmpty()) {
            return expression.toString();
        }
        var expressionOperator = expression.getExprType();
        var production = expression.getChildren().stream()
                .map(this::buildFunctionPart)
                .collect(Collectors.joining(NOT_DIVIDER));
        if (NOT.equals(expressionOperator)) {
            return String.format(NOT_CONVERT_FORMAT, production);
        }
        return production;
    }

    private String truncateFirstLastBracket(String input) {
        return input.substring(1, input.length() - 1);
    }

    @Override
    public double calculate(LvmProbabilityFunction function, Map<String, Double> args) {
        return expressionCalculator.eval(function.function(), args);
    }


}

