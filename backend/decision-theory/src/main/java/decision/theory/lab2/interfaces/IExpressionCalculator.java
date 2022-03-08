package decision.theory.lab2.interfaces;

import java.util.Map;

public interface IExpressionCalculator<T> {

    T eval(String expression, Map<String, T> args);

}
