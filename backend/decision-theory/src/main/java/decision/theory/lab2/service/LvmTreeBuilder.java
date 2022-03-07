package decision.theory.lab2.service;

import decision.theory.lab2.interfaces.ILvmTreeBuilder;
import decision.theory.lab2.model.LvmNodeRelation;
import decision.theory.lab2.model.LvmTree;
import decision.theory.lab2.model.LvmTreeEdge;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LvmTreeBuilder implements ILvmTreeBuilder {

    @Override
    public LvmTree buildFromEdgesList(List<LvmTreeEdge> treeEdges, Map<Integer, String> nodesDescriptor) {
        LvmTree root = null;
        for (var edgeI : treeEdges) {
            var fromParentFound = false;
            var toParentFound = false;
            for (var edgeJ : treeEdges) {
                if (edgeJ.to() == edgeI.from()) {
                    fromParentFound = true;
                }
                if (edgeJ.to() == edgeI.to()) {
                    toParentFound = true;
                }
                if (fromParentFound && toParentFound) {
                    break;
                }
            }
            if (!fromParentFound || !toParentFound) {
                var rootNumber = !fromParentFound ? edgeI.from() : edgeI.to();
                var name = nodesDescriptor.get(rootNumber);
                if (name == null) {
                    throw new IllegalArgumentException("Unknown node with number " + rootNumber);
                }
                root = new LvmTree(rootNumber, name, LvmNodeRelation.EMPTY);
                break;
            }
        }
        if (root == null) {
            throw new IllegalArgumentException("Tree can't form from this list");
        }
        return restoreTree(root, treeEdges, nodesDescriptor);
    }

    private LvmTree restoreTree(LvmTree subTree, List<LvmTreeEdge> treeEdges, Map<Integer, String> nodesDescriptor) {
        var number = subTree.getNumber();
        for (var edge : treeEdges) {
            if (edge.from() == number) {
                if (subTree.getChildRelation() == LvmNodeRelation.EMPTY) {
                    subTree.setChildRelation(edge.relation());
                }
                var nodeNumber = edge.to();
                var name = nodesDescriptor.get(nodeNumber);
                if (name == null) {
                    throw new IllegalArgumentException("Unknown node with number " + nodeNumber);
                }
                subTree.getChildren().add(new LvmTree(nodeNumber, name, LvmNodeRelation.EMPTY));
            }
        }
        for (var child : subTree.getChildren()) {
            restoreTree(child, treeEdges, nodesDescriptor);
        }
        return subTree;
    }

}
