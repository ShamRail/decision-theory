package decision.theory.lab2.service;

import decision.theory.lab2.interfaces.ILvmTreeBuilder;
import decision.theory.lab2.model.LvmNodeRelation;
import decision.theory.lab2.model.LvmTree;
import decision.theory.lab2.model.LvmTreeEdge;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("checkstyle:MagicNumber")
class LvmTreeBuilderTest {

    private final ILvmTreeBuilder treeBuilder = new LvmTreeBuilder();

    @Test
    public void whenRestoreTreeFrom2Edges() {

        LvmTreeEdge edge1 = new LvmTreeEdge(1, 2, LvmNodeRelation.OR, -1);
        LvmTreeEdge edge2 = new LvmTreeEdge(1, 3, LvmNodeRelation.OR, -1);

        Map<Integer, String> nodeDescriptor = Map.of(
                1, "First",
                2, "Second",
                3, "Third"
        );

        LvmTree expected = new LvmTree(
                1, "First", LvmNodeRelation.OR, List.of(
                new LvmTree(2, "Second", LvmNodeRelation.EMPTY),
                new LvmTree(3, "Third", LvmNodeRelation.EMPTY)
            )
        );

        assertEquals(expected, treeBuilder.buildFromEdgesList(List.of(edge1, edge2), nodeDescriptor));

    }

    @Test
    public void whenRestoreTreeFrom6Edges() {

        LvmTreeEdge edge1 = new LvmTreeEdge(1, 2, LvmNodeRelation.OR, -1);
        LvmTreeEdge edge2 = new LvmTreeEdge(1, 3, LvmNodeRelation.OR, -1);
        LvmTreeEdge edge3 = new LvmTreeEdge(2, 4, LvmNodeRelation.AND, -1);
        LvmTreeEdge edge4 = new LvmTreeEdge(2, 5, LvmNodeRelation.AND, -1);
        LvmTreeEdge edge5 = new LvmTreeEdge(3, 6, LvmNodeRelation.AND, -1);
        LvmTreeEdge edge6 = new LvmTreeEdge(3, 7, LvmNodeRelation.AND, -1);

        Map<Integer, String> nodeDescriptor = Map.of(
                1, "First",
                2, "Second",
                3, "Third",
                4, "Fourth",
                5, "Fifth",
                6, "Sixth",
                7, "Seventh"
        );

        LvmTree expected = new LvmTree(
                1, "First", LvmNodeRelation.OR, List.of(
                new LvmTree(
                        2, "Second", LvmNodeRelation.AND, List.of(
                        new LvmTree(4, "Fourth", LvmNodeRelation.EMPTY),
                        new LvmTree(5, "Fifth", LvmNodeRelation.EMPTY)
                    )
                ),
                new LvmTree(
                        3, "Third", LvmNodeRelation.AND, List.of(
                        new LvmTree(6, "Sixth", LvmNodeRelation.EMPTY),
                        new LvmTree(7, "Seventh", LvmNodeRelation.EMPTY)
                    )
                )
            )
        );

        assertEquals(
                expected, treeBuilder.buildFromEdgesList(List.of(
                            edge1, edge2, edge3, edge4, edge5, edge6
                        ),
                        nodeDescriptor
                )
        );

    }

}
