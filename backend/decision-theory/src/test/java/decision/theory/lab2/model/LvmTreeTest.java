package decision.theory.lab2.model;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LvmTreeTest {

    @Test
    public void whenIterateThrowSeven() {
        LvmTree node4 = new LvmTree("4", LvmNodeRelation.EMPTY);
        LvmTree node5 = new LvmTree("5", LvmNodeRelation.EMPTY);
        LvmTree node6 = new LvmTree("6", LvmNodeRelation.EMPTY);
        LvmTree node7 = new LvmTree("7", LvmNodeRelation.EMPTY);
        LvmTree node2 = new LvmTree("2", LvmNodeRelation.AND, List.of(node4, node5));
        LvmTree node3 = new LvmTree("3", LvmNodeRelation.AND, List.of(node6, node7));
        LvmTree root = new LvmTree(
                "1", LvmNodeRelation.OR, List.of(
                node2,
                node3
            )
        );
        Iterator<LvmTree> iterator = root.iterator();

        assertEquals(root, iterator.next());
        assertSame(node2, iterator.next());
        assertSame(node3, iterator.next());
        assertSame(node4, iterator.next());
        assertSame(node5, iterator.next());
        assertSame(node6, iterator.next());
        assertSame(node7, iterator.next());
        assertFalse(iterator.hasNext());

    }

}
