package decision.theory.lab2.model;

// https://stackoverflow.com/questions/61140857/unable-to-deserialize-when-using-new-record-classes

public record LvmTreeEdge(String from, String to, LvmNodeRelation relation, int probability) {
    public LvmTreeEdge() {
        this("", "", LvmNodeRelation.AND, 0);
    }
}
