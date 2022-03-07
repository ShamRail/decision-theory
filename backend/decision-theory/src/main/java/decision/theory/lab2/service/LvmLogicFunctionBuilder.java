package decision.theory.lab2.service;

import com.bpodgursky.jbool_expressions.And;
import com.bpodgursky.jbool_expressions.Or;
import com.bpodgursky.jbool_expressions.Variable;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import decision.theory.lab2.interfaces.ILvmLogicFunctionBuilder;
import decision.theory.lab2.model.LvmLogicalNode;
import decision.theory.lab2.model.LvmNodeRelation;
import decision.theory.lab2.model.LvmTree;

import java.util.stream.Collectors;

public class LvmLogicFunctionBuilder implements ILvmLogicFunctionBuilder {

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

}
