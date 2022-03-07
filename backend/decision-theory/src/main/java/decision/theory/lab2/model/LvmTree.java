package decision.theory.lab2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LvmTree {

    private final int number;

    private final String name;

    private LvmNodeRelation childRelation;

    private final List<LvmTree> children;

    public LvmTree(int number, String name, LvmNodeRelation childRelation, List<LvmTree> children) {
        this.number = number;
        this.name = name;
        this.childRelation = childRelation;
        this.children = children;
    }

    public LvmTree(int number, String name, LvmNodeRelation childRelation) {
        this(number, name, childRelation, new ArrayList<>());
    }

    public int getNumber() {
        return number;
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
        return number == lvmTree.number
                && Objects.equals(name, lvmTree.name)
                && childRelation == lvmTree.childRelation
                && Objects.equals(children, lvmTree.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name, childRelation, children);
    }
}
