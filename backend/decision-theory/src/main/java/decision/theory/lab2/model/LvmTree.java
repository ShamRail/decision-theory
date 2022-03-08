package decision.theory.lab2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LvmTree {

    private final String name;

    private LvmNodeRelation childRelation;

    private final List<LvmTree> children;

    public LvmTree(String name, LvmNodeRelation childRelation, List<LvmTree> children) {
        this.name = name;
        this.childRelation = childRelation;
        this.children = children;
    }

    public LvmTree(String name, LvmNodeRelation childRelation) {
        this(name, childRelation, new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public LvmNodeRelation getChildRelation() {
        return childRelation;
    }

    public List<LvmTree> getChildren() {
        return children;
    }

    public void setChildRelation(LvmNodeRelation childRelation) {
        this.childRelation = childRelation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LvmTree lvmTree = (LvmTree) o;
        return Objects.equals(name, lvmTree.name)
                && childRelation == lvmTree.childRelation
                && Objects.equals(children, lvmTree.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, childRelation, children);
    }
}
