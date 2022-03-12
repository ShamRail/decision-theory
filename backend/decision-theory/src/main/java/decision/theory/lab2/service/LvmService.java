package decision.theory.lab2.service;

import decision.theory.lab2.interfaces.ILvmLogicFunctionBuilder;
import decision.theory.lab2.interfaces.ILvmProbabilityFunctionSupplier;
import decision.theory.lab2.interfaces.ILvmService;
import decision.theory.lab2.interfaces.ILvmTreeBuilder;
import decision.theory.lab2.model.LvmNodeResult;
import decision.theory.lab2.model.LvmTree;
import decision.theory.lab2.model.LvmTreeEdge;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LvmService implements ILvmService {

    private final ILvmTreeBuilder lvmTreeBuilder;

    private final ILvmLogicFunctionBuilder lvmLogicFunctionBuilder;

    private final ILvmProbabilityFunctionSupplier lvmProbabilityFunctionSupplier;

    public LvmService(ILvmTreeBuilder lvmTreeBuilder,
                      ILvmLogicFunctionBuilder lvmLogicFunctionBuilder,
                      ILvmProbabilityFunctionSupplier lvmProbabilityFunctionSupplier) {
        this.lvmTreeBuilder = lvmTreeBuilder;
        this.lvmLogicFunctionBuilder = lvmLogicFunctionBuilder;
        this.lvmProbabilityFunctionSupplier = lvmProbabilityFunctionSupplier;
    }

    @Override
    public List<LvmNodeResult> restoreFunctionsAndCalculate(List<LvmTreeEdge> treeEdges, Map<String, Double> probabilities) {
        var tree = lvmTreeBuilder.buildFromEdgesList(treeEdges);
        return StreamSupport.stream(tree.spliterator(), false)
                .filter(subTree -> subTree.getChildren().size() > 0)
                .map(subTree -> handleSubtree(subTree, probabilities))
                .collect(Collectors.toList());
    }

    private LvmNodeResult handleSubtree(LvmTree subTree, Map<String, Double> probabilities) {
        var logicalFunction = lvmLogicFunctionBuilder.buildLogicalFunction(subTree);
        var notAndLogicalFunction = lvmLogicFunctionBuilder.convertToNotAndBasis(logicalFunction);
        var probabilityFunction = lvmProbabilityFunctionSupplier.buildFunction(notAndLogicalFunction);
        var probability = lvmProbabilityFunctionSupplier.calculate(probabilityFunction, probabilities);
        return new LvmNodeResult(
                subTree.getName(),
                logicalFunction.getExpressionAsString(),
                notAndLogicalFunction.getExpressionAsString(),
                probabilityFunction.function(),
                probability
        );
    }

}
