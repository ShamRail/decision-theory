package decision.theory.lab2.service;

import decision.theory.lab2.interfaces.ILvmTreeBuilder;
import decision.theory.lab2.model.LvmNodeRelation;
import decision.theory.lab2.model.LvmTree;
import decision.theory.lab2.model.LvmTreeEdge;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("checkstyle:MagicNumber")
class LvmTreeBuilderTest {

    private final ILvmTreeBuilder treeBuilder = new LvmTreeBuilder();

    @Test
    public void whenRestoreTreeFrom2Edges() {

        LvmTreeEdge edge1 = new LvmTreeEdge("1", "2", LvmNodeRelation.OR, -1);
        LvmTreeEdge edge2 = new LvmTreeEdge("1", "3", LvmNodeRelation.OR, -1);

        LvmTree expected = new LvmTree(
                "1", LvmNodeRelation.OR, List.of(
                new LvmTree("2", LvmNodeRelation.NONE),
                new LvmTree("3", LvmNodeRelation.NONE)
            )
        );

        assertEquals(expected, treeBuilder.buildFromEdgesList(List.of(edge1, edge2)));

    }

    @Test
    public void whenRestoreTreeFrom6Edges() {

        LvmTreeEdge edge1 = new LvmTreeEdge("1", "2", LvmNodeRelation.OR, -1);
        LvmTreeEdge edge2 = new LvmTreeEdge("1", "3", LvmNodeRelation.OR, -1);
        LvmTreeEdge edge3 = new LvmTreeEdge("2", "4", LvmNodeRelation.AND, -1);
        LvmTreeEdge edge4 = new LvmTreeEdge("2", "5", LvmNodeRelation.AND, -1);
        LvmTreeEdge edge5 = new LvmTreeEdge("3", "6", LvmNodeRelation.AND, -1);
        LvmTreeEdge edge6 = new LvmTreeEdge("3", "7", LvmNodeRelation.AND, -1);

        LvmTree expected = new LvmTree(
                "1", LvmNodeRelation.OR, List.of(
                new LvmTree(
                        "2", LvmNodeRelation.AND, List.of(
                        new LvmTree("4", LvmNodeRelation.NONE),
                        new LvmTree("5", LvmNodeRelation.NONE)
                    )
                ),
                new LvmTree(
                        "3", LvmNodeRelation.AND, List.of(
                        new LvmTree("6", LvmNodeRelation.NONE),
                        new LvmTree("7", LvmNodeRelation.NONE)
                    )
                )
            )
        );

        assertEquals(
                expected, treeBuilder.buildFromEdgesList(List.of(
                            edge1, edge2, edge3, edge4, edge5, edge6
                        )
                )
        );

    }

}
