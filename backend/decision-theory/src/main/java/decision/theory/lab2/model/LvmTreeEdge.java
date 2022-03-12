package decision.theory.lab2.model;

import java.util.Objects;

public class LvmTreeEdge {

    private String from;

    private String to;

    private LvmNodeRelation type;

    private double probability;

    public LvmTreeEdge(String from, String to, LvmNodeRelation relation, double probability) {
        this.from = from;
        this.to = to;
        this.type = relation;
        this.probability = probability;
    }

    public LvmTreeEdge() {
        this("", "", LvmNodeRelation.AND, 0);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LvmNodeRelation getType() {
        return type;
    }

    public void setType(LvmNodeRelation type) {
        this.type = type;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LvmTreeEdge edge = (LvmTreeEdge) o;
        return Double.compare(edge.probability, probability) == 0
                && Objects.equals(from, edge.from)
                && Objects.equals(to, edge.to) && type == edge.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, type, probability);
    }

    @Override
    public String toString() {
        return "LvmTreeEdge{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", type=" + type +
                ", probability=" + probability +
                '}';
    }

}
