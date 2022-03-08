package decision.theory.lab2.service;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;
import decision.theory.lab2.interfaces.IExpressionCalculator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FatherDoubleExpressionCalculator implements IExpressionCalculator<Double> {

    private static final DoubleEvaluator EVALUATOR = new DoubleEvaluator();

    @Override
    public Double eval(String expression, Map<String, Double> args) {
        var variables = new StaticVariableSet<Double>();
        for (var varValue : args.entrySet()) {
            variables.set(varValue.getKey(), varValue.getValue());
        }
        return EVALUATOR.evaluate(expression, variables);
    }

}
