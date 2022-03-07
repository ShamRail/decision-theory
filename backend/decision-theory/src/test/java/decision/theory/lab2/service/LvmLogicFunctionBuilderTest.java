package decision.theory.lab2.service;

import com.bpodgursky.jbool_expressions.Not;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import decision.theory.lab2.interfaces.ILvmLogicFunctionBuilder;
import decision.theory.lab2.model.LvmLogicalNode;
import decision.theory.lab2.model.LvmNodeRelation;
import decision.theory.lab2.model.LvmTree;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LvmLogicFunctionBuilderTest {

    private final ILvmLogicFunctionBuilder lvmLogicFunctionBuilder = new LvmLogicFunctionBuilder();

    @Test
    public void whenTreeNodes() {
        LvmTree tree = new LvmTree(
                1, "Compound", LvmNodeRelation.OR, List.of(
                new LvmTree(2, "x1", LvmNodeRelation.EMPTY),
                new LvmTree(3, "x2", LvmNodeRelation.EMPTY)
            )
        );
        String expectedExpression = "(x1 | x2)";

        LvmLogicalNode rootNode = lvmLogicFunctionBuilder.buildLogicalFunction(tree);
        assertEquals(expectedExpression, rootNode.getExpressionAsString());
    }

    @Test
    public void whenSevenNodes() {

        LvmTree tree = new LvmTree(
                1, "CompoundFirst", LvmNodeRelation.AND, List.of(
                new LvmTree(
                        2, "CompoundSecond", LvmNodeRelation.OR, List.of(
                        new LvmTree(4, "x1", LvmNodeRelation.EMPTY),
                        new LvmTree(5, "x2", LvmNodeRelation.EMPTY)
                )
                ),
                new LvmTree(
                        3, "CompoundThird", LvmNodeRelation.OR, List.of(
                        new LvmTree(6, "x3", LvmNodeRelation.EMPTY),
                        new LvmTree(7, "x4", LvmNodeRelation.EMPTY)
                )
                )
            )
        );
        String expectedExpression = "((x1 & x3) | (x1 & x4) | (x2 & x3) | (x2 & x4))";

        LvmLogicalNode rootNode = lvmLogicFunctionBuilder.buildLogicalFunction(tree);
        assertEquals(expectedExpression, rootNode.getExpressionAsString());
    }

}
