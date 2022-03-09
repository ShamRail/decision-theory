package decision.theory.lab2.service;

import com.bpodgursky.jbool_expressions.*;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import decision.theory.lab2.interfaces.ILvmLogicFunctionBuilder;
import decision.theory.lab2.model.LvmLogicalNode;
import decision.theory.lab2.model.LvmNodeRelation;
import decision.theory.lab2.model.LvmTree;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class LvmLogicFunctionBuilder implements ILvmLogicFunctionBuilder {

    private static final String AND = "and";

    private static final String OR = "or";

    private static final String NOT = "not";

    @Override
    public LvmLogicalNode buildLogicalFunction(LvmTree tree) {
        if (tree.getChildren().isEmpty()) {
            return new LvmLogicalNode(Variable.of(tree.getName()));
        }
        var relation = tree.getChildRelation();
        var expressions = tree.getChildren().stream()
                .map(this::buildLogicalFunction)
                .map(LvmLogicalNode::expression)
                .collect(Collectors.toList());
        var expression = relation == LvmNodeRelation.AND ? And.of(expressions) : Or.of(expressions);
        return new LvmLogicalNode(RuleSet.toDNF(expression));
    }

    @Override
    public LvmLogicalNode convertToNotAndBasis(LvmLogicalNode node) {
        return convertToNotAndBasis(node.expression());
    }

    private LvmLogicalNode convertToNotAndBasis(Expression<String> expression) {
        if (expression.getChildren().isEmpty()) {
            return new LvmLogicalNode(expression);
        }
        var subExpressions = expression.getChildren().stream()
                .map(this::convertToNotAndBasis)
                .map(LvmLogicalNode::expression)
                .collect(Collectors.toList());
        Expression<String> convertedExpression = null;
        var expressionOperator = expression.getExprType();
        if (OR.equals(expressionOperator)) {
            subExpressions = subExpressions.stream()
                    .map(Not::of)
                    .collect(Collectors.toList());
            convertedExpression = Not.of(And.of(subExpressions));
        } else if (AND.equals(expressionOperator)) {
            convertedExpression = And.of(subExpressions);
        }
        return new LvmLogicalNode(convertedExpression);
    }

}
