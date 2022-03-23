package decision.theory.lab3.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

@SuppressWarnings("checkstyle:MagicNumber")
public class ProbabilitySolverTests {

    @Test
    public void test2x2() {
        var sourceMatrix = new double[][] {
                {1, 3},
                {4, 2}
        };
        var result = new ProbabilitySolver().solve(sourceMatrix);
        assertArrayEquals(toDoubleArray(List.of(0.25, 0.75)), toDoubleArray(result.getRowResult()), 0.01);
        assertArrayEquals(toDoubleArray(List.of(0.5, 0.5)), toDoubleArray(result.getColResult()), 0.01);
        assertEquals(2.5, result.getGamePrice(), 0.01);
    }
    
    @Test
    public void test2x3() {
        var sourceMatrix = new double[][] {
                {1, 2, 4},
                {3, 5, 2}
        };
        var result = new ProbabilitySolver().solve(sourceMatrix);
        assertArrayEquals(toDoubleArray(List.of(0.25, 0.75)), toDoubleArray(result.getColResult()), 0.01);
        assertArrayEquals(toDoubleArray(List.of(0.5, 0.0, 0.5)), toDoubleArray(result.getRowResult()), 0.01);
        assertEquals(2.5, result.getGamePrice(), 0.01);
    }
    
    @Test
    public void test3x2() {
        var sourceMatrix = new double[][] {
                {1, 4},
                {3, 2},
                {1, 0}
        };
        var result = new ProbabilitySolver().solve(sourceMatrix);
        assertArrayEquals(toDoubleArray(List.of(0.25, 0.75, 0.0)), toDoubleArray(result.getColResult()), 0.01);
        assertArrayEquals(toDoubleArray(List.of(0.5, 0.5)), toDoubleArray(result.getRowResult()), 0.01);
        assertEquals(2.5, result.getGamePrice(), 0.01);
    }
    
    
    @Test
    public void test2x2WithDominantRow() {
        var sourceMatrix = new double[][] {
                {5, 5},
                {1, 3},
                {4, 2}
        };
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ProbabilitySolver().solve(sourceMatrix);
        });
        String expectedMessage = "MaxMin = MinMax. Mix strategies can't be found. Game price = 5,00";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void test2x2WithDominantCol() {
        var sourceMatrix = new double[][] {
                {1, 0, 3},
                {4, 0, 2}
        };
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ProbabilitySolver().solve(sourceMatrix);
        });
        String expectedMessage = "MaxMin = MinMax. Mix strategies can't be found. Game price = 0,00";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    private double[] toDoubleArray(List<Double> list) {
        return list.stream()
                .mapToDouble(d -> d)
                .toArray();
    }
    
}
