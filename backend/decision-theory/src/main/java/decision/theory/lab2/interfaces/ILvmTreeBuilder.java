package decision.theory.lab2.interfaces;

import decision.theory.lab2.model.LvmTree;
import decision.theory.lab2.model.LvmTreeEdge;

import java.util.List;

public interface ILvmTreeBuilder {

    LvmTree buildFromEdgesList(List<LvmTreeEdge> treeEdges);

}
