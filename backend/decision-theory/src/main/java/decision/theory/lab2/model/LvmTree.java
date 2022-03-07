package decision.theory.lab2.model;

import java.util.ArrayList;
import java.util.List;

public record LvmTree(int number, String name, List<LvmTree> children) {
    public LvmTree(int number, String name) {
        this(number, name, new ArrayList<>());
    }
}
