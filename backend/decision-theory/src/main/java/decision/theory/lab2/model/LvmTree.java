package decision.theory.lab2.model;

import java.util.*;

public class LvmTree implements Iterable<LvmTree> {

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

    @Override
    public String toString() {
        return "LvmTree{" +
                "name='" + name + '\'' +
                ", childRelation=" + childRelation +
                '}';
    }

    @Override
    public Iterator<LvmTree> iterator() {
        return new BFSIterator(name, childRelation, children);
    }

    private static final class BFSIterator implements Iterator<LvmTree> {

        private final Queue<LvmTree> queue = new LinkedList<>();

        private BFSIterator(String name, LvmNodeRelation relation, List<LvmTree> children) {
            this.queue.add(new LvmTree(name, relation, children));
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public LvmTree next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var currentNode = queue.poll();
            queue.addAll(currentNode.children);
            return currentNode;
        }
    }

}
