package decision.theory.lab2.service;

import decision.theory.lab2.interfaces.ILvmTreeBuilder;
import decision.theory.lab2.model.LvmNodeRelation;
import decision.theory.lab2.model.LvmTree;
import decision.theory.lab2.model.LvmTreeEdge;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LvmTreeBuilder implements ILvmTreeBuilder {

    @Override
    public LvmTree buildFromEdgesList(List<LvmTreeEdge> treeEdges) {
        LvmTree root = null;
        for (var edgeI : treeEdges) {
            var fromParentFound = false;
            var toParentFound = false;
            for (var edgeJ : treeEdges) {
                if (edgeJ.to().equals(edgeI.from())) {
                    fromParentFound = true;
                }
                if (edgeJ.to().equals(edgeI.to())) {
                    toParentFound = true;
                }
                if (fromParentFound && toParentFound) {
                    break;
                }
            }
            if (!fromParentFound || !toParentFound) {
                var name = !fromParentFound ? edgeI.from() : edgeI.to();
                root = new LvmTree(name, LvmNodeRelation.EMPTY);
                break;
            }
        }
        if (root == null) {
            throw new IllegalArgumentException("Tree can't form from this list");
        }
        return restoreTree(root, treeEdges);
    }

    private LvmTree restoreTree(LvmTree subTree, List<LvmTreeEdge> treeEdges) {
        var number = subTree.getName();
        for (var edge : treeEdges) {
            if (edge.from().equals(number)) {
                if (subTree.getChildRelation() == LvmNodeRelation.EMPTY) {
                    subTree.setChildRelation(edge.relation());
                }
                var name = edge.to();
                subTree.getChildren().add(new LvmTree(name, LvmNodeRelation.EMPTY));
            }
        }
        for (var child : subTree.getChildren()) {
            restoreTree(child, treeEdges);
        }
        return subTree;
    }

}
