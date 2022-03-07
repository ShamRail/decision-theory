package decision.theory.lab2.model;

import com.bpodgursky.jbool_expressions.Expression;

public record LvmLogicalNode(Expression<String> expression) {

    public String getExpressionAsString() {
        return expression.toString();
    }

}
