package decision.theory.lab2.service;

import decision.theory.lab2.interfaces.IExpressionCalculator;
import decision.theory.lab2.interfaces.ILvmLogicFunctionBuilder;
import decision.theory.lab2.interfaces.ILvmProbabilityFunctionSupplier;
import decision.theory.lab2.model.LvmLogicalNode;
import decision.theory.lab2.model.LvmNodeRelation;
import decision.theory.lab2.model.LvmProbabilityFunction;
import decision.theory.lab2.model.LvmTree;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("checkstyle:MagicNumber")
class LvmProbabilityFunctionSupplierTest {

    private final IExpressionCalculator<Double> expressionCalculator = new FatherDoubleExpressionCalculator();

    private final ILvmLogicFunctionBuilder lvmLogicFunctionBuilder = new LvmLogicFunctionBuilder();

    private final ILvmProbabilityFunctionSupplier probabilityFunctionSupplier = new LvmProbabilityFunctionSupplier(expressionCalculator);

    @Test
    public void whenBuildProbabilityFunction() {

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
        String expectedExpression = "1 - (1 - x1 * x3) * (1 - x1 * x4) * (1 - x2 * x3) * (1 - x2 * x4)";

        LvmLogicalNode rootNode = lvmLogicFunctionBuilder.convertToNotAndBasis(lvmLogicFunctionBuilder.buildLogicalFunction(tree));
        assertEquals(expectedExpression, probabilityFunctionSupplier.buildFunction(rootNode).function());

    }

    @Test
    public void whenCalculateProbabilityFunction() {

        LvmProbabilityFunction function = new LvmProbabilityFunction("1 - (1 - x1 * x3) * (1 - x1 * x4) * (1 - x2 * x3) * (1 - x2 * x4)");
        Map<String, Double> values = Map.of(
                "x1", 0.1,
                "x2", 0.2,
                "x3", 0.3,
                "x4", 0.4
        );
        double expectedResult = 0.195;

        assertEquals(expectedResult, probabilityFunctionSupplier.calculate(function, values), 0.001);

    }

}
