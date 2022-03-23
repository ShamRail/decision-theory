package decision.theory.lab3.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings("checkstyle:MagicNumber")
public class MaxMinInitStrategyTests {

    @Test
    public void whenMinMaxAndMaxMinEqual() {
        var matrix = new double[][]{
            {10, 12, 2, 23},
            {9, 56, 65, 29},
            {14, 3, 37, 43},
            {78, 4, 17, 28}
        };
        var result = new MaxMinInitStrategy().choose(matrix);
        assertEquals(1, result.getRowIndex());
        assertEquals(3, result.getColIndex());
        assertEquals(9, result.getMaxMin());
        assertEquals(43, result.getMinMax());
    }
    
    @Test
    public void whenMinMaxAndMaxMinNotEqual() {
        var matrix = new double[][]{
            {5, 5},
            {1, 3},
            {4, 2}
        };
        var result = new MaxMinInitStrategy().choose(matrix);
        assertEquals(0, result.getRowIndex());
        assertEquals(0, result.getColIndex());
        assertEquals(5, result.getMaxMin());
        assertEquals(5, result.getMinMax());
    }
    
}
