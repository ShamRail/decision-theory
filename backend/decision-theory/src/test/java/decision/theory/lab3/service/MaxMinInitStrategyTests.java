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
        assertEquals(0, result.getRowIndex());
        assertEquals(3, result.getColIndex());
        assertEquals(23, result.getMaxMin());
        assertEquals(23, result.getMinMax());
    }
    
    @Test
    public void whenMinMaxAndMaxMinNotEqual() {
        var matrix = new double[][]{
            {3, 7, 9},
            {6, 1, 0},
            {2, 5, 10}
        };
        var result = new MaxMinInitStrategy().choose(matrix);
        assertEquals(1, result.getRowIndex());
        assertEquals(0, result.getColIndex());
        assertEquals(6, result.getMaxMin());
        assertEquals(2, result.getMinMax());
    }
    
}
