package decision.theory.lab2.interfaces;

import decision.theory.lab2.model.LvmLogicalNode;
import decision.theory.lab2.model.LvmTree;

public interface ILvmLogicFunctionBuilder {

    LvmLogicalNode buildLogicalFunction(LvmTree tree);

}
