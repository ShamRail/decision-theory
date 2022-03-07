package decision.theory.lab2.interfaces;

import decision.theory.lab2.model.LvmTree;
import decision.theory.lab2.model.LvmTreeEdge;

import java.util.List;
import java.util.Map;

public interface ILvmTreeBuilder {

    LvmTree buildFromEdgesList(List<LvmTreeEdge> treeEdges, Map<Integer, String> nodesDescriptor);

}
