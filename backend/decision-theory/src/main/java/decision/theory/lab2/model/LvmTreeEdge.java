package decision.theory.lab2.model;

// https://stackoverflow.com/questions/61140857/unable-to-deserialize-when-using-new-record-classes

public record LvmTreeEdge(int from, int to, LvmNodeRelation relation, int probability) {
    public LvmTreeEdge() {
        this(0, 0, LvmNodeRelation.AND, 0);
    }
}
